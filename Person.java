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

    public String getName() {
        return name;
    }

    public String getPersonID() {
        return personID;
    }

}
