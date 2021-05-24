package application.categories;

import se.chalmers.cse.dat216.project.ProductCategory;

public class CategoryItem {

    private ProductCategory category;
    private String categoryName;

    public CategoryItem(ProductCategory category, String categoryName) {
        this.category = category;
        this.categoryName = categoryName;
    }

    public ProductCategory getCategory() {
        return category;
    }

    public void setCategory(ProductCategory category) {
        this.category = category;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

}
