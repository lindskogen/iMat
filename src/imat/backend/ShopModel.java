package imat.backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.LinkedList;
import java.util.List;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.ShoppingCart;

public class ShopModel {
	private List<ProductList> userLists = new LinkedList<ProductList>();
	private ProductList cart = new ProductList("Cart");
	private IMatDataHandler imat = IMatDataHandler.getInstance();
	private ShoppingCart sCart;

	public ShopModel() {
		sCart = imat.getShoppingCart();
		readLists();
	}
	private boolean readLists() {
		File listFile = new File(imat.imatDirectory() + "/lists.txt");
		if (listFile.exists()) {
			try {
				FileInputStream in = new FileInputStream(listFile);

				String fileData;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				while (reader.ready()) {
					fileData = reader.readLine();
					userLists.add(ProductList.parseString(fileData));
				}
				reader.close();
				in.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return true;
		}
		return false;
	}
	public void saveLists() {
		File listFile = new File(imat.imatDirectory() + "/lists.txt");
		if (listFile.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(listFile);
				Writer writer = new OutputStreamWriter(out);
				for (ProductList pList : userLists) {
					writer.write(pList.stringify() + "\n");
				}
				writer.close();
				out.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
