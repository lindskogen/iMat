package imat.gui;

import imat.backend.ListNode;
import imat.backend.ProductList;
import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class TabbedView extends JPanel implements PropertyChangeListener, ActionListener {
	private JXTreeTable shoppingBasket;
	private JXTreeTable lists;
	private JXTreeTable history;
	private JLabel totalSum;
	private JTabbedPane tabbedPane;
	private JButton createListBtn;
	private JButton toCheckoutBtn;
	
	private List<String> headers;
	
	private ShopModel model;
	
	private final String AC_CART_LIST = "cartToList";
	
	private final String AC_CHECKOUT = "switchCheckout";

	/**
	 * Create the panel.
	 */
	public TabbedView(ShopModel model) {
		this.model = model;
		model.addPropertyChangeListener(this);
		setPreferredSize(new Dimension(350, 400));
		setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel basketPanel = new JPanel();
		tabbedPane.addTab("Varukorg", null, basketPanel, null);
		basketPanel.setLayout(new BorderLayout());
		
		shoppingBasket = new IMatTreeTable(true);
		
		JScrollPane basketScroll = new JScrollPane(shoppingBasket);
		basketPanel.add(basketScroll, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		basketPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.SOUTH);
		
		createListBtn = new JButton("Skapa Lista");
		panel.add(createListBtn);
		createListBtn.addActionListener(this);
		createListBtn.setActionCommand(AC_CART_LIST);
		
		toCheckoutBtn = new JButton("Till Kassan");
		toCheckoutBtn.addActionListener(this);
		toCheckoutBtn.setActionCommand(AC_CHECKOUT);
		panel.add(toCheckoutBtn);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_1.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		totalSum = new JLabel("Summa: 00,00 kr");
		totalSum.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_2.add(totalSum);
		
		Component horizontalStrut = Box.createHorizontalStrut(20);
		horizontalStrut.setPreferredSize(new Dimension(10, 0));
		panel_2.add(horizontalStrut, BorderLayout.EAST);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		verticalStrut.setPreferredSize(new Dimension(0, 10));
		panel_2.add(verticalStrut, BorderLayout.NORTH);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		verticalStrut_1.setPreferredSize(new Dimension(0, 5));
		panel_2.add(verticalStrut_1, BorderLayout.SOUTH);
		
		JPanel listPanel = new JPanel();
		tabbedPane.addTab("Listor", null, listPanel, null);
		listPanel.setLayout(new BorderLayout());
		
		lists = new IMatTreeTable(true);
		
		JScrollPane listScroll = new JScrollPane(lists);
		listPanel.add(listScroll, BorderLayout.CENTER);
		
		JPanel historyPanel = new JPanel();
		tabbedPane.addTab("Historik", null, historyPanel, null);
		historyPanel.setLayout(new BorderLayout());
		
		history = new IMatTreeTable(false);
		
		JScrollPane historyScroll = new JScrollPane(lists);
		listPanel.add(historyScroll, BorderLayout.CENTER);
		
		headers = new ArrayList<String>(5);
		headers.add("Namn");
		headers.add("Antal");
		headers.add("Pris");
		headers.add("");
		headers.add("");
		
		setLists(model.getLists());
	}
	
	private TreeTableModel toModel(ListNode l) {
		return new DefaultTreeTableModel(l, headers);
	}
	
	private void setShoppingBasket(ProductList list) {
		shoppingBasket.setTreeTableModel(toModel(new ListNode(list, model)));
		totalSum.setText("Summa: " + NumberFormat.getCurrencyInstance(Locale.forLanguageTag("sv-SE")).format(list.getPrice()));
	}
	private void setLists(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		lists.setTreeTableModel(toModel(root));
	}
	private void setHistory(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		history.setTreeTableModel(toModel(root));
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "cart":
			ProductList pl = model.getProductCart();
			boolean itemsInCart = pl.size() > 0;
			createListBtn.setEnabled(itemsInCart);
			toCheckoutBtn.setEnabled(itemsInCart);
			setShoppingBasket(pl);
			tabbedPane.setSelectedIndex(0);
			break;
		case "history":
			setHistory(model.getHistoryLists());
			tabbedPane.setSelectedIndex(2);
			break;
		case "lists":
			setLists(model.getLists());
			tabbedPane.setSelectedIndex(1);
			break;
		default:
			return;
		}
		revalidate();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(AC_CART_LIST)) {
			model.addList(model.getProductCart());
			tabbedPane.setSelectedIndex(1);
			revalidate();
		} else if (e.getActionCommand().equals(AC_CHECKOUT)) {
			model.switchCenter(AC_CHECKOUT);
			revalidate();
		}
	}
}
