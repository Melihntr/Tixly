--
-- PostgreSQL database dump
--

-- Dumped from database version 15.7
-- Dumped by pg_dump version 16.3

-- Started on 2024-08-03 16:06:55

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 3 (class 3079 OID 16810)
-- Name: pgcrypto; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;


--
-- TOC entry 3480 (class 0 OID 0)
-- Dependencies: 3
-- Name: EXTENSION pgcrypto; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';


--
-- TOC entry 2 (class 3079 OID 16684)
-- Name: uuid-ossp; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;


--
-- TOC entry 3481 (class 0 OID 0)
-- Dependencies: 2
-- Name: EXTENSION "uuid-ossp"; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';


--
-- TOC entry 929 (class 1247 OID 17282)
-- Name: account_status; Type: TYPE; Schema: public; Owner: postgres
--

CREATE TYPE public.account_status AS ENUM (
    'PENDING',
    'ACTIVE',
    'BANNED'
);


ALTER TYPE public.account_status OWNER TO postgres;

--
-- TOC entry 282 (class 1255 OID 17276)
-- Name: sha256(text); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.sha256(text) RETURNS text
    LANGUAGE plpgsql
    AS $_$
BEGIN
    RETURN encode(digest($1, 'sha256'), 'hex');
END;
$_$;


ALTER FUNCTION public.sha256(text) OWNER TO postgres;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- TOC entry 221 (class 1259 OID 17183)
-- Name: bus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.bus (
    id integer NOT NULL,
    plateno character varying(20) NOT NULL,
    companyid integer,
    bustype character varying(50),
    seatno integer
);


ALTER TABLE public.bus OWNER TO postgres;

--
-- TOC entry 220 (class 1259 OID 17182)
-- Name: bus_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.bus_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.bus_id_seq OWNER TO postgres;

--
-- TOC entry 3482 (class 0 OID 0)
-- Dependencies: 220
-- Name: bus_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.bus_id_seq OWNED BY public.bus.id;


--
-- TOC entry 219 (class 1259 OID 17176)
-- Name: companies; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.companies (
    id integer NOT NULL,
    name character varying(100) NOT NULL,
    companyno character varying(50),
    website character varying(100),
    contact_phone character varying(20),
    contact_email character varying(50),
    logo_url character varying(255)
);


ALTER TABLE public.companies OWNER TO postgres;

--
-- TOC entry 218 (class 1259 OID 17175)
-- Name: companies_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.companies_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.companies_id_seq OWNER TO postgres;

--
-- TOC entry 3483 (class 0 OID 0)
-- Dependencies: 218
-- Name: companies_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.companies_id_seq OWNED BY public.companies.id;


--
-- TOC entry 217 (class 1259 OID 17169)
-- Name: customer; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.customer (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character(64) NOT NULL,
    mail character varying(100) NOT NULL,
    gender character varying(10),
    auth_key character varying(255),
    account_status character varying(255) DEFAULT 'PENDING'::public.account_status,
    verification_code character varying(10),
    phonenumber character varying(20),
    tc_no character varying(11)
);


ALTER TABLE public.customer OWNER TO postgres;

--
-- TOC entry 216 (class 1259 OID 17168)
-- Name: customer_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.customer_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.customer_id_seq OWNER TO postgres;

--
-- TOC entry 3484 (class 0 OID 0)
-- Dependencies: 216
-- Name: customer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.customer_id_seq OWNED BY public.customer.id;


--
-- TOC entry 235 (class 1259 OID 17309)
-- Name: ilceler; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ilceler (
    id integer NOT NULL,
    il_id integer NOT NULL,
    ilce_adi character varying(20) NOT NULL
);


ALTER TABLE public.ilceler OWNER TO postgres;

--
-- TOC entry 234 (class 1259 OID 17308)
-- Name: ilceler_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ilceler_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.ilceler_id_seq OWNER TO postgres;

--
-- TOC entry 3485 (class 0 OID 0)
-- Dependencies: 234
-- Name: ilceler_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ilceler_id_seq OWNED BY public.ilceler.id;


--
-- TOC entry 233 (class 1259 OID 17302)
-- Name: iller; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.iller (
    id integer NOT NULL,
    il_adi character varying(20) NOT NULL
);


ALTER TABLE public.iller OWNER TO postgres;

--
-- TOC entry 232 (class 1259 OID 17301)
-- Name: iller_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.iller_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.iller_id_seq OWNER TO postgres;

--
-- TOC entry 3486 (class 0 OID 0)
-- Dependencies: 232
-- Name: iller_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.iller_id_seq OWNED BY public.iller.id;


--
-- TOC entry 223 (class 1259 OID 17195)
-- Name: owner; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.owner (
    id integer NOT NULL,
    username character varying(50) NOT NULL,
    password character(64) NOT NULL,
    companyid integer,
    auth_key character varying(255),
    mail character varying(255),
    verification_code character varying(64),
    "phoneNumber" character varying(15)
);


ALTER TABLE public.owner OWNER TO postgres;

--
-- TOC entry 222 (class 1259 OID 17194)
-- Name: owner_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.owner_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.owner_id_seq OWNER TO postgres;

--
-- TOC entry 3487 (class 0 OID 0)
-- Dependencies: 222
-- Name: owner_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.owner_id_seq OWNED BY public.owner.id;


--
-- TOC entry 231 (class 1259 OID 17254)
-- Name: passenger; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.passenger (
    id integer NOT NULL,
    ticketid integer,
    seatid integer,
    tripid integer,
    gender character varying(10)
);


ALTER TABLE public.passenger OWNER TO postgres;

--
-- TOC entry 230 (class 1259 OID 17253)
-- Name: passenger_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.passenger_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.passenger_id_seq OWNER TO postgres;

--
-- TOC entry 3488 (class 0 OID 0)
-- Dependencies: 230
-- Name: passenger_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.passenger_id_seq OWNED BY public.passenger.id;


--
-- TOC entry 227 (class 1259 OID 17224)
-- Name: seats; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.seats (
    id integer NOT NULL,
    no character varying(10),
    busid integer
);


ALTER TABLE public.seats OWNER TO postgres;

--
-- TOC entry 226 (class 1259 OID 17223)
-- Name: seats_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.seats_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.seats_id_seq OWNER TO postgres;

--
-- TOC entry 3489 (class 0 OID 0)
-- Dependencies: 226
-- Name: seats_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.seats_id_seq OWNED BY public.seats.id;


