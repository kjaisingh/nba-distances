-------------
NBA Distances
-------------

EXECUTION INSTRUCTIONS
----------------------
The program can be run by executing Interface.java, which contains the main method and program that interacts with user input. 

When run, it will ask for a question number, after which you simply type an integer between 1 to 4 and press enter. 

After this, it will ask for an input text, which is dependent on the question. Refer to the documentation below to see the format for these inputs.

After this, the program will complete some amount of processing, and then print its response in the console. 


QUESTION BREAKDOWN
------------------
Q1: What is the shortest path between any two NBA players?
Expected Input: Full names of two players in comma-separated form. 
Example Input: Lebron James, Michael Jordan.
Expected Output: All shortest distance paths of teammates from the first to second player.
Example Output:  
    1. Michael Jordan --> JR Smith --> Lebron James
    2. Michael Jordan --> Bradley Beal --> Lebron James
    3. Michael Jordan --> Chris Paul --> Lebron James

Q2: What is the minimum cost path between any two NBA players?
Expected Input: Full names of two players in comma-separated form. 
Example Input: Lebron James, Michael Jordan.
Expected Output: The minimum-cost path of teammates from the first to second player.
Example Output:  Michael Jordan --> JR Smith (2) --> Lebron James (4)

Q3: What are the high-level statistics regarding any NBA player?
Expected Input: Full name of a player.
Example Input: Lebron James.
Expected Output: A list of statistics and facts regarding that player.
Example Output:
    - Number of Different Teammates: 57.
    - Longest-Duration Teammate: JR Smith (12).
    - Longest-Duration Mutual Teammates: Kyle Korver & Kevin Love (14).
    - Clustering Coefficient: 0.11.

Q4: What are the high-level statistics regarding the NBA as a whole?
Expected Input: N/A.
Example Input: Hit enter.
Expected Output: Intuitive statistics about the NBA and relationships within it.
Example Output: 
	- Longest-Duration Teammates: A & B (18), C & D (18), E & F (17), G & H (17), I & J (17). 
    - Connected Graph: No (4 Connected Components).
    - Highest Clustering Coefficient: Lebron James (0.11).
    - Highest Betweeness: Michael Jordan (0.0023).

