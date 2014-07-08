-- phpMyAdmin SQL Dump
-- version 3.5.1
-- http://www.phpmyadmin.net
--
-- Client: localhost
-- Généré le: Ven 27 Juin 2014 à 06:58
-- Version du serveur: 5.5.24-log
-- Version de PHP: 5.3.13

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de données: `school_manager`
--

-- --------------------------------------------------------

--
-- Structure de la table `absence`
--

CREATE TABLE IF NOT EXISTS `absence` (
  `id_absence` int(11) NOT NULL AUTO_INCREMENT,
  `date_absence` varchar(16) DEFAULT NULL,
  `reason_absence` varchar(60) DEFAULT NULL,
  `id_student` int(11) NOT NULL,
  `id_dicipline` int(11) NOT NULL,
  PRIMARY KEY (`id_absence`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `name_admin` varchar(60) DEFAULT NULL,
  `lastName_admin` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `class`
--

CREATE TABLE IF NOT EXISTS `class` (
  `id_class` int(11) NOT NULL AUTO_INCREMENT,
  `promo_class` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_class`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `delay`
--

CREATE TABLE IF NOT EXISTS `delay` (
  `id_delay` int(11) NOT NULL AUTO_INCREMENT,
  `date_delay` varchar(16) DEFAULT NULL,
  `reason_delay` varchar(60) DEFAULT NULL,
  `id_student` int(11) NOT NULL,
  `id_dicipline` int(11) NOT NULL,
  PRIMARY KEY (`id_delay`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `dicipline`
--

CREATE TABLE IF NOT EXISTS `dicipline` (
  `id_dicipline` int(11) NOT NULL AUTO_INCREMENT,
  `libelle_dicipline` varchar(60) DEFAULT NULL,
  `coef_dicipline` double DEFAULT NULL,
  `id_class` int(11) DEFAULT NULL,
  `id_teacher` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_dicipline`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id_student` int(11) NOT NULL AUTO_INCREMENT,
  `LastName_student` varchar(60) DEFAULT NULL,
  `name_student` varchar(60) DEFAULT NULL,
  `id_class` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_student`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `support`
--

CREATE TABLE IF NOT EXISTS `support` (
  `id_support` int(11) NOT NULL AUTO_INCREMENT,
  `title_support` varchar(60) DEFAULT NULL,
  `date_support` varchar(16) DEFAULT NULL,
  `addr_support` text,
  `id_dicipline` int(11) NOT NULL,
  PRIMARY KEY (`id_support`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `teacher`
--

CREATE TABLE IF NOT EXISTS `teacher` (
  `id_teacher` int(11) NOT NULL AUTO_INCREMENT,
  `name_teacher` varchar(60) DEFAULT NULL,
  `LastName_teacher` varchar(60) DEFAULT NULL,
  PRIMARY KEY (`id_teacher`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int(11) NOT NULL AUTO_INCREMENT,
  `login_user` varchar(60) DEFAULT NULL,
  `pwd_user` varchar(60) DEFAULT NULL,
  `connexion_user` varchar(16) DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
