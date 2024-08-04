import React, { useState, useEffect } from 'react';
import { Accordion, Card, Button, Form } from 'react-bootstrap';
import styles from './ActiveTrips.module.css';
import Header from '../components/Header';
import axios from 'axios';
import { useLocation } from 'react-router-dom';
import BusSeatingComponent from '../components/BusSeatingComponent'; // Import the BusSeatingComponent

const ActiveTrips = () => {
    const [trips, setTrips] = useState([
        {
            "id": 35,
            "peronNo": "P1231",
            "departureLocationId": "Adana",
            "arrivalLocationId": "Ankara",
            "estimatedTime": 300,
            "price": 30,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-31T15:50:00",
            "state": "Aktif"
        },
        {
            "id": 23,
            "peronNo": "PN123",
            "departureLocationId": "Ýstanbul",
            "arrivalLocationId": "Ankara",
            "estimatedTime": 300,
            "price": 150,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-01T15:00:00",
            "state": "Aktif"
        },
        {
            "id": 24,
            "peronNo": "PN001",
            "departureLocationId": "Ýstanbul",
            "arrivalLocationId": "Ankara",
            "estimatedTime": 450,
            "price": 180,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-01T09:00:00",
            "state": "Aktif"
        },
        {
            "id": 25,
            "peronNo": "PN002",
            "departureLocationId": "Ýstanbul",
            "arrivalLocationId": "Ýzmir",
            "estimatedTime": 600,
            "price": 220,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-02T11:00:00",
            "state": "Aktif"
        },
        {
            "id": 26,
            "peronNo": "PN003",
            "departureLocationId": "Ankara",
            "arrivalLocationId": "Ýzmir",
            "estimatedTime": 540,
            "price": 200,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-03T13:00:00",
            "state": "Aktif"
        },
        {
            "id": 27,
            "peronNo": "PN004",
            "departureLocationId": "Ýstanbul",
            "arrivalLocationId": "Bursa",
            "estimatedTime": 150,
            "price": 90,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-04T10:00:00",
            "state": "Aktif"
        },
        {
            "id": 28,
            "peronNo": "PN005",
            "departureLocationId": "Ýzmir",
            "arrivalLocationId": "Antalya",
            "estimatedTime": 720,
            "price": 250,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-05T15:00:00",
            "state": "Aktif"
        },
        {
            "id": 29,
            "peronNo": "PN006",
            "departureLocationId": "Ankara",
            "arrivalLocationId": "Antalya",
            "estimatedTime": 660,
            "price": 230,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-06T08:00:00",
            "state": "Aktif"
        },
        {
            "id": 30,
            "peronNo": "PN007",
            "departureLocationId": "Ýstanbul",
            "arrivalLocationId": "Adana",
            "estimatedTime": 830,
            "price": 270,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-07T16:00:00",
            "state": "Aktif"
        },
        {
            "id": 33,
            "peronNo": "PN010",
            "departureLocationId": "Ankara",
            "arrivalLocationId": "Adana",
            "estimatedTime": 790,
            "price": 260,
            "companyId": 1,
            "busId": 1,
            "departureTime": "2024-08-10T14:00:00",
            "state": "Aktif"
        }
    ]);
    const [selectedTrip, setSelectedTrip] = useState(null);
    const [selectedSeat, setSelectedSeat] = useState('');
    const [selectedGender, setSelectedGender] = useState('Male');
    const location = useLocation(); // Use location object to get state

    const { state } = location; // Destructure the state
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
                                            
                                            <div className='mt-3'>
                                                <BusSeatingComponent
                                                    busType={trip.busType} // Pass the bus type from the trip object
                                                    selectedSeat={selectedSeat}
                                                    selectedGender={selectedGender}
                                                    
                                                    onSeatSelect={setSelectedSeat}
                                                    onGenderSelect={setSelectedGender}
                                                    // Directly pass the setter function
                                                />
                                            </div>

                                            <Button className='mt-3' variant="primary" onClick={() => {/* Handle purchase */}}>
                                                Satın al
                                            </Button>
                                        </div>
                                    )}
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
