# User Table Design

## 1. Overview

### Table Name

`users`

### Purpose

The `users` table stores FinScope user account information.

This table manages:

* User identity
* Authentication information
* Account status
* Audit timestamps

---

# 2. Table Structure

## Table: users

| Column     | Data Type    | Constraints      | Description                |
| ---------- | ------------ | ---------------- | -------------------------- |
| id         | BIGSERIAL    | Primary Key      | Unique user identifier     |
| email      | VARCHAR(255) | NOT NULL, UNIQUE | User login email address   |
| password   | VARCHAR(255) | NOT NULL         | Encrypted user password    |
| created_at | TIMESTAMP    | NOT NULL         | Account creation timestamp |
| updated_at | TIMESTAMP    | NOT NULL         | Last update timestamp      |

---

# 3. SQL Definition

```sql
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,

    email VARCHAR(255) NOT NULL UNIQUE,

    password VARCHAR(255) NOT NULL,

    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
```

---

# 4. Column Details

## id

Purpose:

* Primary identifier for users.

Design decision:

* Use `BIGSERIAL` instead of `SERIAL`.
* Allows future growth for a large number of users.

Example:

```
1
2
3
```

---

## email

Purpose:

* User login identifier.

Rules:

* Must be unique.
* Cannot be empty.

Example:

```
user@example.com
```

---

## password

Purpose:

* Stores encrypted password.

Important:

* Never store plain text passwords.
* Use BCrypt hashing before saving.

Example:

```
$2a$10$EixZaYVK1fsbw1ZfbX3OXe...
```

---

## created_at

Purpose:

Stores when the user account was created.

Example:

```
2026-07-13 17:00:00
```

Used for:

* Account history
* Reporting
* Auditing

---

## updated_at

Purpose:

Stores the last modification time.

Example:

```
2026-07-14 10:30:00
```

Updated when:

* Email changes
* Password changes
* Profile information changes

---

# 5. Index Design

## Primary Key Index

Automatically created:

```
users_pkey
```

Column:

```
id
```

---

## Unique Index

Automatically created:

```
users_email_key
```

Column:

```
email
```

Purpose:

* Prevent duplicate accounts
* Improve email lookup performance

---

# 6. Future Considerations

Possible future columns:

| Column        | Purpose              |
| ------------- | -------------------- |
| username      | Display name         |
| first_name    | User first name      |
| last_name     | User last name       |
| role          | USER / ADMIN         |
| status        | ACTIVE / INACTIVE    |
| last_login_at | Track login activity |

These should be added through new Flyway migrations:

Example:

```
V2__add_user_status.sql
```

---

# 7. Design Notes

## Why both created_at and updated_at?

Every important business entity should have audit timestamps.

`created_at` answers:

> When was this record created?

`updated_at` answers:

> When was this record last changed?

This helps with:

* Debugging
* Data auditing
* Synchronization
* Future analytics
* System maintenance
