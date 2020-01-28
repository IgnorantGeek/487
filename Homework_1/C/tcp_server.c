#include "tcp_server.h"

int main(int argc, char **argv)
{
    // Initialize locals
    int server_socket;
    char buffer[1024];
    struct sockaddr_in server;
    memset(&server, 0, sizeof(server));

    // Create the network socket
    if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        perror("SOCKET ERROR: Failed to create socket\n");
        exit(EXIT_FAILURE);
    }

    // Configure IP route
    if (argc == 2)
    {
        unsigned short svrPort = atoi(argv[1]);
        configure_route(&server, svrPort);
    } 
    else configure_route(&server, DEF_PORT);

    // Bind the port
    if ((bind(server_socket, (struct sockaddr *) &server, sizeof(server))) < 0)
    {
        perror("BIND ERROR: Failed to bind socket.");
        exit(EXIT_FAILURE);
    }

    // Listen for connections from client
    printf("Listening for incoming connections...\n");
    listen(server_socket, 5);
    
    return 0;
}

void configure_route(struct sockaddr_in * clientAddr, unsigned short Port)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = INADDR_ANY; // Vulnerability? Should the user specify where the connection is coming from?
}