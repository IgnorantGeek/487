#ifndef GNUTELLA_H
 #define GNUTELLA_H

// Header file for the gnutella node project
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
    char pl_descriptor;
    unsigned char TTL;
    unsigned char Hops;
    int32_t pl_length;
};


void serialize_header(struct HEADER * header, char bytes[23]);
void init_header(char ID[16], char pl_descriptor, int TTL, int pl_length, struct HEADER * header);
void send_header(char header[23]);
void send_payload(char * pl, int pl_length);
void generate_id(char ID[16]);

#endif