package application.shoppinglist;

import application.MainController;
import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class editListController implements Initializable {

    private MainController mainController = MainController.getInstance();
    private Model model = Model.getInstance();

    private @FXML TextField editField;

    private shoppingList currentList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currentList = model.getCurrentShoppingList();
    }

    @FXML
    private void handleSearchAction() {
        String newName = editField.getText();
        currentList.setShoppingListName(newName);
        closeWindow();
    }

    @FXML
    private void closeWindow() {
        mainController.closeOverlayWindow();
    }
}
