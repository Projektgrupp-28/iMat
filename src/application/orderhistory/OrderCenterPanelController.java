package application.orderhistory;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderCenterPanelController implements Initializable {

    // Main panel elements:
    @FXML private Label mainPanelHeader;
    @FXML private FlowPane flowPane;
    @FXML private Button addOrderButton;

    private Model model = Model.getInstance();
    private Order order;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (model.getSelectedOrder() == null) {
            mainPanelHeader.setText("Välj order");
            addOrderButton.setVisible(false);
        } else {
            addOrderButton.setVisible(true);
            order = model.getSelectedOrder();
            mainPanelHeader.setText(model.getOrderName(order.getOrderNumber()) + " Köptes:");
            loadFlowPane(order.getItems());
        }
    }

    public void loadFlowPane(List<ShoppingItem> shoppingItems) {
            for (ShoppingItem shoppingItem : shoppingItems) {
                ProductCard productCard = new ProductCard(shoppingItem.getProduct());
                productCard.disableAddButton();
                productCard.setAmount((int)shoppingItem.getAmount());
                flowPane.getChildren().add(productCard);
            }
    }

    @FXML
    public void addToCart() {
        for (int i = 0; i < order.getItems().size(); i++) {
            for (int j = 0; j < (order.getItems().get(i).getAmount()); j++) {
                model.addToShoppingCart(order.getItems().get(i).getProduct());
            }
        }
    }
}