--
-- TOC entry 229 (class 1259 OID 17236)
-- Name: tickets; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tickets (
    id integer NOT NULL,
    customerid integer,
    tripid integer,
    printdate timestamp without time zone,
    checkoutdate timestamp without time zone,
    purchasedate timestamp without time zone,
    invoiceid uuid DEFAULT public.uuid_generate_v4(),
    "from" character varying(255),
    "to" character varying(255),
    seatid integer
);


ALTER TABLE public.tickets OWNER TO postgres;

--
-- TOC entry 228 (class 1259 OID 17235)
-- Name: tickets_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tickets_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tickets_id_seq OWNER TO postgres;

--
-- TOC entry 3490 (class 0 OID 0)
-- Dependencies: 228
-- Name: tickets_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tickets_id_seq OWNED BY public.tickets.id;


--
-- TOC entry 225 (class 1259 OID 17207)
-- Name: trips; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.trips (
    id integer NOT NULL,
    peronno character varying(50),
    price numeric(10,2),
    companyid integer,
    busid integer,
    estimatedtime integer,
    departuretime timestamp without time zone,
    state character varying(50),
    departure_location_id character varying(255),
    arrival_location_id character varying(255)
);


ALTER TABLE public.trips OWNER TO postgres;

--
-- TOC entry 224 (class 1259 OID 17206)
-- Name: trips_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.trips_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.trips_id_seq OWNER TO postgres;

--
-- TOC entry 3491 (class 0 OID 0)
-- Dependencies: 224
-- Name: trips_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.trips_id_seq OWNED BY public.trips.id;


--
-- TOC entry 3273 (class 2604 OID 17186)
-- Name: bus id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus ALTER COLUMN id SET DEFAULT nextval('public.bus_id_seq'::regclass);


--
-- TOC entry 3272 (class 2604 OID 17179)
-- Name: companies id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.companies ALTER COLUMN id SET DEFAULT nextval('public.companies_id_seq'::regclass);


--
-- TOC entry 3270 (class 2604 OID 17172)
-- Name: customer id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer ALTER COLUMN id SET DEFAULT nextval('public.customer_id_seq'::regclass);


--
-- TOC entry 3281 (class 2604 OID 17312)
-- Name: ilceler id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ilceler ALTER COLUMN id SET DEFAULT nextval('public.ilceler_id_seq'::regclass);


--
-- TOC entry 3280 (class 2604 OID 17305)
-- Name: iller id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iller ALTER COLUMN id SET DEFAULT nextval('public.iller_id_seq'::regclass);


--
-- TOC entry 3274 (class 2604 OID 17198)
-- Name: owner id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner ALTER COLUMN id SET DEFAULT nextval('public.owner_id_seq'::regclass);


--
-- TOC entry 3279 (class 2604 OID 17257)
-- Name: passenger id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passenger ALTER COLUMN id SET DEFAULT nextval('public.passenger_id_seq'::regclass);


--
-- TOC entry 3276 (class 2604 OID 17227)
-- Name: seats id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats ALTER COLUMN id SET DEFAULT nextval('public.seats_id_seq'::regclass);


--
-- TOC entry 3277 (class 2604 OID 17239)
-- Name: tickets id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets ALTER COLUMN id SET DEFAULT nextval('public.tickets_id_seq'::regclass);


--
-- TOC entry 3275 (class 2604 OID 17210)
-- Name: trips id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trips ALTER COLUMN id SET DEFAULT nextval('public.trips_id_seq'::regclass);


--
-- TOC entry 3460 (class 0 OID 17183)
-- Dependencies: 221
-- Data for Name: bus; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.bus (id, plateno, companyid, bustype, seatno) FROM stdin;
1	34XYZ01	1	\N	\N
2	34XYZ02	1	\N	\N
3	34XYZ03	2	\N	\N
4	34XYZ04	2	\N	\N
30	01ACB345	1	2s1	3
34	02ACB345	1	2s2	12
36	04ABC345	1	2s2	12
\.


--
-- TOC entry 3458 (class 0 OID 17176)
-- Dependencies: 219
-- Data for Name: companies; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.companies (id, name, companyno, website, contact_phone, contact_email, logo_url) FROM stdin;
3	Company 3	003	http://companyc.com	\N	\N	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRkFA0BUiJ2GnflPMAil_qbsx8iLoWFXiz3kw&s
4	Company 4	004	http://companyd.com	\N	\N	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSiEctlWZDaTopnz75xN8EtBL1_nhQHujb0Xg&s
5	Company 5	005	http://companye.com	\N	\N	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRIpoYbwob_Uzmmjw54W5Hc4rN-crvrG-oJZw&s
2	Company 2	002	http://companyb.com	\N	\N	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQrewaAFPf4OkTcce3hiNDjmjVfIqYlDOjrBQ&s
1	Company 1	001	http://companya.com	+905061713530	company1@gmail.com	https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRNpsDgQduK1B32N6PZfZ71fK8oRqNzMQJ7fA&s
\.


--
-- TOC entry 3456 (class 0 OID 17169)
-- Dependencies: 217
-- Data for Name: customer; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.customer (id, username, password, mail, gender, auth_key, account_status, verification_code, phonenumber, tc_no) FROM stdin;
5	customer5	8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023	customer5@example.com	Male	\N	PENDING	741954	+905053002122	15523248280
88	owenwjrfo	8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023	melihavci439@gmail.com	Male	\N	PENDING	565488	134434314	15523248280
3	customer3	5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764	customer3@example.com	Male	\N	PENDING	\N	\N	\N
1	customer1	0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e	customer1@example.com	Male	\N	active	\N	\N	\N
4	customer4	b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b	customer4@example.com	Female	\N	PENDING	\N	\N	\N
2	customer2	6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4	customer2@example.com	Female	\N	PENDING	\N	\N	\N
\.


