import React, { useState } from 'react';
import axios from 'axios';
import styles from './VerificationModal.module.css'; // Ensure this path is correct

const VerificationModal = ({ onClose }) => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [message, setMessage] = useState('');
  const [error, setError] = useState('');
  const [codeSent, setCodeSent] = useState(false); // Track if the code has been sent

  const handleSendCode = async () => {
    try {
      if (!username || !email) {
        setError('Kullanıcı adı ve e-posta gereklidir.');
        return;
      }

      await axios.post('http://localhost:8080/reset/send-code', {
        username,
        mail: email,
      });
      setMessage('Doğrulama kodu gönderildi.');
      setCodeSent(true); // Set the state to show verification form
    } catch (err) {
      setError('Kod gönderme başarısız.');
    }
  };

  const handleVerifyCode = async (e) => {
    e.preventDefault();
    try {
      await axios.post('http://localhost:8080/register/verifyCode', {
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
        <h2 className={styles.modalTitle}>Hesap Doğrulama</h2>   
        {!codeSent ? (
          <div>
            <div className={styles.formGroup}>
              <label htmlFor="username">Kullanıcı Adı:</label>
              <input
                id="username"
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                placeholder="Kullanıcı adınızı girin"
                required
              />
            </div>
            <div className={styles.formGroup}>
              <label htmlFor="email">E-posta:</label>
              <input
                id="email"
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                placeholder="E-posta adresinizi girin"
                required
              />
            </div>
            <button className={styles.sendCodeButton} onClick={handleSendCode}>
              Kod Gönder
            </button>
          </div>
        ) : (
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
        )}
      </div>
    </div>
  );
};

export default VerificationModal;
