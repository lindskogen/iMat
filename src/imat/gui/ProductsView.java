package imat.gui;

import imat.backend.CustomCategory;
import imat.backend.ProductNameSort;
import imat.backend.ProductPriceSort;
import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collections;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.MatteBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductsView extends JPanel implements ActionListener {

	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	private JScrollPane scrollPane;
	private JPanel scrollPanel;
	private JPanel featuredPanel;
	private JPanel productsPanel;
	private JLabel lblDuHarTidigare;
	private JPanel featuredThumb;
	private JPanel buttonPanel;
	private JButton thumbViewButton;
	private JButton listViewButton;
	private JPanel centerPanel;
	private final String[] FILTER_CHOICES = new String[] { "Namn", "Pris" };

	private final String TIDIGARE = "Du har tidigare köpt: ";
	private final String RESULTAT = "Sökresultat för ";
	private final String LIST_VIEW = "wrap 1";
	private final String THUMB_VIEW = "wrap 2";
	private boolean listView;

	private ShopModel model;
	private JLabel sortLabel;
	private JComboBox filterComboBox;
	private JLabel label;

	/**
	 * Create the panel.
	 */
	public ProductsView(ShopModel model) {
		this.model = model;
		setBorder(new MatteBorder(1, 1, 1, 1, Color.DARK_GRAY));
		setBackground(Color.LIGHT_GRAY);
		listView = false;
		scrollPane = new JScrollPane();
		scrollPane.setPreferredSize(new Dimension(600, 600));
		scrollPane.setBorder(null);
		scrollPane.setDoubleBuffered(true);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);

		scrollPanel = new JPanel();
		scrollPanel.setBackground(Color.GRAY);
		scrollPane.setViewportView(scrollPanel);

		featuredPanel = new JPanel();
		featuredPanel.setBackground(Color.GRAY);

		lblDuHarTidigare = new JLabel();
		setTitle(TIDIGARE);
		setLayout(new BorderLayout(0, 0));
		lblDuHarTidigare.setFont(new Font("SansSerif", Font.BOLD, 16));

		featuredThumb = new JPanel();
		featuredThumb.setBackground(Color.GRAY);
		featuredThumb.setPreferredSize(new Dimension(600, 126));
		GroupLayout gl_featuredPanel = new GroupLayout(featuredPanel);
		gl_featuredPanel
				.setHorizontalGroup(gl_featuredPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_featuredPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_featuredPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																featuredThumb,
																GroupLayout.DEFAULT_SIZE,
																834,
																Short.MAX_VALUE)
														.addComponent(
																lblDuHarTidigare))
										.addContainerGap()));
		gl_featuredPanel.setVerticalGroup(gl_featuredPanel.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_featuredPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblDuHarTidigare)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(featuredThumb, GroupLayout.DEFAULT_SIZE,
								161, Short.MAX_VALUE).addContainerGap()));
		featuredThumb.setLayout(new GridLayout(1, 1, 0, 0));
		featuredPanel.setLayout(gl_featuredPanel);
		scrollPanel.setLayout(new BorderLayout(0, 0));
		scrollPanel.add(featuredPanel, BorderLayout.NORTH);

		centerPanel = new JPanel();
		centerPanel.setBackground(Color.GRAY);
		scrollPanel.add(centerPanel, BorderLayout.CENTER);
		productsPanel = new JPanel();
		centerPanel.add(productsPanel);
		productsPanel.setBackground(Color.LIGHT_GRAY);
		productsPanel.setLayout(new MigLayout(THUMB_VIEW));
		add(scrollPane);

		buttonPanel = new JPanel();
		buttonPanel.setPreferredSize(new Dimension(10, 35));
		buttonPanel.setBackground(Color.LIGHT_GRAY);
		add(buttonPanel, BorderLayout.NORTH);

		thumbViewButton = new JButton("");
		thumbViewButton.setPreferredSize(new Dimension(24, 24));
		thumbViewButton.setIcon(new ImageIcon(ProductsView.class
				.getResource("/imat/resources/expandedview.PNG")));
		thumbViewButton.addActionListener(this);
		thumbViewButton.setActionCommand(THUMB_VIEW);

		listViewButton = new JButton("");
		listViewButton.setPreferredSize(new Dimension(24, 24));
		listViewButton.setIcon(new ImageIcon(ProductsView.class
				.getResource("/imat/resources/listview.PNG")));
		listViewButton.addActionListener(this);
		listViewButton.setActionCommand(LIST_VIEW);

		sortLabel = new JLabel("Sortera efter:");

		filterComboBox = new JComboBox(FILTER_CHOICES);
		filterComboBox.addActionListener(new FilterComboBoxActionListener());

		label = new JLabel("");
		GroupLayout gl_buttonPanel = new GroupLayout(buttonPanel);
		gl_buttonPanel
				.setHorizontalGroup(gl_buttonPanel
						.createParallelGroup(Alignment.TRAILING)
						.addGroup(
								gl_buttonPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_buttonPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_buttonPanel
																		.createSequentialGroup()
																		.addComponent(
																				sortLabel)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				filterComboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				633,
																				Short.MAX_VALUE)
																		.addComponent(
																				thumbViewButton,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				listViewButton,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addGap(6))
														.addGroup(
																gl_buttonPanel
																		.createSequentialGroup()
																		.addComponent(
																				label)
																		.addContainerGap(
																				784,
																				Short.MAX_VALUE)))));
		gl_buttonPanel
				.setVerticalGroup(gl_buttonPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_buttonPanel
										.createSequentialGroup()
										.addGap(6)
										.addGroup(
												gl_buttonPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_buttonPanel
																		.createParallelGroup(
																				Alignment.BASELINE)
																		.addComponent(
																				sortLabel)
																		.addComponent(
																				filterComboBox,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE))
														.addComponent(
																thumbViewButton,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(
																listViewButton,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addComponent(label)
										.addContainerGap(13, Short.MAX_VALUE)));
		buttonPanel.setLayout(gl_buttonPanel);
		scrollPanel.remove(featuredPanel);
		this.revalidate();

	}

	private void setTitle(String title) {
		if (title != null) {
			lblDuHarTidigare.setText("<html>" + title);
		}
	}

	public void setProducts(List<Product> products, String searchString) {
		setTitle(RESULTAT + "\"" + searchString + "\":");
		setProducts(products);
	}

	public void setProducts(List<Product> products) {
		productsPanel.removeAll();
		boolean hasFavorite = false;
		for (Product p : products) {
			if (IDH.isFavorite(p) && !hasFavorite) {
				featuredThumb.removeAll();
				featuredThumb.add(new ProductDisplay(p, true, false, this));
				hasFavorite = true;
			}
			if (listView) {
				productsPanel.setLayout(new MigLayout(LIST_VIEW));
			} else {
				productsPanel.setLayout(new MigLayout(THUMB_VIEW));
			}
			productsPanel.add(new ProductDisplay(p, false, listView, this));
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("buy")) {
			Object src = e.getSource();
			if (src instanceof ShoppingItem) {
				model.addToCart((ShoppingItem) src);
			}
		} else if (ac.matches("wrap \\d")) {
			listView = ac.equals(LIST_VIEW);
			productsPanel.setLayout(new MigLayout(ac));
			setProducts(NavigatorView.getCurrentCategory().getProducts());
			revalidate();
		} else if (ac.equals("fav")) {
			if (NavigatorView.getCurrentCategory() == CustomCategory.FAVORITES) {
				setProducts(IDH.favorites());
			}

		}
	}

	private class FilterComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JComboBox cb = (JComboBox) e.getSource();
			String filter = (String) cb.getSelectedItem();
			List<Product> tmp = NavigatorView.getCurrentCategory()
					.getProducts();
			if (filter.equals(FILTER_CHOICES[0])) {
				Collections.sort(tmp, new ProductNameSort());
			} else if (filter.equals(FILTER_CHOICES[1])) {
				Collections.sort(tmp, new ProductPriceSort());
			}
			setProducts(tmp);

		}
	}
}
