import React from "react";
import { Button } from "react-bootstrap";

export default function CartItem({ item, onIncrease, onDecrease, onRemove }) {
    const handleRemoveClick = () => {
        onRemove(item.id);
    };

    return (
        <div className="cart-item d-flex justify-content-between align-items-center border p-3 rounded mb-3 bg-white shadow-sm">
            {/* Left section - product image & name */}
            <div className="d-flex align-items-center">
                <img
                    src={item.productImage}
                    alt={item.productName}
                    style={{
                        width: "80px",
                        height: "80px",
                        objectFit: "contain",
                        borderRadius: "8px",
                        backgroundColor: "#fff",
                        marginRight: "15px",
                    }}
                />
                <div>
                    <h6 className="fw-semibold mb-1">{item.productName}</h6>
                    <p className="mb-0 text-muted small">₹{item.price.toFixed(2)}</p>
                </div>
            </div>

            {/* Right section - quantity controls + price + remove */}
            <div className="d-flex align-items-center" style={{ gap: "20px" }}>
                {/* quantity controls */}
                <div className="d-flex align-items-center" style={{ gap: "8px" }}>
                    <Button
                        variant="outline-secondary"
                        size="sm"
                        className="fw-bold"
                        onClick={onDecrease}
                    >
                        −
                    </Button>
                    <span className="fw-semibold">{item.quantity}</span>
                    <Button
                        variant="outline-secondary"
                        size="sm"
                        className="fw-bold"
                        onClick={onIncrease}
                    >
                        +
                    </Button>
                </div>

                {/* price + remove */}
                <div className="text-end" style={{ minWidth: "100px" }}>
                    <p className="mb-1 fw-semibold">
                        ₹{(item.price * item.quantity).toFixed(2)}
                    </p>
                    <Button
                        variant="outline-danger"
                        size="sm"
                        onClick={handleRemoveClick}
                    >
                        Remove
                    </Button>
                </div>
            </div>
        </div>
    );
}
