#include "cmd_listener.h"

// Listen for a command from the manager over TCP
void * cmd_listen(void * args)
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

    configure_route_host(&server_addr, TCP_PORT, DEFAULT_HOST);
    int32_t beacon_port = TCP_PORT;

    // Bind the port
    if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) < 0)
    {
        // Here we need to pick another port and try to rebind it
        perror("BIND ERROR: Failed to bind default TCP socket.\n");

        // Increment up from the default port number until we find a valid one
        while (1)
        {
            beacon_port++;
            configure_route_host(&server_addr, beacon_port, DEFAULT_HOST);
            if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) == 0) break;
        }
    }
    printf("%d\n", beacon_port);
    // Signal beacon to be sent.
    pthread_t udp_thread;
    pthread_create(&udp_thread, NULL, send_beacon, (void *) (intptr_t) beacon_port);

    // Listen for connections from client
    printf("TCP-SERVER: socket created. Listening for incoming connections....\n");
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
        printf("TCP-SERVER: Acknowledgement recieved. Reading transmission....\n");

        // Read the length from the first 4 bytes
        char packet_length_bytes[4];
        receive_bytes(client_socket, packet_length_bytes, 4);
        int packet_length = toInteger32_be(packet_length_bytes);

        // Allocate buffer and read data
        char *buf = (char *) malloc(packet_length);
        receive_bytes(client_socket, buf, packet_length);

        // Log the message from the client
        printf("TCP-SERVER: Message recieved from client: %s\n", buf);

        // Send back the response
        char send_length_bytes[4];
        char buffer[44] = "this is the message to send back to client.";
        int_to_bytes(send_length_bytes, sizeof(buffer));
        send(client_socket, send_length_bytes, 4, 0);
        send(client_socket, buffer, sizeof(buffer), 0);

        // release buffer
        free(buf);
        printf("TCP-SERVER: Listening for new connection....\n");
    }

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