import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";

function Cart() {
  const [cartItems, setCartItems] = useState([]);
  const [paymentMode, setPaymentMode] = useState("COD");
  const [loading, setLoading] = useState(false);
  const navigate = useNavigate();

  useEffect(() => {
    loadCart();
  }, []);

  const loadCart = () => {
    api.get("/cart")
      .then(res => setCartItems(res.data))
      .catch(() => alert("Failed to load cart"));
  };

  // ➕ INCREMENT
  const incrementQty = (foodId) => {
    api.post(`/cart/add/${foodId}`)
      .then(loadCart)
      .catch(() => alert("Failed to increase quantity"));
  };

  // ➖ DECREMENT
  const decrementQty = (item) => {
    if (item.quantity === 1) {
      removeItem(item.id);
      return;
    }

    api.put(`/cart/decrement/${item.id}`)
      .then(loadCart)
      .catch(() => alert("Failed to decrease quantity"));
  };

  // ❌ REMOVE ITEM
  const removeItem = (id) => {
    api.delete(`/cart/${id}`)
      .then(loadCart)
      .catch(() => alert("Failed to remove item"));
  };

  // 📦 PLACE ORDER
  const placeOrder = () => {
    if (cartItems.length === 0) return;

    const uniqueAdmins = [
      ...new Set(cartItems.map(i => i.foodItem.admin.id))
    ];

    if (uniqueAdmins.length > 1) {
      alert("You can order from only one restaurant at a time");
      return;
    }

    setLoading(true);

    api.post("/orders/place-from-cart", null, {
      params: {
        adminId: uniqueAdmins[0],
        paymentMode
      }
    })
      .then(res => {
        if (paymentMode === "COD") {
          navigate("/my-orders");
        } else {
          navigate(`/payment?orderId=${res.data.id}`);
        }
      })
      .catch(err => {
        alert(err.response?.data?.message || "Order failed");
      })
      .finally(() => setLoading(false));
  };

  const totalAmount = cartItems.reduce(
    (sum, item) => sum + item.quantity * item.foodItem.price,
    0
  );

  return (
    <div className="container mt-4 mb-5 pb-5">
      <h3 className="fw-bold mb-4">🛒 Your Cart</h3>

      {cartItems.length === 0 && (
        <p className="text-muted">Your cart is empty</p>
      )}

      {cartItems.map(item => (
        <div key={item.id} className="card shadow-soft mb-3 p-3">
          <div className="cart-item">

            <div>
              <h6 className="fw-semibold mb-1">
                {item.foodItem.name}
              </h6>
              <p className="text-muted-sm mb-0">
                ₹ {item.foodItem.price} each
              </p>
            </div>

            <div className="d-flex align-items-center gap-2">
              <button
                className="qty-btn"
                onClick={() => decrementQty(item)}
              >
                −
              </button>

              <span className="qty-count">{item.quantity}</span>

              <button
                className="qty-btn"
                onClick={() => incrementQty(item.foodItem.id)}
              >
                +
              </button>
            </div>

            <div className="fw-bold">
              ₹ {item.foodItem.price * item.quantity}
            </div>

          </div>
        </div>
      ))}

      {/* ================= STICKY CHECKOUT ================= */}
      {cartItems.length > 0 && (
        <div className="sticky-checkout">
          <div className="sticky-checkout-inner">

            <div>
              <div className="total-amount">
                ₹ {totalAmount}
              </div>
              <small className="text-muted">
                Taxes & charges included
              </small>
            </div>

            <div className="d-flex gap-2 align-items-center">
              <select
                className="form-select"
                value={paymentMode}
                onChange={(e) => setPaymentMode(e.target.value)}
              >
                <option value="COD">Cash on Delivery</option>
                <option value="UPI">UPI</option>
                <option value="NET_BANKING">Net Banking</option>
              </select>

              <button
                className="btn btn-primary px-4"
                disabled={loading}
                onClick={placeOrder}
              >
                {loading ? "Placing…" : "Place Order"}
              </button>
            </div>

          </div>
        </div>
      )}
    </div>
  );
}

export default Cart;