--
-- TOC entry 3474 (class 0 OID 17309)
-- Dependencies: 235
-- Data for Name: ilceler; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ilceler (id, il_id, ilce_adi) FROM stdin;
3	1	Çukurova
4	1	Feke
5	1	İmamoğlu
6	1	Karaisalı
7	1	Karataş
8	1	Kozan
9	1	Pozantı
10	1	Saimbeyli
11	1	Sarıçam
12	1	Seyhan
13	1	Tufanbeyli
14	1	Yumurtalık
15	1	Yüreğir
16	2	Besni
17	2	Çelikhan
18	2	Gerger
19	2	Gölbaşı
20	2	Kahta
21	2	Merkez
22	2	Samsat
23	2	Sincik
24	2	Tut
25	3	Başmakçı
26	3	Bayat
27	3	Bolvadin
28	3	Çay
29	3	Çobanlar
30	3	Dazkırı
31	3	Dinar
32	3	Emirdağ
33	3	Evciler
34	3	Hocalar
35	3	İhsaniye
36	3	İscehisar
37	3	Kızılören
38	3	Merkez
39	3	Sandıklı
40	3	Sinanpaşa
41	3	Sultandağı
42	3	Şuhut
43	4	Diyadin
44	4	Doğubayazıt
45	4	Eleşkirt
46	4	Hamur
47	4	Merkez
48	4	Patnos
49	4	Taşlıçay
50	4	Tutak
51	5	Ağaçören
52	5	Eskil
53	5	Gülağaç
54	5	Güzelyurt
55	5	Merkez
56	5	Ortaköy
57	5	Sarıyahşi
58	5	Sultanhanı
59	6	Göynücek
60	6	Gümüşhacıköy
61	6	Hamamözü
62	6	Merkez
63	6	Merzifon
64	6	Suluova
65	6	Taşova
66	7	Akyurt
67	7	Altındağ
68	7	Ayaş
69	7	Bala
70	7	Beypazarı
71	7	Çamlıdere
72	7	Çankaya
73	7	Çubuk
74	7	Elmadağ
75	7	Etimesgut
76	7	Evren
77	7	Gölbaşı
78	7	Güdül
79	7	Haymana
80	7	Kahramankazan
81	7	Kalecik
82	7	Keçiören
83	7	Kızılcahamam
84	7	Mamak
85	7	Nallıhan
86	7	Polatlı
87	7	Pursaklar
88	7	Sincan
89	7	Şereflikoçhisar
90	7	Yenimahalle
91	8	Akseki
92	8	Aksu
93	8	Alanya
94	8	Demre
95	8	Döşemealtı
96	8	Elmalı
97	8	Finike
98	8	Gazipaşa
99	8	Gündoğmuş
100	8	İbradı
101	8	Kaş
102	8	Kemer
103	8	Kepez
104	8	Konyaaltı
105	8	Korkuteli
106	8	Kumluca
107	8	Manavgat
108	8	Muratpaşa
109	8	Serik
110	9	Çıldır
111	9	Damal
112	9	Göle
113	9	Hanak
114	9	Merkez
115	9	Posof
116	10	Ardanuç
117	10	Arhavi
118	10	Borçka
119	10	Hopa
120	10	Kemalpaşa
121	10	Merkez
122	10	Murgul
123	10	Şavşat
124	10	Yusufeli
125	11	Bozdoğan
126	11	Buharkent
127	11	Çine
128	11	Didim
129	11	Efeler
130	11	Germencik
131	11	İncirliova
132	11	Karacasu
133	11	Karpuzlu
134	11	Koçarlı
135	11	Köşk
136	11	Kuşadası
137	11	Kuyucak
138	11	Nazilli
139	11	Söke
140	11	Sultanhisar
141	11	Yenipazar
142	12	Altıeylül
143	12	Ayvalık
144	12	Balya
145	12	Bandırma
146	12	Bigadiç
147	12	Burhaniye
148	12	Dursunbey
149	12	Edremit
150	12	Erdek
151	12	Gömeç
152	12	Gönen
153	12	Havran
154	12	İvrindi
155	12	Karesi
156	12	Kepsut
157	12	Manyas
158	12	Marmara
159	12	Savaştepe
160	12	Sındırgı
161	12	Susurluk
162	13	Amasra
163	13	Kurucaşile
164	13	Merkez
165	13	Ulus
166	14	Beşiri
167	14	Gercüş
168	14	Hasankeyf
169	14	Kozluk
170	14	Merkez
171	14	Sason
172	15	Aydıntepe
173	15	Demirözü
174	15	Merkez
175	16	Bozüyük
176	16	Gölpazarı
177	16	İnhisar
178	16	Merkez
179	16	Osmaneli
180	16	Pazaryeri
181	16	Söğüt
182	16	Yenipazar
183	17	Adaklı
184	17	Genç
185	17	Karlıova
186	17	Kiğı
187	17	Merkez
188	17	Solhan
189	17	Yayladere
190	17	Yedisu
191	18	Adilcevaz
192	18	Ahlat
193	18	Güroymak
194	18	Hizan
195	18	Merkez
196	18	Mutki
197	18	Tatvan
198	19	Dörtdivan
199	19	Gerede
200	19	Göynük
201	19	Kıbrıscık
202	19	Mengen
203	19	Merkez
204	19	Mudurnu
205	19	Seben
206	19	Yeniçağa
207	20	Ağlasun
208	20	Altınyayla
209	20	Bucak
210	20	Çavdır
211	20	Çeltikçi
212	20	Gölhisar
213	20	Karamanlı
214	20	Kemer
215	20	Merkez
216	20	Tefenni
217	20	Yeşilova
218	21	Büyükorhan
219	21	Gemlik
220	21	Gürsu
221	21	Harmancık
222	21	İnegöl
223	21	İznik
224	21	Karacabey
225	21	Keles
226	21	Kestel
227	21	Mudanya
228	21	Mustafakemalpaşa
229	21	Nilüfer
230	21	Orhaneli
231	21	Orhangazi
232	21	Osmangazi
233	21	Yenişehir
234	21	Yıldırım
235	22	Ayvacık
236	22	Bayramiç
237	22	Biga
238	22	Bozcaada
239	22	Çan
240	22	Eceabat
241	22	Ezine
242	22	Gelibolu
243	22	Gökçeada
244	22	Lapseki
245	22	Merkez
246	22	Yenice
247	23	Atkaracalar
248	23	Bayramören
249	23	Çerkeş
250	23	Eldivan
251	23	Ilgaz
252	23	Kızılırmak
253	23	Korgun
254	23	Kurşunlu
255	23	Merkez
256	23	Orta
257	23	Şabanözü
258	23	Yapraklı
259	24	Alaca
260	24	Bayat
261	24	Boğazkale
262	24	Dodurga
263	24	İskilip
264	24	Kargı
265	24	Laçin
266	24	Mecitözü
267	24	Merkez
268	24	Oğuzlar
269	24	Ortaköy
270	24	Osmancık
271	24	Sungurlu
272	24	Uğurludağ
273	25	Acıpayam
274	25	Babadağ
275	25	Baklan
276	25	Bekilli
277	25	Beyağaç
278	25	Bozkurt
279	25	Buldan
280	25	Çal
281	25	Çameli
282	25	Çardak
283	25	Çivril
284	25	Güney
285	25	Honaz
286	25	Kale
287	25	Merkezefendi
288	25	Pamukkale
289	25	Sarayköy
290	25	Serinhisar
291	25	Tavas
292	26	Bağlar
293	26	Bismil
294	26	Çermik
295	26	Çınar
296	26	Çüngüş
297	26	Dicle
298	26	Eğil
299	26	Ergani
300	26	Hani
301	26	Hazro
302	26	Kayapınar
303	26	Kocaköy
304	26	Kulp
305	26	Lice
306	26	Silvan
307	26	Sur
308	26	Yenişehir
309	27	Akçakoca
310	27	Cumayeri
311	27	Çilimli
312	27	Gölyaka
313	27	Gümüşova
314	27	Kaynaşlı
315	27	Merkez
316	27	Yığılca
317	28	Enez
318	28	Havsa
319	28	İpsala
320	28	Keşan
321	28	Lalapaşa
322	28	Meriç
323	28	Merkez
324	28	Süloğlu
325	28	Uzunköprü
326	29	Ağın
327	29	Alacakaya
328	29	Arıcak
329	29	Baskil
330	29	Karakoçan
331	29	Keban
332	29	Kovancılar
333	29	Maden
334	29	Merkez
335	29	Palu
336	29	Sivrice
337	30	Çayırlı
338	30	İliç
339	30	Kemah
340	30	Kemaliye
341	30	Merkez
342	30	Otlukbeli
343	30	Refahiye
344	30	Tercan
345	30	Üzümlü
346	31	Aşkale
347	31	Aziziye
348	31	Çat
349	31	Hınıs
350	31	Horasan
351	31	İspir
352	31	Karaçoban
353	31	Karayazı
354	31	Köprüköy
355	31	Narman
356	31	Oltu
357	31	Olur
358	31	Palandöken
359	31	Pasinler
360	31	Pazaryolu
361	31	Şenkaya
362	31	Tekman
363	31	Tortum
364	31	Uzundere
365	31	Yakutiye
366	32	Alpu
367	32	Beylikova
368	32	Çifteler
369	32	Günyüzü
370	32	Han
371	32	İnönü
372	32	Mahmudiye
373	32	Mihalgazi
374	32	Mihalıççık
375	32	Odunpazarı
376	32	Sarıcakaya
377	32	Seyitgazi
378	32	Sivrihisar
379	32	Tepebaşı
380	33	Araban
381	33	İslahiye
382	33	Karkamış
383	33	Nizip
384	33	Nurdağı
385	33	Oğuzeli
386	33	Şahinbey
387	33	Şehitkamil
388	33	Yavuzeli
389	34	Alucra
390	34	Bulancak
391	34	Çamoluk
392	34	Çanakçı
393	34	Dereli
394	34	Doğankent
395	34	Espiye
396	34	Eynesil
397	34	Görele
398	34	Güce
399	34	Keşap
400	34	Merkez
401	34	Piraziz
402	34	Şebinkarahisar
403	34	Tirebolu
404	34	Yağlıdere
405	35	Kelkit
406	35	Köse
407	35	Kürtün
408	35	Merkez
409	35	Şiran
410	35	Torul
411	36	Çukurca
412	36	Derecik
413	36	Merkez
414	36	Şemdinli
415	36	Yüksekova
416	37	Altınözü
417	37	Antakya
418	37	Arsuz
419	37	Belen
420	37	Defne
421	37	Dörtyol
422	37	Erzin
423	37	Hassa
424	37	İskenderun
425	37	Kırıkhan
426	37	Kumlu
427	37	Payas
428	37	Reyhanlı
429	37	Samandağ
430	37	Yayladağı
431	38	Aralık
432	38	Karakoyunlu
433	38	Merkez
434	38	Tuzluca
435	39	Aksu
436	39	Atabey
437	39	Eğirdir
438	39	Gelendost
439	39	Gönen
440	39	Keçiborlu
441	39	Merkez
442	39	Senirkent
443	39	Sütçüler
444	39	Şarkikaraağaç
445	39	Uluborlu
446	39	Yalvaç
447	39	Yenişarbademli
448	40	Adalar
449	40	Arnavutköy
450	40	Ataşehir
451	40	Avcılar
452	40	Bağcılar
453	40	Bahçelievler
454	40	Bakırköy
455	40	Başakşehir
456	40	Bayrampaşa
457	40	Beşiktaş
458	40	Beykoz
459	40	Beylikdüzü
460	40	Beyoğlu
461	40	Büyükçekmece
462	40	Çatalca
463	40	Çekmeköy
464	40	Esenler
465	40	Esenyurt
466	40	Eyüpsultan
467	40	Fatih
468	40	Gaziosmanpaşa
469	40	Güngören
470	40	Kadıköy
471	40	Kağıthane
472	40	Kartal
473	40	Küçükçekmece
474	40	Maltepe
475	40	Pendik
476	40	Sancaktepe
477	40	Sarıyer
478	40	Silivri
479	40	Sultanbeyli
480	40	Sultangazi
481	40	Şile
482	40	Şişli
483	40	Tuzla
484	40	Ümraniye
485	40	Üsküdar
486	40	Zeytinburnu
487	41	Aliağa
488	41	Balçova
489	41	Bayındır
490	41	Bayraklı
491	41	Bergama
492	41	Beydağ
493	41	Bornova
494	41	Buca
495	41	Çeşme
496	41	Çiğli
497	41	Dikili
498	41	Foça
499	41	Gaziemir
500	41	Güzelbahçe
501	41	Karabağlar
502	41	Karaburun
503	41	Karşıyaka
504	41	Kemalpaşa
505	41	Kınık
506	41	Kiraz
507	41	Konak
508	41	Menderes
509	41	Menemen
510	41	Narlıdere
511	41	Ödemiş
512	41	Seferihisar
513	41	Selçuk
514	41	Tire
515	41	Torbalı
516	41	Urla
517	42	Afşin
518	42	Andırın
519	42	Çağlayancerit
520	42	Dulkadiroğlu
521	42	Ekinözü
522	42	Elbistan
523	42	Göksun
524	42	Nurhak
525	42	Onikişubat
526	42	Pazarcık
527	42	Türkoğlu
528	43	Eflani
529	43	Eskipazar
530	43	Merkez
531	43	Ovacık
532	43	Safranbolu
533	43	Yenice
534	44	Ayrancı
535	44	Başyayla
536	44	Ermenek
537	44	Kazımkarabekir
538	44	Merkez
539	44	Sarıveliler
540	45	Akyaka
541	45	Arpaçay
542	45	Digor
543	45	Kağızman
544	45	Merkez
545	45	Sarıkamış
546	45	Selim
547	45	Susuz
548	46	Abana
549	46	Ağlı
550	46	Araç
551	46	Azdavay
552	46	Bozkurt
553	46	Cide
554	46	Çatalzeytin
555	46	Daday
556	46	Devrekani
557	46	Doğanyurt
558	46	Hanönü
559	46	İhsangazi
560	46	İnebolu
561	46	Küre
562	46	Merkez
563	46	Pınarbaşı
564	46	Seydiler
565	46	Şenpazar
566	46	Taşköprü
567	46	Tosya
568	47	Akkışla
569	47	Bünyan
570	47	Develi
571	47	Felahiye
572	47	Hacılar
573	47	İncesu
574	47	Kocasinan
575	47	Melikgazi
576	47	Özvatan
577	47	Pınarbaşı
578	47	Sarıoğlan
579	47	Sarız
580	47	Talas
581	47	Tomarza
582	47	Yahyalı
583	47	Yeşilhisar
584	48	Bahşılı
585	48	Balışeyh
586	48	Çelebi
587	48	Delice
588	48	Karakeçili
589	48	Keskin
590	48	Merkez
591	48	Sulakyurt
592	48	Yahşihan
593	49	Babaeski
594	49	Demirköy
595	49	Kofçaz
596	49	Lüleburgaz
597	49	Merkez
598	49	Pehlivanköy
599	49	Pınarhisar
600	49	Vize
601	50	Akçakent
602	50	Akpınar
603	50	Boztepe
604	50	Çiçekdağı
605	50	Kaman
606	50	Merkez
607	50	Mucur
608	51	Elbeyli
609	51	Merkez
610	51	Musabeyli
611	51	Polateli
612	52	Başiskele
613	52	Çayırova
614	52	Darıca
615	52	Derince
616	52	Dilovası
617	52	Gebze
618	52	Gölcük
619	52	İzmit
620	52	Kandıra
621	52	Karamürsel
622	52	Kartepe
623	52	Körfez
624	53	Ahırlı
625	53	Akören
626	53	Akşehir
627	53	Altınekin
628	53	Beyşehir
629	53	Bozkır
630	53	Cihanbeyli
631	53	Çeltik
632	53	Çumra
633	53	Derbent
634	53	Derebucak
635	53	Doğanhisar
636	53	Emirgazi
637	53	Ereğli
638	53	Güneysınır
639	53	Hadim
640	53	Halkapınar
641	53	Hüyük
642	53	Ilgın
643	53	Kadınhanı
644	53	Karapınar
645	53	Karatay
646	53	Kulu
647	53	Meram
648	53	Sarayönü
649	53	Selçuklu
650	53	Seydişehir
651	53	Taşkent
652	53	Tuzlukçu
653	53	Yalıhüyük
654	53	Yunak
655	54	Altıntaş
656	54	Aslanapa
657	54	Çavdarhisar
658	54	Domaniç
659	54	Dumlupınar
660	54	Emet
661	54	Gediz
662	54	Hisarcık
663	54	Merkez
664	54	Pazarlar
665	54	Simav
666	54	Şaphane
667	54	Tavşanlı
668	55	Akçadağ
669	55	Arapgir
670	55	Arguvan
671	55	Battalgazi
672	55	Darende
673	55	Doğanşehir
674	55	Doğanyol
675	55	Hekimhan
676	55	Kale
677	55	Kuluncak
678	55	Pütürge
679	55	Yazıhan
680	55	Yeşilyurt
681	56	Ahmetli
682	56	Akhisar
683	56	Alaşehir
684	56	Demirci
685	56	Gölmarmara
686	56	Gördes
687	56	Kırkağaç
688	56	Köprübaşı
689	56	Kula
690	56	Salihli
691	56	Sarıgöl
692	56	Saruhanlı
693	56	Selendi
694	56	Soma
695	56	Şehzadeler
696	56	Turgutlu
697	56	Yunusemre
698	57	Artuklu
699	57	Dargeçit
700	57	Derik
701	57	Kızıltepe
702	57	Mazıdağı
703	57	Midyat
704	57	Nusaybin
705	57	Ömerli
706	57	Savur
707	57	Yeşilli
708	58	Akdeniz
709	58	Anamur
710	58	Aydıncık
711	58	Bozyazı
712	58	Çamlıyayla
713	58	Erdemli
714	58	Gülnar
715	58	Mezitli
716	58	Mut
717	58	Silifke
718	58	Tarsus
719	58	Toroslar
720	58	Yenişehir
721	59	Bodrum
722	59	Dalaman
723	59	Datça
724	59	Fethiye
725	59	Kavaklıdere
726	59	Köyceğiz
727	59	Marmaris
728	59	Menteşe
729	59	Milas
730	59	Ortaca
731	59	Seydikemer
732	59	Ula
733	59	Yatağan
734	60	Bulanık
735	60	Hasköy
736	60	Korkut
737	60	Malazgirt
738	60	Merkez
739	60	Varto
740	61	Acıgöl
741	61	Avanos
742	61	Derinkuyu
743	61	Gülşehir
744	61	Hacıbektaş
745	61	Kozaklı
746	61	Merkez
747	61	Ürgüp
748	62	Altunhisar
749	62	Bor
750	62	Çamardı
751	62	Çiftlik
752	62	Merkez
753	62	Ulukışla
754	63	Akkuş
755	63	Altınordu
756	63	Aybastı
757	63	Çamaş
758	63	Çatalpınar
759	63	Çaybaşı
760	63	Fatsa
761	63	Gölköy
762	63	Gülyalı
763	63	Gürgentepe
764	63	İkizce
765	63	Kabadüz
766	63	Kabataş
767	63	Korgan
768	63	Kumru
769	63	Mesudiye
770	63	Perşembe
771	63	Ulubey
772	63	Ünye
773	64	Bahçe
774	64	Düziçi
775	64	Hasanbeyli
776	64	Kadirli
777	64	Merkez
778	64	Sumbas
779	64	Toprakkale
780	65	Ardeşen
781	65	Çamlıhemşin
782	65	Çayeli
783	65	Derepazarı
784	65	Fındıklı
785	65	Güneysu
786	65	Hemşin
787	65	İkizdere
788	65	İyidere
789	65	Kalkandere
790	65	Merkez
791	65	Pazar
792	66	Adapazarı
793	66	Akyazı
794	66	Arifiye
795	66	Erenler
796	66	Ferizli
797	66	Geyve
798	66	Hendek
799	66	Karapürçek
800	66	Karasu
801	66	Kaynarca
802	66	Kocaali
803	66	Pamukova
804	66	Sapanca
805	66	Serdivan
806	66	Söğütlü
807	66	Taraklı
808	67	19 Mayıs
809	67	Alaçam
810	67	Asarcık
811	67	Atakum
812	67	Ayvacık
813	67	Bafra
814	67	Canik
815	67	Çarşamba
816	67	Havza
817	67	İlkadım
818	67	Kavak
819	67	Ladik
820	67	Salıpazarı
821	67	Tekkeköy
822	67	Terme
823	67	Vezirköprü
824	67	Yakakent
825	68	Baykan
826	68	Eruh
827	68	Kurtalan
828	68	Merkez
829	68	Pervari
830	68	Şirvan
831	68	Tillo
832	69	Ayancık
833	69	Boyabat
834	69	Dikmen
835	69	Durağan
836	69	Erfelek
837	69	Gerze
838	69	Merkez
839	69	Saraydüzü
840	69	Türkeli
841	70	Akıncılar
842	70	Altınyayla
843	70	Divriği
844	70	Doğanşar
845	70	Gemerek
846	70	Gölova
847	70	Gürün
848	70	Hafik
849	70	İmranlı
850	70	Kangal
851	70	Koyulhisar
852	70	Merkez
853	70	Suşehri
854	70	Şarkışla
855	70	Ulaş
856	70	Yıldızeli
857	70	Zara
858	71	Akçakale
859	71	Birecik
860	71	Bozova
861	71	Ceylanpınar
862	71	Eyyübiye
863	71	Halfeti
864	71	Haliliye
865	71	Harran
866	71	Hilvan
867	71	Karaköprü
868	71	Siverek
869	71	Suruç
870	71	Viranşehir
871	72	Beytüşşebap
872	72	Cizre
873	72	Güçlükonak
874	72	İdil
875	72	Merkez
876	72	Silopi
877	72	Uludere
878	73	Çerkezköy
879	73	Çorlu
880	73	Ergene
881	73	Hayrabolu
882	73	Kapaklı
883	73	Malkara
884	73	Marmaraereğlisi
885	73	Muratlı
886	73	Saray
887	73	Süleymanpaşa
888	73	Şarköy
889	74	Almus
890	74	Artova
891	74	Başçiftlik
892	74	Erbaa
893	74	Merkez
894	74	Niksar
895	74	Pazar
896	74	Reşadiye
897	74	Sulusaray
898	74	Turhal
899	74	Yeşilyurt
900	74	Zile
901	75	Akçaabat
902	75	Araklı
903	75	Arsin
904	75	Beşikdüzü
905	75	Çarşıbaşı
906	75	Çaykara
907	75	Dernekpazarı
908	75	Düzköy
909	75	Hayrat
910	75	Köprübaşı
911	75	Maçka
912	75	Of
913	75	Ortahisar
914	75	Sürmene
915	75	Şalpazarı
916	75	Tonya
917	75	Vakfıkebir
918	75	Yomra
919	76	Çemişgezek
920	76	Hozat
921	76	Mazgirt
922	76	Merkez
923	76	Nazımiye
924	76	Ovacık
925	76	Pertek
926	76	Pülümür
927	77	Banaz
928	77	Eşme
929	77	Karahallı
930	77	Merkez
931	77	Sivaslı
932	77	Ulubey
933	78	Bahçesaray
934	78	Başkale
935	78	Çaldıran
936	78	Çatak
937	78	Edremit
938	78	Erciş
939	78	Gevaş
940	78	Gürpınar
941	78	İpekyolu
942	78	Muradiye
943	78	Özalp
944	78	Saray
945	78	Tuşba
946	79	Altınova
947	79	Armutlu
948	79	Çınarcık
949	79	Çiftlikköy
950	79	Merkez
951	79	Termal
952	80	Akdağmadeni
953	80	Aydıncık
954	80	Boğazlıyan
955	80	Çandır
956	80	Çayıralan
957	80	Çekerek
958	80	Kadışehri
959	80	Merkez
960	80	Saraykent
961	80	Sarıkaya
962	80	Sorgun
963	80	Şefaatli
964	80	Yenifakılı
965	80	Yerköy
966	81	Alaplı
967	81	Çaycuma
968	81	Devrek
969	81	Ereğli
970	81	Gökçebey
971	81	Kilimli
972	81	Kozlu
973	81	Merkez
\.


