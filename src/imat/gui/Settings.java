package imat.gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import net.miginfocom.swing.MigLayout;
import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.Customer;
import se.chalmers.ait.dat215.project.IMatDataHandler;

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
	private JTextField textField_1;
	private JTextField emailTextField;
	private JTextField homeNbrTextField;
	private JTextField cellPhoneTextField;
	private JRadioButton rdbtnVisa;
	private JRadioButton rdbtnMastercard;
	private Customer customer = IMatDataHandler.getInstance().getCustomer();
	private CreditCard card = IMatDataHandler.getInstance().getCreditCard();
	private JComboBox monthBox, yearBox;
	private boolean cardNbrError;
	private boolean cardCVCError;

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
		setPreferredSize(new Dimension(400, 0));
		setResizable(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(
				Settings.class.getResource("/imat/resources/settingsIcon.png")));
		setTitle("Inst\u00E4llningar");
		setAlwaysOnTop(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 400, 438);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JButton saveChanges = new JButton("Spara \u00E4ndringar");

		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(gl_contentPane
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_contentPane
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(saveChanges,
										GroupLayout.PREFERRED_SIZE, 134,
										GroupLayout.PREFERRED_SIZE))
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 379,
						Short.MAX_VALUE));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(
				Alignment.TRAILING).addGroup(
				gl_contentPane
						.createSequentialGroup()
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE,
								354, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED, 41,
								Short.MAX_VALUE).addComponent(saveChanges)));

		JPanel personal = new JPanel();
		tabbedPane.addTab("Personuppgifter", null, personal, null);
		personal.setLayout(new GridLayout(7, 1, 0, 0));

		JPanel namePanel = new JPanel();
		namePanel.setOpaque(false);
		namePanel.setBackground(Color.RED);
		namePanel.setAlignmentY(0.0f);
		namePanel.setAlignmentX(0.0f);
		personal.add(namePanel);
		namePanel.setLayout(new MigLayout("", "[104.00px][102.00]",
				"[14px][20px]"));

		JLabel firstNameTextField = new JLabel("F\u00F6rnamn");
		namePanel.add(firstNameTextField, "cell 0 0,alignx left,aligny top");

		JLabel lblEfternamn = new JLabel("Efternamn");
		namePanel.add(lblEfternamn, "cell 1 0");

		nameText = new JTextField(customer.getFirstName());
		nameText.setColumns(10);
		namePanel.add(nameText, "cell 0 1,growx,aligny center");

		textField_1 = new JTextField(customer.getLastName());
		namePanel.add(textField_1, "cell 1 1,growx");
		textField_1.setColumns(10);

		JPanel adressPanel = new JPanel();
		adressPanel.setOpaque(false);
		adressPanel.setAlignmentY(Component.TOP_ALIGNMENT);
		adressPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
		adressPanel.setBackground(Color.RED);
		adressPanel.setLayout(new MigLayout("", "[208.00px]", "[14px][20px]"));

		JLabel addressLabel = new JLabel("Adress");
		adressPanel.add(addressLabel, "cell 0 0,alignx left,aligny top");

		addressTextField = new JTextField(customer.getAddress());
		adressPanel.add(addressTextField, "cell 0 1,growx,aligny center");
		addressTextField.setColumns(10);
		personal.add(adressPanel);

		JPanel postPanel = new JPanel();
		postPanel.setOpaque(false);
		postPanel.setBackground(Color.RED);
		postPanel.setAlignmentY(0.0f);
		postPanel.setAlignmentX(0.0f);
		personal.add(postPanel);
		postPanel
				.setLayout(new MigLayout("", "[86px][][grow]", "[14px][20px]"));

		JLabel postCodeLabel = new JLabel("Postkod");
		postPanel.add(postCodeLabel, "cell 0 0,alignx left,aligny top");

		JLabel postaddressLabel = new JLabel("Postadress");
		postPanel.add(postaddressLabel, "cell 2 0");

		postCodeTextField = new JTextField(customer.getPostCode());
		postCodeTextField.setColumns(10);
		postPanel.add(postCodeTextField, "cell 0 1,alignx left,aligny center");

		postAdressTextField = new JTextField(customer.getPostAddress());
		postPanel.add(postAdressTextField, "cell 2 1,alignx left");
		postAdressTextField.setColumns(10);

		JPanel panel = new JPanel();
		panel.setOpaque(false);
		panel.setBackground(Color.RED);
		panel.setAlignmentY(0.0f);
		panel.setAlignmentX(0.0f);
		personal.add(panel);
		panel.setLayout(new MigLayout("", "[208px]", "[][]"));

		JLabel lblEmail = new JLabel("E-Mail");
		panel.add(lblEmail, "cell 0 0");

		emailTextField = new JTextField(customer.getEmail());
		panel.add(emailTextField, "cell 0 1,growx");
		emailTextField.setColumns(10);

		JPanel panel_1 = new JPanel();
		panel_1.setOpaque(false);
		panel_1.setBackground(Color.RED);
		panel_1.setAlignmentY(0.0f);
		panel_1.setAlignmentX(0.0f);
		personal.add(panel_1);
		panel_1.setLayout(new MigLayout("", "[208px]", "[][]"));

		JLabel lblHemtelefon = new JLabel("Hemtelefon");
		panel_1.add(lblHemtelefon, "cell 0 0");

		homeNbrTextField = new JTextField(customer.getPhoneNumber());
		panel_1.add(homeNbrTextField, "cell 0 1,growx");
		homeNbrTextField.setColumns(10);

		JPanel panel_2 = new JPanel();
		panel_2.setOpaque(false);
		panel_2.setBackground(Color.RED);
		panel_2.setAlignmentY(0.0f);
		panel_2.setAlignmentX(0.0f);
		personal.add(panel_2);
		panel_2.setLayout(new MigLayout("", "[208px]", "[][]"));

		JLabel lblMobiltelefon = new JLabel("Mobiltelefon");
		panel_2.add(lblMobiltelefon, "cell 0 0");

		cellPhoneTextField = new JTextField(customer.getPhoneNumber());
		panel_2.add(cellPhoneTextField, "cell 0 1,growx");
		cellPhoneTextField.setColumns(10);

		JPanel payment = new JPanel();
		tabbedPane.addTab("Betalningsuppgifter", null, payment, null);

		JPanel paymentTitlePanel = new JPanel();
		paymentTitlePanel.setOpaque(false);
		paymentTitlePanel.setBackground(Color.RED);
		paymentTitlePanel.setAlignmentY(0.0f);
		paymentTitlePanel.setAlignmentX(0.0f);
		paymentTitlePanel.setLayout(new MigLayout("", "[363.00]", "[]"));

		JLabel lblKortbetalning = new JLabel("Kortbetalning");
		lblKortbetalning.setFont(new Font("Tahoma", Font.BOLD, 16));
		paymentTitlePanel.add(lblKortbetalning, "cell 0 0,alignx left");

		JPanel visaMasterPanel = new JPanel();
		visaMasterPanel.setOpaque(false);
		visaMasterPanel.setBackground(Color.RED);
		visaMasterPanel.setAlignmentY(0.0f);
		visaMasterPanel.setAlignmentX(0.0f);
		visaMasterPanel.setLayout(new MigLayout("", "[][]", "[]"));

		rdbtnVisa = new JRadioButton("Visa");
		buttonGroup.add(rdbtnVisa);
		rdbtnVisa.setSelected(true);
		visaMasterPanel.add(rdbtnVisa, "cell 0 0");

		rdbtnMastercard = new JRadioButton("Mastercard");
		buttonGroup.add(rdbtnMastercard);
		visaMasterPanel.add(rdbtnMastercard, "cell 1 0");

		JPanel cardNbrPanel = new JPanel();
		cardNbrPanel.setOpaque(false);
		cardNbrPanel.setBackground(Color.RED);
		cardNbrPanel.setAlignmentY(0.0f);
		cardNbrPanel.setAlignmentX(0.0f);

		final JLabel cardNbr = new JLabel("Kortnummer");

		final JLabel CVC = new JLabel("CVC");

		cardNumberText = new JTextField(card.getCardNumber());
		cardNumberText.setPreferredSize(new Dimension(6, 16));
		cardNumberText.setColumns(10);

		CVCText = new JTextField(card.getVerificationCode());
		CVCText.setColumns(3);

		JPanel cardOwnerPanel = new JPanel();
		cardOwnerPanel.setOpaque(false);
		cardOwnerPanel.setBackground(Color.RED);
		cardOwnerPanel.setAlignmentY(0.0f);
		cardOwnerPanel.setAlignmentX(0.0f);
		cardOwnerPanel.setLayout(new MigLayout("", "[210px]", "[][]"));

		JLabel lblNamn = new JLabel("Kortinnehavarens namn");
		cardOwnerPanel.add(lblNamn, "cell 0 0");

		textField = new JTextField(card.getHoldersName());
		cardOwnerPanel.add(textField, "cell 0 1,growx");
		textField.setColumns(10);
		GroupLayout gl_payment = new GroupLayout(payment);
		gl_payment.setHorizontalGroup(gl_payment
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_payment
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(paymentTitlePanel,
										GroupLayout.PREFERRED_SIZE, 321,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(
						gl_payment
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(visaMasterPanel,
										GroupLayout.PREFERRED_SIZE, 321,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(
						gl_payment
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(cardNbrPanel,
										GroupLayout.PREFERRED_SIZE, 321,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(24, Short.MAX_VALUE))
				.addGroup(
						gl_payment
								.createSequentialGroup()
								.addContainerGap()
								.addComponent(cardOwnerPanel,
										GroupLayout.PREFERRED_SIZE, 321,
										GroupLayout.PREFERRED_SIZE)
								.addContainerGap(24, Short.MAX_VALUE)));
		gl_payment.setVerticalGroup(gl_payment.createParallelGroup(
				Alignment.LEADING).addGroup(
				gl_payment
						.createSequentialGroup()
						.addComponent(paymentTitlePanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(visaMasterPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE)
						.addGap(7)
						.addComponent(cardNbrPanel, GroupLayout.PREFERRED_SIZE,
								106, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cardOwnerPanel,
								GroupLayout.PREFERRED_SIZE,
								GroupLayout.DEFAULT_SIZE,
								GroupLayout.PREFERRED_SIZE).addGap(66)));

		JLabel yearLabel = new JLabel("År");

		JLabel monthLabel = new JLabel("Månad");

		JLabel label = new JLabel("/");

		monthBox = new JComboBox();
		monthBox.setModel(new DefaultComboBoxModel(new Integer[] { 1, 2, 3, 4,
				5, 6, 7, 8, 9, 10, 11, 12 }));
		monthBox.setSelectedItem(card.getValidMonth());

		yearBox = new JComboBox();
		yearBox.setModel(new DefaultComboBoxModel(new Integer[] { 13, 14, 15,
				16, 17, 18, 18, 20, 21 }));
		yearBox.setSelectedItem(card.getValidYear());
		GroupLayout gl_cardNbrPanel = new GroupLayout(cardNbrPanel);
		gl_cardNbrPanel
				.setHorizontalGroup(gl_cardNbrPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_cardNbrPanel
										.createSequentialGroup()
										.addGap(6)
										.addGroup(
												gl_cardNbrPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_cardNbrPanel
																		.createSequentialGroup()
																		.addComponent(
																				cardNbr)
																		.addGap(69)
																		.addComponent(
																				CVC))
														.addGroup(
																gl_cardNbrPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_cardNbrPanel
																						.createParallelGroup(
																								Alignment.LEADING,
																								false)
																						.addComponent(
																								cardNumberText,
																								GroupLayout.PREFERRED_SIZE,
																								150,
																								GroupLayout.PREFERRED_SIZE)
																						.addGroup(
																								gl_cardNbrPanel
																										.createSequentialGroup()
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addGroup(
																												gl_cardNbrPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																monthBox,
																																GroupLayout.PREFERRED_SIZE,
																																GroupLayout.DEFAULT_SIZE,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																monthLabel))
																										.addPreferredGap(
																												ComponentPlacement.RELATED)
																										.addComponent(
																												label,
																												GroupLayout.PREFERRED_SIZE,
																												7,
																												GroupLayout.PREFERRED_SIZE)
																										.addGroup(
																												gl_cardNbrPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addGroup(
																																gl_cardNbrPanel
																																		.createSequentialGroup()
																																		.addPreferredGap(
																																				ComponentPlacement.RELATED)
																																		.addComponent(
																																				yearLabel))
																														.addGroup(
																																gl_cardNbrPanel
																																		.createSequentialGroup()
																																		.addGap(3)
																																		.addComponent(
																																				yearBox,
																																				GroupLayout.PREFERRED_SIZE,
																																				GroupLayout.DEFAULT_SIZE,
																																				GroupLayout.PREFERRED_SIZE)))))
																		.addPreferredGap(
																				ComponentPlacement.RELATED,
																				13,
																				Short.MAX_VALUE)
																		.addComponent(
																				CVCText,
																				GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE,
																				GroupLayout.PREFERRED_SIZE)
																		.addPreferredGap(
																				ComponentPlacement.RELATED)))
										.addGap(132)));
		gl_cardNbrPanel
				.setVerticalGroup(gl_cardNbrPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_cardNbrPanel
										.createSequentialGroup()
										.addGroup(
												gl_cardNbrPanel
														.createParallelGroup(
																Alignment.LEADING)
														.addGroup(
																gl_cardNbrPanel
																		.createSequentialGroup()
																		.addGap(27)
																		.addComponent(
																				CVCText,
																				GroupLayout.PREFERRED_SIZE,
																				19,
																				GroupLayout.PREFERRED_SIZE))
														.addGroup(
																gl_cardNbrPanel
																		.createSequentialGroup()
																		.addGap(6)
																		.addGroup(
																				gl_cardNbrPanel
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								cardNbr)
																						.addComponent(
																								CVC))
																		.addPreferredGap(
																				ComponentPlacement.RELATED)
																		.addComponent(
																				cardNumberText,
																				GroupLayout.PREFERRED_SIZE,
																				19,
																				GroupLayout.PREFERRED_SIZE)))
										.addPreferredGap(
												ComponentPlacement.UNRELATED)
										.addGroup(
												gl_cardNbrPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																monthLabel)
														.addComponent(yearLabel))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												gl_cardNbrPanel
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																monthBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE)
														.addComponent(label)
														.addComponent(
																yearBox,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addContainerGap()));
		cardNbrPanel.setLayout(gl_cardNbrPanel);
		payment.setLayout(gl_payment);

		JPanel general = new JPanel();
		tabbedPane.addTab("Tema", null, general, null);
		general.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JPanel panel_3 = new JPanel();
		general.add(panel_3);
		panel_3.setLayout(new MigLayout("", "[][][][]", "[46px][46px][][]"));

		JButton grayButton = new JButton("");
		panel_3.add(grayButton, "cell 0 0,alignx center");
		grayButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});

		grayButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		grayButton.setMargin(new Insets(0, 0, 0, 0));
		grayButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/gray.PNG")));

		JButton blueButton = new JButton("");
		panel_3.add(blueButton, "cell 1 0");
		blueButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		blueButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		blueButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/darkBlue.PNG")));
		blueButton.setMargin(new Insets(0, 0, 0, 0));

		JButton lGreenButton = new JButton("");
		panel_3.add(lGreenButton, "cell 2 0");
		lGreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		lGreenButton.setMargin(new Insets(0, 0, 0, 0));
		lGreenButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/lightGreen.PNG")));
		lGreenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JButton dGreenButton = new JButton("");
		panel_3.add(dGreenButton, "cell 3 0");
		dGreenButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		dGreenButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/darkGreen.PNG")));
		dGreenButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		dGreenButton.setMargin(new Insets(0, 0, 0, 0));

		JLabel lblGr = new JLabel("Gr\u00E5");
		panel_3.add(lblGr, "cell 0 1,alignx center,aligny top");
		lblGr.setVerticalAlignment(SwingConstants.TOP);

		JLabel lblBl = new JLabel("Bl\u00E5");
		panel_3.add(lblBl, "cell 1 1,alignx center,aligny top");

		JLabel lblLjusgrn = new JLabel("Ljusgr\u00F6n");
		panel_3.add(lblLjusgrn, "cell 2 1,alignx center,aligny top");

		JLabel lblMrkgrn = new JLabel("M\u00F6rkgr\u00F6n");
		panel_3.add(lblMrkgrn, "cell 3 1,alignx center,aligny top");

		JButton goldButton = new JButton("");
		panel_3.add(goldButton, "cell 0 2");
		goldButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		goldButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		goldButton.setMargin(new Insets(0, 0, 0, 0));
		goldButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/gold.PNG")));

		JButton redButton = new JButton("");
		panel_3.add(redButton, "cell 1 2");
		redButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		redButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/red.PNG")));
		redButton.setMargin(new Insets(0, 0, 0, 0));

		JButton violetButton = new JButton("");
		panel_3.add(violetButton, "cell 2 2");
		violetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		violetButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/violet.PNG")));
		violetButton.setMargin(new Insets(0, 0, 0, 0));
		violetButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		violetButton.setActionCommand("");

		JButton blackButton = new JButton("");
		panel_3.add(blackButton, "cell 3 2");
		blackButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// TODO
			}
		});
		blackButton.setMargin(new Insets(0, 0, 0, 0));
		blackButton.setIcon(new ImageIcon(Settings.class
				.getResource("/imat/resources/themes/black.PNG")));
		blackButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		JLabel lblGild = new JLabel("Guld");
		panel_3.add(lblGild, "cell 0 3,alignx center,aligny top");

		JLabel lblRd = new JLabel("R\u00F6d");
		panel_3.add(lblRd, "cell 1 3,alignx center,aligny top");

		JLabel lblLila = new JLabel("Lila");
		panel_3.add(lblLila, "cell 2 3,alignx center,aligny top");

		JLabel lblSvart = new JLabel("Svart");
		panel_3.add(lblSvart, "cell 3 3,alignx center,aligny top");
		contentPane.setLayout(gl_contentPane);

		saveChanges.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				// card number check
				if ((cardNumberText.getText().length() == 16 || cardNumberText
						.getText().length() == 19)) {
					if (cardNumberCheck(cardNumberText.getText())) {
						// Passed
						// just digits, reset text color.
						cardNbr.setText("Kortnummer");
						card.setCardNumber(cardNumberText.getText());
						cardNbrError = false;
					} else {
						// Failed.
						// other characters in string
						cardNbr.setText("<html> <font color='red'>Kortnummer*</font></html>");
						tabbedPane.setSelectedIndex(1);
						cardNbrError = true;

					}
				} else {
					System.out.println(cardNumberText.getText().length()
							+ " fel längd");
					cardNbr.setText("<html> <font color='red'>Kortnummer*</font></html>");
					cardNbrError = true;
				}

				// CVC check
				String CVCNoSpace = CVCText.getText().replaceAll(" ", "");

				if (CVCNoSpace.length() == 3) {
					if (cardNumberCheck(CVCNoSpace)) {
						CVC.setText("CVC");
						cardCVCError = false;
						System.out.println("OK");
					} else {
						System.out.println("ej digit");
						CVC.setText("<html> <font color='red'>CVC*</font></html>");
						cardCVCError = true;
						tabbedPane.setSelectedIndex(1);
					}
				} else {
					System.out.println(CVCText.getText().length()
							+ " fel längd");
					CVC.setText("<html> <font color='red'>CVC*</font></html>");
					tabbedPane.setSelectedIndex(1);
					cardCVCError = true;
				}
				customer.setFirstName(nameText.getText());
				customer.setLastName(textField_1.getText());
				customer.setAddress(addressTextField.getText());
				customer.setPostCode(postCodeTextField.getText());
				customer.setPostAddress(postAdressTextField.getText());
				customer.setEmail(emailTextField.getText());
				customer.setPhoneNumber(homeNbrTextField.getText());
				customer.setPhoneNumber(cellPhoneTextField.getText());
				if (rdbtnMastercard.isSelected()) {
					card.setCardType(rdbtnMastercard.getText());
				} else {
					card.setCardType(rdbtnVisa.getText());
				}
				card.setHoldersName(textField.getText());
				card.setValidYear((int) yearBox.getSelectedItem());
				card.setValidMonth((int) monthBox.getSelectedItem());
			if(!IMatDataHandler.getInstance().isCustomerComplete()) {
				tabbedPane.setSelectedIndex(0);
				
			}else if (IMatDataHandler.getInstance().isCustomerComplete() && !cardNbrError && !cardCVCError ) {
					dispose();
				}

			}// end listener
		});
	}

	private boolean cardNumberCheck(String cardNumber) {
		cardNumber = cardNumber.replaceAll(" ", "");

		for (int i = 0; i < cardNumber.length(); i++) {
			if (!(Character.isDigit(cardNumber.charAt(i)))) {
				return false;
			}

		}
		return true;

	}
}