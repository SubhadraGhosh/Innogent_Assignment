// Importing dependencies
import React, { useEffect, useState } from "react";
import { Button, Card, Spinner } from "react-bootstrap";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";

// Importing API functions
import { getOrderSummaryByUser, cancelOrder } from "../api/orderAPI";

// Orders Page Component
export default function Orders() {
    const userId = 1; // replace this with logged-in user ID
    const [orders, setOrders] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchOrders = async () => {
            try {
                const data = await getOrderSummaryByUser(userId);
                setOrders(data);
            } catch (error) {
                toast.error("Failed to fetch orders");
                console.error(error);
            } finally {
                setLoading(false);
            }
        };
        fetchOrders();
    }, [userId]);

    const handleCancel = async (orderId) => {
        try {
            await cancelOrder(orderId);
            toast.warn("Order cancelled successfully");
            const updated = orders.map((o) =>
                o.id === orderId ? { ...o, status: "CANCELLED" } : o
            );
            setOrders(updated);
        } catch (error) {
            toast.error("Failed to cancel order");
            console.error(error);
        }
    };

    if (loading) {
        return (
            <div className="container py-5 text-center" style={{ minHeight: "71.5vh" }}>
                <Spinner animation="border" variant="primary" />
                <p className="mt-3 text-muted">Loading your orders...</p>
            </div>
        );
    }

    if (!orders || orders.length === 0) {
        return (
            <div className="container py-5 text-center" style={{ minHeight: "71.5vh" }}>
                <img
                    src="/assets/noOrders.png"
                    alt="No Orders"
                    width="220"
                    className="mb-3"
                />
                <h5 className="text-muted">You haven't placed any orders yet</h5>
                <Link to="/" className="btn btn-primary mt-3">
                    Start Shopping
                </Link>
            </div>
        );
    }

    return (
        <div className="container py-4" style={{ minHeight: "71.5vh" }}>
            <h2 className="mb-4 fw-semibold text-primary">My Orders</h2>

            <div className="d-flex flex-column gap-3">
                {orders.map((order) => (
                    <Card key={order.id} className="order-card shadow-sm border-0">
                        <Card.Body>
                            <div className="d-flex justify-content-between align-items-start">
                                {/* Order details */}
                                <div>
                                    <h5 className="fw-semibold mb-2">Order #{order.id}</h5>

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
                                        <strong>Items:</strong> {order.itemCount || 0}
                                    </p>

                                    {/* total before discount */}
                                    <p className="mb-1">
                                        <strong>Total (Before Discount):</strong> ₹
                                        {Number(order.totalAmount || 0).toFixed(2)}
                                    </p>

                                    {/* discount applied */}
                                    <p
                                        className="mb-1"
                                        style={{ color: order.discountApplied > 0 ? "green" : "#555" }}
                                    >
                                        <strong>Discount:</strong> ₹
                                        {Number(order.discountApplied || 0).toFixed(2)}
                                    </p>

                                    {/* ✅ total after discount (fixed field name + safe clamp) */}
                                    <p className="mb-1 fw-semibold text-primary">
                                        <strong>Total After Discount:</strong> ₹
                                        {Math.max(
                                            Number(order.totalAfterDiscount ?? 0),
                                            0
                                        ).toFixed(2)}
                                    </p>

                                    {/* placed on */}
                                    <p className="mb-1 text-muted">
                                        <strong>Placed On:</strong>{" "}
                                        {new Date(order.orderDate).toLocaleString()}
                                    </p>
                                </div>

                                {/* Action buttons */}
                                <div className="d-flex flex-column align-items-end gap-2">
                                    {order.status === "PLACED" && (
                                        <Button
                                            variant="danger"
                                            size="sm"
                                            onClick={() => handleCancel(order.id)}
                                        >
                                            Cancel
                                        </Button>
                                    )}
                                    {order.status === "DELIVERED" && (
                                        <Button variant="outline-success" size="sm" disabled>
                                            Delivered
                                        </Button>
                                    )}
                                    {order.status === "CANCELLED" && (
                                        <Button variant="outline-danger" size="sm" disabled>
                                            Cancelled
                                        </Button>
                                    )}
                                    <Link
                                        to={`/orders/${order.id}`}
                                        className="btn btn-sm btn-outline-primary"
                                    >
                                        View Details
                                    </Link>
                                </div>
                            </div>
                        </Card.Body>
                    </Card>
                ))}
            </div>
        </div>
    );
}
