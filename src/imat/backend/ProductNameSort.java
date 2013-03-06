package imat.backend;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import se.chalmers.ait.dat215.project.Product;

public class ProductNameSort implements Comparator<Product> {

	@Override
	public int compare(Product arg0, Product arg1) {
		Product p1 = (Product) arg0;
		Product p2 = (Product) arg1;
		return p1.getName().compareTo(p2.getName());
	}

	public static void main(String[] args) {
		List<Product> tmp = CustomCategory.SPICES.getProducts();
		System.out.println(tmp);
		Collections.sort(tmp, new ProductNameSort());
		System.out.println(tmp);
	}
}
