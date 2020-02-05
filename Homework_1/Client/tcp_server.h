#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

#include "./defaults.h"

// Header file for the tcp server

void * cmd_listen(void * args);
void receive_one_byte(int client_socket, char *cur_char);
void receive_bytes(int client_socket, char * buffer, int n);
void initialize_tcp_server(int socket_fd, struct sockaddr_in server_addr, char * host);

#endif