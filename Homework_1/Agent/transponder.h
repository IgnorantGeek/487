#ifndef TRANSPONDER_H
 #define TRANSPONDER_H

#include "./defaults.h"

// Header file for UDP beacon transponder

struct BEACON
{
    int32_t ID;
    int32_t StartUpTime;
    int32_t TimeInterval;
    unsigned char IP[4];
    int32_t cmdPort;
};

void * send_beacon(void * args);
void configure_beacon(struct BEACON * beacon, int TimeInterval, unsigned char IP[4], int cmdPort);
void serialize_beacon(struct BEACON * beacon, char buffer[20]);

#endif