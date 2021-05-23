package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.Product;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * This class represents a product card including the FXML-elements added in ProductCard.fxml.
 * It builds from iMat Mini's ProductPanel class. It extends AnchorPane since it needs to represent
 * a node.
 * @author Philip Winsnes
 */
public class ProductCard extends AnchorPane {

    @FXML private ImageView productImage;
    @FXML private Label productName;
    @FXML private Label priceLabel;
    @FXML private Label ecoLabel;
    @FXML private AnchorPane buttonRestore;
    @FXML private AnchorPane buttonAdd;
    @FXML private HBox buttonGroup;
    @FXML private ImageView likeButton;
    @FXML private ImageView hideButton;
    @FXML private ImageView addToListButton;
    @FXML private Label highSum;
    @FXML private Label lowSum;

    /**
     * Wrapper class of the data handler that holds some backend functionalities.
     */
    private Model model = Model.getInstance();
    /**
     * The card's product.
     */
    private Product product;

    public ProductCard(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ProductCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.product = product;
        this.productImage.setImage(model.getImage(product));
        this.productName.setText(product.getName());

        updateSumLabels(product.getPrice());

        checkIfHidden();

    }

    private void checkIfHidden() {
        if (isHidden()){
            buttonAdd.setVisible(false);
            buttonAdd.setDisable(true);
            buttonRestore.setVisible(true);
            buttonRestore.setDisable(false);
        } else {
            buttonAdd.setVisible(true);
            buttonAdd.setDisable(false);
            buttonRestore.setVisible(false);
            buttonRestore.setDisable(true);
        }
    }

    /**
     * Adds the product to the shopping cart after e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        System.out.println("Add " + product.getName());
        model.addToShoppingCart(product);
    }

    @FXML
    private void onMouseEntered() {
        if(!isHidden()) {
            buttonGroup.setVisible(true);
            if(model.getFavourites().contains(product)){
                likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_red.png")));
            }
            updateHideButton();
        }
    }
    @FXML
    private void onMouseExit() {
        if(!isHidden()) { buttonGroup.setVisible(false); }
    }

    @FXML
    private void likeItem() {
        if(!model.getFavourites().contains(product)){
            model.addFavourite(product);
        }
        else {
            model.removeFavourite(product);
        }
        updateHideButton();
    }

    @FXML
    private void hideItem() {
        //System.out.println(product.getName() + " hidden");
        model.addToHiddenProductList(product);
        System.out.println(model.getHiddenProductList());
    }

    @FXML
    private void restoreHiddenItem() {
        model.removeFromHiddenProductList(product);
    }

    @FXML
    private void addItemToList() {
        model.addProductToList(product);
    }

    private Boolean isHidden() {
        if(model.getHiddenProductList().contains(product)){ return true; }
        else { return false; }
    }

    private void updateHideButton() {
        boolean toggle;

        if(model.getFavourites().contains(product)){
            likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_red.png")));
            toggle = true;
        }
        else {
            likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart.png")));
            toggle = false;
        }

        hideButton.setVisible(!toggle);
        hideButton.setDisable(toggle);
    }

    private void updateSumLabels(Double productPrice) {
        highSum.setText(getHighFormatSum(productPrice));
        lowSum.setText(getLowFormatSum(productPrice));
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

}
