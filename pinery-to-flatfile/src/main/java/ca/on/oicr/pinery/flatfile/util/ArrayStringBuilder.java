package ca.on.oicr.pinery.flatfile.util;

/**
 * A StringBuilder wrapper class for creating a String representation of a list of items in format
 * "[item1,item2,item3...]"
 */
public class ArrayStringBuilder {

  private final StringBuilder sb = new StringBuilder();
  private boolean hasFirstItem = false;

  public ArrayStringBuilder() {
    // Default constructor
  }

  /**
   * Add an item to the representation
   *
   * @param item the item to add
   * @return this same ArrayStringBuilder, to allow chaining
   */
  public ArrayStringBuilder append(String item) {
    if (hasFirstItem) {
      sb.append(',');
    }
    sb.append(item);
    hasFirstItem = true;
    return this;
  }

  /**
   * Add an item to the representation
   *
   * @param item the item to add
   * @return this same ArrayStringBuilder, to allow chaining
   */
  public ArrayStringBuilder append(int item) {
    return append("" + item);
  }

  @Override
  public String toString() {
    return '[' + sb.toString() + ']';
  }
}