--
-- TOC entry 3472 (class 0 OID 17302)
-- Dependencies: 233
-- Data for Name: iller; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.iller (id, il_adi) FROM stdin;
1	Adana
2	Adıyaman
3	Afyonkarahisar
4	Ağrı
5	Aksaray
6	Amasya
7	Ankara
8	Antalya
9	Ardahan
10	Artvin
11	Aydın
12	Balıkesir
13	Bartın
14	Batman
15	Bayburt
16	Bilecik
17	Bingöl
18	Bitlis
19	Bolu
20	Burdur
21	Bursa
22	Çanakkale
23	Çankırı
24	Çorum
25	Denizli
26	Diyarbakır
27	Düzce
28	Edirne
29	Elazığ
30	Erzincan
31	Erzurum
32	Eskişehir
33	Gaziantep
34	Giresun
35	Gümüşhane
36	Hakkari
37	Hatay
38	Iğdır
39	Isparta
40	İstanbul
41	İzmir
42	Kahramanmaraş
43	Karabük
44	Karaman
45	Kars
46	Kastamonu
47	Kayseri
48	Kırıkkale
49	Kırklareli
50	Kırşehir
51	Kilis
52	Kocaeli
53	Konya
54	Kütahya
55	Malatya
56	Manisa
57	Mardin
58	Mersin
59	Muğla
60	Muş
61	Nevşehir
62	Niğde
63	Ordu
64	Osmaniye
65	Rize
66	Sakarya
67	Samsun
68	Siirt
69	Sinop
70	Sivas
71	Şanlıurfa
72	Şırnak
73	Tekirdağ
74	Tokat
75	Trabzon
76	Tunceli
77	Uşak
78	Van
79	Yalova
80	Yozgat
81	Zonguldak
\.


