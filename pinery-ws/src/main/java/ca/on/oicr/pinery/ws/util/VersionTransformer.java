package ca.on.oicr.pinery.ws.util;

public interface VersionTransformer<T> {

  public T transform(T original);

}
