package ca.on.oicr.pinery.flatfile.writer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.opencsv.CSVWriter;

public abstract class Writer {
  
  /**
   * @return the column headers to write as the first line of the file
   */
  protected abstract String[] getHeaders();
  
  /**
   * @return the total number of records that will be written
   */
  protected abstract int getRecordCount();
  
  /**
   * @param row the data row number being written. The implementation may order records in any way. The default 
   * {@link #writeFile(File, char)} method will simply call this method once for every row from 0 to {@link #getRecordCount()}
   * @return the data to write in this row
   */
  protected abstract String[] getRecord(int row);
  
  /**
   * Writes the resources to file
   * 
   * @param file output file
   * @param separator delimiter
   * @throws IOException if there is an error writing to file
   */
  public void writeFile(File file, char separator) throws IOException {
    try (CSVWriter writer = new CSVWriter(new FileWriter(file), separator, CSVWriter.NO_QUOTE_CHARACTER)) {
      writer.writeNext(getHeaders());
      
      for (int i = 0, count = getRecordCount(); i < count; i++) {
        writer.writeNext(getRecord(i));
      }
    }
  }
  
}
