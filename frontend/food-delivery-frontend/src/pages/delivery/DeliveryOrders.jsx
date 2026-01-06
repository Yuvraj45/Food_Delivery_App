import { useEffect, useState } from "react";
import api from "../../api/api";
import "../../styles/admin.css";

function DeliveryOrders() {
  const [requests, setRequests] = useState([]);
  const [myOrders, setMyOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  const loadData = () => {
    Promise.all([
      api.get("/orders/delivery/requests"),
      api.get("/orders/delivery/my")
    ])
      .then(([reqRes, myRes]) => {
        setRequests(reqRes.data);
        setMyOrders(myRes.data);
      })
      .finally(() => setLoading(false));
  };

  useEffect(() => {
    loadData();
  }, []);

  const acceptOrder = (orderId) => {
    api.put(`/orders/delivery/${orderId}/accept`)
      .then(loadData)
      .catch(() => alert("Order already taken"));
  };

  const updateStatus = (orderId, status) => {
    api.put(`/orders/delivery/${orderId}/status`, null, {
      params: { status }
    })
      .then(loadData)
      .catch(() => alert("Failed to update status"));
  };

  if (loading) {
    return <h4 className="text-center mt-5">Loading deliveries...</h4>;
  }

  return (
    <div className="admin-page">
      <h1 className="admin-title">🚚 Delivery Dashboard</h1>

      {/* ================= AVAILABLE REQUESTS ================= */}
      <h3 className="mt-4 mb-3">📦 Available Delivery Requests</h3>

      {requests.length === 0 && (
        <p className="text-muted">No delivery requests available</p>
      )}

      <div className="orders-grid">
        {requests.map(order => (
          <div key={order.id} className="order-card">
            <div className="order-header">
              <span><b>Order #{order.id}</b></span>
              <span className="status-pill DELIVERY_REQUESTED">
                DELIVERY REQUESTED
              </span>
            </div>

            <div className="order-body">
              <p><b>Customer:</b> {order.customer?.name}</p>
              <p><b>Total:</b> ₹ {order.totalAmount}</p>
            </div>

            <button
              className="btn-primary-admin mt-3"
              onClick={() => acceptOrder(order.id)}
            >
              Accept Delivery
            </button>
          </div>
        ))}
      </div>

      {/* ================= MY DELIVERIES ================= */}
      <h3 className="mt-5 mb-3">🛵 My Active Deliveries</h3>

      {myOrders.length === 0 && (
        <p className="text-muted">No active deliveries</p>
      )}

      <div className="orders-grid">
        {myOrders.map(order => (
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

            {/* STATUS ACTIONS */}
            {order.status === "ASSIGNED" && (
              <button
                className="btn-primary-admin mt-3"
                onClick={() => updateStatus(order.id, "PICKED_UP")}
              >
                Picked Up
              </button>
            )}

            {order.status === "PICKED_UP" && (
              <button
                className="btn-primary-admin mt-3"
                onClick={() => updateStatus(order.id, "OUT_FOR_DELIVERY")}
              >
                Out for Delivery
              </button>
            )}

            {order.status === "OUT_FOR_DELIVERY" && (
              <button
                className="btn-primary-admin mt-3"
                onClick={() => updateStatus(order.id, "DELIVERED")}
              >
                Mark as Delivered
              </button>
            )}

          </div>
        ))}
      </div>
    </div>
  );
}

export default DeliveryOrders;