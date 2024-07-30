import React from 'react';
import styles from './Biletlerim.module.css'; // Import the CSS module
import Header from '../components/Header';


const Biletlerim = () => {
    return (
        <div className={styles.biletlerimContainer}>
            <Header /> {/* Your custom header */}
            <div className={styles.card}>
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
                                <p><span className={styles.heading}>Nereden</span><br />IST</p> &#8594;
                                <p><span className={styles.heading}>Nereye</span><br />ADA</p>
                                <p><span className={styles.heading}>Tarih</span><br />15AĞU2024</p>
                            </div>
                            <div className={styles.flightDetails}>
                                <p><span className={styles.heading}>Seyahat</span><br />TK0123</p>
                                <p><span className={styles.heading}>Kalkış</span><br />10:00</p>
                                <p><span className={styles.heading}>Peron No</span><br />G04</p>
                                <p><span className={styles.heading}>Koltuk</span><br />23A</p>
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
                            <p><span className={styles.heading}>Yolcu</span><br />Customer 5</p>
                            <p><span className={styles.heading}>Koltuk</span><br />23A</p>
                        </div>
                        <div className={styles.centered}>
                            <p><span className={styles.heading}>Nereden</span><br />İstanbul</p> &#8594;
                            <p><span className={styles.heading}>Nereye</span><br />Adana</p>
                        </div>
                        <div className={styles.centered}>
                            <p><span className={styles.heading}>Seyahat</span><br />TK0123</p>
                            <p><span className={styles.heading}>Tarih</span><br />15AĞU2024</p>
                        </div>
                    </main>
                </div>
            </div>
        </div>
    );
};

export default Biletlerim;
