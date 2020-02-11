This assignment is divided into two parts, the manager (java) and the agent (C).

To run the manager run the shell script "run_java.sh"
(NOTE: you may need to run "chmod +x run_java.sh" to allow execution of the script)

To run the agent, go into the Homework_1/Agent folder and run "make" then run "./agent"
There are three running configurations for the agent

default    (`./agent`)
Manager IP (`./agent <IP>`)
Custom     (`./agent <IP> <Port> <Interval>`)

To clean up the agent run "make clean"

Current Issue:
 - Having more than one agent running causes the manager to have a "TCP connection refused" error and crash
 - To reproduce error: run two clients, wait for second TCP command to attempt to send (there is a delay of 40 seconds)