package imat.gui;

import imat.backend.BackendController;
import imat.backend.ProductList;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.commons.lang3.StringUtils;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class MainFrameTest extends JFrame {

	private JPanel contentPane;
	private BackendController bc;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrameTest frame = new MainFrameTest();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrameTest() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		List<ProductList> listList = new LinkedList<ProductList>();
		
		
		
		ProductList list1 = new ProductList("Fredagskväll");
		ProductList list2 = new ProductList("Pastorns Partypåse");
		
		IMatDataHandler imat = IMatDataHandler.getInstance();
		list1.add(new ShoppingItem(imat.getProduct(10), 4));
		list1.add(new ShoppingItem(imat.getProduct(12), 3));
		list1.add(new ShoppingItem(imat.getProduct(83), 5));
		list1.add(new ShoppingItem(imat.getProduct(45), 7));
		
		
		list2.add(new ShoppingItem(imat.getProduct(9), 2));
		list2.add(new ShoppingItem(imat.getProduct(16), 53));
		list2.add(new ShoppingItem(imat.getProduct(84), 4));
		list2.add(new ShoppingItem(imat.getProduct(66), 9));
		
		listList.add(list1);
		listList.add(list2);
		
		TabbedView view = new TabbedView();
		
		view.setShoppingBasket(list1);
		view.setLists(listList);
		
		contentPane.add(view, BorderLayout.EAST);
		
		ProductsView productsView = new ProductsView();
		contentPane.add(productsView, BorderLayout.CENTER);
		
		productsView.setProducts(imat.findProducts("mjölk"), "mjölk");
		
		
		List<Product> products = imat.getProducts();
		
		
		for (Product p : products) {
			int distance = StringUtils.getLevenshteinDistance("mjlk".toLowerCase(), p.getName().toLowerCase(), 3);
			if (distance != -1) {
				System.out.println(p.getName() + " " + distance);
			}
		}
	}

}
