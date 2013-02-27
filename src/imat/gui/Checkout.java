package imat.gui;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import se.chalmers.ait.dat215.project.CreditCard;
import se.chalmers.ait.dat215.project.IMatDataHandler;

public class Checkout implements ActionListener {

	// TODO: Better confirmation of order
	
	//Constants
	//The price of home delivery
	private int DELIVERY = 20;
	//The price of pickup
	private int PICKUP = 0;
	
	//The IMatDataHandler
	private IMatDataHandler imdh;
	
	//Various variables
	private double sum;
	private double shoppingCart;
	private JLabel sumLabel;
	private JLabel deliveryLabel;
	private JCheckBox save;
	private JFrame frame;
	private JTextField txtCard;
	private JTextField txtSec;
	private JPanel cardPanel;
	private JComboBox delivery;
	private JComboBox pickup;
	private JComboBox year;
	private JComboBox month;
	private JTextField txtName;
	private JRadioButton visa;
	private JRadioButton mastercard;
	private JRadioButton iButik;
	private CreditCard cc;
	private ButtonGroup cardGroup;
	private ButtonGroup payGroup;
	private ButtonGroup deliveryGroup;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Checkout window = new Checkout();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Checkout() {
		initialize();
		InitCardInfo();
	}
	
	public void actionPerformed (ActionEvent e){
		
		if(e.getActionCommand().equals("delivery")){
			//If customer chooses home delivery, set related
			//elements enabled and disable irrelevant ones
			pickup.setEnabled(false);
			delivery.setEnabled(true);
			iButik.setEnabled(false);
			deliveryLabel.setText("Leverans: " + DELIVERY + " kr");
			amendSum(DELIVERY);
		} else if (e.getActionCommand().equals("pickup")){
			//If customer chooses pick up at store, set related
			//elements enabled and disable irrelevant ones
			pickup.setEnabled(true);
			delivery.setEnabled(false);
			iButik.setEnabled(true);
			deliveryLabel.setText("Leverans: " + PICKUP + " kr");
			amendSum(-DELIVERY);
		} else if (e.getActionCommand().equals("credit")){
			//If customer chooses to pay by credit card, set 
			//related elements enabled and add color to show
			//how the fields and panels are now enabled
			cardPanel.setBackground(new Color(238,238,238));
			visa.setBackground(new Color(238,238,238));
			mastercard.setBackground(new Color(238,238,238));
			visa.setEnabled(true);
			mastercard.setEnabled(true);
			txtCard.setEnabled(true);
			txtSec.setEnabled(true);
			txtName.setEnabled(true);
			year.setEnabled(true);
			month.setEnabled(true);
		} else if (e.getActionCommand().equals("finish")){
			//If the customer wishes to finish his purchase,
			//store data (and shutdown, for now)
			
			//---------------------DEBUG ONLY -------------------------
			System.out.println("The password is now Test123");
			imdh.getUser().setPassword("Test123");
			//---------------------DEBUG ONLY -------------------------
			
			if(shallPass()){
				JOptionPane.showMessageDialog(null, "Tack för ditt köp");
				if(save.isSelected()){
					saveCardInfo();
				}
				imdh.placeOrder();
				
				//---------------------DEBUG ONLY -------------------------
				imdh.shutDown();
				System.exit(1);
				//---------------------DEBUG ONLY -------------------------
			}
		} else {
			//If any other way of paying is chosen but credit card,
			//disable all elements in the cardPanel and darken it
			//to make it seem unusable
			cardPanel.setBackground(Color.LIGHT_GRAY);
			visa.setBackground(Color.LIGHT_GRAY);
			mastercard.setBackground(Color.LIGHT_GRAY);
			visa.setEnabled(false);
			mastercard.setEnabled(false);
			txtCard.setEnabled(false);
			txtSec.setEnabled(false);
			txtName.setEnabled(false);
			year.setEnabled(false);
			month.setEnabled(false);
		}
	}
	
	private boolean shallPass() {
		String s = JOptionPane.showInputDialog("Lösenord krävs");
		while(s != null){
			if(s.equals(imdh.getUser().getPassword())){
				return true;
			}			
			s = JOptionPane.showInputDialog("Felaktigt lösenord");
		}
		return false;
	}
	
	//Checks what card the customer has selected
	private String selectedCard() {
		if(mastercard.isSelected()){
			return "Mastercard";
		} else {
			return "Visa";
		}
	}
	
	//Amends the displayed sum with the specified value
	private void amendSum (int i) {
		sum += i;
		sumLabel.setText("Summa: " + sum + " kr");
	}
	
	//Gets the chosen year for the credit cards validity
	private int getChosenYear() {
		String s = (String)year.getSelectedItem();
		return Integer.parseInt(s);
	}
	
