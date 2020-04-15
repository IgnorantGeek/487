#include "gnutella.h"

int main()
{
    printf("%s\n", VERSION);
    return 0;
}

// Initialize the header
void init_header(char ID[16], char pl_descriptor, int TTL, int pl_length, struct HEADER * header)
{
    for (int i = 0; i < 16; i++) header->ID[i] = ID[i];
    header->pl_descriptor = pl_descriptor;
    header->TTL = TTL;
    header->pl_length = pl_length;
}

void serialize_header(struct HEADER * header, char bytes[23])
{
    // Descriptor ID write
    for (int i = 0; i < 16; i++)
    {
        bytes[i] = header->ID[i];
    }

    // single byte writes
    bytes[16] = header->pl_descriptor;
    bytes[17] = header->TTL;
    bytes[18] = header->Hops;

    // pl_length write
    char pl_len_bytes[4];
    int_to_bytes_be(pl_len_bytes, header->pl_length);
    bytes[19] = pl_len_bytes[0];
    bytes[20] = pl_len_bytes[1];
    bytes[21] = pl_len_bytes[2];
    bytes[22] = pl_len_bytes[3];
}