# water-supply-notification-system
This is the backend implementation of a Water Supply Notification System built using **Spring Boot**. It allows users to register with a location ID, receive water supply notifications via **email**, and enables admins to schedule or manage supplies for specific locations. The services are designed as **microservices** with JWT-based authentication and Kafka-based event-driven communication.

---

## ✨ Features

**Secure Authentication:** Implements **JWT-based authentication** and **role-based authorization** (User/Admin) for all API access.
**Automated Email Notifications:** Sends scheduled water supply notifications directly to users' registered email addresses.
**Admin Dashboard APIs:** Provides comprehensive REST APIs for administrators to create, update, and delete water supply schedules for specific locations.
**User Management:** Enables user registration and login, with associated location binding for targeted notifications.
**Event-Driven Communication:** Leverages **Apache Kafka** for asynchronous messaging and efficient event-based notification dispatch between services.
**Data Persistence:** Utilizes **PostgreSQL** for robust and reliable storage of application data.

---

## 🛠️ Tech Stack

* **Language:** Java 17
* **Frameworks:** Spring Boot, Spring Security (with JWT)
* **Messaging:** Apache Kafka
* **Database:** PostgreSQL
* **Email Service:** JavaMailSender
* **Build Tool:** Maven

## 🔐 Authentication

- Users/Admins authenticate via JWT tokens
- Token must be passed as a Bearer token in the Authorization header

## 📬 Notifications

- Implemented via **JavaMailSender**
- Kafka publishes `WaterSupplyEvent`, and Notification Service listens and sends email

  
---

## 🚀 Running the Project

### 1. Clone the Repository
git clone https://github.com/prachitil11/water-supply-notification-system
cd water-supply-notification-system
Configure PostgresSQL & Kafka
PostgresSQL should be running on localhost:5432 (can be changed in application.properties)
Kafka must be up and running (default port 9092)
Update your email credentials in the Notification Service

##Build and Run
mvn clean install

Run each microservice individually:
cd UserService
mvn spring-boot:run

cd ../WaterSupplyService
mvn spring-boot:run

cd ../NotificationService
mvn spring-boot:run

Sample API Endpoints
🔐 Auth
POST /auth/register - Register user
POST /auth/login - Login and receive JWT token

👤 User
GET /users/{locationId} - Get users by location (admin only)

💧 Admin
POST /admin/schedule - Schedule water supply
PUT /admin/schedule/{id} - Update schedule
DELETE /admin/schedule/{id} - Delete schedule

## 📈 Future Improvements

* Integrate a Geolocation API for real-time user location during registration.
* Develop a user-friendly frontend (e.g., using React or Angular) to interact with the backend APIs.
* Explore deploying to a cloud platform (AWS, Azure, GCP).

---

## 🤝 Author

* **Prachiti Lokhande**