--
-- TOC entry 3462 (class 0 OID 17195)
-- Dependencies: 223
-- Data for Name: owner; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.owner (id, username, password, companyid, auth_key, mail, verification_code, "phoneNumber") FROM stdin;
2	owner2	6cf615d5bcaac778352a8f1f3360d23f02f34ec182e259897fd6ce485d7870d4	1	\N	owner2@example.com	\N	\N
1	owner1	0b14d501a594442a01c6859541bcb3e8164d183d32937b851835442f69d5c94e	1	\N	owner1@example.com	\N	\N
3	owner3	5906ac361a137e2d286465cd6588ebb5ac3f5ae955001100bc41577c3d751764	2	\N	owner3@example.com	\N	\N
5	owner5	8b2c86ea9cf2ea4eb517fd1e06b74f399e7fec0fef92e3b482a6cf2e2b092023	1	eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJvd25lcjUiLCJpYXQiOjE3MjI2ODk3OTl9.w1ofkNxRf4KSAXvik-LRPCfNfv4GqecupQeh1pRNh95cBThaYsWzxFubOuU3UmRRT1K4Lvl1hfcZNjBGhFmnlA	owner5@example.com	\N	\N
4	owner4	b97873a40f73abedd8d685a7cd5e5f85e4a9cfb83eac26886640a0813850122b	2	\N	owner4@example.com	\N	\N
\.


