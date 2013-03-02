package imat.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.BorderLayout;

public class MainFrame {

	private JFrame frame;

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
		frame = new JFrame();
		frame.setBounds(50, 50, 900, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		NavigatorView navigatorView = new NavigatorView();
		frame.getContentPane().add(navigatorView, BorderLayout.WEST);
		
		ProductsView productsView = navigatorView.getProductsView();
		frame.getContentPane().add(productsView, BorderLayout.CENTER);

		TabbedView tabbedView = new TabbedView();
		frame.getContentPane().add(tabbedView, BorderLayout.EAST);
	}

}
