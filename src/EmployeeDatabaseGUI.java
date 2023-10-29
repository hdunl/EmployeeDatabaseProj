import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class EmployeeDatabaseGUI {
    private final JFrame frame;
    private final EmployeeManager employeeManager;
    private final DefaultTableModel tableModel;
    private final String[] firstNames = {
            "James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth",
            "David", "Susan", "Joseph", "Jessica", "Charles", "Karen", "Thomas", "Nancy", "Daniel", "Lisa",
            "Matthew", "Betty", "Christopher", "Dorothy", "George", "Sandra", "Richard", "Helen", "Edward", "Donna",
            "Brian", "Carol", "Ronald", "Ruth", "Kenneth", "Sharon", "Anthony", "Michelle", "Larry", "Laura",
            "Mark", "Sarah", "Paul", "Kimberly", "Steven", "Deborah", "Kevin", "Cynthia", "Jason", "Kathleen",
            "Jeffrey", "Angela", "Frank", "Pamela", "Scott", "Debra", "Eric", "Stephanie", "Stephen", "Carolyn",
            "Andrew", "Maria", "Raymond", "Sue", "Gregory", "Janet", "Joshua", "Christine", "Dennis", "Katherine",
            "Peter", "Gloria", "Jerry", "Teresa", "Patrick", "Virginia", "Douglas", "Diane", "Brian", "Julie",
            "Jose", "Joyce", "Arthur", "Beverly", "Ryan", "Frances", "Albert", "Joan", "Jonathan", "Alice",
            "Justin", "Evelyn", "Terry", "Doris", "Timothy", "Jean", "Anthony", "Cheryl", "Henry", "Mildred",
            "Sean", "Kathy", "Lawrence", "Joann", "Christian", "Lori", "Austin", "Ann", "Joe", "Martha",
            "Ethan", "Louise", "Benjamin", "Diana", "Nicholas", "Judith", "Samuel", "Rose", "Willie", "Grace",
            "Jeremy", "Victoria", "Ralph", "Sara", "Harry", "Julia", "Philip", "Hannah", "Johnny", "Theresa",
            "Bobby", "Madison", "Walter", "Brenda", "Eugene", "Peggy", "Ray", "Denise", "Wayne", "Carolyn",
            "Billy", "Janice", "Steve", "Jeanette", "Louis", "Katie", "Alan", "Ellen", "Bryan", "Crystal",
            "Howard", "Rita", "Evan", "Shirley", "Phillip", "Glenda", "Vincent", "Thelma", "Logan", "Florence",
            "Randy", "Ethel", "Carlos", "Audrey", "Martin", "Tina", "Victor", "Bobbie", "Caleb", "Marie",
            "Roy", "Vicki", "Sam", "Kristen", "Frederick", "Connie", "Alan", "Tracy", "Harry", "Melissa",
            "Troy", "Renee", "Derek", "Veronica", "Tony", "Sylvia", "Cody", "Edna", "Carlos", "Viola",
            "Eddie", "Kelli", "Jordan", "Gladys", "Ian", "Hazel", "Theodore", "Georgia", "Clarence", "Jackie",
            "Nathan", "Samantha", "Alex", "Eva", "Ricky", "Yvonne", "Mason", "Wilma", "Ronnie", "Jo",
            "Philip", "Daisy", "Isaac", "Carole", "Jared", "Della", "Lewis", "Vera", "Hunter", "Joanna",
            "Aaron", "Dianne", "Henry", "Roxanne", "Timothy", "Geraldine", "Jesse", "Kristin", "Antonio", "Rosalie",
            "Eli", "Candace", "Jasper", "Lillian", "Bradley", "Marlene", "Colton", "Margie", "Oscar", "Gina",
            "Joel", "Sally", "Dustin", "Jessie", "Landon", "Vivian", "Aiden", "Lorraine", "Carter", "Lena",
            "Bentley", "Becky", "Chase", "Marian", "Dawson", "Gwendolyn", "Jaxson", "Eleanor", "Miles", "Lucille",
            "Lincoln", "Marcia", "Tristan", "Lillie", "Kayden", "Roxie", "Grayson", "Nellie", "Eli", "Estelle",
            "Hudson", "Harriet", "Easton", "Eunice", "Brandon", "Lela", "Carson", "Faye", "Brayden", "Carmen",
            "Jackson", "Delores", "Nolan", "Patsy", "Dominic", "Nora", "Austin", "Georgia", "Bryson", "Jeannette",
            "Liam", "Arlene", "Brody", "Inez", "Leo", "Betsy", "Levi", "Susie", "Caleb", "Fannie",
            "Xavier", "Cecelia", "Parker", "Nina", "Owen", "Blanche", "Wyatt", "Maureen", "Grayson", "Essie"
    };
    private final String[] lastNames = {
            "Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Davis", "García", "Rodriguez", "Martinez",
            "Hernandez", "López", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "White",
            "Harris", "Clark", "Lewis", "Young", "Walker", "Hall", "Allen", "Scott", "Adams", "Baker",
            "Carter", "Mitchell", "Turner", "Roberts", "Phillips", "Campbell", "Parker", "Evans", "Edwards", "Collins",
            "Stewart", "Morris", "Nguyen", "Murphy", "Rivera", "Cook", "Bell", "Bailey", "Cooper", "Richardson",
            "Cox", "Howard", "Ward", "Torres", "Peterson", "Gray", "Ramirez", "James", "Watson", "Brooks",
            "Kelly", "Sanders", "Price", "Bennett", "Wood", "Barnes", "Ross", "Henderson", "Coleman", "Jenkins",
            "Perry", "Powell", "Long", "Patterson", "Hughes", "Flores", "Washington", "Butler", "Simmons", "Foster",
            "Gonzales", "Bryant", "Alexander", "Russell", "Griffin", "Diaz", "Hayes", "Myers", "Ford", "Hamilton",
            "Graham", "Sullivan", "Wallace", "Woods", "Cole", "West", "Jordan", "Owens", "Reynolds", "Fisher",
            "Ellis", "Harrison", "Gibson", "McDonald", "Cruz", "Marshall", "Ortiz", "Gomez", "Murray", "Freeman",
            "Wells", "Webb", "Simpson", "Stevens", "Tucker", "Porter", "Hunter", "Hicks", "Crawford", "Henry",
            "Boyd", "Mason", "Morales", "Kennedy", "Warren", "Dixon", "Ramos", "Reyes", "Burns", "Gordon",
            "Shaw", "Holmes", "Rice", "Robertson", "Hunt", "Black", "Daniels", "Palmer", "Mills", "Nichols",
            "Grant", "Knight", "Ferguson", "Rose", "Stone", "Hawkins", "Dunn", "Perkins", "Hudson", "Spencer",
            "Gardner", "Stephens", "Payne", "Pierce", "Berry", "Matthews", "Arnold", "Wagner", "Willis", "Ray",
            "Watkins", "Olson", "Carroll", "Duncan", "Snyder", "Hart", "Cunningham", "Bradley", "Lane", "Andrews",
            "Ruiz", "Harper", "Fox", "Riley", "Armstrong", "Carpenter", "Weaver", "Greene", "Lawrence", "Elliott",
            "Chavez", "Sims", "Austin", "Peters", "Kelley", "Franklin", "Lawson", "Fields", "Gutierrez", "Ryan",
            "Schmidt", "Carr", "Vasquez", "Castillo", "Wheeler", "Chapman", "Oliver", "Montgomery", "Richards", "Williamson",
            "Johnston", "Banks", "Meyer", "Bishop", "McCoy", "Howell", "Alvarez", "Morrison", "Hansen", "Fernandez",
            "Garza", "Harvey", "Little", "Burton", "Stanley", "Ng", "George", "Jacobs", "Reid", "Kim"
    };
    private final String[] jobTitles = {
            "Programmer", "UI/UX Designer", "Systems Engineer", "Cybersecurity Architect", "Sales", "Data Analyst",
            "Network Administrator", "Database Administrator", "DevOps Engineer", "Cloud Architect", "Web Developer",
            "Software Engineer", "IT Support Specialist", "Digital Marketing Specialist", "Product Manager",
            "Artificial Intelligence (AI) Engineer", "Machine Learning Engineer", "Quality Assurance (QA) Tester",
            "Business Analyst", "Project Manager", "Network Engineer", "Front-End Developer", "Back-End Developer",
            "Scrum Master", "Technical Writer", "UX Researcher", "Data Scientist", "Mobile App Developer", "UI Designer",
            "ERP Consultant", "Full Stack Developer", "Solution Architect", "Content Strategist", "DevSecOps Engineer",
            "Cloud Solutions Architect", "Marketing Manager", "Data Engineer", "Product Owner", "Systems Administrator",
            "UX/UI Developer", "Data Privacy Officer", "Customer Support Specialist", "Graphic Designer",
            "Financial Analyst", "Marketing Coordinator", "Operations Manager", "Business Development Manager",
            "Product Marketing Manager", "Market Research Analyst", "E-commerce Manager", "SEO Specialist", "Content Writer",
            "Social Media Manager", "Email Marketing Specialist", "Sales Manager", "Account Executive", "Financial Planner",
            "Management Consultant", "Supply Chain Analyst", "Brand Manager", "Public Relations Manager", "Customer Success Manager",
            "Event Coordinator", "HR Generalist", "Technical Project Manager", "Digital Advertising Specialist", "Data Architect",
            "CRM Analyst", "IT Auditor", "Data Quality Analyst", "UX/UI Designer", "Digital Strategist", "Technical Support Specialist",
            "Content Marketing Manager", "Online Community Manager", "Customer Experience Manager", "Demand Generation Manager",
            "Market Analyst", "Product Analyst", "Revenue Analyst", "Sales Operations Manager", "Business Intelligence Analyst",
            "Marketing Automation Specialist", "Product Designer", "UX Research Manager", "Cybersecurity Analyst"
    };

    public EmployeeDatabaseGUI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;
        frame = new JFrame("Employee Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        tableModel = new DefaultTableModel(
                new String[]{"Employee ID", "First Name", "Last Name", "Job Title", "Salary", "Email", "Phone Number"},
                0
        );



        // Make cells non-editable
        JTable employeeTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        setUpColumnSorting(employeeTable);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        JButton addButton = new JButton("Add Employee");
        JButton searchButton = new JButton("Search Employee");
        JButton deleteButton = new JButton("Delete Employee");
        JButton populateDatabaseButton = new JButton("Populate Database");

        final Employee[] employee = {null};

        addButton.addActionListener(e -> {
            EmployeeDialog dialog = new EmployeeDialog(frame, "Add Employee", employee[0]);
            dialog.setVisible(true);

            if (dialog.isEmployeeAdded()) {
                employee[0] = dialog.getEmployee();
                long startTime = System.nanoTime();
                employeeManager.insertEmployee(employee[0]);
                long endTime = System.nanoTime();
                updateEmployeeTable();
                JOptionPane.showMessageDialog(frame, "Addition time: " + (endTime - startTime) + " ns", "Employee Addition", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        searchButton.addActionListener(e -> openSearchDialog());

        deleteButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Employee ID to delete:");
            if (input != null && !input.isEmpty()) {
                try {
                    int employeeID = Integer.parseInt(input);
                    long startTime = System.nanoTime();
                    employeeManager.deleteEmployee(employeeID);
                    long endTime = System.nanoTime();
                    updateEmployeeTable();
                    JOptionPane.showMessageDialog(frame, "Deletion time: " + (endTime - startTime) + " ns", "Employee Deletion", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        populateDatabaseButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter number of employees to generate:");
            if (input != null && !input.isEmpty()) {
                try {
                    int numberOfEmployees = Integer.parseInt(input);
                    long startTime = System.currentTimeMillis();
                    for (int i = 0; i < numberOfEmployees; i++) {
                        Employee employee1 = generateRandomEmployee(i + 1);
                        employeeManager.insertEmployee(employee1);
                    }
                    long endTime = System.currentTimeMillis();
                    JOptionPane.showMessageDialog(frame, "Population time: " + (endTime - startTime) + " ms", "Database Population", JOptionPane.INFORMATION_MESSAGE);
                    updateEmployeeTable();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid number.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(populateDatabaseButton);

        frame.setLayout(new BorderLayout());
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setUpColumnSorting(JTable employeeTable) {
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(tableModel);
        employeeTable.setRowSorter(sorter);

        // Enable sorting for each column
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            sorter.setComparator(i, new EmployeeColumnComparator(i));
        }
    }

    private record EmployeeColumnComparator(int columnIndex) implements Comparator<Object> {

        @Override
            public int compare(Object o1, Object o2) {
                String s1 = o1.toString();
                String s2 = o2.toString();

                if (columnIndex == 0 || columnIndex == 4) {
                    // Sort Employee ID and Salary as integers
                    try {
                        int num1 = Integer.parseInt(s1);
                        int num2 = Integer.parseInt(s2);
                        return Integer.compare(num1, num2);
                    } catch (NumberFormatException e) {
                        return s1.compareTo(s2);
                    }
                } else {
                    // Sort other columns as strings
                    return s1.compareTo(s2);
                }
            }
        }

    private void displayEmployee(Employee employee) {
        JOptionPane.showMessageDialog(frame, employee.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void openSearchDialog() {
        JComboBox<String> searchFieldComboBox = new JComboBox<>(new String[]{"First Name", "Last Name", "Job Title", "Salary", "Email", "Phone Number", "Employee ID"});
        JTextField searchValueTextField = new JTextField();

        int result = JOptionPane.showConfirmDialog(frame,
                new Object[] {"Select a search field:", searchFieldComboBox, "Enter a search value:", searchValueTextField},
                "Search Employee",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String selectedField = Objects.requireNonNull(searchFieldComboBox.getSelectedItem()).toString();
            String searchValue = searchValueTextField.getText().trim();

            if (!searchValue.isEmpty()) {
                // Start the timer in milliseconds
                long startTime = System.currentTimeMillis();

                // Perform the search in a background thread
                SwingUtilities.invokeLater(() -> {
                    List<Employee> foundEmployees = searchEmployeesByField(selectedField, searchValue);

                    // Stop the timer in milliseconds
                    long endTime = System.currentTimeMillis();

                    // Update the UI with the search results
                    SwingUtilities.invokeLater(() -> {
                        if (!foundEmployees.isEmpty()) {
                            displayFoundEmployees(foundEmployees);
                            long searchTimeMillis = endTime - startTime;
                            JOptionPane.showMessageDialog(frame, "Search time: " + searchTimeMillis + " ms", "Employee Search", JOptionPane.INFORMATION_MESSAGE);
                        } else {
                            JOptionPane.showMessageDialog(frame, "No employees found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                        }
                    });
                });
            } else {
                JOptionPane.showMessageDialog(frame, "Please enter a search value.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }



    private void displayFoundEmployees(List<Employee> foundEmployees) {
        if (foundEmployees.isEmpty()) {
            JOptionPane.showMessageDialog(frame, "No employees found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] employeeNames = foundEmployees.stream().map(employee ->
                employee.getFirstName() + " " + employee.getLastName()).toArray(String[]::new);

        JComboBox<String> employeeComboBox = new JComboBox<>(employeeNames);
        int result = JOptionPane.showConfirmDialog(frame,
                new Object[] {"Select an employee:", employeeComboBox},
                "Select Employee",
                JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            int selectedIndex = employeeComboBox.getSelectedIndex();
            if (selectedIndex >= 0 && selectedIndex < foundEmployees.size()) {
                Employee selectedEmployee = foundEmployees.get(selectedIndex);
                displayEmployee(selectedEmployee);
            }
        }
    }





    private Employee generateRandomEmployee(int currentID) {
        Random random = new Random();
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        String jobTitle = jobTitles[random.nextInt(jobTitles.length)];
        double randomSalaryBase = 20000 + random.nextInt(80000);
        double randomSalary = randomSalaryBase - (randomSalaryBase % 100);
        String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@company.com";
        String phoneNumber = String.format("%010d", random.nextInt(1000000000));

        return new Employee(firstName, lastName, currentID, jobTitle, randomSalary, email, phoneNumber);
    }

    private List<Employee> searchEmployeesByField(String field, String searchValue) {
        List<Employee> foundEmployees = new ArrayList<>();
        List<Employee> allEmployees = employeeManager.getAllEmployees();

        for (Employee employee : allEmployees) {
            if (employee != null && doesFieldMatch(employee, field, searchValue)) {
                foundEmployees.add(employee);
            }
        }

        return foundEmployees;
    }


    private boolean doesFieldMatch(Employee employee, String field, String searchValue) {
        return switch (field) {
            case "First Name" -> employee.getFirstName().equalsIgnoreCase(searchValue);
            case "Last Name" -> employee.getLastName().equalsIgnoreCase(searchValue);
            case "Job Title" -> employee.getJobTitle().equalsIgnoreCase(searchValue);
            case "Salary" -> String.valueOf(employee.getSalary()).equalsIgnoreCase(searchValue);
            case "Email" -> employee.getEmail().equalsIgnoreCase(searchValue);
            case "Phone Number" -> employee.getPhoneNumber().equalsIgnoreCase(searchValue);
            case "Employee ID" -> String.valueOf(employee.getEmployeeID()).equalsIgnoreCase(searchValue);
            default -> false;
        };
    }




    private void updateEmployeeTable() {
        tableModel.setRowCount(0);

        List<Employee> employees = employeeManager.getAllEmployees();
        for (Employee employee : employees) {
            if (employee != null) {
                Object[] rowData = {
                        employee.getEmployeeID(),
                        employee.getFirstName(),
                        employee.getLastName(),
                        employee.getJobTitle(),
                        employee.getSalary(),
                        employee.getEmail(),
                        employee.getPhoneNumber()
                };
                tableModel.addRow(rowData);
            }
        }
    }

    public void show() {
        updateEmployeeTable();
        frame.setVisible(true);
    }

    private static class EmployeeDialog extends JDialog {
        private final AtomicReference<Employee> employeeReference;
        private boolean employeeAdded;

        private final JTextField firstNameField;
        private final JTextField lastNameField;
        private final JTextField jobTitleField;
        private final JTextField salaryField;
        private final JTextField emailField;
        private final JTextField phoneNumberField;
        private final JTextField employeeIDField;

        public EmployeeDialog(JFrame parent, String title, Employee employee) {
            super(parent, title, true);
            this.employeeReference = new AtomicReference<>(employee);

            JPanel panel = new JPanel(new GridLayout(8, 2));
            JLabel firstNameLabel = new JLabel("First Name:");
            JLabel lastNameLabel = new JLabel("Last Name:");
            JLabel jobTitleLabel = new JLabel("Job Title:");
            JLabel salaryLabel = new JLabel("Salary:");
            JLabel emailLabel = new JLabel("Email:");
            JLabel phoneNumberLabel = new JLabel("Phone Number:");
            JLabel employeeIDLabel = new JLabel("Employee ID:");

            firstNameField = new JTextField();
            lastNameField = new JTextField();
            jobTitleField = new JTextField();
            salaryField = new JTextField();
            emailField = new JTextField();
            phoneNumberField = new JTextField();
            employeeIDField = new JTextField();

            if (employee != null) {
                firstNameField.setText(employee.getFirstName());
                lastNameField.setText(employee.getLastName());
                jobTitleField.setText(employee.getJobTitle());
                salaryField.setText(String.valueOf(employee.getSalary()));
                emailField.setText(employee.getEmail());
                phoneNumberField.setText(employee.getPhoneNumber());
                employeeIDField.setText(String.valueOf(employee.getEmployeeID()));
            }

            panel.add(firstNameLabel);
            panel.add(firstNameField);
            panel.add(lastNameLabel);
            panel.add(lastNameField);
            panel.add(jobTitleLabel);
            panel.add(jobTitleField);
            panel.add(salaryLabel);
            panel.add(salaryField);
            panel.add(emailLabel);
            panel.add(emailField);
            panel.add(phoneNumberLabel);
            panel.add(phoneNumberField);
            panel.add(employeeIDLabel);
            panel.add(employeeIDField);

            JButton addButton = new JButton("Add");
            addButton.addActionListener(e -> {
                if (validateInput()) {
                    Employee newEmployee = createEmployee();
                    employeeReference.set(newEmployee);
                    employeeAdded = true;
                    dispose();
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(e -> dispose());

            panel.add(addButton);
            panel.add(cancelButton);

            getContentPane().add(panel);
            pack();
            setLocationRelativeTo(parent);
        }

        private boolean validateInput() {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String jobTitle = jobTitleField.getText().trim();
            double salary;
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();

            if (firstName.isEmpty() || lastName.isEmpty() || jobTitle.isEmpty() || email.isEmpty() || phoneNumber.isEmpty()) {
                showError("All fields are required.");
                return false;
            }

            try {
                salary = Double.parseDouble(salaryField.getText());
                if (salary <= 0) {
                    showError("Salary must be a positive number.");
                    return false;
                }
            } catch (NumberFormatException ex) {
                showError("Invalid Salary. Please enter a valid number.");
                return false;
            }

            if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", email)) {
                showError("Invalid Email Format.");
                return false;
            }

            if (!Pattern.matches("^\\d{10}$", phoneNumber)) {
                showError("Invalid Phone Number Format (10 digits without spaces or dashes).");
                return false;
            }

            return true;
        }

        private void showError(String message) {
            JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
        }

        private Employee createEmployee() {
            String firstName = firstNameField.getText().trim();
            String lastName = lastNameField.getText().trim();
            String jobTitle = jobTitleField.getText().trim();
            double salary = Double.parseDouble(salaryField.getText());
            String email = emailField.getText().trim();
            String phoneNumber = phoneNumberField.getText().trim();
            int employeeID = Integer.parseInt(employeeIDField.getText());

            return new Employee(firstName, lastName, employeeID, jobTitle, salary, email, phoneNumber);
        }

        public Employee getEmployee() {
            return employeeReference.get();
        }

        public boolean isEmployeeAdded() {
            return employeeAdded;
        }
    }

    public static void main(String[] args) {
        EmployeeManager employeeManager = new EmployeeManager();

        SwingUtilities.invokeLater(() -> {
            EmployeeDatabaseGUI gui = new EmployeeDatabaseGUI(employeeManager);
            gui.show();
        });
    }
}
