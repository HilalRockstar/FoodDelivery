# Foodie Frontend

Foodie is the React customer interface for the FoodDelivery Spring Boot application. It provides restaurant browsing, menu discovery, cart management, order history, authentication screens, and role-specific dashboard entry points.

## Requirements

- Node.js 18+
- npm
- The Spring Boot backend running on `http://localhost:8080`

## Run Locally

```bash
npm install
npm run dev
```

Open [http://localhost:5173](http://localhost:5173).

Useful commands:

```bash
npm run build
npm run lint
npm run preview
```

## Authentication Flow

The frontend now uses JWT-based authentication for API requests.

### Login

The login page sends a `POST` request to:

- `POST /api/auth/login`

The backend responds with:

- `token`
- `type`
- `email`
- `role`

The frontend stores the token in `localStorage` and uses it for protected API calls via the `Authorization: Bearer <token>` header.

### Role handling

After a successful login, the frontend stores the returned role and uses it to protect routes.

- `USER` -> customer pages
- `ADMIN` -> admin pages
- `DELIVERY_PARTNER` -> delivery pages

### Logout

The navbar sign-out button clears the token and role from local storage and calls the backend logout endpoint.

## Routes

### Public routes

- `/` - Foodie home page
- `/login` - Login page
- `/register` - Registration page

### Protected customer routes

- `/restaurants` - Restaurant directory
- `/restaurants/:id` - Restaurant menu
- `/cart` - Current cart and checkout action
- `/orders` - Customer order history
- `/user/dashboard` - Customer dashboard entry

### Protected admin routes

- `/admin/dashboard`
- `/admin/restaurants`
- `/admin/restaurants/create`
- `/admin/delivery-partners`
- `/admin/delivery-partners/create`
- `/admin/orders`
- `/admin/menu/:restaurantId`
- `/admin/menu/create/:restaurantId`

### Protected delivery routes

- `/delivery/dashboard`
- `/delivery/orders`
- `/delivery/order/:id`

## Backend Integration

The frontend now integrates with these backend endpoints:

- `POST /api/auth/login`
- `POST /register`
- `POST /logout`
- `GET /api/restaurants`
- `GET /api/restaurants/:restaurantId/menu`
- `GET /api/cart`
- `GET /api/orders`
- `GET /api/admin/...`
- `GET /api/delivery/...`

The shared API helper in `src/services/api.js` automatically adds the JWT bearer token when available and redirects to login on `401` or `403` responses.

### Environment variables

Set the backend origin for the frontend using:

```bash
VITE_BACKEND_URL=http://localhost:8080
```

If you want to point the frontend API layer to a different backend base path, also use:

```bash
VITE_API_BASE_URL=http://localhost:8080
```

## Project Structure

```text
src/
  components/   Shared navigation, restaurant, and menu components
  context/      Cart state provider
  hooks/        Cart and restaurant data hooks
  pages/        Customer, auth, and role dashboard screens
  services/     Backend request adapters and JWT-aware API helpers
```

The UI is responsive and designed for desktop and mobile widths. Backend Java and Thymeleaf files are kept outside this frontend project.
