import React, { useEffect, useState } from 'react';
import styles from './UserProfile.module.css'; // Ensure you are using CSS modules

const UserProfile = () => {
    const [userInfo, setUserInfo] = useState(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);

    useEffect(() => {
        const fetchUserInfo = async () => {
            try {
                const response = await fetch('http://localhost:8080/user', {
                    method: 'GET',
                    headers: {
                        'Authorization': `Bearer ${localStorage.getItem('authToken')}`,
                        'Content-Type': 'application/json'
                    }
                });
                
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }

                const data = await response.json();
                setUserInfo(data);
            } catch (error) {
                setError(error.message);
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
            <p><strong>Name:</strong> {userInfo?.name}</p>
            <p><strong>Gender:</strong> {userInfo?.gender}</p>
            <p><strong>Email:</strong> {userInfo?.email}</p>
            <p><strong>Phone Number:</strong> {userInfo?.phoneNumber}</p>
            <p><strong>TCKN:</strong> {userInfo?.tcNo}</p>
        </div>
    );
};

export default UserProfile;