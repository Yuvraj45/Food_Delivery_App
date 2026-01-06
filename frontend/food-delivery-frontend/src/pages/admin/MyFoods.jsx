import { useEffect, useState } from "react";
import api from "../../api/api";
import "../../styles/admin.css";

function MyFoods() {
  const [foods, setFoods] = useState([]);
  const [loading, setLoading] = useState(true);

  // LOAD FOODS
  useEffect(() => {
    api
      .get("/admin/food/my")
      .then((res) => setFoods(res.data))
      .catch((err) => {
        console.error("LOAD FOODS ERROR 👉", err);
        alert("Failed to load foods");
      })
      .finally(() => setLoading(false));
  }, []);

  // DELETE FOOD
  const deleteFood = async (id) => {
    if (!window.confirm("Delete this food item?")) return;

    try {
      await api.delete(`/admin/food/${id}`);
      setFoods((prev) => prev.filter((f) => f.id !== id));
    } catch (err) {
      console.error("DELETE ERROR 👉", err);

      const message =
        err.response?.data?.message ||
        err.response?.data ||
        "Delete failed";

      alert(message);
    }
  };

  if (loading) {
    return <h4 className="text-center mt-5">Loading foods...</h4>;
  }

  if (foods.length === 0) {
    return (
      <div className="empty-state">
        <h3>No food items added</h3>
        <p>Add dishes to start receiving orders</p>
      </div>
    );
  }

  return (
    <div className="admin-page">
      <h2 className="admin-title">🍱 My Foods</h2>

      <div className="food-grid">
        {foods.map((food) => (
          <div key={food.id} className="food-card-premium">
            <div className="food-image">
              <img
                src={food.imageUrl || "https://via.placeholder.com/300"}
                alt={food.name}
              />
              <span
                className={`food-status ${
                  food.available ? "active" : "inactive"
                }`}
              >
                {food.available ? "Available" : "Out of Stock"}
              </span>
            </div>

            <div className="food-body">
              <h4>{food.name}</h4>
              <p className="category">{food.category}</p>

              <div className="food-meta">
                <span
                  className={`badge ${
                    food.type === "VEG" ? "veg" : "nonveg"
                  }`}
                >
                  {food.type}
                </span>

                <span className="price">₹ {food.price}</span>
              </div>

              <button
                className="admin-danger-btn mt-3"
                onClick={() => deleteFood(food.id)}
              >
                Delete
              </button>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}

export default MyFoods;