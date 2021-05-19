package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.*;

import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class of iMat. This class builds from iMat Mini
 * @author Philip Winsnes
 */

public class iMatController implements Initializable, ShoppingCartListener {

    /** FXML-elements **/
    @FXML private BorderPane mainPane;
    @FXML private BorderPane shoppingCartBorderPane;
    @FXML private AnchorPane shoppingCartLayerPane;

    @FXML private TextArea searchField;
    @FXML private AnchorPane accountPane;
    @FXML private AnchorPane homePane;
    @FXML private ListView<String> categoriesList;
    @FXML private ListView<String> profileList;
    @FXML private Label highSum;
    @FXML private Label lowSum;
    @FXML private Label cartSumSymbol;
    // @FXML private HBox cartSum;
    @FXML private Circle shoppingPaneCircleGuide1;
    @FXML private Circle shoppingPaneCircleGuide2;
    @FXML private Circle shoppingPaneCircleGuide3;
    @FXML private Circle shoppingPaneCircleGuide4;
    private Circle shoppingPaneCircleGuideReserved = new Circle(); // This circle is not shown but needed for indexing.

    /** Instances **/
    private FxmlLoader fxmlLoader = new FxmlLoader();
    private String previousSelectedCategory;
    ObservableList observableCategoriesList = FXCollections.observableArrayList();
    ObservableList observableProfileList = FXCollections.observableArrayList();

    Pane shoppingCart = fxmlLoader.getPage("ShoppingCart");
    Pane shoppingCartDeliveryOptions1 = fxmlLoader.getPage("ShoppingCartDeliveryOptions1");
    Pane shoppingCartDeliveryOptions2 = fxmlLoader.getPage("ShoppingCartDeliveryOptions2");
    Pane shoppingCartPaymentOptions = fxmlLoader.getPage("ShoppingCartPaymentOptions");
    Pane shoppingCartThanksForPurchasing = fxmlLoader.getPage("ShoppingCartThanksForPurchasing");

    List<Pane> shoppingCartViews = new ArrayList<>();

    List<Circle> shoppingPaneCircleGuides = new ArrayList<>();

    /**
     * Wrapper class that handles some backend functionalities.
     */
    private final Model model = Model.getInstance();

    public String getSelectedCategory() {
        return categoriesList.getSelectionModel().getSelectedItem();
    }

    public String getSelectedFromProfileList() {
        return profileList.getSelectionModel().getSelectedItem();
    }

    private String getPreviousSelectedCategory() {
        return previousSelectedCategory;
    }

    @FXML
    private void displayCategoryFromList(MouseEvent event) {
        Pane view;
        if (getSelectedCategory() == null || getSelectedCategory().isEmpty()) {
            // Nothing is selected. Return home.
            categoriesList.getSelectionModel().clearSelection();
            goHome();
        } else if (getSelectedCategory() == getPreviousSelectedCategory()) {
            // Same selection have been made. Return home.
            categoriesList.getSelectionModel().clearSelection();
            view = fxmlLoader.getPage("Home");
            mainPane.setCenter(view);
        } else {
            // A new category have been chosen. Show that category.
            view = fxmlLoader.getPage(getSelectedCategory());
            mainPane.setCenter(view);
        }
        previousSelectedCategory = getSelectedCategory();
    }

    @FXML
    private void displayFromProfileList(MouseEvent event) {
        Pane view = new Pane();
        switch (getSelectedFromProfileList()) {
            case "Orderhistorik":
                view = fxmlLoader.getPage("Orders");
                break;
            case "Dolda varor":
                view = fxmlLoader.getPage("HiddenItems");
                break;
            case "Leveransinformation":
                view = fxmlLoader.getPage("DeliveryOptions");
                break;
            case "Betalningssätt":
                view = fxmlLoader.getPage("PaymentOptions");
                break;
            default: System.out.println("Unrecognizable selection");
        }
        profileList.getSelectionModel().clearSelection();
        categoriesList.getSelectionModel().clearSelection();
        mainPane.setCenter(view);
        closeAccountView();
    }

