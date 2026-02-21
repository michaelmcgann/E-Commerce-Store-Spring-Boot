
## E-commerce store built using Java 21 & Spring Boot

Built as a RESTful back-end system with modularity and separation of concerns in mind.

### Technologies used:
- Java 21
- Spring Boot
- Maven
- Postgres & H2
- Spring Security
- SwaggerUI/OpenAPI
- AWS Deployment

### Backend Domain Decomposition

The backend is organised around a set of database-backed domain modules, each responsible for a specific part of the
e-commerce workflow. This keeps the system easier to reason about, extend, and test as features grow.

### Identity & Access

Tables: users, roles, user_role

This module handles authentication/authorisation concerns and user role assignment (for example, customer/admin-style access).
It defines who the user is and what they are allowed to do in the system.

### User Profile & Addressing

Tables: addresses, user_address

This module stores address information and links addresses to users.
It supports the user/account side of checkout and order delivery while keeping address data separate from core identity data.

### Product Catalogue

Tables: categories, products

This module represents the storefront catalogue: product categories and the products within them.
It is responsible for the core product data used across browsing, cart, and order flows (e.g., name, price, stock quantity, category relationship).

### Shopping Cart

Tables: carts, cart_items

This module models a user’s active shopping basket.
It stores the cart itself and the selected products/items inside it, including quantities and pricing-related values at cart level.

### Orders & Checkout

Tables: orders, order_items, payments

This module captures completed purchase intent and payment linkage.
It stores order-level information (customer/order metadata), the individual items included in the order, and payment 
method/payment references used during checkout.

