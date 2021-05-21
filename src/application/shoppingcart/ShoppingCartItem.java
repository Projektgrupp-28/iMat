package application.shoppingcart;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.ShoppingItem;

public class ShoppingCartItem extends AnchorPane {

    @FXML ImageView increaseAmount;
    @FXML ImageView decreaseAmount;
    @FXML ImageView removeItem;
    @FXML Label amount;
    @FXML Label itemName;
    @FXML Label totalItemPrice;
    ShoppingCartController parentController;
    //FxmlLoader fxmlLoader = new FxmlLoader();
    ShoppingItem shoppingItem;

    public ShoppingCartItem(ShoppingItem shoppingItem, ShoppingCartController shoppingCartController) {
        this.shoppingItem = shoppingItem;
        this.parentController = shoppingCartController;
        //fxmlLoader.getPage("ShoppingCartItem");
        itemName.setText(shoppingItem.getProduct().getName());
    }
}
