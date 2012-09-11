package ca.on.oicr.pinery.lims;

public class GsleSampleParents {

	private Integer parent;
	private Integer template;

	public Integer getParent() {
		return parent;
	}

	public void setParentString(String parent) {
		if (parent != null && !parent.equals("")) {
			this.parent = Integer.parseInt(parent);
		}
	}

	public Integer getTemplate() {
		return template;
	}

	public void setTemplateString(String template) {
		if (template != null && !template.equals("")) {
			this.template = Integer.parseInt(template);
		}
	}
}
