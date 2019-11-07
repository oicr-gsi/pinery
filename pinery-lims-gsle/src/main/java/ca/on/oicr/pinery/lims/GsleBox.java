package ca.on.oicr.pinery.lims;

public class GsleBox extends DefaultBox {

  public void setIdString(String id) {
    setId(Long.parseLong(id));
  }

  public void setContainerType(String containerType) {
    switch (containerType) {
      case "Matrix Box":
        setRows(8);
        setColumns(12);
        break;
      case "Storage Box":
        setRows(10);
        setColumns(10);
        break;
      default:
        throw new IllegalArgumentException(
            "Only accepted types are 'Matrix Box' and 'Storage Box'");
    }
  }
}
