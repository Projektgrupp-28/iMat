package application.shoppinglist;

import application.MainController;
import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class shoppingListCatalogueController implements Initializable, ShoppingListCatalogueListener {
    @FXML private ListView<String> listCatalogue;
    private final Model model = Model.getInstance();
    private final MainController mc = MainController.getInstance();
    private boolean toggle = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateShoppingListCatalogue(model.getShoppingListList());
        model.addCatalogueListener(this);
    }

    private void updateShoppingListCatalogue(List<shoppingList> shoppingListList) {
        listCatalogue.getItems().clear();

        // Reverses list
        for (int i = shoppingListList.size(); i-- > 0; ) {
            listCatalogue.getItems().add(shoppingListList.get(i).getShoppingListName());
        }

        listCatalogue.getSelectionModel().selectFirst();
    }

    public void showList() {
        String chosenList = listCatalogue.getSelectionModel().getSelectedItem();
        model.setSelectedShoppingList(chosenList);
    }

    public void createNewList() {
        model.createShoppingList(null);
        model.fireListChanged(model.getShoppingListList().get(model.getShoppingListList().size() - 1));
    }

    public void goBack() {
        mc.closeShoppingList();
    }

    @Override
    public void shoppingListCatalogueChanged() {
        updateShoppingListCatalogue(model.getShoppingListList());
    }
}
