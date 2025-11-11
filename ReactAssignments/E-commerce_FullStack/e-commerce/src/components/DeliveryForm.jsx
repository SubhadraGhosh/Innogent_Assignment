import React, { useState } from "react";
import { Button, Form, Row, Col, Spinner } from "react-bootstrap";
import { toast } from "react-toastify";
import { addAddress } from "../api/addressAPI";

export default function DeliveryForm({ onSubmit, onCancel }) {
    const [loading, setLoading] = useState(false);
    const [form, setForm] = useState({
        fullName: "",
        phone: "",
        street: "",
        city: "",
        state: "",
        pincode: "",
        country: "India",
    });
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        const { name, value } = e.target;
        setForm((prev) => ({ ...prev, [name]: value }));
    };

    const validate = () => {
        const e = {};
        if (!form.fullName.trim()) e.fullName = "Full name is required";
        else if (!/^[A-Za-z ]{3,}$/.test(form.fullName)) e.fullName = "Name must be only letters";

        if (!/^[6-9]\d{9}$/.test(form.phone)) e.phone = "Enter a valid 10-digit number";

        if (!form.street.trim()) e.street = "Street address is required";
        if (!form.city.trim()) e.city = "City is required";
        if (!form.state.trim()) e.state = "State is required";
        if (!/^\d{6}$/.test(form.pincode)) e.pincode = "Pincode must be 6 digits";

        setErrors(e);
        return Object.keys(e).length === 0;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        if (!validate()) return;

        try {
            setLoading(true);
            const payload = { ...form, userId: 1 };
            const saved = await addAddress(payload);
            if (saved?.id) {
                toast.success("Address added successfully!");
                if (onSubmit) onSubmit(saved);
            } else toast.error("Something went wrong!");
        } catch (err) {
            console.error(err);
            toast.error("Failed to save address");
        } finally {
            setLoading(false);
        }
    };

    return (
        <Form onSubmit={handleSubmit}>
            <h5 className="mb-3 border-bottom pb-2">Delivery Details</h5>

            <Row className="g-3">
                <Col md={6}>
                    <Form.Group>
                        <Form.Label>Full Name</Form.Label>
                        <Form.Control
                            name="fullName"
                            value={form.fullName}
                            onChange={handleChange}
                            placeholder="Enter full name"
                        />
                        {errors.fullName && <small className="text-danger">{errors.fullName}</small>}
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group>
                        <Form.Label>Phone</Form.Label>
                        <Form.Control
                            name="phone"
                            value={form.phone}
                            onChange={handleChange}
                            placeholder="10-digit number"
                        />
                        {errors.phone && <small className="text-danger">{errors.phone}</small>}
                    </Form.Group>
                </Col>

                <Col md={12}>
                    <Form.Group>
                        <Form.Label>Street</Form.Label>
                        <Form.Control
                            as="textarea"
                            name="street"
                            value={form.street}
                            onChange={handleChange}
                            placeholder="House no, street name..."
                        />
                        {errors.street && <small className="text-danger">{errors.street}</small>}
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group>
                        <Form.Label>City</Form.Label>
                        <Form.Control name="city" value={form.city} onChange={handleChange} />
                        {errors.city && <small className="text-danger">{errors.city}</small>}
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group>
                        <Form.Label>State</Form.Label>
                        <Form.Control name="state" value={form.state} onChange={handleChange} />
                        {errors.state && <small className="text-danger">{errors.state}</small>}
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group>
                        <Form.Label>Pincode</Form.Label>
                        <Form.Control name="pincode" value={form.pincode} onChange={handleChange} />
                        {errors.pincode && <small className="text-danger">{errors.pincode}</small>}
                    </Form.Group>
                </Col>

                <Col md={6}>
                    <Form.Group>
                        <Form.Label>Country</Form.Label>
                        <Form.Control
                            name="country"
                            value={form.country}
                            onChange={handleChange}
                            disabled
                        />
                    </Form.Group>
                </Col>
            </Row>

            <div className="d-flex justify-content-end mt-4">
                <Button variant="secondary" className="me-2" onClick={onCancel} disabled={loading}>
                    Cancel
                </Button>
                <Button variant="primary" type="submit" disabled={loading}>
                    {loading ? (
                        <>
                            <Spinner animation="border" size="sm" /> Saving...
                        </>
                    ) : (
                        "Save & Place Order"
                    )}
                </Button>
            </div>
        </Form>
    );
}
