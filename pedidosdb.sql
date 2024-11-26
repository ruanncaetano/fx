--
-- PostgreSQL database dump
--

-- Dumped from database version 10.10
-- Dumped by pg_dump version 16.2

-- Started on 2024-11-14 08:46:59

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
-- TOC entry 6 (class 2615 OID 2200)
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

-- *not* creating schema, since initdb creates it


ALTER SCHEMA public OWNER TO postgres;

SET default_tablespace = '';

--
-- TOC entry 196 (class 1259 OID 213307)
-- Name: categoria; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.categoria (
    cat_id integer NOT NULL,
    cat_nome character varying(20)
);


ALTER TABLE public.categoria OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 213310)
-- Name: categoria_cat_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.categoria_cat_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.categoria_cat_id_seq OWNER TO postgres;

--
-- TOC entry 2858 (class 0 OID 0)
-- Dependencies: 197
-- Name: categoria_cat_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.categoria_cat_id_seq OWNED BY public.categoria.cat_id;


--
-- TOC entry 198 (class 1259 OID 213312)
-- Name: empresa; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.empresa (
    emp_id integer NOT NULL,
    emp_razao character varying(60),
    emp_fantasia character varying(50),
    emp_cnpj character varying(18),
    emp_cep character varying(9),
    emp_rua character varying(50),
    emp_numero character varying(8),
    emp_bairro character varying(30),
    emp_cidade character varying(30),
    emp_uf character varying(2),
    emp_fone character varying(15),
    emp_email character varying(80),
    emp_vlremb numeric(10,2)
);


ALTER TABLE public.empresa OWNER TO postgres;

--
-- TOC entry 199 (class 1259 OID 213315)
-- Name: empresa_emp_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.empresa_emp_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.empresa_emp_id_seq OWNER TO postgres;

--
-- TOC entry 2859 (class 0 OID 0)
-- Dependencies: 199
-- Name: empresa_emp_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.empresa_emp_id_seq OWNED BY public.empresa.emp_id;


--
-- TOC entry 200 (class 1259 OID 213317)
-- Name: item; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.item (
    pro_id integer NOT NULL,
    ped_id integer NOT NULL,
    it_quant integer,
    it_valor numeric(10,2)
);


ALTER TABLE public.item OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 213320)
-- Name: pedido; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pedido (
    ped_id integer NOT NULL,
    ped_data date,
    ped_clinome character varying(50),
    ped_clifone character varying(15),
    ped_total numeric(10,2),
    ped_viagem character(1),
    tpg_id integer
);


ALTER TABLE public.pedido OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 213323)
-- Name: pedido_ped_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pedido_ped_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.pedido_ped_id_seq OWNER TO postgres;

--
-- TOC entry 2860 (class 0 OID 0)
-- Dependencies: 202
-- Name: pedido_ped_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pedido_ped_id_seq OWNED BY public.pedido.ped_id;


--
-- TOC entry 203 (class 1259 OID 213325)
-- Name: produto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.produto (
    pro_id integer NOT NULL,
    pro_nome character varying(30),
    pro_descr text,
    pro_valor numeric(10,2),
    pro_foto bytea,
    cat_id integer
);


ALTER TABLE public.produto OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 213331)
-- Name: produto_pro_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.produto_pro_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.produto_pro_id_seq OWNER TO postgres;

--
-- TOC entry 2861 (class 0 OID 0)
-- Dependencies: 204
-- Name: produto_pro_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.produto_pro_id_seq OWNED BY public.produto.pro_id;


--
-- TOC entry 205 (class 1259 OID 213333)
-- Name: tipo_pagamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tipo_pagamento (
    tpg_id integer NOT NULL,
    tpg_nome character varying(20)
);


ALTER TABLE public.tipo_pagamento OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 213336)
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tipo_pagamento_tpg_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNER TO postgres;

--
-- TOC entry 2862 (class 0 OID 0)
-- Dependencies: 206
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tipo_pagamento_tpg_id_seq OWNED BY public.tipo_pagamento.tpg_id;


