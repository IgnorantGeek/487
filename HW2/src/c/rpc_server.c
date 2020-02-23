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

    int port = 1069;

    while (1)
    {
        configure_route_any(&server_addr, port);
        if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) == 0) 
        {
            printf("Using port: %d\n", port);
            break;
        }
        port++;
    }

    // Listen for connections from client
    printf("TCP-SERVER: socket created. Listening for incoming connections....\n");
    listen(server_socket, 5);

    // Initialize the client address
    struct sockaddr client_addr;
    unsigned int client_len;

    // Accept the connection
    int client_socket = accept(server_socket, &client_addr, &client_len);
    printf("TCP-SERVER: Acknowledgement recieved. Reading transmission....\n");

    char header[104];
    receive_bytes(client_socket, header, 104);

    int cmd_len = 0;
    for (int i = 0; i < 104; i++)
    {
        if (header[i] != '0') cmd_len++;
        else break;
    }

    char * command = (char *) malloc(cmd_len);
    memcpy(command, header, cmd_len);

    printf("Command - %s\n", command);

    char length[4];
    memcpy(length, header+100, 4);

    // Allocate the buffer
    char * buffer = (char *) malloc(toInteger32_be(length));
    
    // Check which command this is
    if (strcmp(command, "GetLocalTime"))
    {
        // run Get local time
    }
    else if (strcmp(command, "GetLocalOS"))
    {
        // run get local os
    }
    else if (strcmp(command, "GetDiskData"))
    {
        // run get disk data
    }
    else
    {
        // Default case
    }
    
    free(buffer);
    free(command);
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
void configure_route_any(struct sockaddr_in * routeAddr, int Port)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = INADDR_ANY;
}

// Big Endian
int toInteger32_be(char *bytes)
{
    int tmp = (bytes[0] << 24) + 
            (bytes[1] << 16) + 
            (bytes[2] << 8) + 
            bytes[3];
    return tmp;
}