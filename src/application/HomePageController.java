package application;

import application.hiddenitems.HiddenProductListener;
import application.likeditems.LikedProductListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HomePageController implements Initializable, HiddenProductListener, LikedProductListener {

    @FXML private FlowPane promotionProductsFlowPane;
    @FXML private FlowPane oftenPurchasedFlowPane;
    @FXML private FlowPane allProductsFlowPane;

    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList(model.getProducts());
        model.addHiddenProductListener(this);
        model.addlikedProductListener(this);
    }

    /**
     * Updates the product list with a given product list.
     * @param products is the given product list.
     */
    private void updateProductList(List<Product> products) {
        // TODO: Fix methods
        promotionProductsFlowPane.getChildren().clear();
        /*
        for (Product product : products) {
            promotionProductsFlowPane.getChildren().add(new ProductCard(product));
        }
         */

        for (int i = 0; i < 3; i++) {
            if(!model.getHiddenProductList().contains(products.get(i))) {
                promotionProductsFlowPane.getChildren().add(new ProductCard(products.get(i)));
            }
        }

        oftenPurchasedFlowPane.getChildren().clear();
        /*
        for (Product product : products) {
            oftenPurchasedFlowPane.getChildren().add(new ProductCard(product));
        }
         */
        for (int i = 4; i < 7; i++) {
            if (!model.getHiddenProductList().contains(products.get(i))) {
                oftenPurchasedFlowPane.getChildren().add(new ProductCard(products.get(i)));
            }
        }

        allProductsFlowPane.getChildren().clear();
        for (Product product : products) {
            if(!model.getHiddenProductList().contains(product)) {
                allProductsFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    @Override
    public void hiddenProductChanged() {
        updateProductList(model.getProducts());
    }

    @Override
    public void productUnliked() {
        //updateProductList(model.getProducts());
        System.out.println("removed from homepage");
    }
}
