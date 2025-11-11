import axios from 'axios';

// Base URL for the fake store API
// const BASE_URL = 'https://fakestoreapi.com';
const BASE_URL = 'http://localhost:8080/api';

//Fetch all products
export const getAllProducts = async () => {
    try {
        const response = await axios.get(`${BASE_URL}/products`);
        return response.data;
    } catch (error) {
        console.error("Error fetching products:", error);
        throw error; // Throw again so we can handle it in context
    }
};

// Fetch product by ID
export const getProductById = async (id) => {
    try {
        const response = await axios.get(`${BASE_URL}/products/${id}`);
        return response.data;
    } catch (error) {
        console.error(`Error fetching product with ID ${id}:`, error);
        throw error;  // Throw again so we can handle it in context
    }
};