import React from 'react';
import UserProfile from '../components/UserProfile';
import './Hesap.module.css'; // Import CSS module for specific styles

const Hesap = () => {
    return (
        <div className="hesap-container">
            <div className="hesap-content">
                <h1 className="hesap-title">Hesap</h1>
                <UserProfile />
            </div>
        </div>
    );
};

export default Hesap;