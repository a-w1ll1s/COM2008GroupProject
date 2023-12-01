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



INSERT INTO `Account` VALUES (7,'testcustomer@test.com','testcustomer',1,0,0,4),(8,'teststaff@test.com','teststaff',1,1,0,5),(9,'testmanager@test.com','testmanager',1,1,1,6);


INSERT INTO `AccountHolder` VALUES (4,'Test','Customer','01','ABC 001'),(5,'Test','Staff','02','ABC 002'),(6,'Test','Manager','03','ABC 003');

INSERT INTO `HolderAddress` VALUES ('01','Test Street','Sheffield','ABC 001'),('02','Test Street','Sheffield','ABC 002'),('03','Test Street','Sheffield','ABC 003');

INSERT INTO `Controller` VALUES (40,0),(41,0),(42,0),(43,1),(44,1),(45,1),(46,1),(47,1),(48,1);

INSERT INTO `HasLocomotive` VALUES (1,175,94),(2,176,95),(3,177,96),(4,178,52),(5,179,53),(6,180,54),(7,181,49),(8,182,50),(9,183,51),(10,184,79),(11,184,82),(12,185,80),(13,185,85),(14,186,81),(15,186,84);

INSERT INTO `HasRollingStock` VALUES (1,175,112),(2,175,139),(3,175,139),(4,176,113),(5,176,140),(6,176,140),(7,177,114),(8,177,141),(9,177,141),(10,178,157),(11,178,157),(12,178,166),(13,179,158),(14,179,158),(15,179,167),(16,180,159),(17,180,159),(18,180,168),(19,181,169),(20,181,145),(21,181,145),(22,182,170),(23,182,146),(24,182,146),(25,183,171),(26,183,147),(27,183,147),(28,184,115),(29,184,118),(30,184,124),(31,185,116),(32,185,119),(33,185,125),(34,186,117),(35,186,120),(36,186,126);

INSERT INTO `HasTrack` VALUES (1,187,19),(2,187,19),(3,187,19),(4,187,19),(5,187,19),(6,187,19),(7,187,19),(8,187,19),(9,187,1),(10,187,1),(11,188,20),(12,188,20),(13,188,20),(14,188,20),(15,188,20),(16,188,20),(17,188,20),(18,188,20),(19,188,2),(20,188,2),(21,189,21),(22,189,21),(23,189,21),(24,189,21),(25,189,21),(26,189,21),(27,189,21),(28,189,21),(29,189,3),(30,189,3),(31,190,22),(32,190,22),(33,190,22),(34,190,22),(35,190,22),(36,190,22),(37,190,22),(38,190,22),(39,190,1),(40,190,1),(41,191,23),(42,191,23),(43,191,23),(44,191,23),(45,191,23),(46,191,23),(47,191,23),(48,191,23),(49,191,2),(50,191,2),(51,192,24),(52,192,24),(53,192,24),(54,192,24),(55,192,24),(56,192,24),(57,192,24),(58,192,24),(59,192,3),(60,192,3),(61,193,25),(62,193,25),(63,193,25),(64,193,25),(65,193,25),(66,193,25),(67,193,25),(68,193,25),(69,193,1),(70,193,1),(71,194,26),(72,194,26),(73,194,26),(74,194,26),(75,194,26),(76,194,26),(77,194,26),(78,194,26),(79,194,2),(80,194,2),(81,195,27),(82,195,27),(83,195,27),(84,195,27),(85,195,27),(86,195,27),(87,195,27),(88,195,27),(89,195,3),(90,195,3),(91,196,1),(92,196,4),(93,196,13),(94,196,13),(95,196,28),(96,196,7),(97,197,2),(98,197,5),(99,197,14),(100,197,14),(101,197,29),(102,197,8),(103,198,3),(104,198,6),(105,198,15),(106,198,15),(107,198,30),(108,198,9),(109,199,1),(110,199,1),(111,199,13),(112,199,22),(113,199,22),(114,199,22),(115,199,22),(116,199,31),(117,199,7),(118,200,2),(119,200,2),(120,200,14),(121,200,23),(122,200,23),(123,200,23),(124,200,23),(125,200,32),(126,200,8),(127,201,3),(128,201,3),(129,201,15),(130,201,24),(131,201,24),(132,201,24),(133,201,24),(134,201,33),(135,201,9);

