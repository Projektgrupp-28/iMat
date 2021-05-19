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

/**
 * This class represents a product card including the FXML-elements added in ProductCard.fxml.
 * It builds from iMat Mini's ProductPanel class. It extends AnchorPane since it needs to represent
 * a node.
 * @author Philip Winsnes
 */

public class ProductCard extends AnchorPane {

    @FXML ImageView productImage;
    @FXML Label productName;
    @FXML Label priceLabel;
    @FXML Label ecoLabel;
    @FXML HBox buttonGroup;
    /**
     * Wrapper class of the data handler that holds some backend functionalities.
     */
    private Model model = Model.getInstance();

    /**
     * The card's product.
     */
    private Product product;

    private final static double kImageWidth = 100.0;
    private final static double kImageRatio = 0.75;

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
        this.productImage.setImage(new Image("resources/imat/imat/images/" + product.getImageName()));
        this.productName.setText(product.getName()); // Sätter titel
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
        //buttonGroup.setOpacity(1);
        buttonGroup.setVisible(true);
    }

    @FXML
    private void onMouseExit() {
        //buttonGroup.setOpacity(0);
        buttonGroup.setVisible(false);
    }

    @FXML
    private void likeItem() {
        System.out.println(product.getName() + " liked");
    }

    @FXML
    private void hideItem() {
        //System.out.println(product.getName() + " hidden");
        model.addToHiddenProductList(product);
        System.out.println(model.getHiddenProductList());
    }

    @FXML
    private void addItemToList() {
        System.out.println(product.getName() + " added to list");
    }
}
