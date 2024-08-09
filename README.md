Library Management System Project
Overview
I have successfully developed a comprehensive Library Management System using Spring Boot. This API allows librarians to efficiently manage books, patrons, and borrowing records. The system is designed with robust RESTful endpoints and includes essential features to ensure smooth library operations.

Core Features
Entities
Book

Attributes: Includes ID, title, author, publication year, ISBN, and other relevant details.
Patron

Attributes: Contains ID, name, contact information, and other personal details.
Borrowing Record

Tracks: The relationship between books and patrons, including borrowing and return dates.
API Endpoints
Book Management

GET /api/books - Retrieve a list of all books.
GET /api/books/{id} - Get details of a specific book by ID.
POST /api/books - Add a new book to the library.
PUT /api/books/{id} - Update an existing book's information.
DELETE /api/books/{id} - Remove a book from the library.
Patron Management

GET /api/patrons - Retrieve a list of all patrons.
GET /api/patrons/{id} - Get details of a specific patron by ID.
POST /api/patrons - Add a new patron to the system.
PUT /api/patrons/{id} - Update an existing patron's information.
DELETE /api/patrons/{id} - Remove a patron from the system.
Borrowing Records

POST /api/borrow/{bookId}/patron/{patronId} - Allow a patron to borrow a book.
PUT /api/return/{bookId}/patron/{patronId} - Record the return of a borrowed book by a patron.
Data Storage
Database: Utilizes H2, MySQL, or PostgreSQL for persistent storage of books, patrons, and borrowing records.
Relationships: Properly configured relationships, such as one-to-many between books and borrowing records.
Validation and Error Handling
Input Validation: Ensures all API requests are validated for required fields and data formats.
Exception Handling: Implements graceful handling of exceptions, with appropriate HTTP status codes and error messages.
Security
Authentication: Implements JWT-based authorization to secure API endpoints, ensuring only authorized users can access certain features.
Transaction Management
Transactions: Uses Spring's @Transactional annotation to manage transactions, ensuring data integrity during critical operations.
Testing
Unit Tests: Includes comprehensive unit tests using JUnit, Mockito, and SpringBootTest to validate the functionality of API endpoints.
Documentation
Documentation: Provides clear instructions on running the application, interacting with API endpoints, and using JWT authentication.
Conclusion
This project not only meets but exceeds the core requirements by integrating essential features and implementing robust error handling, security, and transaction management. The system is fully tested and documented, ensuring reliability and ease of use.

The project repository is available for review, and I am confident in the solution’s capability to efficiently manage library operations.
=========================================================================================
===============================================================================================================




Documentation
Book Management API
The BookController provides RESTful API endpoints for managing books in the Library Management System. Below are the details on how to use each endpoint.

Get All Books
Endpoint: GET /books
Description: Retrieves a list of all books available in the library.
Response:
Status Code: 200 OK
Body: A JSON array of BookDto objects representing the books.
Example:
json
نسخ الكود
[
  {
    "id": 1,
    "title": "The Catcher in the Rye",
    "author": "J.D. Salinger",
    "isbn": "9780316769488"
  },
  {
    "id": 2,
    "title": "To Kill a Mockingbird",
    "author": "Harper Lee",
    "isbn": "9780061120084"
  }
]
Get Book by ID
Endpoint: GET /books/{id}
Description: Retrieves the details of a book by its ID.
Path Parameter:
id: The ID of the book to retrieve.
Response:
Status Code: 200 OK (if the book is found) or 404 Not Found (if the book is not found).
Body: A BookDto object representing the book details.
Example:
json
نسخ الكود
{
  "id": 1,
  "title": "The Catcher in the Rye",
  "author": "J.D. Salinger",
  "isbn": "9780316769488"
}
Create a New Book
Endpoint: POST /books
Description: Creates a new book in the library.
Request Body: A JSON object representing the book details. All fields are required.
Example:
json
نسخ الكود
{
  "title": "1984",
  "author": "George Orwell",
  "isbn": "9780451524935"
}
Response:
Status Code: 201 Created
Body: The BookDto object representing the newly created book.
Example:
json
نسخ الكود
{
  "id": 3,
  "title": "1984",
  "author": "George Orwell",
  "isbn": "9780451524935"
}
Error Handling:
409 Conflict: If a book with the same title and ISBN already exists.
json
نسخ الكود
{
  "status": 409,
  "message": "Book with title '1984' and ISBN '9780451524935' already exists."
}
Update a Book
Endpoint: PUT /books/{id}
Description: Updates the details of an existing book by its ID.
Path Parameter:
id: The ID of the book to update.
Request Body: A JSON object representing the updated book details. All fields are required.
Example:
json
نسخ الكود
{
  "title": "Animal Farm",
  "author": "George Orwell",
  "isbn": "9780451526342"
}
Response:
Status Code: 200 OK
Body: The updated BookDto object.
Example:
json
نسخ الكود
{
  "id": 3,
  "title": "Animal Farm",
  "author": "George Orwell",
  "isbn": "9780451526342"
}
Error Handling:
404 Not Found: If the book with the specified ID does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Book with ID 3 does not exist."
}
Delete a Book
Endpoint: DELETE /books/{id}
Description: Deletes a book by its ID.
Path Parameter:
id: The ID of the book to delete.
Response:
Status Code: 204 No Content (if the deletion is successful) or 404 Not Found (if the book is not found).
Error Handling:
404 Not Found: If the book with the specified ID does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Book with ID 3 does not exist."
}
This documentation should be added to the appropriate section in your README file, and it explains how to interact with the BookController                 





