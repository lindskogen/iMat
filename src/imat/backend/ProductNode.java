package imat.backend;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import org.jdesktop.swingx.treetable.AbstractMutableTreeTableNode;

import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductNode extends AbstractMutableTreeTableNode implements ActionListener {
	
	private ShoppingItem item;
	
	private final String ADD = "cart";
	private final String DEL = "delete";
	
	private final ImageIcon BTN_BUY = new ImageIcon(ListNode.class.getResource("/imat/resources/buyButton60x30.PNG"));
	private final ImageIcon BTN_DEL = new ImageIcon(ListNode.class.getResource("/imat/resources/delete.PNG"));
	
	public ProductNode(ShoppingItem s) {
		super();
		item = s;
	}
	
	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int column) {
		switch(column) {
		case 0:
			return item.getProduct().getName();
		case 1:
			double amount = item.getAmount();
			if (Math.round(amount) == amount) {
				return (int)amount;				
			} else {
				return amount;
			}
		case 2:
			return item.getTotal();
		case 3:
			return new JButton();
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
			System.out.println("ADD: " + item.getProduct().getName());
		} else if (event.getActionCommand().equals(DEL)) {
			System.out.println("DEL: " + item.getProduct().getName());
		}		
	}
}
