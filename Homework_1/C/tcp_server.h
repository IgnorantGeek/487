#ifndef TCP_SERVER_H
 #define TCP_SERVER_H

#include "./defaults.h"
#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <arpa/inet.h>

// Header file for the server
void confClientAddr(struct sockaddr_in * clientAddr, int Port);

#endif