package application.shoppinglist;

import application.MainController;
import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import javafx.scene.control.Label;
import java.net.URL;
import java.util.ResourceBundle;

public class RemoveListDialogController implements Initializable {

    MainController mainController = MainController.getInstance();
    Model model = Model.getInstance();

    private @FXML Label listId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        listId.setText(model.getCurrentShoppingList().getShoppingListName());
    }

    public void deleteShoppingList() {
        model.getShoppingListList().remove(model.getCurrentShoppingList());
        model.fireListCatalogueChanged();
        if (isListListEmpty()) { model.fireListChanged(null); }
        else { model.fireListChanged(model.getShoppingListList().get(model.getShoppingListList().size() - 1)); }
        closeWindow();
    }

    public Boolean isListListEmpty() {
       return model.getShoppingListList().isEmpty();
    }

    @FXML
    private void closeWindow() {
        mainController.closeOverlayWindow();
    }
}
