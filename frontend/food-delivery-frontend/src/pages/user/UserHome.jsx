import { useEffect, useRef, useState } from "react";
import { useSearchParams } from "react-router-dom";
import api from "../../api/api";
import "./userHome.css";

/* ================= HERO SLIDES ================= */
const HERO_SLIDES = [
  {
    title: "Royal Hyderabadi Biryani",
    image:
      "https://i.pinimg.com/1200x/37/74/08/3774086181a7f7c4b3f3c3c562255935.jpg"
  },
  {
    title: "Juicy Cheesy Burgers",
    image:
      "https://i.pinimg.com/1200x/d2/57/66/d25766786affa21bbfb3022f1c75ceb4.jpg"
  },
  {
    title: "Refreshing Juices",
    image:
      "https://i.pinimg.com/1200x/f6/7e/3f/f67e3ff8563f97abdffa1cd464447fc9.jpg"
  },
  {
    title: "Delicious Cakes",
    image:
      "https://i.pinimg.com/1200x/bd/16/95/bd169527a3bcb9cdae6a229bee5eb2e3.jpg"
  },
  {
    title: "Cheesy Hot Pizza",
    image:
      "https://i.pinimg.com/1200x/ac/b4/4a/acb44a5df768e436adbe55a49b61b3af.jpg"
  }
];

/* ================= CATEGORIES ================= */
const CATEGORIES = [
  "Biryani",
  "Beverages",
  "Pizza",
  "Desserts",
  "Burger",
  "Shawarma",
  "Chinese",
  "Curry",
  "Starters",
  "VegThali"
];

/* ================= CATEGORY IMAGE MAP ================= */
const CATEGORY_IMAGES = {
  Biryani:
    "https://i.pinimg.com/736x/a4/66/9a/a4669a419a1d51fc927182f6660bfb3e.jpg",
  Beverages:
    "https://i.pinimg.com/1200x/ee/3f/42/ee3f42ae97d810aa1e73c78d2c95cba1.jpg",
  Pizza:
    "https://i.pinimg.com/564x/ab/e6/57/abe65721a6d06545c99230151aab0177.jpg",
  "Desserts":
    "https://i.pinimg.com/736x/db/0d/ae/db0dae69c14733c1500dff980914e604.jpg",
  Burger:
    "https://i.pinimg.com/564x/1f/c3/c1/1fc3c179191b2009e9aa347ca15001b1.jpg",
  Shawarma:
    "https://i.pinimg.com/1200x/b9/37/7a/b9377ae0d7a98fd2cb8827bf1780fb15.jpg",
  Chinese:
    "https://i.pinimg.com/1200x/8c/f6/9a/8cf69ad0bdd31015fca7efdaa6988bf6.jpg",
  Curry:
    "https://i.pinimg.com/1200x/2d/91/13/2d9113b94f0e87e3a40c2490c6f7a0f0.jpg",
  Starters:
    "https://i.pinimg.com/736x/33/0b/ff/330bffee8d79a65b012e437c3aa7e789.jpg",
  VegThali:
    "https://i.pinimg.com/736x/3e/0b/a0/3e0ba0540b1cc809e84bbd9b762fd686.jpg"
};

