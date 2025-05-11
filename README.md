E-Commerce Microservice Project
🚀 Overview

This is a full-stack, containerized e-commerce application built using microservices architecture. The backend consists of Spring Boot (Java) and Express.js (Node.js) microservices. The frontend is developed using React.js and styled with Tailwind CSS.

The application supports Stripe and PayPal for payment processing, uses Kafka for asynchronous messaging, includes service discovery via Eureka, and uses Spring Cloud Config Server for centralized configuration management. Spring Cloud Gateway is used as an API Gateway for routing requests to individual services, load balancing, and handling security concerns across all microservices.

All services run in Docker containers managed with Docker Compose.

🧩 Key Features

🛠️ Backend Microservices

Spring Boot Microservices:

Product Service

Order Service

Authentication Service

Payment Service (Supports Stripe & PayPal)

Node.js (Express.js) Microservices:

Shipping Service

Notification Service

🎨 Frontend
Built with React.js and styled using Tailwind CSS

Integrated Stripe and PayPal SDKs for secure payments

User registration, login, product listing, cart, and order flow

🗃️ Database
PostgreSQL (Dockerized)

MongoDB (Dockerized)

🔍 Service Discovery

Eureka Server for microservice registration and discovery

📑 API Documentation

Swagger UI for each backend service using Springdoc OpenAPI

📦 Asynchronous Messaging

Apache Kafka for inter-service communication

🐳 Containerization

Fully containerized using Docker

Docker Compose for orchestration

🧰 Tech Stack

Frontend: React.js, Tailwind CSS

Backend:

Spring Boot (Java)

Express.js (Node.js)

Database: PostgreSQL, MongoDB

Messaging: Kafka

Service Discovery: Eureka

Payments: Stripe SDK, PayPal REST API

Containerization: Docker, Docker Compose

Documentation: Swagger (OpenAPI)

✅ Prerequisites

Ensure you have the following installed:

Docker

Docker Compose

Node.js

Java 17

🛠️ Setup & Run Instructions

Clone the Repository

bash
Copy
Edit
git clone https://github.com/your-username/ecommerce-microservices.git

cd ecommerce-microservices

Create .env Files

Create .env files for each microservice needing environment variables (e.g., DB credentials, Stripe, PayPal keys):

Example for payment-service/.env:

env
Copy
Edit

DATABASE_URL=jdbc:postgresql://localhost:5432/paymentdb

DATABASE_USERNAME=your_db_username

DATABASE_PASSWORD=your_db_password

# Stripe

STRIPE_SECRET_KEY=your_stripe_secret_key

STRIPE_PUBLIC_KEY=your_stripe_public_key

# PayPal

PAYPAL_CLIENT_ID=your_paypal_client_id

PAYPAL_CLIENT_SECRET=your_paypal_client_secret

PAYPAL_MODE=sandbox

🔒 Don't forget to add .env files to .gitignore.

Configure Docker Compose

Ensure that docker-compose.yml exists in the root directory and includes all services (frontend, backend, Kafka, Eureka, DB, Config Server, API Gateway, etc.).

Run the App

bash
Copy
Edit

docker-compose up --build

🌐 Accessing the Application

🔗 API Gateway

All backend services are accessible through the API Gateway:

URL: http://localhost:8085

Microservice	Route

Product Service	/products

Order Service	/orders

Payment Service	/payments

Authentication	/auth

Shipping	/shipping

Notification	/notifications

📚 Swagger UI

Microservice	Swagger URL

Product Service	localhost:8089/swagger-ui

Order Service	localhost:8083/swagger-ui

Payment Service	localhost:8081/swagger-ui

🎨 Frontend UI

URL: http://localhost:3001

Integrated Stripe & PayPal SDKs

Fully responsive design

Includes checkout, and user auth

💳 Payment Integration

The app supports Stripe and PayPal:

Backend (Java):

Uses Stripe AND PayPal Developer API

Payment logic is implemented in the payment-service.

Frontend (React):

Stripe: Uses @stripe/react-stripe-js and @stripe/stripe-js for secure card collection and tokenization.


PayPal: Uses PayPal JS SDK to render PayPal buttons and handle payments.


You can choose either method during checkout.


🐳 Containerization with Docker & Docker Compose

This project uses Docker for containerization and Docker Compose for orchestration.

📦 Docker Commands

Start all services:

bash

Copy
Edit

docker-compose up --build

Stop all services:

bash
Copy
Edit

docker-compose 

View logs:

bash
Copy
Edit

docker-compose logs

🛠️ Troubleshooting

Check that all .env files are correctly configured.

Ensure PostgreSQL and Kafka are healthy in Docker.

If Swagger UI doesn't load, verify the microservice is running.

🔐 API Gateway & Centralized Configuration

Spring Cloud Config Server

The Spring Cloud Config Server is used to centralize the configuration of all microservices, making it easier to manage application properties, database credentials, and other configurations. It allows for:

Centralized management of configuration properties across microservices

Easy updates to configurations without redeploying individual services

Support for various backends like Git, JDBC, or native property files

Spring Cloud Gateway

Spring Cloud Gateway acts as an API Gateway for routing requests to different services. Its key benefits are:

Centralized Routing: It provides a unified entry point for accessing backend services, reducing complexity for clients.

Load Balancing: Integrated with Eureka, Spring Cloud Gateway can distribute incoming requests across multiple instances of a service to improve scalability and reliability.

Security: With Spring Cloud Gateway, security can be centralized, applying authentication and authorization policies uniformly to all microservices.

Rate Limiting: You can implement throttling and rate-limiting at the API Gateway level to protect backend services from excessive load.

Resiliency: It integrates with Spring Cloud Circuit Breaker to provide fault tolerance, ensuring that failures in one service do not impact the entire application.

🤝 Contributing

Contributions are welcome! Fork the repo, make your changes, and submit a PR.

📄 License
This project is licensed under the MIT License. See the LICENSE file for details.
