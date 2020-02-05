#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

#include "./defaults.h"

// Header file for the tcp server
void receive_one_byte(int client_socket, unsigned char *cur_char);
void receive_bytes(int client_socket, unsigned char * buffer, int length);

#endif