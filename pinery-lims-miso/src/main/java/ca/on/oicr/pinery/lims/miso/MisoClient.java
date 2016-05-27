package ca.on.oicr.pinery.lims.miso;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Change;
import ca.on.oicr.pinery.api.ChangeLog;
import ca.on.oicr.pinery.api.Instrument;
import ca.on.oicr.pinery.api.InstrumentModel;
import ca.on.oicr.pinery.api.Lims;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.api.PreparationKit;
import ca.on.oicr.pinery.api.Run;
import ca.on.oicr.pinery.api.RunPosition;
import ca.on.oicr.pinery.api.RunSample;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Status;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.api.User;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultAttributeName;
import ca.on.oicr.pinery.lims.DefaultChangeLog;
import ca.on.oicr.pinery.lims.DefaultInstrument;
import ca.on.oicr.pinery.lims.DefaultInstrumentModel;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultPreparationKit;
import ca.on.oicr.pinery.lims.DefaultRun;
import ca.on.oicr.pinery.lims.DefaultSample;
import ca.on.oicr.pinery.lims.DefaultSampleProject;
import ca.on.oicr.pinery.lims.DefaultStatus;
import ca.on.oicr.pinery.lims.DefaultType;
import ca.on.oicr.pinery.lims.DefaultUser;
import ca.on.oicr.pinery.lims.miso.MisoClient.SampleRowMapper.AttributeKey;

public class MisoClient implements Lims {
  
  private static final Logger log = LoggerFactory.getLogger(MisoClient.class);
  
  private static final String MISO_SAMPLE_ID_PREFIX = "SAM";
  private static final String MISO_LIBRARY_ID_PREFIX = "LIB";
  
  // InstrumentModel queries
  private static final String queryAllModels = "SELECT p.platformId, p.instrumentModel " +
      "FROM Platform as p";
  private static final String queryModelById = queryAllModels + " WHERE p.platformId = ?";
  
  // Instrument queries
  private static final String queryAllInstruments = "SELECT sr.referenceId, sr.name, sr.platformId " + 
      "FROM SequencerReference AS sr";
  private static final String queryInstrumentById = queryAllInstruments + " WHERE sr.referenceId = ?";
  private static final String queryInstrumentsByModelId = queryAllInstruments + " WHERE sr.platformId = ?";
  
  // Order queries
  private static final String queryAllOrders = "SELECT o.poolOrderId orderId, o.creationDate createdDate, o.createdBy createdById, " +
      "o.lastUpdated modifiedDate, o.updatedBy modifiedById, pool.platformType platform " +
      "FROM PoolOrder o " +
      "JOIN Pool pool ON pool.poolId = o.poolId ";
  private static final String queryOrderById = queryAllOrders + " WHERE poolOrderId = ?";
  private static final String queryAllOrderSamples = "SELECT o.poolOrderId orderId, lib.name libraryId, bc1.sequence barcode, " +
      "bc2.sequence barcode_two, sp.paired paired, sp.readLength read_length, tr.alias targeted_resequencing " +
      "FROM PoolOrder o " +
      "LEFT JOIN SequencingParameters sp ON sp.parametersId = o.parametersId " +
      "LEFT JOIN Pool p ON p.poolId = o.poolId " +
      "LEFT JOIN Pool_Elements pe ON pe.elementType='uk.ac.bbsrc.tgac.miso.core.data.impl.LibraryDilution' " +
      "AND pe.pool_poolId = p.poolId " +
      "LEFT JOIN LibraryDilution ld ON ld.dilutionId = pe.elementId " +
      "LEFT JOIN TargetedResequencing tr ON tr.targetedResequencingId = ld.targetedResequencingId " +
      "LEFT JOIN Library lib ON lib.libraryId = ld.library_libraryId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name NOT LIKE 'N5%' AND name NOT LIKE 'S5%' " +
      ") bc1 ON bc1.library_libraryId = lib.libraryId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name LIKE 'N5%' OR name LIKE 'S5%' " +
      ") bc2 ON bc2.library_libraryId = lib.libraryId";
  private static final String queryOrderSamplesByOrderId = queryAllOrderSamples + " WHERE poolOrderId = ?";
  
  // User queries
  private static final String queryAllUsers = "SELECT u.userId, u.fullname, u.email, u.active " +
      "FROM User AS u";
  private static final String queryUserById = queryAllUsers + " WHERE u.userId = ?";
  
  // Run queries
  private static final String queryAllRuns = "SELECT DISTINCT r.alias, r.sequencerReference_sequencerReferenceId AS instrumentId, " +
      "r.runId, r.filePath, st.health, st.startDate, st.completionDate, spc.identificationBarcode, createLog.userId, " +
      "createLog.changeTime, updateLog.userId, updateLog.changeTime, sp.paired paired, sp.readLength read_length " +
      "FROM Run AS r " +
      "LEFT JOIN Status AS st ON st.statusId = r.status_statusId " +
      "LEFT JOIN SequencingParameters AS sp ON sp.parametersId = r.sequencingParameters_parametersId " +
      "LEFT JOIN Run_SequencerPartitionContainer AS rscp ON rscp.Run_runId = r.runId " +
      "LEFT JOIN SequencerPartitionContainer AS spc ON spc.containerId = rscp.containers_containerId " +
      "LEFT JOIN RunChangeLog AS createLog ON createLog.runId = r.runId " +
      "LEFT JOIN RunChangeLog AS rcl1 ON rcl1.runId = createLog.runId AND createLog.changeTime > rcl1.changeTime " +
      "LEFT JOIN RunChangeLog AS updateLog ON updateLog.runId = r.runId " +
      "LEFT JOIN RunChangeLog AS rcl2 ON rcl2.runId = updateLog.runId AND updateLog.changeTime < rcl2.changeTime " +
      "WHERE rcl1.runId IS NULL AND rcl2.runId IS NULL";
  private static final String queryRunById = queryAllRuns + " AND r.runId = ?";
  private static final String queryRunByName = queryAllRuns + " AND r.alias = ?";
  
