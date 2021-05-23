package application.likeditems;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class likedProductPageController implements Initializable, LikedProductListener {

    @FXML private FlowPane likedFlowPane;

    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList(model.getFavourites());
        model.addlikedProductListener(this);
    }

    private void updateProductList(List<Product> products) {
        likedFlowPane.getChildren().clear();

        for (Product product : products) {
            likedFlowPane.getChildren().add(new ProductCard(product));
        }
    }

    @Override
    public void productUnliked() {
        updateProductList(model.getFavourites());
    }
}