// Importing dependencies
import React, { useState, useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import { clearCart, applyPromo, removePromo } from "../redux/slices/cartSlice";
import { useNavigate } from "react-router-dom";
import SelectAddress from "../components/SelectAddress";
import { toast } from "react-toastify";

// Importing backend API
import { placeOrder } from "../api/orderAPI";

export default function Checkout() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    // cart state
    const { items = [], totalPrice = 0, discount = 0, promoCode, promoData } = useSelector(
        (state) => state.cart || {}
    );

    // address state
    const { selectedAddress } = useSelector((state) => state.address);

    const [promoInput, setPromoInput] = useState("");
    const [error, setError] = useState("");

    // clear promo when cart is empty
    useEffect(() => {
        if (!items || items.length === 0) {
            dispatch(removePromo());
        }
    }, [items, dispatch]);

    // apply promo code
    const handleApplyPromo = async () => {
        if (!promoInput.trim()) {
            setError("Please enter a promo code");
            return;
        }

        setError("");
        try {
            const result = await dispatch(applyPromo(promoInput)).unwrap();
            if (result) {
                toast.success(`${result.code} applied successfully!`);
            }
        } catch (err) {
            setError(err || "Invalid or expired promo code");
            toast.error(err || "Invalid or expired promo code");
        }
    };

    // remove promo code
    const handleRemovePromo = () => {
        dispatch(removePromo());
        setPromoInput("");
        toast.info("Promo code removed");
    };

    // place order
    const handlePlaceOrder = async () => {
        if (!items || items.length === 0) {
            toast.warn("Your cart is empty!");
            navigate("/cart");
            return;
        }

        if (!selectedAddress) {
            toast.warn("Please select a delivery address before placing the order");
            return;
        }

        try {
            const orderPayload = {
                userId: 1, // static for now
                addressId: selectedAddress.id,
                promoCode: promoData ? promoData.code : "",
                items: items.map((item) => ({
                    itemId: item.id,
                    productId: item.productId,
                    quantity: item.quantity,
                    price: item.price,
                })),
            };

            console.log("Sending order payload:", orderPayload);
            const response = await placeOrder(orderPayload);
            console.log("Order placed successfully:", response);

            await dispatch(clearCart()).unwrap();
            toast.success("Order placed successfully!");
            navigate("/orders");
        } catch (error) {
            console.error("Order placement failed:", error);
            toast.error("Failed to place order, please try again");
        }
    };

    // ✅ Clamp discount & total safely
    const validDiscount = Math.min(discount, totalPrice);
    const totalPayable = Math.max(totalPrice - validDiscount, 0);

    return (
        <div className="container py-4">
            <h2>Checkout</h2>

            {/* basic cart summary */}
            <p>
                Items: {items.length} — Total: ₹{(totalPrice || 0).toFixed(2)}
            </p>

            {/* address section */}
            <div className="mb-4">
                <SelectAddress userId={1} />
            </div>

            {/* promo code section */}
            <div className="promo-section mb-3">
                <h5>Apply Promo Code</h5>
                <div className="d-flex gap-2">
                    <input
                        type="text"
                        placeholder="Enter promo code"
                        value={promoInput}
                        onChange={(e) => setPromoInput(e.target.value)}
                        className="form-control w-25"
                    />

                    {!promoCode ? (
                        <button className="btn btn-primary" onClick={handleApplyPromo}>
                            Apply
                        </button>
                    ) : (
                        <button className="btn btn-danger" onClick={handleRemovePromo}>
                            Remove
                        </button>
                    )}
                </div>

                {error && <p className="text-danger mt-2">{error}</p>}
                {promoData && <p className="text-success mt-2">{promoData.code} applied!</p>}
            </div>

            {/* ✅ updated price summary */}
            <div className="price-summary mb-4">
                <p>Discount: -₹{validDiscount.toFixed(2)}</p>
                <h4>Total Payable: ₹{totalPayable.toFixed(2)}</h4>
            </div>

            {/* button - place order */}
            <button className="btn btn-success" onClick={handlePlaceOrder}>
                Place Order
            </button>
        </div>
    );
}
