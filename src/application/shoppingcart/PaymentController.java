package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import se.chalmers.cse.dat216.project.CreditCard;

import java.net.URL;
import java.util.ResourceBundle;

public class PaymentController implements Initializable {

    Model model = Model.getInstance();
    CreditCard creditCard = model.getCreditCard();
    @FXML TextField name;
    @FXML TextField cardNumber;
    @FXML TextField date;
    @FXML TextField cvc;
    @FXML TextField cardType;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepopulateFields(creditCard);
    }

    @FXML
    public void InformationChanged() {
        creditCard.setCardNumber(cardNumber.getText());
        creditCard.setHoldersName(name.getText());
        creditCard.setCardType(cardType.getText());
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
