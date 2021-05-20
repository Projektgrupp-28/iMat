package application.categories;

import application.HiddenProductListener;
import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController implements Initializable, HiddenProductListener {

    Model model = Model.getInstance();



    @FXML private FlowPane categoryFlowPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadFlowPane(model.getProducts());
    }

    private void loadFlowPane(List<Product> products) {
        categoryFlowPane.getChildren().clear();

        for (int i = 0; i < 3; i++) {
            if(!model.getHiddenProductList().contains(products.get(i))) {
                categoryFlowPane.getChildren().add(new ProductCard(products.get(i)));
            }
        }
    }

    @Override
    public void hiddenProductChanged() {
        loadFlowPane(model.getProducts());
    }
}
