import axios from "axios";

// create - base axios instance for address APIs
const addressAPI = axios.create({
    baseURL: "http://localhost:8080/api/address",
});

// create - add new address
export const addAddress = async (addressData) => {
    try {
        // send POST request to backend
        const response = await addressAPI.post("/add", addressData);
        return response.data;
    } catch (error) {
        // checking - handle server or network error
        console.error("Error adding address:", error.response?.data || error.message);
        throw error;
    }
};

// checking - get all addresses for a user
export const getAddressesByUser = async (userId) => {
    try {
        // fetch address list
        const response = await addressAPI.get(`/user/${userId}`);
        return response.data;
    } catch (error) {
        console.error("Error fetching addresses:", error.response?.data || error.message);
        throw error;
    }
};

export default addressAPI;
