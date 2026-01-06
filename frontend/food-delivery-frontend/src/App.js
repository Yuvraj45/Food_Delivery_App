import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./auth/Login";
import Register from "./auth/Register";
import ProtectedRoute from "./auth/ProtectedRoute";

import Navbar from "./layouts/Navbar";

import UserHome from "./pages/user/UserHome";
import Cart from "./pages/user/Cart";
import MyOrders from "./pages/user/MyOrders";
import Checkout from "./pages/user/Checkout";
import FakePayment from "./pages/user/FakePayment";

import AddFood from "./pages/admin/AddFood";
import MyFoods from "./pages/admin/MyFoods";
import AdminOrders from "./pages/admin/AdminOrders";
import AdminDashboard from "./pages/admin/AdminDashboard";

import DeliveryOrders from "./pages/delivery/DeliveryOrders";

import "./styles/admin.css";
import "./pages/user/userHome.css";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        {/* ================= PUBLIC ================= */}
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />

        {/* ================= USER ================= */}
        <Route
          path="/"
          element={
            <ProtectedRoute allowedRoles={["USER"]}>
              <UserHome />
            </ProtectedRoute>
          }
        />

        <Route
          path="/cart"
          element={
            <ProtectedRoute allowedRoles={["USER"]}>
              <Cart />
            </ProtectedRoute>
          }
        />

        <Route
          path="/checkout"
          element={
            <ProtectedRoute allowedRoles={["USER"]}>
              <Checkout />
            </ProtectedRoute>
          }
        />

        <Route
          path="/my-orders"
          element={
            <ProtectedRoute allowedRoles={["USER"]}>
              <MyOrders />
            </ProtectedRoute>
          }
        />

        <Route
          path="/payment"
          element={
            <ProtectedRoute allowedRoles={["USER"]}>
              <FakePayment />
            </ProtectedRoute>
          }
        />

        {/* ================= ADMIN ================= */}
        <Route
          path="/admin/dashboard"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <AdminDashboard />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/add-food"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <AddFood />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/my-foods"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <MyFoods />
            </ProtectedRoute>
          }
        />

        <Route
          path="/admin/orders"
          element={
            <ProtectedRoute allowedRoles={["ADMIN"]}>
              <AdminOrders />
            </ProtectedRoute>
          }
        />

        {/* ================= DELIVERY ================= */}
        <Route
          path="/delivery/orders"
          element={
            <ProtectedRoute allowedRoles={["DELIVERY"]}>
              <DeliveryOrders />
            </ProtectedRoute>
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;