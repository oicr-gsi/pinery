package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Status;

public class DefaultStatus implements Status {

	private String name;
	private String state;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

}
