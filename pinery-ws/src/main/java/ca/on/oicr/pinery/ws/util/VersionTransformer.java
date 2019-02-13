package ca.on.oicr.pinery.ws.util;

public interface VersionTransformer<T, U extends T> {

  public U transform(T original);

}
