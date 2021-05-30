package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import se.chalmers.cse.dat216.project.*;

import java.io.IOException;
import java.math.RoundingMode;
import java.text.NumberFormat;

/**
 * This class represents a product card including the FXML-elements added in ProductCard.fxml.
 * It builds from iMat Mini's ProductPanel class. It extends AnchorPane since it needs to represent
 * a node.
 * @author Philip Winsnes
 */
public class ProductCard extends AnchorPane implements ShoppingCartListener {

    @FXML
    private ImageView productImage;
    @FXML
    private Label productName;
    @FXML
    private Label priceLabel;
    @FXML
    private Label ecoLabel;
    @FXML
    private AnchorPane buttonRestore;
    @FXML
    private AnchorPane buttonAdd;
    @FXML
    private HBox buttonGroup;
    @FXML
    private ImageView likeButton;
    //private ImageView hideButton;
    @FXML
    private ImageView addToListButton;
    @FXML
    private Label highSum;
    @FXML
    private Label lowSum;
    @FXML
    private AnchorPane ecoLabelAnchorPane;
    @FXML
    private AnchorPane amountPanel;
    private AnchorPane previousPanel = new AnchorPane();
    @FXML
    private TextField productAmount;
    @FXML
    private Label unitLabel;

    @FXML Button incButton;
    @FXML Button decButton;

    private int testAmount;
    private boolean productIsInHistoryOrList;



    /**
     * Wrapper class of the data handler that holds some backend functionalities.
     */
    private Model model = Model.getInstance();
    /**
     * The card's product.
     */
    private Product product;
    ShoppingItem shoppingItem = new ShoppingItem(product);
    private MainController mainController;

    public ProductCard(Product product) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/application/ProductCard.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        model.getShoppingCart().addShoppingCartListener(this);
        selectButtonPanel(buttonAdd);


        this.product = product;
        this.shoppingItem.setProduct(product);
        this.unitLabel.setText(product.getUnitSuffix());
        this.productAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
        this.productImage.setImage(model.getImage(product));
        this.productName.setText(product.getName());
        priceLabel.setText(String.format("%.2f", product.getPrice()) + " " + product.getUnit());
        updateSumLabels(product.getPrice());

        if (!product.isEcological()) {
            ecoLabel.setText("");
            ecoLabelAnchorPane.setVisible(false);
        }

