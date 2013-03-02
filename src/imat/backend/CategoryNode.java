package imat.backend;

import javax.swing.tree.DefaultMutableTreeNode;

import se.chalmers.ait.dat215.project.ProductCategory;

public class CategoryNode extends DefaultMutableTreeNode {
	private CustomCategories category;

	public CategoryNode(CustomCategories p) {
		this(p.getTitle());
		category = p;
	}

	public CategoryNode(String s) {
		super(s);

	}

	public CustomCategories getCustomCategory() {
		return category;
	}
}