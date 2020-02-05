#ifndef UDP_CLIENT_H
 #define UDP_CLIENT_H

#include "./tcp_server.h"

// header file for UDP

struct BEACON
{
    int ID;
    int StartUpTime;
    int TimeInterval;
    unsigned char IP[4];
    int cmdPort;
};

void configure_beacon(struct BEACON * beacon, int TimeInterval, unsigned char IP[4], int cmdPort);
void serialize_beacon(struct BEACON * beacon, char buffer[20]);

#endif