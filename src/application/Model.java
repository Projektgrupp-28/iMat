package application;

import application.categories.CategoryItem;
import application.hiddenitems.HiddenProductListener;
import application.likeditems.LikedProductListener;
import application.shoppinglist.ShoppingListCatalogueListener;
import application.shoppinglist.ShoppingListListener;
import application.shoppinglist.shoppingList;
import javafx.scene.image.Image;
import se.chalmers.cse.dat216.project.*;

import java.io.*;
import java.text.DateFormat;
import java.util.*;

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
    private Order selectedOrder;
    private IMatDataHandler iMatDataHandler;
    private MainController mainController;
    private List<Product> hiddenProductList = new ArrayList<>();
    private List<CategoryItem> categories = new ArrayList<>();
    private ArrayList<HiddenProductListener> hiddenProductListenersList = new ArrayList();
    private ArrayList<LikedProductListener> likedProductListenersList = new ArrayList<>();
    private ArrayList<ShoppingListCatalogueListener> shoppingListCatalogueListenersList = new ArrayList<>();
    private ArrayList<ShoppingListListener> shoppingListListenersList = new ArrayList<>();
    private List<shoppingList> shoppingListList = new ArrayList<>();
    private int nrOfShoppingLists = 0;

    private String currentSearchTerm;
    private Product currentProduct;
    private shoppingList currentShoppingList;

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
        //this.shoppingListList = new ArrayList<>();
        //this.loadShoppingLists();
        initCategories();
    }

    private void initCategories() {
        categories.add(new CategoryItem(ProductCategory.POD, "Baljväxter"));
        categories.add(new CategoryItem(ProductCategory.BREAD, "Bröd"));
        categories.add(new CategoryItem(ProductCategory.BERRY, "Bär"));
        categories.add(new CategoryItem(ProductCategory.CITRUS_FRUIT, "Citrusfrukter"));
        categories.add(new CategoryItem(ProductCategory.EXOTIC_FRUIT, "Exotiska frukter"));
        categories.add(new CategoryItem(ProductCategory.FISH, "Fisk och skaldjur"));
        categories.add(new CategoryItem(ProductCategory.VEGETABLE_FRUIT, "Grönsaksfrukter"));
        categories.add(new CategoryItem(ProductCategory.COLD_DRINKS, "Kalla drycker"));
        categories.add(new CategoryItem(ProductCategory.CABBAGE, "Kål"));
        categories.add(new CategoryItem(ProductCategory.MEAT, "Kött"));
        categories.add(new CategoryItem(ProductCategory.DAIRIES, "Mejeri"));
        categories.add(new CategoryItem(ProductCategory.MELONS, "Meloner"));
        categories.add(new CategoryItem(ProductCategory.FLOUR_SUGAR_SALT, "Mjöl, socker och salt"));
        categories.add(new CategoryItem(ProductCategory.NUTS_AND_SEEDS, "Nötter och frön"));
        categories.add(new CategoryItem(ProductCategory.PASTA, "Pasta"));
        categories.add(new CategoryItem(ProductCategory.POTATO_RICE, "Potatis och ris"));
        categories.add(new CategoryItem(ProductCategory.ROOT_VEGETABLE, "Rotfrukter"));
        categories.add(new CategoryItem(ProductCategory.FRUIT, "Stenfrukt"));
        categories.add(new CategoryItem(ProductCategory.SWEET, "Sötsaker"));
        categories.add(new CategoryItem(ProductCategory.HOT_DRINKS, "Varma drycker"));
        categories.add(new CategoryItem(ProductCategory.HERB, "Örter"));
    }

    public List<CategoryItem> getCategories() {
        return categories;
    }

    public List<String> getCategoryNames() {
        List<String> categoryNames = new ArrayList<>();
        for (CategoryItem item : categories) {
            categoryNames.add(item.getCategoryName());
        }
        return categoryNames;
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

    public List<Product> getProducts(String categoryName) {
        ProductCategory chosenCategory;
        for (CategoryItem category : categories) {
            if (category.getCategoryName().contains(categoryName)){
                chosenCategory = category.getCategory();
                return getProducts(chosenCategory);
            }
        }
        return null;
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
        for (int i = 0; i < shoppingCart.getItems().size(); i++) {
            if (shoppingCart.getItems().get(i).getProduct().getName().equals(p.getName())) {
                shoppingCart.getItems().get(i).setAmount(shoppingCart.getItems().get(i).getAmount() + 1);
                shoppingCart.fireShoppingCartChanged(shoppingCart.getItems().get(i),true);
                return;
            }
        }
        ShoppingItem item = new ShoppingItem(p);
        Model.getInstance().getShoppingCart().addItem(item);
        shoppingCart.fireShoppingCartChanged(item,true);
    }

    public void removeFromShoppingCart(Product p) {
        ShoppingCart shoppingCart = iMatDataHandler.getShoppingCart();
        for (int i = 0; i < shoppingCart.getItems().size(); i++) {
            if (shoppingCart.getItems().get(i).getProduct().getName().equals(p.getName())) {
                if (shoppingCart.getItems().get(i).getAmount() == 1) {
                    shoppingCart.removeItem(i);
                }
                shoppingCart.getItems().get(i).setAmount(shoppingCart.getItems().get(i).getAmount() - 1);
                shoppingCart.fireShoppingCartChanged(shoppingCart.getItems().get(i),true);
                return;
            }
        }
        System.out.println("trying to remove item that doesnt exit in shoppingcart");
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

    public List<Order> getOrders() {
        return iMatDataHandler.getOrders();
    }

    public void setSelectedOrder(Order order) {
        this.selectedOrder = order;
    }

    public Order getSelectedOrder() {
        return selectedOrder;
    }

    public String getOrderName(int orderNumber) {
        for (int i = 0; i < getOrders().size(); i++) {
            if (getOrders().get(i).getOrderNumber() == orderNumber) {
                Date date = getOrders().get(i).getDate();
                int day = date.getDay();
                String dag = "";
                switch (day) {
                    case 0 -> dag = "Söndag";
                    case 1 -> dag = "Måndag";
                    case 2 -> dag = "Tisdag";
                    case 3 -> dag = "Onsdag";
                    case 4 -> dag = "Torsdag";
                    case 5 -> dag = "Fredag";
                    case 6 -> dag = "Lördag";
                }
                return (dag + " " + date.getDate() + "/" + (date.getMonth() + 1) + (date.toString().substring(10,16)));
            }
        }
        return "";
    }

    /**
     * Shuts down the data handler.
     */
    public void shutDown() {
        //this.saveShoppingLists();
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

    public void addProductToList(ShoppingItem shoppingItem) {
        currentProduct = shoppingItem.getProduct();
    }

    public void createShoppingList(Product product) {
        System.out.println("list created");
        shoppingListList.add(new shoppingList(setShoppingListName(), product));
        fireListChanged(shoppingListList.get(shoppingListList.size() - 1));
        fireListCatalogueChanged();
        setCurrentList(shoppingListList.get(shoppingListList.size() - 1));
    }

    private String setShoppingListName() {
        nrOfShoppingLists++;
        return "Inköpslista " + nrOfShoppingLists;
    }

    public List<shoppingList> getShoppingListList() {
        return shoppingListList;
    }

    public void addCatalogueListener(ShoppingListCatalogueListener slcl) {
        this.shoppingListCatalogueListenersList.add(slcl);
        System.out.println("added shoppinglist catalogue listener");
    }

    public void fireListCatalogueChanged() {
        Iterator var = this.shoppingListCatalogueListenersList.iterator();

        while(var.hasNext()) {
            ShoppingListCatalogueListener slcl = (ShoppingListCatalogueListener) var.next();
            slcl.shoppingListCatalogueChanged();
            System.out.println("shoppinglist catalogue changed in while loop");
        }
    }

    public void setSelectedShoppingList(String shoppingList) {
        for (shoppingList sl : shoppingListList) {
            if (sl.getShoppingListName().equals(shoppingList)) {
                fireListChanged(sl);
                setCurrentList(sl);
            }
        }
    }

    public void addListListener(ShoppingListListener sll) {
        this.shoppingListListenersList.add(sll);
        System.out.println("added shoppinglist listener");
    }

    public void fireListChanged(shoppingList sl) {
        Iterator var = this.shoppingListListenersList.iterator();

        while (var.hasNext()) {
                ShoppingListListener sll = (ShoppingListListener) var.next();
                sll.updateShownShoppingList(sl);
                System.out.println("shoppinglist changed in while loop");
        }
    }

    public void addToSelectedShoppingList(String shoppingList) {
        for (shoppingList sl : shoppingListList) {
            if (sl.getShoppingListName().equals(shoppingList)) {
                int temp = shoppingListList.indexOf(sl);
                shoppingListList.get(temp).addProductToShoppingList(currentProduct);
                System.out.println(currentProduct.getName() + " added to list: " + shoppingList);
            }
        }
    }

    public void setCurrentList(shoppingList sl) {
        currentShoppingList = sl;
    }

    public shoppingList getCurrentShoppingList() {
        return currentShoppingList;
    }

    public Product getCurrentProduct() {
        return currentProduct;
    }

    public void setCurrentSearchTerm(String searchTerm) {
        currentSearchTerm = searchTerm;
    }

    public String getCurrentSearchTerm() {
        return currentSearchTerm;
    }

    public void createOrder() {
        placeOrder();
        model.getShoppingCart().fireShoppingCartChanged(new ShoppingItem(new Product()), false);
    }
/*
    private String shoppingListsFile() {
        //return this.imatDirectory() + File.separatorChar + "favorites.txt";
        return iMatDataHandler.imatDirectory();
    }

    private void loadShoppingLists() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(this.shoppingListsFile()), "ISO-8859-1"));
            System.out.println("loadShoppingLists, starting...");

            String line;
            while((line = reader.readLine()) != null) {
                String[] tokens = line.split(";");

                for(int i = 0; i < tokens.length - 1; ++i) {
                    this.shoppingListList.add(Integer.parseInt(tokens[i]));
                }
            }

            reader.close();
        } catch (IOException var5) {
            var5.printStackTrace();
        }

    }

    private void saveShoppingLists() {
        try {
            FileOutputStream fos = new FileOutputStream(this.shoppingListsFile());
            OutputStreamWriter osw = new OutputStreamWriter(fos, "ISO-8859-1");
            System.out.println("saveFavorites() size: " + this.shoppingListList.size());
            String line = "";
            Iterator var4 = this.shoppingListList.iterator();

            while(var4.hasNext()) {
                Product favorite = (Product)var4.next();
                line = line + favorite.getProductId() + ";";
                System.out.println("saveShoppingLists() " + line);
            }

            line = line + "end\n";
            osw.write(line);
            osw.flush();
            osw.close();
        } catch (IOException var6) {
            var6.printStackTrace();
        }

    }*/
}
