#ifndef SCOPE_TABLE_HPP
#define SCOPE_TABLE_HPP

#include "sdbm_hash.hpp"
#include "symbol_info.hpp"
#include <string>

class scope_table
{
private:
    int id;
    int hash_id = 0;
    int bucket_size;
    int collision_count = 0;
    symbol_info **table;
    scope_table *parent_scope;

public:
    scope_table(int bucket_size, int id = 1, scope_table *parent = nullptr, int hash_id = 0)
        : id(id), bucket_size(bucket_size), parent_scope(parent), hash_id(hash_id)
    {
        std::cout << "\tScopeTable# " << id << " created" << std::endl;
        table = new symbol_info *[bucket_size];
        for (int i = 0; i < bucket_size; ++i)
            table[i] = nullptr;
    }

    ~scope_table()
    {
        std::cout << "	ScopeTable# " << id << " removed" << std::endl;
        for (int i = 0; i < bucket_size; i++)
        {
            symbol_info *current = table[i];
            while (current != nullptr)
            {
                symbol_info *next = current->getNextSymbol();
                delete current;
                current = next;
            }
        }
        delete[] table;
    }

    int hash_function(const std::string &name)
    {
        if (hash_id == 1)
            return hash_function_one(name, bucket_size);
        else if (hash_id == 2)
            return hash_function_two(name, bucket_size);
        else
            return SDBMHash(name, bucket_size);
    }

    bool insert(symbol_info *symbol)
    {
        int index = hash_function(symbol->getSymbolName());

        symbol_info *current_symbol = table[index];
        if (current_symbol != nullptr)
            collision_count++;
        symbol->setNextSymbol(nullptr);

        if (current_symbol == nullptr)
        {
            table[index] = symbol;
            // print_table();
            return true;
        }
        else
        {
            while (current_symbol->getNextSymbol() != nullptr)
            {
                current_symbol = current_symbol->getNextSymbol();
            }
            current_symbol->setNextSymbol(symbol);
            // print_table();
            return true;
        }
        return false;
    }

    symbol_info *lookup(const std::string &name)
    {
        int index = hash_function(name);
        symbol_info *current_symbol = table[index];
        while (current_symbol != nullptr)
        {
            if (current_symbol->getSymbolName() == name)
            {
                return current_symbol;
            }
            current_symbol = current_symbol->getNextSymbol();
        }
        return nullptr;
    }

    bool delete_symbol(const std::string &name)
    {
        int index = hash_function(name);
        symbol_info *current_symbol = table[index];
        symbol_info *previous_symbol = nullptr;
        while (current_symbol != nullptr)
        {
            if (current_symbol->getSymbolName() == name)
            {
                if (previous_symbol == nullptr)
                {
                    table[index] = current_symbol->getNextSymbol();
                }
                else
                {
                    previous_symbol->setNextSymbol(current_symbol->getNextSymbol());
                }
                delete current_symbol;
                return true;
            }
            previous_symbol = current_symbol;
            current_symbol = current_symbol->getNextSymbol();
        }
        return false;
    }

    void print_table(int white_space_count)
    {
        for (int i = 0; i < bucket_size;)
        {
            symbol_info *current_symbol = table[i];
            for (int j = 0; j < white_space_count; j++)
                std::cout << "	";
            std::cout << ++i << "--> ";
            if (current_symbol != nullptr)
            {
                while (current_symbol != nullptr)
                {
                    current_symbol->print();
                    current_symbol = current_symbol->getNextSymbol();
                }
                std::cout << std::endl;
            }
            else
                std::cout << std::endl;
        }
    }

    int get_id() { return id; };
    void set_id(int id) { this->id = id; };
    int get_bucket_size() { return bucket_size; };
    int get_collision_count() { return collision_count; };
    scope_table *get_parent_scope() { return parent_scope; };
    symbol_info **get_table() { return table; };
};

#endif // SCOPE_TABLE_HPP
