// src/views/OwnerDashboard.js
import React, { useState } from 'react';
import { Container, Button, Modal, Form, Alert } from 'react-bootstrap';
import axios from 'axios';

const OwnerDashboard = () => {
    const [showModal, setShowModal] = useState(false);
    const [plateNo, setPlateNo] = useState('');
    const [companyId, setCompanyId] = useState('');
    const [busType, setBusType] = useState('');
    const [seatNo, setSeatNo] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleShow = () => setShowModal(true);
    const handleClose = () => setShowModal(false);

    const handleAddBus = async (e) => {
        e.preventDefault();

        const busData = {
            plateNo,
            companyId,
            busType,
            seatNo
        };

        try {
            const response = await axios.post('http://localhost:8080/buses', busData, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {
                setSuccess('Bus added successfully!');
                setError('');
                handleClose(); // Close the modal on success
            }
        } catch (error) {
            console.error('Error adding bus:', error);
            setError('Failed to add bus. Please try again.');
            setSuccess('');
        }
    };

    return (
        <Container className="mt-5">
            <h2>Owner Dashboard</h2>
            <p>Welcome to the Owner Dashboard!</p>
            <Button variant="primary" onClick={handleShow}>
                Add Bus
            </Button>

            <Modal show={showModal} onHide={handleClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Add Bus</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    {error && <Alert variant="danger">{error}</Alert>}
                    {success && <Alert variant="success">{success}</Alert>}
                    <Form onSubmit={handleAddBus}>
                        <Form.Group controlId="formPlateNo">
                            <Form.Label>Plate Number</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter plate number"
                                value={plateNo}
                                onChange={(e) => setPlateNo(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formCompanyId">
                            <Form.Label>Company ID</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter company ID"
                                value={companyId}
                                onChange={(e) => setCompanyId(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formBusType">
                            <Form.Label>Bus Type</Form.Label>
                            <Form.Control
                                type="text"
                                placeholder="Enter bus type"
                                value={busType}
                                onChange={(e) => setBusType(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Form.Group controlId="formSeatNo">
                            <Form.Label>Number of Seats</Form.Label>
                            <Form.Control
                                type="number"
                                placeholder="Enter number of seats"
                                value={seatNo}
                                onChange={(e) => setSeatNo(e.target.value)}
                                required
                            />
                        </Form.Group>
                        <Button variant="primary" type="submit" className="mt-3">
                            Add Bus
                        </Button>
                    </Form>
                </Modal.Body>
            </Modal>
        </Container>
    );
};

export default OwnerDashboard;

