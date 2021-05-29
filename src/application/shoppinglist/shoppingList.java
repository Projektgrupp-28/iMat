package application.shoppinglist;
import application.Model;
import se.chalmers.cse.dat216.project.Product;
import java.util.ArrayList;
import java.util.List;

public class shoppingList {

    private List<Product> productList = new ArrayList<>();
    private String shoppingListName;

    public shoppingList(String shoppingListName, Product product) {
        this.shoppingListName = shoppingListName;
        if (product == null) {
            // Do nothing
        }
        else { this.productList.add(product); }
    }

    public void setShoppingListName(String shoppingListName) {
        this.shoppingListName = shoppingListName;
    }

    public String getShoppingListName() {
        return shoppingListName;
    }

    public void addProductToShoppingList(Product product) {
        if (productList.contains(product)) {
            System.out.println("List already contains product");
        }
        else { productList.add(product); }
    }

    public List<Product> getProductList() {
        return productList;
    }
}