-------------------------------------------------------------------------------------------------------------------


API Documentation
Borrowing Record Management API
The BorrowingRecordController provides RESTful API endpoints for managing borrowing records in the Library Management System. Below are the details on how to use each endpoint.

Get Borrowing Record by Book ID and Patron ID
Endpoint: GET /borrowing/{bookId}/{patronId}
Description: Retrieves the borrowing record for a specific book and patron.
Path Parameters:
bookId: The ID of the book.
patronId: The ID of the patron.
Response:
Status Code: 200 OK (if the borrowing record is found) or 404 Not Found (if the borrowing record is not found).
Body: A BorrowRecordDto object representing the borrowing record details.
Example:
json
نسخ الكود
{
  "id": 1,
  "bookId": 3,
  "patronId": 2,
  "borrowDate": "2024-08-09",
  "dueDate": "2024-09-09",
  "returnDate": null
}
Error Handling:
404 Not Found: If the borrowing record for the specified book and patron does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Borrowing record not found for book ID 3 and patron ID 2."
}
Create a New Borrowing Record
Endpoint: POST /borrowing
Description: Creates a new borrowing record for a book and patron.
Request Body: A JSON object representing the borrowing record details. All fields are required.
Example:
json
نسخ الكود
{
  "bookId": 3,
  "patronId": 2,
  "borrowDate": "2024-08-09",
  "dueDate": "2024-09-09"
}
Response:
Status Code: 201 Created
Body: The BorrowRecordDto object representing the newly created borrowing record.
Example:
json
نسخ الكود
{
  "id": 1,
  "bookId": 3,
  "patronId": 2,
  "borrowDate": "2024-08-09",
  "dueDate": "2024-09-09",
  "returnDate": null
}
Error Handling:
500 Internal Server Error: If an error occurs while creating the borrowing record.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred while creating the borrowing record: [error details]"
}
This documentation provides clear instructions on how to use the BorrowingRecordController API endpoints, including details about the expected input and output formats, status codes, and error handling. Be sure to include this in the appropriate section of your README file to guide users in interacting with your API.






----------------------------------------------------------------------------------------------------------------------------------























API Documentation
Patron Management API
The PatronController provides RESTful API endpoints for managing patrons in the Library Management System. Below are the details on how to use each endpoint.

Get All Patrons
Endpoint: GET /patrons
Description: Retrieves a list of all patrons in the library.
Response:
Status Code: 200 OK
Body: A JSON array of PatronDto objects representing the patrons.
Example:
json
نسخ الكود
[
  {
    "id": 1,
    "name": "John Doe",
    "email": "john.doe@example.com",
    "membershipDate": "2023-01-15"
  },
  {
    "id": 2,
    "name": "Jane Smith",
    "email": "jane.smith@example.com",
    "membershipDate": "2023-03-22"
  }
]
Get Patron by ID
Endpoint: GET /patrons/{id}
Description: Retrieves the details of a patron by their ID.
Path Parameter:
id: The ID of the patron.
Response:
Status Code: 200 OK (if the patron is found) or 404 Not Found (if the patron is not found).
Body: A PatronDto object representing the patron details.
Example:
json
نسخ الكود
{
  "id": 1,
  "name": "John Doe",
  "email": "john.doe@example.com",
  "membershipDate": "2023-01-15"
}
Error Handling:
404 Not Found: If the patron with the specified ID does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Patron not found with ID: 1"
}
Create a New Patron
Endpoint: POST /patrons
Description: Creates a new patron in the library.
Request Body: A JSON object representing the patron details. All fields are required.
Example:
json
نسخ الكود
{
  "name": "Alice Johnson",
  "email": "alice.johnson@example.com",
  "membershipDate": "2024-08-09"
}
Response:
Status Code: 201 Created
Body: The PatronDto object representing the newly created patron.
Example:
json
نسخ الكود
{
  "id": 3,
  "name": "Alice Johnson",
  "email": "alice.johnson@example.com",
  "membershipDate": "2024-08-09"
}
Error Handling:
409 Conflict: If a patron with the same name already exists.
json
نسخ الكود
{
  "status": 409,
  "message": "Patron with name 'Alice Johnson' already exists."
}
500 Internal Server Error: If an error occurs while creating the patron.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred while creating the patron: [error details]"
}
Update a Patron
Endpoint: PUT /patrons/{id}
Description: Updates the details of an existing patron by their ID.
Path Parameter:
id: The ID of the patron to update.
Request Body: A JSON object representing the updated patron details. All fields are required.
Example:
json
نسخ الكود
{
  "name": "Alice Johnson",
  "email": "alice.johnson@newdomain.com",
  "membershipDate": "2024-08-09"
}
Response:
Status Code: 200 OK
Body: The updated PatronDto object.
Example:
json
نسخ الكود
{
  "id": 3,
  "name": "Alice Johnson",
  "email": "alice.johnson@newdomain.com",
  "membershipDate": "2024-08-09"
}
Error Handling:
404 Not Found: If the patron with the specified ID does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Patron with ID 3 does not exist."
}
500 Internal Server Error: If an error occurs while updating the patron.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred while updating the patron: [error details]"
}
Delete a Patron
Endpoint: DELETE /patrons/{id}
Description: Deletes a patron by their ID.
Path Parameter:
id: The ID of the patron to delete.
Response:
Status Code: 200 OK (if the deletion is successful) or 404 Not Found (if the patron is not found).
Body: A message indicating successful deletion.
Example:
json
نسخ الكود
{
  "status": 200,
  "message": "Patron with ID 3 has been successfully deleted."
}
Error Handling:
404 Not Found: If the patron with the specified ID does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Patron with ID 3 does not exist."
}
500 Internal Server Error: If an error occurs while deleting the patron.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred while deleting the patron: [error details]"
}
This documentation provides details on how to interact with the PatronController API endpoints, including expected request 
and response formats, status codes, and error handling scenarios. Make sure to include this in the README file of your project to help users understand how to use the API.
--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------














