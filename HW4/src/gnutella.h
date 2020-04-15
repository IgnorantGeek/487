#ifndef GNUTELLA_H
 #define GNUTELLA_H

// Header file for the gnutella node project
#include <stdio.h>
#include <stdlib.h>
#include "defaults.h"

#define VERSION "0.4"
#define PING 0x00
#define PONG 0x01
#define PUSH 0x40
#define QUERY 0x80
#define QUERYHIT 0x81


struct HEADER
{
    char ID[16];
    char payload_descriptor;
    unsigned char TTL;
    unsigned char Hops;
    int32_t payload_length;
};



// need to implement 5 methods: Ping, Pong, Query, QueryHit, and Push
void serialize_header(struct HEADER, char bytes[23]);

#endif