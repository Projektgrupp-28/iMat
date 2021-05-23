package application;

import application.hiddenitems.HiddenProductListener;
import application.likeditems.LikedProductListener;
import application.shoppinglist.shoppingList;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Wrapper around the IMatDataHandler. The idea is that it might be useful to
 * add an extra layer that can provide special features.
 * The constructor should never be called. Instead use getInstance().
 * @author Philip Winsnes
 */

public class Model {

    private static Model model;
    private ListObject catalogList = new ListObject();
    private ListObject profileList = new ListObject();
    private IMatDataHandler iMatDataHandler;
    private iMatController iMatController;
    private List<Product> hiddenProductList = new ArrayList<>();
    private ArrayList<HiddenProductListener> hiddenProductListenersList = new ArrayList();
    private ArrayList<LikedProductListener> likedProductListenersList = new ArrayList<>();
    private List<shoppingList> shoppingListList = new ArrayList<>();
    private int nrOfShoppingLists = 0;
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


    public List<Product> getProducts(ProductCategory pc) {
        return iMatDataHandler.getProducts(pc);
    }

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

    /**
     *
     * Adds product to hiddenProductList
     */
    public void addToHiddenProductList(Product p) {
        if(!hiddenProductList.contains(p)) {
            hiddenProductList.add(p);
            fireHiddenProductChanged();
        }
        else { System.out.println(p + " is already hidden"); }
    }

    /**
     *
     * Removes product from hiddenProductList
     */
    public void removeFromHiddenProductList(Product p) {
        if(hiddenProductList.contains(p)) {
            hiddenProductList.remove(p);
            fireHiddenProductChanged();
        }
        else { System.out.println("Can't remove something that isn't already in the list");}
    }

    /**
     *
     * Returns hiddenProductList
     */
    public List<Product> getHiddenProductList() {
        return hiddenProductList;
    }

    public void addHiddenProductListener(HiddenProductListener hpl) {
        this.hiddenProductListenersList.add(hpl);
        System.out.println("added listener");
    }

    public void removeHiddenProductListener(HiddenProductListener hpl) {
        this.hiddenProductListenersList.remove(hpl);
    }

    public void fireHiddenProductChanged() {
        Iterator var = this.hiddenProductListenersList.iterator();

        while(var.hasNext()) {
            HiddenProductListener hpl = (HiddenProductListener)var.next();
            hpl.hiddenProductChanged();
            System.out.println("hidden product changed in while loop");
        }
        System.out.println("hidden product changed");
    }

    public String getSelectedCategoryName() {
        return catalogList.getSelectedListName();
    }

    public void setSelectedCategoryName(String categoryName) {
        catalogList.setSelectedListName(categoryName);
    }

    public String getSelectedProfileOption() {
        return profileList.getSelectedListName();
    }

    public void setSelectedProfileOption(String profileOption) {
        profileList.setSelectedListName(profileOption);
    }

    public void addFavourite(Product product){
        iMatDataHandler.addFavorite(product);
    }

    public void removeFavourite(Product product){
        iMatDataHandler.removeFavorite(product);
        fireLikedProductChanged();
    }

    public List<Product> getFavourites(){
        return iMatDataHandler.favorites();
    }

    public void addlikedProductListener(LikedProductListener lpl) {
        this.likedProductListenersList.add(lpl);
        System.out.println("added listener");
    }

    public void fireLikedProductChanged() {
        Iterator var = this.likedProductListenersList.iterator();

        while(var.hasNext()) {
            LikedProductListener lpl = (LikedProductListener)var.next();
            lpl.productUnliked();
            System.out.println("liked product changed in while loop");
        }
        System.out.println("liked product changed");
    }

    public void addProductToList(Product product) {
        if(shoppingListList.isEmpty()){
            createShoppingList(product);
        }
        else if (shoppingListList.get(0).getProductList().contains(product)) {
            // Product already in list, do nothing
        }
        else {
            shoppingListList.get(0).addProductToShoppingList(product);
        }
    }

    public void createShoppingList(Product product) {
        System.out.println("list created");
        shoppingListList.add(new shoppingList(setShoppingListName(), product));
    }

    private String setShoppingListName() {
        nrOfShoppingLists++;
        return "Inköpslista " + nrOfShoppingLists;
    }

    public List<shoppingList> getShoppingListList() {
        return shoppingListList;
    }
}