INSERT INTO `HasTrackPack` VALUES (49,175,187),(50,175,196),(51,176,188),(52,176,197),(53,177,189),(54,177,198),(55,178,190),(56,178,199),(57,179,191),(58,179,200),(59,180,192),(60,180,201),(61,181,190),(62,181,196),(63,182,191),(64,182,197),(65,183,192),(66,183,198),(67,184,193),(68,184,199),(69,185,194),(70,185,200),(71,186,195),(72,186,201);


INSERT INTO `Inventory` VALUES (1,10),(2,10),(3,10),(4,10),(5,10),(6,10),(7,10),(8,10),(9,10),(10,10),(11,10),(12,10),(13,10),(14,10),(15,10),(16,10),(17,10),(18,10),(19,10),(20,10),(21,10),(22,10),(23,10),(24,10),(25,10),(26,10),(27,10),(28,10),(29,10),(30,10),(31,10),(32,10),(33,10),(34,10),(35,10),(36,10),(37,10),(38,10),(39,10),(40,10),(41,10),(42,10),(43,10),(44,10),(45,10),(46,10),(47,10),(48,10),(49,10),(50,10),(51,10),(52,10),(53,10),(54,10),(55,10),(56,10),(57,10),(58,10),(59,10),(60,10),(61,10),(62,10),(63,10),(64,10),(65,10),(66,10),(67,10),(68,10),(69,10),(70,10),(71,10),(72,10),(73,10),(74,10),(75,10),(76,10),(77,10),(78,10),(79,10),(80,10),(81,10),(82,10),(83,10),(84,10),(85,10),(86,10),(87,10),(88,10),(89,10),(90,10),(91,10),(92,10),(93,10),(94,10),(95,10),(96,10),(97,10),(98,10),(99,10),(100,10),(101,10),(102,10),(103,10),(104,10),(105,10),(106,10),(107,10),(108,10),(109,10),(110,10),(111,10),(112,10),(113,10),(114,10),(115,10),(116,10),(117,10),(118,10),(119,10),(120,10),(121,10),(122,10),(123,10),(124,10),(125,10),(126,10),(127,10),(128,10),(129,10),(130,10),(131,10),(132,10),(133,10),(134,10),(135,10),(136,10),(137,10),(138,10),(139,10),(140,10),(141,10),(142,10),(143,10),(144,10),(145,10),(146,10),(147,10),(148,10),(149,10),(150,10),(151,10),(152,10),(153,10),(154,10),(155,10),(156,10),(157,10),(158,10),(159,10),(160,10),(161,10),(162,10),(163,10),(164,10),(165,10),(166,10),(167,10),(168,10),(169,10),(170,10),(171,10),(172,10),(173,10),(174,10);


INSERT INTO `Locomotive` VALUES (49,'Analogue','1-2'),(50,'Analogue','1-2'),(51,'Analogue','1-2'),(52,'Analogue','1-2'),(53,'Analogue','1-2'),(54,'Analogue','1-2'),(55,'Analogue','1-2'),(56,'Analogue','1-2'),(57,'Analogue','1-2'),(58,'Analogue','3-4'),(59,'Analogue','3-4'),(60,'Analogue','3-4'),(61,'DCC-Ready','3-4'),(62,'DCC-Ready','3-4'),(63,'DCC-Ready','3-4'),(64,'DCC-Ready','3-4'),(65,'DCC-Ready','3-4'),(66,'DCC-Ready','3-4'),(67,'DCC-Ready','5-6'),(68,'DCC-Ready','5-6'),(69,'DCC-Ready','5-6'),(70,'DCC-Ready','5-6'),(71,'DCC-Ready','5-6'),(72,'DCC-Ready','5-6'),(73,'DCC-Ready','5-6'),(74,'DCC-Ready','5-6'),(75,'DCC-Ready','5-6'),(76,'DCC-Fitted','7-8'),(77,'DCC-Fitted','7-8'),(78,'DCC-Fitted','7-8'),(79,'DCC-Fitted','7-8'),(80,'DCC-Fitted','7-8'),(81,'DCC-Fitted','7-8'),(82,'DCC-Fitted','7-8'),(83,'DCC-Fitted','7-8'),(84,'DCC-Fitted','7-8'),(85,'DCC-Fitted','9-10'),(86,'DCC-Fitted','9-10'),(87,'DCC-Fitted','9-10'),(88,'DCC-Fitted','9-10'),(89,'DCC-Fitted','9-10'),(90,'DCC-Fitted','9-10'),(91,'DCC-Sound','9-10'),(92,'DCC-Sound','9-10'),(93,'DCC-Sound','9-10'),(94,'DCC-Sound','11'),(95,'DCC-Sound','11'),(96,'DCC-Sound','11'),(97,'DCC-Sound','11'),(98,'DCC-Sound','11'),(99,'DCC-Sound','11'),(100,'DCC-Sound','11'),(101,'DCC-Sound','11'),(102,'DCC-Sound','11'),(103,'DCC-Sound','11'),(104,'DCC-Sound','11'),(105,'DCC-Sound','11');


