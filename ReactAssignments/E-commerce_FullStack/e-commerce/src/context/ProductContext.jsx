import { createContext, useState, useEffect } from "react";
import { getAllProducts } from "../api/productAPI";

// Create context object
export const ProductContext = createContext();

// Cpmtext Provider component
export function ProductProvider({ children }) {

    //Store all product
    const [products, setProducts] = useState([]);

    //tracking loading state
    const [loading, setLoading] = useState(true);

    //track any errors
    const [error, setError] = useState(null);

    //Fetch Product on mount
    useEffect(() => {

        async function fetchProduct() {
            try {
                const data = await getAllProducts();
                setProducts(data);
            } catch (err) {
                setError(err.message || "Failed to fetch products");
            } finally {
                setLoading(false);
            }
        }

        fetchProduct();
    }, []);

    // provide state to children
    return (
        <ProductContext.Provider value={{ products, loading, error }}>
            {children}
        </ProductContext.Provider>
    );
}