API Documentation
Authentication API
The AuthenticationController provides endpoints for user registration, login, and token refreshing. Below are the details on how to use each endpoint.

Register a New User
Endpoint: POST /register
Description: Registers a new user in the system.
Request Body: A JSON object representing the user details.
Example:
json
نسخ الكود
{
  "username": "john_doe",
  "password": "securepassword",
  "email": "john.doe@example.com"
}
Response:
Status Code: 200 OK
Body: An AuthenticationResponse object containing the authentication token and other details.
Example:
json
نسخ الكود
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "expiresIn": 3600
}
Error Handling:
500 Internal Server Error: If an error occurs during registration.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred during registration: [error details]"
}
Log In
Endpoint: POST /login
Description: Authenticates a user and returns an authentication token.
Request Body: A JSON object representing the user credentials.
Example:
json
نسخ الكود
{
  "username": "john_doe",
  "password": "securepassword"
}
Response:
Status Code: 200 OK
Body: An AuthenticationResponse object containing the authentication token and other details.
Example:
json
نسخ الكود
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "expiresIn": 3600
}
Refresh Token
Endpoint: POST /refresh_token
Description: Refreshes an authentication token using a valid refresh token.
Request: The request should include the current token in the Authorization header.
Response:
Status Code: 200 OK
Body: An AuthenticationResponse object containing the new authentication token and other details.
Example:
json
نسخ الكود
{
  "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c",
  "expiresIn": 3600
}
This documentation provides clear instructions on how to use the authentication endpoints, 
including details about the expected request and response formats, status codes, and error
handling. Make sure to include this in the README file of your project to help users understand how to interact with the authentication API.
=======================================================================================================================================================
API Documentation
Role Management API
The RoleController provides endpoints for creating and retrieving roles in the Library Management System. Below are the details on how to use each endpoint.

Create a New Role
Endpoint: POST /roles
Description: Creates a new role in the system.
Request Body: A JSON object representing the role details.
Example:
json
نسخ الكود
{
  "name": "ROLE_ADMIN"
}
Response:
Status Code: 201 Created
Body: A Role object representing the newly created role.
Example:
json
نسخ الكود
{
  "id": 1,
  "name": "ROLE_ADMIN"
}
Error Handling:
400 Bad Request: If the role name is missing or empty.
json
نسخ الكود
{
  "status": 400,
  "message": "Invalid role creation request."
}
500 Internal Server Error: If an error occurs during role creation.
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred while creating the role: [error details]"
}
Get Role by Name
Endpoint: GET /roles/{name}
Description: Retrieves a role by its name.
Path Parameter:
name: The name of the role.
Response:
Status Code: 200 OK (if the role is found) or 404 Not Found (if the role is not found).
Body: A Role object representing the role details.
Example:
json
نسخ الكود
{
  "id": 1,
  "name": "ROLE_ADMIN"
}
Error Handling:
404 Not Found: If the role with the specified name does not exist.
json
نسخ الكود
{
  "status": 404,
  "message": "Role not found with name: ROLE_ADMIN"
}
Global Exception Handling
Exception Handler: Exception.class
Description: Handles any exceptions thrown by the controller and returns a generic error message.
Response:
Status Code: 500 Internal Server Error
Body: A message indicating an error occurred.
Example:
json
نسخ الكود
{
  "status": 500,
  "message": "An error occurred: [error details]"
}
This documentation provides detailed information 


on how to use the role management endpoints, including 
request and response formats, status codes, and error handling. Make sure to
include this in the README file of your project to help users understand how to interact with the role management API.
==================================================================================================================






























in your Spring Boot application. The examples provided should help developers or API consumers understand the expected inputs and outputs when working with the API.
