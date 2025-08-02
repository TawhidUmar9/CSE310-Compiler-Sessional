#ifndef SDBM_HASH_HPP
#define SDBM_HASH_HPP

#include <string>

#define PRIME_1 31
#define PRIME_2 193

unsigned int SDBMHash(std::string str, unsigned int num_buckets)
{
    unsigned int hash = 0;

    unsigned int len = str.length();

    for (unsigned int i = 0; i < len; i++)
    {
        hash = ((str[i]) + (hash << 6) + (hash << 16) - hash) %
               num_buckets;
    }

    return hash;
}

unsigned int hash_function_one(std::string str, unsigned int num_buckets)
{
    unsigned int hash = 0;
    unsigned int len = str.length();
    for (unsigned int i = 0; i < len; i++)
    {
        hash = (hash * PRIME_1 + str[i]) % num_buckets;
    }
    return hash;
}

unsigned int hash_function_two(std::string str, unsigned int num_buckets)
{
    unsigned int hash = 0;
    unsigned int len = str.length();
    for (unsigned int i = 0; i < len; i++)
    {
        hash = (hash * PRIME_2 + str[i]) % num_buckets;
    }
    return hash;
}

#endif // SDBM_HASH_HPP