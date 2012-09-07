package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.PreparationKit;

public class DefaultPreparationKit implements PreparationKit {

	private String name;
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