	//Gets the chosen month for the credit cards validity
	private int getChosenMonth() {
		String s = (String)month.getSelectedItem();
		return Integer.parseInt(s);
	}
	
	//Sets the corrects year and month validity for the credit card,
	//as received from the CreditCard class.
	private void setYearAndMonth() {
		String s = String.valueOf(cc.getValidMonth());
		month.setSelectedItem(s);
		s = String.valueOf(cc.getValidYear());
		year.setSelectedItem(s);
	}
	
	//Saves the customers card info
	private void saveCardInfo(){
		cc.setHoldersName(txtName.getText());
		cc.setCardNumber(txtCard.getText());
		cc.setVerificationCode(Integer.parseInt(txtSec.getText()));
		cc.setValidMonth(getChosenMonth());
		cc.setValidYear(getChosenYear());
		cc.setCardType(selectedCard());
	}
	
	//Fills the fields containing information about the costumers 
	//credit card
	private void InitCardInfo() {
		cc = imdh.getCreditCard();
		txtName.setText(cc.getHoldersName());
		txtCard.setText(cc.getCardNumber());
		txtSec.setText(String.valueOf(cc.getVerificationCode()));
		setYearAndMonth();
		if(cc.getCardType().equals("Mastercard")){
			mastercard.setSelected(true);
		}
	
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		imdh = IMatDataHandler.getInstance();
		shoppingCart = imdh.getShoppingCart().getTotal();
		sum = shoppingCart + DELIVERY;
		
		payGroup = new ButtonGroup();
		deliveryGroup = new ButtonGroup();
		cardGroup = new ButtonGroup();
		
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 540);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel checkoutPanel = new JPanel();
		frame.getContentPane().add(checkoutPanel, BorderLayout.CENTER);
		checkoutPanel.setLayout(new CardLayout(0, 0));
		
		JPanel panel_1 = new JPanel();
		checkoutPanel.add(panel_1, "name_4538180736579");
		
		JPanel panel = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_2, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 590, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 590, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JRadioButton rdbtnKreditkort = new JRadioButton("Kreditkort");
		rdbtnKreditkort.setSelected(true);
		
		JRadioButton rdbtnInternetbank = new JRadioButton("Internetbank");
		
		JRadioButton rdbtnIButik = new JRadioButton("I butik");
		rdbtnIButik.setEnabled(false);
		
		iButik = rdbtnIButik;
		
		payGroup.add(rdbtnKreditkort);
		payGroup.add(rdbtnInternetbank);
		payGroup.add(rdbtnIButik);
		
		rdbtnKreditkort.addActionListener(this);
		rdbtnKreditkort.setActionCommand("credit");
		rdbtnInternetbank.addActionListener(this);
		rdbtnInternetbank.setActionCommand("bank");
		rdbtnIButik.addActionListener(this);
		rdbtnIButik.setActionCommand("store");
		
		
		JLabel lblHurVillDu_1 = new JLabel("Hur vill du betala?");
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(UIManager.getColor("Panel.background"));
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JButton btnSlutfr = new JButton("Slutför");
		btnSlutfr.addActionListener(this);
		btnSlutfr.setActionCommand("finish");
		
