import { useState } from "react";
import api from "../../api/api";
import "../../styles/admin.css";

function AddFood() {
  const [food, setFood] = useState({
    name: "",
    category: "Biryani",
    customCategory: "",
    type: "VEG",
    price: "",
    description: "",
    imageUrl: "",
    available: true
  });

  const handleChange = (e) => {
    const { name, value, type, checked } = e.target;
    setFood({ ...food, [name]: type === "checkbox" ? checked : value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const finalCategory =
      food.category === "Other" ? food.customCategory : food.category;

    try {
      await api.post("/admin/food/add", {
        ...food,
        category: finalCategory
      });

      alert("✅ Food added successfully");

      setFood({
        name: "",
        category: "Biryani",
        customCategory: "",
        type: "VEG",
        price: "",
        description: "",
        imageUrl: "",
        available: true
      });
    } catch {
      alert("Failed to add food");
    }
  };

  return (
    <div className="admin-page">
      <div className="admin-glass-card addfood-card">
        <div className="admin-header">
          <h2>Add New Food</h2>
          <p>Create premium menu items for customers</p>
        </div>

        <form onSubmit={handleSubmit} className="admin-form">

          <input
            className="admin-input"
            placeholder="Food Name"
            name="name"
            value={food.name}
            onChange={handleChange}
            required
          />

         <select
  className="admin-input admin-select"
  name="category"
  value={food.category}
  onChange={handleChange}
>

            <option>Biryani</option>
            <option>Pizza</option>
            <option>Burger</option>
            <option>Chinese</option>
            <option>Shawarma</option>
            <option>Desserts</option>
            <option>Beverages</option>
            <option>VegThali</option>
            <option>Curry</option>
            <option>Starters</option>
            <option>Other</option>
          </select>

          {food.category === "Other" && (
            <input
              className="admin-input"
              placeholder="Custom Category"
              name="customCategory"
              value={food.customCategory}
              onChange={handleChange}
              required
            />
          )}

          <div className="type-toggle">
            <label>
              <input
                type="radio"
                name="type"
                value="VEG"
                checked={food.type === "VEG"}
                onChange={handleChange}
              />
              🟢 Veg
            </label>

            <label>
              <input
                type="radio"
                name="type"
                value="NON_VEG"
                checked={food.type === "NON_VEG"}
                onChange={handleChange}
              />
              🔴 Non-Veg
            </label>
          </div>

          <input
            type="number"
            className="admin-input"
            placeholder="Price (₹)"
            name="price"
            value={food.price}
            onChange={handleChange}
            required
          />

          <textarea
            className="admin-input"
            rows="3"
            placeholder="Short description"
            name="description"
            value={food.description}
            onChange={handleChange}
          />

          <input
            className="admin-input"
            placeholder="Food Image URL"
            name="imageUrl"
            value={food.imageUrl}
            onChange={handleChange}
          />

          <div className="availability-toggle">
            <input
              type="checkbox"
              checked={food.available}
              name="available"
              onChange={handleChange}
            />
            <span>Available for ordering</span>
          </div>

          <button className="admin-primary-btn">
            Save Food Item
          </button>

        </form>
      </div>
    </div>
  );
}

export default AddFood;