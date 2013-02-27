package imat.backend;

import javax.swing.tree.DefaultMutableTreeNode;

import se.chalmers.ait.dat215.project.ProductCategory;

public class CategoryNode extends DefaultMutableTreeNode {
	private ProductCategory category;
	public CategoryNode(ProductCategory p) {
		this(Utils.sweCategoryName(p));
		category = p;
	}
	public CategoryNode(String s) {
		super(s);
		
	}
	
	public ProductCategory getProductCategory(){
		return category;
	}
}