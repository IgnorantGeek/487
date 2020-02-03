#include "udp_client.h"


int main(int argc, char *args[])
{
    // Initialize locals
    int server_socket;
    char buffer[] = "Hello from client.\n";
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));

    // Create the network socket
    if ((server_socket = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("Failed to create socket.\n");
        exit(EXIT_FAILURE);
    }

    // Configure IP route
    configure_route_host(&server_addr, UDP_PORT, DEFAULT_HOST);

    // Send payload
    unsigned int n = sendto(server_socket, buffer, sizeof(buffer), MSG_CONFIRM, (const struct sockaddr *) &server_addr, sizeof(server_addr));

    printf("Return code: %d\n", n);

    return 0;
}
