import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import 'bootstrap/dist/css/bootstrap.min.css';
import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Register from './views/Register';
import ForgotPassword from './views/ForgotPassword';
import OwnerLogin from './views/OwnerLogin';
import MainPage from './views/MainPage';
import Hesap from './views/Hesap';
import Footer from './components/Footer'; // Import Footer component
import OwnerDashboard from './views/OwnerDashboard';

function App() {
  return (
    <Router>
      <div className="d-flex flex-column min-vh-100">
        <main className="flex-fill">
          <Routes>
            <Route path="/" element={<MainPage />} />
            <Route path="/hesap" element={<Hesap />} />
            <Route path="/register" element={<Register />} />
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