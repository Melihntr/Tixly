// BusSeatingComponent.js
import React from 'react';
import styles from './BusSeatingComponent.module.css'; // Ensure you have the corresponding CSS file
import { Card } from 'react-bootstrap';

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


const BusSeatingComponent = ({ busType, selectedSeat, onSeatSelect, selectedGender, setSelectedGender }) => {
    const render2s1 = () => {
       return "2s1";
    };

    const render2s2 = () => {
        
        
        const seats = [
            {
                x: 20,
                y: 120,
                label: "1",
                type: SeatType.SEAT_MAN
            },
            {
                x: 60,
                y: 120,
                label: "2",
                type: SeatType.SEAT_EMPTY
            },
            {
                x: 140,
                y: 120,
                label: "3",
                type: SeatType.SEAT_SELECTED
            },
            {
                x: 180,
                y: 120,
                label: "4",
                type: SeatType.SEAT_WOMEN
            },

            {
                x: 20,
                y: 170,
                label: "5",
                type: SeatType.SEAT_EMPTY
            },
            {
                x: 60,
                y: 170,
                label: "6",
                type: SeatType.SEAT_EMPTY
            },
            {
                x: 140,
                y: 170,
                label: "7",
                type: SeatType.SEAT_EMPTY
            },
            {
                x: 180,
                y: 170,
                label: "8",
                type: SeatType.SEAT_EMPTY
            },
        ];
        
        return(
            <div className={styles.busLayout}>
            <img src="/bus.png" alt="Background" className={styles.busLayoutBackground} />
            
                {seats.map((eachSeat) => {
                    const seatStyle = {
                        position: 'absolute',
                        top: `${eachSeat.x }px`,
                        left: `${eachSeat.y}px`,
                        width: '40px',
                        height: '40px'
                    };

                    const labelStyle = {
                        position: 'absolute',
                        top: `${eachSeat.x + 6 }px`,
                        left: `${eachSeat.y + 14}px`,
                        
                    };

                    const seatImagePath = `/${SeatImages[eachSeat.type]}.png`;

                    const onClickCallback = () => {
                        alert("sa, bilgilerim:" +  JSON.stringify(eachSeat));
                    }

                    return (
                        <div onClick={onClickCallback}>
                            <img src={seatImagePath}  alt="Overlay" style={seatStyle}/>
                            <p style={labelStyle}>{eachSeat.label}</p>
                        </div>
                    );
                }) }
          </div>
        )
    }

    return (
        <div>
            {busType == "2s1" ?  render2s1() : render2s2()}
        </div>
    );
};

export default BusSeatingComponent;
