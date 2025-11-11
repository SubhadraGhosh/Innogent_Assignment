import React, { useEffect, useState } from "react";
import { useParams, Link } from "react-router-dom";
import { Card, Spinner, Button } from "react-bootstrap";
import { toast } from "react-toastify";
import { getOrderDetails } from "../api/orderAPI";

export default function OrderDetails() {
    const { orderId } = useParams();
    const [order, setOrder] = useState(null);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrderDetails = async () => {
            try {
                const data = await getOrderDetails(orderId);
                setOrder(data);
            } catch (error) {
                toast.error("Failed to fetch order details");
                console.error("Error fetching order details:", error);
            } finally {
                setLoading(false);
            }
        };
        fetchOrderDetails();
    }, [orderId]);

    if (loading) {
        return (
            <div className="container py-5 text-center" style={{ minHeight: "71.5vh" }}>
                <Spinner animation="border" variant="primary" />
                <p className="mt-3 text-muted">Loading order details...</p>
            </div>
        );
    }

    if (!order) {
        return (
            <div className="container py-5 text-center" style={{ minHeight: "71.5vh" }}>
                <h4 className="text-danger">Order not found</h4>
                <Link to="/orders" className="btn btn-primary mt-3">
                    Back to Orders
                </Link>
            </div>
        );
    }

    return (
        <div className="container py-4" style={{ minHeight: "71.5vh" }}>
            <h2 className="fw-semibold text-primary mb-4">Order #{order.id}</h2>

            {/* Order summary */}
            <Card className="shadow-sm mb-4">
                <Card.Body>
                    <h5 className="fw-semibold mb-3">Order Summary</h5>

                    <p className="mb-1">
                        <strong>Status:</strong>{" "}
                        <span
                            style={{
                                color:
                                    order.status === "DELIVERED"
                                        ? "green"
                                        : order.status === "CANCELLED"
                                            ? "red"
                                            : "orange",
                            }}
                        >
                            {order.status}
                        </span>
                    </p>

                    <p className="mb-1">
                        <strong>Placed On:</strong>{" "}
                        {new Date(order.orderDate).toLocaleString()}
                    </p>

                    <p className="mb-1">
                        <strong>Total (Before Discount):</strong> ₹
                        {Number(order.totalAmount || 0).toFixed(2)}
                    </p>

                    <p className="mb-1 text-success">
                        <strong>Discount:</strong> ₹
                        {Number(order.discountApplied || 0).toFixed(2)}
                    </p>

                    <h5 className="mt-2 text-primary">
                        Total After Discount: ₹
                        {Math.max(Number(order.totalAfterDiscount ?? 0), 0).toFixed(2)}
                    </h5>

                    {order.promoCode && (
                        <p className="mt-2 text-success">
                            <strong>Promo Used:</strong> {order.promoCode}
                        </p>
                    )}
                </Card.Body>
            </Card>

            {/* Delivery Address */}
            <Card className="shadow-sm mb-4">
                <Card.Body>
                    <h5 className="fw-semibold mb-3">Delivery Address</h5>
                    <p className="mb-1">
                        <strong>{order.address?.fullName}</strong>
                    </p>
                    <p className="mb-1">{order.address?.street}</p>
                    <p className="mb-1">
                        {order.address?.city}, {order.address?.state} -{" "}
                        {order.address?.pincode}
                    </p>
                    <p className="mb-1">{order.address?.country}</p>
                    <p className="mb-0">📞 {order.address?.phone}</p>
                </Card.Body>
            </Card>

            {/* Items Ordered */}
            <Card className="shadow-sm">
                <Card.Body>
                    <h5 className="fw-semibold mb-3">Items Ordered</h5>

                    {(order.orderItems || []).map((item, index) => (
                        <div
                            key={index}
                            className="d-flex justify-content-between align-items-center border-bottom py-2"
                        >
                            <div>
                                <p className="mb-1 fw-semibold">
                                    {item.productTitle || `Product #${item.productId}`}
                                </p>
                                <small>
                                    Qty: {item.quantity} × ₹
                                    {Number(item.price || 0).toFixed(2)}
                                </small>
                            </div>
                            <span className="fw-semibold">
                                ₹{Number((item.price || 0) * (item.quantity || 0)).toFixed(2)}
                            </span>
                        </div>
                    ))}
                </Card.Body>
            </Card>

            {/* Navigation */}
            <div className="text-end mt-4">
                <Link to="/orders" className="btn btn-outline-primary">
                    Back to My Orders
                </Link>
            </div>
        </div>
    );
}
