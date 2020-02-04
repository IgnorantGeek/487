#ifndef UDP_CLIENT_H
 #define UDP_CLIENT_H

#include "./tcp_server.h"

// header file for UDP

struct BEACON
{
    int ID;
    int StartUpTime;
    int TimeInterval;
    char *IP[4];
    int cmdPort;
};

void configure_beacon(struct BEACON * beacon, int ID, int TimeInterval, char *IP[4], int cmdPort);

#endif