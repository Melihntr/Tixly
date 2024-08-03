import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';
import styles from './LoginModal.module.css'; // Import your CSS module for LoginModal

const LoginModal = ({ showModal, handleClose, handleLogin, username, setUsername, password, setPassword, loginError }) => {
    const handleSubmit = async (e) => {
        e.preventDefault();
        await handleLogin();
    };

    return (
        <Modal show={showModal} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Giriş yap</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleSubmit}>
                {loginError && (
                        <div className={styles.errorBox}>
                            {loginError}
                        </div>
                    )}
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Kullanıcı Adı</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Kullanıcı adı girin"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Şifre</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Şifre girin"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>
                    
                    <div className={styles['button-container']}>
                        <Button variant="primary" type="submit" className={styles.loginButton}>
                            Giriş yap
                        </Button>
                    </div>
                </Form>
                <div className={`${styles['modal-links']} text-center mt-3`}>
                    <a href="/forgot-password">Şifreni mi Unuttun?</a><br />
                    <a href="/register">Kayıt Ol</a><br />
                    <a href="/owner-login">İşletme Girişi</a>
                </div>
            </Modal.Body>
        </Modal>
    );
};

export default LoginModal;
