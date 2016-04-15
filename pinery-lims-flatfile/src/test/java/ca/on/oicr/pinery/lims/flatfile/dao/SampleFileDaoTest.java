package ca.on.oicr.pinery.lims.flatfile.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.AttributeName;
import ca.on.oicr.pinery.api.Sample;
import ca.on.oicr.pinery.api.SampleProject;
import ca.on.oicr.pinery.api.Type;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/test-context.xml")
public class SampleFileDaoTest {
  
  @Autowired
  private SampleFileDao dao;
  
  @Test
  public void testGetSingleSampleAndMapping() {
    Sample sample = dao.getSample("1");
    Assert.assertNotNull(sample);

    Assert.assertEquals("1", sample.getId());
    Assert.assertEquals("Sample1", sample.getName());
    Assert.assertEquals("First", sample.getDescription());
    Assert.assertEquals("12345", sample.getTubeBarcode());
    Assert.assertEquals("freezer", sample.getStorageLocation());
    Assert.assertEquals("Identity", sample.getSampleType());
    Assert.assertEquals(ModelUtils.convertToDate("2012-06-01T16:53:19-04:00"), sample.getCreated());
    Assert.assertEquals(Integer.valueOf(2), sample.getCreatedById());
    Assert.assertEquals(ModelUtils.convertToDate("2012-06-01T16:56:20-04:00"), sample.getModified());
    Assert.assertEquals(Integer.valueOf(1), sample.getModifiedById());
    Assert.assertEquals("TestProject", sample.getProject());
    Assert.assertEquals(Boolean.valueOf(true), sample.getArchived());
    Assert.assertEquals("Ready", sample.getStatus().getName());
    Assert.assertEquals("Ready", sample.getStatus().getState());
    
    Set<Attribute> atts = sample.getAttributes();
    Assert.assertEquals(1, atts.size());
    for (Attribute att : atts) {
      Assert.assertEquals("Organism", att.getName());
      Assert.assertEquals("Homo sapiens", att.getValue());
    }
  }
  
  @Test
  public void testGetAllSamples() {
    List<Sample> samples = dao.getAllSamples();
    Assert.assertEquals(6, samples.size());
  }
  
  @Test
  public void testGetSamplesNoFilter() {
    List<Sample> samples = dao.getSamplesFiltered(null, null, null, null, null);
    Assert.assertEquals(6, samples.size());
  }
  
  @Test
  public void testGetSamplesDateFilters() {
    DateTime before = DateTime.parse("2012-10-01T17:53:19-04:00", ModelUtils.dateTimeFormatter);
    DateTime after = DateTime.parse("2012-07-01T15:53:19-04:00", ModelUtils.dateTimeFormatter);
    List<Sample> samples = dao.getSamplesFiltered(null, null, null, before, after);
    Assert.assertEquals(4, samples.size());
  }
  
  @Test
  public void testGetSamplesProjectAndArchiveFilter() {
    Set<String> projects = new HashSet<>();
    projects.add("TestProject");
    List<Sample> samples = dao.getSamplesFiltered(false, projects, null, null, null);
    Assert.assertEquals(2, samples.size());
  }
  
  @Test
  public void testGetSamplesTypeFilter() {
    Set<String> types = new HashSet<>();
    types.add("Illumina PE Library");
    List<Sample> samples = dao.getSamplesFiltered(null, null, types, null, null);
    Assert.assertEquals(5, samples.size());
  }
  
  @Test
  public void testGetAllSampleProjects() {
    List<SampleProject> projects = dao.getAllSampleProjects();
    Assert.assertEquals(2, projects.size());
  }
  
  @Test
  public void testGetAllSampleTypes() {
    List<Type> types = dao.getAllSampleTypes();
    Assert.assertEquals(2, types.size());
  }
  
  @Test
  public void testGetAllSampleAttributes() {
    List<AttributeName> atts = dao.getAllSampleAttributes();
    Assert.assertEquals(7, atts.size());
  }
  
}
