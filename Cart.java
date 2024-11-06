import java.util.ArrayList;
import java.util.List;

public class Cart {
    private List<Drink> drinks = new ArrayList<>();
    private List<Integer> quantities = new ArrayList<>();
    private List<Topping> toppings = new ArrayList<>();
    private List<Sweetness> sweetnessLevels = new ArrayList<>();
    private List<PreparationType> preparationTypes = new ArrayList<>();
    private float totalPrice;

    // Constructor
    public Cart() {
        this.totalPrice = 0.0f;
    }

    // เพิ่มเครื่องดื่มในตะกร้า พร้อมการปรับแต่ง
    public void addItem(Drink selectedDrink, Topping topping, Sweetness sweetness, PreparationType preparationType, int quantity) {
        drinks.add(selectedDrink);
        quantities.add(quantity);
        toppings.add(topping);
        sweetnessLevels.add(sweetness);
        preparationTypes.add(preparationType);

        // คำนวณราคารวมตามการปรับแต่งแต่ละชนิด
        double prepPrice = (preparationType != null) ? preparationType.getPrepprice() : 0;
        double itemTotalPrice = (selectedDrink.getPrice() + prepPrice) * quantity;
        totalPrice += itemTotalPrice;
    }

     // เมธอดเพื่อดึงรายการท็อปปิ้งทั้งหมดจากตะกร้า
     public List<Topping> getToppings() {
        return toppings;
    }

    // เมธอดเพื่อดึงรายการระดับความหวานทั้งหมดจากตะกร้า
    public List<Sweetness> getSweetnessLevels() {
        return sweetnessLevels;
    }

    // เมธอดเพื่อดึงรายการประเภทเครื่องดื่มทั้งหมดจากตะกร้า
    public List<PreparationType> getPreparationTypes() {
        return preparationTypes;
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
                Topping topping = toppings.get(i);
                Sweetness sweetness = sweetnessLevels.get(i);
                PreparationType preparationType = preparationTypes.get(i);

                // แสดงรายละเอียดเครื่องดื่ม พร้อมประเภท ท็อปปิ้ง และความหวาน
                cartSummary.append(drink.getName())
                        .append(" (จำนวน: ").append(quantity)
                        .append(", ราคา: ").append((drink.getPrice() + (preparationType != null ? preparationType.getPrepprice() : 0)) * quantity)
                        .append(" บาท");

                if (preparationType != null) {
                    cartSummary.append(", ประเภท: ").append(preparationType.getPrepName());
                }
                if (topping != null) {
                    cartSummary.append(", ท็อปปิ้ง: ").append(topping.getToppingName());
                }
                if (sweetness != null) {
                    cartSummary.append(", ความหวาน: ").append(sweetness.getSweetnessName());
                }

                cartSummary.append(")\n");
                totalDrinkCount += quantity;
            }
            cartSummary.append("จำนวนเครื่องดื่มทั้งหมด: ").append(totalDrinkCount).append("\n");
        }

        // แสดงราคารวม
        cartSummary.append("ราคารวมทั้งหมด: ").append(String.format("%.2f", totalPrice)).append(" บาท\n");

        // พิมพ์ผลลัพธ์ออกมา
        System.out.println(cartSummary.toString());
    }

    public void clearCart() {
        drinks.clear();
        quantities.clear();
        toppings.clear();
        sweetnessLevels.clear();
        preparationTypes.clear();
        totalPrice = 0.0f;
        System.out.println("ตะกร้าถูกล้างเรียบร้อยแล้ว.");
    }

    public boolean isEmpty() {
        return drinks.isEmpty();
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public List<Integer> getQuantities() {
        return quantities;
    }
    public List<Drink> getDrinks() {
        return drinks;
    }

}

