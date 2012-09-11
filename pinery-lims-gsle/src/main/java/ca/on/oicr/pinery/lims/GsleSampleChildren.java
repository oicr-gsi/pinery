package ca.on.oicr.pinery.lims;

public class GsleSampleChildren {

	private Integer parent;
	private Integer child;

	public Integer getParent() {
		return parent;
	}

	public void setParentString(String parent) {
		if (parent != null && !parent.equals("")) {
			this.parent = Integer.parseInt(parent);
		}
	}

	public Integer getChild() {
		return child;
	}

	public void setChildString(String child) {
		if (child != null && !child.equals("")) {
			this.child = Integer.parseInt(child);
		}
	}
}
