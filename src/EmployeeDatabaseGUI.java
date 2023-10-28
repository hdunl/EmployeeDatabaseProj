import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Pattern;

public class EmployeeDatabaseGUI {
    private final JFrame frame;
    private final EmployeeManager employeeManager;
    private final DefaultTableModel tableModel;
    private final String[] firstNames = {"James", "Mary", "John", "Patricia", "Robert", "Jennifer", "Michael", "Linda", "William", "Elizabeth", "David", "Susan", "Joseph", "Jessica", "Charles", "Karen", "Thomas", "Nancy", "Daniel", "Lisa", "Matthew", "Betty", "Christopher", "Dorothy"};
    private final String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Miller", "Davis", "García", "Rodriguez", "Martinez", "Hernandez", "López", "Gonzalez", "Wilson", "Anderson", "Thomas", "Taylor", "Moore", "Jackson", "White", "Harris", "Clark", "Lewis", "Young", "Walker"};
    private final String[] jobTitles = {"Programmer", "UI/UX Designer", "Systems Engineer", "Cybersecurity Architect", "Sales", "Data Analyst", "Network Administrator", "Database Administrator", "DevOps Engineer", "Cloud Architect", "Web Developer", "Software Engineer", "IT Support Specialist", "Digital Marketing Specialist", "Product Manager", "Artificial Intelligence (AI) Engineer", "Machine Learning Engineer", "Quality Assurance (QA) Tester", "Business Analyst", "Project Manager", "Network Engineer", "Front-End Developer", "Back-End Developer", "Scrum Master", "Technical Writer"};

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

        searchButton.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(frame, "Enter Employee ID to search:");
            if (input != null && !input.isEmpty()) {
                try {
                    int employeeID = Integer.parseInt(input);
                    long startTime = System.nanoTime();
                    Employee foundEmployee = employeeManager.searchEmployee(employeeID);
                    long endTime = System.nanoTime();

                    if (foundEmployee != null) {
                        displayEmployee(foundEmployee);
                        JOptionPane.showMessageDialog(frame, "Search time: " + (endTime - startTime) + " ns", "Employee Search", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(frame, "Employee not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(frame, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

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
            sorter.setComparator(i, (Comparator<?>) new EmployeeColumnComparator(i));
        }
    }

    private class EmployeeColumnComparator implements Comparator<Object> {
        private final int columnIndex;

        public EmployeeColumnComparator(int columnIndex) {
            this.columnIndex = columnIndex;
        }

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
