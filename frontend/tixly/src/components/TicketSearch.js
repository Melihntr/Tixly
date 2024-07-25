import React from 'react';
import { Card, Form, Row, Col, Button } from 'react-bootstrap';
import styles from './TicketSearch.module.css'; // Import custom CSS module

const TicketSearch = () => (
    <Row className="justify-content-md-center">
        <Col md={9}>
            <Card style={{ marginBottom: '20px' }}>
                <Card.Body>
                    <Card.Title>Bilet Arama</Card.Title>
                    <Form>
                        <Row>
                            <Col>
                                <Form.Group controlId="from">
                                    <Form.Label>Nereden</Form.Label>
                                    <Form.Control type="text" placeholder="Kalkış yerini girin" />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="to">
                                    <Form.Label>Nereye</Form.Label>
                                    <Form.Control type="text" placeholder="Varış yerini girin" />
                                </Form.Group>
                            </Col>
                            <Col>
                                <Form.Group controlId="date">
                                    <Form.Label>Tarih</Form.Label>
                                    <Form.Control type="date" />
                                </Form.Group>
                            </Col>
                            <Col style={{ display: 'flex', alignItems: 'flex-end' }}>
                                <Button variant="primary" type="submit" className={styles.customButton}>Ara</Button>
                            </Col>
                        </Row>
                    </Form>
                </Card.Body>
            </Card>
        </Col>
    </Row>
);

export default TicketSearch;
