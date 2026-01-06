import { useEffect, useState } from "react";
import api from "../../api/api";

const STATUS_FLOW = [
  "PLACED",
  "CONFIRMED",
  "PICKED_UP",
  "OUT_FOR_DELIVERY",
  "DELIVERED"
];

function MyOrders() {
  const [orders, setOrders] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    api.get("/orders/my")
      .then(res => setOrders(res.data))
      .catch(() => alert("Failed to load orders"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <h4 className="text-center mt-5">Loading your orders…</h4>;
  }

  if (orders.length === 0) {
    return <h4 className="text-center mt-5">📭 No orders yet</h4>;
  }

  const getStepIndex = (status) =>
    STATUS_FLOW.indexOf(status);

  return (
    <div className="container mt-4 mb-5">
      <h3 className="fw-bold mb-4">📦 Your Orders</h3>

      {orders.map(order => {
        const currentStep = getStepIndex(order.status);

        return (
          <div key={order.id} className="card shadow-soft mb-4 p-3">

            {/* HEADER */}
            <div className="d-flex justify-content-between align-items-center">
              <h6 className="fw-bold mb-0">
                Order #{order.id}
              </h6>
              <span className="badge bg-primary">
                {order.status}
              </span>
            </div>

            <p className="text-muted-sm mt-1 mb-1">
              Total: ₹ {order.totalAmount}
            </p>

            <p className="text-muted-sm mb-3">
              Payment: {order.paymentMode} ({order.paymentStatus})
            </p>

            {/* TIMELINE */}
            <div className="timeline">
              {STATUS_FLOW.map((step, index) => (
                <div
                  key={step}
                  className={`timeline-step ${
                    index <= currentStep ? "active" : ""
                  }`}
                >
                  {index + 1}
                </div>
              ))}
            </div>

            <div className="timeline-labels">
              <span>Placed</span>
              <span>Confirmed</span>
              <span>Picked</span>
              <span>On Way</span>
              <span>Done</span>
            </div>

          </div>
        );
      })}
    </div>
  );
}

export default MyOrders;