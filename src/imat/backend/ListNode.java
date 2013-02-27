package imat.backend;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import se.chalmers.ait.dat215.project.ShoppingItem;

public class ListNode extends AbstractMutableTreeTableNode implements ActionListener {
	
	private ProductList list;
	
	private final String ADD = "cart";
	private final String DEL = "delete";
	
	private final ImageIcon BTN_BUY = new ImageIcon(ListNode.class.getResource("/imat/resources/buyButtonMini.PNG"));
	private final ImageIcon BTN_DEL = new ImageIcon(ListNode.class.getResource("/imat/resources/delete.PNG"));
	
	public ListNode() {
		super();
	}
	
	public ListNode(ProductList theList) {
		this();
		list = theList;
		for (ShoppingItem s : list) {
			add(new ProductNode(s));
		}
	}
	
	@Override
	public int getColumnCount() {
		return 5;
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
			toCart.setActionCommand(ADD);
			toCart.addActionListener(this);
			return toCart;
		case 4:
			JButton delete = new JButton(BTN_DEL);
			delete.setActionCommand(DEL);
			delete.addActionListener(this);
			return delete;
		}
		throw new IndexOutOfBoundsException();
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		if (event.getActionCommand().equals(ADD)) {
			System.out.println("ADD: " + list.getName());
		} else if (event.getActionCommand().equals(DEL)) {
			System.out.println("DEL: " + list.getName());
		}
	}
}
