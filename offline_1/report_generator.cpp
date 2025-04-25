#include <iostream>


int main()
{
    std::string command = "./main.exe -f output.txt -r";
    int ret = system(command.c_str());
}