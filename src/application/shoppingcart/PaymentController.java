package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.CreditCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    Model model = Model.getInstance();
    CreditCard creditCard = model.getCreditCard();
    private static PaymentController paymentController;

    @FXML private TextField name;
    @FXML private TextField cardNumber;
    @FXML private TextField month;
    @FXML private TextField year;
    @FXML private TextField cvc;
    @FXML private ComboBox cardTypeSelector;
    @FXML private HBox dateBox;
    @FXML private CheckBox saveInformationBox;

    List<TextField> textFields = new ArrayList<>();

    private String errorStyle = "-fx-border-color: red; -fx-prompt-text-fill: #FF8888;";
    private String orgStyle = "-fx-border-color: black; -fx-prompt-text-fill: grey;";

    private ShoppingCartController shoppingCartController;

    public static PaymentController getInstance() {
        if (paymentController == null) {
            System.out.println("ERROR: DeliveryController is null!");
        }
        return paymentController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        paymentController = this;
        shoppingCartController = ShoppingCartController.getInstance();
        initTextFields();
        prepopulateFields(creditCard);
        cardTypeSelector.getItems().add("MasterCard");
        cardTypeSelector.getItems().add("Visa");
    }

    private void initTextFields() {
        textFields.add(name);
        textFields.add(cardNumber);
        textFields.add(month);
        textFields.add(cvc);
        textFields.add(year);
    }

    @FXML
    public void InformationChanged() {
        creditCard.setCardNumber(cardNumber.getText());
        creditCard.setHoldersName(name.getText());
        /*
        if (date.getText().length() >= 6) {
            date.deleteText(5,6);
        } else if (!date.getText().matches("\\d+")) {
            if (date.getText().matches("")) {
                creditCard.setValidYear(0);
            }
            if (date.getText().length() > 0) {
                date.deleteText(date.getText().length()-1,date.getText().length());
            }


        } else {
            if (date.getText().equals("")) {
                creditCard.setValidYear(0);
            } else {
                creditCard.setValidYear(Integer.parseInt(date.getText()));
            }
        }

         */
        if (cvc.getText().length() >= 4) {
            cvc.deleteText(3,4);

        } else if (!cvc.getText().matches("\\d+")) {
            if (cvc.getText().matches("")) {
                creditCard.setVerificationCode(0);
            }
            if (cvc.getText().length() > 0) {
                cvc.deleteText(cvc.getText().length()-1,cvc.getText().length());
            }
        } else {
            if (cvc.getText().equals("")) {
                creditCard.setVerificationCode(0);
            } else {
                creditCard.setVerificationCode(Integer.parseInt(cvc.getText()));
            }
        }
    }

    @FXML
    private void cardNumTypeCheck() {
        if (cardNumber.getText().length() > 16) {
            cardNumber.deletePreviousChar();
        } else if (!cardNumber.getText().matches("\\d+")) {
            // Given text does not include digits.
            cardNumber.deletePreviousChar();
        } else if (cardNumber.getText().length() == 16) {
            cardNumber.setStyle("-fx-border-color: green; -fx-prompt-text-fill: grey;");
            checkPrecedence();
        } else {
            cardNumber.setStyle("-fx-border-color: red; -fx-prompt-text-fill: #FF8888;");
            shoppingCartController.disableNextButton(true);
            saveInformationBox.setDisable(true);
        }
    }

    @FXML
    private void monthTypeCheck() {
        if (month.getText().length() > 2) {
            month.deletePreviousChar();
        } else if (!month.getText().matches("\\d+")) {
            // Given text does not include digits.
            month.deletePreviousChar();
        } else if (month.getText().length() == 2) {
            dateBox.setStyle("-fx-border-color: black;");
            checkPrecedence();
        } else {
            dateBox.setStyle("-fx-border-color: red;");
            shoppingCartController.disableNextButton(true);
            saveInformationBox.setDisable(true);
        }
    }

    @FXML
    private void yearTypeCheck() {
        if (year.getText().length() > 2) {
            year.deletePreviousChar();
        } else if (!year.getText().matches("\\d+")) {
            // Given text does not include digits.
            year.deletePreviousChar();
        } else if (year.getText().length() == 2) {
            dateBox.setStyle("-fx-border-color: black;");
            checkPrecedence();
        } else {
            dateBox.setStyle("-fx-border-color: red;");
            shoppingCartController.disableNextButton(true);
            saveInformationBox.setDisable(true);
        }
    }

    @FXML
    private void cvcTypeCheck() {
        if (cvc.getText().length() > 3) {
            cvc.deletePreviousChar();
        } else if (!cvc.getText().matches("\\d+")) {
            // Given text does not include digits.
            cvc.deletePreviousChar();
        } else if (cvc.getText().length() == 3) {
            cvc.setStyle(orgStyle);
            checkPrecedence();
        } else {
            cvc.setStyle(errorStyle);
            shoppingCartController.disableNextButton(true);
            saveInformationBox.setDisable(true);
        }
    }

    private void prepopulateFields(CreditCard creditCard) {
        if (creditCard.getValidYear() == 0) {
            year.setText("");
        } else {
            year.setText(String.valueOf(creditCard.getValidYear()));
        }

        if (creditCard.getValidMonth() == 0) {
            month.setText("");
        } else {
            month.setText(String.valueOf(creditCard.getValidMonth()));
        }

        if (creditCard.getVerificationCode() == 0) {
            cvc.setText("");
        } else {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
        }
        name.setText(creditCard.getHoldersName());
        cardNumber.setText(creditCard.getCardNumber());
    }

    @FXML
    private void checkPrecedence() {
        markUnsolvedTextFields();
        for (TextField textField : textFields) {
            if (textField.getText().equals("") || cardTypeSelector.getSelectionModel().selectedItemProperty().getValue() == null) {
                // There is no information in the text field.
                shoppingCartController.disableNextButton(true);
                saveInformationBox.setDisable(true);
            }
        }
    }

    private void markUnsolvedTextFields() {
        for (TextField textField : textFields) {
            if (textField.getText().equals("") ) {
                // One text field is left empty.
                if (textField.equals(month) || textField.equals(year)){
                    // Month or year field is left empty.
                    dateBox.setStyle("-fx-border-color: red;");
                } else {
                    textField.setStyle(errorStyle);
                }
            } else {
                // This text fields have been filled with information.
                if (textField.getStyle().equals(errorStyle)) {
                    textField.setStyle(orgStyle);
                }
                shoppingCartController.disableNextButton(false);
                saveInformationBox.setDisable(false);
            }
        }

        if (cardTypeSelector.getSelectionModel().selectedItemProperty().getValue() == null) {
            // No card have been selected.
            cardTypeSelector.setStyle(errorStyle);
        } else {
            cardTypeSelector.setStyle(orgStyle);
        }
    }

    public boolean permissionToSaveInfo() {
        return saveInformationBox.isSelected();
    }

    public void saveInformation() {
        creditCard.setCardNumber(cardNumber.getText());
        creditCard.setHoldersName(name.getText());
        //creditCard.setVerificationCode(Integer.parseInt(cvc.getText())); Do not save for safety reasons...
        creditCard.setValidYear(Integer.parseInt(year.getText()));
        creditCard.setValidMonth(Integer.parseInt(month.getText()));
        // creditCard.setCardType(cardTypeSelector.selectionModelProperty().getName());
    }
}
