package imat.gui;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.TreeTableModel;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class TabbedView extends JPanel {
	private static final long serialVersionUID = 1L;
	private JXTreeTable shoppingBasket;
	private JXTreeTable lists;
	private JXTreeTable history;

	/**
	 * Create the panel.
	 */
	public TabbedView() {
		setLayout(new BorderLayout(0, 0));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane);
		
		JPanel basketPanel = new JPanel();
		tabbedPane.addTab("Varukorg", null, basketPanel, null);
		basketPanel.setLayout(new BorderLayout(0, 0));
		
		shoppingBasket = new JXTreeTable();
		basketPanel.add(shoppingBasket, BorderLayout.CENTER);
		
		JPanel panel = new JPanel();
		basketPanel.add(panel, BorderLayout.SOUTH);
		
		JButton createListBtn = new JButton("Skapa Lista");
		panel.add(createListBtn);
		
		JButton toCheckoutBtn = new JButton("Till Kassan");
		panel.add(toCheckoutBtn);
		
		JPanel listPanel = new JPanel();
		tabbedPane.addTab("Listor", null, listPanel, null);
		listPanel.setLayout(new BorderLayout(0, 0));
		
		lists = new JXTreeTable();
		listPanel.add(lists);
		
		JPanel historyPanel = new JPanel();
		tabbedPane.addTab("Historik", null, historyPanel, null);
		historyPanel.setLayout(new BorderLayout(0, 0));
		
		history = new JXTreeTable();
		historyPanel.add(history);
	}
	public void setShoppingBasket(TreeTableModel model) {
		shoppingBasket.setTreeTableModel(model);
	}
	public void setLists(TreeTableModel model) {
		lists.setTreeTableModel(model);
	}
	public void setHistory(TreeTableModel model) {
		history.setTreeTableModel(model);
	}
}
