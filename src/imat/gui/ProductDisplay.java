package imat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class ProductDisplay extends JPanel implements ActionListener {
	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	SpinnerModel stModel = new SpinnerNumberModel(new Integer(1),
			new Integer(1), null, new Integer(1));
	SpinnerModel kgModel = new SpinnerNumberModel(new Double(0.1), new Double(
			0.1), null, new Double(0.1));
	private Product product;
	private JPanel panel;
	private JLabel titleLabel;
	private JSpinner qSpinner;
	private JLabel suffixLabel;
	private JButton buyButton;
	private JLabel sumLabel;
	private JLabel priceLabel;
	private JPanel panel_1;
	private JLabel imageLabel;
	
	private ActionListener listener;

	private static NumberFormat format = NumberFormat
			.getCurrencyInstance(Locale.forLanguageTag("sv-SE"));

	/**
	 * Create the panel.
	 */
	public ProductDisplay(Product p, boolean featured, boolean list, ActionListener al) {
		setBackground(Color.WHITE);
		listener = al;
		product = p;
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);

		if (list) {
			setPreferredSize(new Dimension(900, 52));
			titleLabel = new JLabel("Label");
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

			buyButton = new JButton("");
			buyButton.setPreferredSize(new Dimension(40, 20));
			buyButton.setOpaque(false);
			buyButton.setIcon(new ImageIcon(ProductStripe.class
					.getResource("/imat/resources/buyButtonMini.PNG")));
			buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));
			buyButton.setActionCommand("buy");
			buyButton.addActionListener(this);

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
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel
					.createParallelGroup(Alignment.LEADING)
					.addGroup(
							gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.LEADING)
													.addComponent(priceLabel)
													.addComponent(titleLabel))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.TRAILING)
													.addGroup(
															gl_panel.createSequentialGroup()
																	.addComponent(
																			qSpinner,
																			GroupLayout.PREFERRED_SIZE,
																			48,
																			GroupLayout.PREFERRED_SIZE)
																	.addGap(3)
																	.addComponent(
																			suffixLabel,
																			GroupLayout.PREFERRED_SIZE,
																			26,
																			GroupLayout.PREFERRED_SIZE)
																	.addGap(2)
																	.addComponent(
																			buyButton,
																			GroupLayout.PREFERRED_SIZE,
																			GroupLayout.DEFAULT_SIZE,
																			GroupLayout.PREFERRED_SIZE))
													.addComponent(
															sumLabel,
															GroupLayout.DEFAULT_SIZE,
															776,
															Short.MAX_VALUE))
									.addContainerGap()));
			gl_panel.setVerticalGroup(gl_panel
					.createParallelGroup(Alignment.LEADING)
					.addGroup(
							gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.TRAILING)
													.addGroup(
															gl_panel.createParallelGroup(
																	Alignment.BASELINE)
																	.addComponent(
																			titleLabel)
																	.addComponent(
																			suffixLabel)
																	.addComponent(
																			qSpinner,
																			GroupLayout.PREFERRED_SIZE,
																			GroupLayout.DEFAULT_SIZE,
																			GroupLayout.PREFERRED_SIZE))
													.addComponent(
															buyButton,
															GroupLayout.PREFERRED_SIZE,
															GroupLayout.DEFAULT_SIZE,
															GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.BASELINE)
													.addComponent(priceLabel)
													.addComponent(sumLabel))
									.addContainerGap(GroupLayout.DEFAULT_SIZE,
											Short.MAX_VALUE)));
		} else {
			titleLabel = new JLabel("Label");
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

			qSpinner = new JSpinner();
			qSpinner.setFont(new Font("SansSerif", Font.BOLD, 12));
			qSpinner.addChangeListener(new QSpinnerChangeListener());

			suffixLabel = new JLabel("st");
			suffixLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

			buyButton = new JButton("");
			buyButton.setMaximumSize(new Dimension(60, 30));
			buyButton.setMinimumSize(new Dimension(60, 30));
			buyButton.setPreferredSize(new Dimension(60, 30));
			buyButton.setOpaque(false);
			buyButton.setIcon(new ImageIcon(ProductDisplay.class
					.getResource("/imat/resources/buyButton60x30.PNG")));
			buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));

			sumLabel = new JLabel("Sum");
			sumLabel.setHorizontalAlignment(SwingConstants.RIGHT);

			priceLabel = new JLabel("Desc");
			priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(gl_panel
					.createParallelGroup(Alignment.LEADING)
					.addGroup(
							gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.LEADING)
													.addComponent(titleLabel)
													.addGroup(
															gl_panel.createSequentialGroup()
																	.addComponent(
																			qSpinner,
																			GroupLayout.PREFERRED_SIZE,
																			48,
																			GroupLayout.PREFERRED_SIZE)
																	.addPreferredGap(
																			ComponentPlacement.RELATED)
																	.addComponent(
																			suffixLabel)
																	.addPreferredGap(
																			ComponentPlacement.RELATED)
																	.addComponent(
																			sumLabel,
																			GroupLayout.DEFAULT_SIZE,
																			108,
																			Short.MAX_VALUE)
																	.addPreferredGap(
																			ComponentPlacement.RELATED)
																	.addComponent(
																			buyButton,
																			GroupLayout.PREFERRED_SIZE,
																			GroupLayout.DEFAULT_SIZE,
																			GroupLayout.PREFERRED_SIZE))
													.addComponent(priceLabel))
									.addContainerGap()));
			gl_panel.setVerticalGroup(gl_panel
					.createParallelGroup(Alignment.TRAILING)
					.addGroup(
							gl_panel.createSequentialGroup()
									.addContainerGap()
									.addGroup(
											gl_panel.createParallelGroup(
													Alignment.TRAILING)
													.addComponent(
															buyButton,
															GroupLayout.PREFERRED_SIZE,
															GroupLayout.DEFAULT_SIZE,
															GroupLayout.PREFERRED_SIZE)
													.addGroup(
															gl_panel.createSequentialGroup()
																	.addComponent(
																			titleLabel)
																	.addPreferredGap(
																			ComponentPlacement.RELATED)
																	.addComponent(
																			priceLabel)
																	.addPreferredGap(
																			ComponentPlacement.RELATED,
																			37,
																			Short.MAX_VALUE)
																	.addGroup(
																			gl_panel.createParallelGroup(
																					Alignment.BASELINE)
																					.addComponent(
																							qSpinner,
																							GroupLayout.PREFERRED_SIZE,
																							GroupLayout.DEFAULT_SIZE,
																							GroupLayout.PREFERRED_SIZE)
																					.addComponent(
																							suffixLabel)
																					.addComponent(
																							sumLabel))))
									.addContainerGap()));
		}

		panel.setLayout(gl_panel);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		add(panel_1, BorderLayout.WEST);

		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_1.add(imageLabel);

		// Set labels and image according to the product
		titleLabel.setText(p.getName());
		priceLabel.setText(format.format(p.getPrice())
				+ p.getUnit().substring(2));

		if (list) {
			imageLabel.setIcon(IDH.getImageIcon(p, new Dimension(67, 50)));
		} else {
			if (featured) {
				titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
				priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
				imageLabel
						.setIcon(IDH.getImageIcon(p, new Dimension(200, 150)));
			} else {
				imageLabel
						.setIcon(IDH.getImageIcon(p, new Dimension(140, 105)));
			}
		}

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
		return new ShoppingItem(product, getAmount());
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

	private class QSpinnerChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			updateSum();
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("buy")) {
			evt.setSource(getItem());
			listener.actionPerformed(evt);
		}
	}
}
