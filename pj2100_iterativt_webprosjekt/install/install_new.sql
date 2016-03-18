SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


CREATE TABLE IF NOT EXISTS `activities` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `location` varchar(255) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `host` int(10) unsigned NOT NULL,
  `description` varchar(20000) NOT NULL,
  `image` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `host` (`host`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;


INSERT INTO `activities` (`uid`, `title`, `location`, `date`, `host`, `description`, `image`) VALUES
(13, 'Skitur', 'Hemsedal', '2014-03-30 10:00:00', 12, 'Skitur til hemsedal, du må være meldt inn i utvalget for å delta på denne turen.', 'http://gfx.dagbladet.no/labrador/250/250109/25010940/jpg/active/978x.jpg'),
(14, 'Spill workshop', 'NITH, Oslo', '2014-04-08 18:00:00', 1, 'Workshop i spillprogrammering. ', 'http://www.hig.no/var/ezwebin_site/storage/images/studietilbud/informatikk/bachelor/spillprogrammering/253273-23-nor-NO/spillprogrammering.jpg');


CREATE TABLE IF NOT EXISTS `communitymembers` (
  `studentcommunity` int(10) unsigned NOT NULL,
  `user` int(10) unsigned NOT NULL,
  `leader` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`studentcommunity`,`user`),
  KEY `user` (`user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


CREATE TABLE IF NOT EXISTS `news` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `title` varchar(255) NOT NULL,
  `author` int(10) unsigned NOT NULL,
  `posted` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `post` varchar(20000) NOT NULL,
  PRIMARY KEY (`uid`),
  KEY `author` (`author`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 AUTO_INCREMENT=1 ;


CREATE TABLE IF NOT EXISTS `signups` (
  `uid` int(10) NOT NULL,
  `host_id` int(10) NOT NULL,
  PRIMARY KEY (`uid`,`host_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `signups` (`uid`, `host_id`) VALUES
(1, 4),
(1, 10),
(1, 15),
(1, 16);

CREATE TABLE IF NOT EXISTS `studentcommunity` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `abbrevation` varchar(16) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` varchar(20000) NOT NULL,
  `color` varchar(6) NOT NULL DEFAULT '000000',
  `image_btn` varchar(100) NOT NULL DEFAULT '',
  `image_banner` varchar(100) NOT NULL DEFAULT '',
  PRIMARY KEY (`uid`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=17 ;


INSERT INTO `studentcommunity` (`uid`, `name`, `abbrevation`, `title`, `description`, `color`, `image_btn`, `image_banner`) VALUES
(1, 'Prosjektutvalg for spill', 'PUS', 'Spill', 'For deg som elsker spill! Her møtes man på skolen for å spille. Man er også med på å arrangere lan partys, og være ansvarlig for konkurranser.', 'f7bc78', 'spill', 'spill'),
(2, 'Prosjektutvalg for support', 'PUSU', 'Support', 'Skolens elite! Dette er gruppen for deg som elsker å hjelpe andre i nød. En meget sosial gruppe som får i oppgave om å løse diverse saker!', '004dcc', 'support', 'support'),
(3, 'Prosjektutvalg for ledere', 'PUL', 'Ledere', 'Alle utvalg trenger ledere, dette er gruppen for deg dersom du ønsker å være leder i noen av skolens utvalg. Man trenger ingen erfaring fra tidligere. Her får den hjelpen du trenge for å bli en leder.', '000000', 'leder', 'leder'),
(4, 'Prosjektutvalg for kvinner', 'PUK', 'Kvinner', 'Gruppen for alle skolens kvinner, er du ikke medlem i dag meld deg inn nå! Det er ikke til å legge skjul på at kvinner er i mindre tall når det gjelder IT. Dette utvalget arrangerer hyggelige kvelder, det hender bedrifter stikker innom for å smiske.', 'f79fe7', 'kvinner', 'kvinner'),
(5, 'Prosjektutvalg for sosial', 'PUSO', 'Sosial', 'For de på skolen som brenner for å arrangere. Her får du en unik mulighet til å påvirke studentene til å være med på alt fra festlige hytte turer til konserter av alle slag. Julebordet på skolen er alltid en suksess!', '9fdff7', 'sosialt', 'sosialt'),
(6, 'Prosjektutvalg for kroa', 'PUKR', 'Kroa', 'Elsker du å henge på skolen er du hjertelig velkommen i kro utvalget, en ekstrem sosial gjeng som har det veldig moro! Stillingen består Hovedsakelig og selge øl og lignende. Arbeidet er ikke lønnet men man får meget gode rabatter. Kroa samarbeider tett med sosial-utvalget for å arrangere fester i kroa.', 'a26a2b', 'kroa', 'kroa'),
(7, 'Prosjektutvalg for musikk', 'PUM', 'Musikk', 'Musikkutvalget er for deg med en lidenskap for musikk! Dette utlvalget vil komme med informasjon om konserter rundt om kring i Oslo, og arrangere kvelder hvor man kan dra på disse som en gruppe. Det blir også arrangert intimkonserter med kjente og ukjente navn. I tillegg til dette blir det arrangert workshop hvor de mer erfarne musikerne av oss kan lære de som har lyst forskjellige instrumenter.', '93d888', 'musikk', 'musikk'),
(8, 'Prosjektutvalg for film', 'PUF', 'Film', 'Er du interessert i film? Da er dette for deg! Her får du muligheten til å diskutere film med mennesker som har samme lidenskap som deg. Det blir også arrangert filmkvelder en gang i uka for de som er interesserte, og kinokvelder en gang måneden. Det er mange erfarne filmstudenter i utvalget, og disse vil stille opp på kvelder for å lære de som er interesserte i hvordan det er å jobbe med film.', 'ff0000', 'film', 'film'),
(9, 'Prosjektutvalg for kunst', 'PUKU', 'Kunst', 'Bor det en kunstner i deg? Da må du melde deg på kunstutvalget! Det blir arrangert kvelden hvor vi samles og drar på museum, galleri og utsillinger. Det blir også holdt male-, skulptur-, og fotokurs for de av dere som er interesserte i det.', 'ffe400', 'kunst', 'kunst'),
(10, 'Prosjektutvalg for teater', 'PUT', 'Teater', 'Brenner du for teater? Da er teaterutvalget for deg! Her arrangeres det kvelder hvor vi ser teater sammer, og diskuterer stykket i etterkant. I løpet av året skal vi også sette opp vår egen produksjon som i slutten av året skal vises fram til hele skolen. Alle er velkommen, og alle vil få en rolle å spille!', 'a06dcb', 'teater', 'teater'),
(11, 'Prosjektutvalg for apple', 'PUA', 'Apple', 'Dette er utvalget for oss som elsker alt Apple! Her diskuterer vi nye og kommende produkter, og anmelder disse i vårt nyhetsmagasin; Apple Times. Om du ønsker å skrive for Apple Times har du selvfølgelig også mulighet til dette. Meld deg på!', 'c9c9c9', 'apple', 'apple'),
(12, 'Prosjektutvalg for idrett', 'PUI', 'Idrett', 'Dette er utvalget for deg som er sporty! I idrettsutvalget blir det gjevnlig holdt treningsøkter i både fotball, basketball, håndball m.m. Det blir også i løpet av vinteren arrangert skiturer og skihopp for de som ønsker dette. Er du med i idrettsutvalget kan du også melde deg opp til de forskjellige lagene vi har. Kampene spilles mot andre studentforeninger og bedriftslag. Du må ikke være proff for å være med her, så bare meld deg på!', '2d770d', 'idrett', 'idrett'),
(13, 'Prosjektutvalg for skriving', 'PUSK', 'Skriving', 'Skriveutvalget er for deg som liker å skrive. Det er spiller ingen rolle om du skriver noveller eller blogger, her er det plass til alle! Vi vil arrangere skriveworkshops hvor man får tips til hvordan man forbedre egen skriving, og det blir invitert forfattere til å holde foredrag og kurs.', '000000', 'skrive', 'skrive'),
(14, 'Prosjektutvalg for utvikling', 'PUU', 'Utvvikling', 'Utviklingsutvalget er for deg som ønsker å utvikle programmer og spill. Utvalget jobber for å engasjere studenter til å jobbe på tvers av studieretningene. Det vil bli holdt workshops hvor man kan lære seg forskjellige språk og programmer. Det blir mye moro med sosiale kvelder og spennende prosjekter!  Her er det en unik mulighet for å få nye venner og utvikle seg selv på mange områder.', 'f4721c', 'utvikling', 'utvikling'),
(15, 'Prosjektutvalget for Microsoft', 'PUMC', 'Microsoft', 'Info.', '000000', 'microsoft', 'microsoft'),
(16, 'Prosjektutvalget for kor', 'PUKO', 'Kor', 'Info.', '000000', 'kor', 'kor');

CREATE TABLE IF NOT EXISTS `studentprograms` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  PRIMARY KEY (`uid`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=23 ;


INSERT INTO `studentprograms` (`uid`, `name`) VALUES
(6, '3D-Grafikk'),
(10, 'Art Direction'),
(8, 'E-Business'),
(11, 'Experience & Event'),
(12, 'Film & TV'),
(18, 'Film og TV'),
(13, 'Grafisk Design'),
(9, 'Industribachelor'),
(1, 'Intelligente Systemer'),
(7, 'Interaktivt Design'),
(16, 'Lydproduksjon'),
(21, 'Lys og scene'),
(2, 'Mobil Apputvikling'),
(17, 'Populærmusikk'),
(3, 'Programmering'),
(22, 'Prosjektledelse kultur'),
(14, 'Retail Design'),
(20, 'Skuespillkunst'),
(5, 'Spilldesign'),
(4, 'Spillprogrammering'),
(15, 'Tekst og Skribent'),
(19, 'Visuell kunst');


CREATE TABLE IF NOT EXISTS `users` (
  `uid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `dateofbirth` date NOT NULL,
  `studentprogram` int(10) unsigned NOT NULL,
  `date_joined` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `access_level` tinyint(1) unsigned DEFAULT '0',
  PRIMARY KEY (`uid`),
  UNIQUE KEY `email` (`email`),
  KEY `studentprogram` (`studentprogram`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=4 ;

ALTER TABLE `activities`
  ADD CONSTRAINT `activities_ibfk_1` FOREIGN KEY (`host`) REFERENCES `studentcommunity` (`uid`);

ALTER TABLE `communitymembers`
  ADD CONSTRAINT `communitymembers_ibfk_1` FOREIGN KEY (`studentcommunity`) REFERENCES `studentcommunity` (`uid`),
  ADD CONSTRAINT `communitymembers_ibfk_2` FOREIGN KEY (`user`) REFERENCES `users` (`uid`);

ALTER TABLE `news`
  ADD CONSTRAINT `news_ibfk_1` FOREIGN KEY (`author`) REFERENCES `users` (`uid`);

ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`studentprogram`) REFERENCES `studentprograms` (`uid`);
