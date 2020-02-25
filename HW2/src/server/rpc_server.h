#include <stdio.h>
#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <netdb.h>
#include <arpa/inet.h>
#include <string.h>
#include <stdint.h>
#include <time.h>
#include <pthread.h>
#include <sys/utsname.h>
#include <ifaddrs.h>

// RPC (Remote Procedure Call) Server header file

// Struct definitions
typedef struct
{
    unsigned int time;
    char valid;
} GET_LOCAL_TIME;

typedef struct
{
    char OS[16];
    char valid;
} GET_LOCAL_OS;

typedef struct
{
    unsigned int DiskNumber;
    unsigned int Cylinder;
    unsigned int Sector;
    char Status;
} GET_DISK_DATA;

// Function declarations
void getLocalTime(GET_LOCAL_TIME *lt);
void getLocalOs(GET_LOCAL_OS *os);
void getDiskData(GET_DISK_DATA *ds);
void receive_one_byte(int client_socket, char *cur_char);
void receive_bytes(int client_socket, char * buffer, int n);
void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname);
void configure_route_any(struct sockaddr_in * routeAddr, int Port);
int toInteger32_be(char *bytes);
void int_to_bytes(char bytes[4], int32_t n);
void * process_command(void * arg);