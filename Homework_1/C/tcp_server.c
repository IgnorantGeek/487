#include "tcp_server.h"

int main(int argc, char **argv)
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
    struct hostent * host = gethostbyname(argv[1]); // gets the hostname from the command line...?
    unsigned short svrPort = atoi(argv[2]);
    configure_route(&sin, svrPort);

    // Bind the port
    if ((bind(cSock, (struct sockaddr *) &sin, sizeof(sin))) < 0)
    {
        perror("BIND ERROR: Failed to bind socket.");
        exit(EXIT_FAILURE);
    }

    // Listen for connections from client
    printf("Listening for incoming connections...\n");
    listen(cSock, 5);
    // Insert accept and recv loop
    return 0;
}

void configure_route(struct sockaddr_in * clientAddr, unsigned short Port)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = INADDR_ANY; // Vulnerability? Should the user specify where the connection is coming from?
}