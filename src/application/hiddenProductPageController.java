package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class hiddenProductPageController implements Initializable {

    @FXML
    private FlowPane hiddenFlowpane;

    private final Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateProductList(model.getHiddenProductList());
    }

    private void updateProductList(List<Product> products) {
        hiddenFlowpane.getChildren().clear();

        for (Product product : products) {
            hiddenFlowpane.getChildren().add(new ProductCard(product));
        }
    }
}
