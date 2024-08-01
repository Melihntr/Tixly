import React, { useState } from 'react';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';

const AddBusModal = ({ showModal, handleClose }) => {
    const [plateNo, setPlateNo] = useState('');
    const [busType, setBusType] = useState('');
    const [seatNo, setSeatNo] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleAddBus = async (e) => {
        e.preventDefault();

        const busData = {
            plateNo,
            busType,
            seatNo
        };

        const authKey = localStorage.getItem('authKey');

        try {
            const response = await axios.post('http://localhost:8080/bus', busData, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authKey}`
                }
            });

            if (response.status === 200) {
                setSuccess('Otobüs başarıyla eklendi!');
                setError('');
                handleClose(); // Close the modal on success
            }
        } catch (error) {
            console.error('Otobüs ekleme hatası:', error);
            setError('Otobüs eklenemedi. Lütfen tekrar deneyin.');
            setSuccess('');
        }
    };

    return (
        <Modal show={showModal} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Otobüs Ekle</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                <Form onSubmit={handleAddBus}>
                    <Form.Group controlId="formPlateNo">
                        <Form.Label>Plaka Numarası</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Plaka numarasını girin"
                            value={plateNo}
                            onChange={(e) => setPlateNo(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formBusType">
                        <Form.Label>Otobüs Türü</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Otobüs türünü girin"
                            value={busType}
                            onChange={(e) => setBusType(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formSeatNo">
                        <Form.Label>Koltuk Sayısı</Form.Label>
                        <Form.Control
                            type="number"
                            placeholder="Koltuk sayısını girin"
                            value={seatNo}
                            onChange={(e) => setSeatNo(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Button variant="primary" type="submit" className="mt-3">
                        Otobüs Ekle
                    </Button>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AddBusModal;
