package imat.backend;

import imat.gui.TabbedView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class BackendController implements PropertyChangeListener {
	
	private ShopModel model;
	private TabbedView tView;
	
	public BackendController(ShopModel model, TabbedView view) {
		this.model = model;
		tView = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("buy")) {
			Object o = evt.getNewValue();
			if (o instanceof ShoppingItem) {
				model.addToCart((ShoppingItem)o);
			}
		}
	}

}
