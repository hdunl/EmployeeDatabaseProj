import java.util.*;

public class EmployeeManager {
    private final AVLTree avlTree; // Assume you have an AVLTree class

    public EmployeeManager() {
        avlTree = new AVLTree(); // Initialize AVL tree
    }

    // Method to insert an employee into the AVL tree
    public void insertEmployee(final Employee employee) { // Add 'final' here
        avlTree.insert(employee);
    }

    // Method to delete an employee by employee ID
    public void deleteEmployee(int employeeID) {
        avlTree.delete(employeeID);
    }


    // Method to retrieve a list of all employees
    public List<Employee> getAllEmployees() {
        return avlTree.getAllEmployees();
    }


}
