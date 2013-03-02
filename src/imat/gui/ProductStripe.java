package imat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductStripe extends JPanel implements ActionListener {
	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	SpinnerModel stModel = new SpinnerNumberModel(new Integer(1),
			new Integer(1), null, new Integer(1));
	SpinnerModel kgModel = new SpinnerNumberModel(new Double(0.1), new Double(
			0.1), null, new Double(0.1));
	private Product product;
	private JPanel panel;
	private JLabel titleLabel;
	private JButton buyButton;
	private JLabel priceLabel;
	private JPanel panel_1;
	private JLabel imageLabel;
	
	private PropertyChangeSupport ps;

	private static NumberFormat format = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("sv-SE")); 
	private JLabel sumLabel;
	private JSpinner qSpinner;
	private JLabel suffixLabel;

	/**
	 * Create the panel.
	 */
	public ProductStripe(Product p) {
		setBackground(Color.WHITE);
		
		ps = new PropertyChangeSupport(this);

		product = p;
		
		setPreferredSize(new Dimension(900, 52));
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		
		titleLabel = new JLabel("Label");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		buyButton = new JButton("");
		buyButton.setPreferredSize(new Dimension(40, 20));
		buyButton.setOpaque(false);
		buyButton.setIcon(new ImageIcon(ProductStripe.class.getResource("/imat/resources/buyButtonMini.PNG")));
		buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));
		
		priceLabel = new JLabel("Desc");
		priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
		
		sumLabel = new JLabel("0,00 kr");
		sumLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
		sumLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		
		qSpinner = new JSpinner();
		qSpinner.addChangeListener(new QSpinnerChangeListener());
		qSpinner.setFont(new Font("SansSerif", Font.BOLD, 10));
		
		suffixLabel = new JLabel("st");
		suffixLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(priceLabel)
						.addComponent(titleLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(qSpinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(suffixLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(2)
							.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(sumLabel, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(titleLabel)
							.addComponent(suffixLabel)
							.addComponent(qSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(priceLabel)
						.addComponent(sumLabel))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		add(panel_1, BorderLayout.WEST);
		
		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_1.add(imageLabel);
		
		imageLabel.setIcon(IDH.getImageIcon(p, new Dimension(67, 50)));
		titleLabel.setText(p.getName());
		priceLabel.setText(format.format(p.getPrice()) + p.getUnit().substring(2));
		

		if (p.getUnitSuffix().equals("kg")) {
			qSpinner.setModel(kgModel);
			suffixLabel.setText(p.getUnitSuffix());
		} else {
			qSpinner.setModel(stModel);
			suffixLabel.setText("st");
		}
		updateSum();
	}
	
	public ShoppingItem getItem() {
		return new ShoppingItem(product,getAmount());
	}
	
	private double getAmount() {
		Object q = qSpinner.getValue();
		double newSum = product.getPrice();
		if (q instanceof Double) {
			newSum = newSum * (double) q;
		} else {
			newSum = newSum * (int) q;
		}
		return newSum;
	}

	public void updateSum() {
		sumLabel.setText(format.format(getAmount()));
	}
	public void addListener(PropertyChangeListener listener) {
		ps.addPropertyChangeListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		ps.firePropertyChange("buy", null, getItem());
	}
	private class QSpinnerChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			updateSum();
		}
	}
}
