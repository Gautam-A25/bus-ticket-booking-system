# Bus Ticket Booking System

A full-stack Bus Ticket Booking System built using Spring Boot, MySQL, Thymeleaf, Docker, and AWS EC2 deployment.

---

# Features

- JWT Authentication
- User Login / Signup
- Bus Management
- Route Management
- Trip Scheduling
- Booking System
- Payment Handling
- Reviews
- Agency & Office Management
- Swagger API Documentation
- Dockerized Deployment
- AWS EC2 Hosting

---

# Tech Stack

## Backend
- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- JWT Authentication

## Frontend
- Thymeleaf
- HTML/CSS

## Database
- MySQL 8

## Deployment
- Docker
- Docker Compose
- AWS EC2

---

# Project Structure

```text
src/
 ├── controller
 ├── service
 ├── repository
 ├── entity
 ├── dto
 ├── config
 ├── exception
 └── security
```

---

# Prerequisites

Install the following before running locally:

- Java 17
- Docker Desktop
- Git

---

# Local Setup (Recommended)

## 1. Clone the repository

```bash
git clone https://github.com/Gautam-A25/bus-ticket-booking-system.git
cd bus-ticket-booking-system
```

---

## 2. Create local environment file

### Windows CMD

```bash
copy .env.example .env
```

### Linux / Mac

```bash
cp .env.example .env
```

---

## 3. Start the application

```bash
docker compose up --build
```

This starts:
- Spring Boot backend
- MySQL database
- Auto database initialization

---

# Application URLs

## Main Application

```text
http://localhost:8080
```

## Swagger UI

```text
http://localhost:8080/swagger-ui.html
```

---

# Reset Database

If containers or database state become inconsistent:

```bash
docker compose down -v
docker compose up --build
```

---

# AWS Deployment

The project is deployed on AWS EC2 using Docker.

## Live URL

```text
http://3.24.110.243:8080
```

---

# Docker Services

## App Container

- Spring Boot application
- Port: 8080

## MySQL Container

- MySQL 8
- Persistent Docker volume storage

---

# Environment Variables

Example `.env`:

```env
MYSQL_ROOT_PASSWORD=your-password
MYSQL_DATABASE=busticketbooking

SPRING_DATASOURCE_URL=jdbc:mysql://mysql:3306/busticketbooking?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your-password

SPRING_JPA_HIBERNATE_DDL_AUTO=update

JWT_SECRET=your-secret
```

---

# Team Workflow

## Pull latest changes

```bash
git pull origin develop
```

## Start project

```bash
docker compose up --build
```

---

# Notes

- Do NOT commit `.env`
- Use `.env.example` for setup reference
- Database seed scripts are automatically initialized through Docker
- Docker volumes preserve MySQL data across restarts

---

# Contributors

- Aayush Gautam
- Mehul Ashra
- Archit Singh
- Anirudh Bansal
- Deeksha S M

---
