import React, { useState, useEffect } from 'react';
import { Accordion, Card, Button } from 'react-bootstrap'; // Import Bootstrap components
import styles from './ActiveTrips.module.css';
import Header from '../components/Header';

const ActiveTrips = () => {
    const [trips, setTrips] = useState([]);

    const statusColors = {
        ACTIVE: 'lightblue',
        PAST: '#fdb913',
        CANCELED: 'lightcoral'
    };

    const sampleTrips = [
        {
            id: 1,
            status: 'ACTIVE',
            destination: 'İstanbul',
            departureTime: '2024-08-01T15:00:00Z'
        },
        {
            id: 2,
            status: 'PAST',
            destination: 'Ankara',
            departureTime: '2024-07-20T10:00:00Z'
        },
        {
            id: 3,
            status: 'CANCELED',
            destination: 'İzmir',
            departureTime: '2024-08-10T12:00:00Z'
        }
    ];

    useEffect(() => {
        const fetchTrips = async () => {
            try {
                const data = sampleTrips;
                const sortedTrips = data.sort((a, b) => new Date(b.departureTime) - new Date(a.departureTime));
                setTrips(sortedTrips);
            } catch (error) {
                console.error('Seyahatleri alırken bir hata oluştu:', error);
            }
        };

        fetchTrips();
    }, []);

    const handleSeatSelect = (seat, gender) => {
        // Handle the seat selection and gender here
        console.log(`Selected Seat: ${seat}, Gender: ${gender}`);
    };

    return (
        <div className={styles.activeTripsContainer}>
            <Header />
            <div className={styles.card}>
                <h1 className={styles.cardHeader}>Aktif Seyahatlerim</h1>
                <Accordion defaultActiveKey="0">
                    {trips
                        .filter(trip => trip.status === 'ACTIVE')
                        .map(trip => (
                            <Card key={trip.id}>
                                <Accordion.Item eventKey={trip.id.toString()}>
                                    <Accordion.Header>
                                        {trip.destination} - {new Date(trip.departureTime).toLocaleString()}
                                    </Accordion.Header>
                                    <Accordion.Body>
                                        <div style={{ backgroundColor: statusColors[trip.status], padding: '10px' }}>
                                            <p><strong>Varış Yeri:</strong> {trip.destination}</p>
                                            <p><strong>Ayrılış Zamanı:</strong> {new Date(trip.departureTime).toLocaleString()}</p>
                                            <Button 
                                                variant="primary"
                                                onClick={() => handleSeatSelect('A1', 'Male')} // Example seat and gender
                                            >
                                                Koltuk Seç
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
