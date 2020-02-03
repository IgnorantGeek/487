#include "defaults.h"

void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = inet_addr(Hostname);
}

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