package imat.backend;

import java.awt.datatransfer.DataFlavor;

import javax.swing.JTree;
import javax.swing.TransferHandler;

public class DragHandler extends TransferHandler {
	@Override
	public boolean canImport(TransferHandler.TransferSupport support) {
        if (!support.isDataFlavorSupported(DataFlavor.stringFlavor) ||
            !support.isDrop()) {
          return false;
        }

        JTree.DropLocation dropLocation =
          (JTree.DropLocation)support.getDropLocation();

        return dropLocation.getPath() != null;
      }
}