--
-- TOC entry 3470 (class 0 OID 17254)
-- Dependencies: 231
-- Data for Name: passenger; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.passenger (id, ticketid, seatid, tripid, gender) FROM stdin;
1	1	1	1	Male
2	2	2	1	Female
3	3	3	2	Male
4	4	4	2	Female
5	1	1	1	Male
6	2	2	1	Female
7	3	3	2	Male
8	4	4	2	Female
9	1	1	1	Male
10	2	2	1	Female
11	3	3	2	Male
12	4	4	2	Female
\.


--
-- TOC entry 3466 (class 0 OID 17224)
-- Dependencies: 227
-- Data for Name: seats; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.seats (id, no, busid) FROM stdin;
1	1	1
2	2	1
3	3	1
4	4	1
5	1	2
6	2	2
7	3	2
8	4	2
9	5	2
10	6	2
11	1	3
12	2	3
13	3	3
14	4	3
15	5	3
16	6	3
17	7	3
18	8	3
19	1	4
20	2	4
147	1	34
148	2	34
149	3	34
150	4	34
151	5	34
152	6	34
153	7	34
154	8	34
155	9	34
156	10	34
157	11	34
158	12	34
171	1	36
172	2	36
173	3	36
174	4	36
175	5	36
176	6	36
177	7	36
178	8	36
179	9	36
180	10	36
181	11	36
182	12	36
126	1	30
127	2	30
128	3	30
\.


