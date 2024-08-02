import React, { useState } from 'react';
import { Modal, Button, Form, Alert } from 'react-bootstrap';
import axios from 'axios';
import styles from './CancelTripModal.module.css'; // Import CSS module

const CancelTripModal = ({ show, handleClose }) => {
    const [id, setId] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');

    const handleCancel = async () => {
        const authKey = localStorage.getItem('authKey');
    
        if (!authKey) {
            console.warn('Auth key not found in localStorage.');
            return;
        }
    
        try {
            await axios.post(`http://localhost:8080/trip/${id}`, {}, {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });
    
            setSuccess('Seyahat başarıyla iptal edildi.');
            setError('');
            handleClose();
        } catch (error) {
            console.error('Seyahat iptali hatası:', error);
            setError('Seyahat iptali sırasında bir hata oluştu. Lütfen tekrar deneyin.');
            setSuccess('');
        }
    };

    return (
        <Modal show={show} onHide={handleClose} className={styles.modal}>
            <Modal.Header closeButton className={styles['modal-header']}>
                <Modal.Title className={styles['modal-title']}>Seyahat İptal</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles['modal-body']}>
                {error && <Alert variant="danger">{error}</Alert>}
                {success && <Alert variant="success">{success}</Alert>}
                <Form.Group controlId="formTripId">
                    <Form.Label>Seyahat ID</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Seyahat ID girin"
                        value={id}
                        onChange={(e) => setId(e.target.value)}
                        className={styles['form-control']}
                    />
                </Form.Group>
            </Modal.Body>
            <Modal.Footer className={styles['modal-footer']}>
                <Button variant="secondary" onClick={handleClose} className={styles['btn-secondary']}>
                    Kapat
                </Button>
                <Button variant="danger" onClick={handleCancel} className={styles['btn-danger']}>
                    İptal Et
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default CancelTripModal;
