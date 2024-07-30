import React, { useState, useEffect } from 'react';
import { Button, Dropdown } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import axios from 'axios'; // Import axios
import LoginModal from './LoginModal'; // Import the LoginModal component
import styles from './Header.module.css';

const Header = () => {
    const [showModal, setShowModal] = useState(false);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [loggedInUser, setLoggedInUser] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        const authKey = localStorage.getItem('authKey');
        if (authKey) {
            const storedUsername = localStorage.getItem('username');
            if (storedUsername) {
                setLoggedInUser(storedUsername);
            }
        }
    }, []);

    const handleShow = () => setShowModal(true);
    const handleClose = () => setShowModal(false);

    const handleLogin = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/auth/login', {
                username,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json',
                }
            });
            const data = response.data;
            if (response.status === 200) {
                localStorage.setItem('authKey', data.authKey);
                localStorage.setItem('username', username);
                setLoggedInUser(username);
                handleClose();
            } else {
                alert(data.message);
            }
        } catch (error) {
            console.error('Error:', error);
            if (error.response && error.response.data) {
                alert(error.response.data.message);
            } else {
                alert('Bir hata oluştu.');
            }
        }
    };

    const handleLogout = async () => {
        const authKey = localStorage.getItem('authKey');
        if (!authKey) return;

        try {
            await axios.post('http://localhost:8080/auth/logout', {}, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${authKey}`,
                },
            });

            localStorage.removeItem('authKey');
            localStorage.removeItem('username');
            setLoggedInUser('');
            navigate('/');
        } catch (error) {
            console.error('Error:', error);
        }
    };

    const handleNavClick = (path) => {
        if (!loggedInUser) {
            handleShow();
        } else {
            navigate(path);
        }
    };

    return (
        <header className={styles.header}>
            <div className={styles.logo}>Tixly</div>
            <nav className={styles.navMenu}>
                <a href onClick={() => handleNavClick('/myTrips')} className={styles.navItem}>Seyahatlerim</a>
                &nbsp;&nbsp;
                <a href onClick={() => handleNavClick('/biletlerim')} className={styles.navItem}>Biletlerim</a>
                &nbsp;&nbsp;
                <a href onClick={() => handleNavClick('/hesap')} className={styles.navItem}>Hesap</a>
            </nav>
            <div className={styles.headerActions}>
                {loggedInUser ? (
                    <Dropdown>
                        <Dropdown.Toggle variant="success" id="dropdown-basic" className={styles.dropdownToggle}>
                            {loggedInUser}
                        </Dropdown.Toggle>

                        <Dropdown.Menu>
                            <Dropdown.Item onClick={handleLogout}>Çıkış Yap</Dropdown.Item>
                        </Dropdown.Menu>
                    </Dropdown>
                ) : (
                    <Button variant="primary" onClick={handleShow} className={styles.loginButton}>
                        Giriş Yap
                    </Button>
                )}
            </div>

            <LoginModal
                showModal={showModal}
                handleClose={handleClose}
                handleLogin={handleLogin}
                username={username}
                setUsername={setUsername}
                password={password}
                setPassword={setPassword}
            />
        </header>
    );
};

export default Header;
