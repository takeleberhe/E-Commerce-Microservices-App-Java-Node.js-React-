🛒 E-Commerce Microservices Application
📝 Overview

This is a full-stack, containerized e-commerce application built using a microservices architecture. The backend is composed of Spring Boot (Java) and Express.js (Node.js) microservices, while the frontend is developed using React.js and styled with Tailwind CSS.

The platform supports secure user authentication, product management, order processing, and integrated payment gateways (Stripe & PayPal). Kafka handles asynchronous messaging between services, Eureka enables service discovery, and Spring Cloud Config Server centralizes configuration management. All services are containerized and orchestrated using Docker Compose.

🚀 Tech Stack

Front-end:

React.js (UI framework)

Tailwind CSS (Styling)

Stripe & PayPal SDKs (Payment integration)

Back-end:

Spring Boot (Java-based services)

Product Service

Order Service

Authentication Service

Payment Service

Express.js (Node.js-based services)

Shipping Service

Notification Service

Infrastructure:

PostgreSQL (Relational database)

MongoDB (NoSQL database)

Apache Kafka (Message broker)

Eureka Server (Service discovery)

Spring Cloud Gateway (API Gateway)

Spring Cloud Config Server (Centralized configuration)

Docker & Docker Compose (Containerization)

Swagger/OpenAPI (API documentation)

🎯 Features

🧩 Microservices

Spring Boot:

🛍 Product Service: Manage products

📦 Order Service: Handle order placement and status

🛡 Authentication Service: JWT-based user auth

💳 Payment Service: Stripe & PayPal integration

Node.js:

🚚 Shipping Service: Shipment and delivery management

📧 Notification Service: Email/SMS notifications

🎨 Frontend

Product listing & shopping cart

User login & registration

Checkout process with Stripe/PayPal

Responsive design with Tailwind CSS

📦 Messaging & Orchestration

Kafka-based asynchronous communication between services

Docker Compose for orchestrating containers

🔍 Service Discovery & API Gateway

Eureka for dynamic service registration and discovery

Spring Cloud Gateway for secure routing and load balancing

🛠 Installation & Setup

1️⃣ Clone the Repository

bash:


git clone https://github.com/takeleberhe/E-Commerce-Microservices-App-Java-Node.js-React-.git
cd ecommerce-microservices

2️⃣ Create .env Files

Create .env files for each microservice that needs secrets or credentials. Example (payment-service/.env):

env


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

⚠️ Make sure .env files are included in .gitignore. Never expose secrets publicly.

3️⃣ Configure Docker Compose

Ensure that docker-compose.yml includes all services:

Frontend

Backend microservices

Kafka

Eureka Server

Config Server

API Gateway

PostgreSQL & MongoDB

4️⃣ Run the Application
bash:


docker-compose up --build

🌐 Access the Application

🔗 API Gateway


URL: http://localhost:8085

Microservice	Route

Product Service	/products

Order Service	/orders

Payment Service	/payments

Authentication	/auth

Shipping Service	/shipping

Notification Service	/notifications

📚 Swagger UI

Service	URL

Product Service	http://localhost:8089/swagger-ui

Order Service	http://localhost:8083/swagger-ui

Payment Service	http://localhost:8081/swagger-ui

🛍 Frontend UI

URL: http://localhost:5173/

Login, registration

Checkout with Stripe or PayPal

Fully responsive design

💳 Payment Integration

Backend (Java - payment-service)

Supports Stripe and PayPal Developer APIs

Secure payment logic implemented in isolated microservice

Frontend (React)

Stripe: @stripe/react-stripe-js, @stripe/stripe-js

PayPal: PayPal JS SDK with hosted buttons

💡 Users can choose either Stripe or PayPal during checkout.

🐳 Docker & Orchestration

All services are containerized using Docker and orchestrated via Docker Compose.

Common Docker Commands:
bash:

# Start services

docker-compose up --build

# Stop services

docker-compose down

# View logs

docker-compose logs

🔐 Configuration & API Gateway

Spring Cloud Config Server

Centralized .yml and .properties management

Supports Git and native file storage

Enables dynamic config refresh for all services

Spring Cloud Gateway

Routes all requests to respective microservices

Integrated with Eureka for dynamic load balancing

Central place for applying:

Authentication and Authorization

Rate Limiting & Throttling

Circuit Breakers for fault tolerance

🤝 Contributing

Contributions are welcome!

Feel free to fork this repo, make changes, and submit a pull request.

📜 License

This project is licensed under the MIT License.
