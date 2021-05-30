package application.orderhistory;

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
import se.chalmers.cse.dat216.project.Order;
import se.chalmers.cse.dat216.project.Product;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OrderLeftPanelController implements Initializable, HiddenProductListener {

    // Left panel elements:
    @FXML private Label leftPanelHeader;
    @FXML private ListView<String> listView;
    @FXML private Button returnButton;

    private Model model = Model.getInstance();
    private MainController mainController = MainController.getInstance();
    private static OrderLeftPanelController orderLeftPanelController;
    private List<Order> orders;
    public static OrderLeftPanelController getInstance() {
        return orderLeftPanelController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        orderLeftPanelController = this;
        orders = model.getOrders();
        loadListView();
    }

    private void loadListView() {
        listView.getItems().clear();
        for (int i = 0; i < orders.size(); i++) {
            int orderNumber = orders.get(i).getOrderNumber();
            listView.getItems().addAll(model.getOrderName(orderNumber));
        }

    }

    private int getSelectedOrder() {
        return orders.get(listView.getSelectionModel().getSelectedIndex()).getOrderNumber();
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
    private void showOrder() {
        Order order = orders.get(listView.getSelectionModel().getSelectedIndex());
        System.out.println(model.getOrderName(order.getOrderNumber()) + " have been chosen.");
        model.setSelectedOrder(order);
        activateReturnButton();
        mainController.showOrderCenterView();
    }

    @FXML
    private void returnHome() {
        listView.getSelectionModel().clearSelection();
        disableReturnButton();
        mainController.goHome();
        mainController.showCategories();
        mainController.openAccountView();
    }

    @Override
    public void hiddenProductChanged() {}
}