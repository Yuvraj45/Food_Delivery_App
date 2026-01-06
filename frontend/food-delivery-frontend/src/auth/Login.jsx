import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../api/api";
import "./auth.css";

function Login() {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();

  const submit = async (e) => {
    e.preventDefault();
    try {
      const res = await api.post("/auth/login", { email, password });

      const role = res.data.role.startsWith("ROLE_")
        ? res.data.role
        : `ROLE_${res.data.role}`;

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("role", role);

      if (role === "ROLE_ADMIN") navigate("/admin/dashboard");
      else if (role === "ROLE_DELIVERY") navigate("/delivery/orders");
      else navigate("/");

    } catch {
      alert("Invalid credentials");
    }
  };

  return (
    <div className="auth-page">
      <div className="auth-overlay" />

      {/* LEFT CONTENT */}
      <div className="auth-left">
        {/*<span className="brand-tag">UVbites</span>*/}
        <h1>
        You bite.<br />
        We bite.<br />
         <span className="brand-tag">UVbites.</span>
        </h1>
        <p>
        From kitchen to doorstep — fresh food, fast delivery, one app.
        </p>
      </div>

      {/* LOGIN CARD */}
      <form className="auth-card" onSubmit={submit}>
        <h2>Login</h2>
        <p className="subtitle">Enter your credentials to continue</p>

        <input
          type="email"
          placeholder="Email address"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          required
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />

        <button type="submit" className="auth-btn">
          Login
        </button>

        <p className="switch-auth">
          New to UVbites ? <Link to="/register">Create an account</Link>
        </p>
      </form>
    </div>
  );
}

export default Login;
