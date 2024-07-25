import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';
import Slider from 'react-slick';

const sliderSettings = {
    dots: true,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    autoplay: true,
    autoplaySpeed: 2000,
};

const Partners = () => (
    <Row className="justify-content-md-center" style={{ marginBottom: '40px' }}>
        <Col md={9}>
            <Card style={{ padding: '20px' }}>
                <Card.Body>
                    <Card.Title>Ortaklarımız</Card.Title>
                    <Slider {...sliderSettings}>
                        <div>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNpsDgQduK1B32N6PZfZ71fK8oRqNzMQJ7fA&s" alt="Company 1" style={{ width: '60px', height: '30px' }} />
                        </div>
                        <div>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrewaAFPf4OkTcce3hiNDjmjVfIqYlDOjrBQ&s" alt="Company 2" style={{ width: '60px', height: '30px' }} />
                        </div>
                        <div>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkFA0BUiJ2GnflPMAil_qbsx8iLoWFXiz3kw&s" alt="Company 3" style={{ width: '60px', height: '30px' }} />
                        </div>
                        <div>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiEctlWZDaTopnz75xN8EtBL1_nhQHujb0Xg&s" alt="Company 4" style={{ width: '60px', height: '30px' }} />
                        </div>
                        <div>
                            <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIpoYbwob_Uzmmjw54W5Hc4rN-crvrG-oJZw&s" alt="Company 5" style={{ width: '60px', height: '30px' }} />
                        </div>
                        {/* Add more company logos as needed */}
                    </Slider>
                </Card.Body>
            </Card>
        </Col>
    </Row>
);

export default Partners;
