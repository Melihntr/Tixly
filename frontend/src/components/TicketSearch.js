// TicketSearch.js
import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom'; 
import axios from 'axios';
import styles from './TicketSearch.module.css';

const TicketSearch = () => {
    const navigate = useNavigate();

    const [provinces, setProvinces] = useState([]);
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');
    const [date, setDate] = useState('');

    useEffect(() => {
        fetchProvinces();
        setDate(new Date().toISOString().split('T')[0]);
    }, []);

    const fetchProvinces = async () => {
        try {
            const response = await axios.get('http://localhost:8080/location/provinces');
            setProvinces(response.data.map(province => ({
                id: province.id,
                name: province.il_adi
            })));
        } catch (error) {
            console.error('Error fetching provinces:', error);
        }
    };

    const handleFromChange = (event) => setFrom(event.target.value);
    const handleToChange = (event) => setTo(event.target.value);

    const handleSubmit = (event) => {
        event.preventDefault();
        navigate('/trips', {
            state: { departureLocation: from, arrivalLocation: to }
        });
    };

    return (
        <Row className="justify-content-md-center">
            <Col md={9}>
                <Card style={{ marginBottom: '20px' }}>
                    <Card.Body>
                        <Card.Title>Bilet Arama</Card.Title>
                        <Form onSubmit={handleSubmit}>
                            <Row>
                                <Col>
                                    <Form.Group controlId="from">
                                        <Form.Label>Nereden</Form.Label>
                                        <Form.Control
                                            as="select"
                                            value={from}
                                            onChange={handleFromChange}
                                        >
                                            <option value="">Kalkış yerini seçin</option>
                                            {provinces.length > 0 ? (
                                                provinces.map((province) => (
                                                    <option key={province.id} value={province.name}>
                                                        {province.name}
                                                    </option>
                                                ))
                                            ) : (
                                                <option value="">Loading...</option>
                                            )}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="to">
                                        <Form.Label>Nereye</Form.Label>
                                        <Form.Control
                                            as="select"
                                            value={to}
                                            onChange={handleToChange}
                                        >
                                            <option value="">Varış yerini seçin</option>
                                            {provinces.length > 0 ? (
                                                provinces.map((province) => (
                                                    <option key={province.id} value={province.name}>
                                                        {province.name}
                                                    </option>
                                                ))
                                            ) : (
                                                <option value="">Loading...</option>
                                            )}
                                        </Form.Control>
                                    </Form.Group>
                                </Col>
                                <Col>
                                    <Form.Group controlId="date">
                                        <Form.Label>Tarih</Form.Label>
                                        <Form.Control
                                            type="date"
                                            defaultValue={date}
                                            value={date}
                                            onChange={(e) => setDate(e.target.value)}
                                        />
                                    </Form.Group>
                                </Col>
                                <Col style={{ display: 'flex', alignItems: 'flex-end' }}>
                                    <Button
                                        variant="primary"
                                        type="submit"
                                        className={styles.customButton}
                                    >
                                        Ara
                                    </Button>
                                </Col>
                            </Row>
                        </Form>
                    </Card.Body>
                </Card>
            </Col>
        </Row>
    );
};

export default TicketSearch;
