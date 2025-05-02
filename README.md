# 🌿 Green Shadow (Pvt) Ltd - Crop Management System (Backend)

This is the backend of the **Crop Management System** for Green Shadow (Pvt) Ltd — an agricultural platform designed to manage staff, vehicles, equipment, crops, fields, and monitoring logs. The system features role-based access control and secure JWT authentication for user operations.

---

## 🔧 Tech Stack

- **Backend Framework:** Spring Boot  
- **Persistence Layer:** Spring Data JPA  
- **Security:** Spring Security with JWT Authentication  
- **ORM:** Hibernate  
- **Database:** MySQL  
- **Boilerplate Reduction:** Lombok  
- **Logging:** Logback (console + file logging)  
- **API Testing:** Postman  

---

## 👥 User Roles & Permissions

The system supports **role-based access control** with the following user categories:

- **Administrative**
- **Manager**
- **Scientist**
- **Others**

Each role has different permissions to access and manage various modules.

---

## 🔐 Authentication & Authorization

- **Sign Up / Sign In** functionality for all roles
- **JWT-based authentication** for secure and stateless access
- Role-based method protection using `@PreAuthorize`

---

## 📦 Key Modules

- 👤 **Staff Management**  
- 🚜 **Vehicle Management**  
- 🛠️ **Equipment Management**  
- 🌾 **Crop Management**  
- 📍 **Field Management**  
- 📈 **Monitoring Logs**

All modules are secured and accessible based on the user's role.

---

## 🚀 Getting Started

### 📁 Clone the Repository
```bash
git clone https://github.com/deshinikanchana/Green_shadow--Pvt-_Ltd.git
cd Green_shadow--Pvt-_Ltd
```
#
### ⚙️ Set Up MySQL Database

1. Create a database named (e.g., green_shadow_db)

2. Configure DB credentials in src/main/resources/application.properties:
```bash
spring.datasource.url=jdbc:mysql://localhost:3306/green_shadow_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
#

### 🛠️ Run the Application

```bash
./mvnw spring-boot:run
```
---

## 📝 License

- This project is licensed under the MIT License — see the [LICENSE](License) file for details.

