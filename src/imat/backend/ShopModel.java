package imat.backend;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ShopModel {
	private static List<ProductList> userLists = new LinkedList<ProductList>();
	private static List<ProductList> historyLists = new LinkedList<ProductList>();
	private static ProductList cart = new ProductList("Cart");
	private static IMatDataHandler imat = IMatDataHandler.getInstance();
	private ShoppingCart sCart;

	private PropertyChangeSupport pcs;
	
	public ShopModel() {
		pcs = new PropertyChangeSupport(this);
		sCart = imat.getShoppingCart();
		readList("/lists.txt", userLists);
		readList("/history.txt", historyLists);
	}
	private boolean readList(String path, List<ProductList> target) {
		File listFile = new File(imat.imatDirectory() + path);
		if (listFile.exists()) {
			try {
				FileInputStream in = new FileInputStream(listFile);

				String fileData;
				BufferedReader reader = new BufferedReader(new InputStreamReader(in));
				while (reader.ready()) {
					fileData = reader.readLine();
					target.add(ProductList.parseString(fileData));
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
	
	public static void saveLists() {
		saveList("/lists.txt", userLists);
		saveList("/history.txt", historyLists);
	}
	
	private static void saveList(String path, List<ProductList> lists) {
		File listFile = new File(imat.imatDirectory() + path);
		if (listFile.exists()) {
			try {
				FileOutputStream out = new FileOutputStream(listFile);
				Writer writer = new OutputStreamWriter(out);
				for (ProductList pList : lists) {
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
	
	public void fuzzySearch(String str) {
		Map<String, Integer> keywords = new HashMap<String,Integer>();
		int lowest = -1;
		for (Product p : imat.getProducts()) {
			int distance = StringUtils.getLevenshteinDistance(str.toLowerCase(), p.getName().toLowerCase(), str.length()/2 + 1);
			if (distance != -1) {
				keywords.put(p.getName(), distance);
				if (distance < lowest || lowest == -1) {
					lowest = distance;
				}
			}
		}
		ArrayList<String> res = new ArrayList<String>();
		for (String s : keywords.keySet()) {
			if (keywords.get(s) == lowest) {
				res.add(s);
			}
		}
		pcs.firePropertyChange("fuzzySearch", null, res);
	}
	
	public ShoppingCart getShoppingCart() {
		sCart.clear();
		for (ShoppingItem sItem : getProductCart()) {			
			sCart.addItem(sItem);
		}
		return sCart;
	}
	
	public ProductList getProductCart() {
		return cart;
	}
	
	public void addToCart(ShoppingItem item) {
		cart.add(item);
		pcs.firePropertyChange("cart", null, cart);
	}
	public void addToCart(ProductList pList) {
		for (ShoppingItem sItem : pList) {
			cart.add(sItem);
		}
		pcs.firePropertyChange("cart", null, cart);
	}
	
	public void addPropertyChangeListeter(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListeter(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	public void delete(ProductList pList, Product product) {
		for (ShoppingItem item : pList) {
			if (item.getProduct().equals(product)) {				
				pList.remove(item);
			}
		}
	}
	public void delete(ProductList pList) {
		cart.remove(pList);
	}

	public List<ProductList> getHistoryLists() {
		return historyLists;
	}

	public List<ProductList> getLists() {
		return userLists;
	}
}
