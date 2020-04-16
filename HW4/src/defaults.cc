#include "defaults.h"

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

// Convert a 32 bit integer to bytes in big endian format
void int_to_bytes_be(char bytes[4], int32_t n)
{
    bytes[0] = (n >> 24) & 0xFF;
    bytes[1] = (n >> 16) & 0xFF;
    bytes[2] = (n >> 8) & 0xFF;
    bytes[3] = n & 0xFF;
}

// Convert a 32 bit integer to bytes in little endian format
void int_to_bytes_le(char bytes[4], int32_t n)
{
    bytes[0] = n & 0xff;
    bytes[1] = (n >> 8) & 0xff;
    bytes[2] = (n >> 16) & 0xff;
    bytes[3] = (n >> 24) & 0xff;
}

// Convert an ip as a string to 4 bytes
void ip4_to_bytes(unsigned char buffer[4], char * ip_addr)
{
    sscanf(ip_addr, "%d.%d.%d.%d", (int *) &buffer[0], (int *) &buffer[1], (int *) &buffer[2], (int *) &buffer[3]);
}

void get_ip_4(char addr[16])
{
    struct ifaddrs * ifAddrStruct=NULL;
    struct ifaddrs * ifa=NULL;
    void * tmpAddrPtr=NULL;

    getifaddrs(&ifAddrStruct);

    for (ifa = ifAddrStruct; ifa != NULL; ifa = ifa->ifa_next)
    {
        if (!ifa->ifa_addr)
        {
            continue;
        }
        if (ifa->ifa_addr->sa_family == AF_INET) // check it is IP4
        { 
            // is a valid IP4 Address
            tmpAddrPtr=&((struct sockaddr_in *)ifa->ifa_addr)->sin_addr;
            char addressBuffer[INET_ADDRSTRLEN];
            inet_ntop(AF_INET, tmpAddrPtr, addressBuffer, INET_ADDRSTRLEN);
            char find[] = "127";
            int flag = 0;
            for (int i = 0; i < 3; i++)
            {
                if (find[i] != addressBuffer[i])
                {
                    flag = 1;
                }
            }
            if (flag)
            {
                memcpy(addr, addressBuffer, 16);
                return;
            }
        }
    }
    if (ifAddrStruct!=NULL) freeifaddrs(ifAddrStruct);
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