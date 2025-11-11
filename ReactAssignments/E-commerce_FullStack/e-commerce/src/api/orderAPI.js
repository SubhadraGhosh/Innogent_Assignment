import axios from "axios";
import { toast } from "react-toastify";

const BASE_URL = "http://localhost:8080/api/orders";

const api = axios.create({
    baseURL: BASE_URL,
});

// Place a new order
export const placeOrder = async (orderPayload) => {
    try {
        const response = await api.post("/", orderPayload);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to place order");
        throw error;
    }
};

// Fetch all orders for a user
export const fetchOrders = async (userId) => {
    try {
        const response = await api.get(`/user/${userId}`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to fetch orders");
        throw error;
    }
};

// Fetch order summary list for a user (used in My Orders page)
export const getOrderSummaryByUser = async (userId) => {
    try {
        const response = await api.get(`/user/${userId}/summary`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to fetch order summary");
        throw error;
    }
};

// Fetch details of one order (used in Order Details page)
export const getOrderDetails = async (orderId) => {
    try {
        const response = await api.get(`/${orderId}`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to fetch order details");
        throw error;
    }
};

// Cancel an order
export const cancelOrder = async (orderId) => {
    try {
        const response = await api.put(`/${orderId}/cancel`);
        return response.data;
    } catch (error) {
        toast.error(error.response?.data?.message || "Failed to cancel order");
        throw error;
    }
};
