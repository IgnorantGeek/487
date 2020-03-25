## Running Instructions

1. Compile the java classes by running the java_compile.sh script.
2. Start the rmiregistry (NOTE: Run the rmiregistry command in the same dir as the shell scripts)
3. In a separate terminal window, start the server by running java src.manager.Server
4. In another terminal window, start the client by running java src.agent.Client
5. (Optional) to remove all the class files run the java_clean.sh script