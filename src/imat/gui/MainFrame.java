package imat.gui;

import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.User;

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
					window.createUser();
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
	
	//If this is the first run, user will be prompted to create a user
	private void createUser() {
		if(IMatDataHandler.getInstance().isFirstRun()){
			
			User user = IMatDataHandler.getInstance().getUser();
			Icon passIcon = new ImageIcon(Checkout.class.getResource("/imat/resources/passwordicon.PNG"));
			JPasswordField passField = new JPasswordField();
			JTextField userField = new JTextField();
			JLabel userLabel = new JLabel("Användarnamn:");
			JLabel passLabel = new JLabel("Lösenord: ");
			
			JComponent[] componentList = new JComponent[]{userLabel, userField, passLabel, passField};

			JOptionPane.showMessageDialog(frame, componentList, "Mata in användaruppgifter", 
					JOptionPane.QUESTION_MESSAGE, passIcon);
			
			user.setPassword(String.copyValueOf(passField.getPassword()));
			user.setUserName(userField.getText());
		}
	}

}
