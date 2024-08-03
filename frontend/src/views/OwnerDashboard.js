import React, { useState, useEffect } from 'react';
import { Container, Card } from 'react-bootstrap';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import Sidebar from '../components/Sidebar'; // Import the Sidebar component
import AddTripModal from '../components/AddTripModal';
import CancelTripModal from '../components/CancelTripModal';
import GetTripModal from '../components/GetTripModal';
import GetCompanyModal from '../components/GetCompanyModal';
import GetBusModal from '../components/GetBusModal'; // Import GetBusModal
import styles from './OwnerDashboard.module.css'; // Import CSS module

const OwnerDashboard = () => {
    const [showTripModal, setShowTripModal] = useState(false);
    const [showCancelTripModal, setShowCancelTripModal] = useState(false);
    const [showGetTripModal, setShowGetTripModal] = useState(false);
    const [showGetCompanyModal, setShowGetCompanyModal] = useState(false);
    const [showGetBusModal, setShowGetBusModal] = useState(false); // State for GetBusModal

    const navigate = useNavigate();

    // Handle modals
    const handleShowTripModal = () => setShowTripModal(true);
    const handleCloseTripModal = () => setShowTripModal(false);

    const handleShowCancelTripModal = () => setShowCancelTripModal(true);
    const handleCloseCancelTripModal = () => setShowCancelTripModal(false);

    const handleShowGetTripModal = () => setShowGetTripModal(true);
    const handleCloseGetTripModal = () => setShowGetTripModal(false);

    const handleShowGetCompanyModal = () => setShowGetCompanyModal(true);
    const handleCloseGetCompanyModal = () => setShowGetCompanyModal(false);

    const handleShowGetBusModal = () => setShowGetBusModal(true); // Show GetBusModal
    const handleCloseGetBusModal = () => setShowGetBusModal(false); // Close GetBusModal

    // Handle logout
    const handleLogout = async () => {
        const authKey = localStorage.getItem('authKey');

        if (authKey) {
            try {
                await axios.post('http://localhost:8080/admin/logout', {}, {
                    headers: {
                        'Authorization': `Bearer ${authKey}`
                    }
                });

                localStorage.removeItem('authKey');
                navigate('/owner-login');
            } catch (error) {
                console.error('Çıkış yapma hatası:', error);
            }
        } else {
            console.warn('Auth key not found in localStorage.');
        }
    };

    // Automatic logout on page unload
    useEffect(() => {
        const handleBeforeUnload = (event) => {
            handleLogout(); // Call logout before page unload
            event.preventDefault();
            event.returnValue = ''; // Required for some browsers to show the confirmation dialog
        };

        window.addEventListener('beforeunload', handleBeforeUnload);

        return () => {
            window.removeEventListener('beforeunload', handleBeforeUnload);
        };
    }, [navigate]);

    return (
        <div className={styles.dashboardContainer}>
            {/* Sidebar */}
            <Sidebar
                handleShowGetCompanyModal={handleShowGetCompanyModal}
                handleLogout={handleLogout}
            />

            {/* Main content */}
            <Container className={styles.mainContent}>
                <Card className={styles.infoCard}>
                    <Card.Body>
                        <h2>Yönetici Panosu</h2>
                        <p>
                            Yönetici Panosu'na hoş geldiniz! Bu panel, Tixly uygulamanızın yönetimi için tüm gerekli araçları sunar. Burada, çeşitli işlevleri kolayca gerçekleştirebilir ve otobüs, sefer ve şirket bilgilerini yönetebilirsiniz. İşte bu panelde yapabileceğiniz bazı işlemler:
                        </p>
                        <p>
                            <strong>Sefer Bilgileri:</strong> Şirketinizin mevcut seferlerini detaylı bir şekilde görüntüleyebilir ve yönetebilirsiniz. Sefer bilgileriniz, seyahat güzergahları, kalkış ve varış noktaları ile diğer önemli detayları içerir.
                        </p>
                        <p>
                            <strong>Otobüs Bilgileri:</strong> Şirketinizin sahip olduğu otobüslerin tüm bilgilerini görüntüleyebilirsiniz. Bu seçenek, otobüslerinizin plaka numarası, tipi ve koltuk sayısı gibi bilgilerini kolayca erişmenizi sağlar.
                        </p>
                        <p>
                            <strong>Şirket Bilgileri:</strong> Şirketinizin iletişim bilgilerini ve diğer önemli bilgilerini güncelleyebilir ve görüntüleyebilirsiniz. Bu bilgiler, şirketinizin adı, iletişim telefonu, e-posta adresi ve web sitesi gibi detayları içerir.
                        </p>
                        <p>
                            Yönetici paneli, sisteminizi etkin bir şekilde yönetmenize olanak sağlar. Tüm işlemler, kullanımı kolay arayüzümüz sayesinde hızlı ve verimli bir şekilde gerçekleştirilebilir. Herhangi bir sorun veya geri bildirimde bulunmak için lütfen bizimle iletişime geçmekten çekinmeyin.
                        </p>
                    </Card.Body>
                </Card>

                <AddTripModal show={showTripModal} handleClose={handleCloseTripModal} />
                <CancelTripModal show={showCancelTripModal} handleClose={handleCloseCancelTripModal} />
                <GetTripModal showModal={showGetTripModal} handleClose={handleCloseGetTripModal} />
                <GetCompanyModal showModal={showGetCompanyModal} handleClose={handleCloseGetCompanyModal} />
                <GetBusModal showModal={showGetBusModal} handleClose={handleCloseGetBusModal} /> {/* Add GetBusModal */}
            </Container>
        </div>
    );
};

export default OwnerDashboard;
