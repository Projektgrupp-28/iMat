package application.categories;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryCenterPanelController implements Initializable {

    // Main panel elements:
    @FXML private Label mainPanelHeader;
    @FXML private FlowPane flowPane;

    private Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mainPanelHeader.setText(model.getSelectedCategoryName());
        loadFlowPane(model.getProducts(model.getSelectedCategoryName()));
    }

    public void loadFlowPane(List<Product> productList) {
        for (Product product : productList) {
            flowPane.getChildren().add(new ProductCard(product));
        }
    }
}
