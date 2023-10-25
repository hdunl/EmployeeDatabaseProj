# Employee Class - In-Depth Explanation

## Introduction

The `Employee` class represents a model for an employee with various attributes such as name, employee ID, job title, and contact information. This class serves as a foundational data structure for any system handling employee-related operations.

## Class Structure

### Attributes

The `Employee` class encapsulates the following attributes:

- `firstName`: Represents the first name of the employee.
- `lastName`: Represents the last name of the employee.
- `employeeID`: A unique identification number for the employee.
- `jobTitle`: Describes the designation or role of the employee.
- `salary`: Denotes the monthly or yearly salary of the employee.
- `email`: An email address associated with the employee for communication.
- `phoneNumber`: The contact number of the employee.

### Constructor

The class has a parameterized constructor that initializes all its attributes:

```java
public Employee(String firstName, String lastName, int employeeID, String jobTitle,
double salary, String email, String phoneNumber)
```

## Accessor Methods

The class provides several accessor (getter) methods to retrieve the value of its attributes:

- `getFirstName()`: Returns the first name.
- `getLastName()`: Returns the last name.
- `getEmployeeID()`: Returns the unique employee ID.
- `getJobTitle()`: Returns the job title.
- `getSalary()`: Returns the salary.
- `getEmail()`: Returns the email address.
- `getPhoneNumber()`: Returns the phone number.
