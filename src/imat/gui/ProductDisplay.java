package imat.gui;

import imat.backend.DragHandler;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
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

public class ProductDisplay extends JPanel implements ActionListener, Transferable, DragSourceListener, DragGestureListener {
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
	private boolean list;

	private ActionListener listener;
	private JButton favButton;
	
	private DragSource source;
	private TransferHandler handler;
	private DataFlavor flavor;

	private PropertyChangeSupport ps;

	private static NumberFormat format = NumberFormat
			.getCurrencyInstance(Locale.forLanguageTag("sv-SE"));

	/**
	 * Create the panel.
	 */
	public ProductDisplay(Product p, boolean featured, boolean list,
			ActionListener al) {
		addMouseListener(new ThisMouseListener());
		setBackground(Color.WHITE);
		listener = al;
		product = p;
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));
		this.list = list;

		panel = new JPanel();
		panel.setBackground(Color.WHITE);
		add(panel, BorderLayout.CENTER);
		GroupLayout gl_panel = new GroupLayout(panel);

		if (list) {
			setPreferredSize(new Dimension(900, 52));
			titleLabel = new JLabel("Label");
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 12));

			buyButton = new JButton(new ImageIcon(ProductStripe.class
					.getResource("/imat/resources/buyButtonMini.PNG")));
			buyButton.setPreferredSize(new Dimension(40, 20));
			buyButton.setOpaque(false);
			buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));

			priceLabel = new JLabel("Desc");
			priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));

			sumLabel = new JLabel("0,00 kr");
			sumLabel.setFont(new Font("SansSerif", Font.BOLD, 10));
			sumLabel.setHorizontalAlignment(SwingConstants.RIGHT);

			qSpinner = new JSpinner();
			qSpinner.addChangeListener(new QSpinnerChangeListener());
			qSpinner.setFont(new Font("SansSerif", Font.BOLD, 10));
			

			suffixLabel = new JLabel("st");
			suffixLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));

			favButton = new JButton("");
			favButton.setPreferredSize(new Dimension(20, 20));
			favButton.setMinimumSize(new Dimension(20,20));
			favButton.addActionListener(this);
			favButton.setActionCommand("fav");
			favButton.setFont(new Font("Dialog", Font.BOLD, 10));
			if(IDH.isFavorite(product)) {
				favButton.setIcon(new ImageIcon(ProductDisplay.class
						.getResource("/imat/resources/favmini.PNG")));
				favButton.setToolTipText("Ta bort favorit");
			}else{
				favButton.setIcon(new ImageIcon(ProductDisplay.class
						.getResource("/imat/resources/unfavmini.PNG")));
				favButton.setToolTipText("Lägg till som favorit");
			}
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(priceLabel)
								.addComponent(titleLabel))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(favButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(qSpinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addGap(3)
									.addComponent(suffixLabel, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
									.addGap(2)
									.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(sumLabel, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE))
							.addContainerGap())
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
									.addComponent(favButton, 0, 0, Short.MAX_VALUE)
									.addGroup(Alignment.TRAILING, gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(titleLabel)
										.addComponent(suffixLabel)
										.addComponent(qSpinner))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(priceLabel)
								.addComponent(sumLabel))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		} else {
			titleLabel = new JLabel("Label");
			titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));

			qSpinner = new JSpinner();
			qSpinner.setFont(new Font("SansSerif", Font.BOLD, 12));
			qSpinner.addChangeListener(new QSpinnerChangeListener());

			suffixLabel = new JLabel("st");
			suffixLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

			buyButton = new JButton(new ImageIcon(ProductDisplay.class
					.getResource("/imat/resources/buyButton60x30.PNG")));
			buyButton.setMaximumSize(new Dimension(60, 30));
			buyButton.setMinimumSize(new Dimension(60, 30));
			buyButton.setPreferredSize(new Dimension(60, 30));
			buyButton.setOpaque(false);
			buyButton.setFont(new Font("SansSerif", Font.BOLD, 12));

			sumLabel = new JLabel("Sum");
			sumLabel.setHorizontalAlignment(SwingConstants.RIGHT);

			priceLabel = new JLabel("Desc");
			priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));

			favButton = new JButton("");
			favButton.setPreferredSize(new Dimension(32,32));
			favButton.setMinimumSize(new Dimension(32,32));

			favButton.addActionListener(this);
			favButton.setActionCommand("fav");
			if(IDH.isFavorite(product)) {
				favButton.setIcon(new ImageIcon(ProductDisplay.class
						.getResource("/imat/resources/fav.PNG")));
				favButton.setToolTipText("Ta bort favorit");
			}else{
				favButton.setIcon(new ImageIcon(ProductDisplay.class
						.getResource("/imat/resources/unfav.PNG")));
				favButton.setToolTipText("Lägg till som favorit");
			}
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
					gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(titleLabel)
								.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(qSpinner, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(suffixLabel)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(sumLabel, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(priceLabel)
											.addGap(103)))
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(favButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addContainerGap())
				);
				gl_panel.setVerticalGroup(
					gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(titleLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(priceLabel)
									.addPreferredGap(ComponentPlacement.RELATED, 27, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(favButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(qSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(suffixLabel)
									.addComponent(sumLabel))
								.addComponent(buyButton, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())
				);
		}

		Component[] tmp = qSpinner.getComponents();
		for(Component c: tmp) {
			c.addMouseListener(new ThisMouseListener());
		}
		favButton.addMouseListener(new ThisMouseListener());
		buyButton.addMouseListener(new ThisMouseListener());
		
		panel.setLayout(gl_panel);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.WHITE);
		add(panel_1, BorderLayout.WEST);

		imageLabel = new JLabel("");
		imageLabel.setBorder(new LineBorder(new Color(128, 128, 128), 2));
		panel_1.add(imageLabel);

		// Set labels and image according to the product
		titleLabel.setText(product.getName());
		priceLabel.setText(format.format(p.getPrice())
				+ p.getUnit().substring(2));
		
		
		// Add actions for the buy button
		buyButton.setActionCommand("buy");
		buyButton.addActionListener(this);
		
		
		// Drag and drop!
		
		handler = new DragHandler();
		try {
			flavor = new DataFlavor(DataFlavor.javaSerializedObjectMimeType + ";class=se.chalmers.ait.dat215.project.ShoppingItem");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		setTransferHandler(handler);
		
		source = new DragSource();
		source.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
		addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e) {
				ProductDisplay.this.getTransferHandler().exportAsDrag(ProductDisplay.this, e, TransferHandler.COPY);
			}
		});
		
		
		

		if (list) {
			imageLabel.setIcon(IDH.getImageIcon(p, new Dimension(67, 50)));
		} else {
			if (featured) {
//				this.setPreferredSize(new Dimension(300, 10));
				titleLabel.setFont(new Font("SansSerif", Font.BOLD, 27));
				priceLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
				imageLabel
						.setIcon(IDH.getImageIcon(p, new Dimension(200, 150)));
			} else {
				imageLabel
						.setIcon(IDH.getImageIcon(p, new Dimension(140, 105)));
			}
		}

		if (p.getUnitSuffix().equals("kg")) {
			qSpinner.setModel(kgModel);
			suffixLabel.setText(p.getUnitSuffix());
		} else {
			qSpinner.setModel(stModel);
			suffixLabel.setText("st");
		}
		favButton.setVisible(false);
		favButton.setContentAreaFilled(false);
		favButton.setBorderPainted(false);
		updateSum();
	}

	public ShoppingItem getItem() {
		return new ShoppingItem(product, getAmount());
	}

	private double getAmount() {
		Object q = qSpinner.getValue();
		return (q instanceof Double)?(double)q:(int)q;
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
	private class ThisMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setBackground(new Color(230, 230, 230));
			favButton.setVisible(true);
		}
		
		@Override
		public void mouseExited(MouseEvent e) {
			panel.setBackground(null);
			favButton.setVisible(false);
		}
	}

	@Override
	public void actionPerformed(ActionEvent evt) {
		if (evt.getActionCommand().equals("buy")) {
			evt.setSource(getItem());
			listener.actionPerformed(evt);
		}else if(evt.getActionCommand().equals("fav")) {
			if(IDH.isFavorite(product)) {
				IDH.removeFavorite(product);
				if(list) {
					favButton.setIcon(new ImageIcon(ProductDisplay.class
							.getResource("/imat/resources/unfavmini.PNG")));
				}else{
					favButton.setIcon(new ImageIcon(ProductDisplay.class
							.getResource("/imat/resources/unfav.PNG")));
				}
				favButton.setToolTipText("Lägg till som favorit");
				listener.actionPerformed(evt);
			}else{
				IDH.addFavorite(product);
				if(list) {
					favButton.setIcon(new ImageIcon(ProductDisplay.class
							.getResource("/imat/resources/favmini.PNG")));
				}else{
					favButton.setIcon(new ImageIcon(ProductDisplay.class
							.getResource("/imat/resources/fav.PNG")));
				}
				listener.actionPerformed(evt);
				favButton.setToolTipText("Ta bort som favorit");
			}
			listener.actionPerformed(evt);
		}
	}
	
	@Override
	public Object getTransferData(DataFlavor flavor) {
		if (flavor.equals(this.flavor)) {			
			return getItem();
		}
		throw new IllegalArgumentException();
	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return new DataFlavor[]{flavor};
	}

	@Override
	public boolean isDataFlavorSupported(DataFlavor flavor) {
		return flavor.equals(this.flavor);
	}

	@Override
	public void dragGestureRecognized(DragGestureEvent dge) {
		source.startDrag(dge, DragSource.DefaultCopyDrop, this, this);
		//System.out.println(getItem().getProduct().getName() + " " + getItem().getAmount());
	}

	public void dragDropEnd(DragSourceDropEvent dsde) {}
	public void dragEnter(DragSourceDragEvent dsde) {}
	public void dragExit(DragSourceEvent dse) {}
	public void dragOver(DragSourceDragEvent dsde) {}
	public void dropActionChanged(DragSourceDragEvent dsde) {}

}
