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
    private List<Drink> drinks;
    private List<Integer> quantities;
    private Order order;

    public Receipt(Order order, List<Drink> drinks, List<Integer> quantities, double discount) {
        this.receiptID = (int) (Math.random() * 100000);
        this.orderDateTime = order.getOrderDateTime();
        this.totalPrice = calculateTotalPrice(drinks, quantities); // คำนวณยอดรวมใหม่
        this.pointsEarned = this.totalPrice * 0.10; // พอยท์ที่ได้คือ 10% ของยอดรวม
        this.paymentID = order.getOrderId();
        this.personID = order.getCustomer().getPersonID();
        this.drinks = drinks;
        this.quantities = quantities;
        this.order = order;
        this.discount = discount;
    }

    private double calculateTotalPrice(List<Drink> drinks, List<Integer> quantities) {
        double calculatedTotal = 0;
        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);

            double prepprice = (drink.getPreparationType() != null) ? drink.getPreparationType().getPrepprice() : 0;
            
            double itemTotal = (drink.getPrice() + prepprice) * quantity;

            calculatedTotal += itemTotal;
        }
        return calculatedTotal;
    }
    
    public double getNetTotal() {
        return totalPrice - discount;
    }
    public void printReceipt() {
        System.out.println("=================================");
        System.out.println("            Cafe Sci             ");
        System.out.println("=================================");
        System.out.printf("เลขที่ใบเสร็จ       #%d\n", receiptID);
        System.out.printf("รหัสสมาชิก          %s\n", personID);
        System.out.printf("วันที่/เวลา         %s\n", orderDateTime);
        System.out.println("---------------------------------");
        System.out.println("รายการสินค้า");
        System.out.printf("%-4s %-30s %10s\n", "จำนวน", "สินค้า", "ราคา");

        for (int i = 0; i < drinks.size(); i++) {
            Drink drink = drinks.get(i);
            int quantity = quantities.get(i);

            double prepprice = (drink.getPreparationType() != null) ? drink.getPreparationType().getPrepprice() : 0;
            double totalItemPrice = (drink.getPrice() + prepprice) * quantity;

            String displayName = drink.getName();
            if (drink.getPreparationType() != null) {
                displayName += " (" + drink.getPreparationType().getPrepName() + ")";
            }
            if (drink.getTopping() != null) {
                displayName += " + " + drink.getTopping().getToppingName();
            }
            System.out.printf("%-4d %-30s %10.2f บาท\n", quantity, displayName, totalItemPrice);
        }

        System.out.println("---------------------------------");
        System.out.printf("ยอดรวม          %6.2f บาท\n", totalPrice);
        System.out.printf("ส่วนลด          %6.2f บาท\n", discount);
        System.out.printf("ยอดสุทธิ         %6.2f บาท\n", getNetTotal());
        System.out.println("---------------------------------");

        System.out.printf("ข้อมูลสมาชิก %s\n", personID);
        System.out.printf("%-10s %6s %6s %6s\n", "พอยท์สมาชิก", "ได้รับ", "ใช้ไป", "คงเหลือ");

        double pointsReceived = pointsEarned;
        double pointsUsed = discount; // ส่วนลดที่ใช้จากพอยท์
        double pointsRemaining = (order.getCustomer().getPoints() - pointsUsed) + pointsReceived; // พอยท์คงเหลือ

        System.out.printf("%-10s %6.1f %6.1f %6.1f\n", "", pointsReceived, pointsUsed, pointsRemaining);
        System.out.println("=================================");
    }
}