function UserHome() {
  const [activeSlide, setActiveSlide] = useState(0);
  const [foods, setFoods] = useState([]);
  const [activeCategory, setActiveCategory] = useState(null);

  /* ✅ NEW: VEG / NON-VEG FILTER */
  const [foodType, setFoodType] = useState("ALL"); // ALL | VEG | NON_VEG

  const scrollRef = useRef(null);

  /* 🔍 SEARCH FROM NAVBAR */
  const [params] = useSearchParams();
  const search = params.get("q") || "";

  /* HERO AUTO SLIDE */
  useEffect(() => {
    const timer = setInterval(() => {
      setActiveSlide((prev) => (prev + 1) % HERO_SLIDES.length);
    }, 4000);
    return () => clearInterval(timer);
  }, []);

  /* LOAD FOODS */
  useEffect(() => {
    api.get("/foods")
      .then((res) => setFoods(res.data))
      .catch(() => alert("Failed to load foods"));
  }, []);

  /* FILTER (CATEGORY + SEARCH + VEG/NON-VEG) */
  const filteredFoods = foods.filter((f) => {
    const matchCategory =
      !activeCategory ||
      f.category?.toLowerCase() === activeCategory.toLowerCase();

    const matchSearch =
      f.name?.toLowerCase().includes(search.toLowerCase());

    const matchType =
      foodType === "ALL" || f.type === foodType;

    return matchCategory && matchSearch && matchType;
  });

  /* CATEGORY SCROLL */
  const scrollLeft = () => {
    scrollRef.current.scrollBy({ left: -600, behavior: "smooth" });
  };

  const scrollRight = () => {
    scrollRef.current.scrollBy({ left: 600, behavior: "smooth" });
  };

  return (
    <div className="user-home">

      {/* ================= HERO ================= */}
      <section
        className="hero"
        style={{
          backgroundImage: `url(${HERO_SLIDES[activeSlide].image})`
        }}
      >
        <div className="hero-overlay" />
        <div className="hero-content">
          <span className="hero-brand">UVbites</span>
          <h1>{HERO_SLIDES[activeSlide].title}</h1>
          <p>You bite. We bite. UVbites.</p>
        </div>
      </section>

      {/* ================= CATEGORY SLIDER ================= */}
      <section className="category-section">
        <h2>Order our best food options</h2>

        <div className="category-wrapper">
          <button className="scroll-btn left" onClick={scrollLeft}>‹</button>

          <div className="category-row" ref={scrollRef}>
            {CATEGORIES.map((cat) => (
              <div
                key={cat}
                className={`category-card ${
                  activeCategory === cat ? "active" : ""
                }`}
                onClick={() =>
                  setActiveCategory(activeCategory === cat ? null : cat)
                }
              >
                <div className="category-icon">
                  <img
                    src={CATEGORY_IMAGES[cat]}
                    alt={cat}
                    className="category-img"
                  />
                </div>
                <p>{cat}</p>
              </div>
            ))}
          </div>

          <button className="scroll-btn right" onClick={scrollRight}>›</button>
        </div>
      </section>

      {/* ================= VEG / NON-VEG FILTER ================= */}
      <div className="container mt-3 mb-4">
        <div className="veg-filter">
          <button
            className={foodType === "ALL" ? "active" : ""}
            onClick={() => setFoodType("ALL")}
          >
            All
          </button>

          <button
            className={foodType === "VEG" ? "active veg" : "veg"}
            onClick={() => setFoodType("VEG")}
          >
            🟢 Veg
          </button>

          <button
            className={foodType === "NON_VEG" ? "active nonveg" : "nonveg"}
            onClick={() => setFoodType("NON_VEG")}
          >
            🔴 Non-Veg
          </button>
        </div>
      </div>

      {/* ================= FOOD GRID ================= */}
      <div className="container mt-4">
        <div className="row">
          {filteredFoods.map((food) => (
            <div key={food.id} className="col-md-4 mb-4">
              <div className="card food-card shadow-soft h-100">
                <img
                  src={food.imageUrl || "https://via.placeholder.com/400"}
                  className="food-img"
                  alt={food.name}
                />

                <div className="card-body">
                  <h5 className="fw-semibold">{food.name}</h5>
                  <p className="text-muted">
                    {food.description || "Delicious & freshly prepared"}
                  </p>
                  <h6 className="fw-bold">₹ {food.price}</h6>
                </div>

                <div className="card-footer bg-white">
                  <button
                    className="btn btn-primary w-100"
                    onClick={async () => {
                      try {
                        await api.post(`/cart/add/${food.id}`);
                        alert("Added to cart 🛒");
                      } catch (err) {
                        alert(
                          err.response?.data ||
                          "Please login again. Unable to add item."
                        );
                      }
                    }}
                  >
                    Add to Cart
                  </button>
                </div>
              </div>
            </div>
          ))}

          {filteredFoods.length === 0 && (
            <p className="text-center text-muted mt-5">
              No items found 😕
            </p>
          )}
        </div>
      </div>
    </div>
  );
}

export default UserHome;