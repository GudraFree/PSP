-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 16-02-2018 a las 22:26:25
-- Versión del servidor: 10.1.28-MariaDB
-- Versión de PHP: 7.1.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ahorcado`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `permiso`
--

CREATE TABLE `permiso` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `permiso`
--

INSERT INTO `permiso` (`nombre`) VALUES
('admin_query'),
('jugar'),
('query');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `nombre` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`nombre`) VALUES
('admin'),
('user');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol_tiene_per`
--

CREATE TABLE `rol_tiene_per` (
  `n_rol` varchar(20) NOT NULL,
  `n_per` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol_tiene_per`
--

INSERT INTO `rol_tiene_per` (`n_rol`, `n_per`) VALUES
('admin', 'admin_query'),
('admin', 'jugar'),
('admin', 'query'),
('user', 'jugar'),
('user', 'query');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `nick` varchar(20) NOT NULL,
  `pass` varchar(100) NOT NULL,
  `n_ganadas` int(11) DEFAULT '0',
  `n_jugadas` int(11) DEFAULT '0',
  `tiempo_juego` int(11) NOT NULL DEFAULT '0',
  `rol` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`nick`, `pass`, `n_ganadas`, `n_jugadas`, `tiempo_juego`, `rol`) VALUES
('admin', 'admin', 1, 2, 46959, 'admin'),
('Gudra', 'patata', 2, 2, 47777, 'admin'),
('user', 'user', 1, 1, 10480, 'user');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `permiso`
--
ALTER TABLE `permiso`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`nombre`);

--
-- Indices de la tabla `rol_tiene_per`
--
ALTER TABLE `rol_tiene_per`
  ADD PRIMARY KEY (`n_rol`,`n_per`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`nick`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
