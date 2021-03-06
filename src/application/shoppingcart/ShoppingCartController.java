package application.shoppingcart;

import application.FxmlLoader;
import application.Model;
import application.MainController;
import application.PaymentOptionsController;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.math.RoundingMode;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable, ShoppingCartListener {

    private static ShoppingCartController shoppingCartController;
    FxmlLoader fxmlLoader = new FxmlLoader();

    List<Pane> shoppingCartViews = new ArrayList<>();
    List<Circle> shoppingPaneCircleGuides = new ArrayList<>();
    private MainController mainController = MainController.getInstance();
    private Model model = Model.getInstance();
    private DeliveryController deliveryController;
    private PaymentController paymentController;

    @FXML private BorderPane shoppingCartBorderPane;
    @FXML private Circle shoppingPaneCircleGuide1;
    @FXML private Circle shoppingPaneCircleGuide2;
    @FXML private Circle shoppingPaneCircleGuide3;
    @FXML private Circle shoppingPaneCircleGuide4;
    @FXML private Button nextButton;
    @FXML private Button backButton;
    @FXML private Button closeButton;
    @FXML private ImageView blueChevron;
    @FXML private Label pageTitle;
    @FXML private HBox sumBox;
    @FXML private Label highSum;
    @FXML private Label lowSum;

    private Circle shoppingPaneCircleGuideReserved = new Circle(); // This circle is not shown but needed for indexing.

    List<String> titles = new ArrayList<>();

    Pane shoppingCart;
    Pane shoppingCartDeliveryOptions1;
    Pane shoppingCartDeliveryOptions2;
    Pane shoppingCartPaymentOptions;
    Pane shoppingCartThanksForPurchasing;

    private void initShoppingPanes() {
        shoppingCart = fxmlLoader.getPage("shoppingcart/ShoppingCart");
        shoppingCartDeliveryOptions1 = fxmlLoader.getPage("shoppingcart/ShoppingCartDeliveryOptions1");
        shoppingCartDeliveryOptions2 = fxmlLoader.getPage("shoppingcart/ShoppingCartDeliveryOptions2");
        shoppingCartPaymentOptions = fxmlLoader.getPage("shoppingcart/ShoppingCartPaymentOptions");
        shoppingCartThanksForPurchasing = fxmlLoader.getPage("shoppingcart/ShoppingCartThanksForPurchasing");


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

    public static ShoppingCartController getInstance() {
        if (shoppingCartController == null) {
            System.out.println("ERROR: ShoppingCartController is null!");
        }
        return shoppingCartController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.getShoppingCart().addShoppingCartListener(this);

        shoppingCartController = this;

        initShoppingCart();

        titles.add("Kundvagn");
        titles.add("Leveransinformation");
        titles.add("Leveransalternativ");
        titles.add("Betalningsalternativ");
        titles.add("");
        pageTitle.setText("Kundvagn");

        updateSumLabels();

        shoppingCartBorderPane.setOnMouseClicked(Event::consume);
        closeButton.setVisible(false);
        backButton.setVisible(false);
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

    @Override
    public void shoppingCartChanged(CartEvent evt) {
        //flowPaneController.loadProducts();
        System.out.println("Hello");
        updateSumLabels();
    }

    private void initShoppingCart() {
        initShoppingPanes();
        initShoppingGuideCircles();
        shoppingCartBorderPane.setCenter(shoppingCartViews.get(0));
        fillCircleGuide(shoppingPaneCircleGuides.get(0));
    }

    private int getCurrentShoppingPaneIndex() {
        Node thisPane = shoppingCartBorderPane.getCenter();
        return shoppingCartViews.indexOf(thisPane);
    }

    private void fillCircleGuide(Circle circle) {
        circle.setFill(Paint.valueOf("#0075ff"));
        circle.setRadius(10.0);
    }

    private void unfillCircleGuide(Circle circle) {
        circle.setFill(Paint.valueOf("#D3D3D3"));
        circle.setRadius(8.0);
    }

    private void hideNavigation () {
        for (int i = 0; i < shoppingPaneCircleGuides.size(); i++) {
            shoppingPaneCircleGuides.get(i).setVisible(false);
        }
        backButton.setVisible(false);
        nextButton.setVisible(false);
    }

    private void hideBackButton () {
        backButton.setVisible(false);
    }

    private void showBackButton () {
        backButton.setVisible(true);
    }

    @FXML
    public void outsideClicked() {
        mainController.closeOverlayWindow();
    }

    @FXML
    public void closeButtonPressed() {
        mainController.closeOverlayWindow();
    }

    @FXML
    public void loadNextShoppingPanel() {
        int thisPanelIndex = getCurrentShoppingPaneIndex();
        pageTitle.setText(titles.get(thisPanelIndex+1));
        if (thisPanelIndex == 0){
            showBackButton();
        }
        if (thisPanelIndex > shoppingCartViews.size()-4) {
            nextButton.setText("   K??p");
            blueChevron.setVisible(false);
            nextButton.setPadding(new Insets(5.7,26,5.7,27));
            String rgb = String.format("%d, %d, %d, 0.75", 0, (int) 201, (int) 0);
            String hover = String.format("%d, %d, %d, 0.75", 0, (int) 250, (int) 8);
            nextButton.setStyle("-fx-background-color: rgba(" + hover + "); -fx-text-fill: white;");
            nextButton.setOnMouseEntered(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + hover + "); -fx-text-fill: white;"));
            nextButton.setOnMouseExited(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + rgb + "); -fx-text-fill: white;"));
        }

        sumBox.setVisible(false);

        if (thisPanelIndex > shoppingCartViews.size()-3) {
            // User have orderd the shopping cart
            hideNavigation();
            closeButton.setVisible(true);
            createOrder();

            deliveryController = DeliveryController.getInstance();
            paymentController = PaymentController.getInstance();
            if (deliveryController.permissionToSaveInfo()) {
                deliveryController.saveInformation();
                System.out.println("Delivery information saved!");
            } else if (paymentController.permissionToSaveInfo()) {
                paymentController.saveInformation();
                System.out.println("Payment information saved!");
            }
        }
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
        pageTitle.setText(titles.get(thisPanelIndex-1));
        if (thisPanelIndex > 0) {
            // The first shopping pane are not selected.

            // Fill circles
            unfillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex));
            fillCircleGuide(shoppingPaneCircleGuides.get(thisPanelIndex - 1));

            // Update the view
            shoppingCartBorderPane.setCenter(shoppingCartViews.get(thisPanelIndex - 1));
        }
        if (thisPanelIndex == 1) {
            hideBackButton();
        }
        if (thisPanelIndex == 1) {
            sumBox.setVisible(true);
        } else {
            sumBox.setVisible(false);
        }
        if (thisPanelIndex == shoppingCartViews.size()-2) {
            nextButton.setPadding(new Insets(5.7,11.3,5.7,11.3));
            nextButton.setText("G?? vidare");
            blueChevron.setVisible(true);
            String rgb = String.format("%d, %d, %d, 1.0", 0, (int) 117, (int) 255);
            String hover = String.format("%d, %d, %d, 0.1", 0, (int) 0, (int) 0);
            nextButton.setStyle("-fx-background-color: white; -fx-text-fill: rgba(" + rgb + ");");
            nextButton.setOnMouseEntered(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + hover + ");"));
            nextButton.setOnMouseExited(mouseEvent -> nextButton.setStyle("-fx-background-color: transparent"));
        }
    }

    public void disableNextButton(boolean b) {
        nextButton.setDisable(b);
    }

    public void createOrder() {
        model.createOrder();
    }
}
