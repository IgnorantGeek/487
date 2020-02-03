#ifndef UDP_CLIENT_H
 #define UDP_CLIENT_H

#include "./defaults.h"

// header file for UDP

struct BEACON
{
    int ID;
    int StartUpTime;
    int TimeInterval;
    char IP[4];
    int cmdPort;
};

#endif