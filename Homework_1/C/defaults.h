#ifndef DEFAULTS_H
 #define DEFAULTS_H

#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>
#include <time.h>

// Defaults header file

#define DEFAULT_HOST "127.0.0.1"

#define TCP_PORT 51717
#define UDP_PORT 51716

void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname);
void configure_route_any(struct sockaddr_in * routeAddr, unsigned short Port);
int toInteger32_le(char *bytes);
int toInteger32_be(char *bytes);

#endif