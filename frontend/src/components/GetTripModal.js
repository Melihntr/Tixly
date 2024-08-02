import React, { useState, useEffect } from 'react';
import { Modal, Button, Table, Alert } from 'react-bootstrap';
import axios from 'axios';
import styles from './GetTripModal.module.css'; // Import the CSS module

const GetTripModal = ({ showModal, handleClose }) => {
    const [trips, setTrips] = useState([]);
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    useEffect(() => {
        if (showModal) {
            const fetchTrips = async () => {
                const authKey = localStorage.getItem('authKey');

                try {
                    const response = await axios.get('http://localhost:8080/trip/company', {
                        headers: {
                            'Authorization': `Bearer ${authKey}`
                        }
                    });

                    setTrips(response.data);
                    setSuccess('Seyahatler başarıyla yüklendi!');
                    setError('');
                } catch (error) {
                    console.error('Seyahatleri yükleme hatası:', error);
                    setError('Seyahatler yüklenemedi. Lütfen tekrar deneyin.');
                    setSuccess('');
                }
            };

            fetchTrips();
        }
    }, [showModal]);

    return (
        <Modal show={showModal} onHide={handleClose} size="lg">
            <Modal.Header closeButton className={styles.modalHeader}>
                <Modal.Title className={styles.modalTitle}>Şirketin Seyahatleri</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles.modalBody}>
                {error && <Alert variant="danger" className={styles.alertError}>{error}</Alert>}
                {success && <Alert variant="success" className={styles.alertSuccess}>{success}</Alert>}
                <Table striped bordered hover className={styles.table}>
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Peron No</th>
                            <th>Yola Çıkış Yeri</th>
                            <th>Varış Yeri</th>
                            <th>Tahmini Süre (dk)</th>
                            <th>Fiyat</th>
                            <th>Şirket ID</th>
                            <th>Otobüs ID</th>
                            <th>Yola Çıkış Zamanı</th>
                            <th>Durum</th>
                        </tr>
                    </thead>
                    <tbody>
                        {trips.map(trip => (
                            <tr key={trip.id}>
                                <td>{trip.id}</td>
                                <td>{trip.peronNo}</td>
                                <td>{trip.departureLocationId}</td>
                                <td>{trip.arrivalLocationId}</td>
                                <td>{trip.estimatedTime}</td>
                                <td>{trip.price}</td>
                                <td>{trip.companyId}</td>
                                <td>{trip.busId}</td>
                                <td>{new Date(trip.departureTime).toLocaleString()}</td>
                                <td>{trip.state}</td>
                            </tr>
                        ))}
                    </tbody>
                </Table>
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Kapat
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default GetTripModal;
