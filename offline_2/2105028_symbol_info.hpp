#ifndef SYMBOL_INFO_HPP
#define SYMBOL_INFO_HPP

#include <iostream>
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
    symbol_info() : next_symbol(nullptr) {}
    symbol_info(const std::string &name, const std::string &type)
        : symbol_name(name), type(type), next_symbol(nullptr) {}

    void setSymbolName(const std::string &name) { symbol_name = name; }
    void setType(const std::string &type) { this->type = type; }
    void setNextSymbol(symbol_info *next) { next_symbol = next; }

    std::string getSymbolName() const { return symbol_name; }
    std::string getType() const { return type; }
    symbol_info *getNextSymbol() const { return next_symbol; }

    void print() const
    {
        std::cout << "< " << symbol_name << " : ";

        std::string type_str = type.substr(0, type.find_first_of(' '));
        std::string params = type.substr(type.find_first_of(' ') + 1);

        for (char &c : type_str)
            c = std::tolower(c);

        if (type_str == "function")
        {
            std::cout << "FUNCTION,";
            std::stringstream ss(params);
            std::string return_type;
            ss >> return_type;

            std::cout << return_type << "<==(";

            bool first = true;
            std::string param;
            while (ss >> param)
            {
                if (!first)
                    std::cout << ",";
                std::cout << param;
                first = false;
            }

            std::cout << ")> ";
        }
        else if (type_str == "struct")
        {
            std::cout << "STRUCT,{";
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
                    std::cout << ",";
                std::cout << "(" << name << "," << type << ")";
                first = false;
            }
            std::cout << "}> ";
        }
        else if (type_str == "union")
        {
            std::cout << "UNION,{";
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
                    std::cout << ",";
                std::cout << "(" << name << "," << type << ")";
                first = false;
            }
            std::cout << "}> ";
        }
        else
        {
            std::cout << type << " >";
        }
    }
};

#endif // SYMBOL_INFO_HPP
