package application;

import application.categories.CategoryCenterPanelController;
import application.categories.CategoryLeftPanelController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import se.chalmers.cse.dat216.project.*;

import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.List;
import java.util.ResourceBundle;

/**
 * Controller class of iMat. This class builds from iMat Mini
 * @author Philip Winsnes
 */

public class MainController implements Initializable, ShoppingCartListener {

    @FXML private BorderPane overlayPane;
    @FXML private BorderPane homePagePane;
    @FXML private BorderPane shoppingCartBorderPane;
    @FXML private AnchorPane shoppingCartLayerPane;
    @FXML private TextField searchField;
    @FXML private AnchorPane accountPane;
    @FXML private AnchorPane homePane;
    @FXML private ListView<String> profileList;
    @FXML private Label highSum;
    @FXML private Label lowSum;
    @FXML private Label cartSumSymbol;
    @FXML private Rectangle headerDim;
    @FXML private ImageView gilladeVarorIkon;
    @FXML private ImageView listIkon;
    @FXML private AnchorPane accountPaneBox;

    // private String previousSelectedCategory;
    ObservableList observableCategoriesList = FXCollections.observableArrayList();
    ObservableList observableProfileList = FXCollections.observableArrayList();
    private boolean likePageIsShown = false;
    private boolean shoppingListIsShown = false;

    private final Model model = Model.getInstance();
    private FxmlLoader fxmlLoader = new FxmlLoader();
    private static MainController mainController;

    private Pane homePage = fxmlLoader.getPage("Home");
    private Pane wizardPane;
    private Pane categoryPane;
    private Pane likedItemsPane;

    private Pane shoppingListPane;
    private Pane shoppingListCataloguePane;
    private Pane shoppingCartPane;

    private Pane lastLoadedPane;
    private Pane categoryLeftPanel;
    private Pane categoryCenterPanel;
    private Pane searchPane;

    private Pane orderLeftPanel;
    private Pane orderCenterPanel;