INSERT INTO `Product` VALUES (1,'R','Hornby','Single Straight',2,'OO'),(2,'R','Hornby','Single Straight',3,'TT'),(3,'R','Hornby','Single Straight',4,'N'),(4,'R','Bachmann','Double Straight',4,'OO'),(5,'R','Bachmann','Double Straight',5,'TT'),(6,'R','Bachmann','Double Straight',6,'N'),(7,'R','Graham Farish','Buffer Stop',3,'OO'),(8,'R','Graham Farish','Buffer Stop',4,'TT'),(9,'R','Graham Farish','Buffer Stop',5,'N'),(10,'R','Peco','1st Radius Single Curve',2,'OO'),(11,'R','Peco','1st Radius Single Curve',3,'TT'),(12,'R','Peco','1st Radius Single Curve',4,'N'),(13,'R','Dapol','2nd Radius Single Curve',2,'OO'),(14,'R','Dapol','2nd Radius Single Curve',3,'TT'),(15,'R','Dapol','2nd Radius Single Curve',4,'N'),(16,'R','Hornby','3rd Radius Single Curve',2,'OO'),(17,'R','Hornby','3rd Radius Single Curve',3,'TT'),(18,'R','Hornby','3rd Radius Single Curve',4,'N'),(19,'R','Bachmann','1st Radius Double Curve',4,'OO'),(20,'R','Bachmann','1st Radius Double Curve',5,'TT'),(21,'R','Bachmann','1st Radius Double Curve',6,'N'),(22,'R','Graham Farish','2nd Radius Double Curve',4,'OO'),(23,'R','Graham Farish','2nd Radius Double Curve',5,'TT'),(24,'R','Graham Farish','2nd Radius Double Curve',6,'N'),(25,'R','Peco','3rd Radius Double Curve',4,'OO'),(26,'R','Peco','3rd Radius Double Curve',5,'TT'),(27,'R','Peco','3rd Radius Double Curve',6,'N'),(28,'R','Dapol','Left-Hand Point',3,'OO'),(29,'R','Dapol','Left-Hand Point',4,'TT'),(30,'R','Dapol','Left-Hand Point',5,'N'),(31,'R','Hornby','Right-Hand Point',3,'OO'),(32,'R','Hornby','Right-Hand Point',4,'TT'),(33,'R','Hornby','Right-Hand Point',5,'N'),(34,'R','Bachmann','Left-Hand Crossover',3,'OO'),(35,'R','Bachmann','Left-Hand Crossover',4,'TT'),(36,'R','Bachmann','Left-Hand Crossover',5,'N'),(37,'R','Graham Farish','Right-Hand Crossover',3,'OO'),(38,'R','Graham Farish','Right-Hand Crossover',4,'TT'),(39,'R','Graham Farish','Right-Hand Crossover',5,'N'),(40,'C','Peco','Standard Controller',10,'OO'),(41,'C','Peco','Standard Controller',10,'TT'),(42,'C','Peco','Standard Controller',10,'N'),(43,'C','Dapol','DCC Controller',15,'OO'),(44,'C','Dapol','DCC Controller',15,'TT'),(45,'C','Dapol','DCC Controller',15,'N'),(46,'C','Hornby','DCC Elite Controller',20,'OO'),(47,'C','Hornby','DCC Elite Controller',20,'TT'),(48,'C','Hornby','DCC Elite Controller',20,'N'),(49,'L','Bachmann','Class A3 \'Flying Scotsman\'',50,'OO'),(50,'L','Bachmann','Class A3 \'Flying Scotsman\'',50,'TT'),(51,'L','Bachmann','Class A3 \'Flying Scotsman\'',50,'N'),(52,'L','Graham Farish','Class A4 \'Mallard\'',50,'OO'),(53,'L','Graham Farish','Class A4 \'Mallard\'',50,'TT'),(54,'L','Graham Farish','Class A4 \'Mallard\'',50,'N'),(55,'L','Peco','Class 5MT Black Five',55,'OO'),(56,'L','Peco','Class 5MT Black Five',55,'TT'),(57,'L','Peco','Class 5MT Black Five',55,'N'),(58,'L','Dapol','Class 07 Austerity',55,'OO'),(59,'L','Dapol','Class 07 Austerity',55,'TT'),(60,'L','Dapol','Class 07 Austerity',55,'N'),(61,'L','Hornby','Class 7P Castle',60,'OO'),(62,'L','Hornby','Class 7P Castle',60,'TT'),(63,'L','Hornby','Class 7P Castle',60,'N'),(64,'L','Bachmann','Class 7P Britannia',60,'OO'),(65,'L','Bachmann','Class 7P Britannia',60,'TT'),(66,'L','Bachmann','Class 7P Britannia',60,'N'),(67,'L','Graham Farish','Class 8P Coronation',65,'OO'),(68,'L','Graham Farish','Class 8P Coronation',65,'TT'),(69,'L','Graham Farish','Class 8P Coronation',65,'N'),(70,'L','Peco','Class 8P Merchant Navy',65,'OO'),(71,'L','Peco','Class 8P Merchant Navy',65,'TT'),(72,'L','Peco','Class 8P Merchant Navy',65,'N'),(73,'L','Dapol','Class 8F Stanier',70,'OO'),(74,'L','Dapol','Class 8F Stanier',70,'TT'),(75,'L','Dapol','Class 8F Stanier',70,'N'),(76,'L','Hornby','Class 9F \'Evening Star\'',70,'OO'),(77,'L','Hornby','Class 9F \'Evening Star\'',70,'TT'),(78,'L','Hornby','Class 9F \'Evening Star\'',70,'N'),(79,'L','Bachmann','Class 08 Shunter',75,'OO'),(80,'L','Bachmann','Class 08 Shunter',75,'TT'),(81,'L','Bachmann','Class 08 Shunter',75,'N'),(82,'L','Graham Farish','Class 20 Shunter',75,'OO'),(83,'L','Graham Farish','Class 20 Shunter',75,'TT'),(84,'L','Graham Farish','Class 20 Shunter',75,'N'),(85,'L','Peco','Class 35 Hymek Diesel',80,'OO'),(86,'L','Peco','Class 35 Hymek Diesel',80,'TT'),(87,'L','Peco','Class 35 Hymek Diesel',80,'N'),(88,'L','Dapol','Class 43 InterCity 125 HST (power car)',80,'OO'),(89,'L','Dapol','Class 43 InterCity 125 HST (power car)',80,'TT'),(90,'L','Dapol','Class 43 InterCity 125 HST (power car)',80,'N'),(91,'L','Hornby','Class 55 Deltic Diesel',85,'OO'),(92,'L','Hornby','Class 55 Deltic Diesel',85,'TT'),(93,'L','Hornby','Class 55 Deltic Diesel',85,'N'),(94,'L','Bachmann','Class 91 InterCity 225 Electra (power car)',85,'OO'),(95,'L','Bachmann','Class 91 InterCity 225 Electra (power car)',85,'TT'),(96,'L','Bachmann','Class 91 InterCity 225 Electra (power car)',85,'N'),(97,'L','Graham Farish','Class 110 Diesel Multiple Unit (power car)',90,'OO'),(98,'L','Graham Farish','Class 110 Diesel Multiple Unit (power car)',90,'TT'),(99,'L','Graham Farish','Class 110 Diesel Multiple Unit (power car)',90,'N'),(100,'L','Peco','Class 150 Sprinter DMU (power car)',90,'OO'),(101,'L','Peco','Class 150 Sprinter DMU (power car)',90,'TT'),(102,'L','Peco','Class 150 Sprinter DMU (power car)',90,'N'),(103,'L','Dapol','Class 800 Azuma (power car)',95,'OO'),(104,'L','Dapol','Class 800 Azuma (power car)',95,'TT'),(105,'L','Dapol','Class 800 Azuma (power car)',95,'N'),(106,'S','Hornby','Cattle Wagon',25,'OO'),(107,'S','Hornby','Cattle Wagon',25,'TT'),(108,'S','Hornby','Cattle Wagon',25,'N'),(109,'S','Bachmann','Horse Box Wagon',25,'OO'),(110,'S','Bachmann','Horse Box Wagon',25,'TT'),(111,'S','Bachmann','Horse Box Wagon',25,'N'),(112,'S','Graham Farish','Parcels Van',30,'OO'),(113,'S','Graham Farish','Parcels Van',30,'TT'),(114,'S','Graham Farish','Parcels Van',30,'N'),(115,'S','Peco','16t Mineral Wagon',30,'OO'),(116,'S','Peco','16t Mineral Wagon',30,'TT'),(117,'S','Peco','16t Mineral Wagon',30,'N'),(118,'S','Dapol','8-Plank Coal Wagon',35,'OO'),(119,'S','Dapol','8-Plank Coal Wagon',35,'TT'),(120,'S','Dapol','8-Plank Coal Wagon',35,'N'),(121,'S','Hornby','20t Hopper Wagon',35,'OO'),(122,'S','Hornby','20t Hopper Wagon',35,'TT'),(123,'S','Hornby','20t Hopper Wagon',35,'N'),(124,'S','Bachmann','102t Bogie Hopper Wagon',40,'OO'),(125,'S','Bachmann','102t Bogie Hopper Wagon',40,'TT'),(126,'S','Bachmann','102t Bogie Hopper Wagon',40,'N'),(127,'S','Graham Farish','Urchin Bogie Open Wagon',40,'OO'),(128,'S','Graham Farish','Urchin Bogie Open Wagon',40,'TT'),(129,'S','Graham Farish','Urchin Bogie Open Wagon',40,'N'),(130,'S','Peco','20t Brake Van',45,'OO'),(131,'S','Peco','20t Brake Van',45,'TT'),(132,'S','Peco','20t Brake Van',45,'N'),(133,'S','Dapol','GWR Toad Guards Van',45,'OO'),(134,'S','Dapol','GWR Toad Guards Van',45,'TT'),(135,'S','Dapol','GWR Toad Guards Van',45,'N'),(136,'S','Hornby','LMS Corridor First',25,'OO'),(137,'S','Hornby','LMS Corridor First',25,'TT'),(138,'S','Hornby','LMS Corridor First',25,'N'),(139,'S','Bachmann','LMS Corridor Second',25,'OO'),(140,'S','Bachmann','LMS Corridor Second',25,'TT'),(141,'S','Bachmann','LMS Corridor Second',25,'N'),(142,'S','Graham Farish','LNER Open First',30,'OO'),(143,'S','Graham Farish','LNER Open First',30,'TT'),(144,'S','Graham Farish','LNER Open First',30,'N'),(145,'S','Peco','LNER Open Second',30,'OO'),(146,'S','Peco','LNER Open Second',30,'TT'),(147,'S','Peco','LNER Open Second',30,'N'),(148,'S','Dapol','GWR Restaurant Car',35,'OO'),(149,'S','Dapol','GWR Restaurant Car',35,'TT'),(150,'S','Dapol','GWR Restaurant Car',35,'N'),(151,'S','Hornby','GWR Buffet Car',35,'OO'),(152,'S','Hornby','GWR Buffet Car',35,'TT'),(153,'S','Hornby','GWR Buffet Car',35,'N'),(154,'S','Bachmann','Mark 1 Sleeper Car',40,'OO'),(155,'S','Bachmann','Mark 1 Sleeper Car',40,'TT'),(156,'S','Bachmann','Mark 1 Sleeper Car',40,'N'),(157,'S','Graham Farish','Mark 1 Composite Coach',40,'OO'),(158,'S','Graham Farish','Mark 1 Composite Coach',40,'TT'),(159,'S','Graham Farish','Mark 1 Composite Coach',40,'N'),(160,'S','Peco','Mark 2 General Utility Van',45,'OO'),(161,'S','Peco','Mark 2 General Utility Van',45,'TT'),(162,'S','Peco','Mark 2 General Utility Van',45,'N'),(163,'S','Dapol','Mark 2 Post Office Sorting Van',45,'OO'),(164,'S','Dapol','Mark 2 Post Office Sorting Van',45,'TT'),(165,'S','Dapol','Mark 2 Post Office Sorting Van',45,'N'),(166,'S','Hornby','Mark 3 Brake Van',50,'OO'),(167,'S','Hornby','Mark 3 Brake Van',50,'TT'),(168,'S','Hornby','Mark 3 Brake Van',50,'N'),(169,'S','Bachmann','Mark 3 Composite Brake Van',50,'OO'),(170,'S','Bachmann','Mark 3 Composite Brake Van',50,'TT'),(171,'S','Bachmann','Mark 3 Composite Brake Van',50,'N'),(172,'S','Graham Farish','Mark 4 Pullman',55,'OO'),(173,'S','Graham Farish','Mark 4 Pullman',55,'TT'),(174,'S','Graham Farish','Mark 4 Pullman',55,'N'),(175,'M','Hornby','Eurostar Train Set',150,'OO'),(176,'M','Hornby','Eurostar Train Set',150,'TT'),(177,'M','Hornby','Eurostar Train Set',150,'N'),(178,'M','Bachmann','Mallard Record Breaker Train Set',160,'OO'),(179,'M','Bachmann','Mallard Record Breaker Train Set',160,'TT'),(180,'M','Bachmann','Mallard Record Breaker Train Set',160,'N'),(181,'M','Graham Farish','Flying Scotsman Train Set',170,'OO'),(182,'M','Graham Farish','Flying Scotsman Train Set',170,'TT'),(183,'M','Graham Farish','Flying Scotsman Train Set',170,'N'),(184,'M','Peco','Mixed Freight DCC Train Set',180,'OO'),(185,'M','Peco','Mixed Freight DCC Train Set',180,'TT'),(186,'M','Peco','Mixed Freight DCC Train Set',180,'N'),(187,'P','Hornby','1st Radius Starter Oval',30,'OO'),(188,'P','Hornby','1st Radius Starter Oval',30,'TT'),(189,'P','Hornby','1st Radius Starter Oval',30,'N'),(190,'P','Bachmann','2nd Radius Starter Oval',35,'OO'),(191,'P','Bachmann','2nd Radius Starter Oval',35,'TT'),(192,'P','Bachmann','2nd Radius Starter Oval',35,'N'),(193,'P','Graham Farish','3rd Radius Starter Oval',40,'OO'),(194,'P','Graham Farish','3rd Radius Starter Oval',40,'TT'),(195,'P','Graham Farish','3rd Radius Starter Oval',40,'N'),(196,'P','Peco','Track Pack A',50,'OO'),(197,'P','Peco','Track Pack A',50,'TT'),(198,'P','Peco','Track Pack A',50,'N'),(199,'P','Dapol','Track Pack B',50,'OO'),(200,'P','Dapol','Track Pack B',50,'TT'),(201,'P','Dapol','Track Pack B',50,'N');


