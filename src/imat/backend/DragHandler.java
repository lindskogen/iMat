package imat.backend;

import imat.gui.ProductThumbnail;

import javax.swing.TransferHandler;

public class DragHandler extends TransferHandler {
	@Override
	public boolean importData(TransferSupport support) {
		if (!canImport(support)) {
			return false;
		}
		Object transferable = support.getTransferable();
		if (transferable instanceof ProductThumbnail) {
			ProductThumbnail p = (ProductThumbnail) transferable;
			// cart.add(p.getItem());
			return true;
		}
		return false;
	}
}
