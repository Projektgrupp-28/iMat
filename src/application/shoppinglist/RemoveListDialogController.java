package application.shoppinglist;

import application.MainController;
import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class RemoveListDialogController implements Initializable {

    MainController mainController = MainController.getInstance();
    Model model = Model.getInstance();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    private void closeWindow() {
        mainController.closeOverlayWindow();
    }
}