  // RunPosition queries
  private static final String queryAllRunPositions = "SELECT p.partitionId, p.partitionNumber, r_spc.Run_runId " +
      "FROM _Partition AS p " +
      "JOIN SequencerPartitionContainer_Partition AS spc_p ON spc_p.partitions_partitionId = p.partitionId " +
      "JOIN Run_SequencerPartitionContainer AS r_spc ON r_spc.containers_containerId = spc_p.container_containerId";
  private static final String queryRunPositionsByRunId = queryAllRunPositions + " WHERE r_spc.Run_runId = ?";
  
  // RunSample queries
  private static final String queryAllRunSamples = "SELECT part.partitionId, l.name libraryId, bc1.sequence barcode, " +
      "bc2.sequence barcode_two, tr.alias targeted_resequencing " +
      "FROM _Partition part " +
      "JOIN Pool pool ON pool.poolId = part.pool_poolId " +
      "JOIN Pool_Elements ele ON ele.elementType='uk.ac.bbsrc.tgac.miso.core.data.impl.LibraryDilution'" + // scary
      "AND ele.pool_poolId = pool.poolId " +
      "JOIN LibraryDilution ld ON ld.dilutionId = ele.elementId " +
      "JOIN Library l ON l.libraryId = ld.library_libraryId " +
      "LEFT JOIN TargetedResequencing tr ON tr.targetedResequencingId = ld.targetedResequencingId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name NOT LIKE 'N5%' AND name NOT LIKE 'S5%' " +
      ") bc1 ON bc1.library_libraryId = l.libraryId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name LIKE 'N5%' OR name LIKE 'S5%' " +
      ") bc2 ON bc2.library_libraryId = l.libraryId";
  private static final String queryRunSamplesByRunId = queryAllRunSamples + 
      " JOIN SequencerPartitionContainer_Partition spcp ON spcp.partitions_partitionId = part.partitionId" +
      " JOIN SequencerPartitionContainer spc ON spc.containerId = spcp.container_containerId" +
      " JOIN Run_SequencerPartitionContainer rcpc ON rcpc.containers_containerId = spc.containerId" +
      " WHERE rcpc.Run_runId = ?";
  
  // Sample queries
  private static final String queryAllSamples = "SELECT s.alias name, s.description description, s.name id, " +
      "parent.name parentId, sc.alias sampleType, NULL sampleType_platform, NULL sampleType_description, tt.alias tissueType, " +
      "p.alias project, sai.archived archived, sai.creationDate created, sai.createdBy createdById, sai.lastUpdated modified, " +
      "sai.updatedBy modifiedById, s.identificationBarcode tubeBarcode, s.volume volume, sai.concentration concentration, " +
      "s.locationBarcode storageLocation, kd.name kitName, kd.description kitDescription, s.receivedDate receive_date, " +
      "i.externalName external_name, tor.alias tissue_origin, tm.alias tissue_preparation, sa.region tissue_region, sa.tubeId tube_id, " +
      "sa.strStatus str_result, sa.groupId group_id, sa.groupDescription group_id_description, sp.alias purpose, qubit.results qubit_concentration, " +
      "nanodrop.results nanodrop_concentration, NULL barcode, NULL barcode_two, qpcr.results qpcr_percentage_human, " +
      "s.qcPassed qcPassed, box.locationBarcode boxLocation, box.alias boxAlias, pos.row boxRow, pos.column boxColumn, " +
      "NULL paired, NULL read_length, NULL targeted_resequencing " +
      "FROM Sample s " +
      "LEFT JOIN SampleAdditionalInfo sai ON sai.sampleId = s.sampleId " +
      "LEFT JOIN Sample parent ON parent.sampleId = sai.parentId " +
      "LEFT JOIN SampleClass sc ON sc.sampleClassId = sai.sampleClassId " +
      "LEFT JOIN TissueType tt ON tt.tissueTypeId = sai.tissueTypeId " +
      "LEFT JOIN Project p ON p.projectId = s.project_projectId " +
      "LEFT JOIN KitDescriptor kd ON kd.kitDescriptorId = sai.kitDescriptorId " +
      "LEFT JOIN Identity i ON i.sampleId = s.sampleId " +
      "LEFT JOIN TissueOrigin tor ON tor.tissueOriginId = sai.tissueOriginId " +
      "LEFT JOIN SampleAnalyte sa ON sa.sampleId = s.sampleId " +
      "LEFT JOIN TissueMaterial tm ON tm.tissueMaterialId = sa.tissueMaterialId " +
      "LEFT JOIN SamplePurpose sp ON sp.samplePurposeId = sa.samplePurposeId " +
      "LEFT JOIN ( " +
        "SELECT sample_sampleId, results FROM SampleQC JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod " +
        "WHERE QCType.name = 'QuBit' " +
      ") qubit ON qubit.sample_sampleId = s.sampleId " +
      "LEFT JOIN ( " +
        "SELECT sample_sampleId, results FROM SampleQC JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod " +
        "WHERE QCType.name = 'Nanodrop' " +
      ") nanodrop ON nanodrop.sample_sampleId = s.sampleId " +
      "LEFT JOIN ( " +
        "SELECT sample_sampleId, results FROM SampleQC JOIN QCType ON QCType.qcTypeId = SampleQC.qcMethod " +
        "WHERE QCType.name = 'Human qPCR' " +
      ") qpcr ON qpcr.sample_sampleId = s.sampleId " +
      "LEFT JOIN BoxPosition pos ON pos.boxPositionId = s.boxPositionId " +
      "LEFT JOIN Box box ON box.boxId = pos.boxId " +
      "UNION ALL " +
      "SELECT l.alias name, l.description description, l.name id, parent.name parentId, NULL sampleType, " +
      "lt.platformType sampleType_platform, lt.description sampleType_description, tt.alias tissueType, " +
      "p.alias project, lai.archived archived, lai.creationDate created, lai.createdBy createdById, lai.lastUpdated modified, " +
      "lai.updatedBy modifiedById, l.identificationBarcode tubeBarcode, l.volume volume, l.concentration concentration, " +
      "l.locationBarcode storageLocation, kd.name kitName, kd.description kitDescription, NULL receive_date, NULL external_name, " +
      "tor.alias tissue_origin, NULL tissue_preparation, NULL tissue_region, NULL tube_id, NULL str_result, lai.groupId group_id, " +
      "lai.groupDescription group_id_description, NULL purpose, qubit.results qubit_concentration, NULL nanodrop_concentration, " +
      "bc1.sequence barcode, bc2.sequence barcode_two, NULL qpcr_percentage_human, l.qcPassed qcPassed, " +
      "box.locationBarcode boxLocation, box.alias boxAlias, pos.row boxRow, pos.column boxColumn, NULL paired, NULL readLength, " +
      "NULL targeted_resequencing " +
      "FROM Library l " +
      "LEFT JOIN Sample parent ON parent.sampleId = l.sample_sampleId " +
      "LEFT JOIN Project p ON p.projectId = parent.project_projectId " +
      "LEFT JOIN LibraryAdditionalInfo lai ON lai.libraryId = l.libraryId " +
      "LEFT JOIN TissueType tt ON tt.tissueTypeId = lai.tissueTypeId " +
      "LEFT JOIN KitDescriptor kd ON kd.kitDescriptorId = lai.kitDescriptorId " +
      "LEFT JOIN TissueOrigin tor ON tor.tissueOriginId = lai.tissueOriginId " +
      "LEFT JOIN LibraryType lt ON lt.libraryTypeId = l.libraryType " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, results FROM LibraryQC JOIN QCType ON QCType.qcTypeId = LibraryQC.qcMethod " +
        "WHERE QCType.name = 'QuBit' " +
      ") qubit ON qubit.library_libraryId = l.libraryId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name NOT LIKE 'N5%' AND name NOT LIKE 'S5%' " +
      ") bc1 ON bc1.library_libraryId = l.libraryId " +
      "LEFT JOIN ( " +
        "SELECT library_libraryId, sequence FROM Library_TagBarcode " +
        "JOIN TagBarcodes ON TagBarcodes.tagId = Library_TagBarcode.barcode_barcodeId " +
        "WHERE name LIKE 'N5%' OR name LIKE 'S5%' " +
      ") bc2 ON bc2.library_libraryId = l.libraryId " +
      "LEFT JOIN BoxPosition pos ON pos.boxPositionId = l.boxPositionId " +
      "LEFT JOIN Box box ON box.boxId = pos.boxId";
  private static final String querySampleById = "SELECT * FROM (" + queryAllSamples + ") combined " + 
      "WHERE id = ?";
  
