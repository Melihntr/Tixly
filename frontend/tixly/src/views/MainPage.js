import React from 'react';
import Header from '../components/Header';
import TicketSearch from '../components/TicketSearch';
import Features from '../components/Features';
import Partners from '../components/Partners';
import FAQ from '../components/FAQ';

const MainPage = () => {
    return (
        <div className="main-page">
            <Header />
            <br />
            <TicketSearch />
            <Features />
            <br />
            <Partners />
            <FAQ />
        </div>
    );
};

export default MainPage;
/* 
                1. useState
                2. useEffect
                3. Axios
                4. two way prop kontrolü
                5. redux
            */