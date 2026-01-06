import { useEffect, useState } from "react";
import api from "../../api/api";
import "../../styles/admin.css";

function AdminOrders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadOrders = () => {
    api.get("/admin/orders")
      .then(res => setOrders(res.data))
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadOrders();
  }, []);

  const requestDelivery = (orderId) => {
    api.put(`/admin/orders/${orderId}/request-delivery`)
      .then(loadOrders)
      .catch(() => alert("Failed to request delivery"));
  };

  if (loading) {
    return <h4 className="text-center mt-5">Loading orders...</h4>;
  }

  if (orders.length === 0) {
    return (
      <div className="empty-state">
        <h3>No orders yet</h3>
        <p>Orders will appear here once customers place them</p>
      </div>
    );
  }

  return (
    <div className="admin-page">
      <h2 className="admin-title">📦 Orders</h2>

      <div className="orders-grid">
        {orders.map(order => (
          <div key={order.id} className="order-card">
            <div className="order-header">
              <span><b>Order #{order.id}</b></span>
              <span className={`status-pill ${order.status}`}>
                {order.status}
              </span>
            </div>

            <div className="order-body">
              <p><b>Customer:</b> {order.customer?.name}</p>
              <p><b>Total:</b> ₹ {order.totalAmount}</p>
            </div>

            {order.status === "PLACED" && (
              <button
                className="btn-primary-admin mt-3"
                onClick={() => requestDelivery(order.id)}
              >
                Request Delivery
              </button>
            )}

            {order.status === "DELIVERY_REQUESTED" && (
              <p className="info-text mt-3">
                🚚 Waiting for delivery partner
              </p>
            )}
          </div>
        ))}
      </div>
    </div>
  );
}

export default AdminOrders;