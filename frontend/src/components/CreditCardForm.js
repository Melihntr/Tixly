import React, { useState } from 'react';
import styles from './CreditCardForm.module.css'; // Import your CSS file for styling
import Cards from 'react-credit-cards-2';
import 'react-credit-cards-2/dist/es/styles-compiled.css';


const CreditCardForm = ({ onSuccess }) => {
    const [cardNumber, setCardNumber] = useState('');
    const [cardHolder, setCardHolder] = useState('');
    const [expirationMonth, setExpirationMonth] = useState('');
    const [expirationYear, setExpirationYear] = useState('');
    const [cvc, setCvc] = useState('');

    const [state, setState] = useState({
        number: '',
        expiry: '',
        cvc: '',
        name: '',
        focus: '',
    });

    const handleInputChange = (evt) => {
        const { name, value } = evt.target;

        setState((prev) => ({ ...prev, [name]: value }));
    }

    const handleInputFocus = (evt) => {
        setState((prev) => ({ ...prev, focus: evt.target.name }));
    }

    const handleSubmit = (e) => {
        e.preventDefault();
        // Implement your payment logic here

        // Simulate a successful payment
        setTimeout(() => {
            onSuccess();
        }, 1000);
    };

    return (
        <div className={styles.creditCardForm}>

            <Cards
                number={state.number}
                expiry={state.expiry}
                cvc={state.cvc}
                name={state.name}
                focused={state.focus}
            />

            <form>
                <input
                    className='pt-3 mt-3'
                    type="number"
                    name="number"
                    placeholder="Card Number"
                    value={state.number}
                    onChange={handleInputChange}
                    onFocus={handleInputFocus}
                />

                <input
                    className='pt-3 mt-3'
                    type="text"
                    name="name"
                    placeholder="Holder Name"
                    value={state.name}
                    onChange={handleInputChange}
                    onFocus={handleInputFocus}
                />

                <input
                    className='pt-3 mt-3'
                    type="text"
                    name="expiry"
                    placeholder="Expire Date"
                    value={state.expiry}
                    onChange={handleInputChange}
                    onFocus={handleInputFocus}
                />

                <input
                    className='pt-3 mt-3'
                    type="text"
                    name="cvc"
                    placeholder="CVC"
                    value={state.cvc}
                    onChange={handleInputChange}
                    onFocus={handleInputFocus}
                />

            </form>

        </div>
    );
};

export default CreditCardForm;
