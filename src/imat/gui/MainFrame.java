package imat.gui;

import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

public class MainFrame {

	private JFrame frame;
	private ShopModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.frame.setVisible(true);
					window.frame.pack();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame("iMat - Grupp 1");
		frame.setBounds(50, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		model = new ShopModel();
		
		NavigatorView navigatorView = new NavigatorView(model);
		ProductsView productsView = navigatorView.getProductsView();
		TabbedView tabbedView = new TabbedView(model);
		

	
		frame.getContentPane().add(navigatorView, BorderLayout.WEST);
		
		frame.getContentPane().add(productsView, BorderLayout.CENTER);

		frame.getContentPane().add(tabbedView, BorderLayout.EAST);
	}

}
