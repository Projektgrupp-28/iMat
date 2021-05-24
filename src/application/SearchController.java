package application;

import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SearchController implements Initializable {

    @FXML private Label header;
    @FXML private FlowPane flowPane;
    @FXML private Button returnButton;

    private String searchTerm;

    private Model model = Model.getInstance();
    private MainController mainController = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        searchTerm = model.getCurrentSearchTerm();
        header.setText(model.findProducts(searchTerm).size() + " resultat för " + searchTerm + " hittades");
        loadFlowPane(model.findProducts(searchTerm));
    }

    private void loadFlowPane(List<Product> productList) {
        System.out.println("# matching products: " + productList.size());
        for (Product product : productList) {
            flowPane.getChildren().add(new ProductCard(product));
        }
    }

    @FXML
    private void returnHome() {
        mainController.goHome();
    }
}
