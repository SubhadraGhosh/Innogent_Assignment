import { useEffect } from "react";
import { Routes, Route } from "react-router-dom";
import { useDispatch } from "react-redux";
import Header from "./components/Header";
import Footer from "./components/Footer";
import Home from "./pages/Home";
import ProductDetails from "./pages/ProductDetails";
import Cart from "./pages/Cart";
import Checkout from "./pages/Checkout";
import Notifications from "./pages/Notifications";
import Profile from "./pages/Profile";
import Orders from "./pages/Orders";
import { ProductProvider } from "./context/ProductContext";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.css";
import "./App.css";
import { fetchCart } from "./redux/slices/cartSlice";

// Main App
export default function App() {
  const dispatch = useDispatch();

  // Fetch cart from backend when app starts
  useEffect(() => {
    dispatch(fetchCart());
  }, [dispatch]);

  return (
    <>
      <ProductProvider>
        <Header />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/product/:id" element={<ProductDetails />} />
          <Route path="/cart" element={<Cart />} />
          <Route path="/checkout" element={<Checkout />} />
          <Route path="/notifications" element={<Notifications />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/orders" element={<Orders />} />
        </Routes>

        <Footer />

        <ToastContainer
          position="top-right"
          autoClose={2000}
          hideProgressBar={false}
          closeOnClick
          pauseOnHover
          draggable
          theme="colored"
        />
      </ProductProvider>
    </>
  );
}
