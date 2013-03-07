package imat.backend;

import java.util.List;

import se.chalmers.ait.dat215.project.IMatDataHandler;
import se.chalmers.ait.dat215.project.Product;
import se.chalmers.ait.dat215.project.ProductCategory;

public enum CustomCategory {

	HOME("Hem", CustomProductLists.getProductList("Hem"), false, false),
	BAKE("Baka", CustomProductLists.getProductList("Baka"), false, false),
	POD("Baljväxter", IMatDataHandler.getInstance().getProducts(ProductCategory.POD), false, false),
	BREAD("Bröd", IMatDataHandler.getInstance().getProducts(ProductCategory.BREAD), false, true),
	BERRYS("Bär", IMatDataHandler.getInstance().getProducts(ProductCategory.BERRY), false, false),
	CITRUS_FRUIT("Citrusfrukter", IMatDataHandler.getInstance().getProducts(ProductCategory.CITRUS_FRUIT), false, false),
	DRINKS("Drycker", null, true, true),
	EXOTIC_FRUIT("Exotiska frukter", IMatDataHandler.getInstance().getProducts(ProductCategory.EXOTIC_FRUIT),false, false),
	FISH("Fisk", IMatDataHandler.getInstance().getProducts(ProductCategory.FISH), false, true),
	GREEN("Frukt & grönt", null, true, true),
	VEGETABLE_FRUIT("Grönsaksfrukter", IMatDataHandler.getInstance().getProducts(ProductCategory.VEGETABLE_FRUIT), false, false),
	COLD_DRINKS("Kalla drycker", IMatDataHandler.getInstance().getProducts(ProductCategory.COLD_DRINKS), false, false),
	SPICES("Kryddor", CustomProductLists.getProductList("Kryddor"), false, false),
	CABBAGE("Kål", IMatDataHandler.getInstance().getProducts(ProductCategory.CABBAGE), false, false),
	MEAT("Kött", IMatDataHandler.getInstance().getProducts(ProductCategory.MEAT), false, true),
	DAIRIES("Mejeri", IMatDataHandler.getInstance().getProducts(ProductCategory.DAIRIES), false, true),
	MELONS("Meloner", IMatDataHandler.getInstance().getProducts(ProductCategory.MELONS), false, false),
	PASTA("Pasta", IMatDataHandler.getInstance().getProducts(ProductCategory.PASTA), false, false),
	POTATOES("Potatis", CustomProductLists.getProductList("Potatis"), false, false),
	RICE("Ris", CustomProductLists.getProductList("Ris"), false, false),
	ROOT_VEGETABLE("Rotfrukter", IMatDataHandler.getInstance().getProducts(ProductCategory.ROOT_VEGETABLE), false, false),
	PANTRY("Skafferi", null, true, true),
	SNACKS("Snacks", IMatDataHandler.getInstance().getProducts(ProductCategory.NUTS_AND_SEEDS), false, false),
	FRUIT("Stenfrukter", IMatDataHandler.getInstance().getProducts(ProductCategory.FRUIT), false, false),
	SWEET("Sötsaker", IMatDataHandler.getInstance().getProducts(ProductCategory.SWEET), false, false),
	HOT_DRINKS("Varma drycker", IMatDataHandler.getInstance().getProducts(ProductCategory.HOT_DRINKS), false, false),
	
	FAVORITES("Favoriter", IMatDataHandler.getInstance().favorites(), false, false);

	
	
	private String titel;
	private List<Product> products;
	private boolean isParent, isMain;

	CustomCategory(String titel, List<Product> products, boolean isParent,
			boolean isMain) {
		this.titel = titel;
		this.products = products;
		this.isParent = isParent;
		this.isMain = isMain;
	}

	public String getTitle() {
		return titel;
	}

	public List<Product> getProducts() {
		return products;
	}

	public boolean isParent() {
		return isParent;
	}

	public boolean isMain() {
		return isMain;
	}

	public static CustomCategory[] getSubCategories(CustomCategory p) {
		switch (p) {
		case DRINKS:
			return new CustomCategory[] { COLD_DRINKS, HOT_DRINKS };
		case GREEN:
			return new CustomCategory[] { POD, BERRYS, CITRUS_FRUIT,
					EXOTIC_FRUIT, VEGETABLE_FRUIT, CABBAGE, MELONS, FRUIT };
		case PANTRY:
			return new CustomCategory[] { BAKE, SPICES, SNACKS, POTATOES, RICE,
					SWEET };
		}
		throw new IllegalArgumentException();
	}
}
