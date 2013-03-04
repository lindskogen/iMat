package imat.gui;

import imat.backend.CategoryNode;
import imat.backend.CustomCategories;
import imat.backend.CustomProductLists;
import imat.backend.ShopModel;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

public class NavigatorView extends JPanel implements ActionListener, PropertyChangeListener {
	private JTree tree;
	private static ProductsView view;
	private JLabel searchLabel;
	private JTextField searchField;
	private static CustomCategories currentCategory;

	private ShopModel model;
	
	/**
	 * Create the panel.
	 */
	public NavigatorView(ShopModel model) {
		this.model = model;
		this.model.addPropertyChangeListeter(this);
		
		CustomProductLists.generateCustomLists();
		setPreferredSize(new Dimension(250, 600));
		view = new ProductsView(this.model);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
		for (CustomCategories c : CustomCategories.values()) {
			if (c.isMain()) {
				CategoryNode tmp = new CategoryNode(c);
				if (c.isParent()) {
					for (CustomCategories t : CustomCategories
							.getSubCategories(c)) {
						tmp.add(new CategoryNode(t));
					}
				}
				root.add(tmp);
			}

		}

		tree = new JTree(root);
		tree.setModel(new DefaultTreeModel(root));
		tree.setPreferredSize(new Dimension(247, 20));
		tree.setMaximumSize(new Dimension(1000, 1000));
		tree.addMouseMotionListener(new TreeMouseMotionListener());
		tree.setBackground(UIManager.getColor("Panel.background"));
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
		tcr.setBackgroundNonSelectionColor(this.getBackground());
		tcr.setFont(new Font("SansSerif", Font.BOLD, 16));
		tcr.setPreferredSize(new Dimension(1000, 20));

		searchLabel = new JLabel("Hitta mat");

		searchField = new JTextField();
		searchField.setFont(new Font("SansSerif", Font.ITALIC, 12));
		searchField.setColumns(10);
		searchField.setActionCommand("search");
		searchField.addActionListener(this);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																tree,
																GroupLayout.DEFAULT_SIZE,
																290,
																Short.MAX_VALUE)
														.addGroup(
																groupLayout
																		.createParallelGroup(
																				Alignment.LEADING,
																				false)
																		.addComponent(
																				searchField,
																				GroupLayout.DEFAULT_SIZE,
																				221,
																				Short.MAX_VALUE)
																		.addComponent(
																				searchLabel)))
										.addContainerGap()));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(
				Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(searchLabel)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(searchField, GroupLayout.PREFERRED_SIZE,
								30, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tree, GroupLayout.DEFAULT_SIZE, 519,
								Short.MAX_VALUE).addContainerGap()));
		setLayout(groupLayout);
		DefaultTreeModel tmp = (DefaultTreeModel) tree.getModel();
		tmp.reload();

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
					currentCategory = node.getCustomCategory();
					if (currentCategory.getProducts() != null) {
						view.setProducts(currentCategory.getProducts());
					}

				}
			}

		}
	}

	private class TreeMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			TreePath path = (TreePath) tree.getPathForLocation(e.getX(),
					e.getY());
			if (path != null) {
				tree.setSelectionPath(path);
			}
		}
	}

	public ProductsView getProductsView() {
		return view;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("search")) {
			Object o = e.getSource();
			String searchString = "";
			if (o instanceof JTextField) {
				searchString = ((JTextField) o).getText();
			} else if (o instanceof SuggestionLabel) {
				searchString = ((SuggestionLabel) o).getText();
			}
			List<Product> res = IMatDataHandler.getInstance().findProducts(searchString);
			if (res.size() == 0) {
				model.fuzzySearch(searchString);
			} else {
				getProductsView().setProducts(res, searchString);
			}
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("fuzzySearch")) {
			Object oList = evt.getNewValue();
			if (oList instanceof List<?>) {
				List<SuggestionLabel> suggestions= new ArrayList<SuggestionLabel>();
				for (String s : (List<String>) oList) {
					suggestions.add(new SuggestionLabel(s, this));
				}
				// TODO: Show notification panel!
				// notify(suggestions);
			}
		}
	}
}
