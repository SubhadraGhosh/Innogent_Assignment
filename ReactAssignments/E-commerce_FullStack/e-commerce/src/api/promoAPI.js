import axios from "axios";

const BASE_URL = "http://localhost:8080/api";

// Create an axios instance with the base URL
const api = axios.create({
    baseURL: BASE_URL,
});

// Validating a promo code
export const validatePromoCode = async (promoCode) => {
    try {
        const response = await api.post("/promo/validate", { code: promoCode });
        return response.data;
    } catch (error) {
        console.error("Error validating promo code:", error);
        throw error;
    }
};
