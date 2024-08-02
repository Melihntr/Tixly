import React, { useState } from 'react';
import axios from 'axios';
import styles from './ForgotPassword.module.css';

const ForgotPassword = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [verificationCode, setVerificationCode] = useState('');
  const [newPassword, setNewPassword] = useState('');
  const [error, setError] = useState('');
  const [message, setMessage] = useState('');
  const [showModal, setShowModal] = useState(false);
  const [step, setStep] = useState(1);

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (step === 1) {
      try {
        await axios.post('http://localhost:8080/reset/send-code', {
          username,
          mail: email,
        });
        setStep(2); // Show modal for verification code
        setError('');
        setMessage('Verification code sent. Please check your email.');
      } catch (err) {
        setError('Failed to send verification code. Please try again.');
      }
    } else if (step === 2) {
      try {
        const response = await axios.post('http://localhost:8080/reset/password', {
          username,
          verificationCode,
          newPassword,
        });
        setMessage('Password reset successfully. Redirecting...');
        setTimeout(() => {
          // Redirect to main page or login page
          window.location.href = '/';
        }, 2000);
      } catch (err) {
        setError('Failed to reset password. Please try again.');
      }
    }
  };

  return (
    <div className={styles.container}>
      <header className={styles.header}>
        <span className={styles.logo} onClick={() => window.location.href = '/'}>
          Tixly
        </span>
      </header>
      <div className={styles.card}>
        <h2>Şifremi Unuttum</h2>
        {step === 1 ? (
          <form onSubmit={handleSubmit}>
            <div className={styles.formGroup}>
              <label>Kullanıcı Adı:</label>
              <input
                type="text"
                value={username}
                onChange={(e) => setUsername(e.target.value)}
                required
              />
            </div>
            <div className={styles.formGroup}>
              <label>Email:</label>
              <input
                type="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              />
            </div>
            {error && <p className={styles.error}>{error}</p>}
            {message && <p className={styles.message}>{message}</p>}
            <button type="submit" className={styles.submitButton}>
              Doğrulama Kodu Gönder
            </button>
          </form>
        ) : (
          <div className={styles.modal}>
            <div className={styles.modalContent}>
              <form onSubmit={handleSubmit}>
                <div className={styles.formGroup}>
                  <label className={styles.modalLabel}>Doğrulama Kodu:</label>
                  <input
                    type="text"
                    className={styles.modalInput}
                    value={verificationCode}
                    onChange={(e) => setVerificationCode(e.target.value)}
                    required
                  />
                </div>
                <div className={styles.formGroup}>
                  <label className={styles.modalLabel}>Yeni Şifre:</label>
                  <input
                    type="password"
                    className={styles.modalInput}
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                  />
                </div>
                {error && <p className={styles.error}>{error}</p>}
                {message && <p className={styles.message}>{message}</p>}
                <button type="submit" className={styles.modalButton}>
                  Şifreyi Yenile
                </button>
              </form>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default ForgotPassword;
