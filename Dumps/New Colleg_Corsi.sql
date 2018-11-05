DROP TABLE IF EXISTS `colleg_corsi`;

CREATE TABLE colleg_corsi (
	This_Corso INT(11) NOT NULL,
    Other_Corso INT(11) NOT NULL,
    Anno_This_Corso INT(11) NOT NULL,
    Anno_Other_Corso INT(11) NOT NULL,
    Tipo varchar(20) NOT NULL,
    primary key(This_Corso, Other_Corso, Anno_This_Corso, Anno_Other_Corso),
    foreign key(This_Corso,Anno_This_Corso) references corso(IDCorso,Anno),
    foreign key(Other_Corso,Anno_Other_Corso) references corso(IDCorso,Anno)
    #foreign key(This_Corso) references corso(IDCorso),
    #foreign key(Anno_This_Corso) references corso(Anno),
    #foreign key(Other_Corso) references corso(IDCorso),
    #foreign key(Anno_Other_Corso) references corso(Anno)
);

INSERT INTO `colleg_corsi` VALUES (4,1,2017,2017,'mutuato');