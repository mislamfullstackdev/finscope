# User API Design

## 1. Overview

### Module Name

User Management API

### Purpose

The User API provides user account management functionality for FinScope.

The module handles:

* User registration
* User authentication
* User profile retrieval
* User profile update
* Password management

---

# 2. API Base Information

## Base URL

```
/api/v1
```

## Authentication

Protected APIs require JWT authentication.

Header:

```
Authorization: Bearer {access_token}
```

---

# 3. API List

| API             | Method | Authentication | Purpose                           |
| --------------- | ------ | -------------- | --------------------------------- |
| Register User   | POST   | Not Required   | Create new account                |
| Login User      | POST   | Not Required   | Authenticate user                 |
| Get Profile     | GET    | Required       | Retrieve current user information |
| Update Profile  | PUT    | Required       | Update user information           |
| Change Password | PATCH  | Required       | Change user password              |

---

# 4. Register User

## Endpoint

```
POST /api/v1/users/register
```

## Description

Creates a new FinScope user account.

## Request Body

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

## Validation Rules

| Field    | Rule                                 |
| -------- | ------------------------------------ |
| email    | Required, valid email format, unique |
| password | Required, minimum 8 characters       |

## Success Response

HTTP Status:

```
201 Created
```

Response:

```json
{
  "id": 1,
  "email": "user@example.com",
  "message": "User created successfully"
}
```

## Error Response

Email already exists:

HTTP Status:

```
409 Conflict
```

```json
{
  "message": "Email already registered"
}
```

---

# 5. Login User

## Endpoint

```
POST /api/v1/users/login
```

## Description

Authenticates a user and returns JWT tokens.

## Request Body

```json
{
  "email": "user@example.com",
  "password": "password123"
}
```

## Success Response

HTTP Status:

```
200 OK
```

```json
{
  "accessToken": "jwt_access_token",
  "refreshToken": "jwt_refresh_token"
}
```

## Error Response

Invalid credentials:

HTTP Status:

```
401 Unauthorized
```

```json
{
  "message": "Invalid email or password"
}
```

---

# 6. Get User Profile

## Endpoint

```
GET /api/v1/users/profile
```

## Description

Returns the authenticated user's profile information.

## Authentication

Required.

## Success Response

HTTP Status:

```
200 OK
```

```json
{
  "id": 1,
  "email": "user@example.com",
  "createdAt": "2026-07-13T15:00:00"
}
```

---

# 7. Update User Profile

## Endpoint

```
PUT /api/v1/users/profile
```

## Description

Updates user profile information.

## Authentication

Required.

## Request Body

```json
{
  "email": "newemail@example.com"
}
```

## Success Response

HTTP Status:

```
200 OK
```

```json
{
  "message": "Profile updated successfully"
}
```

---

# 8. Change Password

## Endpoint

```
PATCH /api/v1/users/password
```

## Description

Changes the current user's password.

## Authentication

Required.

## Request Body

```json
{
  "currentPassword": "oldPassword123",
  "newPassword": "newPassword123"
}
```

## Success Response

HTTP Status:

```
200 OK
```

```json
{
  "message": "Password changed successfully"
}
```

---

# 9. Common Error Response

All APIs use a common error format.

Example:

```json
{
  "timestamp": "2026-07-13T15:30:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed"
}
```

---

# 10. Future Improvements

Possible future APIs:

* Email verification
* Password reset by email
* Social login
* Two-factor authentication
* Account deletion
* User preferences
