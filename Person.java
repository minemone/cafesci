public class Person {
    protected String personID;
    private String name;
    private Role role;

    // Constructor
    public Person(String personID, String name, Role role) {
        this.personID = personID;
        this.name = name;
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public String getPersonID() {
        return personID;
    }

    public void performAction(String action) {
        switch (role) {
            case CUSTOMER:
                if ("order".equals(action)) {
                    orderFood();
                } else if ("viewMenu".equals(action)) {
                    viewMenu();
                }
                break;
            case MANAGER:
                if ("manageMenu".equals(action)) {
                    manageMenu();
                } else if ("viewReports".equals(action)) {
                    viewReports();
                }
                break;
            case RIDER:
                if ("viewDelivery".equals(action)) {
                    viewDeliveryStatus();
                } else if ("updateStatus".equals(action)) {
                    updateStatus();
                }
                break;
            default:
                System.out.println("Action not permitted.");
                break;
        }
    }

    // ฟังก์ชันสำหรับแต่ละการกระทำ
    private void orderFood() {
        System.out.println(name + " is ordering food.");
    }

    private void viewMenu() {
        System.out.println(name + " is viewing the menu.");
    }

    private void manageMenu() {
        System.out.println(name + " is managing the menu.");
    }

    private void viewReports() {
        System.out.println(name + " is viewing reports.");
    }

    private void viewDeliveryStatus() {
        System.out.println(name + " is viewing delivery status.");
    }

    private void updateStatus() {
        System.out.println(name + " is updating the delivery status.");
    }
}
