package application.shoppingcart;

import application.FxmlLoader;
import application.Model;
import application.MainController;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import se.chalmers.cse.dat216.project.CartEvent;
import se.chalmers.cse.dat216.project.ShoppingCartListener;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ShoppingCartController implements Initializable, ShoppingCartListener {

    FxmlLoader fxmlLoader = new FxmlLoader();

    List<Pane> shoppingCartViews = new ArrayList<>();
    List<Circle> shoppingPaneCircleGuides = new ArrayList<>();
    private MainController mainController = MainController.getInstance();

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

    private Circle shoppingPaneCircleGuideReserved = new Circle(); // This circle is not shown but needed for indexing.

    List<String> titles = new ArrayList<>();

    Pane shoppingCart = fxmlLoader.getPage("shoppingcart/ShoppingCart");
    Pane shoppingCartDeliveryOptions1 = fxmlLoader.getPage("shoppingcart/ShoppingCartDeliveryOptions1");
    Pane shoppingCartDeliveryOptions2 = fxmlLoader.getPage("shoppingcart/ShoppingCartDeliveryOptions2");
    Pane shoppingCartPaymentOptions = fxmlLoader.getPage("shoppingcart/ShoppingCartPaymentOptions");
    Pane shoppingCartThanksForPurchasing = fxmlLoader.getPage("shoppingcart/ShoppingCartThanksForPurchasing");

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initShoppingCart();

        titles.add("Kundvagn");
        titles.add("Leveransalternativ");
        titles.add("Leveransalternativ");
        titles.add("Betalningsalternativ");
        titles.add("");
        pageTitle.setText("Kundvagn");

        shoppingCartBorderPane.setOnMouseClicked(Event::consume);
        closeButton.setVisible(false);
        backButton.setVisible(false);
    }

    @Override
    public void shoppingCartChanged(CartEvent cartEvent) {
        //flowPaneController.loadProducts();
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
            nextButton.setText("   Köp");
            blueChevron.setVisible(false);
            nextButton.setPadding(new Insets(5.7,26,5.7,27));
            String rgb = String.format("%d, %d, %d, 0.75", 0, (int) 201, (int) 0);
            String hover = String.format("%d, %d, %d, 0.75", 0, (int) 250, (int) 8);
            nextButton.setStyle("-fx-background-color: rgba(" + hover + "); -fx-text-fill: white;");
            nextButton.setOnMouseEntered(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + hover + "); -fx-text-fill: white;"));
            nextButton.setOnMouseExited(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + rgb + "); -fx-text-fill: white;"));
        }
        if (thisPanelIndex > shoppingCartViews.size()-3) {
            hideNavigation();
            closeButton.setVisible(true);
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
        if (thisPanelIndex == shoppingCartViews.size()-2) {
            nextButton.setPadding(new Insets(5.7,11.3,5.7,11.3));
            nextButton.setText("Gå vidare");
            blueChevron.setVisible(true);
            String rgb = String.format("%d, %d, %d, 1.0", 0, (int) 117, (int) 255);
            String hover = String.format("%d, %d, %d, 0.1", 0, (int) 0, (int) 0);
            nextButton.setStyle("-fx-background-color: white; -fx-text-fill: rgba(" + rgb + ");");
            nextButton.setOnMouseEntered(mouseEvent -> nextButton.setStyle("-fx-background-color: rgba(" + hover + ");"));
            nextButton.setOnMouseExited(mouseEvent -> nextButton.setStyle("-fx-background-color: transparent"));
        }
    }
}
