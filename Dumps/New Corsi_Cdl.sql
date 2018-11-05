DROP TABLE IF EXISTS `corsi_cdl`;

CREATE TABLE `corsi_cdl` (
  `Corso` int(11) NOT NULL,
  AnnoCorso int(11) NOT NULL,
  `CDL` int(11) NOT NULL,
  PRIMARY KEY (`Corso`,AnnoCorso,`CDL`),
  foreign key(Corso,AnnoCorso) references corso(IDCorso,Anno),
  foreign key(CDL) references cdl(IDCDL)
);

INSERT INTO `corsi_cdl` VALUES (1,2018,1),(1,2017,1),(2,2018,1),(4,2017,1),(3,2018,2);