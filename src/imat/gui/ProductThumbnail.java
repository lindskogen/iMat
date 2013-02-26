package imat.gui;

import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.GroupLayout;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.GroupLayout.Alignment;
import java.awt.Font;
import java.text.DecimalFormat;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.JSpinner;
import javax.swing.JButton;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;

public class ProductThumbnail extends JPanel {
	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	SpinnerModel stModel = new SpinnerNumberModel(new Integer(1),
			new Integer(1), null, new Integer(1));
	SpinnerModel kgModel = new SpinnerNumberModel(new Double(0.1), new Double(
			0.1), null, new Double(0.1));
	private JPanel panel;
	private JLabel titleLabel;
	private JLabel priceLabel;
	private JSpinner qSpinner;
	private JLabel sumLabel;
	private JButton buyButton;
	private JLabel suffixLabel;
	private Product product;
	private JPanel panel_1;
	private JLabel imageLabel;

	/**
	 * Create the panel.
	 */
	public ProductThumbnail(Product p, boolean featured) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		product = p;
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		
		panel_1 = new JPanel();
		add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		imageLabel = new JLabel("");
		panel_1.add(imageLabel);

		panel = new JPanel();
		panel.setBorder(null);
		add(panel);

		titleLabel = new JLabel("Titel");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

		priceLabel = new JLabel("Price");
		priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

		qSpinner = new JSpinner();

		sumLabel = new JLabel("Sum");

		buyButton = new JButton("Köp");

		suffixLabel = new JLabel("Suf");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING, false)
												.addComponent(
														titleLabel,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.DEFAULT_SIZE,
														Short.MAX_VALUE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.TRAILING,
																				false)
																				.addComponent(
																						qSpinner,
																						Alignment.LEADING,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						priceLabel,
																						Alignment.LEADING,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						Short.MAX_VALUE))
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		sumLabel)))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(suffixLabel)
								.addPreferredGap(ComponentPlacement.RELATED,
										GroupLayout.DEFAULT_SIZE,
										Short.MAX_VALUE)
								.addComponent(buyButton).addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addComponent(titleLabel)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(priceLabel)
								.addPreferredGap(ComponentPlacement.RELATED,
										34, Short.MAX_VALUE)
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.BASELINE)
												.addComponent(
														qSpinner,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(sumLabel)
												.addComponent(buyButton)
												.addComponent(suffixLabel))
								.addContainerGap()));
		panel.setLayout(gl_panel);
		// Set labels and image according to the product
		titleLabel.setText(p.getName());
		priceLabel.setText(p.getPrice() + " " + p.getUnit());
		if (featured) {
			imageLabel.setPreferredSize(new Dimension(200, 150));
			imageLabel.setIcon(IDH.getImageIcon(p, new Dimension(200, 150)));
		} else {
			imageLabel.setIcon(IDH.getImageIcon(p, new Dimension(140, 105)));
		}

		if (p.getUnitSuffix().equals("kg")) {
			qSpinner.setModel(kgModel);
			suffixLabel.setText(p.getUnitSuffix());
		} else {
			qSpinner.setModel(stModel);
			suffixLabel.setText("st");
		}
		// if(featured) {
		// this.setPreferredSize(new Dimension(844,128));
		// }
		updateSum();
	}

	public void updateSum() {
		Object q = qSpinner.getValue();
		double newSum = product.getPrice();
		if (q instanceof Double) {
			newSum = newSum * (double) q;
		} else {
			newSum = newSum * (int) q;
		}
		DecimalFormat d = new DecimalFormat("#.00");
		sumLabel.setText(d.format(newSum) + " kr");
	}

	private class QSpinnerChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			updateSum();
		}
	}

}
