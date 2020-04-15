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
#include <stdint.h>
#include <time.h>
#include <pthread.h>
#include <sys/utsname.h>
#include <ifaddrs.h>

// Defaults header file

// Struct for input parameters
struct beacon_arg
{
    int Port;
    int Interval;
    char ip[16];
};

#define DEFAULT_HOST "127.0.0.1"

#define TCP_PORT 1069
#define UDP_PORT 1068

void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname);
void configure_route_any(struct sockaddr_in * routeAddr, unsigned short Port);
int toInteger32_le(char *bytes);
int toInteger32_be(char *bytes);
void int_to_bytes_be(char bytes[4], int32_t n);
void int_to_bytes_le(char bytes[4], int32_t n);
void ip4_to_bytes(unsigned char buffer[4], char * ip_addr);
void get_ip_4(char addr[16]);

#endif