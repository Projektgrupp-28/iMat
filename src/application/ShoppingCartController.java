package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable, ShoppingCartListener {

    @FXML
    FlowPane flowPane;
    private final Model model = Model.getInstance();
    ArrayList<ShoppingCartItem> products = new ArrayList<>();

    ShoppingItem shoppingItem = new ShoppingItem(model.getProduct(1));
    //ShoppingCartItem shoppingCartItem = new ShoppingCartItem(shoppingItem, this);
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //products.add(shoppingCartItemController);
        //flowPane.getChildren().addAll(shoppingCartItemController);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
    }
}
