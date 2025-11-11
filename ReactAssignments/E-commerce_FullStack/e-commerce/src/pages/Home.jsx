import React, { useContext, useMemo, useState } from "react";
import { ProductContext } from "../context/ProductContext";
import { useNavigate } from "react-router-dom";
import { Container, Row, Col, Card, Spinner, Alert, Button } from "react-bootstrap";
import { useDispatch, useSelector } from "react-redux";
import { addToCart } from "../redux/slices/cartSlice";
import { toast } from "react-toastify";

function Home() {

    const { products, loading, error } = useContext(ProductContext);

    console.log("Products from backend:", products);
    const navigate = useNavigate();
    const dispatch = useDispatch();

    const { loading: cartLoading } = useSelector((state) => state.cart);

    const [selectedCategory, setSelectedCategory] = useState("All");
    const [searchTerm, setSearchTerm] = useState("");

    // Unique category list
    const categories = useMemo(() => {
        if (!products || products.length === 0) return ["All"];
        const unique = new Set(products.map((p) => p.categoryName));
        return ["All", ...unique];
    }, [products]);

    // Filtered products by category + search
    const filteredProducts = useMemo(() => {
        let filtered = products;
        if (selectedCategory !== "All") {
            filtered = filtered.filter((p) => p.categoryName === selectedCategory);
        }
        if (searchTerm.trim() !== "") {
            filtered = filtered.filter((p) =>
                p.title.toLowerCase().includes(searchTerm.toLowerCase())
            );
        }
        return filtered;
    }, [selectedCategory, products, searchTerm]);

    if (loading) {
        return (
            <Container className="text-center mt-5">
                <Spinner animation="border" role="status" />
                <p className="mt-3">Loading products...</p>
            </Container>
        );
    }

    if (error) {
        return (
            <Container className="mt-5 text-center">
                <Alert variant="danger">Error: {error}</Alert>
            </Container>
        );
    }

    if (!products || products.length === 0) {
        return (
            <Container className="mt-5 text-center">
                <Alert variant="warning">No products available</Alert>
            </Container>
        );
    }

    if (filteredProducts.length === 0) {
        return (
            <Container className="mt-5 text-center">
                <Alert variant="info">No products match your search.</Alert>
            </Container>
        );
    }

    return (
        <div className="products-list">
            <h2>All Products</h2>

            {/* Search bar */}
            <div className="search-bar">
                <input
                    type="text"
                    placeholder="Search products..."
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    className="search-input"
                />
            </div>

            {/* Category filter */}
            <div className="category-filter">
                {categories.map((cat, idx) => (
                    <button
                        key={`${cat}-${idx}`}
                        className={`category-btn ${selectedCategory === cat ? "active" : ""}`}
                        onClick={() => setSelectedCategory(cat)}
                    >
                        {cat}
                    </button>
                ))}
            </div>

            {/* Product Cards */}
            <Container className="mt-4">
                <Row className="g-4">
                    {filteredProducts.map((product, idx) => (
                        <Col
                            key={product.id ?? idx}
                            xs={12}
                            sm={6}
                            md={4}
                            lg={3}
                            style={{ cursor: "pointer" }}
                        >
                            <Card className="h-100 shadow-sm border-0">
                                {/* Product Image */}
                                <div onClick={() => navigate(`/product/${product.id}`)}>
                                    <Card.Img
                                        variant="top"
                                        src={product.imageUrl || "/assets/placeholder.png"}
                                        alt={product.title}
                                        style={{
                                            height: "200px",
                                            objectFit: "contain",
                                            padding: "15px",
                                        }}
                                        onError={(e) => (e.target.src = "/assets/placeholder.png")}
                                    />
                                </div>

                                <Card.Body className="text-center">
                                    <Card.Title style={{ fontSize: "15px" }}>
                                        {product.title}
                                    </Card.Title>
                                    <Card.Text className="fw-bold">₹ {product.price}</Card.Text>
                                    <Card.Text className="text-muted" style={{ fontSize: "14px" }}>
                                        ⭐ {product.rating || "N/A"} ({product.ratingCount || 0})
                                    </Card.Text>

                                    {/* Add to Cart */}
                                    <Button
                                        variant="secondary"
                                        disabled={cartLoading}
                                        onClick={(e) => {
                                            e.stopPropagation(); // prevent triggering navigate
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
                                </Card.Body>
                            </Card>
                        </Col>
                    ))}
                </Row>
            </Container>
        </div>
    );
}

export default Home;
