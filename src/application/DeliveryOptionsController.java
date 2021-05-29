package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryOptionsController implements Initializable {
    private MainController mainController = application.MainController.getInstance();
    private Model model = Model.getInstance();
    Customer customer = model.getCustomer();
    @FXML Button saveButton;
    @FXML TextField address;
    @FXML TextField zipcode;
    @FXML TextField city;
    @FXML TextField name;
    @FXML TextField telephone;
    @FXML Label saved;

    String sAddress = "";
    String sZipcode = "";
    String sCity = "";
    String sName = "";
    String sTelephone = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);
        prepopulateFields(customer);

        sAddress = customer.getAddress();
        sZipcode = customer.getPostCode();
        sCity = customer.getLastName();
        sName = customer.getFirstName();
        sTelephone = customer.getPhoneNumber();
    }

    @FXML
    public void addressChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sAddress = address.getText();
    }

    @FXML
    public void zipcodeChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sZipcode = zipcode.getText();
    }
    @FXML
    public void cityChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sCity = city.getText();
    }
    @FXML
    public void nameChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sName = name.getText();
    }
    @FXML
    public void telephoneChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sTelephone = telephone.getText();
    }

    @FXML
    public void saveButtonPressed() {
        saved.setVisible(true);
        saveButton.setDisable(true);
        customer.setAddress(sAddress);
        customer.setFirstName(sName);
        customer.setLastName(sCity);
        customer.setPhoneNumber(sTelephone);
        customer.setPostAddress(sZipcode);
    }

    @FXML
    public void backButtonPressed() {
        mainController.goHome();
    }

    private void prepopulateFields (Customer customer) {
        address.setText(customer.getAddress());
        zipcode.setText(customer.getPostAddress());
        city.setText(customer.getLastName());
        name.setText(customer.getFirstName());
        telephone.setText(customer.getPhoneNumber());
    }
}