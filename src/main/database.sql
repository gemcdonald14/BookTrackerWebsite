--
-- File generated with SQLiteStudio v3.4.4 on Thu Nov 16 21:21:34 2023
--
-- Text encoding used: System
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Book
CREATE TABLE Book (BookID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, Title TEXT NOT NULL, Author TEXT NOT NULL, NumPages INTEGER NOT NULL);

-- Table: Book_On_Shelf
CREATE TABLE Book_On_Shelf (BookID INTEGER REFERENCES Book (BookID) NOT NULL, ShelfID INTEGER REFERENCES Shelf (ShelfID) NOT NULL, UserID INTEGER REFERENCES User (UserID) NOT NULL, PRIMARY KEY (BookID, ShelfID, UserID));

-- Table: Goal
CREATE TABLE Goal (GoalID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, GoalName TEXT NOT NULL, GoalType TEXT NOT NULL, UserID INTEGER REFERENCES User (UserID) NOT NULL, TargetBooks INTEGER NOT NULL, CompletedBooks INTEGER DEFAULT (0) NOT NULL);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (32, 'Books and stuff', '1', 5, 3, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (33, 'Books and stuff', '2', 5, 4, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (34, 'Just Some Goal', '2', 6, 3, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (35, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (36, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (37, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (38, 'may goal', '1', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (39, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (40, 'june goal', '1', 6, 3, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (41, 'Books and stuff', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (42, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (43, 'Just Some Goal', '1', 6, 7, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (44, 'june goal', '1', 6, 7, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (45, 'Just Some Goal', '1', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (46, 'Just Some Goal', '1', 6, 7, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (47, 'june goal', '1', 6, 3, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (48, 'june goal', '1', 6, 3, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (49, 'Books and stuff', '1', 6, 7, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (50, 'Books and stuff', '2', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (51, 'Books and stuff', '2', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (52, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (53, 'june goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (54, 'Just Some Goal', '1', 6, 8, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (55, 'new goal', '1', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (56, 'Just Some Goal', '1', 6, 10, 0);
INSERT INTO Goal (GoalID, GoalName, GoalType, UserID, TargetBooks, CompletedBooks) VALUES (57, 'june goal', '1', 6, 3, 0);

-- Table: Shelf
CREATE TABLE Shelf (ShelfID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, ShelfName TEXT (20) NOT NULL, UserID INTEGER REFERENCES User (UserID) NOT NULL);
INSERT INTO Shelf (ShelfID, ShelfName, UserID) VALUES (1, 'Want to Buy', 5);
INSERT INTO Shelf (ShelfID, ShelfName, UserID) VALUES (2, 'Want to Buy', 6);
INSERT INTO Shelf (ShelfID, ShelfName, UserID) VALUES (3, 'Want to Buy', 6);
INSERT INTO Shelf (ShelfID, ShelfName, UserID) VALUES (4, 'Want to Buy', 6);

-- Table: User
CREATE TABLE User (UserID INTEGER PRIMARY KEY AUTOINCREMENT UNIQUE NOT NULL, Username TEXT (30) UNIQUE NOT NULL, Password TEXT (20) NOT NULL, Email TEXT (100) NOT NULL, SecurityQuestion TEXT (100) NOT NULL, SecurityAnswer TEXT (50) NOT NULL, Bio TEXT, TotalBooks INTEGER NOT NULL DEFAULT (0), FavBook TEXT, FavAuthor TEXT);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (1, 'gemcdonald14', 'elizabeth14', 'gemcdonald14@gmail.com', 'What city were you born?', 'scranton', NULL, 0, NULL, NULL);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (2, 'test', '693a52e49881a1ec3f2c2a8a0b281a2a', 'test@gmail.com', 'test', 'test', NULL, 0, NULL, NULL);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (3, 'stew', '46709db989834ee20a27e4b33e8010d9', 'stew@gmail.com', 'who', 'me', NULL, 0, NULL, NULL);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (4, 'hello', '5d41402abc4b2a76b9719d911017c592', 'hello@gmail.com', 'hello', 'hello', NULL, 0, NULL, NULL);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (5, 'mom', 'daffd55e1b8020c7a60a7b6e36afb775', 'mom@gmail.com', 'mom', 'mom', NULL, 0, NULL, NULL);
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (6, 'cheryl', 'ddc5f5e86d2f85e1b1ff763aff13ce0a', 'cheryl@gmail.com', 'wubbies?', 'sure', 'i love books on dismantling capitalism', 0, 'If you give a mouse a cookie', 'Dr. Seuss');
INSERT INTO User (UserID, Username, Password, Email, SecurityQuestion, SecurityAnswer, Bio, TotalBooks, FavBook, FavAuthor) VALUES (7, 'wendell', '79af0c177db2ee64b7301af6e1d53634', 'stripes@gmail.com', 'who is your fav beetle?', 'dung beetle', 'hey guys', 0, 'the turning of the screw', 'charles dickens');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
