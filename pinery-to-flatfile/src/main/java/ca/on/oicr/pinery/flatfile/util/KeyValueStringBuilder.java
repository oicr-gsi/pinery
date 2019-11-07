package ca.on.oicr.pinery.flatfile.util;

/**
 * A StringBuilder wrapper class for creating a String representation of a list of key:value pairs
 * in format "{key1=val1;key2=val2...}"
 */
public class KeyValueStringBuilder {

  private final StringBuilder sb = new StringBuilder();
  private boolean hasFirstPair = false;

  public KeyValueStringBuilder() {
    // Default constructor
  }

  /**
   * Adds a key:value pair to the representation. If the value is a list or another key:value pair
   * set, the other append methods should be used instead; otherwise the values will be escaped
   * improperly, causing parsing issues
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, String value) {
    doAppend(key, escapeValue(value));
    return this;
  }

  public void doAppend(String key, String value) {
    if (hasFirstPair) {
      sb.append('|');
    }
    sb.append(key).append('=').append(value);
    hasFirstPair = true;
  }

  /**
   * Adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, ArrayStringBuilder valueBuilder) {
    doAppend(key, valueBuilder.toString());
    return this;
  }

  /**
   * Adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, KeyValueStringBuilder valueBuilder) {
    doAppend(key, valueBuilder.toString());
    return this;
  }

  private String escapeValue(String original) {
    return original
        .replace("{", "\\{")
        .replace("}", "\\}")
        .replace("[", "\\[")
        .replace("]", "\\]")
        .replace("|", "\\|")
        .replace("\\", "\\\\");
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

  /**
   * Adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder append(String key, boolean value) {
    return append(key, "" + value);
  }

  /**
   * Does nothing if value is null; otherwise adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder appendNonNull(String key, String value) {
    return value == null ? this : append(key, value);
  }

  /**
   * Does nothing if value is null; otherwise adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder appendNonNull(String key, Integer value) {
    return value == null ? this : append(key, value);
  }

  /**
   * Does nothing if value is null; otherwise adds a key:value pair to the representation
   *
   * @param key
   * @param value
   * @return this same KeyValueStringBuilder, to allow chaining
   */
  public KeyValueStringBuilder appendNonNull(String key, Boolean value) {
    return value == null ? this : append(key, value);
  }

  @Override
  public String toString() {
    return '{' + sb.toString() + '}';
  }
}