--
-- TOC entry 3468 (class 0 OID 17236)
-- Dependencies: 229
-- Data for Name: tickets; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.tickets (id, customerid, tripid, printdate, checkoutdate, purchasedate, invoiceid, "from", "to", seatid) FROM stdin;
1	1	1	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	b6ba4ead-bc05-4464-b10c-e9251eac0ae3	\N	\N	\N
2	2	1	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	72cf59aa-424a-4d04-8afa-f557855ff96a	\N	\N	\N
3	3	2	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	8f44eb48-befe-4d88-8e4a-21f079954a69	\N	\N	\N
4	4	2	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	2024-07-09 11:08:53.844565	ac1ef4e3-3640-434e-9db0-52497079352b	\N	\N	\N
8	5	1	2024-07-30 18:00:00	2024-07-30 19:00:00	2024-07-29 15:00:00	c9eb0f7b-4d6c-41c5-9c12-3c6e9d63b3d0	New York	Los Angeles	12
9	5	1	2024-07-30 18:00:00	2024-07-30 19:00:00	2024-07-29 15:00:00	c9eb0f7b-4d6c-41c5-9c12-3c6e9d63b3d0	New York	Los Angeles	12
15	5	22	2024-08-01 11:47:19.465	2024-10-30 15:30:00	2024-08-01 11:47:19.465	8141ba2b-d9bc-4466-b24f-8f3312a801e5	Ankara	Adana	2
16	5	22	2024-08-01 11:32:50.908	2024-10-30 12:30:00	2024-08-01 11:32:50.908	aba0e94b-c130-4617-bf4b-d8958629c660	Ankara	Adana	2
17	5	33	2024-08-02 06:30:47.735	2024-08-10 11:00:00	2024-08-02 06:30:47.735	68f42d82-9d5d-4e0c-a191-3cf78415787a	Ankara	Adana	3
\.


--
-- TOC entry 3464 (class 0 OID 17207)
-- Dependencies: 225
-- Data for Name: trips; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.trips (id, peronno, price, companyid, busid, estimatedtime, departuretime, state, departure_location_id, arrival_location_id) FROM stdin;
35	P1231	30.00	1	1	300	2024-08-31 15:50:00	Aktif	Adana	Ankara
32	PN009	280.00	1	1	850	2024-08-09 09:00:00	İptal	İzmir	Adana
31	PN008	200.00	1	1	600	2024-08-08 12:00:00	İptal	İstanbul	Konya
1	P01	50.00	1	1	\N	\N	\N	101	201
2	P02	75.00	2	2	\N	\N	\N	102	202
23	PN123	150.00	1	1	300	2024-08-01 15:00:00	Aktif	İstanbul	Ankara
24	PN001	180.00	1	1	450	2024-08-01 09:00:00	Aktif	İstanbul	Ankara
25	PN002	220.00	1	1	600	2024-08-02 11:00:00	Aktif	İstanbul	İzmir
26	PN003	200.00	1	1	540	2024-08-03 13:00:00	Aktif	Ankara	İzmir
27	PN004	90.00	1	1	150	2024-08-04 10:00:00	Aktif	İstanbul	Bursa
28	PN005	250.00	1	1	720	2024-08-05 15:00:00	Aktif	İzmir	Antalya
29	PN006	230.00	1	1	660	2024-08-06 08:00:00	Aktif	Ankara	Antalya
30	PN007	270.00	1	1	830	2024-08-07 16:00:00	Aktif	İstanbul	Adana
33	PN010	260.00	1	1	790	2024-08-10 14:00:00	Aktif	Ankara	Adana
22	P123	29.99	1	1	120	2024-10-30 15:30:00	İptal	Ankara	Adana
34	P123	29.90	1	1	300	2024-09-21 20:15:00	İptal	Adana	Ankara
\.


