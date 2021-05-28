package application;

import application.shoppinglist.shoppingList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ListDialogController implements Initializable {

    private @FXML Label productName;
    private @FXML ChoiceBox<String> listor;
    MainController mainController = MainController.getInstance();
    Model model = Model.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productName.setText("Hej");
        updateShoppingListCatalogue(model.getShoppingListList());
    }

    private void updateShoppingListCatalogue(List<shoppingList> shoppingListList) {
        listor.getItems().clear();

        // Reverses list
        for (int i = shoppingListList.size(); i-- > 0; ) {
            listor.getItems().add(shoppingListList.get(i).getShoppingListName());
        }

        listor.getSelectionModel().selectFirst();
    }

    public void addToList() {
        String chosenList = listor.getSelectionModel().getSelectedItem();
        model.addToSelectedShoppingList(chosenList);
        closeWindow();
    }
    @FXML
    public void closeWindow() { mainController.closeOverlayWindow(); }
}
