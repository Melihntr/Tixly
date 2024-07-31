import React, { useState, useEffect } from 'react';
import { Accordion, Card, Button, Form } from 'react-bootstrap'; // Import Bootstrap components
import styles from './ActiveTrips.module.css';
import Header from '../components/Header';
import axios from 'axios'; // Import axios for HTTP requests

const ActiveTrips = () => {
    const [trips, setTrips] = useState([]);
    const [selectedTrip, setSelectedTrip] = useState(null);
    const [selectedSeat, setSelectedSeat] = useState('');
    const [selectedGender, setSelectedGender] = useState('Male');
    
    const statusColors = {
        ACTIVE: 'lightblue',
        PAST: '#fdb913',
        CANCELED: 'lightcoral'
    };

    // Sample trips data
    const sampleTrips = [
        {
            id: 1,
            status: 'ACTIVE',
            destination: 'İstanbul',
            departureTime: '2024-08-01T15:00:00Z',
            from: 'Ankara',
            to: 'İstanbul'
        },
        {
            id: 2,
            status: 'ACTIVE',
            destination: 'Ankara',
            departureTime: '2024-07-20T10:00:00Z',
            from: 'İstanbul',
            to: 'Ankara'
        },
        {
            id: 3,
            status: 'ACTIVE',
            destination: 'İzmir',
            departureTime: '2024-08-10T12:00:00Z',
            from: 'Bursa',
            to: 'İzmir'
        }
    ];

    useEffect(() => {
        const fetchTrips = async () => {
            try {
                // Replace this with actual API call in production
                const data = sampleTrips;
                const sortedTrips = data.sort((a, b) => new Date(b.departureTime) - new Date(a.departureTime));
                setTrips(sortedTrips);
            } catch (error) {
                console.error('Seyahatleri alırken bir hata oluştu:', error);
            }
        };

        fetchTrips();
    }, []);

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
            const response = await axios.post('http://localhost:8080/tickets/add', {
                customerId: 5, // Replace with actual customer ID from auth context
                tripId: selectedTrip.id,
                from: selectedTrip.from,
                to: selectedTrip.to,
                seatId: 1,
                seatId: selectedSeat,
                printDate: new Date().toISOString(),
                checkoutDate: new Date().toISOString(),
                purchaseDate: new Date().toISOString(),
                invoiceId: 'c9eb0f7b-4d6c-41c5-9c12-3c6e9d63b3d0' // Replace with actual invoice ID
            });

            alert('Ticket successfully purchased!');
            // Reset state or update UI as needed
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
                        .filter(trip => trip.status === 'ACTIVE')
                        .map(trip => (
                            <Card key={trip.id}>
                                <Accordion.Item eventKey={trip.id.toString()}>
                                    <Accordion.Header onClick={() => handleTripSelect(trip)}>
                                        {trip.destination} - {new Date(trip.departureTime).toLocaleString()}
                                    </Accordion.Header>
                                    <Accordion.Body>
                                        <div style={{ backgroundColor: statusColors[trip.status], padding: '10px' }}>
                                            <p><strong>Varış Yeri:</strong> {trip.destination}</p>
                                            <p><strong>Ayrılış Zamanı:</strong> {new Date(trip.departureTime).toLocaleString()}</p>
                                            <p><strong>Nereden:</strong> {trip.from}</p>
                                            <p><strong>Nereye:</strong> {trip.to}</p>
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
