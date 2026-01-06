import { useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./auth.css";

function Register() {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    name: "",
    email: "",
    password: "",
    role: "USER" // backend-friendly default
  });

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const register = async (e) => {
    e.preventDefault();

    try {
      // ✅ SEND CLEAN ROLE (NO ROLE_ PREFIX)
      const payload = {
        ...form,
        role: form.role.replace("ROLE_", "")
      };

      await api.post("/auth/register", payload);

      alert("Account created successfully ✅");
      navigate("/login");

    } catch (err) {
      console.error("REGISTER ERROR 👉", err);

      const message =
        err.response?.data?.message ||
        err.response?.data ||
        "Registration failed";

      alert(message);
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-overlay" />

      {/* LEFT CONTENT */}
      <div className="auth-left">
        <span className="brand-tag">CRAVE</span>
        <h1>
          Join the <br /> Food Revolution.
        </h1>
        <p>
          Create your account to order food, sell dishes,
          or deliver with UVbites.
        </p>
      </div>

      {/* REGISTER CARD */}
      <div className="auth-card">
        <h2>Create Account</h2>
        <p className="subtitle">It takes less than a minute</p>

        <form onSubmit={register}>
          <input
            type="text"
            name="name"
            placeholder="Full name"
            required
            onChange={handleChange}
          />

          <input
            type="email"
            name="email"
            placeholder="Email address"
            required
            onChange={handleChange}
          />

          <input
            type="password"
            name="password"
            placeholder="Password"
            required
            onChange={handleChange}
          />

          {/* ROLE DROPDOWN */}
          <select
            name="role"
            value={form.role}
            onChange={handleChange}
            className="auth-select"
            required
          >
            <option value="USER">Customer</option>
            <option value="ADMIN">Restaurant</option>
            <option value="DELIVERY">Delivery Partner</option>
          </select>

          <button type="submit" className="auth-btn">
            Register
          </button>
        </form>

        <p className="switch-auth">
          Already have an account?
          <span onClick={() => navigate("/login")}> Login</span>
        </p>
      </div>
    </div>
  );
}

export default Register;