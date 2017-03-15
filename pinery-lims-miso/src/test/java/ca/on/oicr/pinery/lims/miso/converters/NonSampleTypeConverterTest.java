package ca.on.oicr.pinery.lims.miso.converters;

import static org.junit.Assert.*;

import org.junit.Test;

public class NonSampleTypeConverterTest {
  
  @Test
  public void testGetSampleType() throws Exception {
    // No library design code
    assertEquals("Illumina PE Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "Paired End"));
    assertEquals("Illumina PE Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "ILLUMINA", "Paired End"));
    
    // matching library type and design code
    assertEquals("Illumina WT Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "Whole Transcriptome"));
    assertEquals("Illumina WT Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "ILLUMINA", "Whole Transcriptome"));
  }

  @Test
  public void testGetSampleTypeUnknowns() throws Exception {
    final String unknown = "Unknown";
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "mRNA Seq"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("X", "ILLUMINA", "mRNA Seq"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "X", "mRNA Seq"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", null, "mRNA Seq"));
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "mRNA Seq"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", null));
  }
  
}
