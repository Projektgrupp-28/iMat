package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingCartListener;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class hiddenProductPageController implements Initializable, HiddenProductListener {

    @FXML
    private FlowPane hiddenFlowpane;

    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList(model.getHiddenProductList());
        model.addHiddenProductListener(this);
    }

    private void updateProductList(List<Product> products) {
        hiddenFlowpane.getChildren().clear();

        for (Product product : products) {
            hiddenFlowpane.getChildren().add(new ProductCard(product));
        }
    }

    @Override
    public void hiddenProductChanged() {
        updateProductList(model.getHiddenProductList());
        System.out.println("hiddenProductChanged in hiddenProductPageController");
    }
}
