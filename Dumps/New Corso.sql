DROP TABLE IF EXISTS `corso`;

CREATE TABLE corso (
  IDCorso int(11) NOT NULL,
  Anno int(11) NOT NULL,
  PRIMARY KEY (IDCorso, Anno),
  Nome_it varchar(50) DEFAULT NULL,
  Nome_en varchar(50) DEFAULT NULL,
  SSD varchar(10) DEFAULT NULL,
  Lingua varchar(20) DEFAULT NULL,
  Semestre int(11) DEFAULT NULL,
  CFU int(11) DEFAULT NULL,
  Tipologia char(1) DEFAULT NULL
);

INSERT INTO `corso` VALUES (1,2018,'Reti neurali','Neural network','007','ita',1,12,'A'),(1,2017,'Reti neurali','Neural network','007','ita',1,12,'A'),(2,2018,'Laboratorio basi di dati','Lab Database','INF','Ita',1,12,'A'),(3,2018,'Teoria Dell\'Informazione','Information Theory','INF','Ita',1,6,'A'),(4,2017,'Algoritmi','Algoritmi','inf','ita',1,12,'A');