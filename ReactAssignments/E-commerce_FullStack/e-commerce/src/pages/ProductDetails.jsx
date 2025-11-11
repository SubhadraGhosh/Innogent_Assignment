import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import { getProductById } from "../api/productAPI";
import { Container, Row, Col, Card, Spinner, Alert, Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { addToCart } from "../redux/slices/cartSlice";
import { toast } from "react-toastify";

function ProductDetails() {
    const { id } = useParams();
    const dispatch = useDispatch();

    const [product, setProduct] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    // Cart loading state from Redux (optional visual feedback)
    const { loading: cartLoading } = useSelector((state) => state.cart);

    // Fetch single product
    useEffect(() => {
        async function fetchProduct() {
            try {
                const data = await getProductById(id);
                if (!data || !data.id) throw new Error("Product not found");
                setProduct(data);
            } catch (err) {
                setError(err.message || "Failed to load product");
            } finally {
                setLoading(false);
            }
        }
        fetchProduct();
    }, [id]);

    // Loading state
    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" role="status" />
                <p className="mt-3">Loading product details...</p>
            </Container>
        );
    }

    // Error handling
    if (error) {
        return (
            <Container className="mt-5">
                <Alert variant="danger" className="text-center">
                    {error}
                </Alert>
            </Container>
        );
    }

    // Product not found
    if (!product) {
        return (
            <Container className="mt-5">
                <Alert variant="warning" className="text-center">
                    Product not found
                </Alert>
            </Container>
        );
    }

    // UI
    return (
        <Container className="mt-5">
            <Row className="justify-content-center align-items-center">
                {/* Image */}
                <Col md={5} className="text-center">
                    <Card className="shadow-sm border-0">
                        <Card.Img
                            variant="top"
                            src={product.imageUrl || "/assets/placeholder.png"}
                            alt={product.title || "Product Image"}
                            style={{ height: "350px", objectFit: "contain", padding: "20px" }}
                            onError={(e) => (e.target.src = "/assets/placeholder.png")}
                        />
                    </Card>
                </Col>

                {/* Details */}
                <Col md={6}>
                    <h2 className="fw-bold mb-3">{product.title || "Unknown Product"}</h2>
                    <p className="text-muted mb-2">
                        Category: <span className="fw-semibold">{product.category || "Not Available"}</span>
                    </p>
                    <p className="mb-4">{product.description || "No description available."}</p>

                    {/* Price */}
                    <h4 className="text-success fw-bold mb-3">
                        ₹ {product.price ? product.price.toFixed(2) : "N/A"}
                    </h4>

                    {/* Rating */}
                    <div className="product-rating text-warning mb-3">
                        ⭐ {product.rating || "N/A"} ({product.ratingCount || 0} reviews)
                    </div>

                    {/* Add to Cart */}
                    <Button
                        variant="secondary"
                        disabled={cartLoading}
                        onClick={() => {
                            if (product?.id) {
                                dispatch(addToCart({ productId: product.id, quantity: 1 }))
                                    .unwrap()
                                    .then(() => toast.success("Item added to cart!"))
                                    .catch(() => toast.error("Failed to add to cart"));
                            } else {
                                toast.error("Invalid product, cannot add to cart.");
                            }
                        }}
                    >
                        {cartLoading ? "Adding..." : "Add to Cart"}
                    </Button>
                </Col>
            </Row>
        </Container>
    );
}

export default ProductDetails;
