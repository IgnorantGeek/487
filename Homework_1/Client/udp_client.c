#include "udp_client.h"


int main()
{
    // Initialize locals
    int server_socket;
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

    char IP[4][3] = {"127", "000", "000", "001"};

    configure_beacon(&send_beacon, 1, IP, 51717);

    printf("Beacon:\nID          - %d\nStartupTime - %d\n\n", send_beacon.ID, send_beacon.StartUpTime);

    // Configure IP route
    configure_route_host(&server_addr, UDP_PORT, DEFAULT_HOST);

    // Serialize the beacon for transfer
    char serial_buffer[28];
    serialize_beacon(&send_beacon, serial_buffer);

    // Send payload (need to serialize the beacon before we send it)
    sendto(server_socket, &serial_buffer, sizeof(serial_buffer), MSG_CONFIRM, (const struct sockaddr *) &server_addr, sizeof(server_addr));

    printf("Payload sent.\n");

    return 0;
}

void configure_beacon(struct BEACON * beacon, int TimeInterval, char IP[4][3], int cmdPort)
{
    srand(time(NULL));
    beacon->ID = rand();
    beacon->StartUpTime = time(NULL);
    beacon->TimeInterval = TimeInterval;
    for (int i = 0; i < 4; i++)
    {
        for (int j = 0; j < 3; j++)
        {
            beacon->IP[i][j] = IP[i][j];
        }
    }
    beacon->cmdPort = cmdPort;
}

// Untested
void serialize_beacon(struct BEACON * beacon, char buffer[28])
{
    // Initialize a buffer for the numbers
    unsigned char int_buf[4];
    memset(int_buf, 0, 4);

    // Serialize ID
    to_bytes(int_buf, beacon->ID);
    memcpy(buffer, int_buf, 4);
    memset(int_buf, 0, 4);

    // Serialize Startup
    to_bytes(int_buf, beacon->StartUpTime);
    memcpy(buffer + 4, int_buf, 4);
    memset(int_buf, 0, 4);

    // Serialize Interval
    to_bytes(int_buf, beacon->TimeInterval);
    memcpy(buffer + 8, int_buf, 4);
    memset(int_buf, 0, 4);

    // Serialize IP
    memcpy(buffer + 12, beacon->IP[0], 3);
    memcpy(buffer + 15, beacon->IP[1], 3);
    memcpy(buffer + 18, beacon->IP[2], 3);
    memcpy(buffer + 21, beacon->IP[3], 3);

    // Serialize CmdPort
    to_bytes(int_buf, beacon->cmdPort);
    memcpy(buffer + 24, int_buf, 4);
}
