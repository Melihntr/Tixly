// ActiveTrips.js
import React, { useState, useEffect } from 'react';
import { Accordion, Card, Button, Form } from 'react-bootstrap';
import styles from './ActiveTrips.module.css';
import Header from '../components/Header';
import axios from 'axios';
import { useLocation } from 'react-router-dom';

const ActiveTrips = () => {
    const [trips, setTrips] = useState([]);
    const [selectedTrip, setSelectedTrip] = useState(null);
    const [selectedSeat, setSelectedSeat] = useState('');
    const [selectedGender, setSelectedGender] = useState('Male');
    const location = useLocation(); // Use location object to get state

    const { state } = location; // Destructure the state
    const departureLocation = state?.departureLocation || '';
    const arrivalLocation = state?.arrivalLocation || '';

    const statusColors = {
        ACTIVE: 'lightblue',
        PAST: '#fdb913',
        CANCELED: 'lightcoral'
    };

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

    const handleSeatSelect = (seat) => {
        setSelectedSeat(seat);
    };

    const handleGenderChange = (event) => {
        setSelectedGender(event.target.value);
    };

    const handleTripSelect = (trip) => {
        setSelectedTrip(trip);
    };

    const handlePurchase = async () => {
        if (!selectedTrip || !selectedSeat) {
            alert('Please select a trip and seat.');
            return;
        }

        try {
            const authKey = localStorage.getItem('authKey'); // Retrieve the authKey from localStorage
    
            const response = await axios.post('http://localhost:8080/tickets/add', {
                tripId: selectedTrip.id,
                from: selectedTrip.departureLocationId,
                to: selectedTrip.arrivalLocationId,
                seatId: selectedSeat,
                printDate: new Date().toISOString(),
                checkoutDate: new Date(selectedTrip.departureTime).toISOString(),
                purchaseDate: new Date().toISOString(),
            }, {
                headers: {
                    'Authorization': `Bearer ${authKey}` // Add the authKey to the Authorization header
                }
            });

            alert('Ticket successfully purchased!');
            setSelectedTrip(null);
            setSelectedSeat('');
        } catch (error) {
            console.error('Failed to purchase ticket:', error);
            alert('Failed to purchase ticket.');
        }
    };

    return (
        <div className={styles.activeTripsContainer}>
            <Header />
            <div className={styles.card}>
                <h1 className={styles.cardHeader}>Aktif Seyahatler</h1>
                <Accordion defaultActiveKey="0">
                    {trips
                        .filter(trip => trip.state === 'Aktif')
                        .map(trip => (
                            <Card key={trip.id}>
                                <Accordion.Item eventKey={trip.id.toString()}>
                                    <Accordion.Header onClick={() => handleTripSelect(trip)}>
                                        {trip.departureLocationId} - {trip.arrivalLocationId} - {new Date(trip.departureTime).toLocaleString()}
                                    </Accordion.Header>
                                    <Accordion.Body>
                                        <div style={{ backgroundColor: statusColors[trip.state], padding: '10px' }}>
                                            <p><strong>Varış Yeri:</strong> {trip.arrivalLocationId}</p>
                                            <p><strong>Ayrılış Zamanı:</strong> {new Date(trip.departureTime).toLocaleString()}</p>
                                            <p><strong>Kalkış Yeri:</strong> {trip.departureLocationId}</p>
                                            <p><strong>Fiyat:</strong> {trip.price} TL</p> {/* Added price display */}
                                            <Form.Group controlId="formSeat">
                                                <Form.Label>Koltuk Seç</Form.Label>
                                                <Form.Control
                                                    type="text"
                                                    placeholder="Koltuk Numarası"
                                                    value={selectedSeat}
                                                    onChange={(e) => handleSeatSelect(e.target.value)}
                                                />
                                            </Form.Group>
                                            <Form.Group controlId="formGender">
                                                <Form.Label>Cinsiyet</Form.Label>
                                                <Form.Control as="select" value={selectedGender} onChange={handleGenderChange}>
                                                    <option value="Male">Erkek</option>
                                                    <option value="Female">Kadın</option>
                                                </Form.Control>
                                            </Form.Group>
                                            <Button 
                                                variant="primary"
                                                onClick={handlePurchase}
                                            >
                                                Satın al
                                            </Button>
                                        </div>
                                    </Accordion.Body>
                                </Accordion.Item>
                            </Card>
                        ))}
                </Accordion>
            </div>
        </div>
    );
};

export default ActiveTrips;
