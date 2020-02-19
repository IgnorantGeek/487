This assignment is divided into two parts, the manager (java) and the agent (C).

# MANAGER
run the shell script "run_java.sh"
(NOTE: you may need to run "chmod +x run_java.sh" to allow execution of the script)

# AGENT
To run the agent, go into the Homework_1/Agent folder and run "make" then run "./agent"
There are three running configurations for the agent

default    (`./agent`)
Manager IP (`./agent <IP>`)
Custom     (`./agent <IP> <Port> <Interval>`)

when run as default, the IP is localhost, the Port is 1069, and the Interval is 1

To clean up the agent run "make clean"