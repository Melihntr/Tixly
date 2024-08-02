import React, { useState } from 'react';
import { Modal, Form, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import styles from './AddTripModal.module.css'; // Import the CSS module

const AddTripModal = ({ show, handleClose }) => {
    const [peronNo, setPeronNo] = useState('');
    const [departureLocationId, setDepartureLocationId] = useState('');
    const [arrivalLocationId, setArrivalLocationId] = useState('');
    const [estimatedTime, setEstimatedTime] = useState('');
    const [price, setPrice] = useState('');
    const [busId, setBusId] = useState('');
    const [departureTime, setDepartureTime] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleAddTrip = async (e) => {
        e.preventDefault();

        const tripData = {
            peronNo,
            departureLocationId,
            arrivalLocationId,
            estimatedTime,
            price,
            busId,
            departureTime
        };

        const authKey = localStorage.getItem('authKey');
        
        try {
            const response = await axios.post('http://localhost:8080/trip', tripData, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authKey}`
                }
            });

            if (response.status === 200) {
                setSuccess('Seyahat başarıyla eklendi!');
                setError('');
                handleClose(); // Close the modal on success
            }
        } catch (error) {
            console.error('Seyahat ekleme hatası:', error);
            setError('Seyahat eklenemedi. Lütfen tekrar deneyin.');
            setSuccess('');
        }
    };

    return (
        <Modal show={show} onHide={handleClose}>
            <Modal.Header closeButton className={styles.modalHeader}>
                <Modal.Title className={styles.modalTitle}>Seyahat Ekle</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles.modalBody}>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                <Form onSubmit={handleAddTrip}>
                    <Form.Group controlId="formPeronNo">
                        <Form.Label>Peron No</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Peron numarasını girin"
                            value={peronNo}
                            onChange={(e) => setPeronNo(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formDepartureLocationId">
                        <Form.Label>Kalkış Yeri</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Kalkış yerini girin"
                            value={departureLocationId}
                            onChange={(e) => setDepartureLocationId(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formArrivalLocationId">
                        <Form.Label>Varış Yeri</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Varış yerini girin"
                            value={arrivalLocationId}
                            onChange={(e) => setArrivalLocationId(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formEstimatedTime">
                        <Form.Label>Tahmini Süre (dakika)</Form.Label>
                        <Form.Control
                            type="number"
                            placeholder="Tahmini süreyi girin"
                            value={estimatedTime}
                            onChange={(e) => setEstimatedTime(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formPrice">
                        <Form.Label>Fiyat</Form.Label>
                        <Form.Control
                            type="number"
                            step="0.01"
                            placeholder="Fiyatı girin"
                            value={price}
                            onChange={(e) => setPrice(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formBusId">
                        <Form.Label>Otobüs ID</Form.Label>
                        <Form.Control
                            type="number"
                            placeholder="Otobüs ID'sini girin"
                            value={busId}
                            onChange={(e) => setBusId(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formDepartureTime">
                        <Form.Label>Kalkış Zamanı</Form.Label>
                        <Form.Control
                            type="datetime-local"
                            placeholder="Kalkış zamanını girin"
                            value={departureTime}
                            onChange={(e) => setDepartureTime(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <div className={styles.submitButtonContainer}>
                        <Button variant="primary" type="submit" className={styles.submitButton}>
                            Seyahat Ekle
                        </Button>
                    </div>
                </Form>
            </Modal.Body>
        </Modal>
    );
};

export default AddTripModal;
