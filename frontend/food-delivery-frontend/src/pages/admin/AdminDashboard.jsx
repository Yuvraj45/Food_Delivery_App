import { useEffect, useState } from "react";
import api from "../../api/api";
import "../../styles/admin.css";

function AdminDashboard() {
  const [orders, setOrders] = useState([]);
  const [revenue, setRevenue] = useState(0);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    Promise.all([
      api.get("/admin/orders"),
      api.get("/admin/orders/revenue")
    ])
      .then(([ordersRes, revenueRes]) => {
        setOrders(ordersRes.data);
        setRevenue(revenueRes.data);
      })
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <h4 className="text-center mt-5">Loading dashboard...</h4>;
  }

  const statusCount = orders.reduce((acc, o) => {
    acc[o.status] = (acc[o.status] || 0) + 1;
    return acc;
  }, {});

  const activeOrders =
    (statusCount.PLACED || 0) +
    (statusCount.CONFIRMED || 0) +
    (statusCount.DELIVERY_REQUESTED || 0) +
    (statusCount.ASSIGNED || 0) +
    (statusCount.OUT_FOR_DELIVERY || 0);

  return (
    <div className="admin-dashboard-page">
      <h1 className="admin-dashboard-title">
        📊 Restaurant Dashboard
      </h1>

      {/* KPI CARDS */}
      <div className="admin-kpi-grid">
        <div className="admin-kpi-card kpi-orange">
          <p>Total Orders</p>
          <h2>{orders.length}</h2>
        </div>

        <div className="admin-kpi-card kpi-green">
          <p>Total Revenue</p>
          <h2>₹ {revenue}</h2>
        </div>

        <div className="admin-kpi-card kpi-blue">
          <p>Active Orders</p>
          <h2>{activeOrders}</h2>
        </div>
      </div>

      {/* STATUS PANEL */}
      <div className="admin-status-panel">
        <h3>📦 Orders by Status</h3>

        {Object.entries(statusCount).map(([status, count]) => (
          <div key={status} className="admin-status-row">
            <span>{status.replaceAll("_", " ")}</span>
            <span className="admin-status-count">{count}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

export default AdminDashboard;