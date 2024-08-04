import React, { useState, useEffect } from 'react';
import { Accordion, Card, Button, Modal, Form } from 'react-bootstrap';
import styles from './ActiveTrips.module.css';
import Header from '../components/Header';
import axios from 'axios';
import { useLocation, useNavigate } from 'react-router-dom';
import BusSeatingComponent from '../components/BusSeatingComponent';

const ActiveTrips = () => {
    const [trips, setTrips] = useState([]);
    const [selectedTrip, setSelectedTrip] = useState(null);
    const [selectedSeat, setSelectedSeat] = useState(null);
    const [showModal, setShowModal] = useState(false);
    const [selectedGender, setSelectedGender] = useState('');
    const [errorMessage, setErrorMessage] = useState('');
    const location = useLocation();
    const navigate = useNavigate();
    const { state } = location;
    const departureLocation = state?.departureLocation || '';
    const arrivalLocation = state?.arrivalLocation || '';

    useEffect(() => {
        const fetchTrips = async () => {
            try {
                const response = await axios.get('http://localhost:8080/trip/active', {
                    params: {
                        departureLocation: departureLocation,
                        arrivalLocation: arrivalLocation
                    }
                });
                const sortedTrips = response.data.sort((a, b) => new Date(b.departureTime) - new Date(a.departureTime));
                setTrips(sortedTrips);
            } catch (error) {
                console.error('Seyahatleri alırken bir hata oluştu:', error);
            }
        };

        fetchTrips();
    }, [departureLocation, arrivalLocation]);

    const handleTripSelect = (trip) => {
        setSelectedTrip(trip);
        setSelectedSeat(null); // Reset selected seat when a new trip is selected
    };

    const handleSeatSelect = (seatLabel) => {
        setSelectedSeat(seatLabel);
        setErrorMessage(''); // Clear any previous error messages
    };

    const handleBuyClick = () => {
        if (selectedSeat) {
            setShowModal(true);
        } else {
            alert("Lütfen önce bir koltuk seçin.");
        }
    };

    const handleModalClose = () => setShowModal(false);

    const handleModalConfirm = async () => {
        const authKey = localStorage.getItem('authKey');
        if (!authKey) {
            alert("Kimlik doğrulama anahtarı eksik.");
            return;
        }
    
        const ticketData = {
            tripId: selectedTrip.id,
            from: selectedTrip.departureLocationId,
            to: selectedTrip.arrivalLocationId,
            seatId: selectedSeat,
            printDate: new Date().toISOString(),
            checkoutDate: new Date().toISOString(),
            purchaseDate: new Date().toISOString(),
            gender: selectedGender
        };
    
        try {
            const response = await fetch('http://localhost:8080/tickets/add', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authKey}`
                },
                body: JSON.stringify(ticketData)
            });
    
            // Read response as text
            const responseText = await response.text();
    
            // Try to parse as JSON
            let responseData;
            try {
                responseData = JSON.parse(responseText);
            } catch (e) {
                // If parsing fails, assume it's a text error message
                responseData = { message: responseText };
            }
    
            if (!response.ok) {
                if (responseData.message.includes("koltuk doludur")) {
                    setErrorMessage("Bu koltuk doludur, lütfen başka koltuk seçin.");
                } else {
                    throw new Error(responseData.message || 'Server error');
                }
            } else {
                alert('Bilet satın alındı!');
                handleModalClose();
                navigate('/biletlerim#'); // Redirect after successful purchase
            }
        } catch (error) {
            console.error('Error:', error);
            alert(`Bilet satın alırken bir hata oluştu: ${error.message}`);
        }
    };

    return (
        <div className={styles.activeTripsContainer}>
            <Header />
            <div className={styles.card}>
                <h1 className={styles.cardHeader}>Aktif Seyahatler</h1>
                <Accordion defaultActiveKey="0">
                    {trips.map(trip => (
                        <Card key={trip.id}>
                            <Accordion.Item eventKey={trip.id.toString()}>
                                <Accordion.Header onClick={() => handleTripSelect(trip)}>
                                    {trip.departureLocationId} - {trip.arrivalLocationId} - {new Date(trip.departureTime).toLocaleString()}
                                </Accordion.Header>
                                <Accordion.Body className={styles.accordionBody}>
                                    {selectedTrip && selectedTrip.id === trip.id && (
                                        <div style={{ padding: '10px' }}>
                                            <p><strong>Varış Yeri:</strong> {trip.arrivalLocationId}</p>
                                            <p><strong>Ayrılış Zamanı:</strong> {new Date(trip.departureTime).toLocaleString()}</p>
                                            <p><strong>Kalkış Yeri:</strong> {trip.departureLocationId}</p>
                                            <p><strong>Fiyat:</strong> {trip.price} TL</p>
                                            
                                            {/* Add Bus Type and Number of Seats */}
                                            <p><strong>Otobüs Tipi:</strong> {trip.busType === "2s1" ? "2+1" : "2+2"}</p>
                                            <p><strong>Koltuk Sayısı:</strong> {trip.seatNo}</p>
                                            
                                            <div className='mt-3'>
                                                <BusSeatingComponent
                                                    busType={trip.busType}
                                                    seatNo={trip.seatNo}
                                                    selectedSeat={selectedSeat}
                                                    onSeatSelect={handleSeatSelect}
                                                />
                                                <Button 
                                                    onClick={handleBuyClick}
                                                    className={styles.buyButton}
                                                >
                                                    Satın Al
                                                </Button>
                                            </div>
                                            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}
                                        </div>
                                    )}
                                </Accordion.Body>
                            </Accordion.Item>
                        </Card>
                    ))}
                </Accordion>
            </div>

            {/* Gender Selection Modal */}
            <Modal show={showModal} onHide={handleModalClose}>
                <Modal.Header closeButton>
                    <Modal.Title>Cinsiyet Seçimi</Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form>
                        <Form.Check 
                            type="radio" 
                            label="Erkek" 
                            value="male" 
                            checked={selectedGender === 'male'} 
                            onChange={(e) => setSelectedGender(e.target.value)} 
                        />
                        <Form.Check 
                            type="radio" 
                            label="Kadın" 
                            value="female" 
                            checked={selectedGender === 'female'} 
                            onChange={(e) => setSelectedGender(e.target.value)} 
                        />
                    </Form>
                </Modal.Body>
                <Modal.Footer>
                    <Button variant="secondary" onClick={handleModalClose}>
                        Kapat
                    </Button>
                    <Button variant="primary" onClick={handleModalConfirm}>
                        Onayla
                    </Button>
                </Modal.Footer>
            </Modal>
        </div>
    );
};

export default ActiveTrips;
