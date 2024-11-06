
import java.util.ArrayList;
import java.util.List;

public class Menu {
    // Fields
    // private List<Menu> menuItems;
    private List<Drink> drinks;
    private List<Topping> toppings;
    private List<Sweetness> sweetnessLevels;
    // private List<DrinkType> drinktypes;
    private List<PreparationType> PreparationTypes;

    // Constructor
    public Menu() {
        // this.menuItems = new ArrayList<>();
        this.drinks = new ArrayList<>();
        this.PreparationTypes = new ArrayList<>();
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

    public void displayPreparationType() {
        System.out.println("\n-- PreparationType  --");
        for (PreparationType PreparationType : PreparationTypes) {
            System.out.printf("%d. %s - $%.2f\n", PreparationType.getPrepID(), PreparationType.getPrepName(),
                    PreparationType.getPrepprice());
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

    public PreparationType getPreparationType(int PrepID) {
        for (PreparationType PreparationType : PreparationTypes) {
            if (PreparationType.getPrepID() == PrepID) {
                return PreparationType;
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

    public void addPreparationType(PreparationType PreparationType) {
        PreparationTypes.add(PreparationType);
    }

    public void setDrinks(List<Drink> drinks) {
        this.drinks = drinks;
        System.out.println("Drinks list set successfully.");
    }

    public void setToppings(List<Topping> toppings) {
        this.toppings = toppings;
        System.out.println("Toppings list set successfully.");
    }

    public void setSweetnessLevels(List<Sweetness> sweetnessLevels) {
        this.sweetnessLevels = sweetnessLevels;
        System.out.println("Sweetness levels list set successfully.");
    }

    public void setPreparationType(List<PreparationType> PreparationTypes) {
        this.PreparationTypes = PreparationTypes;
        System.out.println("PreparationTypes list set successfully.");
    }

    public List<Drink> getDrinks() {
        return drinks;
    }
}
