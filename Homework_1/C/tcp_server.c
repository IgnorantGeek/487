#include "tcp_server.h"

int main(int argc, char **argv)
{
    // Initialize locals
    int server_socket;
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));

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
        configure_route(&server_addr, svrPort);
    } 
    else configure_route(&server_addr, DEFAULT_PORT);

    // Bind the port
    if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) < 0)
    {
        perror("BIND ERROR: Failed to bind socket\n");
        exit(EXIT_FAILURE);
    }

    // Listen for connections from client
    printf("Listening for incoming connections...\n");
    listen(server_socket, 5);

    // Accept connection loop
    while(1)
    {
        // Initialize the client address
        struct sockaddr client_addr;
        unsigned int client_len;

        // Accept the connection
        int client_socket = accept(server_socket, &client_addr, &client_len);

        // Read the length from the first 4 bytes
        char packet_length_bytes[4];
        receive_bytes(client_socket, packet_length_bytes, 4);
        int packet_length = toInteger32_le(packet_length_bytes);

        // Allocate buffer and read data
        char * buf = (char *) malloc(packet_length);
        receive_bytes(client_socket, buf, packet_length);

        // Log the message from the client
        printf("Message recieved from client: %s", buf);

        // Send back the response
        // send(client_socket, packet_length_bytes, 4, 0);
        // send(client_socket, buf, packet_length, 0);

        // release buffer
        free(buf);

        // Remove me
        break;
    }
    return 0;
}

void configure_route(struct sockaddr_in * clientAddr, unsigned short Port)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = INADDR_ANY; // Accept from any IP
}

void receive_one_byte(int client_socket, char *cur_char)
{
    ssize_t bytes_received = 0;
    while (bytes_received != 1)
    {
    bytes_received = recv(client_socket, cur_char, 1, 0);
    } 
    return 1;
}

void receive_bytes(int client_socket, char * buffer, int length)
{
    char *cur_char = buffer;
    ssize_t bytes_received = 0;
    while (bytes_received != length)
    {
        receive_one_byte(client_socket, cur_char);
        cur_char++;
        bytes_received++;
    }
}

// Little Endian
int toInteger32_le(char *bytes)
{
    int tmp = bytes[0] +
            (bytes[1] << 8) + 
            (bytes[2] << 16) + 
            (bytes[3] << 24);
    
    return tmp;
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