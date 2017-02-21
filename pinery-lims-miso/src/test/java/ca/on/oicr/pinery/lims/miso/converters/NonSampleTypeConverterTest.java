package ca.on.oicr.pinery.lims.miso.converters;

import static org.junit.Assert.*;

import org.junit.Test;

public class NonSampleTypeConverterTest {
  
  @Test
  public void testGetSampleType() throws Exception {
    // No library design code
    assertEquals("Illumina PE Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "Paired End", null));
    assertEquals("Illumina PE Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "ILLUMINA", "Paired End", null));
    
    // matching library type and design code
    assertEquals("Illumina WT Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "Whole Transcriptome", "WT"));
    assertEquals("Illumina WT Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "ILLUMINA", "Whole Transcriptome", "WT"));
    
    
    // library type vs. design code conflict - side with design code
    assertEquals("Illumina WT Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "Paired End", "WT"));
    assertEquals("Illumina WT Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "ILLUMINA", "Paired End", "WT"));
  }

  @Test
  public void testGetSampleTypeUnknowns() throws Exception {
    final String unknown = "Unknown";
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("X", "ILLUMINA", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "X", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", null, "mRNA Seq", "MR"));
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", null, "MR"));
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", "mRNA Seq", null));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "ILLUMINA", null, null));
  }
  
}
