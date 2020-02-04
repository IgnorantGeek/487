#include "udp_client.h"


int main(int argc, char *args[])
{
    // Initialize locals
    int server_socket;
    char buffer[] = "Hello from client.\n";
    struct sockaddr_in server_addr;
    memset(&server_addr, 0, sizeof(server_addr));
    struct BEACON send_beacon;
    memset(&send_beacon, 0, sizeof(send_beacon));

    // Create the network socket
    if ((server_socket = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("Failed to create socket.\n");
        exit(EXIT_FAILURE);
    }

    char *IP[4] = {"127", "0", "0", "1"};

    configure_beacon(&send_beacon, 1234, 1, IP, 51716);

    printf("Time value: %d\n", send_beacon.StartUpTime);

    // Configure IP route
    configure_route_host(&server_addr, UDP_PORT, DEFAULT_HOST);

    // Send payload
    sendto(server_socket, buffer, sizeof(buffer), MSG_CONFIRM, (const struct sockaddr *) &server_addr, sizeof(server_addr));

    printf("Payload sent.\n");

    return 0;
}

void configure_beacon(struct BEACON * beacon, int ID, int TimeInterval, char *IP[4], int cmdPort)
{
    beacon->ID = ID;
    beacon->StartUpTime = time(NULL);
    beacon->TimeInterval = TimeInterval;
    beacon->IP[0] = IP[0];
    beacon->IP[1] = IP[1];
    beacon->IP[2] = IP[2];
    beacon->IP[3] = IP[3];
    beacon->cmdPort = cmdPort;
}
