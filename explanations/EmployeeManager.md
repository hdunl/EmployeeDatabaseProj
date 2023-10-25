# EmployeeManager Class - In-Depth Explanation

## Introduction

The `EmployeeManager` class serves as a management layer over the `AVLTree` data structure for handling employee-related operations. It provides functionalities like inserting, deleting, searching for employees, and retrieving all employees.

## Class Structure

### Attributes

The `EmployeeManager` class contains the following attribute:

- `avlTree`: An instance of the AVLTree class. This tree will store Employee objects and allow for efficient insertion, deletion, and retrieval.

### Constructor

The class has a default constructor that initializes the `avlTree`:

```java
public EmployeeManager() {
avlTree = new AVLTree(); // Initialize AVL tree
}
```

## Management Methods

### Insert Employee

The `insertEmployee` method allows you to add a new `Employee` object into the AVL tree:

```java
public void insertEmployee(final Employee employee) {
avlTree.insert(employee);
}
```

### Delete Employee

The `deleteEmployee` method lets you remove an employee from the AVL tree based on the employee ID:

```java
public void deleteEmployee(int employeeID) {
avlTree.delete(employeeID);
}
```

### Search Employee

You can search for an employee in the AVL tree using their employee ID via the `searchEmployee` method:

```java
public Employee searchEmployee(int employeeID) {
return avlTree.search(employeeID);
}
```

### Retrieve All Employees

The `getAllEmployees` method fetches a list containing all employees stored in the AVL tree:

```java
public List<Employee> getAllEmployees() {
return avlTree.getAllEmployees();
}
```

## Dependency

This class is dependent on the `AVLTree` class for its underlying data storage and management. The `Employee` objects are stored in this AVL tree, which provides efficient operations thanks to its self-balancing nature.
