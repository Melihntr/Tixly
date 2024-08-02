// BusSeatingComponent.js
import React from 'react';
import styles from './BusSeatingComponent.module.css'; // Ensure you have the corresponding CSS file

const BusSeatingComponent = ({ busType, selectedSeat, onSeatSelect }) => {
    const renderSeats = () => {
        const seats = [];
        if (busType === '2s1') {
            for (let i = 1; i <= 2; i++) {
                seats.push(
                    <button
                        key={`L${i}`}
                        className={`${styles.seat} ${selectedSeat === `L${i}` ? styles.selected : ''}`}
                        onClick={() => onSeatSelect(`L${i}`)}
                    >
                        L{i}
                    </button>
                );
            }
            for (let i = 1; i <= 2; i++) {
                seats.push(
                    <button
                        key={`R${i}`}
                        className={`${styles.seat} ${selectedSeat === `R${i}` ? styles.selected : ''}`}
                        onClick={() => onSeatSelect(`R${i}`)}
                    >
                        R{i}
                    </button>
                );
            }
        } else if (busType === '2s2') {
            for (let i = 1; i <= 2; i++) {
                seats.push(
                    <button
                        key={`L${i}`}
                        className={`${styles.seat} ${selectedSeat === `L${i}` ? styles.selected : ''}`}
                        onClick={() => onSeatSelect(`L${i}`)}
                    >
                        L{i}
                    </button>
                );
            }
            for (let i = 1; i <= 2; i++) {
                seats.push(
                    <button
                        key={`R${i}`}
                        className={`${styles.seat} ${selectedSeat === `R${i}` ? styles.selected : ''}`}
                        onClick={() => onSeatSelect(`R${i}`)}
                    >
                        R{i}
                    </button>
                );
            }
        }
        return seats;
    };

    return (
        <div className={styles.busLayout}>
            <div className={styles.leftSide}>
                {renderSeats().filter(seat => seat.key.startsWith('L'))}
            </div>
            <div className={styles.rightSide}>
                {renderSeats().filter(seat => seat.key.startsWith('R'))}
            </div>
        </div>
    );
};

export default BusSeatingComponent;
