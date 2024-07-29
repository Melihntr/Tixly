import React, { useState } from 'react';
import axios from 'axios';

const ResetPassword = () => {
    const [username, setUsername] = useState('');
    const [verificationCode, setVerificationCode] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [error, setError] = useState('');
    const [message, setMessage] = useState('');

    const handleResetPassword = async (e) => {
        e.preventDefault();
        try {
            const response = await axios.post('/auth/reset-password', {
                username,
                verificationCode,
                newPassword,
            });
            console.log('Password reset successful:', response.data);
            setMessage('Password reset successful. Please login with your new password.');
        } catch (err) {
            setError('Password reset failed. Please check your inputs and try again.');
        }
    };
 
    
    return (
        <div className="reset-password-container">
            <h2>Reset Password</h2>
            <form onSubmit={handleResetPassword}>
                <div>
                    <label>Username:</label>
                    <input
                        type="text"
                        value={username}
                        onChange={(e) => setUsername(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>Verification Code:</label>
                    <input
                        type="text"
                        value={verificationCode}
                        onChange={(e) => setVerificationCode(e.target.value)}
                        required
                    />
                </div>
                <div>
                    <label>New Password:</label>
                    <input
                        type="password"
                        value={newPassword}
                        onChange={(e) => setNewPassword(e.target.value)}
                        required
                    />
                </div>
                {error && <p className="error">{error}</p>}
                {message && <p className="message">{message}</p>}
                <button type="submit">Reset Password</button>
            </form>
        </div>
    );
};

export default ResetPassword;