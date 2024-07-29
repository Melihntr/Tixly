import React from 'react';
import { Card, Row, Col } from 'react-bootstrap';

const Features = () => (
    <Row className="justify-content-md-center">
        <Col md={9}>
            <Card>
                <Card.Body>
                    <Row>
                        <Col md={3}>
                            <Card className="text-center">
                                <Card.Body>
                                    <Card.Title>7/24 Müşteri Hizmetleri</Card.Title>
                                    <Card.Text>
                                        Tixly.com ve Tixly mobil uygulamaları üzerinden yapacağınız tüm işlemlerde müşteri hizmetleri ekibimiz 7/24 yanınızda. Bir tıkla destek ekibimize bağlanabilirsiniz.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={3}>
                            <Card className="text-center">
                                <Card.Body>
                                    <Card.Title>Güvenli Ödeme</Card.Title>
                                    <Card.Text>
                                        Tüm otobüs bilet alım işlemlerinizi ister evinizden, ister ofisinizden ya da dilerseniz cep telefonunuzdan kolay, hızlı ve güvenilir bir şekilde gerçekleştirebilirsiniz.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={3}>
                            <Card className="text-center">
                                <Card.Body>
                                    <Card.Title>Bütçe Dostu</Card.Title>
                                    <Card.Text>
                                        Tixly size tüm firmaların otobüs biletlerini sorgulama ve karşılaştırma imkanı sunar. Bu sayede bütçenize uygun otobüs biletini rahatlıkla bulabilir ve satın alabilirsiniz.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                        <Col md={3}>
                            <Card className="text-center">
                                <Card.Body>
                                    <Card.Title>Seçkin Firmalar</Card.Title>
                                    <Card.Text>
                                        Tixly olarak seçkin otobüs firmalarını sizler için bir araya getirdik. Firmaların otobüs biletlerini Tixly'de karşılaştırabilir, uygun otobüs biletini bulabilir ve çevrimiçi bir şekilde satın alabilirsiniz.
                                    </Card.Text>
                                </Card.Body>
                            </Card>
                        </Col>
                    </Row>
                </Card.Body>
            </Card>
        </Col>
    </Row>
);

export default Features;
