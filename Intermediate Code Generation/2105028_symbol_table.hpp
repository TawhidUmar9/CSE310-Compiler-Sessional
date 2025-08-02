#pragma once
#include <sstream>
#ifndef SYMBOL_TABLE_HPP
#define SYMBOL_TABLE_HPP

#define DEFAULT_BUCKET_SIZE 10

#include "2105028_scope_table.hpp"

#define DEFAULT_BUCKET_SIZE 10

class symbol_table
{
private:
    scope_table *current_scope_table;
    scope_table *scope_table_list;
    int bucket_size;
    int hash_id = 0;
    int scope_table_count = 0;
    int collision_count = 0;

public:
    symbol_table(int bucket_size = DEFAULT_BUCKET_SIZE, int hash_id = 0) : bucket_size(bucket_size)
    {
        current_scope_table = new scope_table(bucket_size, ++scope_table_count, nullptr, hash_id);
        scope_table_list = current_scope_table;
        this->hash_id = hash_id;
    }
    ~symbol_table()
    {
        while (current_scope_table != nullptr)
        {
            scope_table *next_scope = current_scope_table->get_parent_scope();
            delete current_scope_table;
            current_scope_table = next_scope;
        }
    }

    bool enter_scope()
    {
        if (current_scope_table == nullptr)
        {
            current_scope_table = new scope_table(bucket_size, ++scope_table_count, nullptr, hash_id);
            scope_table_list = current_scope_table;
        }
        else
        {
            scope_table *new_scope = new scope_table(bucket_size, ++scope_table_count, current_scope_table, hash_id);
            current_scope_table = new_scope;
        }
        return true;
    }
    bool exit_scope()
    {
        if (current_scope_table == nullptr)
            return false;
        if (get_current_scope_id() == 1)
            return false;
        scope_table *temp = current_scope_table;
        current_scope_table = current_scope_table->get_parent_scope();
        int current_collision_count = temp->get_collision_count();
        delete temp;
        collision_count += current_collision_count;
        return true;
    }
    bool insert(const std::string &name, const std::string &type, const std::string &return_type = "void", int number_of_param = 0,
                const std::string &symbol_type = "INT", const std::string &symbol_category = "VARIABLE", const std::string &params_list = "")
    {
        if (current_scope_table == nullptr)
            return false;
        symbol_info *new_symbol = new symbol_info(name, type, return_type, number_of_param, symbol_type, symbol_category, params_list);
        bool val = current_scope_table->insert(new_symbol);
        print_all_scopes();
        return val;
    }
    bool remove(const std::string &name)
    {
        if (current_scope_table == nullptr)
            return false;

        return current_scope_table->delete_symbol(name);
    }
    symbol_info *lookup(const std::string &name) const
    {
        if (current_scope_table == nullptr)
            return nullptr;

        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            symbol_info *symbol = temp->lookup(name);
            if (symbol != nullptr)
                return symbol;
            temp = temp->get_parent_scope();
        }
        return nullptr;
    }

    int get_current_scope_id() const
    {
        if (current_scope_table != nullptr)
            return current_scope_table->get_id();
        return -1;
    }

    void print_current_scope() const
    {
        if (current_scope_table != nullptr)
            current_scope_table->print_table(1);
    }
    void print_all_scopes() const
    {
        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            int id = temp->get_id();
            std::cout << "ScopeTable # " << id << std::endl;
            temp->print_table(0);
            temp = temp->get_parent_scope();
        }
        std::cout << std::endl;
    }
    std::string get_all_scopes_as_string() const
    {
        std::ostringstream oss;
        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            int id = temp->get_id();
            oss << "ScopeTable # " << id << std::endl;
            temp->print_table(0, oss);
            temp = temp->get_parent_scope();
        }
        return oss.str();
    }
    std::pair<int, int> get_position_of_symbol(const std::string &name) const
    {
        if (current_scope_table == nullptr)
            return std::make_pair(-1, -1);
        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            symbol_info *symbol = temp->lookup(name);
            if (symbol != nullptr)
            {
                int second = temp->hash_function(name) % temp->get_bucket_size();
                int first = 0;
                symbol_info *current_symbol = temp->get_table()[second];
                while (current_symbol != nullptr)
                {
                    if (current_symbol->getSymbolName() == name)
                        break;
                    current_symbol = current_symbol->getNextSymbol();
                    first++;
                }
                return std::make_pair(first, second);
            }
            temp = temp->get_parent_scope();
        }
        return std::make_pair(-1, -1);
    }
    scope_table *get_current_scope_table() const
    {
        return current_scope_table;
    }

    int get_scope_table_count() const
    {
        return scope_table_count;
    }

    int lookup_target_scope_id(const std::string &name)
    {
        if (current_scope_table == nullptr)
            return -1;

        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            symbol_info *symbol = temp->lookup(name);
            if (symbol != nullptr)
                return temp->get_id();
            temp = temp->get_parent_scope();
        }
        return -1;
    }
    int get_collision_count() const
    {
        int count = 0;
        scope_table *temp = current_scope_table;
        while (temp != nullptr)
        {
            count += temp->get_collision_count();
            temp = temp->get_parent_scope();
        }
        count += collision_count;
        return count;
    }
    int get_hash_id() const { return hash_id; }
    int get_bucket_size() const { return bucket_size; }
    void set_offset_for_symbol(const std::string &name, int offset)
    {
        symbol_info *symbol = lookup(name);
        if (symbol != nullptr)
        {
            symbol->set_offset(offset);
        }
    }
    int get_offset_for_symbol(const std::string &name) const
    {
        symbol_info *symbol = lookup(name);
        if (symbol != nullptr)
        {
            return symbol->get_offset();
        }
        return -1;
    }
};

#endif // SYMBOL_TABLE_HPP