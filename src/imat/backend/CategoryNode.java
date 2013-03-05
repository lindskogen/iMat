package imat.backend;

import javax.swing.tree.DefaultMutableTreeNode;

public class CategoryNode extends DefaultMutableTreeNode {
	private CustomCategory category;

	public CategoryNode(CustomCategory p) {
		this(p.getTitle());
		category = p;
	}

	public CategoryNode(String s) {
		super(s);

	}

	public CustomCategory getCustomCategory() {
		return category;
	}
}