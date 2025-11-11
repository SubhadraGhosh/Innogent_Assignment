import axios from "axios";
import { toast } from "react-toastify";

// Base URL for all cart-related API calls
const BASE_URL = "http://localhost:8080/api/cart";

// Creating a configured axios instance
const api = axios.create({
    baseURL: BASE_URL,
});

// Adding an item to the cart
export const addToCart = async (productId, quantity = 1) => {
    try {
        const response = await api.post("/add", { productId, quantity });
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to add item");
        throw error;
    }
};

// Updating item quantity in the cart
export const updateCartItem = async (itemId, quantity) => {
    try {
        const response = await api.put(`/update/${itemId}?quantity=${quantity}`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to update item");
        throw error;
    }
};

// Removing a specific item from the cart
export const removeCartItem = async (itemId) => {
    try {
        const response = await api.delete(`/remove/${itemId}`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to remove item");
        throw error;
    }
};

// Clearing the entire cart
export const clearCart = async () => {
    try {
        const response = await api.delete("/clear");
        console.log("CartAPI sent the clear request");
        return response.data || {};
    } catch (error) {
        console.error("clearCart API failed:", error);
        toast.error(error.response?.data?.message || "Failed to clear cart");
        throw error;
    }
};

// Fetching the current cart items
export const fetchCart = async () => {
    try {
        const response = await api.get("");
        console.log("🧩 Backend cart data received:", response.data);
        const data = response.data;

        if (Array.isArray(data.items)) {
            data.items = data.items.map(item => ({
                ...item,
                productId: item.product?.id || item.productId,
                productName: item.product?.title || item.productName,
                productImage: item.product?.image || item.productImage,
                price: item.product?.price || item.price
            }));
        }

        return data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to fetch cart");
        throw error;
    }
};
