import React, { useEffect, useState } from 'react';
import { Modal, Button, Table, Spinner } from 'react-bootstrap';
import axios from 'axios';
import styles from './GetBusModal.module.css'; // Import the CSS module

const GetBusModal = ({ showModal, handleClose }) => {
    const [buses, setBuses] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        if (showModal) {
            fetchBuses();
        }
    }, [showModal]);

    const fetchBuses = async () => {
        const authKey = localStorage.getItem('authKey');

        try {
            const response = await axios.get('http://localhost:8080/bus/company', {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });
            setBuses(response.data);
        } catch (error) {
            console.error('Error fetching buses:', error);
            setError('Bilgiler getirilemedi.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Modal show={showModal} onHide={handleClose} size="lg" centered>
            <Modal.Header closeButton className={styles.modalHeader}>
                <Modal.Title className={styles.modalTitle}>Otobüs Bilgileri</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles.modalBody}>
                {loading ? (
                    <div className={styles.spinnerWrapper}>
                        <Spinner animation="border" />
                        <p>Yükleniyor...</p>
                    </div>
                ) : error ? (
                    <p className={styles.alertError}>{error}</p>
                ) : (
                    <Table striped bordered hover className={styles.table}>
                        <thead>
                            <tr>
                                <th>ID</th>
                                <th>Plaka No</th>
                                <th>Şirket ID</th>
                                <th>Otobüs Tipi</th>
                                <th>Koltuk Sayısı</th>
                            </tr>
                        </thead>
                        <tbody>
                            {buses.map(bus => (
                                <tr key={bus.id}>
                                    <td>{bus.id}</td>
                                    <td>{bus.plateNo}</td>
                                    <td>{bus.companyId}</td>
                                    <td>{bus.busType}</td>
                                    <td>{bus.seatNo}</td>
                                </tr>
                            ))}
                        </tbody>
                    </Table>
                )}
            </Modal.Body>
            <Modal.Footer>
                <Button variant="secondary" onClick={handleClose}>
                    Kapat
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default GetBusModal;
