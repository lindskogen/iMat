package imat.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.NumberFormat;
import java.util.Locale;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.TransferHandler;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductThumbnail extends JPanel implements ActionListener, Transferable, DragSourceListener, DragGestureListener {
	private static final IMatDataHandler IDH = IMatDataHandler.getInstance();
	SpinnerModel stModel = new SpinnerNumberModel(new Integer(1),
			new Integer(1), null, new Integer(1));
	SpinnerModel kgModel = new SpinnerNumberModel(new Double(0.1), new Double(
			0.1), null, new Double(0.1));
	private Product product;
	private JPanel panel;
	private JLabel titleLabel;
	private JSpinner qSpinner;
	private JLabel suffixLabel;
	private JButton buyButton;
	private JLabel sumLabel;
	private JLabel priceLabel;
	private JPanel panel_1;
	private JLabel imageLabel;
	
	private DragSource source;
	private TransferHandler handler;
	
	private PropertyChangeSupport ps;

	private static NumberFormat format = NumberFormat
			.getCurrencyInstance(Locale.forLanguageTag("sv-SE"));

	/**
	 * Create the panel.
	 */
	public ProductThumbnail(Product p, boolean featured) {
		setBackground(Color.WHITE);

		ps = new PropertyChangeSupport(this);

		product = p;

		setPreferredSize(new Dimension(300, 126));
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);

		titleLabel = new JLabel("Label");
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

		qSpinner = new JSpinner();
		qSpinner.setFont(new Font("SansSerif", Font.BOLD, 12));
		qSpinner.addChangeListener(new QSpinnerChangeListener());

		suffixLabel = new JLabel("st");
		suffixLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

		buyButton = new JButton("");
		buyButton.setMaximumSize(new Dimension(60, 30));
		buyButton.setMinimumSize(new Dimension(60, 30));
		buyButton.setPreferredSize(new Dimension(60, 30));
		buyButton.setOpaque(false);
		buyButton.setIcon(new ImageIcon(ProductThumbnail.class
				.getResource("/imat/resources/buyButton60x30.PNG")));
		buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));

		sumLabel = new JLabel("Sum");
		sumLabel.setHorizontalAlignment(SwingConstants.RIGHT);

		priceLabel = new JLabel("Desc");
		priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel
				.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.LEADING)
												.addComponent(titleLabel)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		qSpinner,
																		GroupLayout.PREFERRED_SIZE,
																		48,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		suffixLabel)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		sumLabel,
																		GroupLayout.DEFAULT_SIZE,
																		108,
																		Short.MAX_VALUE)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		buyButton,
																		GroupLayout.PREFERRED_SIZE,
																		GroupLayout.DEFAULT_SIZE,
																		GroupLayout.PREFERRED_SIZE))
												.addComponent(priceLabel))
								.addContainerGap()));
		gl_panel.setVerticalGroup(gl_panel
				.createParallelGroup(Alignment.TRAILING)
				.addGroup(
						gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_panel.createParallelGroup(
												Alignment.TRAILING)
												.addComponent(
														buyButton,
														GroupLayout.PREFERRED_SIZE,
														GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addGroup(
														gl_panel.createSequentialGroup()
																.addComponent(
																		titleLabel)
																.addPreferredGap(
																		ComponentPlacement.RELATED)
																.addComponent(
																		priceLabel)
																.addPreferredGap(
																		ComponentPlacement.RELATED,
																		37,
																		Short.MAX_VALUE)
																.addGroup(
																		gl_panel.createParallelGroup(
																				Alignment.BASELINE)
																				.addComponent(
																						qSpinner,
																						GroupLayout.PREFERRED_SIZE,
																						GroupLayout.DEFAULT_SIZE,
																						GroupLayout.PREFERRED_SIZE)
																				.addComponent(
																						suffixLabel)
																				.addComponent(
																						sumLabel))))
								.addContainerGap()));
		panel.setLayout(gl_panel);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		add(panel_1, BorderLayout.WEST);

		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_1.add(imageLabel);

		// Set labels and image according to the product
		titleLabel.setText(p.getName());
		priceLabel.setText(format.format(p.getPrice())
				+ p.getUnit().substring(2));

		if (featured) {
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
			priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
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
		updateSum();
		
		//handler = new DragHandler();
		//setTransferHandler(handler);
		
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				ProductThumbnail pt = (ProductThumbnail) e.getSource();
				TransferHandler handle = pt.getTransferHandler();
				handle.exportAsDrag(pt, e, TransferHandler.COPY);
			}
		});
	}

	public ShoppingItem getItem() {
		return new ShoppingItem(product, getAmount());
	}

	private double getAmount() {
		Object q = qSpinner.getValue();
		if (q instanceof Double) {
			return (double) q;
		} else {
			return (int) q;
		}
	}
	
	private double getTotal() {
		return getAmount()*product.getPrice();
	}

	public void updateSum() {
		sumLabel.setText(format.format(getTotal()));
	}

	private class QSpinnerChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent arg0) {
			updateSum();
		}
	}

	public void addListener(PropertyChangeListener listener) {
		ps.addPropertyChangeListener(listener);
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		ps.firePropertyChange("buy", null, getItem());
	}

	@Override
	public Object getTransferData(DataFlavor flavor) {
		return this;
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return null;
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return true;
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		source.startDrag(dge, DragSource.DefaultCopyDrop, this, this);
		System.out.println(getItem().getProduct().getName() + " " + getItem().getAmount());
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {}
	public void dragEnter(DragSourceDragEvent dsde) {}
	public void dragExit(DragSourceEvent dse) {}
	public void dragOver(DragSourceDragEvent dsde) {}
	public void dropActionChanged(DragSourceDragEvent dsde) {}
}
