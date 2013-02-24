package imat.backend;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ShoppingItem;

public class ProductList implements Iterable<ShoppingItem> {
	private List<ShoppingItem> list;
    private String name;
    
    private static ShoppingItemSorter ps = new ShoppingItemSorter();
    
    
    public ProductList(String n) {
        list = new LinkedList<ShoppingItem>();
        name = n;
    }
    
    public static ProductList parseString(String s) {
        Scanner sc = new Scanner(s);
        sc.useDelimiter(";");
        ProductList pl = new ProductList(sc.next());
        while (sc.hasNext()) {
            pl.add(fromString(sc.next()));
        }
        sc.close();
        return pl;
    }
    
    private static ShoppingItem fromString(String s) {
        int id = Integer.parseInt(s.split(",")[0]);
        double amount = Double.parseDouble(s.split(",")[1]);
        Product p = IMatDataHandler.getInstance().getProduct(id);
        return new ShoppingItem(p, amount);
    }
    
    public double getPrice() {
        double total = 0;
        for (ShoppingItem s : list) {
            total += s.getTotal();
        }
        return total;
    }
    
    public boolean add(ShoppingItem s) {
        boolean b = list.add(s);
        Collections.sort(list, ps);
        return b;
    }
    
    public ShoppingItem remove(int index) {
        return list.remove(index);
    }
    
    public boolean remove(Object o) {
        return list.remove(o);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(name).append(";");
        for (ShoppingItem s : list) {
            sb.append(s.getProduct().getProductId()).append(",").append(s.getAmount()).append(";");
        }
        return sb.toString();
    }
    
	public int size() {
		return list.size();
	}

	@Override
	public Iterator<ShoppingItem> iterator() {
		return list.iterator();
	}
    
    private static class ShoppingItemSorter implements Comparator<ShoppingItem> {

        public int compare(ShoppingItem s1, ShoppingItem s2) {
            return s1.getProduct().getName().compareTo(s2.getProduct().getName());
        }
        
    }
}