  private static final String querySampleChildIdsBySampleId = "SELECT child.name id " +
      "FROM Sample child " +
      "JOIN SampleAdditionalInfo csai ON csai.sampleId = child.sampleId " +
      "JOIN Sample parent ON parent.sampleId = csai.parentId " +
      "WHERE parent.name = ? " +
      "UNION ALL " +
      "SELECT child.name id " +
      "FROM Library child " +
      "JOIN Sample parent ON parent.sampleId = child.sample_sampleId " +
      "WHERE parent.name = ?";
  
  // SampleType (MISO SampleClass and Library) queries
  private static final String queryAllSampleTypes = "SELECT sc.alias name, NULL sampleType_platform, NULL sampleType_description, " +
      "COUNT(*) count, COUNT(CASE WHEN sai.archived = true THEN sai.archived END) archivedCount, MIN(sai.creationDate) earliest, " +
      "MAX(sai.lastUpdated) latest " +
      "FROM SampleAdditionalInfo sai " +
      "JOIN SampleClass sc ON sc.sampleClassId = sai.sampleClassId " +
      "GROUP BY sai.sampleClassId " +
      "UNION ALL " +
      "SELECT NULL name, lt.platformType sampleType_platform, lt.description sampleType_description, COUNT(*) count, " +
      "COUNT(CASE WHEN lai.archived = true THEN lai.archived END) archivedCount, MIN(lai.creationDate) earliest, " +
      "MAX(lai.lastUpdated) latest " +
      "FROM Library l " +
      "JOIN LibraryAdditionalInfo lai ON lai.libraryId = l.libraryId " +
      "JOIN LibraryType lt ON lt.libraryTypeId = l.libraryType " +
      "GROUP BY l.libraryType";
  
  // SampleProject queries
  private static final String queryAllSampleProjects = "SELECT name, COUNT(*) count, " +
      "COUNT(CASE WHEN archived = true THEN archived END) archivedCount, MIN(created) earliest, MAX(updated) latest " +
      "FROM (" +
        "SELECT sp.alias name, sai.archived archived, sai.creationDate created, sai.lastUpdated updated " +
        "FROM SampleAdditionalInfo sai " +
        "JOIN Sample s ON s.sampleId = sai.sampleId " +
        "JOIN Project sp ON sp.projectId = s.project_projectId " +
        "UNION ALL " +
        "SELECT lp.alias name, lai.archived archived, lai.creationDate created, lai.lastUpdated updated " +
        "FROM LibraryAdditionalInfo lai " +
        "JOIN Library l ON l.libraryId = lai.libraryId " +
        "JOIN Sample ls ON l.sample_sampleId = ls.sampleId " +
        "JOIN Project lp ON lp.projectId = ls.project_projectId " +
      ") combined " +
      "GROUP BY name";
  
