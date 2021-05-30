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
    @FXML private Label highSum;
    @FXML private Label lowSum;
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
        productAmount.setText(String.valueOf((int)shoppingItem.getAmount()));
        imageView.setImage(model.getImage(product));
        updatePriceLabel();
    }

    private void updatePriceLabel() {
        Double totalPrice = shoppingItem.getTotal();
        highSum.setText(getHighFormatSum(totalPrice));
        lowSum.setText(getLowFormatSum(totalPrice));
    }

    @FXML
    private void onAmountFieldClicked() {
        productAmount.selectAll();
    }

    @FXML
    private void updateAmount() {
        int amount;
        if (productAmount.getText().equals("")) {
            amount = 1;
            productAmount.setText("1");
            productAmount.selectAll();
        } else {
            amount = Integer.parseInt(productAmount.getText());
        }
        System.out.println("New amount is: " + amount);

        model.getShoppingCart().removeItem(shoppingItem);
        shoppingItem.setAmount(amount);
        updatePriceLabel();
        model.getShoppingCart().addItem(shoppingItem);
    }

    @FXML
    private void amountTypeCheck() {
        if (productAmount.getText().length() > 3) {
            productAmount.deletePreviousChar();
        } else if (!productAmount.getText().matches("\\d+")) {
            // Given text does not include digits.
            productAmount.deletePreviousChar();
        }
    }

    /**
     * Formats the total price for the high label which only shows the integers.
     * @param value is the Double value being formatted.
     * @return the value in the new format casted as a String.
     */
    private String getHighFormatSum(Double value) {
        NumberFormat highFormat = NumberFormat.getNumberInstance();
        highFormat.setMaximumFractionDigits(0);
        highFormat.setRoundingMode(RoundingMode.FLOOR);
        return highFormat.format(value);
    }

    /**
     * Formats the total price for the low label which only shows the integers.
     * @param value is the Double value being formatted.
     * @return the value in the new format casted as a String.
     */
    private String getLowFormatSum(Double value) {
        // TODO: remove the decimal.
        NumberFormat lowFormat = NumberFormat.getNumberInstance();
        lowFormat.setMaximumIntegerDigits(0);
        lowFormat.setMinimumFractionDigits(2);
        lowFormat.setMaximumFractionDigits(2);
        return lowFormat.format(value);
    }

    @FXML public void incrementAmount() {
        model.getShoppingCart().removeItem(shoppingItem);
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        model.getShoppingCart().addItem(shoppingItem);
        productAmount.setText(String.valueOf((int)shoppingItem.getAmount()));

        NumberFormat rounded = NumberFormat.getNumberInstance();
        rounded.setMaximumFractionDigits(2);
        rounded.setRoundingMode(RoundingMode.FLOOR);
        updatePriceLabel();
    }

    @FXML public void decrementAmount() {
        if (shoppingItem.getAmount() > 1) {
            model.getShoppingCart().removeItem(shoppingItem);
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            model.getShoppingCart().addItem(shoppingItem);
            productAmount.setText(String.valueOf((int)shoppingItem.getAmount()));

            NumberFormat rounded = NumberFormat.getNumberInstance();
            rounded.setMaximumFractionDigits(2);
            rounded.setRoundingMode(RoundingMode.FLOOR);
            updatePriceLabel();
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
