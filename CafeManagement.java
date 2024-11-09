import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CafeManagement {

    private List<PreOrder> preOrders = new ArrayList<>();
    private List<Promotion> promotions = new ArrayList<>();
    private List<Order> completedOrders = new ArrayList<>();
    private Menu menu;
    private Cart cart;
    private Payment paymentSystem;
    private Customer currentCustomer;
    private Role currentRole;
    private Scanner scanner;
    private List<Topping> toppingsList;
    private Status currentOrderStatus;
    private double discount = 0.0;
    private Manager manager;
    private String deliveryAddress;

    private DrinkCategory allCategory;
    private DrinkCategory promoCategory;
    private DrinkCategory teaCategory;
    private DrinkCategory coffeeCategory;
    private DrinkCategory milkCategory;
    private List<Cafetable> tables;

    private String currentPromotion = "";
    private String drinkName = "";
    private String drinkType = "";
    private int durationDays = 0;
    private int promotionDuration = 0; // วัน

    // Constructor
    public CafeManagement() {
        menu = new Menu();
        cart = new Cart();
        tables = new ArrayList<>();
        toppingsList = new ArrayList<>();
        paymentSystem = new Payment(0, null, currentCustomer);
        currentCustomer = new Customer("U001", "Alice", "alice@example.com", Role.CUSTOMER);
        manager = new Manager("001", "Admin", "0812345678", Role.MANAGER, "M001", new ArrayList<>());
        scanner = new Scanner(System.in);
        initializeMenu();
        initializeTables();
    }

    private void initializeMenu() {

        allCategory = new DrinkCategory(1, "ทั้งหมด");
        promoCategory = new DrinkCategory(2, "โปรโมชั่น");
        teaCategory = new DrinkCategory(3, "ชา");
        coffeeCategory = new DrinkCategory(4, "กาแฟ");
        milkCategory = new DrinkCategory(5, "นม");

        // เพิ่มเครื่องดื่มลงในเมนู
        menu.addDrink(new Drink(1, "ชาเขียว", 35, teaCategory));
        menu.addDrink(new Drink(2, "ชามะนาว", 30, teaCategory));
        menu.addDrink(new Drink(3, "ลาเต้", 40, coffeeCategory));
        menu.addDrink(new Drink(4, "โกโก้", 40, milkCategory));
        menu.addDrink(new Drink(5, "คาปูชิโน่เย็น", 40, coffeeCategory));
        menu.addDrink(new Drink(6, "นมสด", 30, milkCategory));
        menu.addDrink(new Drink(7, "อเมริกาโน่", 40, coffeeCategory));
        menu.addDrink(new Drink(8, "มัตติอาโต้", 40, coffeeCategory));
        menu.addDrink(new Drink(9, "นมชมพู", 25, milkCategory));

        // เพิ่มท็อปปิ้ง
        toppingsList.add(new Topping(1, "บุกไข่มุก"));
        toppingsList.add(new Topping(2, "บุกลิ้นจี่"));
        toppingsList.add(new Topping(3, "เจลลี่น้ำผึ้ง"));
        toppingsList.add(new Topping(4, "วิปครีม"));

        // เพิ่มท็อปปิ้งลงใน menu ด้วย
        for (Topping topping : toppingsList) {
            menu.addTopping(topping); // เพิ่มท็อปปิ้งลงในเมนู
        }

        // เพิ่มระดับความหวาน
        menu.addSweetness(new Sweetness(1, "หวานน้อย"));
        menu.addSweetness(new Sweetness(2, "หวานปกติ"));
        menu.addSweetness(new Sweetness(3, "หวานมาก"));
        menu.addSweetness(new Sweetness(4, "อื่นๆ"));

        menu.addPreparationType(new PreparationType(1, "ร้อน", 0));
        menu.addPreparationType(new PreparationType(2, "เย็น", 5));
        menu.addPreparationType(new PreparationType(3, "ปั่น", 10));
    }

    public void start() {
        System.out.println("ยินดีต้อนรับสู่ CafeSci!");
        // เรียก switchRole เพื่อให้เลือกบทบาทก่อนเข้าสู่เมนูหลัก
        switchRole();
        boolean running = true;
        while (running) {
            // ตรวจสอบว่า currentRole เป็น null หรือไม่
            if (currentRole == null) {
                switchRole();
                continue; // ข้ามส่วนที่เหลือของลูปไปเลือกบทบาทใหม่
            }
            switch (currentRole) {
                case MANAGER:
                    displayManagerOptions();
                    int managerChoice = getUserInput();
                    handleManagerOptions(managerChoice);
                    if (managerChoice == 0) { // ออกจากระบบ
                        currentRole = null;
                    }
                    break;
                case CUSTOMER:
                    displayCustomerOptions();
                    int customerChoice = getUserInput();
                    handleCustomerOptions(customerChoice);
                    if (customerChoice == 0) { // ออกจากระบบ
                        currentRole = null;
                    }
                    break;
                case RIDER:
                    displayRiderOptions();
                    int riderChoice = getUserInput();
                    handleRiderOptions(riderChoice);
                    if (riderChoice == 0) { // ออกจากระบบ
                        currentRole = null;
                    }
                    break;
                default:
                    System.out.println("บทบาทไม่ถูกต้อง.");
                    running = false;
            }
        }
        System.out.println("ขอบคุณที่มาเยี่ยมชม CafeSci!");
    }

    public void switchRole() {
        System.out.println("\nเลือกบทบาทที่คุณต้องการ:");
        System.out.println("1. ลูกค้า (Customer)");
        System.out.println("2. ผู้จัดการ (Manager)");
        System.out.println("3. ไรเดอร์ (Rider)");
        System.out.println("4. จบการทำงาน");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int roleChoice = getUserInput();
        switch (roleChoice) {
            case 1:
                currentRole = Role.CUSTOMER;
                System.out.println("เปลี่ยนบทบาทเป็น: ลูกค้า");
                break;
            case 2:
                currentRole = Role.MANAGER;
                System.out.println("เปลี่ยนบทบาทเป็น: ผู้จัดการ");
                break;
            case 3:
                currentRole = Role.RIDER;
                System.out.println("เปลี่ยนบทบาทเป็น: ไรเดอร์");
                break;
            case 4:
                System.out.println("จบการทำงาน");
                System.exit(0);
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
                break;
        }
    }

    /*
     * ==============================================================
     * ==============================================================
     * Customer - Main(MENU)
     * ==============================================================
     * ==============================================================
     */

     private void customerUsePromotion() {
        System.out.println("\n=== เลือกโปรโมชันเพื่อใช้ ===");
    
        // ตรวจสอบว่าโปรโมชันมีอยู่หรือไม่
        if (promotions.isEmpty()) {
            System.out.println("ไม่มีโปรโมชันที่สร้างไว้ในระบบ");
            return;
        }
    
        // แสดงรายการโปรโมชันที่มีในระบบ
        for (int i = 0; i < promotions.size(); i++) {
            Promotion promotion = promotions.get(i);
            System.out.println((i + 1) + ". " + promotion.getPromotionName());
        }
    
        // ให้ลูกค้าเลือกโปรโมชัน
        System.out.print("กรุณาเลือกโปรโมชันที่ต้องการใช้: ");
        int promotionChoice = scanner.nextInt() - 1;
    
        // ตรวจสอบการเลือกโปรโมชัน
        if (promotionChoice < 0 || promotionChoice >= promotions.size()) {
            System.out.println("โปรโมชันไม่ถูกต้อง");
            return;
        }
    
        Promotion selectedPromotion = promotions.get(promotionChoice);
    
        // ตรวจสอบว่าโปรโมชันหมดอายุ
        if (selectedPromotion.isPromotionExpired()) {
            System.out.println("โปรโมชันนี้หมดอายุแล้ว");
            return;
        }
    
        // แสดงรายละเอียดโปรโมชันที่ลูกค้าเลือก
        System.out.println("\nโปรโมชันที่คุณเลือก: " + selectedPromotion.getPromotionName());
        System.out.println("รายละเอียดโปรโมชัน: " + selectedPromotion.getPromotionType());
        System.out.println("ส่วนลด: " + selectedPromotion.getDiscountPercentage() + "%");
    
        // ดึงเครื่องดื่มที่ใช้ได้กับโปรโมชัน
        List<Drink> applicableDrinks = selectedPromotion.getApplicableDrinks();
        if (applicableDrinks.isEmpty()) {
            System.out.println("ไม่มีเครื่องดื่มที่ใช้ได้กับโปรโมชันนี้");
            return;
        }
    
        // แสดงเครื่องดื่มที่ร่วมรายการและให้ลูกค้าเลือก
        System.out.println("เครื่องดื่มที่ร่วมรายการในโปรโมชันนี้:");
        for (int i = 0; i < applicableDrinks.size(); i++) {
            System.out.println((i + 1) + ". " + applicableDrinks.get(i).getName());
        }
    
        System.out.print("กรุณาเลือกเครื่องดื่มที่ต้องการใช้: ");
        int drinkChoice = scanner.nextInt() - 1;
    
        // ตรวจสอบการเลือกเครื่องดื่ม
        if (drinkChoice < 0 || drinkChoice >= applicableDrinks.size()) {
            System.out.println("การเลือกเครื่องดื่มไม่ถูกต้อง");
            return;
        }
    
        Drink selectedDrink = applicableDrinks.get(drinkChoice);
    
        // เลือกท็อปปิ้ง, ความหวาน, และประเภทการเตรียม
        Topping topping = getToppingFromUser();
        Sweetness sweetness = getSweetnessFromUser();
        PreparationType preparationType = getPreparationTypeFromUser();
    
        // เพิ่มเครื่องดื่มที่เลือกลงในตะกร้า
        System.out.print("กรุณาระบุจำนวน: ");
        int quantity = scanner.nextInt();
        cart.addItem(selectedDrink, topping, sweetness, preparationType, quantity);
    
        // ใช้โปรโมชันและคำนวณส่วนลด
        cart.applyPromotion(selectedPromotion); // คำนวณและใช้โปรโมชันในตะกร้า
    
        // แสดงรายละเอียดของตะกร้าและราคาหลังส่วนลด
        System.out.println("\n--- รายละเอียดตะกร้า ---");
        cart.displayCart();
        System.out.println("ยอดรวมหลังจากใช้โปรโมชัน: " + cart.getTotalPrice() + " บาท");
    }
    
    // ฟังก์ชันให้ผู้ใช้เลือกท็อปปิ้งจากรายการที่มี
    private Topping getToppingFromUser() {
        if (toppingsList == null || toppingsList.isEmpty()) {
            System.out.println("ไม่มีท็อปปิ้งที่พร้อมให้เลือก");
            return null;
        }

        System.out.println("กรุณาเลือกท็อปปิ้ง:");
        for (int i = 0; i < toppingsList.size(); i++) {
            System.out.println((i + 1) + ". " + toppingsList.get(i).getToppingName());
        }
        System.out.println((toppingsList.size() + 1) + ". ไม่ใส่ท็อปปิ้ง"); // ตัวเลือกสำหรับไม่ใส่ท็อปปิ้ง

        int choice = scanner.nextInt() - 1;

        // ตรวจสอบว่าผู้ใช้เลือกในขอบเขตที่ถูกต้อง
        if (choice >= 0 && choice < toppingsList.size()) {
            return toppingsList.get(choice); // คืนค่าท็อปปิ้งที่ผู้ใช้เลือก
        } else {
            System.out.println("คุณเลือกไม่ใส่ท็อปปิ้ง"); // กรณีผู้ใช้เลือก "ไม่ใส่ท็อปปิ้ง"
            return null;
        }
    }

    // ฟังก์ชันให้ผู้ใช้เลือกระดับความหวาน
    private Sweetness getSweetnessFromUser() {
        System.out.println("กรุณาเลือกระดับความหวาน:");
        System.out.println("1. หวานน้อย");
        System.out.println("2. หวานปกติ");
        System.out.println("3. หวานมาก");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return new Sweetness(1, "หวานน้อย");
            case 2: return new Sweetness(2, "หวานปกติ");
            case 3: return new Sweetness(3, "หวานมาก");
            default: return new Sweetness(2, "หวานปกติ"); // ใช้ค่าเริ่มต้นเป็น "หวานปกติ" ถ้าผู้ใช้ใส่ค่าผิด
        }
    }

    // ฟังก์ชันให้ผู้ใช้เลือกประเภทการเตรียมเครื่องดื่ม
    private PreparationType getPreparationTypeFromUser() {
        System.out.println("กรุณาเลือกประเภทการเตรียม:");
        System.out.println("1. ร้อน");
        System.out.println("2. เย็น");
        System.out.println("3. ปั่น");
        int choice = scanner.nextInt();
        switch (choice) {
            case 1: return new PreparationType(1, "ร้อน", 0);
            case 2: return new PreparationType(2, "เย็น", 5);
            case 3: return new PreparationType(3, "ปั่น", 10);
            default: return new PreparationType(1, "ร้อน", 0); // ใช้ค่าเริ่มต้นเป็น "ร้อน" ถ้าผู้ใช้ใส่ค่าผิด
        }
    }

    private void displayCustomerOptions() {
        System.out.println("\n== ตัวเลือกสำหรับลูกค้า ==");

        System.out.println("1. สั่งเครื่องดื่ม");
        System.out.println("2. จองโต๊ะ");
        System.out.println("3. ติดตามสถานะคำสั่งซื้อ");
        System.out.println("4. ติดตามสถานะการจัดส่ง");
        System.out.println("\n0. ออกจากระบบ");
        System.out.print("กรุณาเลือกหมายเลข: ");
    }

    private void handleCustomerOptions(int choice) {
        switch (choice) {
            case 1:
                System.out.println("ฟังก์ชันนี้เฉพาะสำหรับลูกค้าเท่านั้น.");
                chooseOrderType();
                break;
            case 2:
                System.out.println("\n");
                reserveTableByInput();
                break;
            case 3:
                trackOrderStatus(); // เรียกใช้ฟังก์ชันติดตามสถานะคำสั่งซื้อ
                break;
            case 4:
                trackDeliveryStatus(); // เรียกใช้ฟังก์ชันติดตามสถานะการจัดส่ง
                break;
            case 5:
                switchRole();
                break;
            case 0:
                switchRole();
                System.out.println("ออกจากระบบ.");
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void chooseOrderType() {
        System.out.println("\nคุณต้องการสั่งแบบไหน");
        System.out.println("1.สั่งเครื่องดื่มแบบทันที");
        System.out.println("2.สั่งเครื่องดื่มแบบล่วงหน้า");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
                orderImmediate();
                break;
            case 2:
                orderPreOrder();
                break;
            case 0:
                displayCustomerOptions();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void orderImmediate() {
        System.out.println("\nคุณต้องการรับรายการเครื่องดื่มแบบไหน");
        System.out.println("1.จัดส่ง");
        System.out.println("2.รับที่ร้าน");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
                System.out.print("กรุณากรอกสถานที่จัดส่ง: ");
                deliveryAddress = scanner.nextLine(); // เก็บที่อยู่จัดส่ง
                System.out.println("คุณได้เลือกจัดส่งไปยัง: " + deliveryAddress);
                displayOrder();
                break;
            case 2:
                deliveryAddress = ""; // ตั้งเป็นค่าว่างหากรับที่ร้าน
                displayOrder();
                break;
            case 0:
                chooseOrderType();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void PreorderImmediate() {
        System.out.println("\nคุณต้องการรับรายการเครื่องดื่มแบบไหน");
        System.out.println("1.จัดส่ง");
        System.out.println("2.รับที่ร้าน");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
                System.out.print("กรุณากรอกสถานที่จัดส่ง: ");
                String deliveryAddress = scanner.nextLine();
                System.out.println("คุณได้เลือกจัดส่งไปยัง: " + deliveryAddress);
                displayPreorder();
                break;
            case 2:
                displayPreorder();
                break;
            case 0:
                chooseOrderType();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    /*
     * ==============================================================
     * ==============================================================
     * Customer - Order
     * ==============================================================
     * ==============================================================
     */

    private void displayOrder() {
        System.out.println("\nเมนูการสั่งเครื่องดื่ม:");
        System.out.println("1. ดูเมนูน้ำ");
        System.out.println("2. สั่งรายการเครื่องดื่ม (เลือกน้ำ/ท็อปปิ้ง/ระดับความหวาน/ประเภทเครื่องดื่ม)");
        System.out.println("3. ดูตะกร้าสินค้าที่สั่ง");
        System.out.println("4. ชำระเงิน (จ่ายแบบ QR และ บัตรเครดิต)");
        System.out.println("5. โปรโมชัน");
        System.out.println("\n0. ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int subChoice = getUserInput();
        switch (subChoice) {
            case 1:
                displayOrderMenu();
                break;
            case 2:
                addToCartOrder();
                break;
            case 3:
                viewCart();
                break;
            case 4:
                proceedToPayment();
                break;
            case 5:
                customerUsePromotion();
                break;
            case 0:
                switchRole();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void addToCartOrder() {
        System.out.print("กรุณาเลือกหมายเลขเครื่องดื่มที่ต้องการเพิ่มในตะกร้า: ");
        if (!scanner.hasNextInt()) {
            System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
            scanner.next();
            return;
        }
        int drinkId = scanner.nextInt();
        scanner.nextLine();

        Drink selectedDrink = menu.getDrink(drinkId);
        if (selectedDrink != null) {
            Topping selectedTopping = null;
            Sweetness selectedSweetness = null;
            PreparationType selectedPreparationType = null;

            System.out.println("เพิ่มเครื่องดื่ม " + selectedDrink.getName() + " ลงในตะกร้าเรียบร้อยแล้ว.");

            // เพิ่มท็อปปิ้ง
            System.out.print("ต้องการเพิ่มท็อปปิ้งหรือไม่? (yes/no): ");
            String addTopping = scanner.nextLine();
            if (addTopping.equalsIgnoreCase("yes")) {
                menu.displayMenutopping();
                System.out.print("กรุณาเลือกหมายเลขท็อปปิ้ง: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                    scanner.next();
                    return;
                }
                int toppingNum = scanner.nextInt();
                scanner.nextLine();
                selectedTopping = menu.getTopping(toppingNum);
                if (selectedTopping != null) {
                    System.out.println("เพิ่มท็อปปิ้ง " + selectedTopping.getToppingName() + " เรียบร้อยแล้ว.");
                } else {
                    System.out.println("ท็อปปิ้งไม่ถูกต้อง.");
                }
            }

            // เลือกระดับความหวาน
            menu.displayMenusweetness();
            System.out.print("กรุณาเลือกหมายเลขระดับความหวาน: ");
            if (!scanner.hasNextInt()) {
                System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                scanner.next();
                return;
            }
            int sweetnessNum = scanner.nextInt();
            scanner.nextLine();
            selectedSweetness = menu.getSweetness(sweetnessNum);
            if (selectedSweetness != null) {
                System.out.println("เลือกระดับความหวาน " + selectedSweetness.getSweetnessName() + " เรียบร้อยแล้ว.");
            } else {
                System.out.println("ระดับความหวานไม่ถูกต้อง.");
            }

            // เลือกประเภทเครื่องดื่ม
            menu.displayPreparationType();
            System.out.print("กรุณาเลือกหมายเลขประเภทเครื่องดื่ม: ");
            if (!scanner.hasNextInt()) {
                System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                scanner.next();
                return;
            }
            int preparationTypeNum = scanner.nextInt();
            scanner.nextLine();
            selectedPreparationType = menu.getPreparationType(preparationTypeNum);
            if (selectedPreparationType != null) {
                System.out
                        .println("เลือกประเภทเครื่องดื่ม " + selectedPreparationType.getPrepName() + " เรียบร้อยแล้ว.");
            } else {
                System.out.println("ประเภทเครื่องดื่มไม่ถูกต้อง.");
            }

            // เพิ่มการถามใช้โปรโมชั่น
            if (!currentPromotion.isEmpty() && promotionDuration > 0) {
                System.out.print("ต้องการใช้โปรโมชั่น " + currentPromotion + " หรือไม่? (yes/no): ");
                String usePromotion = scanner.nextLine();
                if (usePromotion.equalsIgnoreCase("yes")) {
                    applyPromotionToCart(selectedDrink);
                }
            }

            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับจำนวนที่สั่ง
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);

                // อัปเดตยอดขายของเครื่องดื่มตามจำนวนที่สั่ง
                selectedDrink.incrementSales(quantity);

                System.out.println(
                        "เพิ่ม " + selectedDrink.getName() + " จำนวน " + quantity + " แก้ว ลงในตะกร้าเรียบร้อยแล้ว.");
            } else {
                System.out.println("จำนวนไม่ถูกต้อง กรุณาลองใหม่.");
            }

        } else {
            System.out.println("หมายเลขเครื่องดื่มไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
        displayOrder();
    }

    private void applyPromotionToCart(Drink selectedDrink) {
        if (currentPromotion.equals("จับคู่แก้วที่ 2 ลด 50%")) {
            cart.applyDiscount(selectedDrink, 0.5); // ลดราคา 50% สำหรับแก้วที่ 2
        } else if (currentPromotion.equals("ลด 30% สำหรับการซื้อเครื่องดื่มชิ้นที่ 3")) {
            cart.applyDiscount(selectedDrink, 0.3); // ลดราคา 30% สำหรับแก้วที่ 3
        }
    }

    private void displayOrderMenu() {
        System.out.println("\nเลือกดูเมนูเครื่องดื่มที่คุณต้องการ");
        System.out.println("1.ทั้งหมด");
        System.out.println("2.ชา");
        System.out.println("3.กาแฟ");
        System.out.println("4.นม");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options5choiceCategory = getUserInput();
        switch (Options5choiceCategory) {
            case 1:
                displayDrinksByCategory(allCategory);
                displayOrder();
                break;
            case 2:
                displayDrinksByCategory(teaCategory);
                displayOrder();
                break;
            case 3:
                displayDrinksByCategory(coffeeCategory);
                displayOrder();
                break;
            case 4:
                displayDrinksByCategory(milkCategory);
                displayOrder();
                break;
            case 0:
                displayOrder();
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    /*
     * ==============================================================
     * ==============================================================
     * Customer - PreOrder
     * ==============================================================
     * ==============================================================
     */

    private void displayatstoreorriderPreorder() {
        System.out.println("\nคุณต้องการรับรายการเครื่องดื่มแบบไหน");
        System.out.println("1.จัดส่ง");
        System.out.println("2.รับที่ร้าน");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int atstoreorrider = getUserInput();
        switch (atstoreorrider) {
            case 1:
                System.out.print("กรุณากรอกสถานที่จัดส่ง: ");
                String deliveryAddress = scanner.nextLine();
                System.out.println("คุณได้เลือกจัดส่งไปยัง: " + deliveryAddress);
                displayPreorder();
                break;
            case 2:
                displayPreorder();
                break;
            case 0:
                chooseOrderType();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void orderPreOrder() {
        System.out.println("การสั่งเครื่องดื่มแบบล่วงหน้า:");
        displayatstoreorriderPreorder();
        displayPreorder();

        if (cart.isEmpty()) {
            System.out.println("กรุณาเพิ่มรายการเครื่องดื่มก่อนการสั่งล่วงหน้า.");
            return;
        }

        // ให้ผู้ใช้เลือกเวลาหลังจากเลือกเครื่องดื่มและตัวเลือกเสร็จ
        LocalDateTime pickupTime = choosePickupTime();

        List<Drink> drinks = new ArrayList<>(cart.getDrinks());
        PreOrder preOrder = new PreOrder(preOrders.size() + 1, drinks, pickupTime);
        preOrders.add(preOrder);

        System.out.println("สั่งเครื่องดื่มล่วงหน้าเรียบร้อย!");
        preOrder.displayPreOrderDetails();
        cart.clearCart();
    }

    private LocalDateTime choosePickupTime() {
        System.out.println("กรุณาเลือกเวลารับสินค้าล่วงหน้า:");
        System.out.println("1. รับภายใน 1 ชั่วโมง");
        System.out.println("2. รับภายใน 2 ชั่วโมง");
        System.out.println("3. รับในวันและเวลาที่กำหนดเอง");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int choice = getUserInput();

        LocalDateTime pickupTime = LocalDateTime.now();

        switch (choice) {
            case 1:
                pickupTime = pickupTime.plusHours(1);
                break;
            case 2:
                pickupTime = pickupTime.plusHours(2);
                break;
            case 3:
                pickupTime = inputCustomPickupTime();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กำลังใช้เวลาเริ่มต้นที่ 1 ชั่วโมงจากนี้");
                pickupTime = pickupTime.plusHours(1);
        }
        return pickupTime;
    }

    private LocalDateTime inputCustomPickupTime() {
        LocalDateTime pickupTime = null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        while (pickupTime == null) {
            System.out.print("กรุณาใส่เวลารับสินค้า (รูปแบบ: HH:mm): ");
            String input = scanner.nextLine();
            try {
                LocalTime time = LocalTime.parse(input, formatter);
                LocalDateTime todayWithTime = LocalDateTime.of(LocalDate.now(), time);
                if (todayWithTime.isBefore(LocalDateTime.now())) {
                    System.out.println("เวลาที่เลือกต้องอยู่ในอนาคต กรุณาลองใหม่.");
                    pickupTime = null;
                } else {
                    pickupTime = todayWithTime;
                }
            } catch (Exception e) {
                System.out.println("รูปแบบเวลาผิดพลาด กรุณาลองใหม่ (รูปแบบ: HH:mm).");
            }
        }
        return pickupTime;
    }

    private void displayPreorderMenu() {
        System.out.println("เลือกดูเมนูเครื่องดื่มที่คุณต้องการ");
        System.out.println("1.ทั้งหมด");
        System.out.println("2.โปรโมชั่น");
        System.out.println("3.ชา");
        System.out.println("4.กาแฟ");
        System.out.println("5.นม");
        System.out.println("\n0.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options5choiceCategory = getUserInput();
        switch (Options5choiceCategory) {
            case 1:
                displayDrinksByCategory(allCategory);
                displayPreorder();
                break;
            case 2:
                displayDrinksByCategory(promoCategory);
                displayPreorder();
                break;
            case 3:
                displayDrinksByCategory(teaCategory);
                displayPreorder();
                break;
            case 4:
                displayDrinksByCategory(coffeeCategory);
                displayPreorder();
                break;
            case 5:
                displayDrinksByCategory(milkCategory);
                displayPreorder();
                break;
            case 0:
                displayPreorder();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void displayPreorder() {
        System.out.println("\nเมนูการสั่งเครื่องดื่ม:");
        System.out.println("1. ดูเมนูน้ำ");
        System.out.println("2. สั่งรายการเครื่องดื่ม (เลือกน้ำ/ท็อปปิ้ง/ระดับความหวาน/ประเภทเครื่องดื่ม)");
        System.out.println("3. ดูตะกร้าสินค้าที่สั่ง");
        System.out.println("4. ชำระเงิน (จ่ายแบบ QR และ บัตรเครดิต)");
        System.out.println("5. โปรโมชัน");
        System.out.println("\n0. ออกจากระบบ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int subChoice = getUserInput();
        switch (subChoice) {
            case 1:
                displayPreorder();
                break;
            case 2:
                addToCartPreorder();
                break;
            case 3:
                viewCart();
                break;
            case 4:
                proceedToPayment();
                break;
            case 5:
                customerUsePromotion();
                break;
            case 0:
                switchRole();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void addToCartPreorder() {
        System.out.print("กรุณาเลือกหมายเลขเครื่องดื่มที่ต้องการเพิ่มในตะกร้า: ");
        if (!scanner.hasNextInt()) {
            System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
            scanner.next();
            return;
        }
        int drinkId = scanner.nextInt();
        scanner.nextLine();

        Drink selectedDrink = menu.getDrink(drinkId);
        if (selectedDrink != null) {
            Topping selectedTopping = null;
            Sweetness selectedSweetness = null;
            PreparationType selectedPreparationType = null;

            System.out.println("เพิ่มเครื่องดื่ม " + selectedDrink.getName() + " ลงในตะกร้าเรียบร้อยแล้ว.");

            // เพิ่มท็อปปิ้ง
            System.out.print("ต้องการเพิ่มท็อปปิ้งหรือไม่? (yes/no): ");
            String addTopping = scanner.nextLine();
            if (addTopping.equalsIgnoreCase("yes")) {
                menu.displayMenutopping();
                System.out.print("กรุณาเลือกหมายเลขท็อปปิ้ง: ");
                if (!scanner.hasNextInt()) {
                    System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                    scanner.next();
                    return;
                }
                int toppingNum = scanner.nextInt();
                scanner.nextLine();
                selectedTopping = menu.getTopping(toppingNum);
                if (selectedTopping != null) {
                    System.out.println("เพิ่มท็อปปิ้ง " + selectedTopping.getToppingName() + " เรียบร้อยแล้ว.");
                } else {
                    System.out.println("ท็อปปิ้งไม่ถูกต้อง.");
                }
            }

            // เลือกระดับความหวาน

            menu.displayMenusweetness();
            System.out.print("กรุณาเลือกหมายเลขระดับความหวาน: ");
            if (!scanner.hasNextInt()) {
                System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                scanner.next();
                return;
            }
            int sweetnessNum = scanner.nextInt();
            scanner.nextLine();
            selectedSweetness = menu.getSweetness(sweetnessNum);
            if (selectedSweetness != null) {
                System.out
                        .println("เลือกระดับความหวาน " + selectedSweetness.getSweetnessName() + " เรียบร้อยแล้ว.");
            } else {
                System.out.println("ระดับความหวานไม่ถูกต้อง.");
            }

            // เลือกประเภทเครื่องดื่ม

            menu.displayPreparationType();
            System.out.print("กรุณาเลือกหมายเลขประเภทเครื่องดื่ม: ");
            if (!scanner.hasNextInt()) {
                System.out.println("กรุณาใส่หมายเลขที่ถูกต้อง.");
                scanner.next();
                return;
            }
            int preparationTypeNum = scanner.nextInt();
            scanner.nextLine();
            selectedPreparationType = menu.getPreparationType(preparationTypeNum);
            if (selectedPreparationType != null) {
                System.out.println(
                        "เลือกประเภทเครื่องดื่ม " + selectedPreparationType.getPrepName() + " เรียบร้อยแล้ว.");
            } else {
                System.out.println("ประเภทเครื่องดื่มไม่ถูกต้อง.");
            }

            if (!promotions.isEmpty()) {
                System.out.println("\n== โปรโมชั่นที่ใช้งานได้ ==");
                for (int i = 0; i < promotions.size(); i++) {
                    Promotion promo = promotions.get(i);
                    if (promo.isPromotionActive()) { // ตรวจสอบว่าโปรโมชั่นยังใช้งานได้อยู่หรือไม่
                        System.out.println((i + 1) + ". " + promo.getPromotionName() + " (หมดอายุ: "
                                + promo.getEndDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + ")");
                    }
                }
                System.out.print("ต้องการใช้โปรโมชั่นหรือไม่? (yes/no): ");
                String usePromotion = scanner.nextLine();

                if (usePromotion.equalsIgnoreCase("yes")) {
                    System.out.print("กรุณาเลือกโปรโมชั่น (หมายเลข): ");
                    int promoChoice = scanner.nextInt();
                    scanner.nextLine();

                    if (promoChoice > 0 && promoChoice <= promotions.size()) {
                        Promotion selectedPromotion = promotions.get(promoChoice - 1);

                        // เพิ่มส่วนลดให้กับเครื่องดื่มในตะกร้า
                        if (selectedPromotion.getPromotionName().contains("แก้วที่ 2 ลด 50%")) {
                            cart.applyDiscount(selectedDrink, 0.5); // ลด 50% สำหรับแก้วที่ 2
                        } else if (selectedPromotion.getPromotionName().contains("ลด 30%")) {
                            cart.applyDiscount(selectedDrink, 0.3); // ลด 30% สำหรับเครื่องดื่มชิ้นที่ 3
                        }
                        System.out.println("ใช้โปรโมชั่น " + selectedPromotion.getPromotionName() + " เรียบร้อยแล้ว");
                    } else {
                        System.out.println("หมายเลขโปรโมชั่นไม่ถูกต้อง.");
                    }
                }
            }

            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับจำนวนที่สั่ง
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);

                // อัปเดตยอดขายของเครื่องดื่มตามจำนวนที่สั่ง
                selectedDrink.incrementSales(quantity);

                System.out.println(
                        "เพิ่ม " + selectedDrink.getName() + " จำนวน " + quantity + " แก้ว ลงในตะกร้าเรียบร้อยแล้ว.");
            } else {
                System.out.println("จำนวนไม่ถูกต้อง กรุณาลองใหม่.");
            }

        } else {
            System.out.println("หมายเลขเครื่องดื่มไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
        LocalDateTime pickupTime = choosePickupTime(); // เรียกฟังก์ชันเลือกเวลารับสินค้า
        System.out.println(
                "เวลารับสินค้าล่วงหน้า: " + pickupTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        displayPreorder();
    }

    private void displayDrinksByCategory(DrinkCategory category) {
        System.out.println("\n=== " + category.getCategoryName() + " ===");
        for (Drink drink : menu.getDrinks()) {
            if (category.getCategoryID() == 1 || drink.getCategory().getCategoryID() == category.getCategoryID()) {
                System.out.printf("%d. %s - $%.2f\n", drink.getDrinkID(), drink.getName(), drink.getPrice());
            }
        }
    }

    private void viewCart() {
        System.out.println("\n--- ตะกร้าของคุณ ---");
        cart.displayCart();
        displayOrder();
    }

    private void proceedToPayment() {
        if (cart.isEmpty()) {
            System.out.println("ตะกร้าของคุณว่างอยู่ กรุณาเพิ่มรายการก่อนทำการชำระเงิน.");
            return;
        }

        float totalAmount = cart.getTotalPrice();

        System.out.println("เลือกวิธีการชำระเงิน:");
        System.out.println("1. QR Code");
        System.out.println("2. บัตรเครดิต");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // จัดการ newline

        String paymentMethod = (paymentChoice == 1) ? "QR" : "Credit Card";

        List<Drink> drinks = cart.getDrinks();
        List<Integer> quantities = cart.getQuantities();
        List<Topping> toppings = cart.getToppings();
        List<Sweetness> sweetnessLevels = cart.getSweetnessLevels();
        List<PreparationType> preparationTypes = cart.getPreparationTypes();

        int orderId = (int) (Math.random() * 1000);
        Order order = new Order(orderId, currentCustomer, totalAmount, drinks, quantities, 0.0);

        // ตั้งค่าที่อยู่จัดส่งให้กับ Order
        if (deliveryAddress != null && !deliveryAddress.isEmpty()) {
            order.setDeliveryAddress(deliveryAddress);
        }

        paymentSystem = new Payment(totalAmount, paymentMethod, currentCustomer);
        paymentSystem.processPayment(order, drinks, quantities, toppings, sweetnessLevels, preparationTypes);

        completedOrders.add(order);

        // ล้างตะกร้าและที่อยู่จัดส่งหลังจากสั่งซื้อเสร็จ
        cart.clearCart();
        deliveryAddress = null;
    }

    private void trackDeliveryStatus() {
        System.out.println("\n== ติดตามสถานะการจัดส่ง ==");
        List<Order> inProgressOrders = completedOrders.stream()
                .filter(order -> order.getStatus().equals("รออนุมัติ") ||
                        order.getStatus().equals("รับคำสั่งซื้อ") ||
                        order.getStatus().equals("กำลังจัดส่ง"))
                .collect(Collectors.toList());

        if (inProgressOrders.isEmpty()) {
            System.out.println("ไม่มีออเดอร์ที่กำลังจัดส่งในขณะนี้.");
        } else {
            for (Order order : inProgressOrders) {
                System.out.println("ออเดอร์ ID: " + order.getOrderId());
                System.out.println("   ที่อยู่จัดส่ง: " + order.getDeliveryAddress());
                System.out.println("   สถานะ: " + order.getStatus());
                System.out.println("-------------------------------");
            }
        }
    }

    // เมธอดสำหรับบันทึกสถานะออเดอร์
    private void saveOrderStatus(Order order) {
        // โค้ดที่ใช้ในการบันทึกสถานะออเดอร์ลงในระบบ เช่น เขียนลงฐานข้อมูลหรือไฟล์
        System.out.println(
                "สถานะของออเดอร์ ID: " + order.getOrderId() + " ถูกบันทึกแล้วเป็น '" + order.getStatus() + "'");
    }

    private void trackOrderStatus() {
        if (completedOrders.isEmpty()) {
            System.out.println("คุณยังไม่มีคำสั่งซื้อที่กำลังดำเนินการ");
        } else {
            Order latestOrder = completedOrders.get(completedOrders.size() - 1); // ใช้คำสั่งซื้อที่ล่าสุด
            System.out.println("สถานะคำสั่งซื้อของคุณ: " + latestOrder.getStatus());
        }
    }

    // private void setupPromotion() {
    //     System.out.println("\n=== จัดโปรโมชันเพื่อกระตุ้นยอดขายของเครื่องดื่ม ===");

    //     // จัดเรียงเครื่องดื่มตามยอดขาย
    //     List<Drink> sortedDrinks = menu.getDrinks().stream()
    //             .sorted((d1, d2) -> Integer.compare(d2.getSalesCount(), d1.getSalesCount()))
    //             .collect(Collectors.toList());

    //     // แสดงเครื่องดื่มที่ขายได้มากที่สุด 3 อันดับแรก
    //     System.out.println("\nขายได้มากที่สุด 3 อันดับแรก:");
    //     for (int i = 0; i < Math.min(3, sortedDrinks.size()); i++) {
    //         Drink drink = sortedDrinks.get(i);
    //         System.out.println((i + 1) + ". " + drink.getName() + " (ขายได้ " + drink.getSalesCount() + " แก้ว)");
    //     }

    //     // แสดงเครื่องดื่มที่ขายได้น้อยที่สุด 3 อันดับแรก
    //     System.out.println("\nขายได้น้อยที่สุด 3 อันดับแรก:");
    //     for (int i = sortedDrinks.size() - 1, rank = 4; i >= Math.max(sortedDrinks.size() - 3, 0); i--, rank++) {
    //         Drink drink = sortedDrinks.get(i);
    //         System.out.println(rank + ". " + drink.getName() + " (ขายได้ " + drink.getSalesCount() + " แก้ว)");
    //     }

    //     // ให้ผู้จัดการเลือกเครื่องดื่มยอดขายมากและน้อยสำหรับโปรโมชัน
    //     System.out.print("เลือกเครื่องดื่มยอดขายมากสุด (ใส่หมายเลข): ");
    //     int topSellingChoice = scanner.nextInt() - 1;
    //     System.out.print("เลือกเครื่องดื่มยอดขายน้อยสุด (ใส่หมายเลข): ");
    //     int lowSellingChoice = scanner.nextInt() - 1;

    //     Drink topSellingDrink = sortedDrinks.get(topSellingChoice);
    //     Drink lowSellingDrink = sortedDrinks.get(lowSellingChoice);

    //     // เลือกประเภทเครื่องดื่มสำหรับโปรโมชัน
    //     System.out.println("เลือกประเภทเครื่องดื่มสำหรับโปรโมชัน:");
    //     System.out.println("1. ร้อน");
    //     System.out.println("2. เย็น");
    //     System.out.println("3. ปั่น");
    //     System.out.print("กรุณาเลือกหมายเลขประเภทเครื่องดื่ม: ");
    //     int typeChoice = scanner.nextInt();
    //     String selectedType = (typeChoice == 1) ? "ร้อน" : (typeChoice == 2) ? "เย็น" : "ปั่น";

    //     // เลือกโปรโมชัน
    //     System.out.println("\nเลือกโปรโมชัน:");
    //     System.out.println("1. จับคู่แก้วที่ 2 ลด 50%");
    //     System.out.println("2. ลด 30% สำหรับการซื้อเครื่องดื่มชิ้นที่ 3");
    //     System.out.print("กรุณาเลือกโปรโมชัน: ");
    //     int promotionTypeChoice = scanner.nextInt();
    //     String promotionType = (promotionTypeChoice == 1) ? "จับคู่แก้วที่ 2 ลด 50%"
    //             : "ลด 30% สำหรับการซื้อเครื่องดื่มชิ้นที่ 3";

    //     // กำหนดระยะเวลาโปรโมชัน
    //     System.out.print("กรุณาระบุระยะเวลาโปรโมชัน (วัน): ");
    //     int durationDays = scanner.nextInt();

    //     // // สร้างโปรโมชันใหม่
    //     // Promotion promotion = new Promotion(topSellingDrink, lowSellingDrink,
    //     // promotionType, selectedType, durationDays);
    //     // promotions.add(promotion);
    //     // System.out.println("โปรโมชันถูกสร้างสำเร็จ: " + promotionType + " สำหรับ " +
    //     // durationDays + " วัน.");
    // }

    private int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("กรุณาใส่หมายเลขที่ถูกต้อง: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    /*
     * ==============================================================
     * ==============================================================
     * Manager
     * ==============================================================
     * ==============================================================
     * 
     */

    private void setupPromotion() {
        System.out.println("\n=== จัดโปรโมชันเพื่อกระตุ้นยอดขายของเครื่องดื่ม ===");
    
        // จัดเรียงเครื่องดื่มตามยอดขาย
        List<Drink> sortedDrinks = menu.getDrinks().stream()
                .sorted((d1, d2) -> Integer.compare(d2.getSalesCount(), d1.getSalesCount()))
                .collect(Collectors.toList());
    
        // แสดงเครื่องดื่มที่ขายได้มากที่สุด 3 อันดับแรก
        System.out.println("\nขายได้มากที่สุด 3 อันดับแรก:");
        for (int i = 0; i < Math.min(3, sortedDrinks.size()); i++) {
            Drink drink = sortedDrinks.get(i);
            System.out.println((i + 1) + ". " + drink.getName() + " (ขายได้ " + drink.getSalesCount() + " แก้ว)");
        }
    
        // แสดงเครื่องดื่มที่ขายได้น้อยที่สุด 3 อันดับแรก
        System.out.println("\nขายได้น้อยที่สุด 3 อันดับแรก:");
        for (int i = sortedDrinks.size() - 1, rank = 4; i >= Math.max(sortedDrinks.size() - 3, 0); i--, rank++) {
            Drink drink = sortedDrinks.get(i);
            System.out.println(rank + ". " + drink.getName() + " (ขายได้ " + drink.getSalesCount() + " แก้ว)");
        }
    
        // ให้ผู้จัดการเลือกเครื่องดื่มยอดขายมากและน้อยสำหรับโปรโมชัน
        System.out.print("เลือกเครื่องดื่มยอดขายมากสุด (ใส่หมายเลข): ");
        int topSellingChoice = scanner.nextInt() - 1;
        System.out.print("เลือกเครื่องดื่มยอดขายน้อยสุด (ใส่หมายเลข): ");
        int lowSellingChoice = scanner.nextInt() - 1;
    
        Drink topSellingDrink = sortedDrinks.get(topSellingChoice);
        Drink lowSellingDrink = sortedDrinks.get(lowSellingChoice);
    
        // เลือกประเภทเครื่องดื่มสำหรับโปรโมชัน
        System.out.println("เลือกประเภทเครื่องดื่มสำหรับโปรโมชัน:");
        System.out.println("1. ร้อน");
        System.out.println("2. เย็น");
        System.out.println("3. ปั่น");
        System.out.print("กรุณาเลือกหมายเลขประเภทเครื่องดื่ม: ");
        int typeChoice = scanner.nextInt();
        String selectedType = (typeChoice == 1) ? "ร้อน" : (typeChoice == 2) ? "เย็น" : "ปั่น";
        
    
        // เลือกโปรโมชัน
        System.out.println("\nเลือกโปรโมชัน:");
        System.out.println("1. จับคู่แก้วที่ 2 ลด 50%");
        System.out.println("2. ลด 30% สำหรับการซื้อเครื่องดื่มชิ้นที่ 3");
        System.out.print("กรุณาเลือกโปรโมชัน: ");
        int promotionTypeChoice = scanner.nextInt();
        String promotionType = (promotionTypeChoice == 1) ? "จับคู่แก้วที่ 2 ลด 50%" : "ลด 30% สำหรับการซื้อเครื่องดื่มชิ้นที่ 3";
    
        // กำหนดระยะเวลาโปรโมชัน
        System.out.print("กรุณาระบุระยะเวลาโปรโมชัน (วัน): ");
        int durationDays = scanner.nextInt();
    
        // สร้างโปรโมชันใหม่
        Promotion promotion = new Promotion(topSellingDrink, lowSellingDrink, promotionType, selectedType, durationDays);
        promotions.add(promotion);
        System.out.println("โปรโมชันถูกสร้างสำเร็จ: " + promotionType + " สำหรับ " + durationDays + " วัน.");
    }

    private void displayManagerOptions() {
        System.out.println("\nหน้าหลัก Manager");
        System.out.println("1. ขายรายการเครื่องดื่มและระบุโต๊ะ");
        System.out.println("2. ปรับสถานะการสั่งซื้อหรือดูใบเสร็จ");
        System.out.println("3. ปรับสถานะโต๊ะหรือใบเสร็จจองโต๊ะ");
        System.out.println("4. จัดโปรโมชันเพื่อกระตุ้นยอดขายของเครื่องดื่ม");
        System.out.println("5. ออกจากระบบ");
        System.out.print("กรุณาเลือกเมนู: ");
    }

    private void handleManagerOptions(int choice) {
        switch (choice) {
            case 1:
                displayOrder();
                break;
            case 2:
                vReceiptsoradStatus();
                break;
            case 3:
                adjustTableStatus();
                break;
            case 4:
                setupPromotion();
                break;
            case 5:
                System.out.println("ออกจากระบบ.");
                currentRole = null;
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    public void vReceiptsoradStatus() {
        System.out.println("\nเลือกรายการที่คุณต้องการ:");
        System.out.println("1. ดูใบเสร็จ");
        System.out.println("2. ปรับสถานะ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int vaChoice = getUserInput();
        switch (vaChoice) {
            case 1:
                viewOrderReceipts();
                break;
            case 2:
                adjustOrderStatus();
                break;

            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
                break;
        }
    }

    private void viewOrderReceipts() {
        if (completedOrders.isEmpty()) {
            System.out.println("ยังไม่มีออเดอร์เข้ามา.");
            return;
        }
        System.out.println("\n=== ใบเสร็จของออเดอร์ทั้งหมด ===");
        for (Order order : completedOrders) {
            order.displayOrderDetails(); // ใช้เมธอดนี้จากคลาส Order ที่คุณมีอยู่แล้ว
            System.out.println("-------------------------------");
        }
    }

    private void adjustOrderStatus() {
        if (completedOrders.isEmpty()) {
            System.out.println("ยังไม่มีออเดอร์เข้ามา.");
            return;
        }
        System.out.println("\n=== ปรับสถานะออเดอร์ ===");
        for (int i = 0; i < completedOrders.size(); i++) {
            Order order = completedOrders.get(i);
            System.out
                    .println((i + 1) + ". ออเดอร์ ID: " + order.getOrderId() + " สถานะปัจจุบัน: " + order.getStatus());
        }
        System.out.print("เลือกหมายเลขออเดอร์ที่ต้องการปรับสถานะ: ");
        int orderChoice = getUserInput();
        if (orderChoice < 1 || orderChoice > completedOrders.size()) {
            System.out.println("ตัวเลือกไม่ถูกต้อง.");
            return;
        }
        Order selectedOrder = completedOrders.get(orderChoice - 1);
        System.out.println("สถานะปัจจุบัน: " + selectedOrder.getStatus());
        System.out.println("เลือกสถานะใหม่:");
        System.out.println("1. รออนุมัติ");
        System.out.println("2. รับคำสั่งซื้อ");
        System.out.println("3. กำลังจัดเตรียม");
        System.out.println("4. เสร็จสิ้น");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int statusChoice = getUserInput();
        String newStatus = "";
        switch (statusChoice) {
            case 1:
                newStatus = "รออนุมัติ";
                break;
            case 2:
                newStatus = "รับคำสั่งซื้อ";
                break;
            case 3:
                newStatus = "กำลังจัดเตรียม";
                break;
            case 4:
                newStatus = "เสร็จสิ้น";
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง.");
                return;
        }
        selectedOrder.setStatus(newStatus);
        System.out.println("อัปเดตสถานะออเดอร์เรียบร้อยแล้ว.");
    }

    // -----------------------------table---------------------->
    // Method สำหรับสร้างโต๊ะตัวอย่าง
    private void initializeTables() {
        tables.add(new Cafetable(1, "โต๊ะ A1", 5.0));
        tables.add(new Cafetable(2, "โต๊ะ A2", 5.0));
        tables.add(new Cafetable(3, "โต๊ะ A3", 5.0));
        tables.add(new Cafetable(4, "โต๊ะ A4", 5.0));
        tables.add(new Cafetable(5, "โต๊ะ A5", 10.0));
        tables.add(new Cafetable(6, "โต๊ะ A6", 10.0));
    }

    public void displayAllTables() {
        System.out.println("รายการโต๊ะและสถานะทั้งหมด:");
        for (Cafetable table : tables) {
            table.displayTableStatus();
        }
    }

    // Method คำนวณยอดเงินที่ต้องชำระสำหรับการจองโต๊ะ
    public void calculateBill(Cafetable table, String memberID) {
        System.out.println("\n===== ใบเสร็จ =====");
        System.out.println("โต๊ะที่จอง: " + table.getTableName());
        System.out.println("ราคาโต๊ะ: " + table.getTablePrice() + " บาท");
        System.out.println("ยอดรวมที่ต้องชำระ: " + table.getTablePrice() + " บาท");

        // เรียกเมธอด processPayment เพื่อดำเนินการชำระเงิน
        processPayment(table, memberID, null);
    }

    // Method สำหรับการจองโต๊ะโดยรับหมายเลขจากผู้ใช้
    public void reserveTableByInput() {
        displayAllTables(); // แสดงโต๊ะทั้งหมดก่อน
        System.out.print("กรุณาใส่โต๊ะที่ต้องการจอง (ID: 1–6): ");
        int tableID = scanner.nextInt();

        System.out.print("กรุณาใส่รหัสสมาชิก: ");
        String memberID = scanner.next() + scanner.nextLine(); // รับรหัสสมาชิก

        // ค้นหาโต๊ะตามหมายเลขที่ผู้ใช้กรอก
        boolean found = false;
        for (Cafetable table : tables) {
            if (table.getTableID() == tableID && table.getStatus().equals("ว่าง")) {
                // เรียกใช้ reserveTableWithDetails และรับค่ากลับของ reservationDateTime
                LocalDateTime reservationDateTime = reserveTableWithDetails(table, memberID);

                if (reservationDateTime != null) {
                    table.setReservationDateTime(reservationDateTime); // ตั้งค่าเวลาจองให้กับโต๊ะ
                    table.setMemberID(memberID); // ตั้งค่ารหัสสมาชิกให้กับโต๊ะที่จอง
                    calculateBill(table, memberID); // แสดงหน้าคิดเงินหลังจากการจองโต๊ะ
                    found = true;
                } else {
                    System.out.println("เวลาจองไม่ถูกต้อง กรุณาลองใหม่.");
                }
                break;
            }
        }

        if (!found) {
            System.out.println("ไม่พบโต๊ะหมายเลข " + tableID + " หรือโต๊ะไม่ว่าง.");
        }

    }

    // แก้ไขใน reserveTableWithDetails เพื่อส่งค่า reservationDateTime กลับไป
    public LocalDateTime reserveTableWithDetails(Cafetable table, String memberID) {
        // รับเฉพาะชั่วโมงและนาที
        System.out.print("กรุณาใส่เวลาจอง (เช่น 12:30) : ");
        String timeInput = scanner.next() + scanner.nextLine();

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        try {
            LocalTime reservationTime = LocalTime.parse(timeInput, timeFormatter);
            LocalDateTime reservationDateTime = LocalDateTime.now().withHour(reservationTime.getHour())
                    .withMinute(reservationTime.getMinute());

            return reservationDateTime; // ส่งค่า reservationDateTime กลับไป
        } catch (Exception e) {
            System.out.println("รูปแบบเวลาไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
        return null;

    }

    // ปรับปรุง processPayment ให้รับ reservationDateTime เป็นพารามิเตอร์
    public void processPayment(Cafetable table, String memberID, LocalDateTime reservationDateTime) {
        double amount = table.getTablePrice();
        System.out.println("\nเลือกวิธีการชำระเงิน:");
        System.out.println("1. QR code");
        System.out.println("2. ชำระด้วยบัตรเครดิต");
        System.out.print("กรุณาเลือกวิธีการชำระเงิน: ");
        int paymentMethod = scanner.nextInt();
        scanner.nextLine(); // เคลียร์ newline

        if (paymentMethod == 1) {
            System.out.println("คุณเลือกชำระด้วย QR Code ขอบคุณที่ใช้บริการ!");
            printReceipt(table, memberID, amount, reservationDateTime); // ส่ง reservationDateTime ให้ printReceipt
            table.setStatus("รออนุมัติ");
            displayAllTables();
            switchRole();
        } else if (paymentMethod == 2) {
            System.out.println("กรุณากรอกรายละเอียดบัตรเครดิต...");
            Scanner scanner = new Scanner(System.in);
            System.out.print("กรุณาใส่หมายเลขบัตรเครดิต: ");
            String cardNumber = scanner.nextLine();
            System.out.print("ชื่อผู้ถือบัตร: ");
            String cardHolderName = scanner.nextLine();
            System.out.print("วันหมดอายุ (MM/YY): ");
            String expirationDate = scanner.nextLine();
            System.out.print("CVV: ");
            String cvv = scanner.nextLine();

            CreditCard creditCard = new CreditCard(cardNumber, cardHolderName, expirationDate, cvv);
            if (creditCard.validateCard()) {
                System.out.println("การชำระเงินผ่านบัตรเครดิตเสร็จสมบูรณ์!");
                printReceipt(table, memberID, amount, reservationDateTime); // ส่ง reservationDateTime ให้ printReceipt
                table.setStatus("รออนุมัติ");
                displayAllTables();
                switchRole();
            } else {
                System.out.println("ข้อมูลบัตรเครดิตไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
            }
        } else {
            System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
            processPayment(table, memberID, reservationDateTime); // ส่ง reservationDateTime อีกครั้ง
        }
    }

    // Method แสดงใบเสร็จหลังจากการชำระเงิน
    public void printReceipt(Cafetable table, String memberID, double amountPaid, LocalDateTime reservationDateTime) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm น.");
        LocalDateTime currentDateTime = LocalDateTime.now();

        System.out.println("\n========= Cafe Sci =========");
        System.out.println("เลขที่ใบเสร็จ: #" + (int) (Math.random() * 1000));
        System.out.println("รหัสสมาชิก: " + memberID);
        System.out.println(currentDateTime.format(dateFormatter));
        System.out.println("----------------------------");

        if (reservationDateTime != null) {
            System.out.println("เวลาที่จอง: " + reservationDateTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        } else {
            System.out.println("เวลาที่จอง: ไม่ระบุ");
        }

        System.out.println("รายการสินค้า");
        System.out.println("1   :   " + table.getTableName() + "           "
                + String.format("%.2f", table.getTablePrice()) + " บาท");
        System.out.println("----------------------------");
        System.out.println("ยอดสุทธิ 1 โต๊ะ       " + String.format("%.2f", table.getTablePrice()) + " บาท");
        System.out.println("ชำระเงิน               " + String.format("%.2f", table.getTablePrice()) + " บาท");
        System.out.println("เงินทอน              0.00 บาท");
        System.out.println("----------------------------");
        System.out.println("\n**หากท่านไม่มาถึงภายใน 15 นาที ทางร้านขอสงวนสิทธิในการปล่อยโต๊ะให้กับลูกค้าท่านอื่น**");
        System.out.println("============================");
    }

    // --------------------------------ปรับสถานโต้ะ--------------------------->
    // เมธอดใน CafeManagement สำหรับการปรับสถานะโต๊ะ
    public void adjustTableStatus() {
        displayAllTables(); // แสดงรายการโต๊ะทั้งหมด

        System.out.print("กรุณาใส่หมายเลขโต๊ะที่ต้องการปรับสถานะ: ");
        int tableID = scanner.nextInt();
        scanner.nextLine(); // เคลียร์ newline

        // ค้นหาโต๊ะตาม ID ที่ระบุ
        for (Cafetable table : tables) {
            if (table.getTableID() == tableID) {
                System.out.println("สถานะปัจจุบันของโต๊ะ " + table.getTableName() + ": " + table.getStatus());
                System.out.println("1. ว่าง");
                System.out.println("2. ไม่ว่าง");
                System.out.print("กรุณาเลือกสถานะใหม่: ");
                int statusOption = scanner.nextInt();
                scanner.nextLine(); // เคลียร์ newline
                double amountPaid = table.getTablePrice();

                // อัปเดตสถานะตามตัวเลือกที่ผู้จัดการเลือก
                if (statusOption == 1) {
                    table.setStatus("ว่าง");
                    System.out.println(table.getTableName() + " ถูกตั้งค่าสถานะเป็นว่าง.");
                    displayAllTables();
                    displayManagerOptions();
                } else if (statusOption == 2) {
                    table.setStatus("ไม่ว่าง");
                    System.out.println(table.getTableName() + " ถูกตั้งค่าสถานะเป็นไม่ว่าง.");
                    displayAllTables();
                    displayManagerOptions();
                } else {
                    System.out.println("ตัวเลือกไม่ถูกต้อง.");
                }
                return;
                // printReceipt();
                // printReceipt(Cafetable table, String memberID, double amountPaid);
            }
        }
        System.out.println("ไม่พบโต๊ะหมายเลข " + tableID);
        displayAllTables();
        displayManagerOptions();
    }

    private void addjustOrreciept() {
        System.out.println("\nปรับสถานะการสั่งซื้อหรือดูใบเสร็จ");
        System.out.println("1. ปรับสถานะโต๊ะ");
        System.out.println("2. ดูใบเสร็จจองโต๊ะ");
        System.out.print("กรุณาเลือกหมายเลข :");
        int choiceMa = getUserInput();

        switch (choiceMa) {
            case 1:
                adjustTableStatus(); // เรียกใช้เมธอดสำหรับปรับสถานะโต๊ะ
                break;
            case 2:
                // ตรวจสอบข้อมูลโต๊ะเพื่อแสดงใบเสร็จ
                System.out.print("กรุณาใส่หมายเลขโต๊ะที่ต้องการดูใบเสร็จ: ");
                int tableID = getUserInput();
                Cafetable selectedTable = null;

                // ค้นหาโต๊ะที่ตรงกับ tableID
                for (Cafetable table : tables) {
                    if (table.getTableID() == tableID) {
                        selectedTable = table;
                        break;
                    }
                }

                if (selectedTable != null) {
                    System.out.print("กรุณาใส่รหัสสมาชิก: ");
                    String memberID = selectedTable.getMemberID(); // ดึงรหัสสมาชิกจากโต๊ะที่จอง
                    double amountPaid = selectedTable.getTablePrice();

                    // ดึงเวลาที่จองโต๊ะจาก `selectedTable`
                    LocalDateTime reservationDateTime = selectedTable.getReservationDateTime(); // สมมติว่ามี getter
                                                                                                // นี้ใน Cafetable

                    if (reservationDateTime != null) {
                        printReceipt(selectedTable, memberID, amountPaid, reservationDateTime); // เรียกใบเสร็จโดยใช้เวลาที่จองจริง
                    } else {
                        System.out.println("ไม่พบเวลาจองสำหรับโต๊ะนี้");
                    }
                } else {
                    System.out.println("ไม่พบโต๊ะหมายเลข " + tableID);
                }
                break;

            case 5:
                System.out.println("ออกจากระบบ.");
                break;

            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");

        }
    }

    public void createAndApplyPromotion(Order order, String promotionType, int durationDays) {
        // ดึงเครื่องดื่มที่ขายดีที่สุดและแย่ที่สุด
        List<Drink> topSelling = manager.getTopSellingDrinks();
        List<Drink> lowSelling = manager.getLowestSellingDrinks();

        // สร้างโปรโมชั่นใหม่
        manager.createPromotion(topSelling, lowSelling, promotionType, null, durationDays); // ใช้ null สำหรับ
                                                                                            // PreparationType
                                                                                            // หรือกำหนดค่าเฉพาะ

        // ดึงโปรโมชั่นที่ active และนำไปใช้ใน Order
        Promotion promotion = manager.getActivePromotions().stream()
                .filter(p -> p.getPromotionType().equals(promotionType))
                .findFirst()
                .orElse(null);

        if (promotion != null) {
            order.applyPromotion(promotion);
        } else {
            System.out.println("No active promotion found for the selected type.");
        }
    }

    // -----------------------------table---------------------->

    /*
     * ==============================================================
     * ==============================================================
     * -------------------------Rider--------------------------------
     * ==============================================================
     * ==============================================================
     */
    private void displayRiderOptions() {
        System.out.println("\n== ตัวเลือกสำหรับไรเดอร์ ==");
        System.out.println("1. ฟังก์ชันสำหรับไรเดอร์");
        System.out.println("2. ติดตามสถานะการจัดส่ง");
        System.out.println("\n0. ออกจากระบบ");
        System.out.print("กรุณาเลือกหมายเลข: ");
    }

    private void handleRiderOptions(int choice) {
        switch (choice) {
            case 1:
                viewDeliveryOrders();
                break;
            case 2:
                trackDeliveryStatus();
                break;
            case 0:
                currentRole = null;
                System.out.println("ออกจากระบบ.");
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void viewDeliveryOrders() {
        System.out.println("\n== รายการออเดอร์สำหรับจัดส่ง ==");
        List<Order> deliveryOrders = completedOrders.stream()
                .filter(order -> order.getDeliveryAddress() != null &&
                        !order.getDeliveryAddress().isEmpty() &&
                        !order.getStatus().equals("จัดส่งแล้ว")) // แสดงออเดอร์ที่ยังไม่ได้จัดส่ง
                .collect(Collectors.toList());

        if (deliveryOrders.isEmpty()) {
            System.out.println("ไม่มีออเดอร์สำหรับจัดส่งในขณะนี้.");
        } else {
            for (int i = 0; i < deliveryOrders.size(); i++) {
                Order order = deliveryOrders.get(i);
                System.out.println((i + 1) + ". ออเดอร์ ID: " + order.getOrderId());
                System.out.println("   ที่อยู่จัดส่ง: " + order.getDeliveryAddress());
                System.out.println("   สถานะ: ");
                System.out.println("-------------------------------");
            }

            System.out.print("กรุณาเลือกออเดอร์ที่ต้องการจัดส่ง (ใส่หมายเลข), หรือ 0 เพื่อกลับ: ");
            int choice = getUserInput();

            if (choice > 0 && choice <= deliveryOrders.size()) {
                Order selectedOrder = deliveryOrders.get(choice - 1);
                deliverOrder(selectedOrder);
            } else if (choice == 0) {
                // กลับไปเมนูหลักของไรเดอร์
            } else {
                System.out.println("ตัวเลือกไม่ถูกต้อง.");
            }
        }
    }

    private void deliverOrder(Order order) {
        System.out.println("คุณได้เลือกจัดส่งออเดอร์ ID: " + order.getOrderId());
        System.out.println("สถานะปัจจุบันของออเดอร์: " + order.getStatus());

        while (true) {
            System.out.println("เลือกสถานะการจัดส่งใหม่:");
            System.out.println("1. รออนุมัติ");
            System.out.println("2. รับคำสั่งซื้อ");
            System.out.println("3. กำลังจัดส่ง");
            System.out.println("4. จัดส่งแล้ว");
            System.out.println("0. ออกจากระบบ");

            int choice = getUserInput();

            switch (choice) {
                case 1:
                    order.setStatus("รออนุมัติ");
                    saveOrderStatus(order);
                    System.out.println("สถานะออเดอร์ถูกอัปเดตเป็น 'รออนุมัติ'");
                    break;
                case 2:
                    order.setStatus("รับคำสั่งซื้อ");
                    saveOrderStatus(order);
                    System.out.println("สถานะออเดอร์ถูกอัปเดตเป็น 'รับคำสั่งซื้อ'");
                    break;
                case 3:
                    order.setStatus("กำลังจัดส่ง");
                    saveOrderStatus(order);
                    System.out.println("สถานะออเดอร์ถูกอัปเดตเป็น 'กำลังจัดส่ง'");
                    break;
                case 4:
                    order.setStatus("จัดส่งแล้ว");
                    saveOrderStatus(order);
                    System.out.println("สถานะออเดอร์ถูกอัปเดตเป็น 'จัดส่งแล้ว'");
                    return; // ออกจากเมธอดเมื่อสถานะเป็น 'จัดส่งแล้ว'
                case 0:
                    System.out.println("ยกเลิกการเปลี่ยนสถานะและกลับไปเมนูหลักของไรเดอร์");
                    return; // ออกจากเมธอดเมื่อกดยกเลิก
                default:
                    System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง");
            }
            System.out.println("สถานะปัจจุบันของออเดอร์: " + order.getStatus());
        }
    }

    public static void main(String[] args) {
        CafeManagement cafeManagement = new CafeManagement();
        cafeManagement.start();
    }

}