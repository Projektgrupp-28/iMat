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
    private static FlowPaneController flowPaneController;

    ArrayList<ShoppingItem> cart;

    public static FlowPaneController getInstance() {
        if (flowPaneController == null) {
            System.out.println("FlowPaneController is null.");
        }
        return flowPaneController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        flowPaneController = this;
        populate();
    }

    public void populate() {
        flowPane.getChildren().clear();
        cart = new ArrayList<>(model.getShoppingCart().getItems());
        if (cart.size() != 0) {
            for (int i = 0; i < cart.size(); i++) {
                ShoppingCartItemHolder shoppingCartItemHolder = new ShoppingCartItemHolder(cart.get(i));
                if (Integer.remainderUnsigned(i,2) == 1) {
                    //shoppingCartItemHolder.setStyle("-fx-background-color: -fx-background;");
                }
                flowPane.getChildren().add(shoppingCartItemHolder);
            }
        } else {
            Label label = new Label();
            label.setFont(Font.font("Lato",18));
            label.setPrefWidth(570);
            label.setPrefHeight(200);
            label.setAlignment(Pos.CENTER);
            label.setText("Din varukorg ??r tom");
            flowPane.getChildren().add(label);
        }
    }
}
