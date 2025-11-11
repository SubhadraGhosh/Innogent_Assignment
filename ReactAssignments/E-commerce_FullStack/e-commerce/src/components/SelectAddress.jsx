// src/components/SelectAddress.jsx
import React, { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchUserAddresses, createNewAddress, selectAddress } from "../redux/slices/addressSlice";
import DeliveryForm from "./DeliveryForm";
import { Button, Spinner, Form, Modal } from "react-bootstrap";
import { toast } from "react-toastify";

function SelectAddress({ userId }) {
    const dispatch = useDispatch();
    const { addresses, selectedAddress, loading } = useSelector((state) => state.address);

    const [showForm, setShowForm] = useState(false);

    useEffect(() => {
        if (userId) dispatch(fetchUserAddresses(userId));
    }, [userId, dispatch]);

    // Handle dropdown change
    const handleSelectChange = (e) => {
        const selected = addresses.find((addr) => addr.id === Number(e.target.value));
        dispatch(selectAddress(selected));
    };

    // Add new address
    const handleAddAddress = async (newAddress) => {
        await dispatch(createNewAddress(newAddress));
        setShowForm(false);
        toast.success("New address added!");
    };

    return (
        <div className="border rounded p-3 mb-4">
            <h5>Select Delivery Address</h5>

            {loading ? (
                <div className="d-flex justify-content-center my-3">
                    <Spinner animation="border" />
                </div>
            ) : (
                <>
                    <Form.Select
                        onChange={handleSelectChange}
                        value={selectedAddress?.id || ""}
                        className="my-2"
                    >
                        <option value="">-- Select Address --</option>
                        {addresses.map((address) => (
                            <option key={address.id} value={address.id}>
                                {`${address.fullName}, ${address.street}, ${address.city} (${address.pincode})`}
                            </option>
                        ))}
                    </Form.Select>

                    <Button variant="outline-primary" onClick={() => setShowForm(true)}>
                        + Add New Address
                    </Button>
                </>
            )}

            {/* Modal for new address form */}
            <Modal show={showForm} onHide={() => setShowForm(false)} centered>
                <Modal.Header closeButton>
                    <Modal.Title>Add New Address</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <DeliveryForm
                        onSubmit={handleAddAddress}
                        onCancel={() => setShowForm(false)}
                    />
                </Modal.Body>
            </Modal>
        </div>
    );
}

export default SelectAddress;
