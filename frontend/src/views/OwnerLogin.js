import React, { useState } from 'react';
import axios from 'axios';
import { Form, Button, Container, Alert } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { loginSuccess, loginFailure } from '../components/authSlice';
import { useNavigate } from 'react-router-dom'; // Import useNavigate
import styles from './OwnerLogin.module.css'; // If you have custom styles

const OwnerLogin = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const dispatch = useDispatch();
    const authStatus = useSelector((state) => state.auth.status);
    const navigate = useNavigate(); // Initialize useNavigate

    const handleLogin = async (e) => {
        e.preventDefault();

        try {
            const response = await axios.post('http://localhost:8080/admin/login', {
                username,
                password
            }, {
                headers: {
                    'Content-Type': 'application/json'
                }
            });

            if (response.status === 200) {
                dispatch(loginSuccess({
                    authKey: response.data.authKey,
                    username
                }));
                setError('');
                navigate('/owner-dashboard'); // Redirect to the dashboard
            } else {
                dispatch(loginFailure('Login failed. Please check your credentials.'));
                setError('Login failed. Please check your credentials.');
            }
        } catch (error) {
            console.error('Login error:', error);
            dispatch(loginFailure('An error occurred. Please try again later.'));
            setError('An error occurred. Please try again later.');
        }
    };

    return (
        <Container className={styles.container}>
            <h2>Owner Login</h2>
            {authStatus === 'succeeded' && <Alert variant="success">Login successful!</Alert>}
            {error && <Alert variant="danger">{error}</Alert>}
            <Form onSubmit={handleLogin}>
                <Form.Group controlId="formUsername">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Enter username"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </Form.Group>
                <Form.Group controlId="formPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        value={password}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                </Form.Group>
                <Button variant="primary" type="submit" className={styles.button}>
                    Login
                </Button>
            </Form>
        </Container>
    );
};

export default OwnerLogin;
