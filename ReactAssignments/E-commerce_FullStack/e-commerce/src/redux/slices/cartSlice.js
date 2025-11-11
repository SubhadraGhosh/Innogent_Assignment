import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { validatePromoCode } from "../../api/promoAPI";
import {
    addToCart as addToCartAPI,
    updateCartItem,
    removeCartItem,
    clearCart as clearCartAPI,
    fetchCart as fetchCartAPI,
} from "../../api/cartAPI";

// Initial cart state
const initialState = {
    items: [],
    totalQuantity: 0,
    totalPrice: 0,
    loading: false,
    error: null,
    promoCode: null,
    discount: 0,
    promoData: null,
};

// Thunks

export const applyPromo = createAsyncThunk(
    "cart/applyPromo",
    async (promoCode, { rejectWithValue }) => {
        try {
            const data = await validatePromoCode(promoCode);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || "Invalid Promo Code");
        }
    }
);

export const fetchCart = createAsyncThunk(
    "cart/fetchCart",
    async (_, { rejectWithValue }) => {
        try {
            const data = await fetchCartAPI();
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

export const addToCart = createAsyncThunk(
    "cart/addToCart",
    async ({ productId, quantity }, { rejectWithValue }) => {
        try {
            const data = await addToCartAPI(productId, quantity);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

export const updateCart = createAsyncThunk(
    "cart/updateCart",
    async ({ itemId, quantity }, { rejectWithValue }) => {
        try {
            const data = await updateCartItem(itemId, quantity);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

export const removeFromCart = createAsyncThunk(
    "cart/removeFromCart",
    async (itemId, { rejectWithValue }) => {
        try {
            const data = await removeCartItem(itemId); // ye itemId hamesha item.id hona chahiye
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

export const clearCart = createAsyncThunk(
    "cart/clearCart",
    async (_, { rejectWithValue }) => {
        try {
            const data = await clearCartAPI();
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

// Decrease quantity thunk - decrement or remove item
export const decreaseQuantity = createAsyncThunk(
    "cart/decreaseQuantity",
    async ({ itemId }, { getState, rejectWithValue }) => {
        try {
            const state = getState();
            const item = state.cart.items.find(
                (i) => String(i.id) === String(itemId) || String(i.itemId) === String(itemId)
            );
            if (!item) {
                return rejectWithValue("Item not found in cart");
            }
            if (item.quantity > 1) {
                const data = await updateCartItem(itemId, item.quantity - 1);
                return data;
            } else {
                const data = await removeCartItem(itemId);
                return data;
            }
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

// Redux slice
const cartSlice = createSlice({
    name: "cart",
    initialState,
    reducers: {
        removePromo: (state) => {
            state.promoCode = null;
            state.discount = 0;
            state.promoData = null;
        },
    },
    extraReducers: (builder) => {
        builder
            .addCase(fetchCart.fulfilled, (state, action) => {
                const data = action.payload || {};
                state.items = data.items || [];
                state.totalQuantity = state.items.reduce((sum, item) => sum + (item.quantity || 1), 0);
                state.totalPrice = state.items.reduce(
                    (sum, item) => sum + (item.price * (item.quantity || 1) || 0),
                    0
                );
            })
            .addCase(addToCart.fulfilled, (state, action) => {
                const backendItems = action.payload.items.map(item => ({
                    ...item,
                    productId: item.product?.id || item.productId,
                    productName: item.product?.title || item.productName,
                    productImage: item.product?.image || item.productImage,
                    price: item.product?.price || item.price,
                }));
                state.items = backendItems;
                state.totalQuantity = action.payload.totalQuantity;
                state.totalPrice = action.payload.totalPrice;
            })
            .addCase(updateCart.fulfilled, (state, action) => {
                const backendItems = action.payload.items.map(item => ({
                    ...item,
                    productId: item.product?.id || item.productId,
                    productName: item.product?.title || item.productName,
                    productImage: item.product?.image || item.productImage,
                    price: item.product?.price || item.price,
                }));
                state.items = backendItems;
                state.totalQuantity = action.payload.totalQuantity;
                state.totalPrice = action.payload.totalPrice;
            })
            .addCase(removeFromCart.fulfilled, (state, action) => {
                const backendItems = action.payload.items.map(item => ({
                    ...item,
                    productId: item.product?.id || item.productId,
                    productName: item.product?.title || item.productName,
                    productImage: item.product?.image || item.productImage,
                    price: item.product?.price || item.price,
                }));
                state.items = backendItems;
                state.totalQuantity = action.payload.totalQuantity;
                state.totalPrice = action.payload.totalPrice;
            })
            .addCase(clearCart.fulfilled, (state) => {
                state.items = [];
                state.totalQuantity = 0;
                state.totalPrice = 0;
                state.promoCode = null;
                state.discount = 0;
                state.promoData = null;
            })
            .addCase(applyPromo.fulfilled, (state, action) => {
                state.loading = false;
                const promo = action.payload;

                state.promoData = promo;
                state.promoCode = promo.code;

                if (promo.discountType === "PERCENTAGE") {
                    state.discount = (state.totalPrice * promo.discountValue) / 100;
                } else if (promo.discountType === "FIXED") {
                    state.discount = promo.discountValue;
                }

                // force UI re-render
                state.totalPrice = state.totalPrice;
            });
    },
});

export const { removePromo } = cartSlice.actions;
export default cartSlice.reducer;