--
-- TOC entry 2699 (class 2604 OID 213338)
-- Name: categoria cat_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria ALTER COLUMN cat_id SET DEFAULT nextval('public.categoria_cat_id_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 213339)
-- Name: empresa emp_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa ALTER COLUMN emp_id SET DEFAULT nextval('public.empresa_emp_id_seq'::regclass);


--
-- TOC entry 2701 (class 2604 OID 213340)
-- Name: pedido ped_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido ALTER COLUMN ped_id SET DEFAULT nextval('public.pedido_ped_id_seq'::regclass);


--
-- TOC entry 2702 (class 2604 OID 213341)
-- Name: produto pro_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto ALTER COLUMN pro_id SET DEFAULT nextval('public.produto_pro_id_seq'::regclass);


--
-- TOC entry 2703 (class 2604 OID 213342)
-- Name: tipo_pagamento tpg_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento ALTER COLUMN tpg_id SET DEFAULT nextval('public.tipo_pagamento_tpg_id_seq'::regclass);


--
-- TOC entry 2841 (class 0 OID 213307)
-- Dependencies: 196
-- Data for Name: categoria; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.categoria VALUES (1, 'lanche');
INSERT INTO public.categoria VALUES (2, 'pizza');
INSERT INTO public.categoria VALUES (3, 'bebida');
INSERT INTO public.categoria VALUES (4, 'sorvete');


--
-- TOC entry 2843 (class 0 OID 213312)
-- Dependencies: 198
-- Data for Name: empresa; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2845 (class 0 OID 213317)
-- Dependencies: 200
-- Data for Name: item; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2846 (class 0 OID 213320)
-- Dependencies: 201
-- Data for Name: pedido; Type: TABLE DATA; Schema: public; Owner: postgres
--



--
-- TOC entry 2848 (class 0 OID 213325)
-- Dependencies: 203
-- Data for Name: produto; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.produto VALUES (1, 'X-Calabreza', 'calabreze, salada e cebola', 25.00, NULL, 1);
INSERT INTO public.produto VALUES (2, 'X-Mignon', 'file mignon, salada e molho especial', 35.00, NULL, 1);
INSERT INTO public.produto VALUES (3, 'Catufrango grande', '8 pedaços', 42.00, NULL, 2);
INSERT INTO public.produto VALUES (4, 'Margerita média', '4 pedaços', 29.00, NULL, 2);
INSERT INTO public.produto VALUES (5, 'Coca Lata', 'lata 350ml', 5.00, NULL, 3);
INSERT INTO public.produto VALUES (6, 'Heinecken Lata', '350ml', 8.00, NULL, 3);
INSERT INTO public.produto VALUES (7, 'Antarctica garrafa', 'garrafa com 600ml', 11.00, NULL, 3);


--
-- TOC entry 2850 (class 0 OID 213333)
-- Dependencies: 205
-- Data for Name: tipo_pagamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

INSERT INTO public.tipo_pagamento VALUES (1, 'PIX');
INSERT INTO public.tipo_pagamento VALUES (2, 'Dinheiro');
INSERT INTO public.tipo_pagamento VALUES (3, 'Cartão Débito');
INSERT INTO public.tipo_pagamento VALUES (4, 'Cartão Crédito');


--
-- TOC entry 2863 (class 0 OID 0)
-- Dependencies: 197
-- Name: categoria_cat_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.categoria_cat_id_seq', 4, true);


--
-- TOC entry 2864 (class 0 OID 0)
-- Dependencies: 199
-- Name: empresa_emp_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.empresa_emp_id_seq', 1, false);


--
-- TOC entry 2865 (class 0 OID 0)
-- Dependencies: 202
-- Name: pedido_ped_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pedido_ped_id_seq', 1, false);


--
-- TOC entry 2866 (class 0 OID 0)
-- Dependencies: 204
-- Name: produto_pro_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.produto_pro_id_seq', 7, true);


--
-- TOC entry 2867 (class 0 OID 0)
-- Dependencies: 206
-- Name: tipo_pagamento_tpg_id_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.tipo_pagamento_tpg_id_seq', 4, true);


--
-- TOC entry 2705 (class 2606 OID 213344)
-- Name: categoria categoria_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.categoria
    ADD CONSTRAINT categoria_pkey PRIMARY KEY (cat_id);


--
-- TOC entry 2707 (class 2606 OID 213346)
-- Name: empresa empresa_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (emp_id);


--
-- TOC entry 2709 (class 2606 OID 213348)
-- Name: item item_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pkey PRIMARY KEY (pro_id, ped_id);


--
-- TOC entry 2711 (class 2606 OID 213350)
-- Name: pedido pedido_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_pkey PRIMARY KEY (ped_id);


--
-- TOC entry 2713 (class 2606 OID 213352)
-- Name: produto produto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (pro_id);


--
-- TOC entry 2715 (class 2606 OID 213354)
-- Name: tipo_pagamento tipo_pagamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tipo_pagamento
    ADD CONSTRAINT tipo_pagamento_pkey PRIMARY KEY (tpg_id);


--
-- TOC entry 2716 (class 2606 OID 213355)
-- Name: item item_ped_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_ped_id_fkey FOREIGN KEY (ped_id) REFERENCES public.pedido(ped_id) NOT VALID;


--
-- TOC entry 2717 (class 2606 OID 213360)
-- Name: item item_pro_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.item
    ADD CONSTRAINT item_pro_id_fkey FOREIGN KEY (pro_id) REFERENCES public.produto(pro_id);


--
-- TOC entry 2718 (class 2606 OID 213365)
-- Name: pedido pedido_tpg_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pedido
    ADD CONSTRAINT pedido_tpg_id_fkey FOREIGN KEY (tpg_id) REFERENCES public.tipo_pagamento(tpg_id) NOT VALID;


--
-- TOC entry 2719 (class 2606 OID 213370)
-- Name: produto produto_cat_id_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_cat_id_fkey FOREIGN KEY (cat_id) REFERENCES public.categoria(cat_id);


--
-- TOC entry 2857 (class 0 OID 0)
-- Dependencies: 6
-- Name: SCHEMA public; Type: ACL; Schema: -; Owner: postgres
--

REVOKE USAGE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO PUBLIC;


-- Completed on 2024-11-14 08:46:59

--
-- PostgreSQL database dump complete
--

