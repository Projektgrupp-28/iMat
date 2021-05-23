package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlowPaneController implements Initializable {

    @FXML
    private FlowPane flowPane;
    private final Model model = Model.getInstance();
    List<ShoppingCartItem> products = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ShoppingItem shoppingItem = new ShoppingItem(model.getProduct(1));
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem);
        products.add(shoppingCartItem);
    }

    public void loadProducts () {
        flowPane.getChildren().clear();
        for (int i = 0; i < 3; i++) {
            flowPane.getChildren().add(products.get(i));
        }
    }

    public void aa () {
        System.out.println("afabsdfoiuabfpauwfebpauwfebapiwfbuapwefuib");
    }
}
