import { Navigate } from "react-router-dom";

function ProtectedRoute({ children, allowedRoles }) {
  const token = localStorage.getItem("token");

  // normalize role
  const rawRole = localStorage.getItem("role"); // ADMIN / USER / DELIVERY
  const role = rawRole?.replace("ROLE_", "");

  // ❌ Not logged in
  if (!token) {
    return <Navigate to="/login" replace />;
  }

  // ❌ Role not allowed
  if (allowedRoles && !allowedRoles.includes(role)) {
    return <Navigate to="/login" replace />;
  }

  // ✅ Authorized
  return children;
}

export default ProtectedRoute;