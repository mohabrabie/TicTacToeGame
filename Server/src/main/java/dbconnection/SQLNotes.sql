/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  Mohab
 * Created: Jan 15, 2021
 * this is the Creation of Tables in TicTacToeDB.db
 */
/*
CREATE TABLE player (
    playerID   INTEGER      PRIMARY KEY AUTOINCREMENT,
    name       STRING (20),
    email      STRING (25),
    password   STRING (20),
    main_score INTEGER (10),
    status     INTEGER (2),
    avatar     STRING (50) 
);

CREATE TABLE game (
    gameID   INTEGER      PRIMARY KEY,
    p1_ID    INTEGER (10) REFERENCES player (playerID) ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
    p2_ID    INTEGER (10) REFERENCES player (playerID) ON DELETE NO ACTION
                                                       ON UPDATE NO ACTION,
    p1_score INTEGER (5),
    p2_score INTEGER (5) 
);

*add some records*
INSERT INTO player (name,email,password,main_score,status,avatar)
values("mohab","mohab@gmail.com",123,120,1,"3.png");

INSERT INTO player (name,email,password,main_score,status,avatar)
values("aya","aya@gmail.com",123,150,1,"5.png");

INSERT INTO player (name,email,password,main_score,status,avatar)
values("eslam","eslam@gmail.com",123,85,1,"7.png");

*/