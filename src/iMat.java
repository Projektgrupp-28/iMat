import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import se.chalmers.cse.dat216.project.IMatDataHandler;
import se.chalmers.cse.dat216.project.Product;

import java.util.List;

public class iMat {

    List<Product> productList;
    @FXML
    ListView<ProductCard> listView;

    public static void main(String[] args) {
        IMatDataHandler dataHandler = IMatDataHandler.getInstance();
        Product product = dataHandler.getProduct(2);
        System.out.println(product.getName() + " kostar " + (int)product.getPrice() + "kr");

        System.out.println(dataHandler.getCustomer().getFirstName());
        System.out.println("Test William");
    }
}
