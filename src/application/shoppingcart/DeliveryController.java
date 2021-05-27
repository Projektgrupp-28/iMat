package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.paint.Color;
import se.chalmers.cse.dat216.project.Customer;

import java.net.URL;
import java.util.ResourceBundle;

public class DeliveryController implements Initializable {
    private Model model = Model.getInstance();
    Customer customer = model.getCustomer();
    @FXML TextField address;
    @FXML TextField zipcode;
    @FXML TextField city;
    @FXML TextField name;
    @FXML TextField telephone;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        prepopulateFields(customer);
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
            System.out.println("Complete!");
            zipcode.setStyle("-fx-border-color: green");
        } else {
            System.out.println("Incomplete");
            zipcode.setStyle("-fx-border-color: red");
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
}