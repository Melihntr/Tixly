import React from 'react';
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

// A simple PRNG with a seed
function seededRandom(seed) {
    const x = Math.sin(seed) * 10000;
    return x - Math.floor(x);
}

const getRandomSeatType = (seed, index) => {
    const random = seededRandom(seed + index);
    if (random < 0.2) { // 20% chance for SEAT_MAN
        return SeatType.SEAT_MAN;
    } else if (random < 0.4) { // 20% chance for SEAT_WOMEN
        return SeatType.SEAT_WOMEN;
    } else {
        return SeatType.SEAT_EMPTY;
    }
};

const BusSeatingComponent = ({ busType, seatNo, selectedSeat, onSeatSelect }) => {
    const handleSeatSelect = (seatLabel) => {
        if (selectedSeat === seatLabel) {
            onSeatSelect(null); // Deselect if the same seat is clicked again
        } else {
            onSeatSelect(seatLabel); // Set the new selected seat
        }
    };

    const renderSeats = () => {
        const seats = [];
        let seatCounter = 1;
        const seatsPerColumn = busType === "2s1" ? 3 : 4; // 3 seats per column for 2s1, 4 for 2s2
        const seatSpacingX = 60; // Space between seats horizontally
        const seatSpacingYFirstRow = 50; // Space between seats vertically for the first row
        const seatSpacingYOtherRows = 50; // Space between seats vertically for other rows
        const seatSpacingBeforeLastRows = 60; // Additional vertical spacing before the last two rows
        const firstColumnOffsetX = 140; // Horizontal offset for the first column
        const normalColumnOffsetX = 140; // Normal horizontal offset for other columns
        const firstColumnOffsetY = -100; // Vertical offset to push the first column up
        const normalColumnOffsetY = -100; // No vertical offset for other columns
        const numColumns = Math.ceil(seatNo / seatsPerColumn);

        // Generate seats based on bus type and seatNo
        for (let col = 0; col < numColumns; col++) {
            for (let row = 0; row < seatsPerColumn; row++) {
                if (seatCounter <= seatNo) {
                    // Calculate y position with additional spacing before the last two rows
                    const yOffset = (row === seatsPerColumn - 1 || row === seatsPerColumn - 2) ? seatSpacingBeforeLastRows : 0;

                    seats.push({
                        x: col === 0 ? firstColumnOffsetX : (col * (seatSpacingX + 20)) + normalColumnOffsetX, // Special offset for first column
                        y: 120 + (row === 0 ? seatSpacingYFirstRow : seatSpacingYOtherRows) * row + (col === 0 ? firstColumnOffsetY : normalColumnOffsetY) + yOffset, // Additional vertical offset before last two rows
                        label: seatCounter++,
                        type: selectedSeat === seatCounter - 1 ? SeatType.SEAT_SELECTED : getRandomSeatType(12345, seatCounter - 1) // Consistent random seed
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
                            onClick={() => handleSeatSelect(eachSeat.label)} 
                            style={{ cursor: 'pointer' }}
                        >
                            <img src={seatImagePath} alt="Overlay" style={seatStyle} />
                            <p style={labelStyle}>{eachSeat.label}</p>
                        </div>
                    );
                })}
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
