import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React, { useEffect } from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Register from './views/Register';
import ForgotPassword from './views/ForgotPassword';
import OwnerLogin from './views/OwnerLogin';
import MainPage from './views/MainPage';
import MyTrips from './views/MyTrips';
import Hesap from './views/Hesap';
import Footer from './components/Footer'; // Import Footer component
import OwnerDashboard from './views/OwnerDashboard';
import Biletlerim from './views/Biletlerim';
import Header from './components/Header';
import ActiveTrips from './views/ActiveTrips';
import OtobusBilgileri from './views/OtobüsBilgileri'; 
import TripsPage from './views/TripsPage';

function App() {
  useEffect(() => {
    // Clear local storage when the app starts
    localStorage.clear();
  }, []);
  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        <main className="flex-fill">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/myTrips" element={<MyTrips />} />
            <Route path="/biletlerim" element={<Biletlerim />} />
            <Route path="/hesap" element={<Hesap />} />
            <Route path="/register" element={<Register />} />
            <Route path="/trips" element={<ActiveTrips />} />
            <Route path="/trips-management" element={<TripsPage />} />
            <Route path="/otobus-bilgileri" element={<OtobusBilgileri />} /> {/* Add this route */}
            <Route path="/forgot-password" element={<ForgotPassword />} />
            <Route path="/owner-login" element={<OwnerLogin />} />
            <Route path="/owner-dashboard" element={<OwnerDashboard />} />
          </Routes>
        </main>
        <Footer />
      </div>
    </Router>
  );
}

export default App;