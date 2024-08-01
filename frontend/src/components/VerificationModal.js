import React, { useState } from 'react';
import axios from 'axios';
import styles from './VerificationModal.module.css'; // Create and use a CSS module for modal styling

const VerificationModal = ({ onClose, userInfo }) => {
  const [verificationCode, setVerificationCode] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');

  const handleSendCode = async () => {
    try {
      // Ensure userInfo has the required properties
      if (!userInfo?.username || !userInfo?.email) {
        setError('Username and email are required.');
        return;
      }

      await axios.post('http://localhost:8080/reset/send-code', {
        username: userInfo.username,
        mail: userInfo.email,
      });
      setMessage('Doğrulama kodu gönderildi.');
    } catch (err) {
      setError('Kod gönderme başarısız.');
    }
  };

  const handleVerifyCode = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/reg/verifyCode', {
        userInput: verificationCode,
      });
      setMessage('Kod başarıyla doğrulandı.');
      setTimeout(() => {
        onClose(); // Close modal after success
        window.location.reload(); // Optionally reload the page
      }, 2000);
    } catch (err) {
      setError('Kod doğrulama başarısız.');
    }
  };

  return (
    <div className={styles.modal}>
      <div className={styles.modalContent}>
        <button className={styles.closeButton} onClick={onClose}>X</button>
        <h3>Hesap Doğrulama</h3>
        <button className={styles.sendCodeButton} onClick={handleSendCode}>
          Kod Gönder
        </button>
        <form onSubmit={handleVerifyCode}>
          <div className={styles.formGroup}>
            <label>Doğrulama Kodu:</label>
            <input
              type="text"
              value={verificationCode}
              onChange={(e) => setVerificationCode(e.target.value)}
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
