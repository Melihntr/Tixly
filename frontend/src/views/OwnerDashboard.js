import React, { useState } from 'react';
import { Container, Nav } from 'react-bootstrap';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import AddBusModal from '../components/AddBusModal';
import AddTripModal from '../components/AddTripModal';
import CancelTripModal from '../components/CancelTripModal'; // Import CancelTripModal
import DeleteBusModal from '../components/DeleteBusModal'; // Import DeleteBusModal
import styles from './OwnerDashboard.module.css'; // Import CSS module

const OwnerDashboard = () => {
    const [showBusModal, setShowBusModal] = useState(false);
    const [showTripModal, setShowTripModal] = useState(false);
    const [showCancelTripModal, setShowCancelTripModal] = useState(false); // State for CancelTripModal
    const [showDeleteBusModal, setShowDeleteBusModal] = useState(false); // State for DeleteBusModal
    const navigate = useNavigate();

    const handleShowBusModal = () => setShowBusModal(true);
    const handleCloseBusModal = () => setShowBusModal(false);

    const handleShowTripModal = () => setShowTripModal(true);
    const handleCloseTripModal = () => setShowTripModal(false);

    const handleShowCancelTripModal = () => setShowCancelTripModal(true); // Show CancelTripModal
    const handleCloseCancelTripModal = () => setShowCancelTripModal(false); // Close CancelTripModal

    const handleShowDeleteBusModal = () => setShowDeleteBusModal(true); // Show DeleteBusModal
    const handleCloseDeleteBusModal = () => setShowDeleteBusModal(false); // Close DeleteBusModal

    const handleLogout = async () => {
        const authKey = localStorage.getItem('authKey');

        if (authKey) {
            try {
                await axios.post('http://localhost:8080/admin/logout', {}, {
                    headers: {
                        'Authorization': `Bearer ${authKey}`
                    }
                });

                localStorage.removeItem('authKey');
                navigate('/owner-login');
            } catch (error) {
                console.error('Çıkış yapma hatası:', error);
            }
        } else {
            console.warn('Auth key not found in localStorage.');
        }
    };

    return (
        <div className={styles.dashboardContainer}>
            {/* Sidebar */}
            <div className={styles.sidebar}>
                <Link to="/" className={styles.logoLink}>
                    <div className={styles.logoText}>Tixly</div> 
                </Link>
                
                <Nav className="flex-column">
                    <Nav.Link href="#companyInfo" className={`${styles.navLink} ${styles.navLinkTop}`}>Şirket Bilgileri</Nav.Link>
                    <Nav.Link href="#tripInfo" className={`${styles.navLink} ${styles.navLinkMiddle}`}>Sefer Bilgileri</Nav.Link>
                    <Nav.Link href="#busInfo" className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Bilgileri</Nav.Link>
                    <Nav.Link onClick={handleShowBusModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Ekle</Nav.Link>
                    <Nav.Link onClick={handleShowTripModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Seyahat Ekle</Nav.Link>
                    <Nav.Link onClick={handleShowDeleteBusModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Sil</Nav.Link> {/* Add Otobüs Sil link */}
                    <Nav.Link onClick={handleShowCancelTripModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Seyahat İptali</Nav.Link> {/* Add Seyahat İptali link */}
                    <Nav.Link onClick={handleLogout} className={`${styles.navLink} ${styles.navLinkBottom}`}>Çıkış Yap</Nav.Link>
                </Nav>
            </div>

            {/* Main content */}
            <Container className={styles.mainContent}>
                <h2>Yönetici Panosu</h2>
                <p>Yönetici Panosuna Hoşgeldiniz!</p>

                <AddBusModal show={showBusModal} handleClose={handleCloseBusModal} />
                <AddTripModal show={showTripModal} handleClose={handleCloseTripModal} />
                <CancelTripModal show={showCancelTripModal} handleClose={handleCloseCancelTripModal} /> {/* Add CancelTripModal */}
                <DeleteBusModal show={showDeleteBusModal} handleClose={handleCloseDeleteBusModal} /> {/* Add DeleteBusModal */}
            </Container>
        </div>
    );
};

export default OwnerDashboard;
