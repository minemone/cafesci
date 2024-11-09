public class Status {
    private int statusID;
    private int cafetableID;
    private int orderID;
    private String statusDesc;

    // Constructor
    public Status(int statusID, int cafetableID, int orderID, String statusDesc) {
        this.statusID = statusID;
        this.cafetableID = cafetableID;
        this.orderID = orderID;
        this.statusDesc = statusDesc;
    }

}