        if(model.getFavourites().contains(product)){
            likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_red.png")));
            buttonGroup.setVisible(true);
           
            addToListButton.setVisible(false);
        }
        checkIfHidden();
    }

    public void disableAddButton() {
        buttonAdd.setVisible(false);
        incButton.setVisible(false);
        decButton.setVisible(false);
        productAmount.setEditable(false);
        productIsInHistoryOrList = true;
    }

    public void setAmount(int amount) {
        productAmount.setText(Integer.toString(amount));
    }

    private void unselectButtonPanel(AnchorPane panel) {
        panel.setVisible(false);
        panel.setDisable(true);
    }

    private void selectButtonPanel(AnchorPane panel) {
        unselectButtonPanel(previousPanel);
        previousPanel = panel;
        panel.setVisible(true);
        panel.setDisable(false);
        panel.toFront();
    }

    private void checkIfHidden() {
        if (isHidden()) {
            buttonAdd.setVisible(false);
            buttonAdd.setDisable(true);
            buttonRestore.setVisible(true);
            buttonRestore.setDisable(false);
        } else {
            buttonAdd.setVisible(true);
            buttonAdd.setDisable(false);
            buttonRestore.setVisible(false);
            buttonRestore.setDisable(true);
        }
    }

    @FXML
    private void informationChanged() {
        if (productAmount.getText().length() > 3) {
            productAmount.deletePreviousChar();
        } else if (!productAmount.getText().matches("\\d+")) {
            // Given text does not include digits.
            productAmount.deletePreviousChar();
        }
    }

    @FXML
    private void clearText() {
        productAmount.selectAll();
    }

    @FXML
    private void updateAmount() {
        int amount;
        if (productAmount.getText().equals("")) {
            amount = 1;
        } else {
            amount = Integer.valueOf(productAmount.getText());
        }

        if (amount == 0) {
            deleteItem();
        } else {
            shoppingItem.setAmount(amount);
            model.getShoppingCart().removeItem(shoppingItem);
            model.getShoppingCart().addItem(shoppingItem);
            productAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
        }
    }

    /**
     * Adds the product to the shopping cart after e.g. a button tap.
     * @param event is the action event.
     */
    @FXML
    public void handleAddAction(ActionEvent event) {
        selectButtonPanel(amountPanel);
        model.getShoppingCart().addItem(shoppingItem);
    }

    @FXML
    private void onMouseEntered() {
        if(!isHidden()){
            buttonGroup.setVisible(true);
            if(model.getFavourites().contains(product)){
                likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_red.png")));
                addToListButton.setVisible(true);
            }
            updateHideButton();
        }

    }

    @FXML
    private void onMouseExit() {
        if(model.getFavourites().contains(product)) {
            likeButton.setVisible(true);
            addToListButton.setVisible(false);
            }

            else  {
                buttonGroup.setVisible(false);
            }
    }

    @FXML
    private void likeItem() {
        if(!model.getFavourites().contains(product)){
            model.addFavourite(product);
        }
        else {
            model.removeFavourite(product);
        }
        updateHideButton();

    }

    @FXML
    private void hideItem() {
        //System.out.println(product.getName() + " hidden");
        model.addToHiddenProductList(product);
        System.out.println(model.getHiddenProductList());
    }

    @FXML
    private void restoreHiddenItem() {
        model.removeFromHiddenProductList(product);
    }

    @FXML
    private void addItemToList() {
        this.mainController = MainController.getInstance();
        model.addProductToList(shoppingItem);
        mainController.openListDialog();
    }

    @FXML
    public void incrementAmount() {
        shoppingItem.setAmount(shoppingItem.getAmount() + 1);
        model.getShoppingCart().removeItem(shoppingItem);
        model.getShoppingCart().addItem(shoppingItem);
        productAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
    }

    @FXML
    public void decrementAmount() {
        if (shoppingItem.getAmount() > 1) {
            shoppingItem.setAmount(shoppingItem.getAmount() - 1);
            model.getShoppingCart().removeItem(shoppingItem);
            model.getShoppingCart().addItem(shoppingItem);
            productAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
        } else {
            // The user would like to remove the item.
            deleteItem();
        }
    }

    @FXML
    public void deleteItem() {
        shoppingItem.setAmount(1);
        model.getShoppingCart().removeItem(shoppingItem);
        productAmount.setText(Integer.toString((int) shoppingItem.getAmount()));
        selectButtonPanel(buttonAdd);
    }

    private Boolean isHidden() {
        if(model.getHiddenProductList().contains(product)){ return true; }
        else { return false; }
    }

    private void updateHideButton() {
        boolean toggle;

        if(model.getFavourites().contains(product)){
            likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart_red.png")));
            toggle = true;
        }
        else {
            likeButton.setImage(new Image(getClass().getClassLoader().getResourceAsStream("application/icons/heart.png")));
            toggle = false;
        }

        //hideButton.setVisible(!toggle);
        //hideButton.setDisable(toggle);
    }

    private void updateSumLabels(Double productPrice) {
        highSum.setText(getHighFormatSum(productPrice));
        lowSum.setText(getLowFormatSum(productPrice));
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
    public void shoppingCartChanged(CartEvent cartEvent) {
        if (productIsInHistoryOrList) {
            //do nothing
        } else {
            int amount = (int) shoppingItem.getAmount();
            productAmount.setText(Integer.toString(amount));
            if (amount == 0) {
                deleteItem();
            }
        }
    }





}
