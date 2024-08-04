import React, { useState, useEffect } from 'react';
import { Container, Card, Button, Table, Spinner, Alert } from 'react-bootstrap';
import axios from 'axios';
import AddTripModal from '../components/AddTripModal';
import styles from './TripsPage.module.css'; // Import the CSS module
import Sidebar from '../components/Sidebar';

const TripsPage = () => {
    const [trips, setTrips] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [showAddTripModal, setShowAddTripModal] = useState(false);
    const [tripToCancel, setTripToCancel] = useState(null);

    useEffect(() => {
        fetchTrips();
    }, []);

    const fetchTrips = async () => {
        const authKey = localStorage.getItem('authKey');

        try {
            const response = await axios.get('http://localhost:8080/trip/company', {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });
            setTrips(response.data);
        } catch (error) {
            console.error('Error fetching trips:', error);
            setError('Seyahat bilgileri getirilemedi.');
        } finally {
            setLoading(false);
        }
    };

    const handleShowAddTripModal = () => setShowAddTripModal(true);
    const handleCloseAddTripModal = () => {
        setShowAddTripModal(false);
        fetchTrips(); // Refresh the trips list when closing the modal
    };

    const handleCancelTrip = async (tripId) => {
        const authKey = localStorage.getItem('authKey');
        if (!authKey) {
            console.warn('Auth key not found in localStorage.');
            return;
        }
        try {
            await axios.post(`http://localhost:8080/trip/${tripId}`, {}, {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });
            setTripToCancel(null);
            fetchTrips(); // Refresh the trips list after cancellation
        } catch (error) {
            console.error('Error cancelling trip:', error);
            setError('Seyahat iptali sırasında bir hata oluştu. Lütfen tekrar deneyin.');
        }
    };

    return (
        <div className={styles.dashboardContainer}>
            <Sidebar handleShowGetCompanyModal={() => { /* Handle this if needed */ }} handleLogout={() => { /* Handle logout if needed */ }} />
            <Container className={styles.container}>
                <Card className={styles.infoCard}>
                    <Card.Body>
                        <h2>Seyahat Bilgileri</h2>
                        <Button onClick={handleShowAddTripModal} className={styles.addButton}>
                            Yeni Seyahat Ekle
                        </Button>
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
                                        <th>Peron No</th>
                                        <th>Kalkış Yeri</th>
                                        <th>Varış Yeri</th>
                                        <th>Tahmini Süre (dakika)</th>
                                        <th>Fiyat</th>
                                        <th>Otobüs ID</th>
                                        <th>Kalkış Zamanı</th>
                                        <th>Durum</th> {/* Status Column */}
                                        <th>İşlemler</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {trips.length > 0 ? (
                                        trips.map((trip, index) => (
                                            <tr key={trip.id}>
                                                <td>{index + 1}</td> {/* Sequential Index */}
                                                <td>{trip.peronNo}</td>
                                                <td>{trip.departureLocationId}</td>
                                                <td>{trip.arrivalLocationId}</td>
                                                <td>{trip.estimatedTime}</td>
                                                <td>{trip.price}</td>
                                                <td>{trip.busId}</td>
                                                <td>{trip.departureTime}</td>
                                                <td>{trip.state}</td> {/* Status Display */}
                                                <td>
                                                    <Button
                                                        variant="danger"
                                                        onClick={() => handleCancelTrip(trip.id)}
                                                    >
                                                        İptal Et
                                                    </Button>
                                                </td>
                                            </tr>
                                        ))
                                    ) : (
                                        <tr>
                                            <td colSpan="10">Seyahat bulunamadı.</td> {/* Adjust colSpan to 10 */}
                                        </tr>
                                    )}
                                </tbody>
                            </Table>
                        )}
                    </Card.Body>
                </Card>

                {/* AddTripModal */}
                <AddTripModal show={showAddTripModal} handleClose={handleCloseAddTripModal} />
            </Container>
        </div>
    );
};

export default TripsPage;
