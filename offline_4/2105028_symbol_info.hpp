#pragma once
#ifndef SYMBOL_INFO_HPP
#define SYMBOL_INFO_HPP

#include <iostream>
#include <algorithm>
#include <string>
#include <sstream>
#include <cctype>

class symbol_info
{
private:
    std::string symbol_name;
    std::string type;
    symbol_info *next_symbol;

public:
    std::string return_type;
    int number_of_param;
    std::string symbol_type;
    std::string symbol_category; // Added to store the category of the symbol
    std::string parameter_list;
    int offset;
    symbol_info() : next_symbol(nullptr) {}
    symbol_info(const std::string &name, const std::string &type, const std::string return_type = "void",
                int number_of_param = 0, const std::string &symbol_type = "INT", const std::string &symbol_category = "VARIABLE", const std::string &parameter_list = "")
        : symbol_name(name), type(type), return_type(return_type),
          number_of_param(number_of_param), symbol_type(symbol_type),
          symbol_category(symbol_category), next_symbol(nullptr), parameter_list(parameter_list)
    {
        char target = ',';
        int count = std::count(parameter_list.begin(), parameter_list.end(), target) + 1;
        if (parameter_list == "")
            count = 0;
        this->number_of_param = count;
    }

    void setSymbolName(const std::string &name) { symbol_name = name; }
    void setType(const std::string &type) { this->type = type; }
    void setNextSymbol(symbol_info *next) { next_symbol = next; }

    std::string getSymbolName() const { return symbol_name; }
    std::string getType() const { return type; }
    symbol_info *getNextSymbol() const { return next_symbol; }
    void setParams_list(const std::string &paramlist)
    {
        this->parameter_list = paramlist;
        char target = ',';
        int count = std::count(paramlist.begin(), paramlist.end(), target) + 1;
        if (paramlist == "")
            count = 0;
        this->number_of_param = count;
    }

    void print(std::ostream &os = std::cout) const
    {
        os << "< " << symbol_name << " : ";

        std::string type_str = type.substr(0, type.find_first_of(' '));
        std::string params = type.substr(type.find_first_of(' ') + 1);

        for (char &c : type_str)
            c = std::tolower(c);

        if (type_str == "function")
        {
            os << "FUNCTION,";
            std::stringstream ss(params);
            std::string return_type;
            ss >> return_type;

            os << return_type << "<==(";

            bool first = true;
            std::string param;
            while (ss >> param)
            {
                if (!first)
                    os << ",";
                os << param;
                first = false;
            }

            os << ")> ";
        }
        else if (type_str == "struct")
        {
            os << "STRUCT,{";
            std::stringstream ss(params);
            std::string param;
            bool first = true;
            while (ss >> param)
            {
                std::string name = param;
                if (!(ss >> param))
                    break;
                std::string type = param;

                if (!first)
                    os << ",";
                os << "(" << name << "," << type << ")";
                first = false;
            }
            os << "}> ";
        }
        else if (type_str == "union")
        {
            os << "UNION,{";
            std::stringstream ss(params);
            std::string param;
            bool first = true;
            while (ss >> param)
            {
                std::string name = param;
                if (!(ss >> param))
                    break;
                std::string type = param;

                if (!first)
                    os << ",";
                os << "(" << name << "," << type << ")";
                first = false;
            }
            os << "}> ";
        }
        else
        {
            os << type << " >";
        }
    }
    bool is_array()
    {
        return symbol_category == "array";
    }
    bool is_function()
    {
        return symbol_type == "function";
    }
    std::string get_return_type()
    {
        return return_type;
    }
    std::string get_type()
    {
        return symbol_type;
    }
    std::string get_parameter_list()
    {
        return parameter_list;
    }
    int get_offset() const
    {
        return offset;
    }
    void set_offset(int offset)
    {
        this->offset = offset;
    }
};

#endif // SYMBOL_INFO_HPP
