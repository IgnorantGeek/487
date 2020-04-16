#include "gnutella.h"

// Entry point
int main()
{
    char ID[16];
    rand_str(ID, 16);
    struct HEADER header;
    memset(&header, 0, sizeof(header));
    init_header(ID, PING, 10, 25, &header);

    // printf("Header ID value before write: %s\n%d\n\n", header2.ID, header2.pl_length);
    // deserialize_header(header_byte, &header2);
    // printf("Header ID value before write: %s\n%d\n", header2.ID ,header2.pl_length);
    
    process_header(&header);

    std::vector<NEIGHBOR> neighbors;

    return 0;
}

// Initialize a header
void init_header(char ID[16], char pl_descriptor, int TTL, int pl_length, struct HEADER * header)
{
    for (int i = 0; i < 16; i++) header->ID[i] = ID[i];
    header->pl_descriptor = pl_descriptor;
    header->TTL = TTL;
    header->pl_length = pl_length;
}

// Process the incoming header
void process_header(struct HEADER * header)
{
    switch (header->pl_descriptor)
    {
        case PING:
            printf("We gotta ping here.\n");
            break;
        case PONG:
            printf("Ayyyye PONG boi here.\n");
            break;
        case QUERY:
            break;
        case QUERYHIT:
            printf("query hit\n");
            break;
        case PUSH:
            break;
    }
}

// Serialize a header for transmission over data stream
void serialize_header(struct HEADER * header, char bytes[23])
{
    // Descriptor ID write
    memcpy(bytes, header->ID, 16);

    // single byte writes
    bytes[16] = header->pl_descriptor;
    bytes[17] = header->TTL;
    bytes[18] = header->Hops;

    // pl_length write
    char pl_len_bytes[4];
    memset(pl_len_bytes, 0, 4);
    int_to_bytes_be(pl_len_bytes, header->pl_length);
    memcpy(bytes + 18, pl_len_bytes, 4);
}

// Deserialize an incoming header from the input stream
void deserialize_header(char bytes[23], struct HEADER * header)
{
    for (int i = 0; i < 16; i++)
    {
        header->ID[i] = bytes[i];
    }
    header->pl_descriptor = bytes[16];
    header->TTL = bytes[17];
    header->Hops = bytes[18];
    char pl_len_bytes[4];
    memset(pl_len_bytes, 0, 4);
    memcpy(pl_len_bytes, bytes + 18, 4);
    header->pl_length = toInteger32_be(pl_len_bytes);
}

// generate a random string
static char * rand_str(char * str, size_t size)
{
    srand(time(NULL));
    const char charset[] = 
    "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    if (size)
    {
        --size;
        for (size_t n = 0; n < size; n++)
        {
            int key = rand() % (int) (sizeof charset - 1);
            str[n] = charset[key];
        }
        str[size] = '\0';
    }
    return str;
}