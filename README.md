# COM2008GroupProject
COM2008 Group Project - Team 28

CREATE TABLE `Account` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isCustomer` tinyint NOT NULL,
  `isStaff` tinyint NOT NULL,
  `isManager` tinyint NOT NULL,
  `holderID` int NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `userID` (`userID`),
  KEY `holderID_idx` (`holderID`),
  CONSTRAINT `holderID` FOREIGN KEY (`holderID`) REFERENCES `AccountHolder` (`holderID`)
)

CREATE TABLE `AccountHolder` (
  `holderID` int NOT NULL AUTO_INCREMENT,
  `forename` varchar(45) NOT NULL,
  `surname` varchar(45) NOT NULL,
  `houseNum` varchar(10) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  PRIMARY KEY (`holderID`),
  KEY `houseNum_idx` (`houseNum`),
  KEY `postcode_idx` (`postcode`),
  CONSTRAINT `houseNum` FOREIGN KEY (`houseNum`) REFERENCES `HolderAddress` (`houseNum`),
  CONSTRAINT `postcode` FOREIGN KEY (`postcode`) REFERENCES `HolderAddress` (`postcode`)
)

CREATE TABLE `BankDetails` (
  `cardBrand` varchar(45) NOT NULL,
  `cardNum` bigint NOT NULL,
  `cardExpiry` int NOT NULL,
  `securityCode` int NOT NULL,
  `userID` int NOT NULL,
  PRIMARY KEY (`cardNum`),
  KEY `cardNum` (`cardNum`) /*!80000 INVISIBLE */,
  KEY `userID_idx` (`userID`),
  CONSTRAINT `userID` FOREIGN KEY (`userID`) REFERENCES `Account` (`userID`)
)

CREATE TABLE `Controller` (
  `productID` int NOT NULL,
  `digital` tinyint NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `controller_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)

CREATE TABLE `Customer` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `hasBankDetails` tinyint NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `userID` (`userID`)
)


CREATE TABLE `HasLocomotive` (
  `pairID` int NOT NULL AUTO_INCREMENT,
  `setID` int NOT NULL,
  `partID` int NOT NULL,
  PRIMARY KEY (`pairID`),
  KEY `locomotive_setID_idx` (`setID`),
  KEY `locomotive_partID_idx` (`partID`),
  CONSTRAINT `locomotive_partID` FOREIGN KEY (`partID`) REFERENCES `Locomotive` (`productID`),
  CONSTRAINT `locomotive_setID` FOREIGN KEY (`setID`) REFERENCES `Train Sets` (`productID`)
)

CREATE TABLE `HasRollingStock` (
  `pairID` int NOT NULL AUTO_INCREMENT,
  `setID` int NOT NULL,
  `partID` int NOT NULL,
  PRIMARY KEY (`pairID`),
  KEY `rollingstock_setID_idx` (`setID`),
  KEY `rollingstock_partID_idx` (`partID`),
  CONSTRAINT `rollingstock_partID` FOREIGN KEY (`partID`) REFERENCES `Rolling Stock` (`productID`),
  CONSTRAINT `rollingstock_setID` FOREIGN KEY (`setID`) REFERENCES `Train Sets` (`productID`)
)


CREATE TABLE `HasTrack` (
  `pairID` int NOT NULL AUTO_INCREMENT,
  `setID` int NOT NULL,
  `partID` int NOT NULL,
  PRIMARY KEY (`pairID`),
  KEY `track_setID_idx` (`setID`),
  KEY `track_partID_idx` (`partID`),
  CONSTRAINT `track_partID` FOREIGN KEY (`partID`) REFERENCES `Track` (`productID`),
  CONSTRAINT `track_setID` FOREIGN KEY (`setID`) REFERENCES `Track Packs` (`productID`)
)


CREATE TABLE `HasTrackPack` (
  `pairID` int NOT NULL AUTO_INCREMENT,
  `setID` int NOT NULL,
  `partID` int NOT NULL,
  PRIMARY KEY (`pairID`),
  KEY `trackpack_setID_idx` (`setID`),
  KEY `trackpack_partID_idx` (`partID`),
  CONSTRAINT `trackpack_partID` FOREIGN KEY (`partID`) REFERENCES `Track Packs` (`productID`),
  CONSTRAINT `trackpack_setID` FOREIGN KEY (`setID`) REFERENCES `Train Sets` (`productID`)
)

CREATE TABLE `HolderAddress` (
  `houseNum` varchar(10) NOT NULL,
  `roadName` varchar(80) NOT NULL,
  `cityName` varchar(45) NOT NULL,
  `postcode` varchar(10) NOT NULL,
  PRIMARY KEY (`houseNum`,`postcode`),
  KEY `houseNum` (`houseNum`),
  KEY `postcode` (`postcode`)
)

CREATE TABLE `Inventory` (
  `productID` int NOT NULL,
  `stockLevel` int NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `inventory_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)

CREATE TABLE `Locomotive` (
  `productID` int NOT NULL,
  `codeDCC` varchar(45) NOT NULL,
  `era` varchar(45) NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `locomotive_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)


CREATE TABLE `Manager` (
  `userID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`userID`),
  KEY `userID` (`userID`)
)

CREATE TABLE `Order Line` (
  `orderID` int NOT NULL,
  `lineID` int NOT NULL,
  `productID` int NOT NULL,
  `quantity` int NOT NULL,
  PRIMARY KEY (`orderID`,`lineID`),
  KEY `orderline_productID_idx` (`productID`),
  CONSTRAINT `orderline_orderID` FOREIGN KEY (`orderID`) REFERENCES `Order` (`orderID`),
  CONSTRAINT `orderline_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)


CREATE TABLE `Order` (
  `orderID` int NOT NULL AUTO_INCREMENT,
  `userID` int NOT NULL,
  `date` int NOT NULL,
  `status` varchar(45) NOT NULL,
  PRIMARY KEY (`orderID`),
  KEY `order_productID_idx` (`userID`),
  CONSTRAINT `order_productID` FOREIGN KEY (`userID`) REFERENCES `Account` (`userID`)
)

CREATE TABLE `Product` (
  `productID` int NOT NULL AUTO_INCREMENT,
  `productCode` varchar(45) NOT NULL,
  `manufacturer` varchar(45) NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int NOT NULL,
  `gauge` varchar(45) NOT NULL,
  PRIMARY KEY (`productID`)
)


CREATE TABLE `Rolling Stock` (
  `productID` int NOT NULL,
  `era` varchar(45) NOT NULL,
  `isCarriage` tinyint NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `rollingstock_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)


CREATE TABLE `Staff` (
  `userID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`userID`),
  KEY `userID` (`userID`)
)

CREATE TABLE `Track Packs` (
  `productID` int NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `trackpack_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)

CREATE TABLE `Track` (
  `productID` int NOT NULL,
  PRIMARY KEY (`productID`),
  CONSTRAINT `track_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)

CREATE TABLE `Train Sets` (
  `productID` int NOT NULL,
  `controllerID` int NOT NULL,
  PRIMARY KEY (`productID`),
  KEY `trainset_controllerID_idx` (`controllerID`),
  CONSTRAINT `trainset_controllerID` FOREIGN KEY (`controllerID`) REFERENCES `Controller` (`productID`),
  CONSTRAINT `trainset_productID` FOREIGN KEY (`productID`) REFERENCES `Product` (`productID`)
)