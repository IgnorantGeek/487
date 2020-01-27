#include "tcp_server.h"

int main(int argc, char *args[])
{
    // Initialize locals
    int cSock;
    char buffer[1024];
    struct sockaddr_in sin;
    memset(&sin, 0, sizeof(sin));

    // Create the network socket
    if ((cSock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        perror("SOCKET ERROR: Failed to create socket\n");
        exit(EXIT_FAILURE);
    }

    // Configure IP route
    struct hostent * host = gethostbyname(args[1]); // gets the hostname from the command line...?
    unsigned int svrAddr = INADDR_ANY;
    unsigned short svrPort = atoi(args[2]);
    sin.sin_family = AF_INET;
    sin.sin_addr.s_addr = svrAddr;
    sin.sin_port = htons(svrPort);

    // Bind the port
    if ((bind(cSock, (struct sockaddr *) &sin, sizeof(sin))) < 0)
    {
        perror("BIND ERROR: Failed to bind socket.");
        exit(EXIT_FAILURE);
    }

    // Listen for incoming connections from client
    printf("Listening for incoming connections...\n");
    listen(cSock, 5);

    return 0;
}

void confClientAddr(struct sockaddr_in * clientAddr, int Port)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = INADDR_ANY; // Vulnerability? Should the user specify where the connection is coming from?
}