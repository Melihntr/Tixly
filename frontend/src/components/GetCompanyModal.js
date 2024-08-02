import React, { useState, useEffect } from 'react';
import { Modal, Button, Alert } from 'react-bootstrap';
import axios from 'axios';
import styles from './GetCompanyModal.module.css'; // Import the CSS module

const GetCompanyModal = ({ showModal, handleClose }) => {
    const [company, setCompany] = useState(null);
    const [error, setError] = useState('');
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        if (showModal) {
            fetchCompany();
        }
    }, [showModal]);

    const fetchCompany = async () => {
        setLoading(true);
        setError('');
        const authKey = localStorage.getItem('authKey');

        if (!authKey) {
            setError('Giriş yapılmış bir oturum bulunamadı.');
            setLoading(false);
            return;
        }

        try {
            const response = await axios.get('http://localhost:8080/company', {
                headers: {
                    'Authorization': `Bearer ${authKey}`
                }
            });

            if (response.status === 200) {
                setCompany(response.data);
                setError('');
            } else {
                setError('Şirket bilgileri alınamadı.');
            }
        } catch (error) {
            console.error('Şirket bilgilerini alırken bir hata oluştu:', error);
            setError('Şirket bilgilerini alırken bir hata oluştu. Lütfen tekrar deneyin.');
        } finally {
            setLoading(false);
        }
    };

    return (
        <Modal show={showModal} onHide={handleClose} size="lg">
            <Modal.Header closeButton className={styles.modalHeader}>
                <Modal.Title className={styles.modalTitle}>Şirket Bilgileri</Modal.Title>
            </Modal.Header>
            <Modal.Body className={styles.modalBody}>
                {loading && <p>Yükleniyor...</p>}
                {error && <Alert variant="danger" className={styles.alertError}>{error}</Alert>}
                {company && (
                    <div>
                        <p><strong>Şirket Adı:</strong> {company.name}</p>
                        <p><strong>Şirket No:</strong> {company.companyNo}</p>
                        <p><strong>Web Sitesi:</strong> <a href={company.website} target="_blank" rel="noopener noreferrer">{company.website}</a></p>
                        <p><strong>İletişim Telefonu:</strong> {company.contactPhone || 'N/A'}</p>
                        <p><strong>İletişim E-Postası:</strong> {company.contactEmail || 'N/A'}</p>
                        <p><strong>Logo:</strong></p>
                        <img
                            src={company.logoUrl}
                            alt="Şirket Logosu"
                            className={styles.modalImage} // Apply CSS class for image
                        />
                    </div>
                )}
            </Modal.Body>
            <Modal.Footer className={styles.modalFooter}>
                <Button variant="secondary" onClick={handleClose}>
                    Kapat
                </Button>
            </Modal.Footer>
        </Modal>
    );
};

export default GetCompanyModal;
