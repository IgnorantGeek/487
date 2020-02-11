#include "./cmd_listener.h"

int main(int argc, char ** argv)
{
    pthread_t tcp_thread;

    printf("Arg count: %d\n", argc);
    struct beacon_arg args;
    memset(&args, 0, sizeof(struct beacon_arg));
    if (argc == 1)
    {
        printf("No arguments detected. Using default Port (1069) and Interval (1 min)\n");
        args.Port = TCP_PORT;
        args.Interval = 1;
    }
    else if (argc == 3)
    {
        args.Port = argv[1];
        args.Interval = argv[2];
    }
    else
    {
        printf("Usage: ./agent <Port> <Interval>");
        exit(0);
    }

    // Run the cmd_listen. It will call send_beacon
    pthread_create(&tcp_thread, NULL, cmd_listen, &args);

    // Wait for threads to finish
    pthread_join(tcp_thread, NULL);
}
