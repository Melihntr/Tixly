import React from 'react';
import { Dropdown } from 'react-bootstrap';
import styles from './Header.module.css'; // Ensure you import your CSS module

const DropdownMenu = ({ loggedInUser, handleLogout }) => {
    return (
        <Dropdown>
            <Dropdown.Toggle variant="success" id="dropdown-basic" className={styles.dropdownToggle}>
                {loggedInUser}
            </Dropdown.Toggle>

            <Dropdown.Menu className={styles.dropdownMenu}>
                <Dropdown.Item className={styles.dropdownItem} onClick={handleLogout}>
                    Logout
                </Dropdown.Item>
            </Dropdown.Menu>
        </Dropdown>
    );
};

export default DropdownMenu;
