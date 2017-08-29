package ca.on.oicr.pinery.lims;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mlaszloffy
 */
public enum LimsAttribute {
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
    SUBPROJECT("subproject", "Subproject", "Sub-project"),
    INSTITUTE("institute", "Institute"),
    POOL_NAME("pool_name", "Pool Name");

    private final Set<String> in;
    private final String out;

    static {
        validate();
    }

    private LimsAttribute(String out, String... in) {
        this.in = new HashSet<>(Arrays.asList(in));
        this.out = out;
    }

    public static LimsAttribute fromString(String text) {
        if (text != null) {
            for (LimsAttribute sa : LimsAttribute.values()) {
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
        for (LimsAttribute la : LimsAttribute.values()) {
            if (Sets.intersection(ins, la.in).isEmpty()) {
                ins.addAll(la.in);
            } else {
                throw new RuntimeException("Duplicate: [" + la.in.toString() + "]");
            }
            if (!ins.contains(la.out)) {
                ins.add(la.out);
            } else {
                throw new RuntimeException("Duplicate: [" + la.out.toString() + "]");
            }
            if (!outs.contains(la.out)) {
                outs.add(la.out);
            } else {
                throw new RuntimeException("Duplicate: [" + la.out + "]");
            }
        }
    }

}
