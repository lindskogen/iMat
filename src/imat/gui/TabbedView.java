package imat.gui;

import imat.backend.ListNode;
import imat.backend.ProductList;
import imat.backend.ShopModel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class TabbedView extends JPanel implements PropertyChangeListener, ActionListener {
	private JXTreeTable shoppingBasket;
	private JXTreeTable lists;
	private JXTreeTable history;
	private JLabel totalSum;
	private JTabbedPane tabbedPane;
	
	private List<String> headers;
	
	private ShopModel model;
	
	private final ImageIcon LIST_ICN = new ImageIcon(TabbedView.class.getResource("/imat/resources/menuListIcon.PNG"));
	
	private final String AC_CART_LIST = "cartToList";
	
	/**
	 * Create the panel.
	 */
	public TabbedView(ShopModel model) {
		this.model = model;
		model.addPropertyChangeListeter(this);
		setPreferredSize(new Dimension(350, 400));
		setLayout(new BorderLayout());
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
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
		createListBtn.addActionListener(this);
		createListBtn.setActionCommand(AC_CART_LIST);
		
		JButton toCheckoutBtn = new JButton("Till Kassan");
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
		prepareTreeTable(shoppingBasket, new ListNode(list, model));
		totalSum.setText("Summa: " + NumberFormat.getCurrencyInstance(Locale.forLanguageTag("sv-SE")).format(list.getPrice()));
	}
	private void setLists(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		prepareTreeTable(lists, root);
	}
	private void setHistory(List<ProductList> list) {
		ListNode root = new ListNode();
		for (ProductList p : list) {
			root.add(new ListNode(p, model));
		}
		prepareTreeTable(history, root);
	}
	private void prepareTreeTable(JXTreeTable treetable, ListNode rootNode) {
		treetable.setEditingColumn(0);
		treetable.setEditingColumn(1);
		
		treetable.setTreeTableModel(toModel(rootNode));
		formatColumns(treetable.getColumnModel());
		treetable.addMouseListener(new ButtonMouseListener(treetable));
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
			    	((JButton) value).doClick();
			    } else if (value instanceof String && e.getClickCount() == 2) {
			    	if(table instanceof JXTreeTable) {
			    		JXTreeTable treeTable = (JXTreeTable) table;
			    		TreePath path = treeTable.getPathForLocation(e.getX(), e.getY());
			    		// TODO: get node somehow?
			    	}
			    }
			}
		}
	}
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		switch (evt.getPropertyName()) {
		case "cart":
			setShoppingBasket(model.getProductCart());
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
		}
	}
}
