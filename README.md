Java JDBC CRUD Application Setup

This guide will help you set up the environment to run the Main.java application.
1. Install MySQL Database

If you don't have MySQL installed, download and install it from the official website:
https://dev.mysql.com/downloads/installer/

During the installation, you will be asked to set a password for the root user. Remember this password.
2. Download MySQL JDBC Driver

You need the MySQL Connector/J driver to allow the Java application to communicate with your database.

    Go to the download page: https://dev.mysql.com/downloads/connector/j/

    Select "Platform Independent" as the operating system.

    Download the ZIP or TAR archive.

    Extract the archive, and you will find a .jar file (e.g., mysql-connector-j-8.x.x.jar). Keep this file handy.

3. Create the Database and Table

Next, you need to create the database and the users table that the application will use.

    Open the MySQL Command Line Client or any SQL tool like MySQL Workbench.

    Log in as the root user with the password you set.

    Run the following SQL commands one by one:

-- Create a new database named 'companydb'
CREATE DATABASE companydb;

-- Switch to the new database
USE companydb;

-- Create the 'users' table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE
);

-- (Optional) Verify that the table was created
DESCRIBE users;

4. Configure the Java Code

Open the Main.java file and update the database connection details at the top of the class to match your setup:

private static final String DB_URL = "jdbc:mysql://localhost:3306/companydb";
private static final String USER = "root";
private static final String PASS = "password"; // <-- CHANGE THIS to your actual root password

5. Compile and Run the Application

You can now compile and run the code from your terminal.

    Place the mysql-connector-j-x.x.x.jar file in the same directory as your JdbcCrudExample.java file.

    Open a terminal or command prompt in that directory.

    Compile the Java file:

        On Windows:

        javac -cp ".;mysql-connector-j-8.x.x.jar" Main.java

        On macOS/Linux:

        javac -cp ".:mysql-connector-j-8.x.x.jar" Main.java

    (Remember to replace mysql-connector-j-8.x.x.jar with the actual name of your JAR file.)

    Run the compiled code:

        On Windows:

        java -cp ".;mysql-connector-j-8.x.x.jar" Main.java

        On macOS/Linux:

        java -cp ".:mysql-connector-j-8.x.x.jar" Main.java

You should see the output in your console demonstrating the creation, reading, updating, and deletion of user records.

Similarly, if you want to use the GUI version of code it is saved as "GUI.java".

Compile it:

        On Windows:

        javac -cp ".;mysql-connector-j-8.x.x.jar" Main.java

        On macOS/Linux:

        javac -cp ".:mysql-connector-j-8.x.x.jar" Main.java

Run it:

        On Windows:

        java -cp ".;mysql-connector-j-8.x.x.jar" Main.java

        On macOS/Linux:

        java -cp ".:mysql-connector-j-8.x.x.jar" Main.java
