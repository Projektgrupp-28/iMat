import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class BorderPaneDemoController implements Initializable {

    private FxmlLoader fxmlLoader = new FxmlLoader();

    String previousSelectedCategory;

    @FXML BorderPane mainPane;

    //@FXML Pane mejeri;
    //@FXML Pane chark;
    //@FXML Pane godis;
    //@FXML Pane snacks;

    //@FXML Pane test;


    @FXML FlowPane promotionProductsFlowPane;
    @FXML FlowPane oftenPurchasedFlowPane;
    @FXML FlowPane allProductsFlowPane;
    @FXML TextArea searchField;
    @FXML AnchorPane accountPane;
    @FXML AnchorPane homePane;
    @FXML ListView<String> categoriesList;
    ObservableList observableCategoriesList = FXCollections.observableArrayList();

    @FXML
    private void handleMejeriAction(ActionEvent event) {
        System.out.println("You clicked me!");
        Pane view = fxmlLoader.getPage("mejeri");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleCharkAction(ActionEvent event) {
        System.out.println("You clicked me!");
        Pane view = fxmlLoader.getPage("chark");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleGodisAction(ActionEvent event) {
        System.out.println("You clicked me!");
        Pane view = fxmlLoader.getPage("godis");
        mainPane.setCenter(view);
    }

    @FXML
    private void handleSnacksAction(ActionEvent event) {
        System.out.println("You clicked me!");
        Pane view = fxmlLoader.getPage("test");
        mainPane.setCenter(view);
    }

    public String getSelectedCategory() {
        return categoriesList.getSelectionModel().getSelectedItem();
    }

    private String getPreviousSelectedCategory() {
        return previousSelectedCategory;
    }

    @FXML
    private void displaySelectedFromList(MouseEvent event) {
        Pane view;
        if (getSelectedCategory() == null || getSelectedCategory().isEmpty()) {
            System.out.println("Nothing is selected.");
            // TODO: Show home view.

        } else if (getSelectedCategory() == getPreviousSelectedCategory()) {
            System.out.println("Same selection have been made.");
            // TODO: Return to home view or maybe nothing.
            view = fxmlLoader.getPage("Home");
            mainPane.setCenter(view);

        } else {
            System.out.println(getSelectedCategory() + " is selected.");
            view = fxmlLoader.getPage(getSelectedCategory());
            mainPane.setCenter(view);
        }
        previousSelectedCategory = getSelectedCategory();
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadCategoriesList();

        Pane view = fxmlLoader.getPage("Home");
        mainPane.setCenter(view);
    }

    private void loadCategoriesList() {
        observableCategoriesList.removeAll();
        observableCategoriesList.addAll("Mejeri", "Chark", "Frukt", "Godis", "Snacks");
        categoriesList.getItems().addAll(observableCategoriesList);
    }
}
