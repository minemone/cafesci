import java.util.List;
import java.util.Scanner;

public class Payment {
    private static int paymentCounter = 1; // เพิ่มตัวนับการชำระเงินอัตโนมัติ
    private int paymentId;
    private double amount;
    private String method; // เช่น "QR", "Credit Card"
    private Order order;
    private Customer customer;
    private double discount; // ฟิลด์ส่วนลดสำหรับการบันทึกส่วนลดทั้งหมดที่ใช้

    // Constructor
    public Payment(double amount, String method, Customer customer) {
        this.paymentId = paymentCounter++;
        this.amount = amount;
        this.method = method;
        this.customer = customer;
    }

    // ฟังก์ชันเพื่อใช้พอยต์เป็นส่วนลด
    public double applyPointsDiscount(double pointsToUse) {
        int availablePoints = customer.getPoints();
        if (pointsToUse > availablePoints) {
            System.out.println("คะแนนพอยต์ไม่พอ");
            return 0;
        }
        double discount = pointsToUse; // 1 พอยต์ = 1 บาท
        customer.reducePoints(pointsToUse); // หักพอยต์ที่ใช้
        return discount;
    }

    // ฟังก์ชันสำหรับการชำระเงิน
    public void processPayment(Order order, List<Drink> drinks, List<Integer> quantities, List<Topping> toppings,
            List<Sweetness> sweetnessLevels, List<DrinkType> preparationTypes) {
        if (order != null) {
            Scanner scanner = new Scanner(System.in);

            // ใช้พอยต์เป็นส่วนลด
            double availablePoints = order.getCustomer().getPoints();
            System.out.println("พอยต์ที่คุณมีอยู่: " + availablePoints);
            System.out.print("คุณต้องการใช้พอยต์เป็นส่วนลดหรือไม่? (yes/no): ");
            String usePoints = scanner.nextLine();

            discount = 0; // กำหนดค่าเริ่มต้นให้กับส่วนลด
            if (usePoints.equalsIgnoreCase("yes")) {
                System.out.print("กรุณาระบุจำนวนพอยต์ที่ต้องการใช้: ");
                double pointsToUse = scanner.nextDouble();
                discount = applyPointsDiscount(pointsToUse);
            }

            double finalAmount = amount - discount;
            System.out.printf("ยอดชำระหลังหักส่วนลด: %.2f บาท\n", finalAmount);

            // เลือกวิธีการชำระเงิน
            if (method.equalsIgnoreCase("QR")) {
                System.out.println("ชำระเงินผ่าน QR Code...");
                simulateQRPayment();
            } else if (method.equalsIgnoreCase("Credit Card")) {
                simulateCreditCardPayment(scanner);
            }

            // สร้างและพิมพ์ใบเสร็จ
            Receipt receipt = new Receipt(order, drinks, quantities, toppings, sweetnessLevels, preparationTypes,
                    discount);
            receipt.printReceipt();

            // เพิ่มพอยต์จากยอดการสั่งซื้อ
            double earnedPoints = finalAmount * 0.10;
            customer.addPoints((int) earnedPoints);
        } else {
            System.out.println("ไม่สามารถสร้างใบเสร็จได้ เนื่องจากคำสั่งซื้อเป็น null");
        }
    }

    // ฟังก์ชันจำลองการชำระเงินด้วย QR
    private void simulateQRPayment() {
        System.out.println("กรุณาสแกน QR Code เพื่อชำระเงิน...");
        System.out.println("การชำระเงินผ่าน QR Code เสร็จสิ้น!");
    }

    // ฟังก์ชันจำลองการชำระเงินด้วยบัตรเครดิต
    private void simulateCreditCardPayment(Scanner scanner) {
        System.out.println("กรุณาใส่ข้อมูลบัตรเครดิต:");
        System.out.print("หมายเลขบัตร: ");
        String cardNumber = scanner.nextLine();
        System.out.print("ชื่อผู้ถือบัตร: ");
        String cardHolderName = scanner.nextLine();
        System.out.print("วันหมดอายุ (MM/YY): ");
        String expirationDate = scanner.nextLine();
        System.out.print("CVV: ");
        String cvv = scanner.nextLine();
        System.out.println("การชำระเงินผ่านบัตรเครดิตเสร็จสิ้น!");
    }

}