INSERT INTO `Rolling Stock` VALUES (106,'1-2',0),(107,'1-2',0),(108,'1-2',0),(109,'1-2',0),(110,'1-2',0),(111,'1-2',0),(112,'3-4',0),(113,'3-4',0),(114,'3-4',0),(115,'3-4',0),(116,'3-4',0),(117,'3-4',0),(118,'5-6',0),(119,'5-6',0),(120,'5-6',0),(121,'7-8',0),(122,'7-8',0),(123,'7-8',0),(124,'9-10',0),(125,'9-10',0),(126,'9-10',0),(127,'9-10',0),(128,'9-10',0),(129,'9-10',0),(130,'11',0),(131,'11',0),(132,'11',0),(133,'11',0),(134,'11',0),(135,'11',0),(136,'1-2',1),(137,'1-2',1),(138,'1-2',1),(139,'1-2',1),(140,'1-2',1),(141,'1-2',1),(142,'3-4',1),(143,'3-4',1),(144,'3-4',1),(145,'3-4',1),(146,'3-4',1),(147,'3-4',1),(148,'5-6',1),(149,'5-6',1),(150,'5-6',1),(151,'5-6',1),(152,'5-6',1),(153,'5-6',1),(154,'7-8',1),(155,'7-8',1),(156,'7-8',1),(157,'7-8',1),(158,'7-8',1),(159,'7-8',1),(160,'9-10',1),(161,'9-10',1),(162,'9-10',1),(163,'9-10',1),(164,'9-10',1),(165,'9-10',1),(166,'11',1),(167,'11',1),(168,'11',1),(169,'11',1),(170,'11',1),(171,'11',1),(172,'11',1),(173,'11',1),(174,'11',1);


INSERT INTO `Track Packs` VALUES (187),(188),(189),(190),(191),(192),(193),(194),(195),(196),(197),(198),(199),(200),(201);


INSERT INTO `Track` VALUES (1),(2),(3),(4),(5),(6),(7),(8),(9),(10),(11),(12),(13),(14),(15),(16),(17),(18),(19),(20),(21),(22),(23),(24),(25),(26),(27),(28),(29),(30),(31),(32),(33),(34),(35),(36),(37),(38),(39);


INSERT INTO `Train Sets` VALUES (175,40),(178,40),(176,41),(179,41),(177,42),(180,42),(181,43),(182,44),(183,45),(184,46),(185,47),(186,48);
