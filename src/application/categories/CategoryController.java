package application.categories;

import application.hiddenitems.HiddenProductListener;
import application.Model;
import application.ProductCard;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;

import se.chalmers.cse.dat216.project.Product;
import se.chalmers.cse.dat216.project.ProductCategory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryController implements Initializable, HiddenProductListener {

    private Model model = Model.getInstance();

    private List<ProductCategory> categoryAssociations = new ArrayList<>();
    private List<Product> categoryProducts = new ArrayList<>();

    @FXML private FlowPane categoryFlowPane;
    @FXML private Label categoryLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        model.addHiddenProductListener(this);
        System.out.println(model.getSelectedCategoryName() + " have been loaded.");
        categoryLabel.setText(model.getSelectedCategoryName());
        selectCategoryAssociations();
        loadFlowPane();
    }

    private void selectCategoryAssociations() {
        categoryAssociations.clear();
        switch (model.getSelectedCategoryName()) {
            case "Bageri":
                categoryAssociations.add(ProductCategory.BREAD);
                break;
            case "Dryck":
                categoryAssociations.add(ProductCategory.HOT_DRINKS);
                categoryAssociations.add(ProductCategory.COLD_DRINKS);
                break;
            case "Frukt & Grönt":
                categoryAssociations.add(ProductCategory.BERRY);
                categoryAssociations.add(ProductCategory.CITRUS_FRUIT);
                categoryAssociations.add(ProductCategory.EXOTIC_FRUIT);
                categoryAssociations.add(ProductCategory.VEGETABLE_FRUIT);
                categoryAssociations.add(ProductCategory.CABBAGE);
                categoryAssociations.add(ProductCategory.MELONS);
                categoryAssociations.add(ProductCategory.ROOT_VEGETABLE);
                categoryAssociations.add(ProductCategory.FRUIT);
                categoryAssociations.add(ProductCategory.HERB);
                break;
            case "Fisk & Skaldjur":
                categoryAssociations.add(ProductCategory.FISH);
                break;
            case "Godis & Snacks":
                categoryAssociations.add(ProductCategory.SWEET);
                break;
            case "Kött":
                categoryAssociations.add(ProductCategory.MEAT);
                break;
            case "Mejeri":
                categoryAssociations.add(ProductCategory.DAIRIES);
                break;
            case "Skafferi":
                categoryAssociations.add(ProductCategory.POD);
                categoryAssociations.add(ProductCategory.FLOUR_SUGAR_SALT);
                categoryAssociations.add(ProductCategory.NUTS_AND_SEEDS);
                categoryAssociations.add(ProductCategory.PASTA);
                categoryAssociations.add(ProductCategory.POTATO_RICE);
                break;
        }
    }

    private void loadFlowPane() {
        categoryFlowPane.getChildren().clear();
        for (Product product : getCategoryProducts()) {
            if (!model.getHiddenProductList().contains(product)) {
                categoryFlowPane.getChildren().add(new ProductCard(product));
            }
        }
    }

    private List<Product> getCategoryProducts() {
        for (ProductCategory categoryAssociation : categoryAssociations) {
            categoryProducts.addAll(model.getProducts(categoryAssociation));
            System.out.println("There is " + model.getProducts(categoryAssociation).size() + " products in association with " + categoryAssociation.toString());
        }
        System.out.println("There is " + categoryProducts.size() + " products in total to be shown.");
        return categoryProducts;
    }

    @Override
    public void hiddenProductChanged() {
        System.out.println("Hello");
        loadFlowPane();
    }

}
