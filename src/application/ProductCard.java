package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

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
        //this.product = product;
        //this.productImage.setImage(new Image(product.getImageName()));
        //this.productName.setText(product.getName());
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

}
