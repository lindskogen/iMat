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


/**
 * Class that represents a list of <code>ShoppingItem</code>s.
 * @author Johan
 *
 */
public class ProductList implements Iterable<ShoppingItem>, Cloneable {
	private List<ShoppingItem> list;
    private String name;
    
    private static ShoppingItemSorter ps = new ShoppingItemSorter();
    
    /**
     * Creates a list with the name "Namnlös".
     */
    public ProductList() {
    	this("Namnlös");
    }
    
    /**
     * Creates a list with the given name.
     * @param n the name of the list
     */
    public ProductList(String n) {
    	name = n;
    	list = new LinkedList<ShoppingItem>();
    }
    
    /**
     * Returns a <code>String</code> the name of the list
     * @return the name of the list
     */
    public String getName() {
    	return name;
    }
    
    /**
     * Parse method for creating a list from a <code>String</code> object.
     * @param s the input string
     * @return the <code>ProductList</code> that was parsed from the input 
     */
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
    
    /**
     * Calculates the total price of all the items in the list.
     * @return the total price of the list.
     */
    public double getPrice() {
        double total = 0;
        for (ShoppingItem s : list) {
            total += s.getTotal();
        }
        return total;
    }
    
    /**
     * Adds a <code>ShoppingItem</code> to the list, also sorts the <code>ProductList</code>.
     * @param s the item to be added to the list
     */
    public void add(ShoppingItem s) {
    	for (ShoppingItem item : list) {
    		if(item.getProduct().equals(s.getProduct())) {
    			item.setAmount(item.getAmount() + s.getAmount());
    			return;
    		}
    	}
        list.add(s);
        Collections.sort(list, ps);
    }
    
    /**
     * Removes an item with a specified index from the list.
     * @param index the index of the item to be removed
     * @return the item that was removed
     */
    public ShoppingItem remove(int index) {
        return list.remove(index);
    }
    
    /**
     * Removes a specified item from the list
     * @param o the item to be removed
     */
    public void remove(Object o) {
    	ShoppingItem removeItem = (ShoppingItem) o;
    	for (ShoppingItem item : list) {
    		if (item.getProduct().equals(removeItem.getProduct())) {
    			list.remove(item);    			
    		}
    	}
    }
    
    /**
     * Returns the string value of the list, to be parsed with {@link #parseString(String) parseString} 
     * 
     * @return the parsable value of the list
     */
    public String stringify() {
        StringBuilder sb = new StringBuilder(name).append(";");
        for (ShoppingItem s : list) {
            sb.append(s.getProduct().getProductId()).append(",").append(s.getAmount()).append(";");
        }
        return sb.toString();
    }
    
    public void setName(String newName) {
    	if (newName != null) {
    		name = newName;    		
    	}
    }
    
    /**
     * Returns the size of the list.
     * @return the amount of items in the list
     */
	public int size() {
		return list.size();
	}
	
	@Override
	public ProductList clone() {
		ProductList nList;
		try {
			nList = (ProductList)super.clone();
			nList.setName(getName());
			nList.list = new LinkedList<ShoppingItem>();
			for (ShoppingItem s : this) {
				nList.add(new ShoppingItem(s.getProduct(), s.getAmount()));				
			}
			return nList;
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Iterator<ShoppingItem> iterator() {
		return list.iterator();
	}
    
	// To be used to sort the ShoppingItems
    private static class ShoppingItemSorter implements Comparator<ShoppingItem> {

        public int compare(ShoppingItem s1, ShoppingItem s2) {
            return s1.getProduct().getName().compareTo(s2.getProduct().getName());
        }
        
    }
}
