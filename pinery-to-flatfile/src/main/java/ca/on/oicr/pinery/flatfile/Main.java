package ca.on.oicr.pinery.flatfile;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import ca.on.oicr.pinery.client.HttpResponseException;
import ca.on.oicr.pinery.client.PineryClient;

public class Main {

  public static void main(String[] args) {
    if (args.length < 2 || args.length > 3) {
      showUsage();
      return;
    }
    
    PineryClient client = new PineryClient(args[0]);
    File outputDir = new File(args[1]);
    String prefix = "";
    if (args.length > 2) {
      prefix = args[2] + '_';
    }
    
    Converter converter = new Converter(client, outputDir);
    
    try {
      System.out.println("Writing instruments file...");
      converter.convertInstruments(prefix + "instruments.tsv");
      
      System.out.println("Writing samples file...");
      converter.convertSamples(prefix + "samples.tsv");
      
      System.out.println("Writing changes file...");
      converter.convertChangeLogs(prefix + "changes.tsv");
      
      System.out.println("Writing users file...");
      converter.convertUsers(prefix + "users.tsv");
      
      System.out.println("Writing sequencer runs file...");
      converter.convertRuns(prefix + "runs.tsv");
      
      System.out.println("Writing orders file...");
      converter.convertOrders(prefix + "orders.tsv");
      
      System.out.println("Finished writing all files.");
      
    } catch (HttpResponseException e) {
      System.out.println("Error retrieving data from Pinery service at " + args[0]);
      e.printStackTrace();
    } catch (IOException e) {
      System.out.println("Error writing to file");
      e.printStackTrace();
    }
  }
  
  private static void showUsage() {
    System.out.println("");
    System.out.println("Usage: java -jar " + getJarName() + " {pinery-url} {output-dir} [export-prefix]");
    System.out.println("\tpinery-url: Required. Base URL of pinery service to export from");
    System.out.println("\toutput-dir: Required. Directory to save files to");
    System.out.println("\texport-prefix: Optional. Prefix for output filenames");
    System.out.println("");
  }
  
  private static String getJarName() {
    try {
      return new File(Main.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getName();
    } catch (URISyntaxException e) {
      return "this.jar";
    }
  }

}
