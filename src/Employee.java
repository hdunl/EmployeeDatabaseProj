public class Employee {
    private final String firstName;
    private final String lastName;
    private final int employeeID;
    private final String jobTitle;
    private final double salary;
    private final String email;
    private final String phoneNumber;

    // Constructor to initialize an Employee object
    public Employee(String firstName, String lastName, int employeeID, String jobTitle,
                    double salary, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.employeeID = employeeID;
        this.jobTitle = jobTitle;
        this.salary = salary;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Getter methods
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getEmployeeID() {
        return employeeID;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public double getSalary() {
        return salary;
    }

    public String getEmail() {
        return email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }


    // Override toString method to provide a string representation of the Employee
    @Override
    public String toString() {
        return "Employee ID: " + employeeID + "\n" +
                "Name: " + firstName + " " + lastName + "\n" +
                "Job Title: " + jobTitle + "\n" +
                "Salary: $" + salary + "\n" +
                "Email: " + email + "\n" +
                "Phone Number: " + phoneNumber;
    }
}
