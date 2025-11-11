import React, { useEffect } from "react";
import { useSelector, useDispatch } from "react-redux";
import {
    updateCart,
    decreaseQuantity,
    removeFromCart,
    fetchCart,
} from "../redux/slices/cartSlice";
import CartItem from "../components/CartItem";
import { useNavigate, Link } from "react-router-dom";
import { Button } from "react-bootstrap";

export default function Cart() {
    const dispatch = useDispatch();
    const navigate = useNavigate();

    const { items = [], totalPrice = 0 } = useSelector((state) => state.cart || {});

    useEffect(() => {
        dispatch(fetchCart());
    }, [dispatch]);

    const handleIncrease = (item) => {
        dispatch(updateCart({ itemId: item.id, quantity: item.quantity + 1 }));
    };

    const handleDecrease = (item) => {
        dispatch(decreaseQuantity({ itemId: item.id }));
    };

    const handleRemove = (id) => {
        dispatch(removeFromCart(id));
    };

    const handleCheckout = () => {
        if (!items || items.length === 0) {
            alert("Cart is empty!");
            return;
        }
        navigate("/checkout");
    };

    // Calculate total on frontend if backend doesn't send updated total
    const calculatedTotal =
        totalPrice > 0
            ? totalPrice
            : items.reduce((sum, item) => sum + item.price * item.quantity, 0);

    return (
        <div className="container py-4" style={{ minHeight: '72vh' }}>
            <h2 className="mb-4 fw-semibold text-primary">Your Cart</h2>

            {(!items || items.length === 0) ? (
                <div className="text-center py-5">
                    <img
                        src="/assets/emptyCart.png"
                        alt="Empty Cart"
                        width="200"
                        className="mb-3"
                    />
                    <h5 className="text-muted">Your cart is empty</h5>
                    <Link to="/" className="btn btn-primary mt-3">
                        Go Shopping
                    </Link>
                </div>
            ) : (
                <>
                    {items.map((item) => (
                        <CartItem
                            key={item.id}
                            item={item}
                            onIncrease={() => handleIncrease(item)}
                            onDecrease={() => handleDecrease(item)}
                            onRemove={handleRemove}
                        />
                    ))}

                    <hr className="my-4" />

                    <div className="d-flex flex-column flex-md-row justify-content-between align-items-center">
                        <h4 className="fw-bold mb-3 mb-md-0">
                            Total: ₹{Number(calculatedTotal || 0).toFixed(2)}
                        </h4>
                        <div className="d-flex gap-3">
                            {/* <Button variant="outline-danger" onClick={handleClearCart}>
                                Clear Cart
                            </Button> */}
                            <Button
                                variant="success"
                                className="px-4"
                                onClick={handleCheckout}
                            >
                                Proceed to Checkout
                            </Button>
                        </div>
                    </div>
                </>
            )}
        </div>
    );
}
