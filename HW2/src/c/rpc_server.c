#include "rpc_server.h"

int main()
{
    // Initialize locals
    int server_socket;
    struct sockaddr_in server_addr;

    // set the sockaddr_in struct to 0
    memset(&server_addr, 0, sizeof(server_addr));

    // Create the socket
    if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        perror("SOCKET ERROR: Failed to create TCP socket\n");
        exit(EXIT_FAILURE);
    }

    // configure the route
    configure_route_any(&server_addr, 1069);

    // Listen for connections from client
    printf("TCP-SERVER: socket created. Listening for incoming connections....\n");
    listen(server_socket, 5);

    // Initialize the client address
    struct sockaddr client_addr;
    unsigned int client_len;

    // Accept the connection
    int client_socket = accept(server_socket, &client_addr, &client_len);
    printf("TCP-SERVER: Acknowledgement recieved. Reading transmission....\n");

    char command[100];
    receive_bytes(client_socket, command, 100);

    printf("Command recieved. C: %s\n", command);
    return 0;
}

// Recieve a single byte from the socket
void receive_one_byte(int client_socket, char *cur_char)
{
    ssize_t bytes_received = 0;
    while (bytes_received != 1)
    {
        bytes_received = recv(client_socket, cur_char, 1, 0);
    }
}

// Recieve n bytes from the socket into a buffer
void receive_bytes(int client_socket, char * buffer, int n)
{
    char *cur_char = buffer;
    ssize_t bytes_received = 0;
    while (bytes_received != n)
    {
        receive_one_byte(client_socket, cur_char);
        cur_char++;
        bytes_received++;
    }
}

// Configure the socket route with a host
void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = inet_addr(Hostname);
}

// Configure the socket route with any in address (for testing)
void configure_route_any(struct sockaddr_in * routeAddr, unsigned short Port)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = INADDR_ANY;
}