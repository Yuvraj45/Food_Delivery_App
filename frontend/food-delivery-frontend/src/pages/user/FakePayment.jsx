import { useNavigate, useSearchParams } from "react-router-dom";
import api from "../../api/api";
import { useState } from "react";

function FakePayment() {
  const navigate = useNavigate();
  const [params] = useSearchParams();
  const orderId = params.get("orderId");
  const [loading, setLoading] = useState(false);

  if (!orderId) {
    return (
      <h4 className="text-center mt-5 text-danger">
        Invalid payment request
      </h4>
    );
  }

  const pay = (status) => {
    setLoading(true);

    api.put(`/payment/${orderId}/status`, null, {
      params: { status }
    })
      .then(() => {
        navigate("/my-orders");
      })
      .catch(() => alert("Payment update failed"))
      .finally(() => setLoading(false));
  };

  return (
    <div className="container mt-5">
      <div
        className="card shadow-lg mx-auto p-4"
        style={{ maxWidth: "420px" }}
      >
        <h4 className="fw-bold text-center mb-3">
          Secure Payment
        </h4>

        <p className="text-muted text-center">
          Order ID: <b>#{orderId}</b>
        </p>

        <div className="border rounded p-3 mb-4">
          <div className="d-flex justify-content-between mb-2">
            <span>Payment Mode</span>
            <b>UPI / Net Banking</b>
          </div>

          <div className="d-flex justify-content-between">
            <span>Status</span>
            <span className="badge bg-warning text-dark">
              Pending
            </span>
          </div>
        </div>

        <button
          className="btn btn-success w-100 mb-3"
          disabled={loading}
          onClick={() => pay("PAID")}
        >
          {loading ? "Processing..." : "Pay Now"}
        </button>

        <button
          className="btn btn-outline-danger w-100"
          disabled={loading}
          onClick={() => pay("FAILED")}
        >
          Cancel Payment
        </button>

        <p className="text-center text-muted small mt-3">
          This is a demo payment screen
        </p>
      </div>
    </div>
  );
}

export default FakePayment;