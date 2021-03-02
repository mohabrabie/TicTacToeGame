CREATE TABLE player (
    playerID   INTEGER       PRIMARY KEY AUTOINCREMENT
                             UNIQUE
                             NOT NULL,
    name       VARCHAR (100),
    email      VARCHAR (100) UNIQUE,
    password   VARCHAR (100),
    main_score INTEGER,
    status     INTEGER (3),
    avatar     VARCHAR (255) 
);

CREATE TABLE games (
    gameID       INTEGER PRIMARY KEY AUTOINCREMENT
                         UNIQUE
                         NOT NULL,
    play1ID      INTEGER REFERENCES player (playerID),
    player2ID    INTEGER REFERENCES player (playerID),
    playr1Score  INTEGER,
    player2Score INTEGER
);
INSERT INTO player (avatar) VALUES("icon1.png") ;
INSERT INTO player (avatar) VALUES("icon2.png") ;
INSERT INTO player (avatar) VALUES("icone3.png") ;
INSERT INTO player (avatar) VALUES("icon4.png") ;
INSERT INTO player (avatar) VALUES("icon5.png") ;
INSERT INTO player (avatar) VALUES("icon6.png") ;
INSERT INTO player (avatar) VALUES("icon7.png") ;
INSERT INTO player (avatar) VALUES("icon8.png") ;
INSERT INTO player (avatar) VALUES("icon9.png") ;
INSERT INTO player (avatar) VALUES("icon10.png") ;
INSERT INTO player (avatar) VALUES("icon11.png") ;
INSERT INTO player (avatar) VALUES("icon12.png") ;
INSERT INTO player (avatar) VALUES("icon13.png") ;
INSERT INTO player (avatar) VALUES("icon14.png") ;
INSERT INTO player (avatar) VALUES("icon15.png") ;
INSERT INTO player (avatar) VALUES("icon16.png") ;
INSERT INTO player (avatar) VALUES("icon17.png") ;
INSERT INTO player (avatar) VALUES("icon18.png") ;
INSERT INTO player (avatar) VALUES("icon19.png") ;
INSERT INTO player (avatar) VALUES("icon20.png") ;
INSERT INTO player (avatar) VALUES("icon21.png") ;
INSERT INTO player (avatar) VALUES("icon22.png") ;
INSERT INTO player (avatar) VALUES("rect.png") ;
INSERT INTO player (avatar) VALUES("rect1.png") ;
