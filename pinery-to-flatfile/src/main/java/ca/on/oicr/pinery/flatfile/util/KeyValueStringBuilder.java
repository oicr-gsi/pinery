package ca.on.oicr.pinery.flatfile.util;

/**
 * A StringBuilder wrapper class for creating a String representation of a list of key:value pairs in format "{key1=val1;key2=val2...}"
 */
public class KeyValueStringBuilder {
  
  private final StringBuilder sb = new StringBuilder();
  private boolean hasFirstPair = false;
  
  public KeyValueStringBuilder() {
    // Default constructor
  }
  
  /**
   * Adds a key:value pair to the representation
   * 
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, String value) {
    if (hasFirstPair) {
      sb.append(';');
    }
    sb.append(key).append('=').append(value);
    hasFirstPair = true;
    return this;
  }
  
  /**
   * Adds a key:value pair to the representation
   * 
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, int value) {
    return append(key, "" + value);
  }

  @Override
  public String toString() {
    return '{' + sb.toString() + '}';
  }
  
}
