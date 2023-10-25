import java.util.ArrayList;
import java.util.List;

class AVLNode {
    Employee employee;
    AVLNode left;
    AVLNode right;
    int height;

    public AVLNode(Employee employee) {
        this.employee = employee;
        this.height = 1; // Height of a new node is initialized to 1
    }
}

public class AVLTree {
    private AVLNode root;

    // Constructor to initialize an empty AVL tree
    public AVLTree() {
        root = null;
    }

    // Helper method to get the height of a node
    private int height(AVLNode node) {
        if (node == null)
            return 0;
        return node.height;
    }

    // Helper method to calculate the balance factor of a node
    private int getBalanceFactor(AVLNode node) {
        if (node == null)
            return 0;
        return height(node.left) - height(node.right);
    }

    // Helper method to update the height of a node based on its children's heights
    private void updateHeight(AVLNode node) {
        if (node != null)
            node.height = Math.max(height(node.left), height(node.right)) + 1;
    }

    // Helper method for right rotation
    private AVLNode rotateRight(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Helper method for left rotation
    private AVLNode rotateLeft(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Helper method to balance the tree after an insertion
    private AVLNode insert(AVLNode node, Employee employee) {
        if (node == null) {
            if (employee != null)
                return new AVLNode(employee);
            else
                return null; // Null employee, return null node
        }

        if (employee == null) {
            // Handle case where employee is null, return the node unchanged
            return node;
        }

        if (employee.getEmployeeID() < node.employee.getEmployeeID())
            node.left = insert(node.left, employee);
        else if (employee.getEmployeeID() > node.employee.getEmployeeID())
            node.right = insert(node.right, employee);
        else
            return node; // Duplicate IDs not allowed

        // Update height of the current node
        updateHeight(node);

        // Get the balance factor
        int balance = getBalanceFactor(node);

        // Perform rotations if necessary to balance the tree
        if (balance > 1) {
            if (employee.getEmployeeID() < node.left.employee.getEmployeeID())
                return rotateRight(node);
            else {
                node.left = rotateLeft(node.left);
                return rotateRight(node);
            }
        }
        if (balance < -1) {
            if (employee.getEmployeeID() > node.right.employee.getEmployeeID())
                return rotateLeft(node);
            else {
                node.right = rotateRight(node.right);
                return rotateLeft(node);
            }
        }

        return node;
    }

    // Public method to insert an employee into the AVL tree
    public void insert(Employee employee) {
        root = insert(root, employee);
    }

    // Helper method to find the node with the smallest key in the tree
    private AVLNode findMinNode(AVLNode node) {
        while (node.left != null)
            node = node.left;
        return node;
    }

    // Helper method to delete an employee by employee ID
    private AVLNode delete(AVLNode root, int employeeID) {
        if (root == null)
            return root;

        if (root.employee == null) {
            // Handle case where the current node has a null Employee object
            return root;
        }

        if (employeeID < root.employee.getEmployeeID())
            root.left = delete(root.left, employeeID);
        else if (employeeID > root.employee.getEmployeeID())
            root.right = delete(root.right, employeeID);
        else {
            // Node with only one child or no child
            if (root.left == null || root.right == null) {
                AVLNode temp = root.left != null ? root.left : root.right;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child

                temp = null;
            } else {
                // Node with two children: get the inorder successor (smallest in the right subtree)
                AVLNode temp = findMinNode(root.right);

                // Copy the inorder successor's data to this node
                root.employee = temp.employee;

                // Delete the inorder successor
                root.right = delete(root.right, temp.employee.getEmployeeID());
            }
        }

        // If the tree had only one node, return
        if (root == null)
            return root;

        // Update height of the current node
        updateHeight(root);

        // Get the balance factor
        int balance = getBalanceFactor(root);

        // Perform rotations if necessary to balance the tree
        if (balance > 1) {
            if (getBalanceFactor(root.left) >= 0)
                return rotateRight(root);
            else {
                root.left = rotateLeft(root.left);
                return rotateRight(root);
            }
        }
        if (balance < -1) {
            if (getBalanceFactor(root.right) <= 0)
                return rotateLeft(root);
            else {
                root.right = rotateRight(root.right);
                return rotateLeft(root);
            }
        }

        return root;
    }

    // Public method to delete an employee by employee ID
    public void delete(int employeeID) {
        root = delete(root, employeeID);
    }

    // Helper method to search for an employee by employee ID
    private Employee search(AVLNode node, int employeeID) {
        if (node == null)
            return null;

        if (employeeID == node.employee.getEmployeeID())
            return node.employee;
        else if (employeeID < node.employee.getEmployeeID())
            return search(node.left, employeeID);
        else
            return search(node.right, employeeID);
    }

    // Public method to search for an employee by employee ID
    public Employee search(int employeeID) {
        return search(root, employeeID);
    }

    // Helper method to traverse the tree in-order and add employees to a list
    private void inOrderTraversal(AVLNode node, List<Employee> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.employee);
            inOrderTraversal(node.right, result);
        }
    }

    // Public method to retrieve a list of all employees in-order
    public List<Employee> getAllEmployees() {
        List<Employee> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }
}
