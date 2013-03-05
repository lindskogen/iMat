package imat.gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Insets;
import java.awt.Cursor;


public class Settings extends JFrame {

	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField addressTextField;
	private JTextField nameText;
	private JTextField postCodeTextField;
	private JTextField postAdressTextField;
	private JTextField cardNumberText;
	private JTextField CVCText;
	private JTextField textField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Settings frame = new Settings();
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
	public Settings() {
		setIconImage(Toolkit.getDefaultToolkit().getImage("/imat/resources/settingsIcon.png"));
		setTitle("Inst\u00E4llningar");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 328, 420);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		this.setResizable(false);
		
		JButton saveChanges = new JButton("Spara \u00E4ndringar");
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(saveChanges, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE))
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
					.addComponent(saveChanges))
		);
		
		JPanel general = new JPanel();
		tabbedPane.addTab("Tema", null, general, null);
		general.setLayout(new MigLayout("", "[][][][]", "[46px][46px][][]"));
		
		JButton grayButton = new JButton("");
		grayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		grayButton.setMargin(new Insets(0, 0, 0, 0));
		grayButton.setIcon(new ImageIcon("/imat/resources/themes/gray.PNG"));
		general.add(grayButton, "cell 0 0,alignx center");
		
		JButton blueButton = new JButton("");
		blueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		blueButton.setIcon(new ImageIcon("/imat/resources/themes/darkBlue.PNG"));
		blueButton.setMargin(new Insets(0, 0, 0, 0));
		general.add(blueButton, "cell 1 0");
		
		JButton lGreenButton = new JButton("");
		lGreenButton.setMargin(new Insets(0, 0, 0, 0));
		lGreenButton.setIcon(new ImageIcon("/imat/resources/themes/lightGreen.PNG"));
		lGreenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		general.add(lGreenButton, "cell 2 0");
		
		JButton dGreenButton = new JButton("");
		dGreenButton.setIcon(new ImageIcon("/imat/resources/themes/darkGreen.PNG"));
		dGreenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dGreenButton.setMargin(new Insets(0, 0, 0, 0));
		general.add(dGreenButton, "cell 3 0");
		
		JLabel lblGr = new JLabel("Gr\u00E5");
		lblGr.setVerticalAlignment(SwingConstants.TOP);
		general.add(lblGr, "cell 0 1,alignx center,aligny top");
		
		JLabel lblBl = new JLabel("Bl\u00E5");
		general.add(lblBl, "cell 1 1,alignx center,aligny top");
		
		JLabel lblLjusgrn = new JLabel("Ljusgr\u00F6n");
		general.add(lblLjusgrn, "cell 2 1,alignx center,aligny top");
		
		JLabel lblMrkgrn = new JLabel("M\u00F6rkgr\u00F6n");
		general.add(lblMrkgrn, "cell 3 1,alignx center,aligny top");
		
		JButton goldButton = new JButton("");
		goldButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		goldButton.setMargin(new Insets(0, 0, 0, 0));
		goldButton.setIcon(new ImageIcon("/imat/resources/themes/gold.PNG"));
		general.add(goldButton, "cell 0 2");
		
		JButton redButton = new JButton("");
		redButton.setIcon(new ImageIcon("/imat/resources/themes/red.PNG"));
		redButton.setMargin(new Insets(0, 0, 0, 0));
		general.add(redButton, "cell 1 2");
		
		JButton violetButton = new JButton("");
		violetButton.setIcon(new ImageIcon("/imat/resources/themes/violet.PNG"));
		violetButton.setMargin(new Insets(0, 0, 0, 0));
		violetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		violetButton.setActionCommand("");
		general.add(violetButton, "cell 2 2");
		
		JButton blackButton = new JButton("");
		blackButton.setMargin(new Insets(0, 0, 0, 0));
		blackButton.setIcon(new ImageIcon("/imat/resources/themes/black.PNG"));
		blackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		general.add(blackButton, "cell 3 2");
		
		JLabel lblGild = new JLabel("Guld");
		general.add(lblGild, "cell 0 3,alignx center,aligny top");
		
		JLabel lblRd = new JLabel("R\u00F6d");
		general.add(lblRd, "cell 1 3,alignx center,aligny top");
		
		JLabel lblLila = new JLabel("Lila");
		general.add(lblLila, "cell 2 3,alignx center,aligny top");
		
		JLabel lblSvart = new JLabel("Svart");
		general.add(lblSvart, "cell 3 3,alignx center,aligny top");
		
		JPanel personal = new JPanel();
		tabbedPane.addTab("Personuppgifter", null, personal, null);
		personal.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.setBackground(Color.RED);
		namePanel.setAlignmentY(0.0f);
		namePanel.setAlignmentX(0.0f);
		personal.add(namePanel);
		namePanel.setLayout(new MigLayout("", "[208.00px]", "[14px][20px]"));
		
		JLabel nameTextField = new JLabel("Namn");
		namePanel.add(nameTextField, "cell 0 0,alignx left,aligny top");
		
		nameText = new JTextField();
		nameText.setColumns(10);
		namePanel.add(nameText, "cell 0 1,growx,aligny center");
		
		JPanel adressPanel = new JPanel();
		adressPanel.setOpaque(false);
		adressPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		adressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		adressPanel.setBackground(Color.RED);
		adressPanel.setLayout(new MigLayout("", "[208.00px]", "[14px][20px]"));
		
		JLabel addressLabel = new JLabel("Adress");
		adressPanel.add(addressLabel, "cell 0 0,alignx left,aligny top");
		
		addressTextField = new JTextField();
		adressPanel.add(addressTextField, "cell 0 1,growx,aligny center");
		addressTextField.setColumns(10);
		personal.add(adressPanel);
		
		JPanel postPanel = new JPanel();
		postPanel.setOpaque(false);
		postPanel.setBackground(Color.RED);
		postPanel.setAlignmentY(0.0f);
		postPanel.setAlignmentX(0.0f);
		personal.add(postPanel);
		postPanel.setLayout(new MigLayout("", "[86px][][grow]", "[14px][20px]"));
		
		JLabel postCodeLabel = new JLabel("Postkod");
		postPanel.add(postCodeLabel, "cell 0 0,alignx left,aligny top");
		
		JLabel postaddressLabel = new JLabel("Postadress");
		postPanel.add(postaddressLabel, "cell 2 0");
		
		postCodeTextField = new JTextField();
		postCodeTextField.setColumns(10);
		postPanel.add(postCodeTextField, "cell 0 1,alignx left,aligny center");
		
		postAdressTextField = new JTextField();
		postPanel.add(postAdressTextField, "cell 2 1,alignx left");
		postAdressTextField.setColumns(10);
		
		JPanel payment = new JPanel();
		tabbedPane.addTab("Betalningsuppgifter", null, payment, null);
		payment.setLayout(new GridLayout(7, 0, 0, 0));
		
		JPanel paymentTitlePanel = new JPanel();
		paymentTitlePanel.setOpaque(false);
		paymentTitlePanel.setBackground(Color.RED);
		paymentTitlePanel.setAlignmentY(0.0f);
		paymentTitlePanel.setAlignmentX(0.0f);
		payment.add(paymentTitlePanel);
		paymentTitlePanel.setLayout(new MigLayout("", "[363.00]", "[]"));
		
		JLabel lblKortbetalning = new JLabel("Kortbetalning");
		lblKortbetalning.setFont(new Font("Tahoma", Font.BOLD, 16));
		paymentTitlePanel.add(lblKortbetalning, "cell 0 0,growx");
		
		JPanel visaMasterPanel = new JPanel();
		visaMasterPanel.setOpaque(false);
		visaMasterPanel.setBackground(Color.RED);
		visaMasterPanel.setAlignmentY(0.0f);
		visaMasterPanel.setAlignmentX(0.0f);
		payment.add(visaMasterPanel);
		visaMasterPanel.setLayout(new MigLayout("", "[][]", "[]"));
		
		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		buttonGroup.add(rdbtnVisa);
		rdbtnVisa.setSelected(true);
		visaMasterPanel.add(rdbtnVisa, "cell 0 0");
		
		JRadioButton rdbtnMastercard = new JRadioButton("Mastercard");
		buttonGroup.add(rdbtnMastercard);
		visaMasterPanel.add(rdbtnMastercard, "cell 1 0");
		
		JPanel cardNbrPanel = new JPanel();
		cardNbrPanel.setOpaque(false);
		cardNbrPanel.setBackground(Color.RED);
		cardNbrPanel.setAlignmentY(0.0f);
		cardNbrPanel.setAlignmentX(0.0f);
		payment.add(cardNbrPanel);
		cardNbrPanel.setLayout(new MigLayout("", "[150.00,left][50.00]", "[][]"));
		
		final JLabel cardNbr = new JLabel("Kortnummer");
		cardNbrPanel.add(cardNbr, "cell 0 0");
		
		final JLabel CVC = new JLabel("CVC");
		cardNbrPanel.add(CVC, "cell 1 0");
		
		cardNumberText = new JTextField();
		cardNumberText.setPreferredSize(new Dimension(6, 16));
		cardNbrPanel.add(cardNumberText, "cell 0 1,growx,aligny top");
		cardNumberText.setColumns(10);
		
		CVCText = new JTextField();
		cardNbrPanel.add(CVCText, "cell 1 1,alignx left");
		CVCText.setColumns(10);
		
		JPanel cardOwnerPanel = new JPanel();
		cardOwnerPanel.setOpaque(false);
		cardOwnerPanel.setBackground(Color.RED);
		cardOwnerPanel.setAlignmentY(0.0f);
		cardOwnerPanel.setAlignmentX(0.0f);
		payment.add(cardOwnerPanel);
		cardOwnerPanel.setLayout(new MigLayout("", "[210px]", "[][]"));
		
		JLabel lblNamn = new JLabel("Namn");
		cardOwnerPanel.add(lblNamn, "cell 0 0");
		
		textField = new JTextField();
		cardOwnerPanel.add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		contentPane.setLayout(gl_contentPane);
		
		saveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				//card number check
				if((cardNumberText.getText().length() == 16 || cardNumberText.getText().length() == 19)){
					if(cardNumberCheck(cardNumberText.getText())){
						//Passed
						//just digits, reset text color.
						cardNbr.setText("Kortnummer");
						tabbedPane.setSelectedIndex(2);
					}else{
						//Failed.
						//other characters in string
						cardNbr.setText("<html> <font color='red'>Kortnummer*</font></html>");
						tabbedPane.setSelectedIndex(2);
					}

				}else{
					System.out.println(cardNumberText.getText().length() + " fel l�ngd");
					cardNbr.setText("<html> <font color='red'>Kortnummer*</font></html>");
				}
				
				//CVC check
				String CVCNoSpace = CVCText.getText().replaceAll(" ", "");
								
				if(CVCNoSpace.length() == 3){
					if(cardNumberCheck(CVCNoSpace)){
						CVC.setText("CVC");	
						System.out.println("OK");
					}else{
						System.out.println("ej digit");
						CVC.setText("<html> <font color='red'>CVC*</font></html>");
						tabbedPane.setSelectedIndex(2);
					}

				}else{
					System.out.println(CVCText.getText().length() + " fel l�ngd");
					CVC.setText("<html> <font color='red'>CVC*</font></html>");
					tabbedPane.setSelectedIndex(2);
				}
				//TODO
				//Save state to DB
				
			}//end listener
		});
	}
	
	
	private boolean cardNumberCheck(String cardNumber){
		cardNumber = cardNumber.replaceAll(" ", "");
		
		for (int i = 0; i < cardNumber.length(); i++) {
            if (!(Character.isDigit(cardNumber.charAt(i)))) {
            	return false;
            }
            
        }
		return true;
		
	}
}
