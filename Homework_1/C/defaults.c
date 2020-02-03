#include "defaults.h";

void configure_route(struct sockaddr_in * clientAddr, unsigned short Port, char * Host)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = inet_addr(Host);
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