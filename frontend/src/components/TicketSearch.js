import React, { useState, useEffect } from 'react';
import { Row, Col, Card, Form, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom'; // Import useNavigate from react-router-dom
import axios from 'axios';
import styles from './TicketSearch.module.css'; // Import your styles if needed

const TicketSearch = () => {
    const navigate = useNavigate(); // Create a navigate function

    const [provinces, setProvinces] = useState([]);
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');
    const [date, setDate] = useState('');
    const [location, setLocation] = useState({
        latitude: null,
        longitude: null,
        error: null,
    });

    useEffect(() => {
        controller();
    }, []);

    const controller = async () => {
        const currentDateAsString = new Date().toISOString().split('T')[0];

        await fetchProvinces(); 
        await getLocation();
        setDate(currentDateAsString);
        console.debug(JSON.stringify(location));
    }

    const getLocation = async() => {
        if (navigator.geolocation) {
            navigator.geolocation.getCurrentPosition(
                (position) => {
                    setLocation({
                        latitude: position.coords.latitude,
                        longitude: position.coords.longitude,
                        error: null,
                    });
                },
                (error) => {
                    console.error(error);
                    setLocation({
                        latitude: null,
                        longitude: null,
                        error: error.message,
                    });
                }
            );
        } else {
            setLocation({
                latitude: null,
                longitude: null,
                error: 'Geolocation is not supported by this browser.',
            });
        }
    }

    const fetchProvinces = async () => {
        try {
            const response = await axios.get('http://localhost:8080/location/provinces');
            console.log('Provinces fetched:', response.data);
            setProvinces(response.data.map(province => ({
                id: province.id,
                name: province.il_adi
            })));
        } catch (error) {
            console.error('Error fetching provinces:', error);
        }
    };

    const handleFromChange = (event) => {
        const selectedFrom = event.target.value;
        if (selectedFrom === to) {
            setTo('');
        }
        setFrom(selectedFrom);
    };

    const handleToChange = (event) => {
        const selectedTo = event.target.value;
        if (selectedTo === from) {
            setFrom('');
        }
        setTo(selectedTo);
    };

    // Define handleNavClick function
    const handleSubmit = (event) => {
        event.preventDefault();
        // Use navigate to redirect to the '/trips' page
        navigate('/trips');
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
                                            defaultValue={new Date()}
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
