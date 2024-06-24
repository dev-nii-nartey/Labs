# Library Management System

## Overview

The Library Management System is a JavaFX application designed to help libraries manage their operations efficiently. This system allows librarians to register patrons, manage book inventory, and handle book issuance and returns. It is built using Java and JavaFX for the frontend and MySQL for the backend database.

## Features

- **Patron Registration:** Easily register new patrons with their name and email.
- **Book Management:** Add, issue, and return books with a few clicks.
- **User-Friendly Interface:** Intuitive and visually appealing GUI.
- **Database Integration:** Uses MySQL for reliable and efficient data storage.

## Requirements

### Software

1. **Java Development Kit (JDK) 11 or higher**
    - [Download JDK](https://www.oracle.com/java/technologies/javase-downloads.html)
2. **MySQL Database**
    - [Download MySQL](https://dev.mysql.com/downloads/installer/)
3. **Apache Maven**
    - [Download Maven](https://maven.apache.org/download.cgi)
4. **MySQL Connector for Java**
    - [Download MySQL Connector](https://dev.mysql.com/downloads/connector/j/)

### Hardware

- A computer with at least 2GB of RAM.
- At least 500MB of free disk space.

## Installation

### Step 1: Clone the Repository

git clone https://github.com/yourusername/library-management-system.git
cd library-management-system 


## Step 2: Set Up the MySQL Database
# 1 Install MySQL:
Follow the installation guide on the MySQL website to install MySQL on your system.
# 2 Create a Database:
Open the MySQL command line client and run the following command to create a database:
```bash
CREATE DATABASE library_management
```
# 3 Create Tables:

Use the following SQL script to create the necessary tables:
USE library_management;
```bash
CREATE TABLE patrons (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE books (
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    genre VARCHAR(255) NOT NULL,
    publisher VARCHAR(255),
    year_published INT
);

CREATE TABLE transactions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    patron_id INT,
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES books(id),
    FOREIGN KEY (patron_id) REFERENCES patrons(id)
);
  
 CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);
```
# step 4: Configure Database Connection
Download MySQL Connector for Java:

Download the MySQL Connector/J from the official MySQL website.
Add MySQL Connector to Project: 

Add the MySQL Connector JAR file to your project's lib directory.
Update Database Configuration:

Open DatabaseHelper.java and update the database connection details:
```bash
public class DatabaseHelper {
    private static final String URL = "jdbc:mysql://localhost:3306/library_management";
    private static final String USER = "your-username";
    private static final String PASSWORD = "your-password";
    public static Connection connect() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
```
## Step 5: Build the Project
# Use Maven to build the project:
mvn clean install
## Step 6: Run the Application
Run the application using the following Maven command:
mvn javafx:run

# Usage
#### Login: Use the login screen to access the main dashboard.
#### Register Patron: Navigate to the patron registration section and enter the required details.
#### Manage Books: Use the options to add new books, issue books to patrons, and process book returns.