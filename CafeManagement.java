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
        // currentRole = Role.CUSTOMER; // กำหนดบทบาทเริ่มต้นเป็น CUSTOMER
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
                    if (managerChoice == 5) { // ออกจากระบบ
                        running = false;
                    }
                    break;
                case CUSTOMER:
                    displayCustomerOptions();
                    int customerChoice = getUserInput();
                    handleCustomerOptions(customerChoice);
                    if (customerChoice == 6) { // ออกจากระบบ
                        running = false;
                    }
                    break;
                case RIDER:
                    displayRiderOptions();
                    int riderChoice = getUserInput();
                    handleRiderOptions(riderChoice);
                    if (riderChoice == 5) { // ออกจากระบบ
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

    private void displayCustomerOptions() {
        System.out.println("\n== ตัวเลือกสำหรับลูกค้า ==");
        System.out.println("1. สั่งเครื่องดื่ม");
        System.out.println("2. จองโต๊ะ");
        System.out.println("3. ติดตามสถานะคำสั่งซื้อ");
        System.out.println("4. ติดตามสถานะการจัดส่ง");
        System.out.println("5. ออกจากระบบ");
        System.out.print("กรุณาเลือกหมายเลข: ");
    }

    private void displayManagerOptions() {
        System.out.println("\nหน้าหลัก Manager");
        System.out.println("1. ขายรายการเครื่องดื่มและระบุโต๊ะ");
        System.out.println("2. ปรับสถานะการสั่งซื้อหรือดูใบเสร็จ");
        System.out.println("3. ปรับสถานะโต๊ะหรือใบเสร็จจองโต๊ะ");
        System.out.println("4. จัดโปรโมชันเพื่อกระตุ้นยอดขายของเครื่องดื่ม");
        System.out.println("5. ออกจากระบบ");
    }

    private void displayRiderOptions() {
        System.out.println("\n== ตัวเลือกสำหรับไรเดอร์ ==");
        System.out.println("1. ฟังก์ชันสำหรับไรเดอร์");
        System.out.println("2. ติดตามสถานะการจัดส่ง");
        System.out.println("5. ออกจากระบบ");
    }

    private void handleCustomerOptions(int choice) {
        switch (choice) {
            case 1:
                System.out.println("ฟังก์ชันนี้เฉพาะสำหรับลูกค้าเท่านั้น.");
                displayDrinkOptions2choice();
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

    private void handleManagerOptions(int choice) {
        // จัดการการทำงานสำหรับตัวเลือกของผู้จัดการ
        if (choice == 5) {
            System.out.println("ออกจากระบบ.");
        } else {
            System.out.println("กำลังดำเนินการฟังก์ชันของผู้จัดการ.");
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

    private void displayDrinkOptions2choice() {
        System.out.println("\nคุณต้องการสั่งแบบไหน");
        System.out.println("1.สั่งเครื่องดื่มแบบทันที");
        System.out.println("2.สั่งเครื่องดื่มแบบล่วงหน้า");
        System.out.println("3.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
                displayatstoreorrider();
                break;
            case 2:
                placePreOrder();
                break;
            case 3:
                // displayMainOptions1();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    // ฟังก์ชัน choosePickupTime ที่เราสร้าง
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

    // เรียกใช้ choosePickupTime() ในการสั่งล่วงหน้า
    private void placePreOrder() {
        System.out.println("การสั่งเครื่องดื่มแบบล่วงหน้า:");
        showOptions5choiceCategory();
        // addToCartadvance();

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

    private void displayatstoreorrider() {
        System.out.println("\nคุณต้องการรับรายการเครื่องดื่มแบบไหน");
        System.out.println("1.จัดส่ง");
        System.out.println("2.รับที่ร้าน");
        System.out.println("3.ย้อนกลับ");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int Options2choice = getUserInput();
        switch (Options2choice) {
            case 1:
            System.out.print("กรุณากรอกสถานที่จัดส่ง: ");
            String deliveryAddress = scanner.nextLine();
            System.out.println("คุณได้เลือกจัดส่งไปยัง: " + deliveryAddress);
            displayDrinkOptions();
                break;
            case 2:
                displayDrinkOptions();
                break;
            case 3:
                displayDrinkOptions2choice();
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void showOptions5choiceCategory() {
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
                break;
            case 2:
                displayDrinksByCategory(promoCategory);
                break;
            case 3:
                displayDrinksByCategory(teaCategory);
                break;
            case 4:
                displayDrinksByCategory(coffeeCategory);
                break;
            case 5:
                displayDrinksByCategory(milkCategory);
                break;
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void Options5choiceCategory() {
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
                displayDrinkOptions();
                break;
            case 2:
                displayDrinksByCategory(promoCategory);
                displayDrinkOptions();
                break;
            case 3:
                displayDrinksByCategory(teaCategory);
                displayDrinkOptions();
                break;
            case 4:
                displayDrinksByCategory(coffeeCategory);
                displayDrinkOptions();
                break;
            case 5:
                displayDrinksByCategory(milkCategory);
                displayDrinkOptions();
                break;
            case 6:
                // displayMainOptions1();
            default:
                System.out.println("ตัวเลือกไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    }

    private void displayDrinksByCategory(DrinkCategory category) {
        System.out.println("\n=== " + category.getCategoryName() + " ===");
        for (Drink drink : menu.getDrinks()) {
            if (category.getCategoryID() == 1 || drink.getCategory().getCategoryID() == category.getCategoryID()) {
                System.out.printf("%d. %s - $%.2f\n", drink.getDrinkID(), drink.getName(), drink.getPrice());
            }
        }
    }

    private void displayDrinkOptions() {
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
                Options5choiceCategory();
                break;
            case 2:
                addToCart();
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

    private int getUserInput() {
        while (!scanner.hasNextInt()) {
            System.out.print("กรุณาใส่หมายเลขที่ถูกต้อง: ");
            scanner.next();
        }
        int input = scanner.nextInt();
        scanner.nextLine();
        return input;
    }

    private void addToCart() {
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
            System.out.print("เลือกระดับความหวานหรือไม่? (yes/no): ");
            String addSweetness = scanner.nextLine();
            if (addSweetness.equalsIgnoreCase("yes")) {
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
            }
    
            // เลือกประเภทเครื่องดื่ม
            System.out.print("เลือกประเภทเครื่องดื่มหรือไม่? (yes/no): ");
            String addPreparationType = scanner.nextLine();
            if (addPreparationType.equalsIgnoreCase("yes")) {
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
                    System.out.println("เลือกประเภทเครื่องดื่ม " + selectedPreparationType.getPrepName() + " เรียบร้อยแล้ว.");
                } else {
                    System.out.println("ประเภทเครื่องดื่มไม่ถูกต้อง.");
                }
            }
    
            // เลือกจำนวนแก้ว
            System.out.print("กรุณาระบุจำนวนแก้วที่ต้องการ: ");
            int quantity = scanner.nextInt();
            scanner.nextLine();
    
            if (quantity > 0) {
                // เพิ่มเครื่องดื่มในตะกร้าพร้อมกับท็อปปิ้ง ความหวาน ประเภทเครื่องดื่ม และจำนวน
                cart.addItem(selectedDrink, selectedTopping, selectedSweetness, selectedPreparationType, quantity);
                System.out.println("เพิ่ม " + selectedDrink.getName() + " จำนวน " + quantity + " แก้ว ลงในตะกร้าเรียบร้อยแล้ว.");
            } else {
                System.out.println("จำนวนไม่ถูกต้อง กรุณาลองใหม่.");
            }
    
        } else {
            System.out.println("หมายเลขเครื่องดื่มไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
        }
    
        displayDrinkOptions();
    }
    
    // private void addToCartadvance() { // ตะกร้าสั่งล่วงหน้า test
    //     System.out.print("กรุณาเลือกหมายเลขเครื่องดื่มที่ต้องการเพิ่มในตะกร้า: ");
    //     int drinkId = getUserInput();
    //     Drink selectedDrink = menu.getDrink(drinkId);
    //     if (selectedDrink != null) {
    //         cart.addItem(selectedDrink);
    //         System.out.println("เพิ่มเครื่องดื่ม " + selectedDrink.getName() + " ลงในตะกร้าเรียบร้อยแล้ว.");
    //         System.out.print("ต้องการเพิ่มท็อปปิ้งหรือไม่? (yes/no): ");
    //         String addTopping = scanner.nextLine();
    //         if (addTopping.equalsIgnoreCase("yes")) {
    //             menu.displayMenutopping();
    //             System.out.print("กรุณาเลือกหมายเลขท็อปปิ้ง: ");
    //             int toppingNum = scanner.nextInt();
    //             scanner.nextLine(); // เพื่อเคลียร์ newline
    //             Topping selectedTopping = menu.getTopping(toppingNum);
    //             if (selectedTopping != null) {
    //                 cart.addItem(selectedTopping);
    //                 System.out
    //                         .println("เพิ่มท็อปปิ้ง " + selectedTopping.getToppingName() + " ลงในตะกร้าเรียบร้อยแล้ว.");
    //             } else {
    //                 System.out.println("ท็อปปิ้งไม่ถูกต้อง.");
    //             }
    //         }

    //         System.out.print("เลือกระดับความหวานหรือไม่? (yes/no): ");
    //         String addSweetness = scanner.nextLine();
    //         if (addSweetness.equalsIgnoreCase("yes")) {
    //             menu.displayMenusweetness();
    //             System.out.print("กรุณาเลือกหมายเลขระดับความหวาน: ");
    //             int sweetnessNum = scanner.nextInt();
    //             scanner.nextLine(); // เพื่อเคลียร์ newline
    //             Sweetness selectedSweetness = menu.getSweetness(sweetnessNum);
    //             if (selectedSweetness != null) {
    //                 cart.addItem(selectedSweetness);
    //                 System.out
    //                         .println("เลือกระดับความหวาน " + selectedSweetness.getSweetnessName() + " เรียบร้อยแล้ว.");
    //             } else {
    //                 System.out.println("ระดับความหวานไม่ถูกต้อง.");
    //             }
    //         }

    //         System.out.print("เลือกประเภทเครื่องดื่มหรือไม่? (yes/no): ");
    //         String addPreparationType = scanner.nextLine();
    //         if (addPreparationType.equalsIgnoreCase("yes")) {
    //             menu.displayPreparationType();
    //             System.out.print("กรุณาเลือกหมายเลขประเภทเครื่องดื่ม: ");
    //             int PreparationTypeNum = scanner.nextInt();
    //             scanner.nextLine(); // เพื่อเคลียร์ newline
    //             PreparationType selectedPreparationType = menu.getPreparationType(PreparationTypeNum);
    //             if (selectedPreparationType != null) {
    //                 cart.addItem(selectedPreparationType);
    //                 System.out.println(
    //                         "เลือกประเภทเครื่องดื่ม " + selectedPreparationType.getPrepName() + " เรียบร้อยแล้ว.");
    //             } else {
    //                 System.out.println("ประเภทเครื่องดื่มไม่ถูกต้อง.");
    //             }
    //         }
    //     } else {
    //         System.out.println("หมายเลขเครื่องดื่มไม่ถูกต้อง กรุณาลองใหม่อีกครั้ง.");
    //     }

    // }

    private void viewCart() {
        System.out.println("\n--- ตะกร้าของคุณ ---");
        cart.displayCart();
        displayDrinkOptions();
    }

    private void trackOrderStatus() {
        if (currentOrderStatus != null) {
            currentOrderStatus.trackOrderStatus();
        } else {
            System.out.println("ยังไม่มีคำสั่งซื้อที่สามารถติดตามสถานะได้.");
        }
    }

    public void processPayment(Order order) {
        if (order != null) {
            // ดึงข้อมูล drinks และ quantities จาก cart
            List<Drink> drinks = cart.getDrinks();
            List<Integer> quantities = cart.getQuantities();

            // สร้าง Receipt โดยส่ง order, drinks, และ quantities
            Receipt receipt = new Receipt(order, drinks, quantities, discount);
            receipt.printReceipt();
        } else {
            System.out.println("ไม่สามารถสร้างใบเสร็จได้ เนื่องจากคำสั่งซื้อเป็น null");
        }
    }

    private void proceedToPayment() {
        if (cart.isEmpty()) {
            System.out.println("ตะกร้าของคุณว่างอยู่ กรุณาเพิ่มรายการก่อนทำการชำระเงิน.");
            return;
        }

        float totalAmount = cart.getTotalPrice();
        Scanner scanner = new Scanner(System.in);

        System.out.println("เลือกวิธีการชำระเงิน:");
        System.out.println("1. QR Code");
        System.out.println("2. บัตรเครดิต");
        System.out.print("กรุณาเลือกหมายเลข: ");
        int paymentChoice = scanner.nextInt();
        scanner.nextLine(); // จัดการ newline

        String paymentMethod = (paymentChoice == 1) ? "QR" : "Credit Card";

        List<Drink> drinks = cart.getDrinks();
        List<Integer> quantities = cart.getQuantities();

        int orderId = (int) (Math.random() * 1000);
        Order order = new Order(orderId, currentCustomer, totalAmount, drinks, quantities, discount);

        Payment payment = new Payment(totalAmount, paymentMethod, currentCustomer);
        payment.processPayment(order, drinks, quantities);

        cart.clearCart();
    }

    public static void main(String[] args) {
        CafeManagement cafeManagement = new CafeManagement();
        cafeManagement.start();
    }
}
