import React from 'react';
import { Modal, Form, Button } from 'react-bootstrap';
import styles from './LoginModal.module.css'; // Import your CSS module for LoginModal

const LoginModal = ({ showModal, handleClose, handleLogin, username, setUsername, password, setPassword }) => {
    return (
        <Modal show={showModal} onHide={handleClose}>
            <Modal.Header closeButton>
                <Modal.Title>Login</Modal.Title>
            </Modal.Header>
            <Modal.Body>
                <Form onSubmit={handleLogin}>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label>Username</Form.Label>
                        <Form.Control
                            type="text"
                            placeholder="Enter username"
                            value={username}
                            onChange={(e) => setUsername(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <Form.Group controlId="formBasicPassword">
                        <Form.Label>Password</Form.Label>
                        <Form.Control
                            type="password"
                            placeholder="Password"
                            value={password}
                            onChange={(e) => setPassword(e.target.value)}
                            required
                        />
                    </Form.Group>
                    <div className={styles['button-container']}>
                        <Button variant="primary" type="submit">
                            Login
                        </Button>
                    </div>
                </Form>
                <div className="text-center mt-3">
                    <a href="/forgot-password">Şifreni mi Unuttun?</a><br />
                    <a href="/register">Kayıt Ol</a><br />
                    <a href="/owner-login">İşletme Girişi</a>
                </div>
            </Modal.Body>
        </Modal>
    );
};

export default LoginModal;
