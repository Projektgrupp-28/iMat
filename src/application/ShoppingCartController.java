package application;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable, ShoppingCartListener {

    @FXML
    ListView<String> shoppingCartList;
    private final Model model = Model.getInstance();
    ArrayList<String> products = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCartList.getItems().removeAll();
        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            products.add(model.getShoppingCart().getItems().get(i).getProduct().getName());
        }
        shoppingCartList.getItems().addAll(products);

        shoppingCartList.getItems().addAll();
        shoppingCartList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                System.out.println(s);
            }
        });
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        for (int i = 0; i < model.getShoppingCart().getItems().size(); i++) {
            products.add(model.getShoppingCart().getItems().get(i).getProduct().getName());
        }
        shoppingCartList.getItems().removeAll();
        shoppingCartList.getItems().addAll(products);
    }
}