    /**
     * When the program begins and MainView loads, this method gets executed.
     * The initialize method initiates the shopping cart, updates the product list,
     * bottom panel and account pane.
     * @param url
     * @param resourceBundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.getShoppingCart().addShoppingCartListener(this);
        model.getShoppingCart().clear();
        loadCategoriesList();
        loadProfileList();
        initShoppingCart();
        goHome();
    }



    private void updateSumLabels() {
        Double totalPrice = model.getShoppingCart().getTotal();
        highSum.setText(getHighFormatSum(totalPrice));
        lowSum.setText(getLowFormatSum(totalPrice));
    }

    /**
     * Formats the total price for the high label which only shows the integers.
     * @param value is the Double value being formatted.
     * @return the value in the new format casted as a String.
     */
    private String getHighFormatSum(Double value) {
        NumberFormat highFormat = NumberFormat.getNumberInstance();
        highFormat.setMaximumFractionDigits(0);
        highFormat.setRoundingMode(RoundingMode.FLOOR);
        return highFormat.format(value);
    }

    /**
     * Formats the total price for the low label which only shows the integers.
     * @param value is the Double value being formatted.
     * @return the value in the new format casted as a String.
     */
    private String getLowFormatSum(Double value) {
        // TODO: remove the decimal.
        NumberFormat lowFormat = NumberFormat.getNumberInstance();
        lowFormat.setMaximumIntegerDigits(0);
        lowFormat.setMinimumFractionDigits(2);
        lowFormat.setMaximumFractionDigits(2);
        return lowFormat.format(value);
    }

    public void goHome() {
        Pane view = fxmlLoader.getPage("Home");
        mainPane.setCenter(view);
        closeAccountView();
        categoriesList.getSelectionModel().clearSelection();
    }

    // Mark: Home pane actions
    /**
     * Opens the account view from e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    private void handleShowAccountAction(ActionEvent event) {
        openAccountView();
    }

    /**
     * Proceeds the search action by updating the product list
     * with the matching products of the text from the search field.
     * @param event is the action event.
     */
    @FXML
    private void handleSearchAction(ActionEvent event) {
        List<Product> matches = model.findProducts(searchField.getText());
        // updateProductList(matches);
        System.out.println("# matching products: " + matches.size());
    }

    /**
     * Handles the clear cart action after e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    private void handleClearCartAction(ActionEvent event) {
        model.clearShoppingCart();
    }

    // TODO: Look over this commented function
    /* We won't need this.
    /**
     * Purchases the order after e.g. a button tap.
     * @param event is the action event.
    @FXML
    private void handleBuyItemsAction(ActionEvent event) {
        model.placeOrder();
        costLabel.setText("Köpet klart!");
    }
    */

    /**
     * This methods loads the categories into the list.
     * The observable list (observableCategoriesList) has the data and gets loaded into
     * the ListView item (categoriesList). The observable list gets its initial data removed at first
     * to prevent duplicates.
     */
    private void loadCategoriesList() {
        observableCategoriesList.removeAll();
        observableCategoriesList.addAll("Mejeri", "Chark", "Frukt", "Godis", "Snacks");
        categoriesList.getItems().addAll(observableCategoriesList);
    }

    /**
     * This methods loads the alternatives into the list under the profile view.
     * The observable list (observableCategoriesList) has the data and gets loaded into
     * the ListView item (categoriesList). The observable list gets its initial data removed at first
     * to prevent duplicates.
     */
    private void loadProfileList() {
        observableProfileList.removeAll();
        observableProfileList.addAll("Orderhistorik", "Dolda varor", "Leveransinformation", "Betalningssätt");
        profileList.getItems().addAll(observableProfileList);
    }

    // Mark: Account pane actions
    /**
     * Closes the account view after e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    private void handleDoneAction(ActionEvent event) {
        closeAccountView();
    }

    // Mark: Navigation
    /**
     * Opens the account view.
     */
    public void openAccountView() {
        //updateAccountPanel();
        accountPane.toFront();
    }

    /**
     * Closes the account view.
     */
    public void closeAccountView() {
        //updateCreditCard();
        mainPane.toFront();
    }




    public void openShoppingCart() {
        shoppingCartLayerPane.toFront();
        shoppingCartBorderPane.setCenter(shoppingCartViews.get(0));
        fillCircleGuide(shoppingPaneCircleGuide1);
    }

    public void closeShoppingCart() {
        mainPane.toFront();
    }

