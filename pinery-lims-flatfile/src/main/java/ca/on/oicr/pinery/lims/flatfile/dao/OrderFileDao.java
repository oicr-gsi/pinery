package ca.on.oicr.pinery.lims.flatfile.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import ca.on.oicr.pinery.api.Attribute;
import ca.on.oicr.pinery.api.Order;
import ca.on.oicr.pinery.api.OrderSample;
import ca.on.oicr.pinery.lims.DefaultAttribute;
import ca.on.oicr.pinery.lims.DefaultOrder;
import ca.on.oicr.pinery.lims.DefaultOrderSample;
import ca.on.oicr.pinery.lims.flatfile.model.ModelUtils;

public class OrderFileDao implements OrderDao {

  private static final String queryAllOrders = "SELECT * FROM orders";
  
  private static final String queryOrderById = queryAllOrders + 
      " WHERE id LIKE ?";
  
  private static final RowMapper<Order> orderMapper = new RowMapper<Order>() {

    @Override
    public Order mapRow(ResultSet rs, int rowNum) throws SQLException {
      Order o = new DefaultOrder();
      
      o.setId(rs.getInt("id"));
      o.setProject(ModelUtils.nullIfEmpty(rs.getString("projectName")));
      o.setStatus(ModelUtils.nullIfEmpty(rs.getString("status")));
      o.setPlatform(ModelUtils.nullIfEmpty(rs.getString("platformName")));
      o.setCreatedDate(ModelUtils.convertToDate(rs.getString("createdDate")));
      o.setCreatedById(ModelUtils.nullIfZero(rs.getInt("createdUserId")));
      o.setModifiedDate(ModelUtils.convertToDate(rs.getString("modifiedDate")));
      o.setModifiedById(ModelUtils.nullIfZero(rs.getInt("createdUserId")));
      
      o.setSample(parseOrderSamples(rs.getString("samples")));
      
      return o;
    }
    
    private Set<OrderSample> parseOrderSamples(String samplesString) {
      List<String> sampleStrings = DaoUtils.parseList(samplesString);
      Set<OrderSample> samples = new HashSet<>();
      for (String sampleString : sampleStrings) {
        Map<String, String> sampleMap = DaoUtils.parseKeyValuePairs(sampleString);
        OrderSample sample = new DefaultOrderSample();
        
        sample.setId(sampleMap.get("id"));
        if (sampleMap.containsKey("barcode")) sample.setBarcode(ModelUtils.nullIfEmpty(sampleMap.get("barcode")));
        if (sampleMap.containsKey("barcodeTwo")) sample.setBarcodeTwo(ModelUtils.nullIfEmpty(sampleMap.get("barcodeTwo")));
        Map<String, String> attributeMap = DaoUtils.parseKeyValuePairs(sampleMap.get("attributes"));
        
        Set<Attribute> attributes = new HashSet<>();
        for (String key : attributeMap.keySet()) {
          Attribute att = new DefaultAttribute();
          att.setName(key);
          att.setValue(attributeMap.get(key));
          attributes.add(att);
        }
        sample.setAttributes(attributes);
        samples.add(sample);
      }
      return samples;
    }
    
  };
  
  @Autowired
  private JdbcTemplate template;
  
  @Override
  public List<Order> getAllOrders() {
    return template.query(queryAllOrders, orderMapper);
  }

  @Override
  public Order getOrder(Integer id) {
    List<Order> orders = template.query(queryOrderById, new Object[]{id}, orderMapper);
    return DaoUtils.getExpectedSingleResult(orders);
  }

}
