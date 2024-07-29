// src/components/AuthButton.js
import React from 'react';
import { useSelector } from 'react-redux';
import { Dropdown } from 'react-bootstrap';
import { useDispatch } from 'react-redux';
import { clearAuth } from './authSlice';
import styles from './Header.module.css';

const AuthButton = () => {
  const dispatch = useDispatch();
  const username = useSelector((state) => state.auth.username);

  const handleLogout = () => {
    dispatch(clearAuth());
    localStorage.removeItem('authKey');
    localStorage.removeItem('username');
  };

  return username ? (
    <Dropdown>
      <Dropdown.Toggle variant="success" id="dropdown-basic" className={styles.dropdownToggle}>
        {username}
      </Dropdown.Toggle>
      <Dropdown.Menu>
        <Dropdown.Item onClick={handleLogout}>Logout</Dropdown.Item>
      </Dropdown.Menu>
    </Dropdown>
  ) : (
    <Button variant="primary" onClick={() => setShowModal(true)} className={styles.loginButton}>
      Giriş Yap
    </Button>
  );
};

export default AuthButton