## Player Finder - A PathFinding Algorithm

For the MSCI 240 final project, I undertook a self-created programming challenge that focused on graph creation, the Breadth-First Search Algorithm, and the creation of a sequence of vertices from an initial vertex to a target vertex. Inspired by the “Six Degrees of Kevin Bacon” challenge posed to students in previous years, the challenge was to find the path from a soccer player to a different player through the pertaining soccer club and nationality of the player. Using a Kaggle database (https://www.kaggle.com/antoinekrajnc/soccer-players-statistics) that holds a record of professional soccer players from around the world, their respective clubs, and their national team, I was able to use the read-in the file and create a graph using players, clubs and countries as vertices. Using this graph and given a user-inputted source player name and target player name, the goal is to compute the path from the source player name to the target player name through a Breadth-First Search algorithm that then produces the output of the sequence of vertices and the degrees of similarity (distance from source vertex to target vertex). The following report covers the initial approach to the challenge, the implementation strategy that was pursued, and a personal reflection. There was no collaboration with other students during this assignment. 

Final Mark Achieved: 100%

For more information on the project, refer to the project documentation: https://docs.google.com/document/d/1rTp6q76G6NEfYyc2QOqZv7Vs2WPxJYaSeCW03pwW_LQ/edit?usp=sharing

## Sample Player Names
- Cristiano Ronaldo
- Lionel Messi
- Ousmane Dembélé
- Corentin Tolisso
- Marcelo Brozović
- Federico Bernardeschi
- Niklas Süle
- Domenico Berardi
- Morgan Sanson
- Samu Castillejo
- Adrien Rabiot
- Leon Goretzka
- Quincy Promes
- Andrea Belotti
- Jonas Hector
- Mateo Kovačić
- Sergio Rico
