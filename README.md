# Secure Notes

A console based Java application for managing personal notes with user authentication and role based access control.

---

## Description

Secure Notes allows users to register an account, log in, and manage their own personal notes through a console menu. Passwords are hashed using BCrypt before being stored in the database. The application supports two roles: USER and ADMIN.

---

## Features

**User**
- Register an account with a username and password
- Log in with hashed password verification
- Create, view, edit, and delete personal notes
- Change password

**Admin**
- View all notes in the system
- Delete any user's note

---

## Technologies

- Java 24
- Maven
- MySQL
- JDBC
- BCrypt (jBCrypt 0.4)

---

## Requirements

- Java 17 or higher
- Maven
- MySQL Server

---

## Database Setup

Run the following SQL in MySQL Workbench or any MySQL client:

```sql
CREATE DATABASE secure_notes;
USE secure_notes;

CREATE TABLE users (
    id       INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(10)  NOT NULL DEFAULT 'USER'
);

CREATE TABLE notes (
    id         INT AUTO_INCREMENT PRIMARY KEY,
    user_id    INT          NOT NULL,
    title      VARCHAR(100) NOT NULL,
    content    TEXT         NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```

To create the admin user, first register a user named `admin` through the application, then run:

```sql
UPDATE users SET role = 'ADMIN' WHERE username = 'admin';
```

---

## Configuration

Open `src/main/java/com/securenotes/database/DatabaseConnection.java` and update the following fields with your database credentials:

```java
private static final String HOST     = "localhost";
private static final String PORT     = "3306";
private static final String DATABASE = "secure_notes";
private static final String USERNAME = "root";
private static final String PASSWORD = "your_password";
```

---

## How to Run

1. Clone the repository
2. Set up the database as described above
3. Update the database credentials in `DatabaseConnection.java`
4. Build the project with Maven:

```bash
mvn clean install
```

5. Run the application:

```bash
mvn exec:java -Dexec.mainClass="com.securenotes.Main"
```

Or run `Main.java` directly from IntelliJ IDEA.

---

## Project Structure

```
src/main/java/com/securenotes/
├── Main.java
├── database/
│   └── DatabaseConnection.java
├── model/
│   ├── User.java
│   └── Note.java
├── repository/
│   ├── UserRepository.java
│   └── NoteRepository.java
├── service/
│   ├── AuthService.java
│   └── NoteService.java
└── ui/
    ├── MainMenu.java
    ├── UserMenu.java
    └── AdminMenu.java
```

---

## Security

- Passwords are never stored in plain text
- BCrypt hashing is applied before saving to the database
- Each user can only access and modify their own notes
- Admin access is assigned directly in the database and cannot be registered through the application

---

## Author

MDAX1