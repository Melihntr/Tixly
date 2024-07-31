import React, { useState } from 'react';
import { Container, Button, Modal, Form, Alert, Nav } from 'react-bootstrap';
import axios from 'axios';
import 'bootstrap/dist/css/bootstrap.min.css';
import styles from './OwnerDashboard.module.css'; // Import CSS module
import { Link, useNavigate } from 'react-router-dom'; // Import Link and useNavigate

const OwnerDashboard = () => {
    const [showModal, setShowModal] = useState(false);
    const [plateNo, setPlateNo] = useState('');
    const [companyId, setCompanyId] = useState('');
    const [busType, setBusType] = useState('');
    const [seatNo, setSeatNo] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate(); // Initialize useNavigate

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

    const handleLogout = async () => {
        const authKey = localStorage.getItem('authKey');

        if (authKey) {
            try {
                await axios.post('http://localhost:8080/admin/logout', {}, {
                    headers: {
                        'Authorization': `Bearer ${authKey}`
                    }
                });

                // Clear authKey from localStorage and redirect to login
                localStorage.removeItem('authKey');
                navigate('/owner-login'); // Redirect to login page
            } catch (error) {
                console.error('Çıkış yapma hatası:', error);
                // Optionally handle error (e.g., show an alert)
            }
        } else {
            // Handle case where authKey is not present
            console.warn('Auth key not found in localStorage.');
        }
    };

    return (
        <div className={styles.dashboardContainer}>
            {/* Sidebar */}
            <div className={styles.sidebar}>
                {/* Logo Text with Link */}
                <Link to="/" className={styles.logoLink}>
                    <div className={styles.logoText}>Tixly</div> 
                </Link>
                
                <Nav className="flex-column">
                    <Nav.Link href="#companyInfo" className={`${styles.navLink} ${styles.navLinkTop}`}>Şirket Bilgileri</Nav.Link>
                    <Nav.Link href="#tripInfo" className={`${styles.navLink} ${styles.navLinkMiddle}`}>Sefer Bilgileri</Nav.Link>
                    <Nav.Link href="#busInfo" className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Bilgileri</Nav.Link>
                    <Nav.Link onClick={handleShow} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Ekle</Nav.Link>
                    <Nav.Link href="#addTrip" className={`${styles.navLink} ${styles.navLinkMiddle}`}>Sefer Ekle</Nav.Link>
                    <Nav.Link onClick={handleLogout} className={`${styles.navLink} ${styles.navLinkBottom}`}>Çıkış Yap</Nav.Link>
                </Nav>
            </div>

            {/* Main content */}
            <Container className={styles.mainContent}>
                <h2>Yönetici Panosu</h2>
                <p>Yönetici Panosuna Hoşgeldiniz!</p>

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
                            <Form.Group controlId="formCompanyId">
                                <Form.Label>Şirket ID</Form.Label>
                                <Form.Control
                                    type="number"
                                    placeholder="Şirket ID'sini girin"
                                    value={companyId}
                                    onChange={(e) => setCompanyId(e.target.value)}
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
            </Container>
        </div>
    );
};

export default OwnerDashboard;
