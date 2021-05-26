package application.shoppingcart;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ShoppingItem;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FlowPaneController implements Initializable {

    @FXML private FlowPane flowPane;

    Model model = Model.getInstance();
    ArrayList<ShoppingItem> cart;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cart = new ArrayList<>(model.getShoppingCart().getItems());
        populate();
    }

    private void populate() {
        flowPane.getChildren().clear();
        if (cart.size() != 0) {
            for (int i = 0; i < cart.size(); i++) {
                ShoppingCartItemHolder shoppingCartItemHolder = new ShoppingCartItemHolder(cart.get(i));
                if (Integer.remainderUnsigned(i,2) == 1) {
                    shoppingCartItemHolder.setStyle("-fx-background-color: -fx-background;");
                }
                flowPane.getChildren().add(shoppingCartItemHolder);
            }
        } else {
            Label label = new Label();
            label.setFont(Font.font("Lato",18));
            label.setPrefWidth(570);
            label.setPrefHeight(200);
            label.setAlignment(Pos.CENTER);
            label.setText("Din varukorg är tom");
            flowPane.getChildren().add(label);
        }
    }
}
