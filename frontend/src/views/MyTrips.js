import React, { useEffect, useState } from 'react';
import { ListGroup } from 'react-bootstrap';
import styles from './MyTrips.module.css'; // Özel stilleri ekle

const MyTrips = () => {
    const [trips, setTrips] = useState([]);

    // Duruma göre renkleri tanımla
    const statusColors = {
        ACTIVE: 'lightblue',   // Aktif seyahat için açık mavi
        PAST: '#fdb913',   // Geçmiş seyahat için açık sarı
        CANCELED: 'lightcoral' // İptal edilen seyahat için açık mercan rengi
    };

    // Örnek veri
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
        // Seyahatleri almak için simüle et
        const fetchTrips = async () => {
            try {
                // Simüle edilmiş veri kullanımı
                const data = sampleTrips;
                // Seyahatleri ayrılış zamanına göre azalan sırada sırala
                const sortedTrips = data.sort((a, b) => new Date(b.departureTime) - new Date(a.departureTime));
                setTrips(sortedTrips);
            } catch (error) {
                console.error('Seyahatleri alırken bir hata oluştu:', error);
            }
        };

        fetchTrips();
    }, []);

    return (
        <div>
            <h1>Seyahatlerim</h1>
            <ListGroup>
                {trips.map(trip => (
                    <ListGroup.Item
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
                    </ListGroup.Item>
                ))}
            </ListGroup>
        </div>
    );
};

export default MyTrips;
