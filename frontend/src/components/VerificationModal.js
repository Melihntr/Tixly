import React, { useState } from 'react';
import axios from 'axios';
import styles from './VerificationModal.module.css'; // Ensure this path is correct

const VerificationModal = ({ onClose }) => {
  const [verificationCode, setVerificationCode] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleVerifyCode = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/register/verifyCode', {
        userInput: verificationCode,
      });
      setMessage('Kod başarıyla doğrulandı.');
      setTimeout(() => {
        onClose(); // Close modal after success
      }, 2000);
    } catch (err) {
      setError('Kod doğrulama başarısız.');
    }
  };

  return (
    <div className={styles.modal}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>X</button>
        <h2 className={styles.modalTitle}>Hesap Doğrulama</h2>
        <form onSubmit={handleVerifyCode}>
          <div className={styles.formGroup}>
            <label htmlFor="verificationCode">Doğrulama Kodu:</label>
            <input
              id="verificationCode"
              type="text"
              value={verificationCode}
              onChange={(e) => setVerificationCode(e.target.value)}
              placeholder="Doğrulama kodunu girin"
              required
            />
          </div>
          {error && <p className={styles.error}>{error}</p>}
          {message && <p className={styles.message}>{message}</p>}
          <button type="submit" className={styles.verifyButton}>
            Onayla
          </button>
        </form>
      </div>
    </div>
  );
};

export default VerificationModal;
