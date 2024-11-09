import java.time.LocalDateTime;
import java.util.List;

public class Receipt {
    private int receiptID;
    private LocalDateTime orderDateTime;
    private double totalPrice;
    private double discount;
    private double pointsEarned;
    private int paymentID;
    private String personID;
    private Order order;
    private List<Drink> drinks;
    private List<Integer> quantities;
    private List<Topping> toppings;
    private List<Sweetness> sweetnessLevels;
    private List<DrinkType> drinkTypes;


    public Receipt(Order order, List<Drink> drinks, List<Integer> quantities, List<Topping> toppings, List<Sweetness> sweetnessLevels, List<DrinkType> drinkTypes, double discount) {
        this.drinks = drinks;
        this.quantities = quantities;
        this.toppings = toppings;
        this.sweetnessLevels = sweetnessLevels;
        this.drinkTypes = drinkTypes;
        this.discount = discount;
        this.order = order;
        this.receiptID = (int) (Math.random() * 100000);
        this.orderDateTime = order.getOrderDateTime();
        this.totalPrice = calculateTotalPrice();
        this.pointsEarned = this.totalPrice * 0.10;
        this.paymentID = order.getOrderId();
        this.personID = order.getCustomer().getPersonID();
        
    }

    private double calculateTotalPrice() {
        double calculatedTotal = 0;
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);
            double drinkPrice = (drinkTypes.get(i) != null) ? drinkTypes.get(i).getdrinktypeprice() : 0;
            double itemTotal = (drink.getPrice() + drinkPrice) * quantity;
            calculatedTotal += itemTotal;
        }
        return calculatedTotal;
    }


    public void printReceipt() {
        System.out.println("=================================");
        System.out.println("            Cafe Sci             ");
        System.out.println("=================================");
        System.out.printf("ใบเสร็จเลขที่      : #%05d\n", receiptID);
        System.out.printf("รหัสสมาชิก        : %s\n", personID);
        System.out.printf("วันที่/เวลา       : %s\n", orderDateTime);
        System.out.println("---------------------------------");
        System.out.println("รายการสินค้า");
        System.out.printf("%-4s %-30s %10s\n", "จำนวน", "สินค้า", "ราคา");
    
        double recalculatedTotalPrice = 0.0;
    
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);
            Topping topping = toppings.get(i);
            Sweetness sweetness = sweetnessLevels.get(i);
            DrinkType drinkType = drinkTypes.get(i);
    
            double drinkPrice = (drinkType != null) ? drinkType.getdrinktypeprice() : 0;
            double totalItemPrice = (drink.getPrice() + drinkPrice) * quantity;
            recalculatedTotalPrice += totalItemPrice;
    
            String displayName = drink.getName();
            if (drinkType != null) {
                displayName += "(" + drinkType.getdrinktypeName() + ")";
            }
            if (topping != null) {
                displayName += " + " + topping.getToppingName();
            }
            if (sweetness != null) {
                displayName += " (" + sweetness.getSweetnessName() + ")";
            }
    
            System.out.printf("%-4d %-30s %10.2f บาท\n", quantity, displayName, totalItemPrice);
        }
    
        System.out.println("---------------------------------");
        System.out.printf("ยอดรวมทั้งหมด     : %10.2f บาท\n", recalculatedTotalPrice);
        System.out.printf("ส่วนลด            : %10.2f บาท\n", discount);
        System.out.printf("ยอดสุทธิ          : %10.2f บาท\n", recalculatedTotalPrice - discount);
        System.out.println("---------------------------------");
    
        System.out.printf("ข้อมูลสมาชิก %s\n", personID);
        System.out.printf("%-10s %6s %6s %6s\n", "พอยท์สมาชิก", "ได้รับ", "ใช้ไป", "คงเหลือ");
    
        double pointsReceived = recalculatedTotalPrice * 0.10;
        double pointsUsed = discount;
        double pointsRemaining = (order.getCustomer().getPoints() - pointsUsed) + pointsReceived;
    
        System.out.printf("%-10s %6.1f %6.1f %6.1f\n", "", pointsReceived, pointsUsed, pointsRemaining);
        System.out.println("=================================");
        System.out.printf("คุณได้รับพอยต์สะสม: %.1f พอยต์\n", pointsReceived);
        System.out.println("ขอบคุณที่ใช้บริการ Cafe Sci!");
    }
    
    
}
