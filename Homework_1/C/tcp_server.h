#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

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

// Header file for the tcp server
void configure_route(struct sockaddr_in * clientAddr, unsigned short Port);
void receive_one_byte(int client_socket, char *cur_char);
void receive_bytes(int client_socket, char * buffer, int length);
int toInteger32_le(char *bytes);
int toInteger32_be(char *bytes);

#endif