package imat.backend;

import se.chalmers.ait.dat215.project.ProductCategory;

public class Utils {
	private static final Object[] mainCategories = new Object[] {
			ProductCategory.BREAD, ProductCategory.BERRY, "Drycker",
			ProductCategory.FISH, "Frukt & grönt", ProductCategory.MEAT,
			ProductCategory.DAIRIES, ProductCategory.FLOUR_SUGAR_SALT,
			ProductCategory.NUTS_AND_SEEDS, ProductCategory.PASTA,
			ProductCategory.POTATO_RICE, ProductCategory.SWEET,
			ProductCategory.HERB };

	private static final ProductCategory[] fruitCategories = new ProductCategory[] {
			ProductCategory.POD, ProductCategory.CITRUS_FRUIT,
			ProductCategory.EXOTIC_FRUIT, ProductCategory.VEGETABLE_FRUIT,
			ProductCategory.CABBAGE, ProductCategory.MELONS,
			ProductCategory.ROOT_VEGETABLE, ProductCategory.FRUIT };

	private static final ProductCategory[] drinkCategories = new ProductCategory[] {
			ProductCategory.COLD_DRINKS, ProductCategory.HOT_DRINKS };

	public static String sweCategoryName(ProductCategory p) {

		switch (p) {
		case BERRY:
			return "Bär";
		case BREAD:
			return "Bröd";
		case CABBAGE:
			return "Kål";
		case CITRUS_FRUIT:
			return "Citrusfrukter";
		case COLD_DRINKS:
			return "Kalla drycker";
		case DAIRIES:
			return "Mejeri";
		case EXOTIC_FRUIT:
			return "Exotiska frukter";
		case FISH:
			return "Fisk";
		case FLOUR_SUGAR_SALT:
			return "Mjöl, socker & salt";
		case FRUIT:
			return "Stenfrukter";
		case HERB:
			return "Örtkryddor";
		case HOT_DRINKS:
			return "Varma drycker";
		case MEAT:
			return "Kött";
		case MELONS:
			return "Meloner";
		case NUTS_AND_SEEDS:
			return "Nötter & frön";
		case PASTA:
			return "Pasta";
		case POD:
			return "Baljväxter";
		case POTATO_RICE:
			return "Potatis & ris";
		case ROOT_VEGETABLE:
			return "Rotfrukter";
		case SWEET:
			return "Sötsaker";
		case VEGETABLE_FRUIT:
			return "Grönsaksfrukter";
		}
		throw new IllegalArgumentException();
	}

	public static Object[] getMainCategories() {
		return mainCategories;
	}

	public static ProductCategory[] getDrinkCategories() {
		return drinkCategories;
	}

	public static ProductCategory[] getFruitCategories() {
		return fruitCategories;
	}

}