--
-- TOC entry 3492 (class 0 OID 0)
-- Dependencies: 220
-- Name: bus_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.bus_id_seq', 37, true);


--
-- TOC entry 3493 (class 0 OID 0)
-- Dependencies: 218
-- Name: companies_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.companies_id_seq', 2, true);


--
-- TOC entry 3494 (class 0 OID 0)
-- Dependencies: 216
-- Name: customer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.customer_id_seq', 88, true);


--
-- TOC entry 3495 (class 0 OID 0)
-- Dependencies: 234
-- Name: ilceler_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ilceler_id_seq', 1, false);


--
-- TOC entry 3496 (class 0 OID 0)
-- Dependencies: 232
-- Name: iller_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.iller_id_seq', 1, false);


--
-- TOC entry 3497 (class 0 OID 0)
-- Dependencies: 222
-- Name: owner_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.owner_id_seq', 5, true);


--
-- TOC entry 3498 (class 0 OID 0)
-- Dependencies: 230
-- Name: passenger_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.passenger_id_seq', 12, true);


--
-- TOC entry 3499 (class 0 OID 0)
-- Dependencies: 226
-- Name: seats_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.seats_id_seq', 194, true);


--
-- TOC entry 3500 (class 0 OID 0)
-- Dependencies: 228
-- Name: tickets_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tickets_id_seq', 17, true);


--
-- TOC entry 3501 (class 0 OID 0)
-- Dependencies: 224
-- Name: trips_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.trips_id_seq', 35, true);


--
-- TOC entry 3287 (class 2606 OID 17188)
-- Name: bus bus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus
    ADD CONSTRAINT bus_pkey PRIMARY KEY (id);


--
-- TOC entry 3285 (class 2606 OID 17181)
-- Name: companies companies_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.companies
    ADD CONSTRAINT companies_pkey PRIMARY KEY (id);


--
-- TOC entry 3283 (class 2606 OID 17174)
-- Name: customer customer_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.customer
    ADD CONSTRAINT customer_pkey PRIMARY KEY (id);


--
-- TOC entry 3301 (class 2606 OID 17314)
-- Name: ilceler ilceler_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ilceler
    ADD CONSTRAINT ilceler_pkey PRIMARY KEY (id);


--
-- TOC entry 3299 (class 2606 OID 17307)
-- Name: iller iller_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.iller
    ADD CONSTRAINT iller_pkey PRIMARY KEY (id);


--
-- TOC entry 3289 (class 2606 OID 17200)
-- Name: owner owner_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_pkey PRIMARY KEY (id);


--
-- TOC entry 3297 (class 2606 OID 17259)
-- Name: passenger passenger_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT passenger_pkey PRIMARY KEY (id);


--
-- TOC entry 3293 (class 2606 OID 17229)
-- Name: seats seats_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats
    ADD CONSTRAINT seats_pkey PRIMARY KEY (id);


--
-- TOC entry 3295 (class 2606 OID 17242)
-- Name: tickets tickets_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_pkey PRIMARY KEY (id);


--
-- TOC entry 3291 (class 2606 OID 17212)
-- Name: trips trips_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trips
    ADD CONSTRAINT trips_pkey PRIMARY KEY (id);


--
-- TOC entry 3302 (class 2606 OID 17189)
-- Name: bus bus_companyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.bus
    ADD CONSTRAINT bus_companyid_fkey FOREIGN KEY (companyid) REFERENCES public.companies(id);


--
-- TOC entry 3312 (class 2606 OID 17315)
-- Name: ilceler ilceler_il_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ilceler
    ADD CONSTRAINT ilceler_il_id_fkey FOREIGN KEY (il_id) REFERENCES public.iller(id);


--
-- TOC entry 3303 (class 2606 OID 17201)
-- Name: owner owner_companyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.owner
    ADD CONSTRAINT owner_companyid_fkey FOREIGN KEY (companyid) REFERENCES public.companies(id);


--
-- TOC entry 3309 (class 2606 OID 17265)
-- Name: passenger passenger_seatid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT passenger_seatid_fkey FOREIGN KEY (seatid) REFERENCES public.seats(id);


--
-- TOC entry 3310 (class 2606 OID 17260)
-- Name: passenger passenger_ticketid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT passenger_ticketid_fkey FOREIGN KEY (ticketid) REFERENCES public.tickets(id);


--
-- TOC entry 3311 (class 2606 OID 17270)
-- Name: passenger passenger_tripid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.passenger
    ADD CONSTRAINT passenger_tripid_fkey FOREIGN KEY (tripid) REFERENCES public.trips(id);


--
-- TOC entry 3306 (class 2606 OID 17230)
-- Name: seats seats_busid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.seats
    ADD CONSTRAINT seats_busid_fkey FOREIGN KEY (busid) REFERENCES public.bus(id);


--
-- TOC entry 3307 (class 2606 OID 17243)
-- Name: tickets tickets_customerid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_customerid_fkey FOREIGN KEY (customerid) REFERENCES public.customer(id);


--
-- TOC entry 3308 (class 2606 OID 17248)
-- Name: tickets tickets_tripid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tickets
    ADD CONSTRAINT tickets_tripid_fkey FOREIGN KEY (tripid) REFERENCES public.trips(id);


--
-- TOC entry 3304 (class 2606 OID 17218)
-- Name: trips trips_busid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trips
    ADD CONSTRAINT trips_busid_fkey FOREIGN KEY (busid) REFERENCES public.bus(id);


--
-- TOC entry 3305 (class 2606 OID 17213)
-- Name: trips trips_companyid_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.trips
    ADD CONSTRAINT trips_companyid_fkey FOREIGN KEY (companyid) REFERENCES public.companies(id);


-- Completed on 2024-08-03 16:06:55

--
-- PostgreSQL database dump complete
--

