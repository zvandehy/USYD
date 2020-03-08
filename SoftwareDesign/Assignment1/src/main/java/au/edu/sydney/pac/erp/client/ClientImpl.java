package au.edu.sydney.pac.erp.client;

public class ClientImpl implements Client {

    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String departmentCode;

    public ClientImpl(int id, String firstName, String lastName, String phoneNumber) throws IllegalArgumentException {
        //verify valid id
        if(id == 0) {
            throw new IllegalArgumentException("id cannot be zero");
        } else if (id < 0) {
            throw new IllegalArgumentException("id cannot be negative");
        } else {
            this.id = id;
        }
        //verify valid first name
        if (firstName == null || firstName.equals("")) {
            throw new IllegalArgumentException("firstName cannot be empty or null");
        } else {
            this.firstName = firstName;
        }
        //verify valid last name
        if (lastName == null || lastName.equals("")) {
            throw new IllegalArgumentException("lastName cannot be empty or null");
        } else {
            this.lastName = lastName;
        }
        //verify valid phone number
        if (phoneNumber == null || phoneNumber.equals("")) {
            throw new IllegalArgumentException("phoneNumber cannot be empty or null");
        } else {
            this.phoneNumber = phoneNumber;
        }
        this.departmentCode = null;
    }

    @Override
    public void assignDepartment(String departmentCode) throws IllegalStateException, IllegalArgumentException {
        if (isAssigned()) {
            throw new IllegalStateException("Client may not already have been assigned a client");
        } else if (departmentCode == null) {
            throw new IllegalArgumentException("department code cannot be null");
        } else if (departmentCode.isEmpty()) {
            throw new IllegalArgumentException("department code cannot be empty");
        } else {
            this.departmentCode = departmentCode;
        }
    }

    @Override
    public boolean isAssigned() {
        return (this.departmentCode != null);
    }

    @Override
    public String getDepartmentCode() {
        return departmentCode;
    }

    @Override
    public int getID() {
        return this.id;
    }

    @Override
    public String getFirstName() {
        return this.firstName;
    }

    @Override
    public String getLastName() {
        return this.lastName;
    }

    @Override
    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
