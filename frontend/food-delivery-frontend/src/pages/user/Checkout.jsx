import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";

function Checkout() {
  const [cartItems, setCartItems] = useState([]);
  const [loading, setLoading] = useState(true);
  const navigate = useNavigate();

  useEffect(() => {
    api.get("/cart")
      .then(res => setCartItems(res.data))
      .catch(() => alert("Failed to load checkout"))
      .finally(() => setLoading(false));
  }, []);

  if (loading) {
    return <h4 className="text-center mt-5">Preparing checkout…</h4>;
  }

  if (cartItems.length === 0) {
    return <h4 className="text-center mt-5">🛒 Cart is empty</h4>;
  }

  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.quantity * item.foodItem.price,
    0
  );

  const adminId = cartItems[0].foodItem.admin.id;

  const placeOrder = () => {
    api.post("/orders/place-from-cart", null, {
      params: {
        adminId,
        paymentMode: "COD"
      }
    })
      .then(() => {
        navigate("/my-orders");
      })
      .catch(() => alert("Order failed"));
  };

  return (
    <div className="container mt-4 mb-5" style={{ maxWidth: "720px" }}>
      <h3 className="fw-bold mb-4">🧾 Order Summary</h3>

      {/* ITEMS */}
      <div className="card shadow-soft mb-4 p-3">
        {cartItems.map(item => (
          <div
            key={item.id}
            className="d-flex justify-content-between mb-3"
          >
            <div>
              <h6 className="mb-1">{item.foodItem.name}</h6>
              <small className="text-muted">
                ₹ {item.foodItem.price} × {item.quantity}
              </small>
            </div>

            <div className="fw-semibold">
              ₹ {item.foodItem.price * item.quantity}
            </div>
          </div>
        ))}

        <hr />

        <div className="d-flex justify-content-between fw-bold fs-5">
          <span>Total</span>
          <span>₹ {totalAmount}</span>
        </div>
      </div>

      {/* PAYMENT */}
      <div className="card shadow-soft p-3">
        <h5 className="fw-semibold mb-3">Payment Method</h5>

        <div className="d-flex align-items-center gap-3">
          <input type="radio" checked readOnly />
          <span>Cash on Delivery</span>
        </div>

        <button
          className="btn btn-primary w-100 mt-4"
          onClick={placeOrder}
        >
          Place Order
        </button>
      </div>
    </div>
  );
}

export default Checkout;