  // SampleChangeLog queries
  private static final String queryAllSampleChangeLogs = "SELECT s.name sampleId, scl.message action, scl.userId, scl.changeTime " +
      "FROM SampleChangeLog scl " +
      "JOIN Sample s ON s.sampleId = scl.sampleId " +
      "UNION ALL " +
      "SELECT l.name sampleId, lcl.message action, lcl.userId, lcl.changeTime " +
      "FROM LibraryChangeLog lcl " +
      "JOIN Library l ON l.libraryId = lcl.libraryId";
  private static final String querySampleChangeLogById = "SELECT * FROM (" + queryAllSampleChangeLogs + ") combined " +
      "WHERE sampleId = ?";
  
  
  private final RowMapper<Instrument> instrumentMapper = new InstrumentMapper();
  private final RowMapper<InstrumentModel> modelMapper = new InstrumentModelRowMapper();
  private final RowMapper<Order> orderMapper = new OrderRowMapper();
  private final RowMapper<MisoOrderSample> orderSampleMapper = new OrderSampleRowMapper();
  private final RowMapper<User> userMapper = new UserRowMapper();
  private final RowMapper<Run> runMapper = new RunRowMapper();
  private final RowMapper<MisoRunPosition> runPositionMapper = new RunPositionRowMapper();
  private final RowMapper<Sample> sampleMapper = new SampleRowMapper();
  private final RowMapper<MisoRunSample> runSampleMapper = new RunSampleRowMapper();
  private final RowMapper<Type> typeMapper = new TypeRowMapper();
  private final RowMapper<SampleProject> sampleProjectMapper = new SampleProjectMapper();
  private final RowMapper<MisoChange> changeMapper = new ChangeMapper();
  private final RowMapper<String> idListMapper = new IdListMapper();
  
  private JdbcTemplate template;

  public MisoClient(JdbcTemplate template) {
    this.template = template;
  }
  
  public MisoClient(DataSource dataSource) {
    this.template = new JdbcTemplate(dataSource);
  }

  public JdbcTemplate getJdbcTemplate() {
    return template;
  }

  public void setJdbcTemplate(JdbcTemplate template) {
    this.template = template;
  }

  @Override
  public Sample getSample(String id) {
    validateSampleId(id);
    List<Sample> samples = template.query(querySampleById, new Object[]{id}, sampleMapper);
    return samples.size() == 1 ? addChildren(samples.get(0)) : null;
  }
  
  private void validateSampleId(String id) {
    if (id != null && id.length() > 3) {
      try {
        Integer.parseInt(id.substring(3, id.length()));
        String idType = id.substring(0, 3);
        if (idType.equals(MISO_SAMPLE_ID_PREFIX) || idType.equals(MISO_LIBRARY_ID_PREFIX)) {
          return;
        }
      } catch (NumberFormatException e) {
        // Ignore; will end up throwing IllegalArgumentException below
      }
    }
    throw new IllegalArgumentException("ID '" + id + "' is not in expected format (e.g. SAM12 or LIB345)");
  }

  @Override
  public List<SampleProject> getSampleProjects() {
    return template.query(queryAllSampleProjects, sampleProjectMapper);
  }

  @Override
  public List<Sample> getSamples(Boolean archived, Set<String> projects,
      Set<String> types, DateTime before, DateTime after) {
    List<Sample> samples = template.query(queryAllSamples, sampleMapper);
    mapChildren(samples);
    if (archived == null && (projects == null || projects.isEmpty()) && (types == null || types.isEmpty())
        && before == null && after == null) {
      return samples;
    } else {
      return filterSamples(samples, archived, projects, types, before, after);
    }
  }
  
  private List<Sample> filterSamples(List<Sample> unfiltered, Boolean archived, Set<String> projects,
      Set<String> types, DateTime before, DateTime after) {
    Set<Filter<Sample>> filters = makeSampleFilters(archived, projects, types, before, after);
    List<Sample> filteredSamples = new ArrayList<>();
    for (Sample sample : unfiltered) {
      boolean match = true;
      for (Filter<Sample> filter : filters) {
        if (!filter.matches(sample)) {
          match = false;
          break;
        }
      }
      if (match) filteredSamples.add(sample);
    }
    return filteredSamples;
  }
  
  private Set<Filter<Sample>> makeSampleFilters(final Boolean archived, final Set<String> projects,
      final Set<String> types, final DateTime before, final DateTime after) {
    Set<Filter<Sample>> filters = new HashSet<>();
    
    // archived filter
    if (archived != null) {
      filters.add(new Filter<Sample>() {
        @Override
        public boolean matches(Sample object) {
          return archived.equals(object.getArchived());
        }
      });
    }
    
    // projects filter
    if (projects != null && !projects.isEmpty()) {
      filters.add(new Filter<Sample>() {
        @Override
        public boolean matches(Sample object) {
          for (String project : projects) {
            if (project.equals(object.getProject())) {
              return true;
            }
          }
          return false;
        }
      });
    }
    
    // types filter
    if (types != null && !types.isEmpty()) {
      filters.add(new Filter<Sample>() {
        @Override
        public boolean matches(Sample object) {
          for (String type : types) {
            if (type.equals(object.getSampleType())) {
              return true;
            }
          }
          return false;
        }
      });
    }
    
    // before filter
    if (before != null) {
      filters.add(new Filter<Sample>() {
        @Override
        public boolean matches(Sample object) {
          return before.isAfter(object.getCreated().getTime());
        }
      });
    }
    
    // after filter
    if (after != null) {
      filters.add(new Filter<Sample>() {
        @Override
        public boolean matches(Sample object) {
          return after.isBefore(object.getModified().getTime());
        }
      });
    }
    return filters;
  }
  
  private interface Filter<T> {
    public boolean matches(T object);
  }
  
  public List<Sample> getSamples() {
    return getSamples(null, null, null, null, null);
  }
  
  private Sample addChildren(Sample parent) {
    List<String> children = template.query(querySampleChildIdsBySampleId, new Object[]{parent.getId(), parent.getId()}, idListMapper);
    if (children.size() > 0) {
      parent.setChildren(new HashSet<>(children));
    }
    return parent;
  }
  
  private List<Sample> mapChildren(List<Sample> samples) {
    Map<String, Sample> map = new HashMap<>();
    for (Sample sample : samples) {
      map.put(sample.getId(), sample);
    }
    for (Sample child : samples) {
      if (child.getParents() != null) {
        for (String parentId : child.getParents()) {
          Sample parent = map.get(parentId);
          if (parent.getChildren() == null) {
            parent.setChildren(new HashSet<String>());
          }
          parent.getChildren().add(child.getId());
        }
      }
    }
    return samples;
  }

  @Override
  public List<User> getUsers() {
    return template.query(queryAllUsers, userMapper);
  }

  @Override
  public User getUser(Integer id) {
    List<User> users = template.query(queryUserById, new Object[]{id}, userMapper);
    return users.size() == 1 ? users.get(0) : null;
  }

