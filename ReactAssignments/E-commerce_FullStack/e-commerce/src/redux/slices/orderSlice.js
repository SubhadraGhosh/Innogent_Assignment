// // Importing Redux toolkit
// import { createSlice } from "@reduxjs/toolkit";

// // Initial state for orders
// const initialState = {
//     orders: [], // Array to store all orders
// };

// // Creating order slice
// const orderSlice = createSlice({
//     name: "order",
//     initialState,

//     reducers: {
//         // Adding a new order (on checkout)
//         addOrder: (state, action) => {
//             const { items, totalAmount, address, promoApplied, discountApplied } = action.payload;

//             // Creating a new order object
//             const newOrder = {
//                 id: new Date().getTime(),   // Unique order ID
//                 items,
//                 totalAmount,
//                 address: address || "Not Provided",
//                 status: "Pending",
//                 createdAt: new Date().toISOString(),
//                 promoApplied: promoApplied || null,
//                 discountApplied: discountApplied || 0
//             };

//             // Pushing order into state
//             state.orders.push(newOrder);

//             // Auto-mark order as delivered after 6 hours
//             setTimeout(() => {
//                 const order = state.orders.find((o) => o.id === newOrder.id);
//                 if (order && order.status === "Pending") {
//                     order.status = "Delivered";
//                 }
//             }, 6 * 60 * 60 * 1000); // 6 hours = 21600000ms
//         },

//         // Cancelling an existing order
//         cancelOrder: (state, action) => {
//             const order = state.orders.find((o) => o.id === action.payload);
//             if (order && order.status === "Pending") {
//                 order.status = "Cancelled";
//             }
//         },

//         // Manually marking an order as delivered
//         markAsDelivered: (state, action) => {
//             const order = state.orders.find((o) => o.id === action.payload);
//             if (order && order.status === "Pending") {
//                 order.status = "Delivered";
//             }
//         },

//         // Clearing all orders (optional - for testing or logout)
//         clearOrders: (state) => {
//             state.orders = [];
//         },
//     },
// });

// // Exporting actions and reducer
// export const { addOrder, cancelOrder, markAsDelivered, clearOrders } =
//     orderSlice.actions;

// export default orderSlice.reducer;

import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { placeOrder, fetchOrders } from "../../api/orderAPI";


import { toast } from "react-toastify";

const initialState = {
    orders: [],
    selectedOrder: null,
    loading: false,
    error: null,
};

// Place order thunk
export const placeNewOrder = createAsyncThunk(
    "order/placeNewOrder",
    async (orderPayload, { rejectWithValue }) => {
        try {
            const data = await placeOrder(orderPayload);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

// Fetch all orders
export const getOrders = createAsyncThunk(
    "order/getOrders",
    async (userId, { rejectWithValue }) => {
        try {
            const data = await fetchOrders(userId);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

const orderSlice = createSlice({
    name: "order",
    initialState,
    reducers: {},
    extraReducers: (builder) => {
        builder
            .addCase(placeNewOrder.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(placeNewOrder.fulfilled, (state, action) => {
                state.loading = false;
                toast.success("Order placed successfully!");
            })
            .addCase(placeNewOrder.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
                toast.error(action.payload || "Failed to place order");
            })
            .addCase(getOrders.fulfilled, (state, action) => {
                state.orders = action.payload;
            });
    },
});

export default orderSlice.reducer;
