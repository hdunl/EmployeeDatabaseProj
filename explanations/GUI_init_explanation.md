# Employee Database Application

## Overview

This Employee Database Application allows users to manage employee information efficiently. It provides a user-friendly graphical interface for adding, searching, and deleting employee records while ensuring data integrity through the use of an AVL tree data structure.

## Initialization of EmployeeManager

- When you click "Run," an `EmployeeManager` object is created to initialize the employee database.

## GUI on the Event Dispatch Thread (EDT)

- The graphical user interface (GUI) is launched on the Event Dispatch Thread (EDT) to ensure responsiveness.

## Initialization of EmployeeDatabaseGUI

- The main GUI class, `EmployeeDatabaseGUI`, is initialized, setting up the application's graphical components.

## Creating the Main Window (JFrame)

- A `JFrame`, titled "Employee Database," serves as the primary application window.

## Table for Employee Data

- A `JTable`, `employeeTable`, is created to display employee information. It's configured to prevent direct cell editing.

## User Interaction Buttons

- Buttons like "Add Employee," "Search Employee," "Delete Employee," and "Populate Database" are created for user interaction.

### Event Handling - Add Employee Button

1. **Button Clicked:**
   - When you click the "Add Employee" button, it triggers an action listener.

2. **Dialog for Input:**
   - A dialog window opens, allowing you to input employee details such as first name, last name, job title, salary, email, and phone number.

3. **Employee Data Creation:**
   - After entering the employee details and confirming, a new `Employee` object is created with the provided information.

4. **Insertion into AVL Tree:**
   - The `EmployeeManager` class is responsible for managing employee data. It utilizes an AVL tree data structure for efficient storage.
   - The `Employee` object created in the previous step is inserted into the AVL tree using the `insertEmployee` method. This method ensures that the AVL tree remains balanced.

5. **Tree Balancing:**
   - The AVL tree automatically balances itself after each insertion to maintain its height balance property.

6. **Table Update:**
   - After successfully inserting the employee data into the AVL tree, the graphical table displaying employee information is updated. The new employee's data is added to the table.

7. **Timing Information:**
   - A message dialog displays the time taken for this addition operation in nanoseconds, providing insight into the efficiency of the insertion process.

### Event Handling - Search Employee Button

1. **Button Clicked:**
   - Clicking the "Search Employee" button triggers an action listener.

2. **Dialog for Input:**
   - A dialog prompts you to enter an employee ID to search for.

3. **Employee Search:**
   - The `EmployeeManager` class searches for the employee with the specified ID in the AVL tree database.

4. **Display Result:**
   - If the employee is found, their details are displayed in a dialog box.
   - A message dialog displays the time taken for this search operation in nanoseconds.

5. **Not Found Handling:**
   - If the employee is not found, an appropriate message dialog informs you.

### Event Handling - Delete Employee Button

1. **Button Clicked:**
   - Clicking the "Delete Employee" button triggers an action listener.

2. **Dialog for Input:**
   - A dialog asks you to enter an employee ID to delete.

3. **Employee Deletion:**
   - The `EmployeeManager` deletes the employee with the specified ID from the AVL tree database.

4. **Table Update:**
   - The table displaying employee data is updated to reflect the removal.

5. **Timing Information:**
   - A message dialog displays the time taken for this deletion operation in nanoseconds.

### Event Handling - Populate Database Button

1. **Button Clicked:**
   - Clicking the "Populate Database" button triggers an action listener.

2. **Dialog for Input:**
   - A dialog prompts you to enter the number of employees to generate and add to the database.

3. **Random Employee Data Generation:**
   - Random employee data, including first name, last name, job title, salary, email, and phone number, is generated for the specified number of employees.

4. **Insertion into AVL Tree:**
   - Each generated employee is inserted into the AVL tree through the `EmployeeManager`.

5. **Table Update:**
   - The table displaying employee data is updated to reflect the newly populated employees.

6. **Timing Information:**
   - A message dialog displays the time taken for this population operation in milliseconds.

## Continued User Interaction

- The GUI remains responsive, allowing you to continue interacting with it until you close the application window.
