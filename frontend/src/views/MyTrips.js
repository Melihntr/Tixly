import React, { useState, useEffect } from 'react';
import styles from './MyTrips.module.css'; // Import the CSS module
import Header from '../components/Header'; // Assuming Header is in this path

const MyTrips = () => {
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

    return (
        <div className={styles.myTripsContainer}>
            <Header /> {/* Your custom header */}
            <div className={styles.card}>
                <h1 className={styles.cardHeader}>Seyahatlerim</h1>
                <ul className={styles.tripList}>
                    {trips.map(trip => (
                        <li
                            key={trip.id}
                            className={styles.tripItem}
                            style={{ backgroundColor: statusColors[trip.status] }}
                        >
                            <div className={styles.tripStatus}>
                                {trip.status === 'ACTIVE' && (
                                    <span className={styles.activeStar}>★</span>
                                )}
                                <span className={styles.statusLabel}>
                                    {trip.status === 'ACTIVE' && 'Aktif'}
                                    {trip.status === 'PAST' && 'Geçmiş'}
                                    {trip.status === 'CANCELED' && 'İptal Edildi'}
                                </span>
                            </div>
                            <div>
                                <strong>Varış Yeri:</strong> {trip.destination}
                                <br />
                                <strong>Ayrılış Zamanı:</strong> {new Date(trip.departureTime).toLocaleString()}
                            </div>
                        </li>
                    ))}
                </ul>
            </div>
        </div>
    );
};

export default MyTrips;