import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Link } from 'react-router-dom'; // Ensure you have react-router-dom installed
import styles from './UserProfile.module.css'; // Ensure you are using CSS modules

const UserProfile = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const authKey = localStorage.getItem('authKey');
    console.log('Auth Key:', authKey);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await axios.get('http://localhost:8080/user', {
                    headers: {
                        'Authorization': `Bearer ${authKey}`,
                        'Content-Type': 'application/json'
                    }
                });

                console.log('User Info Response:', response.data); // Log the response for debugging
                setUserInfo(response.data);
            } catch (error) {
                setError(error.response?.data?.message || 'Error fetching user info');
                console.error('Error fetching user info:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo();
    }, [authKey]);

    if (loading) {
        return <div className={styles.loading}>Loading...</div>;
    }

    if (error) {
        return <div className={styles.error}>Error: {error}</div>;
    }

    return (

        <div className={styles.userProfile}>
            <h2>Hesap Detayları</h2>
            <div className={styles.infoCard}>
                <p><strong>E-Posta:</strong> {userInfo?.email}</p>
                <p><strong>Cinsiyet:</strong> {userInfo?.gender}</p>
                <p><strong>Hesap Durumu:</strong> {userInfo?.accountStatus}</p>
                <p><strong>Telefon Numarası:</strong> {userInfo?.phoneNumber}</p>
                <p><strong>TCKN:</strong> {userInfo?.tcNo}</p>
            </div>
            <div className={styles.links}>
                <Link to="/reset-password" className={styles.link}>Şifreni Yenile</Link>
                <Link to="/verify-account" className={styles.link}>Hesabı Doğrula</Link>
            </div>
        </div>
    );
};

export default UserProfile;
