#include "tcp_client.h"

int main(int argc, char *args[])
{
    // Initialize locals
    int cSock;
    char buffer[1024];
    struct sockaddr_in sin;
    memset(&sin, 0, sizeof(sin));

    // Create the network socket
    if ((cSock = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        perror("socket");
        printf("Failed to create socket\n");
        abort();
    }

    // Configure IP route
    struct hostent * host = gethostbyname(args[1]); // gets the hostname from the command line...?
    unsigned int svrAddr = * (unsigned long * ) host->h_addr_list[0];
    unsigned short svrPort = atoi(args[2]);
    sin.sin_family = AF_INET;
    sin.sin_addr.s_addr = svrAddr;
    sin.sin_port = htons(svrPort);

    return 0;
}

void confClientAddr(struct sockaddr_in * clientAddr, int Port)
{
    clientAddr->sin_family = AF_INET;
    clientAddr->sin_port = htons(Port);
    clientAddr->sin_addr.s_addr = INADDR_ANY; // Vulnerability? Should the user specify where the connection is coming from?
}