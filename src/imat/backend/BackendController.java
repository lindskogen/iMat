package imat.backend;

import imat.gui.TabbedView;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.ait.dat215.project.ShoppingItem;

public class BackendController implements PropertyChangeListener {
	
	private final TabbedView tView;
	
	public BackendController(TabbedView view) {
		tView = view;
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("buy")) {
			Object o = evt.getNewValue();
			if (o instanceof ShoppingItem) {
				//TODO code for adding a ShoppingItem to the cart
			}
		}
	}

}
