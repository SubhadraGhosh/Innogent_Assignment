import { createSlice, createAsyncThunk } from "@reduxjs/toolkit";
import { getAddressesByUser, addAddress } from "../../api/addressAPI";
import { toast } from "react-toastify";

// Initial state
const initialState = {
    addresses: [],
    selectedAddress: null,
    loading: false,
    error: null,
};

// Fetch all addresses for a user
export const fetchUserAddresses = createAsyncThunk(
    "address/fetchUserAddresses",
    async (userId, { rejectWithValue }) => {
        try {
            const data = await getAddressesByUser(userId);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

// Add a new address
export const createNewAddress = createAsyncThunk(
    "address/createNewAddress",
    async (addressData, { rejectWithValue }) => {
        try {
            const data = await addAddress(addressData);
            return data;
        } catch (error) {
            return rejectWithValue(error.response?.data?.message || error.message);
        }
    }
);

const addressSlice = createSlice({
    name: "address",
    initialState,
    reducers: {
        // select address card
        selectAddress: (state, action) => {
            state.selectedAddress = action.payload;
        },
        clearAddressError: (state) => {
            state.error = null;
        },
    },
    extraReducers: (builder) => {
        builder
            // 🔹 fetch addresses
            .addCase(fetchUserAddresses.pending, (state) => {
                state.loading = true;
                state.error = null;
            })
            .addCase(fetchUserAddresses.fulfilled, (state, action) => {
                state.loading = false;
                state.addresses = action.payload;
            })
            .addCase(fetchUserAddresses.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
                toast.error(action.payload || "Failed to fetch addresses");
            })

            // 🔹 create address
            .addCase(createNewAddress.pending, (state) => {
                state.loading = true;
            })
            .addCase(createNewAddress.fulfilled, (state, action) => {
                state.loading = false;
                state.addresses.push(action.payload);
                toast.success("Address added successfully!");
            })
            .addCase(createNewAddress.rejected, (state, action) => {
                state.loading = false;
                state.error = action.payload;
                toast.error(action.payload || "Failed to add address");
            });
    },
});

export const { selectAddress, clearAddressError } = addressSlice.actions;
export default addressSlice.reducer;
