# Foodie Frontend

Foodie is the React customer interface for the FoodDelivery Spring Boot application. It provides restaurant browsing, menu discovery, cart management, order history, authentication screens, and role-specific dashboard entry points.

## Requirements

- Node.js 18+
- npm
- The Spring Boot backend running on `http://localhost:8080` for authentication and server-side role pages

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

## Routes

### Public and customer routes

- `/` - Foodie home page
- `/restaurants` - Restaurant directory
- `/restaurants/:id` - Restaurant menu
- `/cart` - Current cart and checkout action
- `/orders` - Customer order history
- `/login` - Spring Security login form
- `/register` - Customer registration form

### Role entry routes

- `/user/dashboard` - Customer dashboard entry
- `/admin/dashboard` - Admin dashboard entry
- `/delivery/dashboard` - Delivery partner dashboard entry

The backend remains the source of truth for authorization. After login, Spring Security redirects users based on their role:

- `ROLE_USER` -> `/user/dashboard`
- `ROLE_ADMIN` -> `/admin/dashboard`
- `ROLE_DELIVERY_PARTNER` -> `/delivery/dashboard`

## Backend Integration

Authentication forms submit directly to the Spring Boot server:

- `POST /login`
- `POST /register`
- `POST /logout`

The API adapter in `src/services/api.js` follows the existing backend paths for restaurants, menus, carts, and orders. The current backend controllers return Thymeleaf HTML rather than JSON, so the frontend uses local fallback data when an endpoint does not return JSON. This keeps the React UI usable while preserving the existing backend implementation.

To use a different backend host, set:

```bash
VITE_BACKEND_URL=http://localhost:8080
```

## Project Structure

```text
src/
  components/   Shared navigation, restaurant, and menu components
  context/      Cart state provider
  hooks/        Cart and restaurant data hooks
  pages/        Customer, auth, and role dashboard screens
  services/     Backend request adapters and fallback data
```

The UI is responsive and designed for desktop and mobile widths. Backend Java and Thymeleaf files are kept outside this frontend project.
