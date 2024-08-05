import React, { useState, useEffect } from 'react';
import axios from 'axios';
import styles from './BusSeatingComponent.module.css';

const SeatType = Object.freeze({
    SEAT_EMPTY: 0,
    SEAT_SELECTED: 1,
    SEAT_MAN: 2,
    SEAT_WOMEN: 3
});

const SeatImages = Object.freeze({
    [SeatType.SEAT_EMPTY]: "seat_empty",
    [SeatType.SEAT_MAN]: "seat_man",
    [SeatType.SEAT_WOMEN]: "seat_women",
    [SeatType.SEAT_SELECTED]: "seat_selected"
});

const BusSeatingComponent = ({ busType, seatNo, selectedSeat, onSeatSelect, tripId }) => {
    const [seatsData, setSeatsData] = useState([]);
    const [error, setError] = useState('');

    useEffect(() => {
        const fetchSeatsData = async () => {
            try {
                const response = await axios.get('http://localhost:8080/passengers', {
                    params: {
                        tripIds: tripId
                    }
                });
                console.log('Seats data fetched:', response.data); // Debugging output
                setSeatsData(response.data);
            } catch (error) {
                console.error('Error fetching seat data:', error);
                setError('Unable to fetch seat data');
            }
        };

        if (tripId) {
            fetchSeatsData();
        }
    }, [tripId]);

    const handleSeatSelect = (seatLabel, seatType) => {
        // Prevent selection of seats that are already occupied (SEAT_MAN or SEAT_WOMEN)
        if (seatType === SeatType.SEAT_MAN || seatType === SeatType.SEAT_WOMEN) {
            return;
        }

        // Toggle seat selection
        if (selectedSeat === seatLabel) {
            onSeatSelect(null);
        } else {
            onSeatSelect(seatLabel);
        }
    };

    const renderSeats = () => {
        const seats = [];
        let seatCounter = 1;
        const seatsPerColumn = busType === "2s1" ? 3 : 4;
        const seatSpacingX = 60;
        const seatSpacingYFirstRow = 50;
        const seatSpacingYOtherRows = 50;
        const seatSpacingBeforeLastRows = 60;
        const firstColumnOffsetX = 140;
        const normalColumnOffsetX = 140;
        const firstColumnOffsetY = -100;
        const normalColumnOffsetY = -100;
        const numColumns = Math.ceil(seatNo / seatsPerColumn);

        // Create a mapping of seatId to seatType from the fetched data
        const seatTypeMap = {};
        seatsData.forEach(seat => {
            seatTypeMap[seat.seatId] = seat.gender === "Male" ? SeatType.SEAT_MAN : SeatType.SEAT_WOMEN;
        });

        for (let col = 0; col < numColumns; col++) {
            for (let row = 0; row < seatsPerColumn; row++) {
                if (seatCounter <= seatNo) {
                    const yOffset = (row === seatsPerColumn - 1 || row === seatsPerColumn - 2) ? seatSpacingBeforeLastRows : 0;

                    const seatType = seatTypeMap[seatCounter] || SeatType.SEAT_EMPTY;

                    seats.push({
                        x: col === 0 ? firstColumnOffsetX : (col * (seatSpacingX + 20)) + normalColumnOffsetX,
                        y: 120 + (row === 0 ? seatSpacingYFirstRow : seatSpacingYOtherRows) * row + (col === 0 ? firstColumnOffsetY : normalColumnOffsetY) + yOffset,
                        label: seatCounter++,
                        type: selectedSeat === seatCounter - 1 ? SeatType.SEAT_SELECTED : seatType
                    });
                }
            }
        }

        return (
            <div className={styles.busLayout}>
                <img src="/bus.png" alt="Background" className={styles.busLayoutBackground} />
                {seats.map((eachSeat) => {
                    const seatStyle = {
                        position: 'absolute',
                        top: `${eachSeat.y}px`,
                        left: `${eachSeat.x}px`,
                        width: '40px',
                        height: '40px'
                    };

                    const labelStyle = {
                        position: 'absolute',
                        top: `${eachSeat.y + 10}px`,
                        left: `${eachSeat.x + 10}px`
                    };

                    const seatImagePath = `/${SeatImages[eachSeat.type]}.png`;

                    return (
                        <div
                            key={eachSeat.label}
                            onClick={() => handleSeatSelect(eachSeat.label, eachSeat.type)}
                            style={{ cursor: 'pointer' }}
                        >
                            <img src={seatImagePath} alt="Overlay" style={seatStyle} />
                            <p style={labelStyle}>{eachSeat.label}</p>
                        </div>
                    );
                })}
                {error && <p style={{ color: 'red' }}>{error}</p>}
            </div>
        );
    };

    return (
        <div>
            {renderSeats()}
        </div>
    );
};

export default BusSeatingComponent;
