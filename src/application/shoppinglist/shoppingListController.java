package application.shoppinglist;

import application.FxmlLoader;
import application.MainController;
import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.control.ListView;
import se.chalmers.cse.dat216.project.Product;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class shoppingListController implements Initializable, ShoppingListListener {

    @FXML private FlowPane shoppingListFlowPane;
    @FXML private Label listName;
    @FXML private ImageView editList;
    @FXML private ImageView removeList;
    @FXML private Button addListToCart;

    private MainController mainController = MainController.getInstance();
    private final Model model = Model.getInstance();
    private final List<Product> emptyProductList = new ArrayList<>();

    private boolean editIcon;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.addListListener(this);
        if (!isListListEmpty()) { model.fireListChanged(model.getShoppingListList().get(model.getShoppingListList().size() - 1)); }
        else { toggleStuff(); }
    }

    private void updateProductList(List<Product> products) {
        shoppingListFlowPane.getChildren().clear();

        for (Product product : products) {
            if(!model.getHiddenProductList().contains(product)) {
                shoppingListFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    public void deleteShoppingList() {
        if (isListListEmpty()) {
            // Do nothing
        }
        else {
            //Det här öppnar en RemoveListDialog ruta. Klasserna finns i shoppingList mappen
            model.setCurrentList(currentList());
            mainController.openRemoveListDialog();
        }
        toggleStuff();
    }

    public Boolean isListListEmpty() {
        return model.getShoppingListList().isEmpty();
    }

    public void toggleStuff() {
        if (isListListEmpty()) {
            listName.setText("Skapa ny inköpslista");

            editList.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/create.png")));
        }
        else {
            editList.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/edit.png")));
        }
        editIcon = !isListListEmpty();
        removeList.setVisible(!isListListEmpty());
        addListToCart.setVisible(!isListListEmpty());
    }

    public void addListToCart() {
        List<Product> products = currentList().getProductList();
        for (Product product : products) {
            model.addToShoppingCart(product);
        }
    }

    public void editOrCreateList() {
        if (editIcon) {
            mainController.openEditListDialogue();
        }
        else {
            shoppingListFlowPane.getChildren().clear();
            createNewList();
        }
        toggleStuff();
    }

    public void createNewList() {
        model.createShoppingList(null);
    }

    public void updateShownList(shoppingList sl) {
        if (sl != null) {
            listName.setText(sl.getShoppingListName());
            updateProductList(sl.getProductList());
        }
        else {
            System.out.println("Shoppinglist can't be null");
        }
        toggleStuff();
    }

    public shoppingList currentList() {
        for (shoppingList sl : model.getShoppingListList()) {
            if (listName.getText() == sl.getShoppingListName()) {
                return sl;
            }
        }
        return null;
    }

    @Override
    public void updateShownShoppingList(shoppingList sl) {
        if (isListListEmpty()) {
            updateProductList(emptyProductList);
        }
        else if (sl != null){
            updateShownList(sl);
        }
        else { updateShownList(null); }
        toggleStuff();
    }
}
