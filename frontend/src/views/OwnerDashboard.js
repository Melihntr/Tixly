import React, { useState } from 'react';
import { Container, Nav, Card } from 'react-bootstrap';
import axios from 'axios';
import { Link, useNavigate } from 'react-router-dom';
import AddBusModal from '../components/AddBusModal';
import AddTripModal from '../components/AddTripModal';
import CancelTripModal from '../components/CancelTripModal';
import DeleteBusModal from '../components/DeleteBusModal';
import GetTripModal from '../components/GetTripModal';
import GetCompanyModal from '../components/GetCompanyModal';
import GetBusModal from '../components/GetBusModal'; // Import GetBusModal
import styles from './OwnerDashboard.module.css'; // Import CSS module

const OwnerDashboard = () => {
    const [showBusModal, setShowBusModal] = useState(false);
    const [showTripModal, setShowTripModal] = useState(false);
    const [showCancelTripModal, setShowCancelTripModal] = useState(false);
    const [showDeleteBusModal, setShowDeleteBusModal] = useState(false);
    const [showGetTripModal, setShowGetTripModal] = useState(false);
    const [showGetCompanyModal, setShowGetCompanyModal] = useState(false);
    const [showGetBusModal, setShowGetBusModal] = useState(false); // State for GetBusModal

    const navigate = useNavigate();

    const handleShowBusModal = () => setShowBusModal(true);
    const handleCloseBusModal = () => setShowBusModal(false);

    const handleShowTripModal = () => setShowTripModal(true);
    const handleCloseTripModal = () => setShowTripModal(false);

    const handleShowCancelTripModal = () => setShowCancelTripModal(true);
    const handleCloseCancelTripModal = () => setShowCancelTripModal(false);

    const handleShowDeleteBusModal = () => setShowDeleteBusModal(true);
    const handleCloseDeleteBusModal = () => setShowDeleteBusModal(false);

    const handleShowGetTripModal = () => setShowGetTripModal(true);
    const handleCloseGetTripModal = () => setShowGetTripModal(false);

    const handleShowGetCompanyModal = () => setShowGetCompanyModal(true);
    const handleCloseGetCompanyModal = () => setShowGetCompanyModal(false);

    const handleShowGetBusModal = () => setShowGetBusModal(true); // Show GetBusModal
    const handleCloseGetBusModal = () => setShowGetBusModal(false); // Close GetBusModal

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

    return (
        <div className={styles.dashboardContainer}>
            {/* Sidebar */}
            <div className={styles.sidebar}>
                <Link to="/" className={styles.logoLink}>
                    <div className={styles.logoText}>Tixly</div>
                </Link>
                
                <Nav className="flex-column">
                    <Nav.Link onClick={handleShowGetCompanyModal} className={`${styles.navLink} ${styles.navLinkTop}`}>Şirket Bilgileri</Nav.Link>
                    <Nav.Link onClick={handleShowGetTripModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Sefer Bilgileri</Nav.Link>
                    <Nav.Link onClick={handleShowGetBusModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Bilgileri</Nav.Link>
                    <Nav className="flex-column">
                        <Nav.Link onClick={handleShowBusModal} className={styles.navLink}>Otobüs Ekle</Nav.Link>
                    </Nav>
                    <Nav.Link onClick={handleShowTripModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Seyahat Ekle</Nav.Link>
                    <Nav.Link onClick={handleShowDeleteBusModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Otobüs Sil</Nav.Link>
                    <Nav.Link onClick={handleShowCancelTripModal} className={`${styles.navLink} ${styles.navLinkMiddle}`}>Seyahat İptali</Nav.Link>
                     
                    <Nav.Link onClick={handleLogout} className={`${styles.navLink} ${styles.navLinkBottom}`}>Çıkış Yap</Nav.Link>
                </Nav>
            </div>

            {/* Main content */}
            <Container className={styles.mainContent}>
            <Card className={styles.infoCard}>
            <Card.Body>
            <h2>Yönetici Panosu</h2>
<p>
    Yönetici Panosu'na hoş geldiniz! Bu panel, Tixly uygulamanızın yönetimi için tüm gerekli araçları sunar. Burada, çeşitli işlevleri kolayca gerçekleştirebilir ve otobüs, sefer ve şirket bilgilerini yönetebilirsiniz. İşte bu panelde yapabileceğiniz bazı işlemler:
</p>
<p>
    <strong>Otobüs Ekle:</strong> Yeni otobüsler eklemek için bu seçeneği kullanabilirsiniz. Otobüsler, plaka numarası, otobüs tipi ve koltuk sayısı gibi detaylarla birlikte eklenir. Bu işlem, sistemdeki otobüs kapasitenizi güncel tutmanıza yardımcı olur.
</p>
<p>
    <strong>Seyahat Ekle:</strong> Yeni seyahatler ekleyebilir ve mevcut seyahatleri yönetebilirsiniz. Seyahat bilgileri, kalkış ve varış noktaları, tarih ve saat gibi bilgileri içerir. Bu sayede müşterilere sunduğunuz seferlerin detaylarını düzenleyebilirsiniz.
</p>
<p>
    <strong>Seyahat İptali:</strong> Planlanan seyahatlerin iptali işlemini gerçekleştirebilirsiniz. İptal edilen seyahatler, müşterilere bildirilmeli ve sistemden kaldırılmalıdır.
</p>
<p>
    <strong>Otobüs Sil:</strong> Artık kullanılmayan otobüsleri sistemden kaldırmak için bu seçeneği kullanabilirsiniz. Otobüsler, plaka numarası ile silinir ve sistemdeki otobüs veritabanınız güncellenir.
</p>
<p>
    <strong>Otobüs Bilgileri:</strong> Şirketinizin sahip olduğu otobüslerin tüm bilgilerini görüntüleyebilirsiniz. Bu seçenek, otobüslerinizin plaka numarası, tipi ve koltuk sayısı gibi bilgilerini kolayca erişmenizi sağlar.
</p>
<p>
    <strong>Sefer Bilgileri:</strong> Şirketinizin mevcut seferlerini detaylı bir şekilde görüntüleyebilir ve yönetebilirsiniz. Sefer bilgileriniz, seyahat güzergahları, kalkış ve varış noktaları ile diğer önemli detayları içerir.
</p>
<p>
    <strong>Şirket Bilgileri:</strong> Şirketinizin iletişim bilgilerini ve diğer önemli bilgilerini güncelleyebilir ve görüntüleyebilirsiniz. Bu bilgiler, şirketinizin adı, iletişim telefonu, e-posta adresi ve web sitesi gibi detayları içerir.
</p>
<p>
    Yönetici paneli, sisteminizi etkin bir şekilde yönetmenize olanak sağlar. Tüm işlemler, kullanımı kolay arayüzümüz sayesinde hızlı ve verimli bir şekilde gerçekleştirilebilir. Herhangi bir sorun veya geri bildirimde bulunmak için lütfen bizimle iletişime geçmekten çekinmeyin.
</p>
</Card.Body>
                </Card>

                <AddBusModal show={showBusModal} handleClose={handleCloseBusModal} />
                <AddTripModal show={showTripModal} handleClose={handleCloseTripModal} />
                <CancelTripModal show={showCancelTripModal} handleClose={handleCloseCancelTripModal} />
                <DeleteBusModal show={showDeleteBusModal} handleClose={handleCloseDeleteBusModal} />
                <GetTripModal showModal={showGetTripModal} handleClose={handleCloseGetTripModal} />
                <GetCompanyModal showModal={showGetCompanyModal} handleClose={handleCloseGetCompanyModal} />
                <GetBusModal showModal={showGetBusModal} handleClose={handleCloseGetBusModal} /> {/* Add GetBusModal */}
            </Container>
        </div>
    );
};

export default OwnerDashboard;
