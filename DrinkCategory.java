
public class DrinkCategory {
    private int categoryID;
    private String categoryName;

    // Constructor
    public DrinkCategory(int categoryID, String categoryName) {
        this.categoryID = categoryID;
        this.categoryName = categoryName;
    }

    public int getCategoryID() {
        return categoryID;
    }

    public String getCategoryName() {
        return categoryName;
    }

}
