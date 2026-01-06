import { Link, useNavigate, useLocation } from "react-router-dom";
import { useEffect, useState } from "react";
import "./navbar.css";

function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();

  const rawRole = localStorage.getItem("role");
  const role = rawRole?.replace("ROLE_", "");

  const [search, setSearch] = useState("");

  // Sync search query
  useEffect(() => {
    const params = new URLSearchParams(location.search);
    setSearch(params.get("q") || "");
  }, [location.search]);

  const logout = () => {
    localStorage.clear();
    navigate("/login");
  };

  const handleSearch = (e) => {
    const value = e.target.value;
    setSearch(value);
    navigate(`/?q=${value}`, { replace: true });
  };

  return (
    <nav className="crave-navbar">
      {/* LEFT */}
      <div className="nav-left">
        <span className="logo" onClick={() => navigate("/")}>
          🍽 UVbites
        </span>
      </div>

      {/* SEARCH – USER ONLY */}
      {role === "USER" && (
        <div className="nav-search">
          <input
            type="text"
            placeholder="Search food..."
            value={search}
            onChange={handleSearch}
          />
        </div>
      )}

      {/* CENTER LINKS */}
      <ul className="nav-center">
        {role === "USER" && (
          <>
            <li><Link to="/">Home</Link></li>
            <li><Link to="/cart">Cart</Link></li>
            <li><Link to="/my-orders">My Orders</Link></li>
          </>
        )}

        {role === "ADMIN" && (
          <>
            <li><Link to="/admin/dashboard">Dashboard</Link></li>
            <li><Link to="/admin/add-food">Add Food</Link></li>
            <li><Link to="/admin/my-foods">My Foods</Link></li>
            <li><Link to="/admin/orders">Orders</Link></li>
          </>
        )}

        {role === "DELIVERY" && (
          <li><Link to="/delivery/orders">My Deliveries</Link></li>
        )}
      </ul>

      {/* RIGHT */}
      {role && (
        <button className="logout-btn" onClick={logout}>
          Logout
        </button>
      )}
    </nav>
  );
}

export default Navbar;