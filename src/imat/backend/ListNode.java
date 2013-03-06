package imat.backend;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import se.chalmers.ait.dat215.project.ShoppingItem;

public class ListNode extends AbstractMutableTreeTableNode implements ActionListener {
	
	private ProductList list;
	private ShopModel model;
	
	private final String AC_ADD = "cart";
	private final String AC_DEL = "delete";
	private final String AC_UNDO = "undo";
	
	private final ImageIcon BTN_BUY = new ImageIcon(ListNode.class.getResource("/imat/resources/buyButtonMini.PNG"));
	private final ImageIcon BTN_DEL = new ImageIcon(ListNode.class.getResource("/imat/resources/delete.PNG"));
	
	public ListNode() {
		super();
	}
	
	public ListNode(ProductList theList, ShopModel m) {
		this();
		list = theList;
		this.model = m;
		for (ShoppingItem s : list) {
			add(new ProductNode(s, model));
		}
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}
	
	public ProductList getList() {
		return list;
	}

	@Override
	public Object getValueAt(int column) {
		if (list == null) {
			return null;
		}
		switch(column) {
		case 0:
			return list.getName();
		case 1:
			return list.size();
		case 2:
			return list.getPrice();
		case 3:
			JButton toCart = new JButton(BTN_BUY);
			toCart.setActionCommand(AC_ADD);
			toCart.addActionListener(this);
			return toCart;
		case 4:
			JButton delete = new JButton(BTN_DEL);
			delete.setActionCommand(AC_DEL);
			delete.addActionListener(this);
			return delete;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		switch (event.getActionCommand()) {
		case AC_ADD:
			System.out.println("ADD: " + getList().getName());
			model.addToCart(getList());
			break;
		case AC_DEL:
			System.out.println("DEL: " + getList().getName());
			model.delete(getList());
			if (list.getName().matches("Ordernr: \\d+")) {
				break;
			}
			JPanel panel = new JPanel();
			panel.add(new JLabel("Du raderade \"" + getList().getName() + "\"!"));
			JButton btn = new JButton("Ångra");
			btn.setBackground(Color.YELLOW);
			btn.addActionListener(this);
			btn.setActionCommand(AC_UNDO);
			btn.setOpaque(false);
			panel.add(btn);
			model.showNotification(panel);
			break;
		case AC_UNDO:
			model.undoDeleteList();
			model.closeNotification();
			break;
		}
	}
}