  @Override
  public List<Order> getOrders() {
    List<Order> orders = template.query(queryAllOrders, orderMapper);
    List<MisoOrderSample> samples = getOrderSamples();
    mapSamplesToOrders(orders, samples);
    return orders;
  }

  @Override
  public Order getOrder(Integer id) {
    List<Order> orders = template.query(queryOrderById, new Object[]{id}, orderMapper);
    if (orders.size() != 1) return null;
    Order order = orders.get(0);
    Set<OrderSample> os = new HashSet<>();
    os.addAll(getOrderSamples(id));
    order.setSample(os);
    return order;
  }
  
  private List<MisoOrderSample> getOrderSamples() {
    return template.query(queryAllOrderSamples, orderSampleMapper);
  }
  
  private List<MisoOrderSample> getOrderSamples(Integer orderId) {
    return template.query(queryOrderSamplesByOrderId,  new Object[]{orderId}, orderSampleMapper);
  }
  
  private List<Order> mapSamplesToOrders(List<Order> orders, List<MisoOrderSample> samples) {
    Map<Integer, Order> map = new HashMap<>();
    for (Order o : orders) {
      map.put(o.getId(), o);
    }
    for (MisoOrderSample s : samples) {
      Order o = map.get(s.getOrderId());
      if (o != null) {
        Set<OrderSample> os = o.getSamples();
        if (os == null) {
          os = new HashSet<OrderSample>();
          o.setSample(os);
        }
        os.add(s);
      }
    }
    return orders;
  }

  @Override
  public List<Run> getRuns() {
    List<Run> runs = template.query(queryAllRuns, runMapper);
    List<MisoRunPosition> positions = getRunPositions();
    Map<Integer, Run> map = new HashMap<>();
    for (Run r : runs) {
      map.put(r.getId(), r);
    }
    for (MisoRunPosition p : positions) {
      Run r = map.get(p.getRunId());
      if (r != null) {
        Set<RunPosition> rp = r.getSamples();
        if (rp == null) {
          rp = new HashSet<RunPosition>();
          r.setSample(rp);
        }
        rp.add(p);
      }
    }
    return runs;
  }

  @Override
  public Run getRun(Integer id) {
    return getSingleRun(queryRunById, new Object[]{id});
  }

  @Override
  public Run getRun(String runName) {
    return getSingleRun(queryRunByName, new Object[]{runName});
  }
  
  private Run getSingleRun(String query, Object[] params) {
    List<Run> runs = template.query(query, params, runMapper);
    if (runs.size() != 1) return null;
    Run run = runs.get(0);
    Set<RunPosition> rp = new HashSet<>();
    rp.addAll(getRunPositions(run.getId()));
    run.setSample(rp);
    return run;
  }
  
  private List<MisoRunPosition> getRunPositions() {
    List<MisoRunPosition> positions = template.query(queryAllRunPositions, runPositionMapper);
    List<MisoRunSample> samples = getRunSamples();
    return mapSamplesToPositions(positions, samples);
  }
  
  private List<MisoRunPosition> getRunPositions(Integer runId) {
    List<MisoRunPosition> positions = template.query(queryRunPositionsByRunId, new Object[]{runId},runPositionMapper);
    List<MisoRunSample> samples = getRunSamples(runId);
    return mapSamplesToPositions(positions, samples);
  }
  
  private List<MisoRunPosition> mapSamplesToPositions(List<MisoRunPosition> positions, List<MisoRunSample> samples) {
    Map<Integer, MisoRunPosition> map = new HashMap<>();
    for (MisoRunPosition p : positions) {
      map.put(p.getPartitionId(), p);
    }
    for (MisoRunSample s : samples) {
      MisoRunPosition p = map.get(s.getPartitionId());
      if (p != null) {
        Set<RunSample> rs = p.getRunSample();
        if (rs == null) {
          rs = new HashSet<RunSample>();
          p.setRunSample(rs);
        }
        rs.add(s);
      }
    }
    return positions;
  }
  
  private List<MisoRunSample> getRunSamples() {
    return template.query(queryAllRunSamples, runSampleMapper);
  }
  
  private List<MisoRunSample> getRunSamples(Integer runId) {
    return template.query(queryRunSamplesByRunId, new Object[]{runId}, runSampleMapper);
  }

  @Override
  public List<Type> getTypes() {
    return template.query(queryAllSampleTypes, typeMapper);
  }

  @Override
  public List<AttributeName> getAttributeNames() {
    List<Sample> allSamples = getSamples();
    Map<String, AttributeName> map = new HashMap<>();
    for (Sample sample : allSamples) {
      int archivedIncrement = sample.getArchived() ? 1 : 0;
      if (sample.getAttributes() != null) {
        for (Attribute att : sample.getAttributes()) {
          AttributeName stats = map.get(att.getName());
          if (stats == null) {
            stats = new DefaultAttributeName();
            stats.setName(att.getName());
            stats.setArchivedCount(0);
            stats.setCount(0);
            map.put(stats.getName(), stats);
          }
          stats.setCount(stats.getCount() + 1);
          stats.setArchivedCount(stats.getArchivedCount() + archivedIncrement);
          if (stats.getEarliest() == null || sample.getCreated().before(stats.getEarliest())) {
            stats.setEarliest(sample.getCreated());
          }
          if (stats.getLatest() == null || sample.getModified().after(stats.getLatest())) {
            stats.setLatest(sample.getModified());
          }
        }
      }
    }
    return new ArrayList<AttributeName>(map.values());
  }

  @Override
  public List<ChangeLog> getChangeLogs() {
    return mapChangesToChangeLogs(template.query(queryAllSampleChangeLogs, changeMapper));
  }

  @Override
  public ChangeLog getChangeLog(String id) {
    validateSampleId(id);
    List<ChangeLog> changes = mapChangesToChangeLogs(template.query(querySampleChangeLogById, new Object[]{id}, changeMapper));
    return changes.size() == 1 ? changes.get(0) : null;
  }
  
