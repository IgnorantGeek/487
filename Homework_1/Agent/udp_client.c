#include "udp_client.h"

// Creates a beacon, and sends it to the server every x minutes, specified by timeInterval value
void * send_beacon(void * args)
{
    // Initialize locals
    int server_socket;
    struct sockaddr_in client_addr;
    memset(&client_addr, 0, sizeof(client_addr));
    struct BEACON send_beacon;
    memset(&send_beacon, 0, sizeof(send_beacon));

    // Create the network socket
    if ((server_socket = socket(AF_INET, SOCK_DGRAM, 0)) < 0)
    {
        perror("SOCKET ERROR: Failed to create UDP socket\n");
        exit(EXIT_FAILURE);
    }

    unsigned char IP[4];

    ip4_to_bytes(IP, DEFAULT_HOST);

    configure_beacon(&send_beacon, 1, IP, 51717);

    printf("UDP-CLIENT: socket created.\n"); // \nUDP Beacon:\nID          - %d\nStartupTime - %d\n\n", send_beacon.ID, send_beacon.StartUpTime);

    // Configure IP route
    configure_route_host(&client_addr, UDP_PORT, DEFAULT_HOST);

    // Serialize the beacon for transfer
    char serial_buffer[20];
    serialize_beacon(&send_beacon, serial_buffer);

    // Send first payload
    sendto(server_socket, &serial_buffer, sizeof(serial_buffer), MSG_CONFIRM, (const struct sockaddr *) &client_addr, sizeof(client_addr));

    printf("UDP-CLIENT: Beacon sent.\n");

    // Send another beacon every x minutes, specified by the TimeInterval in the beacon
    while (1)
    {
        sleep(send_beacon.TimeInterval * 60);
        sendto(server_socket, &serial_buffer, sizeof(serial_buffer), MSG_CONFIRM, (const struct sockaddr *) &client_addr, sizeof(client_addr));
        printf("UDP-CLIENT: Beacon sent.\n");
    }

    return 0;
}

// Constructor for the beacon struct
void configure_beacon(struct BEACON * beacon, int TimeInterval, unsigned char IP[4], int cmdPort)
{
    srand(time(NULL));
    beacon->ID = rand();
    beacon->StartUpTime = time(NULL);
    beacon->TimeInterval = TimeInterval;
    memcpy(beacon->IP, IP, 4);
    beacon->cmdPort = cmdPort;
}

// Convert the beacon into a 20 byte message for UDP transport
void serialize_beacon(struct BEACON * beacon, char buffer[20])
{
    // Initialize a buffer for the numbers
    char int_buf[4];
    memset(int_buf, 0, 4);

    // Serialize ID
    int_to_bytes(int_buf, beacon->ID);
    memcpy(buffer, int_buf, 4);

    // Serialize Startup
    int_to_bytes(int_buf, beacon->StartUpTime);
    memcpy(buffer + 4, int_buf, 4);

    // Serialize Interval
    int_to_bytes(int_buf, beacon->TimeInterval);
    memcpy(buffer + 8, int_buf, 4);

    // Serialize IP
    memcpy(buffer + 12, beacon->IP, 4);

    // Serialize CmdPort
    int_to_bytes(int_buf, beacon->cmdPort);
    memcpy(buffer + 16, int_buf, 4);
}