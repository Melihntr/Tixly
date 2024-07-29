import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card, Form, Row, Col, Button } from 'react-bootstrap';
import styles from './TicketSearch.module.css';

const TicketSearch = () => {
    const Statics = Object.freeze({
        
    })

    const [provinces, setProvinces] = useState([]);
    const [from, setFrom] = useState('');
    const [to, setTo] = useState('');
    const [date, setDate] = useState(Statics.DateString);
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
    const handleFromChange = (event) => {
        const selectedFrom = event.target.value;
        if (selectedFrom === to) {
            // If selected 'from' is the same as 'to', clear 'to' value
            setTo('');
        }
        setFrom(selectedFrom);
    };

    const fetchProvinces = async () => {
            
        try {
            const response = await axios.get('http://localhost:8080/location/provinces');
            console.log('Provinces fetched:', response.data); // Debug: Log the fetched data
            setProvinces(response.data); // Set fetched data to state
        } catch (error) {
            console.error('Error fetching provinces:', error);
        }
    };

    const handleToChange = (event) => {
        const selectedTo = event.target.value;
        if (selectedTo === from) {
            // If selected 'to' is the same as 'from', clear 'from' value
            setFrom('');
        }
        setTo(selectedTo);
    };

    const handleSubmit = (event) => {
        event.preventDefault();
        // Handle form submission
        console.log({ from, to, date });
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
                                    <Button variant="primary" type="submit" className={styles.customButton}>
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
