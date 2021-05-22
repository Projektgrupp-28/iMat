package application.hiddenitems;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class hiddenProductPageController implements Initializable, HiddenProductListener {

    @FXML
    private FlowPane hiddenFlowPane;

    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList(model.getHiddenProductList());
        model.addHiddenProductListener(this);
    }

    private void updateProductList(List<Product> products) {
        hiddenFlowPane.getChildren().clear();

        for (Product product : products) {
            hiddenFlowPane.getChildren().add(new ProductCard(product));
        }
    }

    @Override
    public void hiddenProductChanged() {
        updateProductList(model.getHiddenProductList());
    }
}
