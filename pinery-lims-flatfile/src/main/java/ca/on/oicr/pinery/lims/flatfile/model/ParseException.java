package ca.on.oicr.pinery.lims.flatfile.model;

public class ParseException extends RuntimeException {

  private static final long serialVersionUID = -2252611185888186476L;
  
  private String badString;
  private int errorPosition;
  
  public ParseException() {
    super();
  }

  public ParseException(String message) {
    super(message);
  }

  public void setErrorData(String badString, int errorPosition) {
    this.badString = badString;
    this.errorPosition = errorPosition;
  }
  
  public String getBadString() {
    return badString;
  }

  public int getErrorPosition() {
    return errorPosition;
  }

  public static ParseException fromErrorData(String badString, int errorPosition, String detailMessage) {
    String message = makeMessage(badString, errorPosition, detailMessage);
    ParseException exception = new ParseException(message);
    exception.setErrorData(badString, errorPosition);
    return exception;
  }
  
  public static ParseException fromErrorData(String badString, int errorPosition) {
   return fromErrorData(badString, errorPosition, "Error parsing String"); 
  }
  
  private static String makeMessage(String badString, int errorPosition, String detailMessage) {
    if (badString == null) return null;
    if (badString.length() == 0) return detailMessage;
    if (errorPosition < 0 || errorPosition > badString.length()-1) return detailMessage;
    
    int startPos = errorPosition < 25 ? 0 : errorPosition - 25;
    int endPos = errorPosition > badString.length() - 25 ? badString.length() : errorPosition + 25;
    int substringErrorPos = errorPosition - startPos + 1;
    String message = detailMessage
        + ": "
        + badString.substring(startPos, substringErrorPos)
        + "[*]"
        + badString.substring(substringErrorPos, endPos);
    return message;
  }

}