    // Mark: Shopping pane methods
    /**
     * Updates the bottom panel whenever a change have been made to the shopping cart.
     * @param evt is the cart event.
     */
    @Override
    public void shoppingCartChanged(CartEvent evt) {
        updateSumLabels();
    }

    private void initShoppingCart() {
        initShoppingPanes();
        initShoppingGuideCircles();
    }

    private void initShoppingPanes() {
        shoppingCartViews.add(shoppingCart);
        shoppingCartViews.add(shoppingCartDeliveryOptions1);
        shoppingCartViews.add(shoppingCartDeliveryOptions2);
        shoppingCartViews.add(shoppingCartPaymentOptions);
        shoppingCartViews.add(shoppingCartThanksForPurchasing);
    }

    private void initShoppingGuideCircles() {
        shoppingPaneCircleGuides.add(shoppingPaneCircleGuide1);
        shoppingPaneCircleGuides.add(shoppingPaneCircleGuide2);
        shoppingPaneCircleGuides.add(shoppingPaneCircleGuide3);
        shoppingPaneCircleGuides.add(shoppingPaneCircleGuide4);
        shoppingPaneCircleGuides.add(shoppingPaneCircleGuideReserved); // This one does not show but are need for indexing.
    }

    private int getCurrentShoppingPaneIndex() {
        Node thisPane = shoppingCartBorderPane.getCenter();
        return shoppingCartViews.indexOf(thisPane);
    }

    private void fillCircleGuide(Circle circle) {
        circle.setFill(Paint.valueOf("blue"));
    }

    private void unfillCircleGuide(Circle circle) {
        circle.setFill(Paint.valueOf("grey"));
    }

    @FXML
    public void loadNextShoppingPanel() {
        int thisPanelIndex = getCurrentShoppingPaneIndex();
        if (thisPanelIndex < shoppingCartViews.size()-1) {
            // The last shopping pane are not selected.

            // Fill circles
            unfillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex));
            fillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex + 1));

            // Update the view.
            shoppingCartBorderPane.setCenter(shoppingCartViews.get(thisPanelIndex + 1));
        }
    }

    @FXML
    public void loadPreviousShoppingPanel() {
        int thisPanelIndex = getCurrentShoppingPaneIndex();
        if (thisPanelIndex > 0) {
            // The first shopping pane are not selected.

            // Fill circles
            unfillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex));
            fillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex - 1));

            // Update the view
            shoppingCartBorderPane.setCenter(shoppingCartViews.get(thisPanelIndex - 1));
        }
    }

    // TODO: Fix commented functions
    /* We might do something out of this one later.
    /**
     * Updates the account panel/information with the entered information.
    private void updateAccountPanel() {
        CreditCard card = model.getCreditCard();

        numberTextField.setText(card.getCardNumber());
        nameTextField.setText(card.getHoldersName());

        cardTypeCombo.getSelectionModel().select(card.getCardType());
        monthCombo.getSelectionModel().select(""+card.getValidMonth());
        yearCombo.getSelectionModel().select(""+card.getValidYear());

        cvcField.setText(""+card.getVerificationCode());

        purchasesLabel.setText(model.getNumberOfOrders()+ " tidigare inköp hos iMat");
    }
    */

    /* We might do something out of this one later.
    /**
     * Updates the credit card information with the entered information.
    private void updateCreditCard() {
        CreditCard card = model.getCreditCard();

        card.setCardNumber(numberTextField.getText());
        card.setHoldersName(nameTextField.getText());

        String selectedValue = (String) cardTypeCombo.getSelectionModel().getSelectedItem();
        card.setCardType(selectedValue);

        selectedValue = (String) monthCombo.getSelectionModel().getSelectedItem();
        card.setValidMonth(Integer.parseInt(selectedValue));

        selectedValue = (String) yearCombo.getSelectionModel().getSelectedItem();
        card.setValidYear(Integer.parseInt(selectedValue));

        card.setVerificationCode(Integer.parseInt(cvcField.getText()));
    }
    */

    /* We might do something out of this one later.
    /**
     * Setups the account pane.
    private void setupAccountPane() {
        cardTypeCombo.getItems().addAll(model.getCardTypes());

        monthCombo.getItems().addAll(model.getMonths());

        yearCombo.getItems().addAll(model.getYears());
    }
    */

}
