import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom'; // Import Link and useNavigate from react-router-dom
import { Nav } from 'react-bootstrap'; // Import Nav from react-bootstrap
import axios from 'axios';
import styles from './Sidebar.module.css'; // Import the CSS module
import GetCompanyModal from './GetCompanyModal'; // Import the GetCompanyModal component

const Sidebar = () => {
    const [showCompanyModal, setShowCompanyModal] = useState(false);
    const navigate = useNavigate(); // Initialize useNavigate for redirection

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
        <div className={styles.sidebar}>
            <Link to="/owner-dashboard" className={styles.logoLink}>
                <div className={styles.logoText}>Tixly</div>
            </Link>
            
            <Nav className="flex-column">
                <Nav.Link onClick={() => setShowCompanyModal(true)} className={`${styles.navLink} ${styles.navLinkTop}`}>
                    Şirket Bilgileri
                </Nav.Link>
                <Nav.Link as={Link} to="/trips-management" className={`${styles.navLink} ${styles.navLinkMiddle}`}>
                    Sefer Bilgileri
                </Nav.Link>
                <Nav.Link as={Link} to="/otobus-bilgileri" className={`${styles.navLink} ${styles.navLinkMiddle}`}>
                    Otobüs Bilgileri
                </Nav.Link>
                <Nav.Link onClick={handleLogout} className={`${styles.navLink} ${styles.navLinkBottom}`}>
                    Çıkış Yap
                </Nav.Link>
            </Nav>

            {/* GetCompanyModal */}
            <GetCompanyModal showModal={showCompanyModal} handleClose={() => setShowCompanyModal(false)} />
        </div>
    );
};

export default Sidebar;
