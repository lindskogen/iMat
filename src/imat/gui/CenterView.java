package imat.gui;

import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class CenterView extends JPanel implements ActionListener, PropertyChangeListener {
	private JPanel ntfyPanel;
	private JPanel cardPanel;

	private ShopModel model;

	private final Dimension NTFY_CLOSED = new Dimension(0,0);
	private final Dimension NTFY_OPEN = new Dimension(0,30);

	private final String AC_NTFY_CLOSE = "closeNtfy";

	/**
	 * Create the panel.
	 * @param productsView
	 */
	public CenterView(ShopModel model, ProductsView productsView, Checkout checkout) {
		this.model = model;
		model.addPropertyChangeListener(this);
		setLayout(new BorderLayout());

		cardPanel = new JPanel();
		add(cardPanel);
		cardPanel.setLayout(new CardLayout());

		cardPanel.add(productsView, "switchProducts");
		cardPanel.add(checkout, "switchCheckout");

		ntfyPanel = new JPanel();
		ntfyPanel.setLayout(new BorderLayout());
		ntfyPanel.setBackground(Color.YELLOW);
		add(ntfyPanel, BorderLayout.NORTH);
		ntfyPanel.setPreferredSize(NTFY_CLOSED);

	}

	public void showNotification(JComponent content) {
		ntfyPanel.removeAll();
		JButton closeBtn = new JButton(new ImageIcon(CenterView.class.getResource("/imat/resources/deletehover.PNG")));
		closeBtn.setActionCommand("closeNtfy");
		closeBtn.setBorderPainted(false);
		closeBtn.setOpaque(false);
		closeBtn.setContentAreaFilled(false);
		closeBtn.addActionListener(this);
		ntfyPanel.add(closeBtn, BorderLayout.EAST);
		content.setOpaque(false);
		ntfyPanel.add(content, BorderLayout.CENTER);
		ntfyPanel.setPreferredSize(NTFY_OPEN);
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals(AC_NTFY_CLOSE)) {
			closeNotification();
		}
	}

	public void closeNotification() {
		ntfyPanel.setPreferredSize(NTFY_CLOSED);
		revalidate();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "notify":
			showNotification((JComponent) evt.getNewValue());
			break;
		case "unNotify":
			closeNotification();
			break;
		case "switch":
			CardLayout cl = (CardLayout) cardPanel.getLayout();
			cl.show(cardPanel, (String)evt.getNewValue());
			break;
		}
	}

}
