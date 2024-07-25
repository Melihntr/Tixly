import React from 'react';
import { Accordion, Card, Row, Col } from 'react-bootstrap'; 

const FAQ = () => (
    <Row className="justify-content-center" style={{ marginBottom: '40px' }}>
        <Col md={9}>
            <Card style={{ padding: '20px' }}>
                <Card.Body>
                    <h2>SSS</h2>
                    <Accordion defaultActiveKey="0">
                        <Accordion.Item eventKey="0">
                            <Accordion.Header>Tixly nedir?</Accordion.Header>
                            <Accordion.Body>
                                Tixly,bir bilet alım platformudur.
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="1">
                            <Accordion.Header>Bilet nasıl alırım?</Accordion.Header>
                            <Accordion.Body>
                                Bilet almak için hesabınıza giriş yapabilir ve etkinlikler bölümüne giderek biletlerinizi satın alabilirsiniz.
                            </Accordion.Body>
                        </Accordion.Item>
                        <Accordion.Item eventKey="2">
                            <Accordion.Header>İade alabilir miyim?</Accordion.Header>
                            <Accordion.Body>
                                İade işlemleri etkinlik düzenleyicisinin politikalarına tabidir. Daha fazla bilgi için destek ile iletişime geçebilirsiniz.
                            </Accordion.Body>
                        </Accordion.Item>
                    </Accordion>
                </Card.Body>
            </Card>
        </Col>
    </Row>
);

export default FAQ;

