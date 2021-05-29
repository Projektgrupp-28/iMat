package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.CreditCard;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentOptionsController implements Initializable {

    MainController mainController = application.MainController.getInstance();
    Model model = Model.getInstance();
    CreditCard creditCard = model.getCreditCard();
    @FXML Button saveButton;
    @FXML TextField name;
    @FXML TextField cardNumber;
    @FXML TextField date;
    @FXML TextField cvc;
    @FXML TextField cardType;
    @FXML Label saved;

    String sCardNumber = "";
    String sName = "";
    String sDate = "";
    String sCVC = "";
    String sCardType = "";

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        saveButton.setDisable(true);
        prepopulateFields(creditCard);

        sCardNumber = creditCard.getCardNumber();
        sName = creditCard.getHoldersName();
        sDate = String.valueOf(creditCard.getValidYear());
        sCVC = String.valueOf(creditCard.getVerificationCode());
        sCardType = creditCard.getCardType();
    }

    @FXML
    public void TelephoneChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sCardType = cardType.getText();
    }
    @FXML
    public void CardNumberChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sCardNumber = cardNumber.getText();
    }
    @FXML
    public void NameChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        sName = name.getText();
    }
    @FXML
    public void DateChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        if (date.getText().length() >= 6) {
            date.deleteText(5,6);
        } else if (!date.getText().matches("\\d+")) {
            if (date.getText().matches("")) {
                sDate = date.getText();
            }
            if (date.getText().length() > 0) {
                date.deleteText(date.getText().length()-1,date.getText().length());
            }
        } else {
            sDate = date.getText();
        }
    }
    @FXML
    public void CVCChanged() {
        saved.setVisible(false);
        saveButton.setDisable(false);
        if (cvc.getText().length() >= 4) {
            cvc.deleteText(3,4);
        } else if (!cvc.getText().matches("\\d+")) {
            if (cvc.getText().matches("")) {
                sCVC = cvc.getText();
            }
            if (cvc.getText().length() > 0) {
                cvc.deleteText(cvc.getText().length()-1,cvc.getText().length());
            }
        } else {
            sCVC = cvc.getText();
        }
    }

    @FXML
    public void saveButtonPressed() {
        saved.setVisible(true);
        saveButton.setDisable(true);
        if (sDate.equals("")) {
            creditCard.setValidYear(0);
        } else {
            creditCard.setValidYear(Integer.parseInt(sDate));
        }
        if (sCVC.equals("")) {
            creditCard.setVerificationCode(0);
        } else {
            creditCard.setVerificationCode(Integer.parseInt(sCVC));
        }
        creditCard.setCardNumber(sCardNumber);
        creditCard.setCardType(sCardType);
        creditCard.setHoldersName(sName);
    }

    @FXML
    public void backButtonPressed() {
        mainController.goHome();
    }

    private void prepopulateFields (CreditCard creditCard) {
        if (creditCard.getValidYear() == 0) {
            date.setText("");
        } else {
            date.setText(String.valueOf(creditCard.getValidYear()));
        }
        if (creditCard.getVerificationCode() == 0) {
            cvc.setText("");
        } else {
            cvc.setText(String.valueOf(creditCard.getVerificationCode()));
        }
        cardType.setText(creditCard.getCardType());
        name.setText(creditCard.getHoldersName());
        cardNumber.setText(creditCard.getCardNumber());
    }
}
