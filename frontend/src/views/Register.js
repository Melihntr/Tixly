import React, { useState } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './Register.module.css'; // Import the CSS module
import VerificationModal from '../components/VerificationModal'; // Import the VerificationModal

const Register = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [email, setEmail] = useState('');
    const [gender, setGender] = useState('Male');
    const [tcNo, setTcNo] = useState('');
    const [phoneNumber, setPhoneNumber] = useState('');
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');
    const [showVerificationModal, setShowVerificationModal] = useState(false); // State for modal visibility
    const navigate = useNavigate();

    const handleRegister = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('http://localhost:8080/register', {
                username,
                password,
                mail: email,
                gender,
                tcNo,
                phoneNumber,
            });

            if (response.status === 201) {
                // Extract username and authKey from response
                const { authKey } = response.data;

                // Store username and authKey in localStorage
                localStorage.setItem('authKey', authKey);
                localStorage.setItem('username', username);

                setMessage('Registration successful. Please verify your account.');
                setShowVerificationModal(true); // Show the verification modal
            }
        } catch (err) {
            setError('Registration failed. Please try again.');
        }
    };

    const handleCloseVerificationModal = () => {
        setShowVerificationModal(false);
        navigate('/'); // Optionally redirect to the main page
    };

    return (
        <div className={styles.container}>
            <header className={styles.header}>
                <span className={styles.logo} onClick={() => navigate('/')}>Tixly</span>
            </header>
            <div className={styles.card}>
                <h1 className={styles.title}>Create Account</h1>
                <form onSubmit={handleRegister}>
                    <div className={styles.formGroup}>
                        <label>Username:</label>
                        <input
                            type="text"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Password:</label>
                        <input
                            type="password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Email:</label>
                        <input
                            type="email"
                            value={email}
                            onChange={(e) => setEmail(e.target.value)}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Gender:</label>
                        <select
                            value={gender}
                            onChange={(e) => setGender(e.target.value)}
                            required
                        >
                            <option value="Male">Male</option>
                            <option value="Female">Female</option>
                        </select>
                    </div>
                    <div className={styles.formGroup}>
                        <label>TC Number:</label>
                        <input
                            type="text"
                            value={tcNo}
                            onChange={(e) => setTcNo(e.target.value)}
                            required
                        />
                    </div>
                    <div className={styles.formGroup}>
                        <label>Phone Number:</label>
                        <input
                            type="text"
                            value={phoneNumber}
                            onChange={(e) => setPhoneNumber(e.target.value)}
                            required
                        />
                    </div>
                    {error && <p className={styles.error}>{error}</p>}
                    {message && <p className={styles.message}>{message}</p>}
                    <button type="submit" className={styles.submitButton}>Register</button>
                </form>
            </div>

            {/* VerificationModal */}
            {showVerificationModal && <VerificationModal onClose={handleCloseVerificationModal} />}
        </div>
    );
};

export default Register;
