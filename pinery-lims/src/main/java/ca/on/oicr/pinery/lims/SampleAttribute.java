package ca.on.oicr.pinery.lims;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public enum SampleAttribute {
    TISSUE_TYPE("geo_tissue_type", "Tissue Type"),
    TISSUE_ORIGIN("geo_tissue_origin", "Tissue Origin"),
    ORGANISM("geo_organism", "Organism"),
    LIBRARY_SOURCE("geo_library_source_template_type", "Source Template Type"),
    LIBRARY_SIZE("geo_library_size", "Library Size"),
    SAMPLE_ID("geo_template_id"),
    SAMPLE_TYPE("geo_template_type", "Template Type", "template type"),
    RUN_ID_AND_POSITION("geo_run_id_and_position"),
    SHORT_TANDEM_REPEAT_RESULT("geo_str_result", "STR"),
    GROUP_ID("geo_group_id", "Group ID"),
    GROUP_ID_DESCRIPTION("geo_group_id_description", "Group Description"),
    PREPARATION_KIT("geo_prep_kit", "Preparation Kit"),
    TISSUE_PREPARATION("geo_tissue_preparation", "Tissue Preparation"),
    QUBIT_CONCENTRATION("geo_qubit_concentration", "Qubit (ng/uL)"),
    TISSUE_REGION("geo_tissue_region", "Region"),
    NANODROP_CONCENTRATION("geo_nanodrop_concentration", "Nanodrop (ng/uL)"),
    SAMPLE_RECEIVE_DATE("geo_receive_date", "Receive Date"),
    EXTERNAL_NAME("geo_external_name", "External Name"),
    TUBE_ID("geo_tube_id", "Tube ID"),
    TARGETED_RESEQUENCING("geo_targeted_resequencing", "Targeted Resequencing"),
    PURPOSE("geo_purpose", "Purpose"),
    QPCR_PERCENTAGE_HUMAN("geo_qpcr_percentage_human", "qPCR %"),
    SUBPROJECT("subproject", "Subproject"),
    INSTITUTE("institute", "Institute");

    private final Set<String> in;
    private final String out;

    static {
        validate();
    }

    private SampleAttribute(String out, String... in) {
        this.in = new HashSet<>(Arrays.asList(in));
        this.out = out;
    }

    public static SampleAttribute fromString(String text) {
        if (text != null) {
            for (SampleAttribute sa : SampleAttribute.values()) {
                if (sa.in.contains(text)) {
                    return sa;
                }
                if (sa.out.contains(text)) {
                    return sa;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return out;
    }

    public String asString(boolean useEnumToString) {
        if (useEnumToString) {
            return toString();
        } else {
            return name();
        }
    }

    private static void validate() {
        Set<String> ins = new HashSet<>();
        Set<String> outs = new HashSet<>();
        for (SampleAttribute sa : SampleAttribute.values()) {
            if (Sets.intersection(ins, sa.in).isEmpty()) {
                ins.addAll(sa.in);
            } else {
                throw new RuntimeException("Duplicate: [" + sa.in.toString() + "]");
            }
            if (!ins.contains(sa.out)) {
                ins.add(sa.out);
            } else {
                throw new RuntimeException("Duplicate: [" + sa.out.toString() + "]");
            }
            if (!outs.contains(sa.out)) {
                outs.add(sa.out);
            } else {
                throw new RuntimeException("Duplicate: [" + sa.out + "]");
            }
        }
    }

}