  private List<ChangeLog> mapChangesToChangeLogs(List<MisoChange> changes) {
    Map<String, ChangeLog> map = new HashMap<>();
    for (MisoChange c : changes) {
     ChangeLog l = map.get(c.getSampleId());
     if (l == null) {
       l = new DefaultChangeLog();
       l.setSampleId(c.getSampleId());
       map.put(l.getSampleId(), l);
     }
     Set<Change> ch = l.getChanges();
     if (ch == null) {
       ch = new HashSet<>();
       l.setChanges(ch);
     }
     ch.add(c);
    }
    return new ArrayList<ChangeLog>(map.values());
  }

  @Override
  public List<InstrumentModel> getInstrumentModels() {
    return template.query(queryAllModels, modelMapper);
  }

  @Override
  public InstrumentModel getInstrumentModel(Integer id) {
    List<InstrumentModel> models = template.query(queryModelById, new Object[]{id}, modelMapper);
    return models.size() == 1 ? models.get(0) : null;
  }

  @Override
  public List<Instrument> getInstruments() {
    return template.query(queryAllInstruments, instrumentMapper);
  }

  @Override
  public Instrument getInstrument(Integer instrumentId) {
    List<Instrument> instruments = template.query(queryInstrumentById, new Object[]{instrumentId}, instrumentMapper);
    return instruments.size() == 1 ? instruments.get(0) : null;
  }

  @Override
  public List<Instrument> getInstrumentModelInstrument(Integer id) {
    return template.query(queryInstrumentsByModelId, new Object[]{id}, instrumentMapper);
  }
  
  private static class InstrumentMapper implements RowMapper<Instrument> {

    @Override
    public Instrument mapRow(ResultSet rs, int rowNum) throws SQLException {
      Instrument ins = new DefaultInstrument();
      
      ins.setId(rs.getInt("referenceId"));
      ins.setName(rs.getString("name"));
      ins.setModelId(rs.getInt("platformId"));
      
      return ins;
    }
    
  }
  
  private static class InstrumentModelRowMapper implements RowMapper<InstrumentModel> {

    @Override
    public InstrumentModel mapRow(ResultSet rs, int rowNum) throws SQLException {
      InstrumentModel m = new DefaultInstrumentModel();
      
      m.setId(rs.getInt("platformId"));
      m.setName(rs.getString("instrumentModel"));
      
      return m;
    }
    
  }
  
  private static class OrderRowMapper implements RowMapper<Order> {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
      Order o = new DefaultOrder();
      
      o.setId(rs.getInt("orderId"));
      o.setPlatform(rs.getString("platform"));
      o.setCreatedById(rs.getInt("createdById"));
      o.setCreatedDate(rs.getTimestamp("createdDate"));
      o.setModifiedById(rs.getInt("modifiedById"));
      o.setModifiedDate(rs.getTimestamp("modifiedDate"));
      