		JCheckBox chckbxSparaMinaUppgifter = new JCheckBox("Spara mina uppgifter");
		save = chckbxSparaMinaUppgifter;
		chckbxSparaMinaUppgifter.setSelected(true);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnInternetbank)
						.addComponent(rdbtnIButik)
						.addComponent(lblHurVillDu_1)
						.addComponent(rdbtnKreditkort))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
							.addComponent(chckbxSparaMinaUppgifter)
							.addPreferredGap(ComponentPlacement.RELATED, 210, Short.MAX_VALUE)
							.addComponent(btnSlutfr))
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 421, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblHurVillDu_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnKreditkort)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnInternetbank)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rdbtnIButik))
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSlutfr)
						.addComponent(chckbxSparaMinaUppgifter))
					.addContainerGap())
		);
		
		cardPanel = panel_4;
		
		JLabel lblKortnummer = new JLabel("Kortnummer");
		
		txtCard = new JTextField();
		txtCard.setColumns(10);
		
		JLabel lblSakerhetsKod = new JLabel("Säkerhetskod (CVC)");
		
		txtSec = new JTextField();
		txtSec.setColumns(10);
		
		JLabel lblKortinnehavare = new JLabel("Kortinnehavare");
		
		txtName = new JTextField();
		txtName.setColumns(10);
		
		JLabel lblGiltligTill = new JLabel("Giltlig till (Månad/År)");
		
		JLabel label = new JLabel("/");
		
		JRadioButton rdbtnVisa = new JRadioButton("Visa");
		rdbtnVisa.setSelected(true);
		rdbtnVisa.setBackground(UIManager.getColor("Panel.background"));
		visa = rdbtnVisa;
		
		JRadioButton rdbtnMastercard = new JRadioButton("Mastercard");
		rdbtnMastercard.setBackground(UIManager.getColor("Panel.background"));
		mastercard = rdbtnMastercard;
		
		cardGroup.add(rdbtnVisa);
		cardGroup.add(rdbtnMastercard);
		
		JLabel lblKorttyp = new JLabel("Korttyp");
		
		JComboBox comboMonth = new JComboBox();
		month = comboMonth;
		comboMonth.setModel(new DefaultComboBoxModel(new String[] {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"}));
		
		JComboBox comboYear = new JComboBox();
		year = comboYear;
		comboYear.setModel(new DefaultComboBoxModel(new String[] {"13", "14", "15", "16", "17", "18", "19", "20", "21"}));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(lblGiltligTill)
							.addGap(18)
							.addComponent(lblKorttyp))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(comboMonth, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(comboYear, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(rdbtnVisa)
							.addGap(18)
							.addComponent(rdbtnMastercard))
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
							.addComponent(lblKortinnehavare)
							.addGroup(gl_panel_4.createSequentialGroup()
								.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
									.addComponent(lblKortnummer)
									.addComponent(txtCard, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(txtSec)
									.addComponent(lblSakerhetsKod)))
							.addComponent(txtName)))
					.addGap(65))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblKortinnehavare)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(txtName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblKortnummer)
						.addComponent(lblSakerhetsKod))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtCard, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(txtSec, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblGiltligTill)
						.addComponent(lblKorttyp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(rdbtnVisa)
						.addComponent(rdbtnMastercard)
						.addComponent(comboMonth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(comboYear, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(12, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		panel_3.setLayout(gl_panel_3);
		
		JRadioButton rdbtnHemleveransKr = new JRadioButton("Hemleverans");
		rdbtnHemleveransKr.setSelected(true);
		
		JComboBox deliveryBox = new JComboBox();
		deliveryBox.setModel(new DefaultComboBoxModel(new String[] {"Måndag em", "Tisdag fm", "Tisdag em", "Onsdag fm", "Onsdag em"}));
		
		JRadioButton rdbtnHmtaIButik = new JRadioButton("Hämta i butik");
		
		deliveryGroup.add(rdbtnHemleveransKr);
		deliveryGroup.add(rdbtnHmtaIButik);
		
		rdbtnHemleveransKr.addActionListener(this);
		rdbtnHemleveransKr.setActionCommand("delivery");
		rdbtnHmtaIButik.addActionListener(this);
		rdbtnHmtaIButik.setActionCommand("pickup");
		
		JComboBox pickupBox = new JComboBox();
		pickupBox.setEnabled(false);
		pickupBox.setModel(new DefaultComboBoxModel(new String[] {"Måndag em", "Tisdag fm", "Tisdag em", "Onsdag fm", "Onsdag em"}));
		
		delivery = deliveryBox;
		pickup = pickupBox;
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Checkout.class.getResource("/imat/resources/homeDelivery150x100.PNG")));
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Checkout.class.getResource("/imat/resources/inStore150x100.PNG")));
		
		JLabel lblSumma = new JLabel("Summa: " + sum + " kr");
		sumLabel = lblSumma;
		lblSumma.setFont(new Font("Dialog", Font.BOLD, 16));
		
		JLabel lblLeveransKr = new JLabel("Leverans: " + DELIVERY + " kr");
		deliveryLabel = lblLeveransKr;
		
		JLabel lblVarukorgKr = new JLabel("Varukorg: " + shoppingCart + " kr");
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
						.addComponent(deliveryBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rdbtnHemleveransKr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(pickupBox, 0, 150, Short.MAX_VALUE)
						.addComponent(label_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rdbtnHmtaIButik, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSumma)
						.addComponent(lblVarukorgKr)
						.addComponent(lblLeveransKr))
					.addGap(125))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblSumma)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblVarukorgKr)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblLeveransKr))
						.addComponent(label_1)
						.addComponent(label_2))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnHemleveransKr)
						.addComponent(rdbtnHmtaIButik))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(deliveryBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(pickupBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(29, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Välkommen till kassan!");
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
		
		JLabel lblHurVillDu = new JLabel("Hur vill du ha dina varor?");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblHurVillDu)
						.addComponent(lblNewLabel))
					.addContainerGap(187, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(5)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblHurVillDu)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		panel_1.setLayout(gl_panel_1);
	}
}