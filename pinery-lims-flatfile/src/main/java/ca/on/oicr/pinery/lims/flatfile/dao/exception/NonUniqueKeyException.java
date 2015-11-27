package ca.on.oicr.pinery.lims.flatfile.dao.exception;

public class NonUniqueKeyException extends RuntimeException {

  private static final long serialVersionUID = -8265487552301357840L;

  public NonUniqueKeyException() {
    super();
  }

  public NonUniqueKeyException(String message, Throwable cause,
      boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public NonUniqueKeyException(String message, Throwable cause) {
    super(message, cause);
  }

  public NonUniqueKeyException(String message) {
    super(message);
  }

  public NonUniqueKeyException(Throwable cause) {
    super(cause);
  }

}
