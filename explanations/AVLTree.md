# AVLTree and AVLNode - In-Depth Explanation

## Overview

The `AVLTree` and `AVLNode` classes together represent an AVL (Adelson-Velsky and Landis) Tree, a self-balancing binary search tree. The `AVLTree` class handles various tree operations, while the `AVLNode` class represents individual nodes within the tree.

## AVLNode Class Structure

### Attributes

- `employee`: An instance of the `Employee` class representing the data stored in the node.
- `left`: Pointer to the left child node.
- `right`: Pointer to the right child node.
- `height`: An integer representing the height of the node in the tree.

### Constructor

```java
public AVLNode(Employee employee) {
this.employee = employee;
this.height = 1; // Height of a new node is initialized to 1
}
```

## AVLTree Class Structure

### Attributes

- `root`: The root node of the AVL Tree.

### Constructor

```java
public AVLTree() {
root = null;
}
```

### Tree Operations

#### Insertion

The insertion operation ensures that the tree remains balanced after every insertion:

```java
public void insert(Employee employee) {
root = insert(root, employee);
}
```

#### Deletion

Deletes an employee node based on its ID:

```java
public void delete(int employeeID) {
root = delete(root, employeeID);
}
```

#### Searching

Search operations can be performed based on employee ID:

```java
public Employee search(int employeeID) {...}
```

#### Retrieving All Employees

To retrieve a list of all employees:

```java
public List<Employee> getAllEmployees() {...}
```

## Tree Balancing

The AVLTree class includes methods to ensure that the tree remains balanced. The balance is maintained by keeping track of a node's height and ensuring that the difference in height (the balance factor) between the left and right child of every node is no more than 1.

### Key Concepts:

1. **Balance Factor**: The balance factor of a node is the height of the left subtree minus the height of the right subtree. A balance factor can be -1, 0, or +1 without the tree violating AVL conditions. Any other value indicates that rebalancing is needed.

2. **Rotations**: Rotations are used to restore the balance of an AVL tree. There are four types of rotations:

    - **Right Rotation (LL Rotation)**: Used when the left subtree of the left child is taller than its right subtree.
    - **Left Rotation (RR Rotation)**: Used when the right subtree of the right child is taller than its left subtree.
    - **Left-Right Rotation (LR Rotation)**: A combination of a left rotation followed by a right rotation.
    - **Right-Left Rotation (RL Rotation)**: A combination of a right rotation followed by a left rotation.

```java
private AVLNode rotateLeft(AVLNode x) {...}

private AVLNode rotateRight(AVLNode y) {...}
```

3. **Height Calculation and Update**: The height of a node is 1 plus the maximum of the heights of its children.

```java
private int height(AVLNode N) {
if (N == null)
return 0;
return N.height;
}
```

4. **Balance Factor Calculation**:

```java
private int getBalanceFactor(AVLNode N) {
if (N == null)
return 0;
return height(N.left) - height(N.right);
}
```

During insertion or deletion, after the basic binary search tree operation is done, the AVL property is restored by checking the balance factor and applying appropriate rotations.

## Dependency

Both `AVLTree` and `AVLNode` are dependent on the `Employee` class for storing and managing employee data.
