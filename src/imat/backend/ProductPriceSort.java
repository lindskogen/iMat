package imat.backend;

import java.util.Comparator;

import se.chalmers.ait.dat215.project.Product;

public class ProductPriceSort implements Comparator<Product> {

	@Override
	public int compare(Product arg0, Product arg1) {
		Product p1 = (Product) arg0;
		Product p2 = (Product) arg1;
		return Double.compare(p1.getPrice(), p2.getPrice());
	}

}
