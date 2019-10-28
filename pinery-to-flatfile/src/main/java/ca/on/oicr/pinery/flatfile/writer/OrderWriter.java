package ca.on.oicr.pinery.flatfile.writer;

import java.util.List;

import ca.on.oicr.pinery.flatfile.util.ArrayStringBuilder;
import ca.on.oicr.pinery.flatfile.util.KeyValueStringBuilder;
import ca.on.oicr.ws.dto.AttributeDto;
import ca.on.oicr.ws.dto.OrderDto;
import ca.on.oicr.ws.dto.OrderDtoSample;

public class OrderWriter extends Writer {
  
  private final String[] headers = {
      "id",
      "projectName",
      "status",
      "platformName",
      "createdDate",
      "createdUserId",
      "modifiedDate",
      "modifiedUserId",
      "samples"
  };
  
  private final List<OrderDto> orders;
  
  public OrderWriter(List<OrderDto> orders) {
    this.orders = orders;
  }
  
  @Override
  protected String[] getHeaders() {
    return headers;
  }

  @Override
  protected int getRecordCount() {
    return orders.size();
  }

  @Override
  protected String[] getRecord(int row) {
    OrderDto order = orders.get(row);
    
    String[] data = {
        order.getId().toString(),
        order.getProject(),
        order.getStatus(),
        order.getPlatform(),
        order.getCreatedDate(),
        order.getCreatedById() == null ? "" : order.getCreatedById().toString(),
        order.getModifiedDate(),
        order.getModifiedById() == null ? "" : order.getModifiedById().toString(),
        getSamplesString(order)
    };
    
    return data;
  }
  
  private static String getSamplesString(OrderDto order) {
    ArrayStringBuilder sb = new ArrayStringBuilder();
    if (order.getSamples() != null) {
      for (OrderDtoSample sample : order.getSamples()) {
        sb.append(getOrderSampleString(sample));
      }
    }
    return sb.toString();
  }
  
  private static String getOrderSampleString(OrderDtoSample sample) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    
    sb.append("id", sample.getId());
    if (sample.getBarcode() != null) sb.append("barcode", sample.getBarcode());
    if (sample.getBarcodeTwo() != null) sb.append("barcodeTwo", sample.getBarcodeTwo());
    sb.append("attributes", getSampleAttributesString(sample));
    
    return sb.toString();
  }
  
  private static KeyValueStringBuilder getSampleAttributesString(OrderDtoSample sample) {
    KeyValueStringBuilder sb = new KeyValueStringBuilder();
    for (AttributeDto att : sample.getAttributes()) {
      sb.append(att.getName(), att.getValue());
    }
    return sb;
  }

}
