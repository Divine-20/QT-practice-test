# QT-practice-test
Blog Post Management API
This is a RESTful API for managing blog posts and comments. It allows users to register, log in, and perform CRUD operations on posts and comments. Authentication is required for creating, updating, and deleting posts and comments.

Features
User Authentication: Register and login functionalities to secure user access.
Post Management: Create, view, update, and delete posts.
Comment Management: Add, view, update, and delete comments on posts.
Secure Access: Only authenticated users can manage posts and comments.
API Endpoints
Authentication Controller
GET /api/auth/profile
Retrieve the profile information of the authenticated user.

POST /api/auth/register
Register a new user account.
Request Body:

firstName:string
lastName:string
userName:string
dateOfBirth:string
nationalId:string
gender:string
email: string
password: string
POST /api/auth/login
Login an existing user.
Request Body:

email: string
password: string
Post Controller
GET /api/posts/all
Retrieve all posts.

GET /api/posts/id/{postId}
Retrieve a specific post by its ID.

GET /api/posts/comments/{postId}
Retrieve all comments for a specific post by its ID.

POST /api/posts/create
Create a new post. Requires authentication.
Request Body:

title: string
content: string
PUT /api/posts/update/{postId}
Update an existing post by its ID. Requires authentication.
Request Body:

title: string
content: string
DELETE /api/posts/delete/{postId}
Delete a post by its ID. Requires authentication.

Comment Controller
GET /api/comments/all
Retrieve all comments.

GET /api/comments/id/{commentId}
Retrieve a specific comment by its ID.

POST /api/comments/create/{post_id}
Add a comment to a specific post. Requires authentication.
Request Body:

content: string
PUT /api/comments/update/{commentId}
Update a comment by its ID. Requires authentication.
Request Body:

content: string
DELETE /api/comments/delete/{commentId}
Delete a comment by its ID. Requires authentication.

Getting Started
Prerequisites
Node.js and npm installed on your machine.
A compatible database (e.g., PostgreSQL or MongoDB).
Installation
Clone the repository:

bash
Copy code
git clone https://github.com/Divine-20/QT-practice-test.git
cd blog-post-management
Install dependencies:

bash
Copy code
pnpm run dev
Access the API at http://localhost:3000.

access swagger on http://localhost:8000/swagger-ui/index.html#/

Usage
Register a new user using the /api/auth/register endpoint.
Login using the /api/auth/login endpoint to receive an authentication token.
Use the token in the Authorization header for any request that requires authentication.
Manage posts and comments using the respective endpoints.
Contributing
Contributions are welcome! Please fork the repository and submit a pull request for any improvements or bug fixes.

