package ca.on.oicr.pinery.lims.miso.converters;

import static org.junit.Assert.*;

import org.junit.Test;

public class NonSampleTypeConverterTest {

  @Test
  public void testGetSampleType() throws Exception {
    // No library design code
    assertEquals("Illumina PE Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", "Paired End", null));
    assertEquals("Illumina PE Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "Illumina", "Paired End", null));
    
    // matching library type and design code
    assertEquals("Illumina WT Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", "Whole Transcriptome", "WT"));
    assertEquals("Illumina WT Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "Illumina", "Whole Transcriptome", "WT"));
    
    
    // library type vs. design code conflict - side with design code
    assertEquals("Illumina WT Library", NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", "Paired End", "WT"));
    assertEquals("Illumina WT Library Seq", NonSampleTypeConverter.getNonSampleSampleType("Dilution", "Illumina", "Paired End", "WT"));
  }

  @Test
  public void testGetSampleTypeUnknowns() throws Exception {
    final String unknown = "Unknown";
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("X", "Illumina", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "X", "mRNA Seq", "MR"));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", null, "mRNA Seq", "MR"));
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", null, "MR"));
    assertNotEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", "mRNA Seq", null));
    assertEquals(unknown, NonSampleTypeConverter.getNonSampleSampleType("Library", "Illumina", null, null));
  }
  
}
