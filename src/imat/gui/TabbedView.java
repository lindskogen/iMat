package imat.gui;

import imat.backend.ListNode;
import imat.backend.ProductList;
import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class TabbedView extends JPanel implements PropertyChangeListener {
	private static final long serialVersionUID = 1L;
	private JXTreeTable shoppingBasket;
	private JXTreeTable lists;
	private JXTreeTable history;
	private JLabel totalSum;
	
	private ShopModel model;
	
	private final ImageIcon LIST_ICN = new ImageIcon(TabbedView.class.getResource("/imat/resources/menuListIcon.PNG"));
	
	/**
	 * Create the panel.
	 */
	public TabbedView(ShopModel model) {
		this.model = model;
		setPreferredSize(new Dimension(350, 400));
		setLayout(new BorderLayout());
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel basketPanel = new JPanel();
		tabbedPane.addTab("Varukorg", null, basketPanel, null);
		basketPanel.setLayout(new BorderLayout());
		
		shoppingBasket = new JXTreeTable();
		shoppingBasket.setClosedIcon(LIST_ICN);
		shoppingBasket.setOpenIcon(LIST_ICN);
		shoppingBasket.setLeafIcon(null);
		
		JScrollPane basketScroll = new JScrollPane(shoppingBasket);
		basketPanel.add(basketScroll, BorderLayout.CENTER);
		
		JPanel panel_1 = new JPanel();
		basketPanel.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel_1.add(panel, BorderLayout.SOUTH);
		
		JButton createListBtn = new JButton("Skapa Lista");
		panel.add(createListBtn);
		
		JButton toCheckoutBtn = new JButton("Till Kassan");
		panel.add(toCheckoutBtn);
		
		JPanel panel_2 = new JPanel();
		panel_2.setAlignmentX(Component.RIGHT_ALIGNMENT);
		panel_1.add(panel_2, BorderLayout.EAST);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		totalSum = new JLabel("Summa: XX,00 kr");
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
		
		lists = new JXTreeTable();
		lists.setClosedIcon(LIST_ICN);
		lists.setOpenIcon(LIST_ICN);
		lists.setLeafIcon(null);
		
		JScrollPane listScroll = new JScrollPane(lists);
		listPanel.add(listScroll, BorderLayout.CENTER);
		
		JPanel historyPanel = new JPanel();
		tabbedPane.addTab("Historik", null, historyPanel, null);
		historyPanel.setLayout(new BorderLayout());
		
		history = new JXTreeTable();
		history.setClosedIcon(LIST_ICN);
		history.setOpenIcon(LIST_ICN);
		history.setLeafIcon(null);
		
		JScrollPane historyScroll = new JScrollPane(lists);
		listPanel.add(historyScroll, BorderLayout.CENTER);
	}
	
	private TreeTableModel toModel(ListNode l) {
		ArrayList<String> headers = new ArrayList<String>();
		headers.add("Namn");
		headers.add("Antal");
		headers.add("Pris");
		headers.add("");
		headers.add("");
		
		return new DefaultTreeTableModel(l, headers);
	}
	
	private void setShoppingBasket(ProductList list) {
		shoppingBasket.setTreeTableModel(toModel(new ListNode(list, model)));
		formatColumns(shoppingBasket.getColumnModel());
		shoppingBasket.addMouseListener(new ButtonMouseListener(shoppingBasket));
		totalSum.setText("Summa: " + NumberFormat.getCurrencyInstance(Locale.forLanguageTag("sv-SE")).format(list.getPrice()));
		revalidate();
	}
	private void setLists(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		lists.setTreeTableModel(toModel(root));
		formatColumns(lists.getColumnModel());
		lists.addMouseListener(new ButtonMouseListener(lists));
		revalidate();
	}
	private void setHistory(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		history.setTreeTableModel(toModel(root));
		formatColumns(history.getColumnModel());
		history.addMouseListener(new ButtonMouseListener(history));
		revalidate();
	}
	private void formatColumns(TableColumnModel m) {
		m.getColumn(1).setMaxWidth(40);
		m.getColumn(2).setMaxWidth(120);
		m.getColumn(3).setMaxWidth(30);
		m.getColumn(4).setMaxWidth(30);
		
		m.getColumn(1).setCellRenderer(NumberFormatter.getNumber());
		m.getColumn(2).setCellRenderer(NumberFormatter.getCurrency("sv-SE"));
		
		JButtonRenderer renderer = new JButtonRenderer();
		m.getColumn(3).setCellRenderer(renderer);
		if (m.getColumn(4) != null) {
			m.getColumn(4).setCellRenderer(renderer);
		}
	}
	
	private static class NumberFormatter extends DefaultTableCellRenderer {
		private Format formatter;
		
		private NumberFormatter(Format formatter) {
			this.formatter = formatter;
			setHorizontalAlignment(SwingConstants.RIGHT);
		}
		public void setValue(Object value) {
			try {
				if (value != null) {
					value = formatter.format(value);
				}
			} catch (IllegalArgumentException e) {
					e.printStackTrace();
			}
			super.setValue(value);
		}
		public static NumberFormatter getCurrency(String locale) {
			return new NumberFormatter(NumberFormat.getCurrencyInstance(Locale.forLanguageTag(locale)));
		}
		public static NumberFormatter getNumber() {
			return new NumberFormatter(NumberFormat.getNumberInstance());
		}
	}
	private static class JButtonRenderer implements TableCellRenderer {

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			JButton button = (JButton) value;
			button.setBorderPainted(false);
			if (isSelected) {
				button.setForeground(table.getSelectionForeground());
				button.setBackground(table.getSelectionBackground());
			} else {
				button.setForeground(table.getForeground());
				button.setBackground(UIManager.getColor("Button.background"));
			}
			return button;
		}	
	}
	private class ButtonMouseListener extends MouseAdapter {
		private final JTable table;
		
		private ButtonMouseListener(JTable table) {
			this.table = table;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			fireEvent(e);
		}
		private void fireEvent(MouseEvent e) {
			int column = table.getColumnModel().getColumnIndexAtX(e.getX());
			int row = e.getY()/table.getRowHeight(); 

			if (row < table.getRowCount() && row >= 0 && column < table.getColumnCount() && column >= 0) {
			    Object value = table.getValueAt(row, column);
			    if (value instanceof JButton) {
			    	JButton btn = (JButton) value;
			    	btn.doClick();
			    }
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "cart":
			setShoppingBasket(model.getProductCart());
			break;
		case "history":
			setHistory(model.getHistoryLists());
			break;
		case "lists":
			setLists(model.getLists());
			break;
		default:
			return;
		}
		revalidate();
	}
}
