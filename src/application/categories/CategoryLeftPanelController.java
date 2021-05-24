package application.categories;

import application.MainController;
import application.ProductCard;
import application.hiddenitems.HiddenProductListener;
import application.Model;
import com.sun.tools.javac.Main;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryLeftPanelController implements Initializable, HiddenProductListener {

    // Left panel elements:
    @FXML private Label leftPanelHeader;
    @FXML private ListView<String> listView;
    @FXML private Button returnButton;

    private Model model = Model.getInstance();
    private MainController mainController = MainController.getInstance();
    private static CategoryLeftPanelController categoryLeftPanelController;

    public static CategoryLeftPanelController getInstance() {
        return categoryLeftPanelController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        categoryLeftPanelController = this;
        disableReturnButton();
        System.out.println("Catalog have been loaded.");
        leftPanelHeader.setText("Katalog");
        loadListView();
    }

    private void loadListView() {
        listView.getItems().addAll(model.getCategoryNames());
    }

    private String getSelectedCategory() {
        return listView.getSelectionModel().getSelectedItem();
    }

    public void disableReturnButton() {
        returnButton.setVisible(false);
        returnButton.setDisable(true);
    }

    public void activateReturnButton() {
        returnButton.setVisible(true);
        returnButton.setDisable(false);
    }

    @FXML
    private void showCategory() {
        String chosenCategory = listView.getSelectionModel().getSelectedItem();
        System.out.println(chosenCategory + " have been chosen.");
        model.setSelectedCategoryName(chosenCategory);
        activateReturnButton();
        mainController.showCategoryCenterView();
    }

    @FXML
    private void returnHome() {
        listView.getSelectionModel().clearSelection();
        disableReturnButton();
        mainController.goHome();
    }

    @Override
    public void hiddenProductChanged() {}
}
