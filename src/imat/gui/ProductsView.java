package imat.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;
import javax.swing.ScrollPaneConstants;
import javax.swing.JLabel;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;
import javax.swing.BoxLayout;
import java.awt.Dimension;
import java.awt.Color;

public class ProductsView extends JPanel {
	
	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	private JScrollPane scrollPane;
	private JPanel scrollPanel;
	private JPanel featuredPanel;
	private JPanel productsPanel;
	private JLabel lblDuHarTidigare;
	private JPanel featuredThumb;
	

	/**
	 * Create the panel.
	 */
	public ProductsView() {
		setBackground(Color.WHITE);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setDoubleBuffered(true);
		scrollPane.getVerticalScrollBar().setUnitIncrement(10);
		setLayout(new MigLayout("", "[900px]", "[563px]"));
		
		scrollPanel = new JPanel();
		scrollPanel.setBackground(Color.WHITE);
		scrollPane.setViewportView(scrollPanel);
		
		featuredPanel = new JPanel();
		featuredPanel.setBackground(Color.WHITE);
		
		lblDuHarTidigare = new JLabel("Du har tidigare köpt");
		lblDuHarTidigare.setFont(new Font("SansSerif", Font.BOLD, 16));
		
		featuredThumb = new JPanel();
		featuredThumb.setBackground(Color.WHITE);
		featuredThumb.setPreferredSize(new Dimension(600, 126));
		GroupLayout gl_featuredPanel = new GroupLayout(featuredPanel);
		gl_featuredPanel.setHorizontalGroup(
			gl_featuredPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_featuredPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_featuredPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(featuredThumb, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
						.addGroup(gl_featuredPanel.createSequentialGroup()
							.addComponent(lblDuHarTidigare)
							.addContainerGap(667, Short.MAX_VALUE))))
		);
		gl_featuredPanel.setVerticalGroup(
			gl_featuredPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_featuredPanel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDuHarTidigare)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(featuredThumb, GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
					.addContainerGap())
		);
		featuredThumb.setLayout(new GridLayout(1, 1, 0, 0));
		featuredPanel.setLayout(gl_featuredPanel);
		
		productsPanel = new JPanel();
		productsPanel.setBackground(Color.WHITE);
		productsPanel.setPreferredSize(new Dimension(600, 252));
		GroupLayout gl_scrollPanel = new GroupLayout(scrollPanel);
		gl_scrollPanel.setHorizontalGroup(
			gl_scrollPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_scrollPanel.createSequentialGroup()
					.addGroup(gl_scrollPanel.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_scrollPanel.createSequentialGroup()
							.addGap(12)
							.addComponent(productsPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addComponent(featuredPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 858, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_scrollPanel.setVerticalGroup(
			gl_scrollPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_scrollPanel.createSequentialGroup()
					.addComponent(featuredPanel, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(productsPanel, GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
					.addContainerGap())
		);
		productsPanel.setLayout(new GridLayout(0, 2, 0, 0));
		scrollPanel.setLayout(gl_scrollPanel);
		add(scrollPane, "cell 0 0,grow");

	}
	
	public void dispCategory(ProductCategory c) {
		List<Product> products = IDH.getProducts(c);
		IDH.addFavorite(39);
		GridLayout l = (GridLayout)productsPanel.getLayout();
		int nbrOfProducts = products.size();
		if(nbrOfProducts % 2 == 0) {
			l.setRows(nbrOfProducts / 2);
		}else{
			l.setRows(nbrOfProducts / 2 + 1);
		}
		
		for(Product p : products) {
			if(IDH.isFavorite(p)) {
				featuredThumb.add(new ProductThumbnail(p,true));
			}
			productsPanel.add(new ProductThumbnail(p,false));
		}
	}
}
