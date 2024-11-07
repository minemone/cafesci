import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CafeManagement {

    private List<PreOrder> preOrders = new ArrayList<>();
    private Menu menu;
    private Cart cart;
    private Payment paymentSystem;
    private Customer currentCustomer;
    private Role currentRole;
    private Scanner scanner;
    private Status currentOrderStatus;
    private double discount = 0.0;

    private DrinkCategory allCategory;
    private DrinkCategory promoCategory;
    private DrinkCategory teaCategory;
    private DrinkCategory coffeeCategory;
    private DrinkCategory milkCategory;

    // Constructor
    public CafeManagement() {
        menu = new Menu();
        cart = new Cart();
        paymentSystem = new Payment(0, null, currentCustomer);
        currentCustomer = new Customer("U001", "Alice", "alice@example.com", Role.CUSTOMER);
        scanner = new Scanner(System.in);
        initializeMenu();
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
        menu.addTopping(new Topping(1, "บุกไข่มุก"));
        menu.addTopping(new Topping(2, "บุกลิ้นจี่"));
        menu.addTopping(new Topping(3, "เจลลี่น้ำผึ้ง"));
        menu.addTopping(new Topping(4, "วิปครีม"));

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
            switch (currentRole) {
                case MANAGER:
                    displayManagerOptions();
                    int managerChoice = getUserInput();
                    handleManagerOptions(managerChoice);
                    if (managerChoice == 0) { // ออกจากระบบ
                        running = false;
                    }
                    break;
                case CUSTOMER:
                    displayCustomerOptions();
                    int customerChoice = getUserInput();
                    handleCustomerOptions(customerChoice);
                    if (customerChoice == 0) { // ออกจากระบบ
                        running = false;
                    }
                    break;
                case RIDER:
                    displayRiderOptions();
                    int riderChoice = getUserInput();
                    handleRiderOptions(riderChoice);
                    if (riderChoice == 0) { // ออกจากระบบ
                        running = false;
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

     private void handleCustomerOptions(int choice) {
        switch (choice) {
            case 1:
                System.out.println("ฟังก์ชันนี้เฉพาะสำหรับลูกค้าเท่านั้น.");
                chooseOrderType();
                break;
            case 2:
                System.out.println("ฟังก์ชันการจองโต๊ะยังไม่ถูกพัฒนา.");
                break;
            case 3:
                trackOrderStatus();
                break;
            case 5:
                switchRole();
                break;
            case 6:
                System.out.println("ออกจากระบบ.");
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
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



    private void  orderImmediate() {
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
            displayOrder();
                break;
            case 2:
            displayOrder();
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
            case 0:
            orderImmediate();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }


    private void displayOrderMenu() {
        System.out.println("\nเลือกดูเมนูเครื่องดื่มที่คุณต้องการ");
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
                displayOrder();
                break;
            case 2:
                displayDrinksByCategory(promoCategory);
                displayOrder();
                break;
            case 3:
                displayDrinksByCategory(teaCategory);
                displayOrder();
                break;
            case 4:
                displayDrinksByCategory(coffeeCategory);
                displayOrder();
                break;
            case 5:
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
                displayOrder();
                break;
            case 2:
                displayPreorder();
                break;
            case 0:
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        while (pickupTime == null) {
            System.out.print("กรุณาใส่วันที่และเวลา (รูปแบบ: yyyy-MM-dd HH:mm): ");
            String input = scanner.nextLine();
            try {
                pickupTime = LocalDateTime.parse(input, formatter);
                if (pickupTime.isBefore(LocalDateTime.now())) {
                    System.out.println("เวลาที่เลือกต้องอยู่ในอนาคต กรุณาลองใหม่.");
                    pickupTime = null;
                }
            } catch (Exception e) {
                System.out.println("รูปแบบวันที่และเวลาไม่ถูกต้อง กรุณาลองใหม่.");
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
            case 6:
                // displayMainOptions1();
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void displayPreorder() { // เมนูสั่งน้ำ ดู สั่ง ตะกร้า จ่าย ออกจากระบบ
        System.out.println("\nเมนูการสั่งเครื่องดื่ม:");
        System.out.println("1. ดูเมนูน้ำ");
        System.out.println("2. สั่งรายการเครื่องดื่ม (เลือกน้ำ/ท็อปปิ้ง/ระดับความหวาน/ประเภทเครื่องดื่ม)");
        System.out.println("3. ดูตะกร้าสินค้าที่สั่ง");
        System.out.println("4. ชำระเงิน (จ่ายแบบ QR และ บัตรเครดิต)");
        System.out.println("5. ออกจากระบบ");
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
            System.out.println("กลับสู่หน้าหลัก");
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
            

            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับท็อปปิ้ง ความหวาน ประเภทเครื่องดื่ม และจำนวน
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);
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
            

            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับท็อปปิ้ง ความหวาน ประเภทเครื่องดื่ม และจำนวน
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);
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

        paymentSystem = new Payment(totalAmount, paymentMethod, currentCustomer);
        paymentSystem.processPayment(order, drinks, quantities, toppings, sweetnessLevels, preparationTypes);

        cart.clearCart();
    }


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

     private void handleManagerOptions(int choice) {
        // จัดการการทำงานสำหรับตัวเลือกของไรเดอร์
        if (choice == 5) {
            System.out.println("ออกจากระบบ.");
        } else {
            System.out.println("กำลังดำเนินการฟังก์ชันของผู้จัดการ.");
        }
    }
    private void displayManagerOptions() {
        System.out.println("\nหน้าหลัก Manager");
        System.out.println("1. ขายรายการเครื่องดื่มและระบุโต๊ะ");
        System.out.println("2. ปรับสถานะการสั่งซื้อหรือดูใบเสร็จ");
        System.out.println("3. ปรับสถานะโต๊ะหรือใบเสร็จจองโต๊ะ");
        System.out.println("4. จัดโปรโมชันเพื่อกระตุ้นยอดขายของเครื่องดื่ม");
        System.out.println("5. ออกจากระบบ");
        int choiceMa = getUserInput();
        switch (choiceMa) {
            case 1:
                displayOrder();
                break;
            case 2:
                System.out.println("ฟังก์ชันการจองโต๊ะยังไม่ถูกพัฒนา.");
                break;
            case 3:
                trackOrderStatus();
                break;
            case 4:
                switchRole();
                break;
            case 5:
                System.out.println("ออกจากระบบ.");
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }
     private void displayOrderMng() { 
        System.out.println("\nเมนูการสั่งเครื่องดื่ม:");
        System.out.println("1. ดูเมนูน้ำ");
        System.out.println("2. สั่งรายการเครื่องดื่ม (เลือกน้ำ/ท็อปปิ้ง/ระดับความหวาน/ประเภทเครื่องดื่ม)");
        System.out.println("3. ดูตะกร้าสินค้าที่สั่ง");
        System.out.println("4. ชำระเงิน (จ่ายแบบ QR และ บัตรเครดิต)");
        System.out.println("5. ออกจากระบบ");
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
                System.out.println("กลับสู่หน้าหลัก");
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void addToCartOrderMng() {
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
            

            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();

            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับท็อปปิ้ง ความหวาน ประเภทเครื่องดื่ม และจำนวน
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);
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

    private void displayOrderMenuMng() {
        System.out.println("เลือกดูเมนูเครื่องดื่มที่คุณต้องการ");
        System.out.println("1.ทั้งหมด");
        System.out.println("2.โปรโมชั่น");
        System.out.println("3.ชา");
        System.out.println("4.กาแฟ");
        System.out.println("5.นม");
        System.out.println("6.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options5choiceCategory = getUserInput();
        switch (Options5choiceCategory) {
            case 1:
                displayDrinksByCategory(allCategory);
                displayOrder();
                break;
            case 2:
                displayDrinksByCategory(promoCategory);
                displayOrder();
                break;
            case 3:
                displayDrinksByCategory(teaCategory);
                displayOrder();
                break;
            case 4:
                displayDrinksByCategory(coffeeCategory);
                displayOrder();
                break;
            case 5:
                displayDrinksByCategory(milkCategory);
                displayOrder();
                break;
            case 6:
                // displayMainOptions1();
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }



    private void trackOrderStatus() { // ยังไม่ได้ใช้
        if (currentOrderStatus != null) {
            currentOrderStatus.trackOrderStatus();
        } else {
            System.out.println("ยังไม่มีคำสั่งซื้อที่สามารถติดตามสถานะได้.");
        }
    }

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
        System.out.println("5. ออกจากระบบ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
                System.out.print("กรุณากรอกสถานที่จัดส่ง: ");
                String deliveryAddress = scanner.nextLine();
                System.out.println("คุณได้เลือกจัดส่งไปยัง: " + deliveryAddress);
            displayOrder();
                break;
            case 2:
            displayOrder();
                break;
            case 0:
            chooseOrderType();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void handleRiderOptions(int choice) {
        // จัดการการทำงานสำหรับตัวเลือกของไรเดอร์
        if (choice == 5) {
            System.out.println("ออกจากระบบ.");
        } else {
            System.out.println("กำลังดำเนินการฟังก์ชันของไรเดอร์.");
        }
    }

    

    public static void main(String[] args) {
        CafeManagement cafeManagement = new CafeManagement();
        cafeManagement.start();
    }

}