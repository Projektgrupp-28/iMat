package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryOptionsController implements Initializable {
    private MainController mainController = application.MainController.getInstance();
    private Model model = Model.getInstance();
    Customer customer = model.getCustomer();

    @FXML Button saveButton;
    @FXML Label zipErrorMessage;
    @FXML TextField address;
    @FXML TextField zipcode;
    @FXML TextField city;
    @FXML TextField name;
    @FXML TextField telephone;
    @FXML Label saved;

    List<TextField> textFields = new ArrayList<>();

    private void initTextFields() {
        textFields.add(address);
        textFields.add(name);
        textFields.add(zipcode);
        textFields.add(city);
        textFields.add(telephone);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTextFields();
        saveButton.setDisable(true);
        prepopulateFields();
    }

    @FXML
    private void zipCodeTypeCheck() {
        if (zipcode.getText().length() > 5) {
            zipcode.deletePreviousChar();
        } else if (!zipcode.getText().matches("\\d+")) {
            // Given text does not include digits.
            zipcode.deletePreviousChar();
        } else if (zipcode.getText().length() == 5) {
            zipcode.setStyle("-fx-border-color: green; -fx-prompt-text-fill: grey;");
            zipErrorMessage.setVisible(false);
            checkPrecedence();
        } else {
            zipcode.setStyle("-fx-border-color: red; -fx-prompt-text-fill: #FF8888;");
            zipErrorMessage.setVisible(true);
            saveButton.setDisable(true);
            saved.setVisible(false);
        }
    }

    @FXML
    private void phoneTypeCheck() {
        if (!telephone.getText().matches("\\d+")) {
            // Given text does not include digits.
            telephone.deletePreviousChar();
        }
    }

    @FXML
    public void saveButtonPressed() {
        saved.setVisible(true);
        saveButton.setDisable(true);

        customer.setAddress(address.getText());
        customer.setPostAddress(city.getText());
        customer.setPostCode(zipcode.getText());
        customer.setFirstName(name.getText()); // Used as full name…
        //customer.setLastName(name.getText());
        customer.setPhoneNumber(telephone.getText());
    }

    @FXML
    public void backButtonPressed() {
        mainController.goHome();
        mainController.openAccountView();
    }

    private void prepopulateFields() {
        address.setText(customer.getAddress());
        city.setText(customer.getPostAddress());
        zipcode.setText(customer.getPostCode());
        //name.setText(customer.getLastName());
        name.setText(customer.getFirstName()); // Used as full name
        telephone.setText(customer.getPhoneNumber());
    }

    @FXML
    private void checkPrecedence() {
        markUnsolvedTextFields();
        for (TextField textField : textFields) {
            if (textField.getText().isEmpty()) {
                saveButton.setDisable(true);
                saved.setVisible(false);
                break;
            } else {
                saveButton.setDisable(false);
                saved.setVisible(false);
            }
        }
    }

    private void markUnsolvedTextFields() {
        String style = "-fx-border-color: red; -fx-prompt-text-fill: #FF8888;";
        for (TextField textField : textFields) {
            if (textField.getText().equals("")) {
                // One text field is left empty.
                textField.setStyle(style);
            } else {
                // This text fields have been filled with information.
                if (textField.getStyle().equals(style)) {
                    textField.setStyle("-fx-border-color: black; -fx-prompt-text-fill: grey;");
                }
            }
        }
    }
}