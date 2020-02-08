#include "./tcp_server.h"

int main(int argc, char ** argv)
{
    pthread_t tcp_thread;

    // Run the cmd_listen. It will call send_beacon
    pthread_create(&tcp_thread, NULL, cmd_listen, NULL);

    // Wait for threads to finish
    pthread_join(tcp_thread, NULL);
}
