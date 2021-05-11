package application;

import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;
import java.util.List;

/**
 * Wrapper around the IMatDataHandler. The idea is that it might be useful to
 * add an extra layer that can provide special features.
 * The constructor should never be called. Instead use getInstance().
 * @author Philip Winsnes
 */

public class Model {

    private static Model model;
    private IMatDataHandler iMatDataHandler;

    /**
     * To be used instead of the constructor.
     * Like singleton pattern.
     * @return the model instance.
     */
    public static Model getInstance() {
        if (model == null) {
            model = new Model();
            model.init();
        }
        return model;
    }

    /**
     * Initiates the data handler.
     */
    private void init() {
        iMatDataHandler = IMatDataHandler.getInstance();
    }

    /**
     * Returns a list of products.
     * @return a list of products.
     */
    public List<Product> getProducts() { return iMatDataHandler.getProducts(); }

    /**
     * Returns a indexed product.
     * @param idNbr is the index number.
     * @return the given product.
     */
    public Product getProduct(int idNbr) { return iMatDataHandler.getProduct(idNbr);}

    /**
     * For searching products.
     * @param s is the given search term.
     * @return the found product.
     */
    public List<Product> findProducts(java.lang.String s) {
        return iMatDataHandler.findProducts(s);
    }

    /**
     * Help method for getting product images.
     * @param p is the given product.
     * @return the product image.
     */
    public Image getImage(Product p) {
        return iMatDataHandler.getFXImage(p);
    }

    /**
     * Help method for getting product images with given dimensions.
     * @param p is the given product.
     * @param width is the image width.
     * @param height is the image height.
     * @return the product image.
     */
    public Image getImage(Product p, double width, double height) {
        return iMatDataHandler.getFXImage(p, width, height);
    }

    /**
     * For adding products to the shopping cart.
     * @param p is the product to be added.
     */
    public void addToShoppingCart(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();

        ShoppingItem item = new ShoppingItem(p);
        Model.getInstance().getShoppingCart().addItem(item);

        // shoppingCart.addProduct(p);
    }

    // TODO: Fix methods.
    /* This methods will be useful later.
    /**
     * Returns the accepted card types for payment.
     * @return a list of available card types.
    public List<String> getCardTypes() {
        return availableCardTypes;
    }

    public List<String> getMonths() {
        return months;
    }

    public List<String> getYears() {
        return years;
    }
    */

    // I don't know what this method does. // Philip.
    public CreditCard getCreditCard() {
        return iMatDataHandler.getCreditCard();
    }

    // I don't know what this method does. // Philip.
    public Customer getCustomer() {
        return iMatDataHandler.getCustomer();
    }

    /**
     * Returns the shopping cart
     * @return the shopping cart.
     */
    public ShoppingCart getShoppingCart() {
        return iMatDataHandler.getShoppingCart();
    }

    /**
     * Clears the shopping cart.
     */
    public void clearShoppingCart() {
        iMatDataHandler.getShoppingCart().clear();
    }

    /**
     * Places the order when a purchase proceeds.
     */
    public void placeOrder() {
        iMatDataHandler.placeOrder();
    }

    /**
     * Returns the number of orders that the customer have made.
     * @return the number of orders.
     */
    public int getNumberOfOrders() {
        return iMatDataHandler.getOrders().size();
    }

    /**
     * Shuts down the data handler.
     */
    public void shutDown() {
        iMatDataHandler.shutDown();
    }

}
