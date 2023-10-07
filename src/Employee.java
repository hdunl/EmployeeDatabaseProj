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

    // Getter method for the first name
    public String getFirstName() {
        return firstName;
    }

    // Getter method for the last name
    public String getLastName() {
        return lastName;
    }

    // Getter method for the employee ID
    public int getEmployeeID() {
        return employeeID;
    }

    // Getter method for the job title
    public String getJobTitle() {
        return jobTitle;
    }

    // Getter method for the salary
    public double getSalary() {
        return salary;
    }

    // Getter method for the email
    public String getEmail() {
        return email;
    }

    // Getter method for the phone number
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
