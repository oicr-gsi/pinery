package ca.on.oicr.pinery.flatfile;

import java.io.File;
import java.io.IOException;

import ca.on.oicr.pinery.client.HttpResponseException;
import ca.on.oicr.pinery.client.PineryClient;
import ca.on.oicr.pinery.flatfile.writer.BoxWriter;
import ca.on.oicr.pinery.flatfile.writer.ChangeWriter;
import ca.on.oicr.pinery.flatfile.writer.InstrumentWriter;
import ca.on.oicr.pinery.flatfile.writer.OrderWriter;
import ca.on.oicr.pinery.flatfile.writer.SampleWriter;
import ca.on.oicr.pinery.flatfile.writer.SequencerRunWriter;
import ca.on.oicr.pinery.flatfile.writer.UserWriter;
import ca.on.oicr.pinery.flatfile.writer.Writer;

/**
 * This class handles the interaction between Pinery and the flatfile Writers
 */
public class Converter {

  private final PineryClient client;
  private final File outputDir;
  private char separator = '\t';
  
  /**
   * Creates a new Converter instance
   * 
   * @param client the Pinery client to use for retrieving input data
   * @param outputDir the directory to write output files to
   */
  public Converter(PineryClient client, File outputDir) {
    this.client = client;
    this.outputDir = outputDir;
  }
  
  /**
   * Creates a new Converter instance
   * 
   * @param client the Pinery client to use for retrieving input data
   * @param outputDir the directory to write output files to
   * @param separator delimiter to use in output files
   */
  public Converter(PineryClient client, File outputDir, char separator) {
    this(client, outputDir);
    this.separator = separator;
  }

  /**
   * Reads all instrument and instrument model data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertInstruments(String filename) throws HttpResponseException, IOException {
    Writer writer = new InstrumentWriter(client.getInstrument().all(), client.getInstrumentModel().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all sample data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertSamples(String filename) throws HttpResponseException, IOException {
    Writer writer = new SampleWriter(client.getSample().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all sample change log data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertChangeLogs(String filename) throws HttpResponseException, IOException {
    Writer writer = new ChangeWriter(client.getChangeLog().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all user data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertUsers(String filename) throws HttpResponseException, IOException {
    Writer writer = new UserWriter(client.getUser().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all sequencer run data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertRuns(String filename) throws HttpResponseException, IOException {
    Writer writer = new SequencerRunWriter(client.getSequencerRun().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all order data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertOrders(String filename) throws HttpResponseException, IOException {
    Writer writer = new OrderWriter(client.getOrder().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
  /**
   * Reads all box data from Pinery and writes it to a flat file
   * 
   * @param filename output file name
   * @throws HttpResponseException if there is an error communicating with Pinery
   * @throws IOException if there is an error writing to file
   */
  public void convertBoxes(String filename) throws HttpResponseException, IOException {
    Writer writer = new BoxWriter(client.getBox().all());
    writer.writeFile(new File(outputDir, filename), separator);
  }
  
}
