-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 06 fév. 2020 à 21:34
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
-- Base de données :  `videogames`
--

-- --------------------------------------------------------

--
-- Structure de la table `editeur`
--

CREATE TABLE `editeur` (
  `id` int(11) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `editeur`
--

INSERT INTO `editeur` (`id`, `email`, `nom`) VALUES
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
  `libelle` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `genre`
--

INSERT INTO `genre` (`id`, `libelle`) VALUES
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
-- Structure de la table `jeux_video`
--

CREATE TABLE `jeux_video` (
  `id` int(11) NOT NULL,
  `date_sortie` date DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `editeur_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `jeux_video`
--

INSERT INTO `jeux_video` (`id`, `date_sortie`, `nom`, `editeur_id`) VALUES
(1, '2007-11-01', 'mario galaxy', 1),
(2, '2005-01-11', 'resident evil 4', 8);

-- --------------------------------------------------------

--
-- Structure de la table `jeux_video_genres`
--

CREATE TABLE `jeux_video_genres` (
  `jeux_videos_id` int(11) NOT NULL,
  `genres_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `jeux_video_genres`
--

INSERT INTO `jeux_video_genres` (`jeux_videos_id`, `genres_id`) VALUES
(1, 8),
(2, 10);

-- --------------------------------------------------------

--
-- Structure de la table `jeux_video_plateformes`
--

CREATE TABLE `jeux_video_plateformes` (
  `jeux_videos_id` int(11) NOT NULL,
  `plateformes_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `jeux_video_plateformes`
--

INSERT INTO `jeux_video_plateformes` (`jeux_videos_id`, `plateformes_id`) VALUES
(1, 5),
(2, 1),
(2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `plateforme`
--

CREATE TABLE `plateforme` (
  `id` int(11) NOT NULL,
  `marque` varchar(255) DEFAULT NULL,
  `nom` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `plateforme`
--

INSERT INTO `plateforme` (`id`, `marque`, `nom`) VALUES
(1, 'MICROSOFT', 'xbox one'),
(2, 'SONY', 'playstation 4'),
(3, 'NINTENDO', 'switch'),
(4, 'DELL/ASUS/ACER/ALIENWARE/MSI', 'computer'),
(5, 'NINTENDO', 'wii / wii U');

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `editeur`
--
ALTER TABLE `editeur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `genre`
--
ALTER TABLE `genre`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `jeux_video`
--
ALTER TABLE `jeux_video`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKeq5slw6kvum6vxw5v2gj0bq9i` (`editeur_id`);

--
-- Index pour la table `jeux_video_genres`
--
ALTER TABLE `jeux_video_genres`
  ADD PRIMARY KEY (`jeux_videos_id`,`genres_id`),
  ADD KEY `FKlk9jgt1t89iwdb3xi0e2qdlh9` (`genres_id`);

--
-- Index pour la table `jeux_video_plateformes`
--
ALTER TABLE `jeux_video_plateformes`
  ADD PRIMARY KEY (`jeux_videos_id`,`plateformes_id`),
  ADD KEY `FKio7q1ioguo3sk1yutwr98ei52` (`plateformes_id`);

--
-- Index pour la table `plateforme`
--
ALTER TABLE `plateforme`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `editeur`
--
ALTER TABLE `editeur`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `genre`
--
ALTER TABLE `genre`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `jeux_video`
--
ALTER TABLE `jeux_video`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT pour la table `plateforme`
--
ALTER TABLE `plateforme`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `jeux_video`
--
ALTER TABLE `jeux_video`
  ADD CONSTRAINT `FKeq5slw6kvum6vxw5v2gj0bq9i` FOREIGN KEY (`editeur_id`) REFERENCES `editeur` (`id`);

--
-- Contraintes pour la table `jeux_video_genres`
--
ALTER TABLE `jeux_video_genres`
  ADD CONSTRAINT `FKlk9jgt1t89iwdb3xi0e2qdlh9` FOREIGN KEY (`genres_id`) REFERENCES `genre` (`id`),
  ADD CONSTRAINT `FKp6oqdy1i1xex5uf26fv0gkoqk` FOREIGN KEY (`jeux_videos_id`) REFERENCES `jeux_video` (`id`);

--
-- Contraintes pour la table `jeux_video_plateformes`
--
ALTER TABLE `jeux_video_plateformes`
  ADD CONSTRAINT `FKio7q1ioguo3sk1yutwr98ei52` FOREIGN KEY (`plateformes_id`) REFERENCES `plateforme` (`id`),
  ADD CONSTRAINT `FKk46dhtgsyekskuy645d0olubo` FOREIGN KEY (`jeux_videos_id`) REFERENCES `jeux_video` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
