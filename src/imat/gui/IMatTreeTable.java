package imat.gui;

import imat.backend.ListNode;
import imat.backend.ProductList;

import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import javax.swing.tree.TreePath;

import org.jdesktop.swingx.JXTreeTable;
import org.jdesktop.swingx.treetable.DefaultTreeTableModel;
import org.jdesktop.swingx.treetable.TreeTableModel;

public class IMatTreeTable extends JXTreeTable implements DropTargetListener {

	private DropTarget dt;
	
	private static List<String> headers;
	private final static ImageIcon ICN_LIST = new ImageIcon(IMatTreeTable.class.getResource("/imat/resources/menuListIcon.PNG"));

	public IMatTreeTable(ListNode root, boolean canDrop) {
		super();

		if (canDrop) {
			dt = new DropTarget(this, this);
		}
		if (headers == null) {
			headers = new ArrayList<String>(5);
			headers.add("Namn");
			headers.add("Antal");
			headers.add("Pris");
			headers.add("");
			headers.add("");
		}
		setTreeTableModel(root);
	}

	public void dragEnter(DropTargetDragEvent arg0) {}
	public void dragExit(DropTargetEvent arg0) {}
	public void dragOver(DropTargetDragEvent arg0) {}
	public void dropActionChanged(DropTargetDragEvent arg0) {}

	private TreeTableModel toModel(ListNode l) {
		return new DefaultTreeTableModel(l, headers);
	}
	
	public void setTreeTableModel(ListNode root) {
		super.setTreeTableModel(toModel(root));

		setClosedIcon(ICN_LIST);
		setOpenIcon(ICN_LIST);
		setLeafIcon(null);

		TableColumnModel m = getColumnModel();

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
		addMouseListener(new ButtonMouseListener(this));
	}
	
	@Override
	public void drop(DropTargetDropEvent dtde) {
		Transferable tr = dtde.getTransferable();
		try {
			for (DataFlavor flavor : tr.getTransferDataFlavors()) {
				if (flavor.isFlavorSerializedObjectType()) {
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					Object o = tr.getTransferData(flavor);

					// TODO Handle drop!

					dtde.dropComplete(true);
				}
			}
		} catch (UnsupportedFlavorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean canImport(TransferHandler.TransferSupport support) {
//		if (!support.isDataFlavorSupported(new DataFlavor(DataFlavor.javaSerializedObjectMimeType)) || !support.isDrop()) {
//			return false;
//		}

		JTree.DropLocation dropLocation = (JTree.DropLocation)support.getDropLocation();

		return dropLocation.getPath() != null;
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
//			button.setOpaque(false);
			button.setContentAreaFilled(false);
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
}
