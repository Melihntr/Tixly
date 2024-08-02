import React from 'react';
import './SeatingComponent.css'; // Import your CSS for styling

const SeatingComponent = ({ busType, onSeatClick }) => {
  const renderSeats = () => {
    if (busType === '2s1') {
      return (
        <div className="bus-layout">
          <div className="left-side">
            <button className="seat" onClick={() => onSeatClick('L1')}>L1</button>
          </div>
          <div className="right-side">
            <button className="seat" onClick={() => onSeatClick('R1')}>R1</button>
            <button className="seat" onClick={() => onSeatClick('R2')}>R2</button>
          </div>
        </div>
      );
    } else if (busType === '2s2') {
      return (
        <div className="bus-layout">
          <div className="left-side">
            <button className="seat" onClick={() => onSeatClick('L1')}>L1</button>
            <button className="seat" onClick={() => onSeatClick('L2')}>L2</button>
          </div>
          <div className="right-side">
            <button className="seat" onClick={() => onSeatClick('R1')}>R1</button>
            <button className="seat" onClick={() => onSeatClick('R2')}>R2</button>
          </div>
        </div>
      );
    }
    return null;
  };

  return (
    <div className="seating-container">
      {renderSeats()}
    </div>
  );
};

export default SeatingComponent;
