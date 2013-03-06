package imat.gui;

import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;

import se.chalmers.ait.dat215.project.IMatDataHandler;

public class MainFrame {

	private JFrame frame;
	private ShopModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
		    public void run() {
				System.out.println("SHUTDOWN EMINENT");
				IMatDataHandler.getInstance().shutDown();
				ShopModel.getInstance().saveLists();
		    }
		}));
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
		
		model = ShopModel.getInstance();
		
		NavigatorView navigatorView = new NavigatorView(model);
		CenterView centerView = new CenterView(model, navigatorView.getProductsView(), new Checkout(model));
		TabbedView tabbedView = new TabbedView(model);
		

	
		frame.getContentPane().add(navigatorView, BorderLayout.WEST);
		frame.getContentPane().add(centerView, BorderLayout.CENTER);
		frame.getContentPane().add(tabbedView, BorderLayout.EAST);
	}

}
