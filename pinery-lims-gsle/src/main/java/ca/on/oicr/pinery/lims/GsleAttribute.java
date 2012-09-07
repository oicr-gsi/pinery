package ca.on.oicr.pinery.lims;

public class GsleAttribute extends DefaultAttribute {

	public void setIdString(String idString) {
		if (idString != null) {
			setId(Integer.parseInt(idString));
		}
	}
}
