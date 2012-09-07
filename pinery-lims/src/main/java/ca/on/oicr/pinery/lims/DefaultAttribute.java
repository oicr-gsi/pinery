package ca.on.oicr.pinery.lims;

import ca.on.oicr.pinery.api.Attribute;

public class DefaultAttribute implements Attribute {

	private Integer id;
	private String name;
	private String value;

	@Override
	public Integer getId() {
		return id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

}
