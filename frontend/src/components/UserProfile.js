import React, { useEffect, useState } from 'react';
import axios from 'axios';
import styles from './UserProfile.module.css'; // Ensure you are using CSS modules

const UserProfile = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await axios.get('http://localhost:8080/account', {
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
                        'Content-Type': 'application/json'
                    }
                });

                setUserInfo(response.data);
            } catch (error) {
                setError(error.response?.data?.message || 'Error fetching user info');
                console.error('Error fetching user info:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchUserInfo();
    }, []);

    if (loading) {
        return <div className={styles.loading}>Loading...</div>;
    }

    if (error) {
        return <div className={styles.error}>Error: {error}</div>;
    }

    return (
        <div className={styles.userProfile}>
            <h2>Hesap Detayları</h2>
            <p><strong>Email:</strong> {userInfo?.email}</p>
            <p><strong>Gender:</strong> {userInfo?.gender}</p>
            <p><strong>Account Status:</strong> {userInfo?.accountStatus}</p>
            <p><strong>Phone Number:</strong> {userInfo?.phoneNumber}</p>
            <p><strong>TCKN:</strong> {userInfo?.tcNo}</p>
        </div>
    );
};

export default UserProfile;
