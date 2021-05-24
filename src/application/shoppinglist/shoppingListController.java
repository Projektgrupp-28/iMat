package application.shoppinglist;

import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class shoppingListController implements Initializable, shoppingListListener {

    @FXML private FlowPane shoppingListFlowPane;
    @FXML private Label listName;
    @FXML private ImageView editList;
    @FXML private ImageView removeList;
    @FXML private Button addListToCart;

    private final Model model = Model.getInstance();
    private final List<Product> emptyProductList = new ArrayList<>();
    private boolean editIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //updateShoppingListCatalogue(model.getShoppingListList());
        if (!model.getShoppingListList().isEmpty()) {
            updateShownList();
        }
        else {
            toggleStuff(false);
        }
    }

    private void updateProductList(List<Product> products) {
        shoppingListFlowPane.getChildren().clear();

        for (Product product : products) {
            if(!model.getHiddenProductList().contains(product)) {
                shoppingListFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    private void updateShoppingListCatalogue(List<shoppingList> shoppingListList) {
        //likedFlowPane.getChildren().clear();

        for (shoppingList shoppingList : shoppingListList) {
            System.out.println(shoppingList);
            //likedFlowPane.getChildren().add(new ProductCard(product));
        }
    }

    public void deleteShoppingList() {
        if (isListListEmpty()) {
            // Do nothing
        }
        else {
            model.getShoppingListList().remove(0);
            if (isListListEmpty()) {
                toggleStuff(false);
                updateProductList(emptyProductList);
            }
            else {
                updateShownList();
            }
        }
    }

    public Boolean isListListEmpty() {
        return model.getShoppingListList().isEmpty();
    }

    public void toggleStuff(Boolean bool) {
        if (!bool) {
            listName.setText("Skapa ny inköpslista");
            editList.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/create.png")));
            editIcon = false;
        }
        else {
            editList.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/edit.png")));
            editIcon = true;
        }
        removeList.setVisible(bool);
        addListToCart.setVisible(bool);
    }

    public void addListToCart() {
        List<Product> products = model.getShoppingListList().get(0).getProductList();
        for (Product product : products) { model.addToShoppingCart(product);}
    }

    public void editOrCreateList() {
        if (editIcon) {
            // Edit list
        }
        else {
            shoppingListFlowPane.getChildren().clear();
            createNewList();
            toggleStuff(true);
        }
    }

    public void createNewList() {
        for (int i = 0; i < 5; i++) {
            model.createShoppingList(null);
        }
        updateShownList();
    }

    public void updateShownList() {
        listName.setText(model.getShoppingListList().get(0).getShoppingListName());
        updateProductList(model.getShoppingListList().get(0).getProductList());
        toggleStuff(true);
    }

    @Override
    public void updateShoppingLists() {
        updateShoppingListCatalogue(model.getShoppingListList());
    }
}
