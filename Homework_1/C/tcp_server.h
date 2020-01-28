#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

#define DEF_PORT 56717

#include "./defaults.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <arpa/inet.h>

// Header file for the server
void configure_route(struct sockaddr_in * clientAddr, unsigned short Port);

#endif