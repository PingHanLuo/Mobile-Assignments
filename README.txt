# Mobile-Assignments

Contributors:
Robin Luo 100998216
Michael Kameoka 100980710

Solutions to assignment#1 in 2017 version of COMP 2601 - Mobile Applications.
This is a tic-tac-toe game that implements threads to distribute the workload
of the A.I. which is the user's opponent in the game. The A.I. will play for
both sides so the user must be quick to make a decision. Click on a button to 
place an 'X', get 3 in a row and you win!

To run Mobile-Assignments; open up the project in the latest version of Android
Studio (January 30th, 2017) and hit 'Run'. Use the application in an AVD.

To test that the requirements of the assignment are met try the following:
-click on all the buttons (other than 'Start') to ensure they're disabled
-click 'Start' and see the 'Start' change to 'Running'
-click 'Running' to see that it changes back and the game stops
-while running the AI should randomly choose one of the nine buttons to play
 regardless of who's turn it is every 2 seconds 
-every odd click(1st, 3rd, 5th...) should play an 'X' will the even 'O's 
 will only be played by the computer in between each 'X'
-user shouldn't be allowed to click any of the 9 buttons while it's '0's 
 turn to play
-game should end when either 'Running' is clicked or someone has won, or 
 all 9 tiles have been chosen
-Display text should show each button that's clicked and show the result

