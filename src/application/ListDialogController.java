package application;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ListDialogController implements Initializable{

    @FXML Label productName;
    MainController mainController = MainController.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        productName.setText("Hej");
    }

    @FXML
    public void closeButtonPressed() {
        mainController.closeOverlayWindow();
    }

    @FXML
    public void outsideClicked() {
        mainController.closeOverlayWindow();
    }
}
