
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Menu {
    private List<Drink> drinks;
    private List<Topping> toppings;
    private List<Sweetness> sweetnessLevels;
    private List<DrinkType> drinkTypes;
    private String promotion = "";
    private int promotionDuration = 0;

    // Constructor
    public Menu() {
        this.drinks = new ArrayList<>();
        this.drinkTypes = new ArrayList<>();
        this.toppings = new ArrayList<>();
        this.sweetnessLevels = new ArrayList<>();
    }

    public void displayMenudrink() {
        System.out.println("\n===== Menu =====\n");
        System.out.println("-- Drinks --");
        for (Drink drink : drinks) {
            System.out.printf("%d. %s - $%.2f\n", drink.getDrinkID(), drink.getName(), drink.getPrice());
        }
    }

    public void displayMenutopping() {
        System.out.println("\n-- Toppings --");
        for (Topping topping : toppings) {
            System.out.printf("%d. %s \n", topping.getToppingID(), topping.getToppingName());
        }
    }

    public void displayMenusweetness() {
        System.out.println("\n-- Sweetness Levels --");
        for (Sweetness sweetness : sweetnessLevels) {
            System.out.printf("%d. %s  \n", sweetness.getSweetnessID(), sweetness.getSweetnessName());
        }
    }

    public void displaydrinkType() {
        System.out.println("\n-- drinkType  --");
        for (DrinkType drinkType : drinkTypes) {
            System.out.printf("%d. %s - $%.2f\n", drinkType.getdrinktypeID(), drinkType.getdrinktypeName(),
                    drinkType.getdrinktypeprice());
        }

        System.out.println("=======================\n");
    }

    public Drink getDrink(int drinkID) {
        for (Drink drink : drinks) {
            if (drink.getDrinkID() == drinkID) {
                return drink;
            }
        }
        System.out.println("Drink not found.");
        return null;
    }

    public Topping getTopping(int toppingID) {
        for (Topping topping : toppings) {
            if (topping.getToppingID() == toppingID) {
                return topping;
            }
        }
        System.out.println("Topping not found.");
        return null;
    }

    public Sweetness getSweetness(int sweetnessLevelID) {
        for (Sweetness sweetness : sweetnessLevels) {
            if (sweetness.getSweetnessID() == sweetnessLevelID) {
                return sweetness;
            }
        }
        System.out.println("Sweetness level not found.");
        return null;
    }

    public DrinkType getdrinkType(int drinkID) {
        for (DrinkType drinkType : drinkTypes) {
            if (drinkType.getdrinktypeID() == drinkID) {
                return drinkType;
            }
        }
        System.out.println("DrinkType not found.");
        return null;
    }

    public void addDrink(Drink drink) {
        drinks.add(drink);
    }

    public void addTopping(Topping topping) {
        toppings.add(topping);
    }

    public void addSweetness(Sweetness sweetness) {
        sweetnessLevels.add(sweetness);
    }

    public void adddrinkType(DrinkType drinkType) {
        drinkTypes.add(drinkType);
    }

    public List<Drink> getDrinks() {
        return drinks;
    }
}
