#include "./udp_client.h"
#include <pthread.h>

int main(int argc, char ** argv)
{
    pthread_t tcp_thread, udp_thread;

    // Run send_beacon and cmd_listen
    pthread_create(&udp_thread, NULL, send_beacon, NULL);
    pthread_create(&tcp_thread, NULL, cmd_listen, NULL);

    // Wait for threads to finish
    pthread_join(udp_thread, NULL);
    pthread_join(tcp_thread, NULL);
}
