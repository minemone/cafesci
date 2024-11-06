import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Drink> drinks = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<Topping> toppings = new ArrayList<>();
    private List<Integer> toppingQuantities = new ArrayList<>();
    private List<Sweetness> sweetnesses = new ArrayList<>();
    private List<PreparationType> preparationTypes = new ArrayList<>();
    private float totalPrice;

    // Constructor
    public Cart() {
        this.totalPrice = 0.0f;
    }

    // เพิ่มเครื่องดื่มในตะกร้า
    public void addItem(Drink selectedDrink) {
        int index = drinks.indexOf(selectedDrink);
        if (index >= 0) {
            quantities.set(index, quantities.get(index) + 1);
        } else {
            drinks.add(selectedDrink);
            quantities.add(1);
        }
        totalPrice += selectedDrink.getPrice();
    }

    // เพิ่มท็อปปิ้งในตะกร้า
    public void addItem(Topping selectedTopping) {
        int index = toppings.indexOf(selectedTopping);
        if (index >= 0) {
            toppingQuantities.set(index, toppingQuantities.get(index) + 1);
        } else {
            toppings.add(selectedTopping);
            toppingQuantities.add(1);
        }
    }

    // เพิ่มระดับความหวาน
    public void addItem(Sweetness selectedSweetness) {
        sweetnesses.add(selectedSweetness);
    }

    // เพิ่มประเภทเครื่องดื่ม
    public void addItem(PreparationType selectedPreparationType) {
        preparationTypes.add(selectedPreparationType);
        totalPrice += selectedPreparationType.getPrepprice(); // เพิ่มราคาของ PreparationType ลงใน totalPrice
    }

    public boolean isEmpty() {
        return drinks.isEmpty() && toppings.isEmpty();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    // แสดงตะกร้าพร้อมจำนวนสินค้า
    public void displayCart() {
        StringBuilder cartSummary = new StringBuilder();

        // แสดงรายการเครื่องดื่มในตะกร้า
        cartSummary.append("รายการเครื่องดื่มในตะกร้า:\n");
        if (drinks.isEmpty()) {
            cartSummary.append("ไม่มีรายการเครื่องดื่ม\n");
        } else {
            int totalDrinkCount = 0;
            for (int i = 0; i < drinks.size(); i++) {
                Drink drink = drinks.get(i);
                int quantity = quantities.get(i);
                cartSummary.append(drink.getName())
                        .append(" (จำนวน: ").append(quantity)
                        .append(", ราคา: ").append(drink.getPrice() * quantity)
                        .append(" บาท)\n");
                totalDrinkCount += quantity;
            }
            cartSummary.append("จำนวนเครื่องดื่มทั้งหมด: ").append(totalDrinkCount).append("\n");
        }

        // แสดงรายการท็อปปิ้งในตะกร้า
        cartSummary.append("รายการท็อปปิ้งในตะกร้า:\n");
        if (toppings.isEmpty()) {
            cartSummary.append("ไม่มีท็อปปิ้ง\n");
        } else {
            for (int i = 0; i < toppings.size(); i++) {
                Topping topping = toppings.get(i);
                int quantity = toppingQuantities.get(i);
                cartSummary.append(topping.getToppingName())
                        .append(" (จำนวน: ").append(quantity).append(")\n");
            }
        }

        // แสดงระดับความหวาน
        cartSummary.append("ระดับความหวาน: ");
        if (sweetnesses.isEmpty()) {
            cartSummary.append("ไม่มีระดับความหวาน\n");
        } else {
            for (Sweetness sweetness : sweetnesses) {
                cartSummary.append(sweetness.getSweetnessName()).append(", ");
            }
            cartSummary.append("\n");
        }

        // แสดงประเภทเครื่องดื่ม
        cartSummary.append("ประเภทเครื่องดื่ม: ");
        if (preparationTypes.isEmpty()) {
            cartSummary.append("ไม่มีประเภทเครื่องดื่ม\n");
        } else {
            for (PreparationType preparationType : preparationTypes) {
                cartSummary.append(preparationType.getPrepName()).append(", ");
            }
            cartSummary.append("\n");
        }

        // แสดงราคารวม
        cartSummary.append("ราคารวม: ").append(totalPrice).append(" บาท\n");

        // พิมพ์ผลลัพธ์ออกมา
        System.out.println(cartSummary.toString());
    }

    public void clearCart() {
        drinks.clear();
        quantities.clear();
        toppings.clear();
        toppingQuantities.clear();
        sweetnesses.clear();
        preparationTypes.clear();
        totalPrice = 0.0f;
        System.out.println("ตะกร้าถูกล้างเรียบร้อยแล้ว.");
    }

    public List<Drink> getDrinks() {
        return drinks;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }

}
