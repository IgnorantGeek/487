#include "./cmd_listener.h"

int main(int argc, char ** argv)
{
    pthread_t tcp_thread;

    struct beacon_arg args;
    //memset(&args, 0, sizeof(struct beacon_arg));
    if (argc == 1)
    {
        printf("No arguments detected. Using defaults (Manager IP: localhost | Port: 1069 | Interval : 1)\n");
        args.Port = TCP_PORT;
        args.Interval = 1;
        sprintf(args.ip, "%s", DEFAULT_HOST);
    }
    else if (argc == 2)
    {
        args.Port = TCP_PORT;
        args.Interval = 1;
        sprintf(args.ip, "%s", argv[1]);
    }
    else if (argc == 4)
    {
        args.Port = atoi(argv[2]);
        args.Interval = atoi(argv[3]);
        sprintf(args.ip, "%s", argv[1]);
    }
    else
    {
        printf("ERROR - Usage: ./agent <Manager IP>\n               ./agent <Manager IP> <Port> <Interval>\n");
        exit(0);
    }

    // Run the cmd_listen. It will call send_beacon
    pthread_create(&tcp_thread, NULL, cmd_listen, &args);

    // Wait for threads to finish
    pthread_join(tcp_thread, NULL);
}
