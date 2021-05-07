import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import se.chalmers.cse.dat216.project.Product;

public class ProductCard {

    Product product;
    @FXML
    ImageView productImage;
    @FXML
    Label productName;

    public ProductCard(Product product) {
        this.product = product;
        this.productImage.setImage(new Image(product.getImageName()));
        this.productName.setText(product.getName());
    }
}
