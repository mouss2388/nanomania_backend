-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  sam. 08 fév. 2020 à 22:50
-- Version du serveur :  10.3.16-MariaDB
-- Version de PHP :  7.3.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `nanomania-boutique`
--

-- --------------------------------------------------------

--
-- Structure de la table `editor`
--

CREATE TABLE `editor` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `editor`
--

INSERT INTO `editor` (`id`, `email`, `name`) VALUES
(1, 'nintendo@hotmail.com', 'NINTENDO'),
(2, 'namco@gmail.com', 'NAMCO'),
(3, 'sony@outlook.fr', 'SONY COMPUTER ENTERTAINMENT'),
(4, 'ubi@hotmail.fr', 'UBISOFT'),
(5, 'thq@gmail.com', 'THQ'),
(6, 'naughty@outlook.fr', 'NAUGHTY DOG'),
(7, 'rockstar@gmail.com', 'ROCKSTAR GAMES'),
(8, 'capcom@survivor.com', 'CAPCOM');

-- --------------------------------------------------------

--
-- Structure de la table `genre`
--

CREATE TABLE `genre` (
  `id` int(11) NOT NULL,
  `label` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `genre`
--

INSERT INTO `genre` (`id`, `label`) VALUES
(1, 'Action'),
(2, 'Aventure'),
(3, 'Beat them all'),
(4, 'Combat'),
(5, 'Course'),
(6, 'GTA-like'),
(7, 'Infiltration'),
(8, 'Plates-formes'),
(9, 'Sport'),
(10, 'Survival horror'),
(11, 'FPS'),
(12, 'infiltration');

-- --------------------------------------------------------

--
-- Structure de la table `image`
--

CREATE TABLE `image` (
  `id` int(11) NOT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_name` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `storage_id` varchar(255) DEFAULT NULL,
  `thumb_storage_id` varchar(255) DEFAULT NULL,
  `video_game_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `image`
--

INSERT INTO `image` (`id`, `content_type`, `file_name`, `nom`, `storage_id`, `thumb_storage_id`, `video_game_id`) VALUES
(1, 'image/jpeg', 'super-mario-galaxy-2-wii-e21876.jpg', 'super-mario-galaxy-2-wii-e21876.jpg', '89276d383dfe8755f2f637d97fb277037b369d9b', '24d3a05b035ee92b1d8afccab5dc87ea0961d103', 1),
(2, 'image/jpeg', 'resident-evil-4-us-e168946.jpg', 'resident-evil-4-us-e168946.jpg', 'b2159510acec1b674c1d5d41d714f9cb8ac02773', '0118edca095df42f3ed760a6ef67e8ad091cdd49', 2);

-- --------------------------------------------------------

--
-- Structure de la table `platform`
--

CREATE TABLE `platform` (
  `id` int(11) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `platform`
--

INSERT INTO `platform` (`id`, `brand`, `name`) VALUES
(1, 'MICROSOFT', 'xbox one'),
(2, 'SONY', 'playstation 4'),
(3, 'NINTENDO', 'switch'),
(4, 'DELL/ASUS/ACER/ALIENWARE/MSI', 'computer'),
(5, 'NINTENDO', 'wii / wii U');

-- --------------------------------------------------------

--
-- Structure de la table `video_game`
--

CREATE TABLE `video_game` (
  `id` int(11) NOT NULL,
  `date_release` date DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `editor_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `video_game`
--

INSERT INTO `video_game` (`id`, `date_release`, `name`, `editor_id`) VALUES
(1, '2010-05-23', 'Mario Galaxy 2', 1),
(2, '2005-01-11', 'resident evil 4', 8);

-- --------------------------------------------------------

--
-- Structure de la table `video_game_genres`
--

CREATE TABLE `video_game_genres` (
  `video_games_id` int(11) NOT NULL,
  `genres_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `video_game_genres`
--

INSERT INTO `video_game_genres` (`video_games_id`, `genres_id`) VALUES
(1, 8),
(2, 10);

-- --------------------------------------------------------

--
-- Structure de la table `video_game_platforms`
--

CREATE TABLE `video_game_platforms` (
  `video_games_id` int(11) NOT NULL,
  `platforms_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `video_game_platforms`
--

INSERT INTO `video_game_platforms` (`video_games_id`, `platforms_id`) VALUES
(1, 3),
(1, 5),
(2, 1),
(2, 2);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `editor`
--
ALTER TABLE `editor`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `image`
--
ALTER TABLE `image`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK26nuflv3856syy3pqhla4es70` (`video_game_id`);

--
-- Index pour la table `platform`
--
ALTER TABLE `platform`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `video_game`
--
ALTER TABLE `video_game`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeq5slw6kvum6vxw5v2gj0bq9i` (`editor_id`);

--
-- Index pour la table `video_game_genres`
--
ALTER TABLE `video_game_genres`
  ADD PRIMARY KEY (`video_games_id`,`genres_id`),
  ADD KEY `FKlk9jgt1t89iwdb3xi0e2qdlh9` (`genres_id`);

--
-- Index pour la table `video_game_platforms`
--
ALTER TABLE `video_game_platforms`
  ADD PRIMARY KEY (`video_games_id`,`platforms_id`),
  ADD KEY `FKio7q1ioguo3sk1yutwr98ei52` (`platforms_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `editor`
--
ALTER TABLE `editor`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `image`
--
ALTER TABLE `image`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `platform`
--
ALTER TABLE `platform`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT pour la table `video_game`
--
ALTER TABLE `video_game`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `image`
--
ALTER TABLE `image`
  ADD CONSTRAINT `FK26nuflv3856syy3pqhla4es70` FOREIGN KEY (`video_game_id`) REFERENCES `video_game` (`id`);

--
-- Contraintes pour la table `video_game`
--
ALTER TABLE `video_game`
  ADD CONSTRAINT `FKeq5slw6kvum6vxw5v2gj0bq9i` FOREIGN KEY (`editor_id`) REFERENCES `editor` (`id`);

--
-- Contraintes pour la table `video_game_genres`
--
ALTER TABLE `video_game_genres`
  ADD CONSTRAINT `FKlk9jgt1t89iwdb3xi0e2qdlh9` FOREIGN KEY (`genres_id`) REFERENCES `genre` (`id`),
  ADD CONSTRAINT `FKp6oqdy1i1xex5uf26fv0gkoqk` FOREIGN KEY (`video_games_id`) REFERENCES `video_game` (`id`);

--
-- Contraintes pour la table `video_game_platforms`
--
ALTER TABLE `video_game_platforms`
  ADD CONSTRAINT `FKio7q1ioguo3sk1yutwr98ei52` FOREIGN KEY (`platforms_id`) REFERENCES `platform` (`id`),
  ADD CONSTRAINT `FKk46dhtgsyekskuy645d0olubo` FOREIGN KEY (`video_games_id`) REFERENCES `video_game` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
