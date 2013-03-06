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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;

import org.apache.commons.lang3.StringUtils;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Order;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingCart;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ShopModel {
	private List<ProductList> userLists = new LinkedList<ProductList>();
	private List<ProductList> historyLists = new LinkedList<ProductList>();
	private ProductList undoList;
	private IMatDataHandler imat = IMatDataHandler.getInstance();
	private ShoppingCart sCart;
	
	private static ShopModel instance;

	private PropertyChangeSupport pcs;
	
	public static ShopModel getInstance() {
		if (instance == null) {
			instance = new ShopModel();
		}
		return instance;
	}
	
	private ShopModel() {
		pcs = new PropertyChangeSupport(this);
		sCart = imat.getShoppingCart();
		readList("lists.txt", userLists);
		historyLists = new LinkedList<ProductList>();
		List<Order> orders = imat.getOrders();
		Collections.sort(orders, new OrderSorter());
		for (Order o : orders) {
			ProductList pl = new ProductList(o.getItems(), o);
			pl.setName("Ordernr: " + o.getOrderNumber());
			historyLists.add(pl);
		}
	}
	private boolean readList(String path, List<ProductList> target) {
		File listFile = new File(imat.imatDirectory() + "/" + path);
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
	
	public void saveLists() {
		saveList("lists.txt", userLists);
	}
	
	private void saveList(String path, List<ProductList> lists) {
		File listFile = new File(imat.imatDirectory() + "/" + path);
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
	
	public List<String> fuzzySearch(String str) {
		long oldValue = System.currentTimeMillis();
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
		System.out.println(System.currentTimeMillis() - oldValue);
		pcs.firePropertyChange("fuzzySearch", null, res);
		return res;
	}
	
	public ShoppingCart getShoppingCart() {
		return sCart;
	}
	
	public ProductList getProductCart() {
		return new ProductList(sCart.getItems());
	}
	
	public void addToCart(ShoppingItem s) {
		boolean duplicate = false;
		for (ShoppingItem item : sCart.getItems()) {
		if(item.getProduct().equals(s.getProduct())) {
			item.setAmount(item.getAmount() + s.getAmount());
			duplicate = true;
			break;
		}
	}
		if (!duplicate) {
			sCart.addItem(s);
		}
		pcs.firePropertyChange("cart", null, getProductCart());
	}
	public void addToCart(ProductList pList) {
		for (ShoppingItem sItem : pList) {
			addToCart(sItem);
		}
		pcs.firePropertyChange("cart", null, getProductCart());
	}
	
	public void addPropertyChangeListener(PropertyChangeListener pcl) {
		pcs.addPropertyChangeListener(pcl);
	}
	
	public void removePropertyChangeListener(PropertyChangeListener pcl) {
		pcs.removePropertyChangeListener(pcl);
	}
	public void delete(ProductList pList, ShoppingItem item) {				
		if (userLists.contains(pList)) {
			pList.remove(item);
			pcs.firePropertyChange("lists", null, getLists());
		} else {
			sCart.removeItem(item);
			pcs.firePropertyChange("cart", null, getProductCart());
		}
	}
	public void undoDeleteList() {
		if (undoList != null) {
			userLists.add(undoList);
			undoList = null;
			pcs.firePropertyChange("lists", null, getLists());
		}
	}

	public void delete(ProductList pList) {
		if (userLists.remove(pList)) {
			undoList = pList;
			pcs.firePropertyChange("lists", null, getLists());
		} else if (historyLists.remove(pList)) {
			if (imat.getOrders().remove(pList.getOrder())) {
				System.out.println("Order deleted!");
			}
			pcs.firePropertyChange("history", null, getHistoryLists());
		} else {
			System.err.println("Tried to delete list: " + pList.getName());
		}
	}
	public void addList(ProductList pList) {
		ProductList tempList = pList.clone();
		if (tempList.getName().equals("unnamed")) {
			tempList.setName("Lista " + (userLists.size() + 1) );
		}
		userLists.add(tempList);
		pcs.firePropertyChange("lists", null, getLists());
	}

	public List<ProductList> getHistoryLists() {
		return historyLists;
	}

	public List<ProductList> getLists() {
		return userLists;
	}

	public void showNotification(JComponent component) {
		pcs.firePropertyChange("notify", null, component);
	}

	public void closeNotification() {
		pcs.firePropertyChange("unNotify", null, null);
	}

	public void switchCenter(String destination) {
		if (destination.equals("switchProducts")) {
			pcs.firePropertyChange("cart", null, getProductCart());
		}
		pcs.firePropertyChange("switch", null, destination);
	}
	private class OrderSorter implements Comparator<Order> {
		@Override
		public int compare(Order o1, Order o2) {
			return o2.getOrderNumber() - o1.getOrderNumber();
		}
	}
}