      return o;
    }
    
  }
  
  private static class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
      User u = new DefaultUser();
      
      u.setId(rs.getInt("userId"));
      
      String fullname = rs.getString("fullname");
      if (fullname.contains(" ")) {
        u.setFirstname(fullname.substring(0, fullname.lastIndexOf(" ")));
        u.setLastname(fullname.substring(fullname.lastIndexOf(" ") + 1));
      }
      else {
        u.setFirstname(fullname);
        u.setLastname(fullname);
      }
      
      u.setEmail(rs.getString("email"));
      u.setArchived(!rs.getBoolean("active"));
      
      return u;
    }
    
  }
  
  private static class RunRowMapper implements RowMapper<Run> {

    @Override
    public Run mapRow(ResultSet rs, int rowNum) throws SQLException {
      Run r = new DefaultRun();
      
      r.setState(rs.getString("health"));
      r.setName(rs.getString("alias"));
      r.setBarcode(rs.getString("identificationBarcode"));
      r.setInstrumentId(rs.getInt("instrumentId"));
      r.setCreatedById(rs.getInt("createLog.userId"));
      r.setCreatedDate(rs.getTimestamp("createLog.changeTime"));
      r.setModifiedById(rs.getInt("updateLog.userId"));
      r.setModified(rs.getTimestamp("updateLog.changeTime"));
      r.setId(rs.getInt("runId"));
      r.setStartDate(rs.getDate("startDate"));
      r.setCompletionDate(rs.getDate("completionDate"));
      r.setReadLength(AttributeKey.READ_LENGTH.extractStringValueFrom(rs));
      r.setRunDirectory(rs.getString("filePath"));
      
      return r;
    }
    
  }
  
  private static class RunPositionRowMapper implements RowMapper<MisoRunPosition> {

    @Override
    public MisoRunPosition mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRunPosition p = new MisoRunPosition();
      
      p.setPosition(rs.getInt("partitionNumber"));
      p.setRunId(rs.getInt("Run_runId"));
      p.setPartitionId(rs.getInt("partitionId"));
      
      return p;
    }
    
  }
  
  public static class SampleRowMapper implements RowMapper<Sample> {

    private static final String SAMPLE_STATUS_NAME = "Ready";
    private static final String SAMPLE_STATUS_UNKNOWN = "Unknown";
    private static final String SAMPLE_STATUS_READY = "Ready";
    private static final String SAMPLE_STATUS_NOT_READY = "Not Ready";
    
    @Override
    public Sample mapRow(ResultSet rs, int rowNum) throws SQLException {
      Sample s = new DefaultSample();
      
      s.setName(rs.getString("name"));
      s.setDescription(rs.getString("description"));
      s.setId(rs.getString("id"));
      String parentId = rs.getString("parentId");
      if (parentId != null) {
        Set<String> parents = new HashSet<>();
        parents.add(parentId);
        s.setParents(parents);
      };
      if (rs.getString("sampleType") != null) {
        s.setSampleType(rs.getString("sampleType"));
      } else {
        s.setSampleType(TypeRowMapper.mapSampleType(rs.getString("sampleType_platform"), rs.getString("sampleType_description")));
      }
      s.setTissueType(rs.getString("tissueType"));
      s.setProject(rs.getString("project"));
      s.setArchived(rs.getBoolean("archived"));
      s.setCreated(rs.getTimestamp("created"));
      s.setCreatedById(rs.getInt("createdById"));
      s.setModified(rs.getTimestamp("modified"));
      s.setModifiedById(rs.getInt("modifiedById"));
      s.setTubeBarcode(rs.getString("tubeBarcode"));
      s.setVolume(rs.getFloat("volume"));
      s.setConcentration(rs.getFloat("concentration"));
      s.setStorageLocation(extractStorageLocation(rs));
      PreparationKit kit = new DefaultPreparationKit();
      kit.setName(rs.getString("kitName"));
      kit.setDescription(rs.getString("kitDescription"));
      if (kit.getName() != null || kit.getDescription() != null) {
        s.setPreparationKit(kit);
      }
      Set<Attribute> atts = new HashSet<>();
      for (AttributeKey possibleAtt : AttributeKey.values()) {
        Attribute att = possibleAtt.extractAttributeFrom(rs);
        if (att != null) {
          atts.add(att);
        }
      }
      if (atts.size() > 0) {
        s.setAttributes(atts);
      }
      Boolean qcPassed = rs.getBoolean("qcPassed");
      Status status = new DefaultStatus();
      status.setName(SAMPLE_STATUS_NAME);
      status.setState(
          rs.wasNull() ? SAMPLE_STATUS_UNKNOWN : (qcPassed ? SAMPLE_STATUS_READY : SAMPLE_STATUS_NOT_READY));
      s.setStatus(status);
      
      return s;
    }
    
    private String extractStorageLocation(ResultSet rs) throws SQLException {
      String boxAlias = rs.getString("boxAlias");
      if (boxAlias == null) return null;
      
      String boxLocation = rs.getString("boxLocation");
      int boxRow = rs.getInt("boxRow");
      int boxColumn = rs.getInt("boxColumn");
      
      StringBuilder sb = new StringBuilder();
      if (boxLocation != null && !boxLocation.isEmpty()) {
        sb.append(boxLocation).append(", ");
      }
      sb.append(boxAlias).append(", ")
      .append(toRowChar(boxRow)).append(boxColumn+1);
      
      return sb.toString();
    }
    
    public static char toRowChar(int row) throws IllegalArgumentException {
      if (row < 0 || row > 25) throw new RuntimeException("Box row number must be between 0 and 25");
      return (char) ( row + 'A');
    }
    
    /**
     * Enum used to pull Attributes from a ResultSet, formatting values correctly and mapping them to the correct keys
     */
    public static enum AttributeKey {
      
      RECEIVE_DATE("receive_date", "Receive Date") {
        @Override
        public String extractStringValueFrom(ResultSet rs) throws SQLException {
          return rs.getDate(getSqlKey()) == null ? null : rs.getDate(getSqlKey()).toString();
        }
      },
      EXTERNAL_NAME("external_name", "External Name"),
      TISSUE_ORIGIN("tissue_origin", "Tissue Origin"),
      TISSUE_PREPARATION("tissue_preparation", "Tissue Preparation"),
      TISSUE_REGION("tissue_region", "Region"),
      TUBE_ID("tube_id", "Tube Id"),
      GROUP_ID("group_id", "Group ID"),
      GROUP_DESCRIPTION("group_id_description", "Group Description"),
      PURPOSE("purpose", "Purpose"),
      STR_RESULT("str_result", "STR") {
        @Override
        public String extractStringValueFrom(ResultSet rs) throws SQLException {
          String str = rs.getString(getSqlKey());
          return str == null ? null : StrStatus.valueOf(str).getValue();
        }
      },
      QPCR_PERCENTAGE_HUMAN("qpcr_percentage_human", "qPCR %"),
      QUBIT_CONCENTRATION("qubit_concentration", "Qubit (ng/uL)"),
      NANODROP_CONCENTRATION("nanodrop_concentration", "Nanodrop (ng/uL)"),
      BARCODE("barcode", "Barcode"),
      BARCODE_TWO("barcode_two", "Barcode Two"),
      READ_LENGTH("read_length", "Read Length") {
        private static final String PAIRED_KEY = "paired";
        
        @Override
        public String extractStringValueFrom(ResultSet rs) throws SQLException {
          boolean paired = rs.getBoolean(PAIRED_KEY);
          if (!rs.wasNull()) {
            int readLength = rs.getInt(READ_LENGTH.getSqlKey());
            if (!rs.wasNull()) {
              return (paired ? "2x" : "1x") + readLength;
            }
          }
          return null;
        }
      },
      TARGETED_RESEQUENCING("targeted_resequencing", "Targeted Resequencing");
      
      private final String sqlKey;
      private final String attributeKey;
      
      private AttributeKey(String sqlKey, String attributeKey) {
        this.sqlKey = sqlKey;
        this.attributeKey = attributeKey;
      }
      
      public String getSqlKey() {
        return sqlKey;
      }
      
      public String getAttributeKey() {
        return attributeKey;
      }
      
      /**
       * Extracts the Attribute represented by this key from the result set. The column name within the ResultSet 
       * must match this.getKey()
       * 
       * @param rs the ResultSet to extract the Attribute from
       * @return
       * @throws SQLException
       */
      public Attribute extractAttributeFrom(ResultSet rs) throws SQLException {
        String val = extractStringValueFrom(rs);
        return val == null ? null : makeAttribute(getAttributeKey(), extractStringValueFrom(rs));
      };
      
      /**
       * Extracts the value belonging to this AttributeKey from a ResultSet
       * 
       * @param rs ResultSet containing data to populate this field
       * @return the value to associate with this AttributeKey; null if absent
       * @throws SQLException
       */
      public String extractStringValueFrom(ResultSet rs) throws SQLException {
        return rs.getString(getSqlKey());
      }
      
      private static Attribute makeAttribute(String name, String value) {
        Attribute att = new DefaultAttribute();
        att.setName(name);
        att.setValue(value);
        return att;
      }
      
      private static enum StrStatus {
        NOT_SUBMITTED("Not Submitted"),
        SUBMITTED("Submitted"),
        PASS("Pass"),
        FAIL("Fail");
        
        private final String value;
        
        private StrStatus(String value) {
          this.value = value;
        }
        
        public String getValue() {
          return value;
        }
      }
      
    }
    
  }
  
  private static class RunSampleRowMapper implements RowMapper<MisoRunSample> {

    @Override
    public MisoRunSample mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoRunSample s = new MisoRunSample();
      
      s.setId(rs.getString("libraryId"));
      s.setPartitionId(rs.getInt("partitionId"));
      s.setBarcode(AttributeKey.BARCODE.extractStringValueFrom(rs));
      s.setBarcodeTwo(AttributeKey.BARCODE_TWO.extractStringValueFrom(rs));
      
      Attribute att = AttributeKey.TARGETED_RESEQUENCING.extractAttributeFrom(rs);
      if (att != null) {
        Set<Attribute> atts = new HashSet<>();
        atts.add(att);
        s.setAttributes(atts);
      }
      
      return s;
    }
    
  }
  
  private static class OrderSampleRowMapper implements RowMapper<MisoOrderSample> {

    private static final AttributeKey[] orderSampleAtts = new AttributeKey[]{AttributeKey.READ_LENGTH, AttributeKey.TARGETED_RESEQUENCING};
    
    @Override
    public MisoOrderSample mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoOrderSample s = new MisoOrderSample();
      
      s.setId(rs.getString("libraryId"));
      s.setOrderId(rs.getInt("orderId"));
      s.setBarcode(AttributeKey.BARCODE.extractStringValueFrom(rs));
      s.setBarcodeTwo(AttributeKey.BARCODE_TWO.extractStringValueFrom(rs));
      
      Set<Attribute> atts = new HashSet<>();
      for (AttributeKey possibleAtt : orderSampleAtts) {
        Attribute att = possibleAtt.extractAttributeFrom(rs);
        if (att != null) {
          atts.add(att);
        }
      }
      if (atts.size() > 0) {
        s.setAttributes(atts);
      }
      
      return s;
    }
    
  }
  
  private static class TypeRowMapper implements RowMapper<Type> {

    @Override
    public Type mapRow(ResultSet rs, int rowNum) throws SQLException {
      Type t = new DefaultType();
      
      t.setName(rs.getString("name"));
      if (t.getName() == null) {
        t.setName(TypeRowMapper.mapSampleType(rs.getString("sampleType_platform"), rs.getString("sampleType_description")));
      }
      t.setCount(rs.getInt("count"));
      t.setArchivedCount(rs.getInt("archivedCount"));
      t.setEarliest(rs.getTimestamp("earliest"));
      t.setLatest(rs.getTimestamp("latest"));
      
      return t;
    }
    
    private static final String PLATFORM_ILLUMINA = "Illumina";
    
    private static final String LIBRARY_TYPE_MRNA = "mRNA Seq";
    private static final String LIBRARY_TYPE_PAIRED_END = "Paired End";
    private static final String LIBRARY_TYPE_SMALL_RNA = "Small RNA";
    private static final String LIBRARY_TYPE_SINGLE_END = "Single End";
    private static final String LIBRARY_TYPE_WHOLE_TRANSCRIPTOME = "Whole Transcriptome";
    
    public static enum IlluminaSampleType {
      
      SE("Illumina SE Library"),
      PE("Illumina PE Library"),
      SM_RNA("Illumina smRNA Library"),
      M_RNA("Illumina mRNA Library"),
      WT("Illumina WT Library");
      
      private final String key;
      
      private IlluminaSampleType(String key) {
        this.key = key;
      }
      
      public String getKey() {
        return key;
      }
    }
    
    private static final String SAMPLE_TYPE_UNKNOWN = "Unknown";
    
    public static String mapSampleType(String platformName, String libraryType) {
      if (platformName == null) {
        log.debug("Cannot determine SampleType due to null platformName");
        return SAMPLE_TYPE_UNKNOWN;
      }
      if (libraryType == null) {
        log.debug("Cannot determine SampleType due to null libraryType");
        return SAMPLE_TYPE_UNKNOWN;
      }
      
      switch (platformName) {
      case PLATFORM_ILLUMINA:
        switch (libraryType) {
        case LIBRARY_TYPE_MRNA:
          return IlluminaSampleType.M_RNA.getKey();
        case LIBRARY_TYPE_PAIRED_END:
          return IlluminaSampleType.PE.getKey();
        case LIBRARY_TYPE_SMALL_RNA:
          return IlluminaSampleType.SM_RNA.getKey();
        case LIBRARY_TYPE_SINGLE_END:
          return IlluminaSampleType.SE.getKey();
        case LIBRARY_TYPE_WHOLE_TRANSCRIPTOME:
          return IlluminaSampleType.WT.getKey();
        default:
          log.debug("Unexpected LibraryType: " + libraryType + ", Cannot determine Sample Type");
          return SAMPLE_TYPE_UNKNOWN;
        }
      default:
        log.debug("Unknown platform: " + platformName + ". Cannot determine Sample Type");
        return SAMPLE_TYPE_UNKNOWN;
      }
    }
    
  }
  
  private static class SampleProjectMapper implements RowMapper<SampleProject> {

    @Override
    public SampleProject mapRow(ResultSet rs, int rowNum) throws SQLException {
      SampleProject p = new DefaultSampleProject();
      
      p.setName(rs.getString("name"));
      p.setCount(rs.getInt("count"));
      p.setArchivedCount(rs.getInt("archivedCount"));
      p.setEarliest(rs.getTimestamp("earliest"));
      p.setLatest(rs.getTimestamp("latest"));
      
      return p;
    }
    
  }
  
  private static class ChangeMapper implements RowMapper<MisoChange> {

    @Override
    public MisoChange mapRow(ResultSet rs, int rowNum) throws SQLException {
      MisoChange c = new MisoChange();
      
      c.setAction(rs.getString("action"));
      c.setCreated(rs.getTimestamp("changeTime"));
      c.setCreatedById(rs.getInt("userId"));
      c.setSampleId(rs.getString("sampleId"));
      
      return c;
    }
    
  }
  
  private static class IdListMapper implements RowMapper<String> {

    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
      return rs.getString("id");
    }
    
  }
  
}
