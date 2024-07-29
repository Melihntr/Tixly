// src/views/AddBus.js
import React, { useState } from 'react';
import axios from 'axios';
import { Button, Form, Container } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { setMessage } from '../redux/slices/alertSlice'; // Assuming you have an alert slice for messages

const AddBus = () => {
    const [plateNo, setPlateNo] = useState('');
    const [seatNo, setSeatNo] = useState('');
    const [companyId, setCompanyId] = useState('');
    const [busType, setBusType] = useState('');
    const dispatch = useDispatch();

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const authKey = localStorage.getItem('authKey'); // Or however you store the auth key
            const response = await axios.post(
                'http://localhost:8080/admin/add-bus',
                { plateNo, seatNo, companyId, busType },
                { headers: { 'Authorization': `Bearer ${authKey}` } }
            );
            dispatch(setMessage(response.data));
        } catch (error) {
            dispatch(setMessage('Failed to add bus.'));
            console.error(error);
        }
    };

    return (
        <Container>
            <h1>Add Bus</h1>
            <Form onSubmit={handleSubmit}>
                <Form.Group controlId="plateNo">
                    <Form.Label>Plate Number</Form.Label>
                    <Form.Control
                        type="text"
                        value={plateNo}
                        onChange={(e) => setPlateNo(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="seatNo">
                    <Form.Label>Seat Number</Form.Label>
                    <Form.Control
                        type="number"
                        value={seatNo}
                        onChange={(e) => setSeatNo(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="companyId">
                    <Form.Label>Company ID</Form.Label>
                    <Form.Control
                        type="text"
                        value={companyId}
                        onChange={(e) => setCompanyId(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="busType">
                    <Form.Label>Bus Type</Form.Label>
                    <Form.Control
                        type="text"
                        value={busType}
                        onChange={(e) => setBusType(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit">Submit</Button>
            </Form>
        </Container>
    );
};

export default AddBus;
