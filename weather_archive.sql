CREATE DATABASE `weather_archive`;

USE `weather_archive`;
-- Create tables etc.

CREATE TABLE `weather_services` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `service_name` VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `cities` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `city_name` VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `countries` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `country_name` VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `regions` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `region_name` VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE `locations` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `location_name` VARCHAR(255) NOT NULL UNIQUE,
   `city_id` INT,
   `country_id` INT,
   `region_id` INT,
   `latitude` DOUBLE NOT NULL,
   `longitude` DOUBLE NOT NULL,
   FOREIGN KEY (`city_id`) REFERENCES `cities`(`id`),
   FOREIGN KEY (`country_id`) REFERENCES `countries`(`id`),
   FOREIGN KEY (`region_id`) REFERENCES `regions`(`id`)
);

CREATE TABLE `weather_data` (
   `id` INT AUTO_INCREMENT PRIMARY KEY,
   `temperature` DOUBLE NOT NULL,
   `pressure` DOUBLE NOT NULL,
   `humidity` DOUBLE NOT NULL,
   `wind_speed` DOUBLE NOT NULL,
   `wind_direction` DOUBLE NOT NULL,
   `location_id` INT NOT NULL,
   `service_id` INT NOT NULL,
   `date_time` DATETIME NOT NULL,
   FOREIGN KEY (`location_id`) REFERENCES `locations`(`id`), 
   FOREIGN KEY (`service_id`) REFERENCES `weather_services`(`id`)
);

INSERT INTO `weather_services`(`service_name`) VALUES('AccuWeather');
INSERT INTO `weather_services`(`service_name`) VALUES('OpenWeatherMap');

-- CREATE USER 'weather_user' IDENTIFIED BY 'wpass';
-- GRANT ALL on weather_archive.* TO 'weather_user';

INSERT INTO `cities`(`city_name`) VALUES('Tallinn');
INSERT INTO `cities`(`city_name`) VALUES('Tartu');
INSERT INTO `cities`(`city_name`) VALUES('Pärnu');
INSERT INTO `cities`(`city_name`) VALUES('Narva');
INSERT INTO `cities`(`city_name`) VALUES('Kuressaare');
INSERT INTO `cities`(`city_name`) VALUES('Helsinki');

INSERT INTO `countries`(`country_name`) VALUES('Estonia');
INSERT INTO `countries`(`country_name`) VALUES('Finland');

INSERT INTO `regions`(`region_name`) VALUES('Harju');
INSERT INTO `regions`(`region_name`) VALUES('Tartu');
INSERT INTO `regions`(`region_name`) VALUES('Ida-Viru');
INSERT INTO `regions`(`region_name`) VALUES('Pärnu');
INSERT INTO `regions`(`region_name`) VALUES('Saare');
INSERT INTO `regions`(`region_name`) VALUES('Uusimaa');

INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav1', 1, 1, 1, 59.397594, 24.672226);
INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav2', 2, 1, 2, 58.357256, 26.68221);
INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav3', 3, 1, 4, 58.390152, 24.495311);
INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav4', 4, 1, 3, 59.377379, 28.190137);
INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav5', 5, 1, 5, 58.252893, 22.484974);
INSERT INTO `locations`(`location_name`, `city_id`, `country_id`, `region_id`, `latitude`, `longitude`) VALUES('Fav6', 6, 2, 6, 60.169522, 24.95233);
