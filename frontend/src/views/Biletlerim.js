import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from './Biletlerim.module.css'; // Import the CSS module
import Header from '../components/Header';

const Biletlerim = () => {
    const [tickets, setTickets] = useState([]);

    useEffect(() => {
        const fetchTickets = async () => {
            try {
                const authKey = localStorage.getItem('authKey');
                const response = await axios.get('http://localhost:8080/tickets', {
                    headers: {
                        'Authorization': `Bearer ${authKey}` // Add the authKey to the Authorization header
                    }
                });
                setTickets(response.data);
            } catch (error) {
                console.error('Error fetching tickets:', error);
            }
        };

        fetchTickets();
    }, []);

    return (
        <div className={styles.biletlerimContainer}>
            <Header /> {/* Your custom header */}
            <div className={styles.cardContainer}>
                {tickets.map(ticket => (
                    <div key={ticket.id} className={styles.card}>
                        <div className={styles.cardLeft}>
                            <header className={styles.header}>
                                <span className={styles.logo}>Tixly</span>
                            </header>
                            <main>
                                <div className={styles.barcode}>
                                    <img src="https://www.freepnglogos.com/uploads/barcode-png/barcode-laser-code-vector-graphic-pixabay-3.png" alt="Barkod" />
                                </div>
                                <section className={styles.mainDetails}>
                                    <div className={styles.centered}>
                                        <p><span className={styles.heading}>Nereden</span><br />{ticket.from}</p> &#8594;
                                        <p><span className={styles.heading}>Nereye</span><br />{ticket.to}</p>
                                        <p><span className={styles.heading}>Tarih</span><br />{new Date(ticket.checkoutDate).toLocaleDateString()}</p>
                                    </div>
                                    <div className={styles.flightDetails}>
                                        <p><span className={styles.heading}>Seyahat</span><br />{ticket.tripId}</p>
                                        <p><span className={styles.heading}>Kalkış</span><br />{new Date(ticket.checkoutDate).toLocaleTimeString()}</p>
                                        <p><span className={styles.heading}>Koltuk</span><br />{ticket.seatId}</p>
                                    </div>
                                    <h5>Kalkıştan 20 dakika önce peronda olunuz.</h5>
                                </section>
                            </main> 
                        </div>
                        <div className={styles.cardRight}>
                            <header className={styles.header}>
                                <span className={styles.logo}>Tixly</span>
                            </header>
                            <main className={styles.main}>
                                <div className={styles.centered}>
                                    <p><span className={styles.heading}>Yolcu</span><br />Customer {ticket.customerId}</p>
                                    <p><span className={styles.heading}>Koltuk</span><br />{ticket.seatId}</p>
                                </div>
                                <div className={styles.centered}>
                                    <p><span className={styles.heading}>Nereden</span><br />{ticket.from}</p> &#8594;
                                    <p><span className={styles.heading}>Nereye</span><br />{ticket.to}</p>
                                </div>
                                <div className={styles.centered}>
                                    <p><span className={styles.heading}>Seyahat</span><br />{ticket.tripId}</p>
                                    <p><span className={styles.heading}>Tarih</span><br />{new Date(ticket.checkoutDate).toLocaleDateString()}</p>
                                </div>
                                <div>
                                <p><span className={styles.heading}>Fatura Kimliği</span><br />{ticket.invoiceId}</p>
                                </div>
                            </main>
                        </div>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Biletlerim;
