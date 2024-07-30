import React from 'react';
import './Biletlerim.module.css'; // Import your CSS file

const Biletlerim = () => {
    return (
        <div className="body">
            <div className="button">
                <input type="button" value="Rumple / Revert" />
            </div>
            <div className="ticket">
                <div className="ticket__head">
                    <div className="ticket__head-url">
                        <h1>otobusticket.com</h1>
                    </div>
                    <div className="ticket__head-holo">
                        <span>OTOBÜSTICKET.COM</span>
                    </div>
                </div>
                <div className="ticket__body">
                    <div className="ticket__body-code">
                        <div className="barcode">
                            <div className="code">
                                <span>AB1234</span>
                            </div>
                        </div>
                        <div className="logo"></div>
                    </div>
                    <div className="ticket__body-data">
                        <div className="data-title">
                            <span className="small">OTOBÜS BİLETİ</span>
                            <span className="big">İSTANBUL - ANKARA</span>
                            <span className="small">SEYAHAT TARİHİ</span>
                        </div>
                        <div className="data-event">
                            <span className="big">GÜN: CUMA</span>
                            <span className="small">TARİH: 01.09.2024</span>
                        </div>
                        <div className="data-date">
                            <span className="big">SAAT: 15:00</span>
                        </div>
                        <div className="data-reservation">
                            <div className="reservation-seat">
                                <span className="small">Koltuk No</span>
                                <span className="big">32A</span>
                            </div>
                            <div className="reservation-bus">
                                <span className="small">Otobüs No</span>
                                <span className="big">1234</span>
                            </div>
                        </div>
                        <div className="data-price">
                            <div className="price-tag">
                                <span className="big">₺250,00</span>
                            </div>
                            <div className="price-cat">
                                <span className="small">Standart Bilet</span>
                            </div>
                        </div>
                    </div>
                    <div className="ticket__body-logo">
                        <div className="logo"></div>
                    </div>
                </div>
            </div>
            {/* Example Inputs */}
            <div className="example-tickets">
                <div className="ticket">
                    <div className="ticket__head">
                        <div className="ticket__head-url">
                            <h1>otobusticket.com</h1>
                        </div>
                        <div className="ticket__head-holo">
                            <span>OTOBÜSTICKET.COM</span>
                        </div>
                    </div>
                    <div className="ticket__body">
                        <div className="ticket__body-code">
                            <div className="barcode">
                                <div className="code">
                                    <span>CD5678</span>
                                </div>
                            </div>
                            <div className="logo"></div>
                        </div>
                        <div className="ticket__body-data">
                            <div className="data-title">
                                <span className="small">OTOBÜS BİLETİ</span>
                                <span className="big">İSTANBUL - İZMİR</span>
                                <span className="small">SEYAHAT TARİHİ</span>
                            </div>
                            <div className="data-event">
                                <span className="big">GÜN: PAZARTESİ</span>
                                <span className="small">TARİH: 04.09.2024</span>
                            </div>
                            <div className="data-date">
                                <span className="big">SAAT: 10:00</span>
                            </div>
                            <div className="data-reservation">
                                <div className="reservation-seat">
                                    <span className="small">Koltuk No</span>
                                    <span className="big">15B</span>
                                </div>
                                <div className="reservation-bus">
                                    <span className="small">Otobüs No</span>
                                    <span className="big">5678</span>
                                </div>
                            </div>
                            <div className="data-price">
                                <div className="price-tag">
                                    <span className="big">₺180,00</span>
                                </div>
                                <div className="price-cat">
                                    <span className="small">Standart Bilet</span>
                                </div>
                            </div>
                        </div>
                        <div className="ticket__body-logo">
                            <div className="logo"></div>
                        </div>
                    </div>
                </div>

                <div className="ticket">
                    <div className="ticket__head">
                        <div className="ticket__head-url">
                            <h1>otobusticket.com</h1>
                        </div>
                        <div className="ticket__head-holo">
                            <span>OTOBÜSTICKET.COM</span>
                        </div>
                    </div>
                    <div className="ticket__body">
                        <div className="ticket__body-code">
                            <div className="barcode">
                                <div className="code">
                                    <span>EF9012</span>
                                </div>
                            </div>
                            <div className="logo"></div>
                        </div>
                        <div className="ticket__body-data">
                            <div className="data-title">
                                <span className="small">OTOBÜS BİLETİ</span>
                                <span className="big">ANKARA - ANTALYA</span>
                                <span className="small">SEYAHAT TARİHİ</span>
                            </div>
                            <div className="data-event">
                                <span className="big">GÜN: PERŞEMBE</span>
                                <span className="small">TARİH: 07.09.2024</span>
                            </div>
                            <div className="data-date">
                                <span className="big">SAAT: 08:00</span>
                            </div>
                            <div className="data-reservation">
                                <div className="reservation-seat">
                                    <span className="small">Koltuk No</span>
                                    <span className="big">22C</span>
                                </div>
                                <div className="reservation-bus">
                                    <span className="small">Otobüs No</span>
                                    <span className="big">9012</span>
                                </div>
                            </div>
                            <div className="data-price">
                                <div className="price-tag">
                                    <span className="big">₺220,00</span>
                                </div>
                                <div className="price-cat">
                                    <span className="small">Standart Bilet</span>
                                </div>
                            </div>
                        </div>
                        <div className="ticket__body-logo">
                            <div className="logo"></div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Biletlerim;
