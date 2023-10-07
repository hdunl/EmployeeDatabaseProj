import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.regex.Pattern;
import java.util.concurrent.atomic.AtomicReference;

public class EmployeeDatabaseGUI {
    private JFrame frame;
    private EmployeeManager employeeManager;
    private DefaultTableModel tableModel;
    private JTable employeeTable;

    public EmployeeDatabaseGUI(EmployeeManager employeeManager) {
        this.employeeManager = employeeManager;

        frame = new JFrame("Employee Database");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);

        tableModel = new DefaultTableModel(
                new String[]{"Employee ID", "First Name", "Last Name", "Job Title", "Salary", "Email", "Phone Number"},
                0
        );

        employeeTable = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make cells non-editable
            }
        };
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        JButton addButton = new JButton("Add Employee");
        JButton searchButton = new JButton("Search Employee");
        JButton deleteButton = new JButton("Delete Employee");

        final Employee[] employee = {null};

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EmployeeDialog dialog = new EmployeeDialog(frame, "Add Employee", employee[0]);
                dialog.setVisible(true);

                if (dialog.isEmployeeAdded()) {
                    employee[0] = dialog.getEmployee();
                    employeeManager.insertEmployee(employee[0]);
                    updateEmployeeTable();
                }
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter Employee ID to search:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int employeeID = Integer.parseInt(input);
                        Employee foundEmployee = employeeManager.searchEmployee(employeeID);

                        if (foundEmployee != null) {
                            displayEmployee(foundEmployee);
                        } else {
                            JOptionPane.showMessageDialog(frame, "Employee not found.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = JOptionPane.showInputDialog(frame, "Enter Employee ID to delete:");
                if (input != null && !input.isEmpty()) {
                    try {
                        int employeeID = Integer.parseInt(input);
                        employeeManager.deleteEmployee(employeeID);
                        updateEmployeeTable();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid Employee ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);

        frame.setLayout(new BorderLayout());
        frame.add(tableScrollPane, BorderLayout.CENTER);
        frame.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void displayEmployee(Employee employee) {
        JOptionPane.showMessageDialog(frame, employee.toString(), "Employee Details", JOptionPane.INFORMATION_MESSAGE);
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

    public static void main(String[] args) {
        EmployeeManager employeeManager = new EmployeeManager();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                EmployeeDatabaseGUI gui = new EmployeeDatabaseGUI(employeeManager);
                gui.show();
            }
        });
    }

    private class EmployeeDialog extends JDialog {
        private AtomicReference<Employee> employeeReference;
        private boolean employeeAdded;

        private JTextField firstNameField;
        private JTextField lastNameField;
        private JTextField jobTitleField;
        private JTextField salaryField;
        private JTextField emailField;
        private JTextField phoneNumberField;
        private JTextField employeeIDField;

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

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (validateInput()) {
                        Employee newEmployee = createEmployee();
                        employeeReference.set(newEmployee);
                        employeeAdded = true;
                        dispose();
                    }
                }
            });

            JButton cancelButton = new JButton("Cancel");
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

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
            int employeeID;

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

            try {
                employeeID = Integer.parseInt(employeeIDField.getText());
            } catch (NumberFormatException ex) {
                showError("Invalid Employee ID. Please enter a valid number.");
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
}