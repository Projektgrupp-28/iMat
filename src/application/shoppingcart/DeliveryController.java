package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.Customer;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {
    private Model model = Model.getInstance();
    private ShoppingCartController shoppingCartController;
    Customer customer = model.getCustomer();

    @FXML Label zipErrorMessage;
    @FXML TextField address;
    @FXML TextField zipcode;
    @FXML TextField city;
    @FXML TextField name;
    @FXML TextField telephone;
    @FXML CheckBox saveInformationBox;

    List<TextField> textFields = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shoppingCartController = ShoppingCartController.getInstance();
        initTextFields();
        prepopulateFields(customer);
        //checkPrecedence();
    }

    private void initTextFields() {
        textFields.add(address);
        textFields.add(name);
        textFields.add(zipcode);
        textFields.add(city);
        textFields.add(telephone);
    }

    @FXML
    public void InformationChanged() {
        customer.setAddress(address.getText());
        customer.setFirstName(name.getText());
        customer.setLastName(city.getText());
        customer.setPhoneNumber(telephone.getText());
        customer.setPostAddress(zipcode.getText());
    }

    @FXML
    private void zipCodeTypeCheck() {
        if (zipcode.getText().length() > 5) {
            zipcode.deletePreviousChar();
            // TODO: Jump to next.
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
            shoppingCartController.disableNextButton(true);
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
    private void nameLabelTypeCheck() {
        if (zipcode.getText().matches("[abc]")) {
            System.out.println("Hello");
        }
    }

    private void prepopulateFields (Customer customer) {
        address.setText(customer.getAddress());
        zipcode.setText(customer.getPostAddress());
        city.setText(customer.getLastName());
        name.setText(customer.getFirstName());
        telephone.setText(customer.getPhoneNumber());
    }

    @FXML
    private void checkPrecedence() {
        markUnsolvedTextFields();
        for (TextField textField : textFields) {
            if (textField.getText().equals("")) {
                // There is no information in the text field.
                shoppingCartController.disableNextButton(true);
                saveInformationBox.setDisable(true);
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
                // All text fields have been filled with information.
                if (textField.getStyle().equals(style)) {
                    textField.setStyle("-fx-border-color: black; -fx-prompt-text-fill: grey;");
                }
                shoppingCartController.disableNextButton(false);
                saveInformationBox.setDisable(false);
            }
        }
    }


}