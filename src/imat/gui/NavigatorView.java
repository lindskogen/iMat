package imat.gui;

import imat.backend.CategoryNode;
import imat.backend.Utils;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import se.chalmers.ait.dat215.project.ProductCategory;

public class NavigatorView extends JPanel {
	private JTree tree;
	private ProductsView view;
	private JLabel searchLabel;
	private JTextField searchField;

	/**
	 * Create the panel.
	 */
	public NavigatorView() {
		view = ProductsView.getSharedInstance();
		setPreferredSize(new Dimension(230, 580));
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for (Object p : Utils.getMainCategories()) {
			if (p instanceof ProductCategory) {
				root.add(new CategoryNode((ProductCategory) p));
			} else {
				if (((String) p).equals("Drycker")) {
					CategoryNode tmp = new CategoryNode((String) p);
					for (ProductCategory i : Utils.getDrinkCategories()) {
						tmp.add(new CategoryNode(i));
					}
					root.add(tmp);
				} else {
					CategoryNode tmp = new CategoryNode((String) p);
					for (ProductCategory i : Utils.getFruitCategories()) {
						tmp.add(new CategoryNode(i));
					}
					root.add(tmp);
				}
			}
		}

		tree = new JTree(root);
		tree.addMouseListener(new TreeMouseListener());
		tree.addTreeExpansionListener(new TreeTreeExpansionListener());
		tree.setFont(new Font("SansSerif", Font.BOLD, 12));
		tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		tree.setRootVisible(false);
		tree.setToggleClickCount(1);
		tree.putClientProperty("JTree.lineStyle", "None");
		DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) tree
				.getCellRenderer();
		tcr.setLeafIcon(null);
		tcr.setOpenIcon(null);
		tcr.setClosedIcon(null);

		searchLabel = new JLabel("Hitta mat");

		searchField = new JTextField();
		searchField.setFont(new Font("SansSerif", Font.ITALIC, 12));
		searchField.setColumns(10);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING)
										.addComponent(tree,
												GroupLayout.DEFAULT_SIZE, 206,
												Short.MAX_VALUE)
										.addComponent(searchLabel)
										.addComponent(searchField,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(searchLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 510,
								Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout);

	}

	private class TreeTreeExpansionListener implements TreeExpansionListener {
		public void treeCollapsed(TreeExpansionEvent arg0) {
		}

		public void treeExpanded(TreeExpansionEvent arg0) {
			TreePath path = (TreePath) arg0.getPath();
			for (int i = 0; i < tree.getRowCount(); i++) {
				if (tree.getPathForRow(i) != path) {
					tree.collapseRow(i);
				}

			}
		}
	}

	private class TreeMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			TreePath path = tree.getPathForLocation(e.getX(), e.getY());
			if (path != null) {
				CategoryNode node = (CategoryNode) tree
						.getLastSelectedPathComponent();
				if (node.getChildCount() == 0) {
					if (path.getPathCount() == 2) {
						for (int i = 0; i < tree.getRowCount(); i++) {
							tree.collapseRow(i);
						}
					}

					view.dispCategory(node.getProductCategory());
				}
			}
		}
	}
}
