import { configureStore } from "@reduxjs/toolkit";
import cartReducer from "./slices/cartSlice";
import orderReducer from "./slices/orderSlice";
import addressReducer from "./slices/addressSlice";

// Central Redux Store Configuration
export const store = configureStore({
    reducer: {
        cart: cartReducer,       // Manages all cart actions and state
        order: orderReducer,     // Manages all order-related actions and status
        address: addressReducer, // Manages user addresses and selected address
    },
    middleware: (getDefaultMiddleware) =>
        getDefaultMiddleware({
            serializableCheck: false, // Prevents warnings for async or non-serializable data
        }),
    devTools: process.env.NODE_ENV !== "production", // Enable Redux DevTools in development mode only
});
