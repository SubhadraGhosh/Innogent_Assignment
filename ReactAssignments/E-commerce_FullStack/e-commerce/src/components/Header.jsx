import React, { useEffect } from "react";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { Navbar, Container, Nav, Badge, Dropdown } from "react-bootstrap";
import { useSelector, useDispatch } from "react-redux";
import { fetchCart } from "../redux/slices/cartSlice";

function Header() {
    const location = useLocation();
    const navigate = useNavigate();
    const dispatch = useDispatch();

    // Fetch cart on mount (to keep badge updated after refresh)
    useEffect(() => {
        dispatch(fetchCart());
    }, [dispatch]);

    // Function to check if link is active
    const isActive = (path) => (location.pathname === path ? "active-link" : "");

    // Fetch cart whenever items or quantity change
    const totalQuantity = useSelector(
        (state) => state.cart?.totalQuantity || 0
    );

    useEffect(() => {
        dispatch(fetchCart());
    }, [dispatch, totalQuantity]);


    console.log(totalQuantity);

    return (
        <Navbar bg="light" expand="lg" fixed="top" className="shadow-sm mb-4" style={{ zIndex: 1030 }}>
            <Container>
                {/* Website brand name */}
                <Navbar.Brand as={Link} to="/" className="fw-bold text-primary">
                    E-Commerce
                </Navbar.Brand>

                {/* Toggle for mobile view */}
                <Navbar.Toggle aria-controls="basic-navbar-nav" />
                <Navbar.Collapse id="basic-navbar-nav">
                    {/* Navigation icons section */}
                    <Nav className="ms-auto d-flex align-items-center gap-3">
                        {/* Notification icon */}
                        <Nav.Link
                            as={Link}
                            to="/notifications"
                            className={isActive("/notifications")}
                        >
                            <img
                                className="header-icon"
                                src="/assets/notification.png"
                                alt="Notifications"
                                width="28"
                                height="28"
                            />
                        </Nav.Link>

                        {/* Profile dropdown menu */}
                        <Dropdown align="end">
                            <Dropdown.Toggle
                                as="div"
                                className="p-0 m-0 border-0 bg-transparent dropdown-toggle-no-arrow"
                                style={{
                                    cursor: "pointer",
                                    background: "transparent",
                                    border: "none",
                                }}
                            >
                                <img
                                    className="header-icon"
                                    src="/assets/user.png"
                                    alt="User Profile"
                                    width="28"
                                    height="28"
                                    style={{
                                        filter: "none",
                                        cursor: "pointer",
                                    }}
                                />
                            </Dropdown.Toggle>

                            <Dropdown.Menu>
                                <Dropdown.Item onClick={() => navigate("/orders")}>
                                    My Orders
                                </Dropdown.Item>
                                <Dropdown.Item onClick={() => navigate("/profile")}>
                                    Profile
                                </Dropdown.Item>
                                <Dropdown.Divider />
                                <Dropdown.Item onClick={() => alert("Logged out (dummy)")}>
                                    Logout
                                </Dropdown.Item>
                            </Dropdown.Menu>
                        </Dropdown>

                        {/* Shopping cart icon with badge */}
                        <Nav.Link as={Link} to="/cart" className={isActive("/cart")}>
                            <div
                                style={{
                                    position: "relative",
                                    display: "inline-flex",
                                    alignItems: "center",
                                    justifyContent: "center",
                                    width: "32px",
                                    height: "32px",
                                }}
                            >
                                <img
                                    className="header-icon"
                                    src="/assets/shoppingBag.png"
                                    alt="Shopping Cart"
                                    width="28"
                                    height="28"
                                    style={{
                                        position: "relative",
                                        zIndex: 2,
                                        filter: "none",
                                    }}
                                />

                                {/* ✅ Always calculate badge safely and make it visible */}
                                {Number(totalQuantity) > 0 && (
                                    <Badge
                                        bg="danger"
                                        pill
                                        style={{
                                            position: "absolute",
                                            top: "-6px",
                                            right: "-8px",
                                            fontSize: "11px",
                                            minWidth: "18px",
                                            height: "18px",
                                            display: "flex",
                                            alignItems: "center",
                                            justifyContent: "center",
                                            zIndex: 9999,
                                        }}
                                    >
                                        {totalQuantity > 9 ? "9+" : totalQuantity}
                                    </Badge>
                                )}
                            </div>
                        </Nav.Link>

                    </Nav>
                </Navbar.Collapse>
            </Container>
        </Navbar>
    );
}

export default Header;
