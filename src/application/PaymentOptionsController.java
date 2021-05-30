package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.CreditCard;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PaymentOptionsController implements Initializable {

    MainController mainController = application.MainController.getInstance();
    Model model = Model.getInstance();
    CreditCard creditCard = model.getCreditCard();
    @FXML private Button saveButton;
    @FXML private TextField name;
    @FXML private TextField cardNumber;
    @FXML private TextField month;
    @FXML private TextField year;
    @FXML private TextField cvc;
    @FXML private ComboBox cardTypeSelector;
    @FXML private HBox dateBox;
    @FXML private Label saved;

    List<TextField> textFields = new ArrayList<>();

    private String errorStyle = "-fx-border-color: red; -fx-prompt-text-fill: #FF8888;";
    private String orgStyle = "-fx-border-color: black; -fx-prompt-text-fill: grey;";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initTextFields();
        saveButton.setDisable(true);
        prepopulateFields(creditCard);
        cardTypeSelector.getItems().add("MasterCard");
        cardTypeSelector.getItems().add("Visa");
    }

    private void initTextFields() {
        textFields.add(name);
        textFields.add(cardNumber);
        textFields.add(month);
        // textFields.add(cvc);
        textFields.add(year);
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
            saveButton.setDisable(true);
            saved.setVisible(false);
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
            saveButton.setDisable(true);
            saved.setVisible(false);
        }
    }

    @FXML
    public void saveButtonPressed() {
        saved.setVisible(true);
        saveButton.setDisable(true);

        creditCard.setCardNumber(cardNumber.getText());
        creditCard.setHoldersName(name.getText());
        //creditCard.setVerificationCode(Integer.parseInt(cvc.getText())); Do not save for safety reasons...
        creditCard.setValidYear(Integer.parseInt(year.getText()));
        creditCard.setValidMonth(Integer.parseInt(month.getText()));
        creditCard.setCardType(cardTypeSelector.selectionModelProperty().getName());
        System.out.println("The saved card type was: " + creditCard.getCardType());
    }

    @FXML
    public void backButtonPressed() {
        mainController.goHome();
        mainController.openAccountView();
    }

    private void prepopulateFields (CreditCard creditCard) {
        if (creditCard.getValidYear() == 0) {
            year.setText("00");
        } else {
            year.setText(String.valueOf(creditCard.getValidYear()));
        }

        if (creditCard.getValidMonth() == 0) {
            month.setText("00");
        } else {
            month.setText(String.valueOf(creditCard.getValidYear()));
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
            if (textField.getText().isEmpty() || cardTypeSelector.getSelectionModel().selectedItemProperty().getValue() == null) {
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
            }
        }

        if (cardTypeSelector.getSelectionModel().selectedItemProperty().getValue() == null) {
            // No card have been selected.
            cardTypeSelector.setStyle(errorStyle);
        } else {
            cardTypeSelector.setStyle(orgStyle);
        }
    }

}
