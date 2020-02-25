#include "rpc_server.h"

int main()
{
    // Initialize locals
    int server_socket;
    struct sockaddr_in server_addr;

    // set the sockaddr_in struct to 0
    memset(&server_addr, 0, sizeof(server_addr));

    // Create the socket
    if ((server_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0)
    {
        perror("SOCKET ERROR: Failed to create TCP socket\n");
        exit(EXIT_FAILURE);
    }

    int port = 1069;

    while (1)
    {
        configure_route_any(&server_addr, port);
        if ((bind(server_socket, (struct sockaddr *) &server_addr, sizeof(server_addr))) == 0) 
        {
            printf("Using port: %d\n", port);
            break;
        }
        port++;
    }

    // Listen for connections from client
    printf("TCP-SERVER: socket created. Listening for incoming connections....\n");
    listen(server_socket, 5);

    // Initialize the client address
    struct sockaddr client_addr;
    unsigned int client_len;

    while (1)
    {
        // Accept the connection
        int client_socket = accept(server_socket, &client_addr, &client_len);
        printf("TCP-SERVER: Acknowledgement recieved. Reading transmission....\n");

        int *arg = malloc(sizeof(*arg));
        if ( arg == NULL )
        {
            fprintf(stderr, "Couldn't allocate memory for thread arg.\n");
            exit(EXIT_FAILURE);
        }

        *arg = client_socket;

        pthread_t pid;
        pthread_create(&pid, 0, process_command, arg);
    }
    return 0;
}

// Recieve a single byte from the socket
void receive_one_byte(int client_socket, char *cur_char)
{
    ssize_t bytes_received = 0;
    while (bytes_received != 1)
    {
        bytes_received = recv(client_socket, cur_char, 1, 0);
    }
}

// Recieve n bytes from the socket into a buffer
void receive_bytes(int client_socket, char * buffer, int n)
{
    char *cur_char = buffer;
    ssize_t bytes_received = 0;
    while (bytes_received != n)
    {
        receive_one_byte(client_socket, cur_char);
        cur_char++;
        bytes_received++;
    }
}

// Configure the socket route with a host
void configure_route_host(struct sockaddr_in * routeAddr, unsigned short Port, char * Hostname)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = inet_addr(Hostname);
}

// Configure the socket route with any in address (for testing)
void configure_route_any(struct sockaddr_in * routeAddr, int Port)
{
    routeAddr->sin_family = AF_INET;
    routeAddr->sin_port = htons(Port);
    routeAddr->sin_addr.s_addr = INADDR_ANY;
}

// Big Endian
int toInteger32_be(char *bytes)
{
    int tmp = (bytes[0] << 24) + 
            (bytes[1] << 16) + 
            (bytes[2] << 8) + 
            bytes[3];
    return tmp;
}

void getLocalTime(GET_LOCAL_TIME *lt)
{
    lt->time = time(NULL);
    lt->valid = '0';
}

void getLocalOs(GET_LOCAL_OS *os)
{
    bzero(os->OS, 16);
    memcpy(os->OS, "Linux", 6);
    os->valid = '1';
}

// Convert a 32 bit integer to bytes
void int_to_bytes(char bytes[4], int32_t n)
{
    bytes[0] = (n >> 24) & 0xFF;
    bytes[1] = (n >> 16) & 0xFF;
    bytes[2] = (n >> 8) & 0xFF;
    bytes[3] = n & 0xFF;
}

void * process_command(void * arg)
{
    int client_socket = *((int *) arg);
    char header[104];
    bzero(header, 104);
    read(client_socket, header, 104);

    char buf_len_in_bytes[4];
    memcpy(buf_len_in_bytes, header+100, 4);
    int buf_len = toInteger32_be(buf_len_in_bytes);
    
    // Check command ID
    if(strstr((const char *)header, "GetLocalTime") != NULL)
    {
        char buffer[buf_len];
        bzero(buffer, buf_len);
        // run Get local time
        GET_LOCAL_TIME lt;
        memset(&lt, 0, sizeof(lt));
        getLocalTime(&lt);
        char time[4];
        int_to_bytes(time, lt.time);
        memcpy(buffer, time, 4);
        buffer[4] = lt.valid;
        send(client_socket, header, 104, 0);
        send(client_socket, buffer, buf_len, 0);
    }
    else if(strstr((const char *)header, "GetLocalOS") != NULL)
    {
        char buffer[104+buf_len];
        bzero(buffer, buf_len);
        // run get local os
        GET_LOCAL_OS os;
        memset(&os, 0, sizeof(os));
        getLocalOs(&os);
        memcpy(buffer, header, 104);
        memcpy(buffer+104, os.OS, 16);
        buffer[104+buf_len-1] = os.valid;
        send(client_socket, buffer, 104+buf_len, 0);
    }
    else if(strstr((const char *)header, "GetDiskData") != NULL)
    {
        // run get disk data
    }
    else
    {
        printf("COMMAND ERROR: could not find specified command");
        exit(EXIT_FAILURE);
    }

    pthread_exit(NULL);
}