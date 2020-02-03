#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

#include "./defaults.h"

// Header file for the tcp server
void configure_route(struct sockaddr_in * clientAddr, unsigned short Port);
void receive_one_byte(int client_socket, char *cur_char);
void receive_bytes(int client_socket, char * buffer, int length);
int toInteger32_le(char *bytes);
int toInteger32_be(char *bytes);

#endif