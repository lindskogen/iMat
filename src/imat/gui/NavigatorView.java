package imat.gui;

import imat.backend.CategoryNode;
import imat.backend.CustomCategories;
import imat.backend.CustomProductLists;
import imat.backend.ShopModel;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTree;
import org.jdesktop.swingx.decorator.ColorHighlighter;
import org.jdesktop.swingx.decorator.HighlightPredicate;
import javax.swing.ImageIcon;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;

public class NavigatorView extends JPanel implements ActionListener, PropertyChangeListener, KeyListener {


	private JXTree tree;
	private static ProductsView view;
	private JLabel searchLabel;
	private JTextField searchField;
	private static CustomCategories currentCategory;
	private JLabel favouriteLabel;

	private ShopModel model;
	private final String AC_SEARCH = "search";
	private JSeparator separator;
	private JButton btnInstllningar;
	private IMatDataHandler imat;
	
	/**
	 * Create the panel.
	 */
	public NavigatorView(ShopModel model) {
		imat = IMatDataHandler.getInstance();
		this.model = model;
		this.model.addPropertyChangeListener(this);

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

		tree = new JXTree(root);
		tree.setSelectionForeground(Color.BLACK);
		tree.setOpaque(false);
		tree.setRolloverEnabled(true);
		tree.setModel(new DefaultTreeModel(root));
		tree.setPreferredSize(new Dimension(247, 20));
		tree.setMaximumSize(new Dimension(1000, 1000));
		tree.addMouseMotionListener(new TreeMouseMotionListener());
		tree.addMouseListener(new TreeMouseListener());
		tree.addTreeExpansionListener(new TreeTreeExpansionListener());
		tree.setFont(new Font("SansSerif", Font.BOLD, 18));
		tree.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		tree.setRootVisible(false);
		tree.setToggleClickCount(1);
		tree.putClientProperty("JTree.lineStyle", "None");
		tree.addHighlighter(new ColorHighlighter(
				HighlightPredicate.ROLLOVER_ROW, null, new Color(106, 90, 205)));
		tree.setCellRenderer(new DefaultTreeCellRenderer());
		DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) tree
				.getWrappedCellRenderer();
		tcr.setLeafIcon(null);
		tcr.setOpenIcon(null);
		tcr.setClosedIcon(null);
		tcr.setBackgroundNonSelectionColor(UIManager
				.getColor("Panel.background"));

		searchLabel = new JLabel("Hitta mat");
		searchLabel.setFont(new Font("SansSerif", Font.BOLD, 22));

		searchField = new JTextField();
		searchField.setFont(new Font("SansSerif", Font.ITALIC, 12));
		searchField.setColumns(10);
		searchField.setActionCommand(AC_SEARCH);
		searchField.addActionListener(this);
		searchField.addKeyListener(this);

		favouriteLabel = new JLabel("Favoriter");
		favouriteLabel
				.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		favouriteLabel.setOpaque(true);
		favouriteLabel.addMouseListener(new FavouriteLabelMouseListener());
		favouriteLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

		separator = new JSeparator();

		btnInstllningar = new JButton("");
		btnInstllningar.setPreferredSize(new Dimension(32, 32));
		btnInstllningar.setIcon(new ImageIcon(NavigatorView.class.getResource("/imat/resources/settingsIcon.png")));

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
							.addComponent(searchField, GroupLayout.DEFAULT_SIZE, 221, Short.MAX_VALUE)
							.addComponent(searchLabel))
						.addComponent(favouriteLabel)
						.addComponent(tree, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addComponent(separator, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
						.addComponent(btnInstllningar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(searchLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(searchField, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(favouriteLabel)
					.addGap(18)
					.addComponent(tree, GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
					.addGap(8)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnInstllningar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
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
			favouriteLabel.setBackground(null);
			favouriteLabel.setBorder(null);
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
			// TreePath path = (TreePath) tree.getPathForLocation(e.getX(),
			// e.getY());
			// if (path != null) {
			// tree.setSelectionPath(path);
			// }
		}
	}

	private class FavouriteLabelMouseListener extends MouseAdapter {

		@Override
		public void mouseEntered(MouseEvent e) {

			favouriteLabel.setForeground(new Color(106, 90, 205));

		}

		@Override
		public void mouseExited(MouseEvent e) {
			favouriteLabel.setForeground(Color.black);
		}

		@Override
		public void mousePressed(MouseEvent e) {
			tree.setSelectionPath(null);
			currentCategory = CustomCategories.FAVORITES;
			view.setProducts(IMatDataHandler.getInstance().favorites());
			DefaultTreeCellRenderer tcr = (DefaultTreeCellRenderer) tree
					.getWrappedCellRenderer();
			favouriteLabel.setBackground(tcr.getBackgroundSelectionColor());
			favouriteLabel.setForeground(Color.black);
			favouriteLabel.setBorder(BorderFactory.createLineBorder(tcr
					.getBorderSelectionColor()));
		}
	}

	public ProductsView getProductsView() {
		return view;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		long oldValue = System.currentTimeMillis();
		if (e.getActionCommand().equals(AC_SEARCH)) {
			model.closeNotification();
			Object o = e.getSource();
			String searchString = "";
			if (o instanceof JTextField) {
				searchString = ((JTextField) o).getText();
			} else if (o instanceof SuggestionLabel) {
				searchString = ((SuggestionLabel) o).getText();
				searchField.setText(searchString);
			}
			List<Product> res;
			if (searchString.length() > 1) {
				res = imat.findProducts(searchString);
				if (res.size() == 0) {
					for (String s: model.fuzzySearch(searchString)) {
						res.addAll(imat.findProducts(s));
					}
				}
				getProductsView().setProducts(res, searchString);
			}
		}
		System.out.println(System.currentTimeMillis() - oldValue);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("fuzzySearch")) {
			Object oList = evt.getNewValue();
			if (oList instanceof List<?>) {
				JPanel panel = new JPanel();
				List<String> sList = (List<String>) oList;
				panel.add(new JLabel(((sList.size()>0)?"Menade du: ":"Hittade inga resultat.")));
				if (sList.size() > 0) {
					for (String s : sList) {
						panel.add(new SuggestionLabel(s, this));
					}
				}
				model.showNotification(panel);
			}
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	}

	@Override
	public void keyReleased(KeyEvent evt) {
		for (ActionListener a : searchField.getActionListeners()) {
			a.actionPerformed(new ActionEvent(searchField,
					ActionEvent.ACTION_PERFORMED, AC_SEARCH) {

			});
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}

	public static CustomCategories getCurrentCategory() {
		return currentCategory;
	}

	public static void setCurrentCategory(CustomCategories category) {
		currentCategory = category;
		
	}
}
