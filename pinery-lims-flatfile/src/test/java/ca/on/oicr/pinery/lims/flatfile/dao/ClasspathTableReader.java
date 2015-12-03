package ca.on.oicr.pinery.lims.flatfile.dao;

import java.io.File;
import java.io.FilenameFilter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Vector;

import org.relique.io.TableReader;

public class ClasspathTableReader implements TableReader {

  private static final String filenameSuffix = ".tsv";
  private static final String directory = "/sampleData/";
  
  @Override
  public Reader getReader(Statement statement, String tableName) throws SQLException {
    String tableFile = directory + tableName + filenameSuffix;
    InputStream is = this.getClass().getResourceAsStream(tableFile);
    InputStreamReader reader = new InputStreamReader(is);
    return reader;
  }

  @Override
  public List<String> getTableNames(Connection connection) throws SQLException {
    Vector<String> v = new Vector<>();
    
    URL url = this.getClass().getResource(directory);
    File dir;
    
    try {
      dir = new File(url.toURI());
    } catch (URISyntaxException e) {
      throw new AssertionError("Unexpected failure", e);
    }
    
    FilenameFilter filter = new FilenameFilter() {
      @Override
      public boolean accept(File dir, String name) {
        return name.endsWith(filenameSuffix);
      }
    };
    String[] files = dir.list(filter);
    for (String file : files) {
      v.add(file.replaceFirst(filenameSuffix + "$", ""));
    }
    
    return v;
  }

}
