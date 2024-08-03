import React, { useState, useEffect } from 'react';
import { Container, Card, Button, Table, Spinner, Alert } from 'react-bootstrap'; // Removed Nav import
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import AddBusModal from '../components/AddBusModal'; // Import AddBusModal
import Sidebar from '../components/Sidebar'; // Import Sidebar component
import styles from './OtobüsBilgileri.module.css'; // Import CSS module

const OtobüsBilgileri = () => {
    const [buses, setBuses] = useState([]);
    const [showAddBusModal, setShowAddBusModal] = useState(false);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const navigate = useNavigate();

    useEffect(() => {
        fetchBuses();
    }, []);

    const fetchBuses = async () => {
        setLoading(true);
        const authKey = localStorage.getItem('authKey');
        if (authKey) {
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
            }
        } else {
            console.warn('Auth key not found in localStorage.');
        }
        setLoading(false);
    };

    const handleShowAddBusModal = () => setShowAddBusModal(true);
    const handleCloseAddBusModal = () => {
        setShowAddBusModal(false);
        fetchBuses(); // Refresh the bus list when closing the modal
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
                localStorage.removeItem('authKey');
                navigate('/owner-login');
            } catch (error) {
                console.error('Çıkış yapma hatası:', error);
            }
        } else {
            console.warn('Auth key not found in localStorage.');
        }
    };

    const handleDeleteBus = async (plateNo) => {
        const authKey = localStorage.getItem('authKey');
        if (authKey) {
            try {
                await axios.delete(`http://localhost:8080/bus/${plateNo}`, {
                    headers: {
                        'Authorization': `Bearer ${authKey}`
                    }
                });
                fetchBuses(); // Refresh the bus list after deletion
            } catch (error) {
                console.error('Error deleting bus:', error);
                setError('Otobüs silinirken bir hata oluştu.');
            }
        } else {
            console.warn('Auth key not found in localStorage.');
        }
    };

    const formatBusType = (busType) => {
        switch (busType) {
            case '2s1':
                return '2+1';
            case '2s2':
                return '2+2';
            default:
                return busType;
        }
    };

    return (
        <div className={styles.dashboardContainer}>
            {/* Sidebar */}
            <Sidebar />

            {/* Main content */}
            <Container className={styles.mainContent}>
                <Card className={styles.infoCard}>
                    <Card.Body>
                        <h2>Otobüs Bilgileri</h2>
                        <Button onClick={handleShowAddBusModal} className={styles.addButton}>Yeni Otobüs Ekle</Button>
                        {loading ? (
                            <div className={styles.spinnerWrapper}>
                                <Spinner animation="border" />
                            </div>
                        ) : error ? (
                            <Alert variant="danger">{error}</Alert>
                        ) : (
                            <Table striped bordered hover className={styles.table}>
                                <thead>
                                    <tr>
                                        <th>#</th> {/* Sequential Index */}
                                        <th>Plaka No</th>
                                        <th>Şirket ID</th>
                                        <th>Otobüs Tipi</th>
                                        <th>Koltuk Sayısı</th>
                                        <th>İşlemler</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {buses.length > 0 ? (
                                        buses.map((bus, index) => (
                                            <tr key={bus.id}>
                                                <td>{index + 1}</td> {/* Sequential Index */}
                                                <td>{bus.plateNo}</td>
                                                <td>{bus.companyId}</td>
                                                <td>{formatBusType(bus.busType)}</td> {/* Formatted Bus Type */}
                                                <td>{bus.seatNo}</td>
                                                <td>
                                                    <Button
                                                        variant="danger"
                                                        onClick={() => handleDeleteBus(bus.plateNo)}
                                                    >
                                                        Sil
                                                    </Button>
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="6">Otobüs bulunamadı.</td>
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        )}
                    </Card.Body>
                </Card>

                {/* AddBusModal */}
                <AddBusModal show={showAddBusModal} handleClose={handleCloseAddBusModal} />
            </Container>
        </div>
    );
};

export default OtobüsBilgileri;
