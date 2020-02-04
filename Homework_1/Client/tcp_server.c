#include "tcp_server.h"

int main()
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
    
    // Configure the route
    configure_route_host(&server_addr, TCP_PORT, DEFAULT_HOST);

    // Bind the port
    if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) < 0)
    {
        perror("BIND ERROR: Failed to bind socket\n");
        exit(EXIT_FAILURE);
    }

    // Listen for connections from client
    printf("Listening for incoming connections....\n");
    listen(server_socket, 5);

    // Accept connection loop
    while(1)
    {
        // Initialize the client address
        struct sockaddr client_addr;
        unsigned int client_len;

        // Accept the connection
        int client_socket = accept(server_socket, &client_addr, &client_len);

        // Log
        printf("Acknowledgement recieved. Reading transmission....\n");

        // Read the length from the first 4 bytes
        char packet_length_bytes[4];
        receive_bytes(client_socket, packet_length_bytes, 4);
        int packet_length = toInteger32_be(packet_length_bytes);

        // Allocate buffer and read data
        char *buf = (char *) malloc(packet_length);
        receive_bytes(client_socket, buf, packet_length);

        // Log the message from the client
        printf("Message recieved from client: %s\n", buf);

        // Send back the response
        char send_length_bytes[4];
        to_bytes(send_length_bytes, sizeof("This is a longer message for testing.\n"));
        send(client_socket, send_length_bytes, 4, 0);
        send(client_socket, "This is a longer message for testing.\n", sizeof("This is a longer message for testing.\n"), 0);

        // release buffer
        free(buf);

        // Remove me
        break;
    }

    return 0;
}

void receive_one_byte(int client_socket, char *cur_char)
{
    ssize_t bytes_received = 0;
    while (bytes_received != 1)
    {
        bytes_received = recv(client_socket, cur_char, 1, 0);
    }
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