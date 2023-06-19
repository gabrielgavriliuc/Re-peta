-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 08, 2023 alle 15:57
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `progetto_ium_tweb_2022_2023`
--
CREATE DATABASE IF NOT EXISTS `progetto_ium_tweb_2022_2023` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `progetto_ium_tweb_2022_2023`;

-- --------------------------------------------------------
--
-- Dropping in Atlanta Abemus Papae
--

DROP TABLE IF EXISTS `Docenti-Corsi`;
DROP TABLE IF EXISTS `Docenti-Ripetizioni`;
DROP TABLE IF EXISTS `Prenotazioni`;
DROP TABLE IF EXISTS `Ripetizioni`;
DROP TABLE IF EXISTS `Docenti`;
DROP TABLE IF EXISTS `Corsi`;
DROP TABLE IF EXISTS `Utenti`;

--
-- Struttura della tabella `corsi`
--

DROP TABLE IF EXISTS `corsi`;
CREATE TABLE `corsi` (
  `Titolo` varchar(100) NOT NULL,
  `CFU` int(2) NOT NULL,
  `Stato` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `corsi`
--

INSERT INTO `corsi` (`Titolo`, `CFU`, `Stato`) VALUES
('Algoritmi e Strutture Dati', 9, 1),
('Analisi Matematica', 9, 1),
('Architettura degli Elaboratori', 9, 1),
('Basi di Dati', 9, 1),
('Calcolo Matriciale', 6, 1),
('Economia e Gestione dell\'Impresa e Diritto', 9, 1),
('Elementi di Probabilità e Statistica', 6, 1),
('Fisica', 6, 1),
('Interazione Uomo Macchina e Tecnologie WEB', 12, 1),
('Lingua Inglese I', 3, 1),
('Linguaggi e Paradigmi di Programmazione', 6, 1),
('Linguaggi Formali e Traduttori', 9, 1),
('Matematica Discreta e Logica', 12, 1),
('Programmazione I', 9, 1),
('Programmazione II', 9, 1),
('Programmazione III', 6, 1),
('Reti I', 6, 1),
('Sicurezza', 6, 0),
('Sistemi Intelligenti', 6, 1),
('Sistemi Operativi', 12, 1),
('Sviluppo delle Applicazioni Software', 9, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti`
--

DROP TABLE IF EXISTS `docenti`;
CREATE TABLE `docenti` (
  `Id` int(6) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `Stato` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenti`
--

INSERT INTO `docenti` (`Id`, `Nome`, `Cognome`, `Stato`) VALUES
(0, 'Tony', 'Effeil', 1),
(1, 'Emily', 'Rattosky', 1),
(2, 'Luigi', 'Eschimese', 1),
(3, 'Gianni', 'Paglia', 1),
(4, 'Rossella', 'Brescia', 1),
(5, 'Loredana', 'Rosina', 1),
(6, 'Katrina', 'Stilton', 1),
(7, 'Alfredo', 'Facaldo', 1),
(8, 'Leonardo', 'Dicheta', 1),
(9, 'Libero', 'Professionista', 1),
(10, 'Felice', 'Piango', 1),
(11, 'Alfonso', 'Davansi', 1),
(12, 'Loredana', 'Berte', 1),
(13, 'Pippo', 'Tutto', 1),
(14, 'Chad', 'Gipiti', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti-corsi`
--

DROP TABLE IF EXISTS `docenti-corsi`;
CREATE TABLE `docenti-corsi` (
  `Corso` varchar(100) NOT NULL,
  `Docente` int(6) NOT NULL,
  `Stato` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenti-corsi`
--

INSERT INTO `docenti-corsi` (`Corso`, `Docente`, `Stato`) VALUES
('Algoritmi e Strutture Dati', 7, 1),
('Algoritmi e Strutture Dati', 14, 1),
('Analisi Matematica', 1, 1),
('Analisi Matematica', 12, 1),
('Architettura degli Elaboratori', 2, 1),
('Architettura degli Elaboratori', 9, 1),
('Basi di Dati', 6, 1),
('Basi di Dati', 7, 1),
('Basi di Dati', 11, 1),
('Calcolo Matriciale', 10, 1),
('Calcolo Matriciale', 14, 1),
('Economia e Gestione dell\'Impresa e Diritto', 4, 1),
('Economia e Gestione dell\'Impresa e Diritto', 6, 1),
('Economia e Gestione dell\'Impresa e Diritto', 13, 1),
('Elementi di Probabilità e Statistica', 6, 1),
('Elementi di Probabilità e Statistica', 14, 1),
('Fisica', 8, 1),
('Interazione Uomo Macchina e Tecnologie WEB', 0, 1),
('Interazione Uomo Macchina e Tecnologie WEB', 12, 1),
('Interazione Uomo Macchina e Tecnologie WEB', 14, 1),
('Lingua Inglese I', 3, 1),
('Lingua Inglese I', 6, 1),
('Linguaggi e Paradigmi di Programmazione', 1, 0),
('Linguaggi e Paradigmi di Programmazione', 2, 1),
('Linguaggi Formali e Traduttori', 0, 1),
('Linguaggi Formali e Traduttori', 9, 1),
('Matematica Discreta e Logica', 8, 1),
('Matematica Discreta e Logica', 11, 1),
('Programmazione I', 10, 1),
('Programmazione I', 14, 1),
('Programmazione II', 7, 1),
('Programmazione II', 14, 1),
('Programmazione III', 6, 1),
('Programmazione III', 14, 1),
('Reti I', 1, 1),
('Reti I', 10, 1),
('Sicurezza', 7, 0),
('Sicurezza', 11, 0),
('Sistemi Intelligenti', 9, 1),
('Sistemi Operativi', 4, 1),
('Sistemi Operativi', 10, 1),
('Sistemi Operativi', 14, 1),
('Sviluppo delle Applicazioni Software', 3, 1),
('Sviluppo delle Applicazioni Software', 5, 1),
('Sviluppo delle Applicazioni Software', 12, 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `docenti-ripetizioni`
--

DROP TABLE IF EXISTS `docenti-ripetizioni`;
CREATE TABLE `docenti-ripetizioni` (
  `Docente` int(6) NOT NULL,
  `Ripetizione` int(6) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `docenti-ripetizioni`
--

INSERT INTO `docenti-ripetizioni` (`Docente`, `Ripetizione`) VALUES
(0, 6),
(0, 14),
(1, 0),
(1, 2),
(1, 16),
(1, 18),
(3, 10),
(6, 4),
(6, 8),
(6, 10),
(6, 17),
(7, 3),
(7, 5),
(7, 7),
(7, 8),
(7, 12),
(7, 17),
(8, 1),
(8, 9),
(8, 15),
(9, 6),
(9, 13),
(10, 2),
(10, 11),
(10, 18),
(10, 19),
(11, 3),
(11, 7),
(11, 8),
(11, 9),
(11, 17),
(12, 0),
(12, 14),
(12, 16),
(14, 4),
(14, 5),
(14, 11),
(14, 12),
(14, 14),
(14, 19);

-- --------------------------------------------------------

--
-- Struttura della tabella `prenotazioni`
--

DROP TABLE IF EXISTS `prenotazioni`;
CREATE TABLE `prenotazioni` (
  `Ripetizione` int(6) NOT NULL,
  `Docente` int(6) NOT NULL,
  `Utente` varchar(20) NOT NULL,
  `Stato` varchar(20) NOT NULL
) ;

-- --------------------------------------------------------

--
-- Struttura della tabella `ripetizioni`
--

DROP TABLE IF EXISTS `ripetizioni`;
CREATE TABLE `ripetizioni` (
  `Id` int(6) NOT NULL,
  `Giorno` varchar(20) NOT NULL,
  `OraInizio` varchar(2) NOT NULL,
  `OraFine` varchar(2) NOT NULL,
  `Corso` varchar(100) NOT NULL,
  `Stato` int(1) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `ripetizioni`
--

INSERT INTO `ripetizioni` (`Id`, `Giorno`, `OraInizio`, `OraFine`, `Corso`, `Stato`) VALUES
(0, '23/01/2023', '15', '16', 'Analisi Matematica', 1),
(1, '23/01/2023', '16', '17', 'Fisica', 1),
(2, '23/01/2023', '17', '18', 'Reti I', 1),
(3, '23/01/2023', '18', '19', 'Sicurezza', 0),
(4, '24/01/2023', '15', '16', 'Programmazione III', 1),
(5, '24/01/2023', '16', '17', 'Algoritmi e Strutture Dati', 1),
(6, '24/01/2023', '17', '18', 'Linguaggi Formali e Traduttori', 1),
(7, '24/01/2023', '18', '19', 'Sicurezza', 0),
(8, '25/01/2023', '15', '16', 'Basi di Dati', 1),
(9, '25/01/2023', '16', '17', 'Matematica Discreta e Logica', 1),
(10, '25/01/2023', '17', '18', 'Lingua Inglese I', 1),
(11, '25/01/2023', '18', '19', 'Calcolo Matriciale', 1),
(12, '26/01/2023', '15', '16', 'Algoritmi e Strutture Dati', 1),
(13, '26/01/2023', '16', '17', 'Sistemi Intelligenti', 1),
(14, '26/01/2023', '17', '18', 'Interazione Uomo Macchina e Tecnologie WEB', 1),
(15, '26/01/2023', '18', '19', 'Fisica', 1),
(16, '27/01/2023', '15', '16', 'Analisi Matematica', 1),
(17, '27/01/2023', '16', '17', 'Basi di Dati', 1),
(18, '27/01/2023', '17', '18', 'Reti I', 1),
(19, '27/01/2023', '18', '19', 'Calcolo Matriciale', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `utenti`
--

DROP TABLE IF EXISTS `utenti`;
CREATE TABLE `utenti` (
  `Username` varchar(20) NOT NULL,
  `Nome` varchar(20) NOT NULL,
  `Cognome` varchar(20) NOT NULL,
  `Password` varchar(20) NOT NULL,
  `Ruolo` varchar(7) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `utenti`
--

INSERT INTO `utenti` (`Username`, `Nome`, `Cognome`, `Password`, `Ruolo`) VALUES
('filippoJuvarra', 'Filippo', 'Juvarra', 'Tort1na', 'Cliente'),
('theoAdmin', 'Theo', 'Admin', 'Iamth3admin', 'Admin');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corsi`
--
ALTER TABLE `corsi`
  ADD PRIMARY KEY (`Titolo`);

--
-- Indici per le tabelle `docenti`
--
ALTER TABLE `docenti`
  ADD PRIMARY KEY (`Id`),
  ADD UNIQUE KEY `docenti_unici` (`Nome`,`Cognome`);

--
-- Indici per le tabelle `docenti-corsi`
--
ALTER TABLE `docenti-corsi`
  ADD PRIMARY KEY (`Corso`,`Docente`),
  ADD KEY `Docente` (`Docente`),
  ADD KEY `Corso` (`Corso`);

--
-- Indici per le tabelle `docenti-ripetizioni`
--
ALTER TABLE `docenti-ripetizioni`
  ADD PRIMARY KEY (`Docente`,`Ripetizione`),
  ADD KEY `Ripetizione` (`Ripetizione`),
  ADD KEY `Docente` (`Docente`);

--
-- Indici per le tabelle `prenotazioni`
--
ALTER TABLE `prenotazioni`
  ADD PRIMARY KEY (`Ripetizione`,`Docente`,`Utente`);

--
-- Indici per le tabelle `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD PRIMARY KEY (`Id`),
  ADD KEY `Corso` (`Corso`);

--
-- Indici per le tabelle `utenti`
--
ALTER TABLE `utenti`
  ADD PRIMARY KEY (`Username`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `docenti`
--
ALTER TABLE `docenti`
  MODIFY `Id` int(6) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `docenti-corsi`
--
ALTER TABLE `docenti-corsi`
  ADD CONSTRAINT `docenti-corsi_ibfk_1` FOREIGN KEY (`Docente`) REFERENCES `docenti` (`Id`),
  ADD CONSTRAINT `docenti-corsi_ibfk_2` FOREIGN KEY (`Corso`) REFERENCES `corsi` (`Titolo`);

--
-- Limiti per la tabella `docenti-ripetizioni`
--
ALTER TABLE `docenti-ripetizioni`
  ADD CONSTRAINT `docenti-ripetizioni_ibfk_1` FOREIGN KEY (`Docente`) REFERENCES `docenti` (`Id`),
  ADD CONSTRAINT `docenti-ripetizioni_ibfk_2` FOREIGN KEY (`Ripetizione`) REFERENCES `ripetizioni` (`Id`);

--
-- Limiti per la tabella `ripetizioni`
--
ALTER TABLE `ripetizioni`
  ADD CONSTRAINT `ripetizioni_ibfk_1` FOREIGN KEY (`Corso`) REFERENCES `corsi` (`Titolo`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
