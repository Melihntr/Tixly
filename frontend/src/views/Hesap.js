import React from 'react';
import UserProfile from '../components/UserProfile';
import './Hesap.module.css'; // Import CSS module for specific styles
import Header from '../components/Header';
const Hesap = () => {
    return (
        <div className="hesap-container">
            <Header />
            <div className="hesap-content">
                <UserProfile />
            </div>
        </div>
    );
};

export default Hesap;