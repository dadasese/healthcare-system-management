# Patient Microservice Architecture Project

A comprehensive project replicating a modern microservice architecture built with **Spring Boot** and leveraging several key technologies for inter-service communication, asynchronous processing, service discovery, and security.

## Technologies Used

* **Primary Language:** Java
* **Framework:** Spring Boot
* **Database:** PostgreSQL (with in-memory H2 for initial setup)
* **Service Communication:** gRPC (Remote Procedure Calls)
* **Messaging:** Apache Kafka (Asynchronous Event Streaming)
* **Security:** JWT-based Authentication Service
* **Gateway:** API Gateway (Service Routing and Security Integration)
* **Containerization:** Docker
* **Infrastructure as Code (IaC):** AWS CloudFormation (via LocalStack for local simulation)
* **Testing:** Integration Testing
* **Cloud Services (Simulated):** AWS services (VPC, RDS, MSK, ECS, ALB)

## Project Architecture Overview

This repository demonstrates the development of a microservice system, primarily featuring a **Patient Service** and its integration with other services:

* **Patient Service:** Core CRUD operations for patient records.
* **Billing Service:** An auxiliary service contacted via **gRPC**.
* **Analytics Service:** A service that consumes patient events via **Kafka**.
* **Auth Service:** Handles user login and JWT generation/validation.
* **API Gateway:** Routes external requests and enforces security policies.

---

## Core Development and API Implementation

This section details the initial setup, core CRUD operations, validation, and documentation.

### Setup and Initial Development

* **Developer Setup**
* **Spring Boot Architecture** Review
* **Project Setup**
* **Model, In-Memory DB (H2), and Data Setup**
* **Repository Layer (JPA)**
* **Service Layer (Business Logic)**

### RESTful API Implementation (Patient Service)

* **Patient Controller:** Implementing **GET** and **CREATE** endpoints (including DTOs).
* **Request Validation:** Handling and custom exception-based error management.
* **Update Endpoint** Implementation and Validation (including email-specific rules).
* **Delete Patient** Endpoint.
* **OpenAPI Documentation** Integration (Swagger/Springdoc).

---

## Containerization and Database Integration

* **Introduction to Docker**
* **Patient Service DB Setup** (Transition to **PostgreSQL**)
* **Patient Service Docker Setup**
* **Testing and Fixing DB Connections**

---

## ⚡ Inter-Service Communication: gRPC

Demonstration of high-performance inter-service communication using gRPC.

* **Introduction to gRPC**
* **Billing Service Setup** (defining the **Proto File**)
* **Generating gRPC Protobuf Code**
* **Implementing gRPC Server** in the Billing Service.
* **Dockerizing the Billing Service**
* **Implementing gRPC Client** in the Patient Service to call billing logic.

---

## Asynchronous Messaging: Apache Kafka

Implementation of asynchronous event streaming using Kafka for decoupling services.

* **Introduction to Kafka**
* **Setting up Kafka Broker Server**
* **Patient Service Kafka Producer** (dependencies, proto file, producer class).
* **Defining Kafka Patient Event Proto File**
* **Creating and Calling the Kafka Producer**
* **Analytics Service Setup** (including proto files).
* **Implementing Kafka Consumer** in the Analytics Service.
* **Dockerizing the Analytics Service** and Configuration/Testing.

---

## Security and Gateway: Auth Service & API Gateway

Centralizing entry points and enforcing security policies.

* **Introduction to API Gateway**
* **API Gateway Setup and Routing Rules**
* **API Gateway Dockerization and Testing**
* **Introduction to Authentication (Auth)**
* **Auth Service:** DB Setup, Model, and Data Seeding (using `data.sql`).
* **Login Endpoints:** Implementing Request/Response DTOs, Login Method, and **Password Encoding** (Spring Security).
* **JWT Implementation:** Creating a `JwtUtil` for token generation and **Token Validation Endpoint**.
* **Integrating Auth Service with API Gateway:** Implementing a **JWT Validation Filter** and exception handling.
* **Auth Service Documentation** via the Gateway.

---

## 🧪 Integration Testing

* **Introduction to Testing**
* **Integration Test Project Setup**
* **Writing Integration Tests** for:
    * Login (Success and Unauthorized scenarios).
    * Get Patient endpoint.

---

## ☁️ Cloud Infrastructure and Deployment (IaC)

Using Infrastructure as Code to define and deploy the microservices architecture.

* **Introduction to AWS** and **LocalStack** Setup.
* **Setup AWS CLI**
* **Introduction to IaC: CloudFormation**
* **Infrastructure Project Setup** and creating the first stack.
* **Defining Core AWS Resources:**
    * **VPC** (Virtual Private Cloud)
    * **RDS** (Databases for services)
    * **MSK Cluster** (Managed Kafka Service)
    * **ECS Cluster and Services** (Container Orchestration)
    * **Load Balanced Application Gateway** (ALB)
* **Preparing Docker Images** for Deployment.
* **Deployment to LocalStack** and **Testing the Deployment**.
* **Stack Deletion and Troubleshooting**

---
