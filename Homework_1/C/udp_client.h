#ifndef UDP_CLIENT_H
 #define UDP_CLIENT_H

#include "./defaults.h"

// header file for UDP
void configure_route(struct sockaddr_in * clientAddr, unsigned short Port);
int toInteger32_le(char *bytes);
int toInteger32_be(char *bytes);

#endif