    public String getSelectedProfileOption() {
        return profileList.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void displayFromProfileList(MouseEvent event) {
        Pane view = new Pane();
        switch (getSelectedProfileOption()) {
            case "Orderhistorik":
                closeTabs();
                lastLoadedPane = view = fxmlLoader.getPage("orderhistory/OrderCenterPanel");
                showOrderHistory();
                break;
            /*
            case "Dolda varor":
                lastLoadedPane = view = fxmlLoader.getPage("hiddenitems/HiddenItems");
                break;
             */
            case "Leveransinformation":
                closeTabs();
                lastLoadedPane = view = fxmlLoader.getPage("DeliveryOptions");
                homePagePane.setLeft(null);
                break;
            case "Betalningssätt":
                closeTabs();
                lastLoadedPane = view = fxmlLoader.getPage("PaymentOptions");
                homePagePane.setLeft(null);
                break;
            default: System.out.println("Unrecognizable selection");
        }
        model.setSelectedProfileOption(getSelectedProfileOption());
        profileList.getSelectionModel().clearSelection();
        homePagePane.setCenter(view);
        closeAccountView();
    }

    public static MainController getInstance() {
        if (mainController == null) {
            System.out.println("ERROR: MainController is null!");
        }
        return mainController;
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
        accountPaneBox.setOnMouseClicked(Event::consume);
        mainController = this;
        model.getShoppingCart().addShoppingCartListener(this);
        model.getShoppingCart().clear();
        //loadCategoriesList();
        loadProfileList();
        showCategories();
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
        searchField.clear();
        homePagePane.setCenter(homePage);
        showCategories();
        closeAccountView();
        lastLoadedPane = homePage;
        if (likePageIsShown) { closeLikedItems(); }
        else if (shoppingListIsShown) { closeShoppingList(); }
    }

    public void runWizard() {
        goHome();
        wizardPane = fxmlLoader.getPage("wizard/WizardWindow");
        overlayPane.toFront();
        dimHeader();
        overlayPane.setCenter(wizardPane);
    }

    public void showCategories() {
        categoryLeftPanel = fxmlLoader.getPage("categories/CategoryLeftPanel");
        homePagePane.setLeft(categoryLeftPanel);
    }

    private void showOrderHistory() {
        orderLeftPanel = fxmlLoader.getPage("orderhistory/OrderLeftPanel");
        homePagePane.setLeft(orderLeftPanel);
    }

    public void showCategoryCenterView() {
        searchField.clear();
        categoryCenterPanel = fxmlLoader.getPage("categories/CategoryCenterPanel");
        homePagePane.setCenter(categoryCenterPanel);
    }

    public void showOrderCenterView() {
        searchField.clear();
        orderCenterPanel = fxmlLoader.getPage("orderhistory/OrderCenterPanel");
        homePagePane.setCenter(orderCenterPanel);
    }

   public void goToLikedItems() {
        if (likePageIsShown) {
            closeLikedItems();
        }
        else {
            if (shoppingListIsShown) { closeShoppingList(); }
            openLikedItems();
        }
   }

    public void openListDialog() {
        Pane ListDialog = fxmlLoader.getPage("ShoppingListDialog");
        unDimHeader();
        overlayPane.toFront();
        overlayPane.setCenter(ListDialog);
    }

    public void openRemoveListDialog() {
        Pane RemoveListDialog = fxmlLoader.getPage("shoppinglist/RemoveListDialog");
        unDimHeader();
        overlayPane.toFront();
        overlayPane.setCenter(RemoveListDialog);
    }

    public void openEditListDialogue() {
        Pane editListDialogue = fxmlLoader.getPage("shoppinglist/editListDialogue");
        unDimHeader();
        overlayPane.toFront();
        overlayPane.setCenter(editListDialogue);
    }

    public void openShoppingCart() {
        shoppingCartPane = fxmlLoader.getPage("shoppingcart/ShoppingCartWindow");
        unDimHeader();
        overlayPane.toFront();
        overlayPane.setCenter(shoppingCartPane);
    }

   private void closeLikedItems() {
       homePagePane.setCenter(lastLoadedPane);
       showCategories();
       likePageIsShown = false;
       gilladeVarorIkon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart.png")));
   }

   private void openLikedItems() {
       likedItemsPane = fxmlLoader.getPage("likeditems/LikedItems");
       homePagePane.setCenter(likedItemsPane);
       homePagePane.setLeft(null);
       likePageIsShown = true;
       gilladeVarorIkon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_heavy_selected.png")));
   }

   public void goToShoppingList() {
       if (shoppingListIsShown) {
           closeShoppingList();
       }
       else {
           if (likePageIsShown) { closeLikedItems(); }
           openShoppingList();
       }
   }

    public void closeShoppingList() {
        homePagePane.setCenter(lastLoadedPane);
        showCategories();
        shoppingListIsShown = false;
        listIkon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/list.png")));
    }

    private void openShoppingList() {
        shoppingListPane = fxmlLoader.getPage("shoppinglist/shoppingList");
        shoppingListCataloguePane = fxmlLoader.getPage("shoppinglist/shoppingListCatalogue");
        homePagePane.setCenter(shoppingListPane);
        homePagePane.setLeft(shoppingListCataloguePane);
        shoppingListIsShown = true;
        listIkon.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/list_heavy_selected.png")));
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
     */
    @FXML
    private void handleSearchAction() {
        System.out.println("Searched after " + searchField.getText());
        String searchTerm = searchField.getText();
        model.setCurrentSearchTerm(searchTerm);
        searchPane = fxmlLoader.getPage("Search");
        lastLoadedPane = searchPane;
        homePagePane.setCenter(searchPane);
    }

    /**
     * Handles the clear cart action after e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    private void handleClearCartAction(ActionEvent event) {
        model.clearShoppingCart();
    }

    /* TODO: MOVE TO MODEL
    /**
     * This methods loads the categories into the list.
     * The observable list (observableCategoriesList) has the data and gets loaded into
     * the ListView item (categoriesList). The observable list gets its initial data removed at first
     * to prevent duplicates.
    private void loadCategoriesList() {
        observableCategoriesList.removeAll();
        observableCategoriesList.addAll("Bageri", "Dryck", "Frukt & Grönt", "Fisk & Skaldjur", "Godis & Snacks", "Kött", "Mejeri", "Skafferi");
        categoriesList.getItems().addAll(observableCategoriesList);
    }
    */

    /**
     * This methods loads the alternatives into the list under the profile view.
     * The observable list (observableCategoriesList) has the data and gets loaded into
     * the ListView item (categoriesList). The observable list gets its initial data removed at first
     * to prevent duplicates.
     */
    private void loadProfileList() {
        observableProfileList.removeAll();
        observableProfileList.addAll("Orderhistorik", "Leveransinformation", "Betalningssätt");
        profileList.getItems().addAll(observableProfileList);
    }

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
        homePagePane.toFront();
    }

    public void closeOverlayWindow() {
        overlayPane.toBack();
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

    private void dimHeader() {
        headerDim.setVisible(true);
    }

    private void unDimHeader() {
        headerDim.setVisible(false);
    }

    public BorderPane getHomePagePane() {
        return homePagePane;
    }

    private void closeTabs() {
        if (likePageIsShown) { closeLikedItems(); }
        else if (shoppingListIsShown) { closeShoppingList(); }
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
