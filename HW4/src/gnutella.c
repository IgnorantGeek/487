#include "gnutella.h"

int main()
{
    char ID[16];
    rand_str(ID, 16);
    printf("%s\n", ID);
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