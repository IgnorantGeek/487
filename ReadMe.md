This assignment is divided into two parts, the manager (java) and the agent (C).

To run the manager simply run the shell script "run_java.sh"
(NOTE: you made need to run "chmod +x run_java.sh" to allow execution of the script)

To run the agent, go into the Homework_1/Agent folder and run "make" then run "./agent"
To clean up the agent simply run "make clean"


Current Issue:
 - Having more than one agent running causes the manager to have a "TCP connection refused" error and crash
 - To reproduce error: run two clients, wait for second TCP command to attempt to send (there is a delay of 40 seconds)