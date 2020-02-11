#ifndef CMD_LISTENER_H
 #define CMD_LISTENER_H

#include "./transponder.h"

// Header file for the tcp command listener

void * cmd_listen(void * args);
void receive_one_byte(int client_socket, char *cur_char);
void receive_bytes(int client_socket, char * buffer, int n);
void get_local_os(char OS[16], int * valid);
void get_local_time(int * time, int * valid);

#endif