import React, { useState } from 'react';
import { Container, Form, Button, Alert, Card } from 'react-bootstrap';
import { useDispatch, useSelector } from 'react-redux';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import styles from './OwnerLogin.module.css'; // Import your CSS module
import { loginSuccess, loginFailure } from '../components/authSlice'; // Update the import path as necessary

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
                // Store the authKey in localStorage
                localStorage.setItem('authKey', response.data.authKey);

                dispatch(loginSuccess({
                    authKey: response.data.authKey,
                    username
                }));
                setError('');
                navigate('/owner-dashboard'); // Redirect to the dashboard
            } else {
                dispatch(loginFailure('Giriş başarısız oldu. Lütfen bilgilerinizi kontrol edin.'));
                setError('Giriş başarısız oldu. Lütfen bilgilerinizi kontrol edin.');
            }
        } catch (error) {
            console.error('Giriş hatası:', error);
            dispatch(loginFailure('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.'));
            setError('Bir hata oluştu. Lütfen daha sonra tekrar deneyin.');
        }
    };

    return (
        <div>
            <header className={styles.header}>
                <a href="/" className={styles.logo}>Tixly</a>
            </header>
            <Container className={styles.container}>
                <Card className={styles.card}>
                    <Card.Body>
                        <h2 className={styles.cardTitle}>Yönetici Girişi</h2>
                        {authStatus === 'succeeded' && <Alert variant="success">Giriş başarılı!</Alert>}
                        {error && <Alert variant="danger">{error}</Alert>}
                        <Form onSubmit={handleLogin}>
                            <Form.Group controlId="formUsername">
                                <Form.Label>Kullanıcı Adı</Form.Label>
                                <Form.Control
                                    type="text"
                                    placeholder="Kullanıcı adınızı girin"
                                    value={username}
                                    onChange={(e) => setUsername(e.target.value)}
                                    required
                                />
                            </Form.Group>
                            <Form.Group controlId="formPassword">
                                <Form.Label>Şifre</Form.Label>
                                <Form.Control
                                    type="password"
                                    placeholder="Şifrenizi girin"
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                    required
                                />
                            </Form.Group>
                            <div className={styles.buttonContainer}>
                                <Button variant="warning" type="submit" className={styles.button}>
                                    Giriş Yap
                                </Button>
                            </div>
                        </Form>
                    </Card.Body>
                </Card>
            </Container>
        </div>
    );
};

export default OwnerLogin;
