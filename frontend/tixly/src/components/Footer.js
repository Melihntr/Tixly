import React from 'react';
import styles from './Footer.module.css';
import { Container, Row, Col } from 'react-bootstrap';


function Footer() {
    return (
        <footer className={styles.footer}> {/* Apply the CSS module class */}

            <Container>
                <Row>
                    <Col>
                        <p>&copy; 2024 Tixly. All rights reserved.</p>
                    </Col>
                </Row>
            </Container>
        </footer>
    );
}

export default Footer;

