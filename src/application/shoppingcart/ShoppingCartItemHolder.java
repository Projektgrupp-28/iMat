package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

public class ShoppingCartItemHolder extends AnchorPane {

    @FXML private Label productName;
    @FXML private TextField productAmount;
    @FXML private Label price;
    @FXML private ImageView imageView;
    @FXML private Button deleteButton;
    @FXML private ImageView trashcan;

    ShoppingItem shoppingItem;
    Product product;
    Model model = Model.getInstance();

    public ShoppingCartItemHolder(ShoppingItem shoppingItem) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("ShoppingItemHolder.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        this.shoppingItem = shoppingItem;

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        product = shoppingItem.getProduct();
        productName.setText(product.getName());
        productAmount.setText( (int) shoppingItem.getAmount()+ " " + product.getUnitSuffix());
        imageView.setImage(model.getImage(product));
        price.setText(shoppingItem.getTotal()+" kr");
    }
    @FXML public void incrementAmount() {
        model.getShoppingCart().removeItem(shoppingItem);
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        model.getShoppingCart().addItem(shoppingItem);
        productAmount.setText((int)shoppingItem.getAmount()+ " " + product.getUnitSuffix());

        NumberFormat rounded = NumberFormat.getNumberInstance();
        rounded.setMaximumFractionDigits(2);
        rounded.setRoundingMode(RoundingMode.FLOOR);
        price.setText(rounded.format(shoppingItem.getTotal()) + " kr");
    }
    @FXML public void decrementAmount() {
        if (shoppingItem.getAmount() > 1) {
            model.getShoppingCart().removeItem(shoppingItem);
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            model.getShoppingCart().addItem(shoppingItem);
            productAmount.setText((int) shoppingItem.getAmount()+ " " + product.getUnitSuffix());

            NumberFormat rounded = NumberFormat.getNumberInstance();
            rounded.setMaximumFractionDigits(2);
            rounded.setRoundingMode(RoundingMode.FLOOR);
            price.setText(rounded.format(shoppingItem.getTotal()) + " kr");
        }
    }
    @FXML public void deleteItem() {
        deleteButton.setStyle("-fx-background-color:red;");
        trashcan.resize(1,1);
        trashcan.setVisible(false);
        deleteButton.setText("Ta bort?");
        /*
        model.getShoppingCart().removeItem(shoppingItem);
        productAmount.setText("0st");
        price.setText("0 kr");
        this.setDisable(true);

         */
    }
}
