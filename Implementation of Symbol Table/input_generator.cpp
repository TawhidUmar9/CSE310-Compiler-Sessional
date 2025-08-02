#include <iostream>
#include <ostream>
#include <fstream>

std::string generate_word()
{
    std::string word;
    int str_len = rand() % 7 + 1;
    for (int i = 0; i < str_len; i++)
    {
        word += 'a' + rand() % 26;
    }
    return word;
}

void gen(int line_count = 100, int bucket_size = 10)
{
    std::ofstream output_file("sample_input.txt");

    if (!output_file)
    {
        std::cerr << "Error: cannot open sample_input.txt" << std::endl;
        return;
    }
    output_file << bucket_size << std::endl;
    for (int i = 0; i < line_count; i++)
    {
        output_file << "I " << generate_word() << std::endl;
    }
    output_file << "P C" << std::endl;
    output_file << "Q " << generate_word() << std::endl;
}

int main(int argc, char *argv[])
{
    srand(time(0));
    int line_count = 100;
    int bucket_size = 10;
    if (argc > 1)
    {
        line_count = std::stoi(argv[1]);
        if (argc > 2)
        {
            bucket_size = std::stoi(argv[2]);
        }
    }

    gen(line_count, bucket_size);
    std::cout << "sample_input.txt generated with " << line_count << " lines." << std::endl;
    return 0;
}