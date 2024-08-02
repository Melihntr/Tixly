import React, { useState } from 'react';
import { Modal, Button, Form } from 'react-bootstrap';
import axios from 'axios';
import styles from './DeleteBusModal.module.css'; // Create a CSS module if needed

const DeleteBusModal = ({ show, handleClose }) => {
    const [plateNo, setPlateNo] = useState('');

    const handleDelete = async () => {
        const authKey = localStorage.getItem('authKey');

        if (!authKey) {
            console.warn('Auth key not found in localStorage.');
            return;
        }

        try {
            await axios.delete(`http://localhost:8080/bus/${plateNo}`, {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });

            alert('Otobüs başarıyla silindi.');
            handleClose();
        } catch (error) {
            console.error('Otobüs silme hatası:', error);
        }
    };

    return (
        <Modal show={show} onHide={handleClose} className={styles.modal}>
            <Modal.Header closeButton className={styles['modal-header']}>
                <Modal.Title className={styles['modal-title']}>Otobüs Sil</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles['modal-body']}>
                <Form.Group controlId="formPlateNo">
                    <Form.Label>Plaka No</Form.Label>
                    <Form.Control
                        type="text"
                        placeholder="Plaka No girin"
                        value={plateNo}
                        onChange={(e) => setPlateNo(e.target.value)}
                        className={styles['form-control']}
                    />
                </Form.Group>
            </Modal.Body>
            <Modal.Footer className={styles['modal-footer']}>
                <Button variant="secondary" onClick={handleClose} className={styles['btn-secondary']}>
                    Kapat
                </Button>
                <Button variant="danger" onClick={handleDelete} className={styles['btn-danger']}>
                    Sil
                </Button>
            </Modal.Footer>
        </Modal>

    );
};

export default DeleteBusModal;
