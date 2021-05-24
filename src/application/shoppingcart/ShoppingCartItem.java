package application.shoppingcart;

import application.FxmlLoader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;

public class ShoppingCartItem extends AnchorPane {

    @FXML ImageView increaseAmount;
    @FXML ImageView decreaseAmount;
    @FXML ImageView removeItem;
    @FXML Label amount;
    @FXML Label itemName;
    @FXML Label totalItemPrice;
    ShoppingItem shoppingItem;

    public ShoppingCartItem(ShoppingItem shoppingItem) {
        this.shoppingItem = shoppingItem;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("application/shoppingcart/ShoppingCartItem.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        itemName.setText(shoppingItem.getProduct().getName());
    }
}
