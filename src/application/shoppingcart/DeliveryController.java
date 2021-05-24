package application.shoppingcart;

import application.Model;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
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

    private void prepopulateFields (Customer customer) {
        address.setText(customer.getAddress());
        zipcode.setText(customer.getPostAddress());
        city.setText(customer.getLastName());
        name.setText(customer.getFirstName());
        telephone.setText(customer.getPhoneNumber());
    }
}