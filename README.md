# Franchise Service API

Reactive REST API for franchise, branch, and product inventory management built with Spring WebFlux and MongoDB Atlas.

## Overview

This project was developed as a technical assessment.

The API allows managing:

* Franchises
* Branches
* Products
* Stock updates
* Top stock queries per branch

It follows a reactive programming model using Spring WebFlux and stores data in MongoDB Atlas.

---

## Technologies Used

* Java 21
* Spring Boot
* Spring WebFlux
* Reactive MongoDB
* MongoDB Atlas
* Lombok
* Maven
* Docker

---

## Features

### Franchise Management

* Create franchise
* Update franchise name

### Branch Management

* Add branch to franchise
* Update branch name

### Product Management

* Add product to branch
* Delete product
* Update product name
* Update product stock

### Inventory Query

* Retrieve highest stock product for each branch of a franchise

---

## Project Structure

src/main/java/com/accenture/franchise

* controller
* service
* repository
* model
* dto

---

## Running Locally

### Clone repository

```bash
git clone https://github.com/Joelru/franquicias-backend-accenture.git
cd franchise-service
```

### Configure MongoDB Atlas

Update:

```properties
src/main/resources/application.properties
```

Add your MongoDB connection string:

```properties
spring.data.mongodb.uri=your_connection_string
```

### Run application

```bash
./mvnw spring-boot:run
```

Application runs at:

```plaintext
http://localhost:8080
```

---

## Running with Docker

### Build image

```bash
docker build -t franchise-service .
```

### Run container

```bash
docker run -p 8080:8080 franchise-service
```

---

## API Endpoints

### Franchise

**Create Franchise**

POST `/api/franchises`

---

**Update Franchise Name**

PUT `/api/franchises/{franchiseId}/name`

---

### Branch

**Add Branch**

POST `/api/franchises/{franchiseId}/branches`

---

**Update Branch Name**

PUT `/api/franchises/{franchiseId}/branches/{branchId}/name`

---

### Product

**Add Product**

POST `/api/franchises/{franchiseId}/branches/{branchId}/products`

---

**Delete Product**

DELETE `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}`

---

**Update Product Name**

PUT `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/name`

---

**Update Product Stock**

PUT `/api/franchises/{franchiseId}/branches/{branchId}/products/{productId}/stock`

---

### Query

**Top Stock Products by Branch**

GET `/api/franchises/{franchiseId}/top-stock`

---

## Additional Improvements Implemented

* Duplicate validation for franchises, branches, and products
* HTTP status handling with ResponseStatusException
* Reusable search methods for branches and products
* Docker containerization
* Reactive non-blocking architecture

---

## Author

Technical assessment developed by Juan Navarro
