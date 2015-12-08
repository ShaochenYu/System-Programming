DROP DATABASE IF EXISTS Roster;
CREATE DATABASE Roster;
USE Roster;

/*
	relation:  Leagues ( league_id, league_name, description, number_of_team )
*/
CREATE TABLE Leagues (
	league_id int,
    league_name varchar(255),
    description varchar(255),
    number_of_teams int,
    primary key (league_id)
);
INSERT INTO Leagues VALUES
(1, 'NBA', 'National Basketball Association', 2),
(2, 'NHL', 'National Hockey League', 0),
(3, 'NFL', 'National Football League', 0),
(4, 'MLB', 'Major League Baseball', 0),
(5, 'NCAA', 'National Collegiate Athletic Association', 0),
(6, 'FL1', 'French Ligue 1', 0),
(7, 'EPL', 'English Premier League', 0),
(8, 'ISA', 'Italian Serie A', 0),
(9, 'GB', 'German Bundesliga', 0),
(10, 'SLL', 'Spanish La Liga', 2);

select * from Leagues;
/*
	relation: Teams (team_id, team_name, head_coach, number_of_players, league_id)
*/

CREATE TABLE Teams (
	team_id int,
    team_name varchar(255),
    head_coach varchar(255),
    number_of_players int,
    league_id int,
    primary key (team_id),
    foreign key (league_id) references Leagues(league_id)
	on delete set null
    on update cascade
);

INSERT INTO Teams VALUES
(1, 'CAV', 'Blatt', 3, 1),
(2, 'GSW', 'karr', 3, 1),
(3, 'OKC', 'brooks', 0, 1),
(4, 'GIANTS', 'coughlin', 0, 3),
(5, 'SEAHAWS', 'carroll', 0, 3),
(6, 'REAL MADRID', 'benitez', 2, 10),
(7, 'BARCELONA FC', 'vilanova', 2, 10),
(8, 'CHELSEA FC', 'mourinho', 0, 7),
(9, 'MUFC','ferguson', 0, 7),
(10, 'Juventus FC', 'Conte', 0, 8);

select * from Teams;

/*
	relation: Players (play_id, play_name, height, weight, team_id)
*/

CREATE TABLE Players (
	player_id int,
    play_name varchar(255),
    height varchar(255),
    weight int,
    team_id int,
    primary key (player_id),
    foreign key (team_id) references Teams(team_id)
    on delete set null
    on update cascade
);

INSERT INTO Players VALUES
(1, 'james', '6f8i', '250', 1),
(2, 'love', '6f10i', '230', 1),
(3, 'irving', '6f10i', '230', 1),
(4, 'curry', '6f10i', '230', 2),
(5, 'thompson', '6f10i', '230', 2),
(6, 'Iguodala', '6f10i', '230', 2),
(7, 'messi', '6f10i', '230', 7),
(8, 'neymar', '6f10i', '230', 7),
(9, 'ronaldo', '6f10i', '230', 6),
(10, 'Bale', '6f10i', '230', 6);



select * from Players;

select *
from Players, Teams, Leagues
where Players.team_id = Teams.team_id and Teams.league_id = Leagues.league_id;
