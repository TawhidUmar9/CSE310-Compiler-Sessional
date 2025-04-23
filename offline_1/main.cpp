#include <iostream>
#include <fstream>
#include <sstream>
#include <cstring>
#include "symbol_table.hpp"
#include "symbol_info.hpp"

using namespace std;

void leading_whitespace_removal(string &input)
{
    input.erase(0, input.find_first_not_of(' '));
}
void trailing_whitespace_removal(string &input)
{
    input.erase(input.find_last_not_of(' ') + 1);
}

string get_type(const string &input)
{
    stringstream ss(input);
    string word, type;

    while (ss >> word)
    {
        if (!type.empty())
            type += ' ';
        type += word;
    }

    return type;
}

void test(symbol_table &symbol_table, ifstream &inputfile, streambuf *terminal, bool diff, string &line, bool &report)
{
    int count = 0;
    int collsion_count = 0;
    while (getline(inputfile, line))
    {
        ++count;
        bool delete_successful = false;
        // char command = line[0];
        string name, type;
        pair<int, int> position;
        leading_whitespace_removal(line);
        char command = line[0];
        line = line.substr(1);
        leading_whitespace_removal(line);
        string target;

        // stringstream ss(line);
        // cout<<"Cmd " << command << ": ";
        // cout<<line<<endl;
        // ss>>command;
        // ss>>line;

        // if (line.length() > 2)
        // {
        //     leading_whitespace_removal(line);
        //     line = line.substr(2);
        //     leading_whitespace_removal(line);
        // }

        name = line.substr(0, line.find_first_of(' '));
        cout << "Cmd " << count << ": ";

        switch (command)
        {
        case 'I':
            cout << command << " " << line << endl;
            if (symbol_table.get_current_scope_table()->lookup(name) != nullptr)
            {
                cout << "	'" << name << "' already exists in the current ScopeTable" << endl;
                break;
            }

            cout << "	Inserted in ScopeTable# " << symbol_table.get_current_scope_id() << " at position ";
            line = line.substr(line.find_first_of(' ') + line.find_first_not_of(' ') + 1);
            type = get_type(line);
            symbol_table.insert(name, type);
            cout << symbol_table.get_position_of_symbol(name).second + 1 << ", "
                 << symbol_table.get_position_of_symbol(name).first + 1 << endl;
            break;

        case 'L':
        {
            cout << command << " " << line << endl;
            leading_whitespace_removal(line);
            trailing_whitespace_removal(line);
            target = line.substr(0, line.find_first_of(' '));
            if (target.length() != line.length())
            {
                cout << "	Number of parameters mismatch for the command L" << endl;
                break;
            }

            symbol_info *symbol = symbol_table.lookup(target);

            // have to get the id of the containing scope table
            int id;
            id = symbol_table.lookup_target_scope_id(target);
            if (symbol != nullptr)
            {
                cout << "	'" << target << "' found in ScopeTable# " << id << " at position ";
                cout << symbol_table.get_position_of_symbol(target).second + 1 << ", "
                     << symbol_table.get_position_of_symbol(target).first + 1 << endl;
            }
            else
            {
                cout << "	'" << name << "' not found in any of the ScopeTables" << endl;
            }
            break;
        }

        case 'D':
            trailing_whitespace_removal(line);
            if (line.length() < 1)
            {
                cout << command << endl;
                cout << "	Number of parameters mismatch for the command D" << endl;
                break;
            }
            cout << command << " " << line << endl;
            trailing_whitespace_removal(line);
            target = line.substr(0, line.find_first_of(' '));
            trailing_whitespace_removal(target);

            if (target.length() != line.length() || target.length() == 0)
            {
                cout << "	Number of parameters mismatch for the command D" << endl;
                break;
            }

            position = symbol_table.get_position_of_symbol(name);
            delete_successful = symbol_table.get_current_scope_table()->delete_symbol(name);
            if (delete_successful)
            {
                cout << "	Deleted '" << name << "' from ScopeTable# " << symbol_table.get_current_scope_id()
                     << " at position " << position.second + 1 << ", " << position.first + 1 << endl;
            }
            else
            {
                cout << "	Not found in the current ScopeTable" << endl;
            }
            break;

        case 'P':
            cout << command << " " << line << endl;
            leading_whitespace_removal(line);
            if (line.length() > 2)
            {
                cout << "   Number of parameters mismatch for the command P" << endl;
                break;
            }

            if (line == "C")
            {
                cout << "	ScopeTable# " << symbol_table.get_current_scope_id() << endl;
                symbol_table.print_current_scope();
            }
            else if (line == "A")
            {
                symbol_table.print_all_scopes();
            }
            else
            {
                cout << "   Invalid command for P" << endl;
            }
            break;

        case 'S':
            cout << command << endl;
            symbol_table.enter_scope();
            break;

        case 'E':
            cout << command << endl;
            leading_whitespace_removal(line);
            if (line.length() > 2)
            {
                cout << "   Number of parameters mismatch for the command E" << endl;
                break;
            }

            if (!symbol_table.exit_scope())
            {
                cout << "   No scope exists" << endl;
            }
            break;

        case 'Q':
            cout << command << endl;
            leading_whitespace_removal(line);
            if (line.length() > 2)
            {
                cout << "   Number of parameters mismatch for the command Q" << endl;
                break;
            }
            inputfile.close();
            break;

        default:
            break;
        }
    }
    int collision_count = symbol_table.get_collision_count();
    if (report)
    {
        ofstream report_file("report.txt", ios::app);
        if (!report_file)
        {
            cerr << "Error: cannot open report.txt" << endl;
            return;
        }

        int collision_count = symbol_table.get_collision_count();
        report_file << "Hash Function ID: " << symbol_table.get_hash_id() << endl;
        report_file << "Total number of collisions: " << collision_count << endl;
        report_file << "Bucket Size: " << symbol_table.get_bucket_size() << endl;
        report_file << "Ration: " << (float)collision_count / symbol_table.get_bucket_size() << endl;
        report_file << "--------------------------------------" << endl;
    }

    symbol_table.~symbol_table();
}

int check_arguments(int argc, char *argv[],
                    bool &file, bool &diff, bool &help, bool &hash, bool &report,
                    int &hash_id, string &diff_file1, string &diff_file2, string &output_file)
{
    file = false;
    diff = false;
    help = false;
    hash = false;
    report = false;
    hash_id = 0;

    for (int i = 1; i < argc; i++)
    {
        if (strcmp(argv[i], "-f") == 0 && i + 1 < argc)
        {
            file = true;
            output_file = argv[++i];
        }
        else if (strcmp(argv[i], "-d") == 0 && i + 2 < argc)
        {
            diff = true;
            diff_file1 = argv[++i];
            diff_file2 = argv[++i];
        }
        else if (strcmp(argv[i], "-h") == 0)
        {
            help = true;
        }
        else if (strcmp(argv[i], "-S") == 0 && i + 1 < argc)
        {
            hash = true;
            hash_id = atoi(argv[++i]);
        }
        else if (strcmp(argv[i], "-r") == 0)
        {
            report = true;
        }
        else if (strcmp(argv[i], "-f") != 0 &&
                 strcmp(argv[i], "-d") != 0 &&
                 strcmp(argv[i], "-S") != 0 &&
                 strcmp(argv[i], "-r") != 0 &&
                 strcmp(argv[i], "-h") != 0)
        {
            cout << "Invalid argument: " << argv[i] << endl;
            cout << "Use -h for help" << endl;
            return 1;
        }
    }

    return 0;
}
int main(int argc, char *argv[])
{
    if (argc < 2)
    {
        cout << "No arguments provided\nUse -h for help" << endl;
        return 0;
    }

    bool file, diff, help, hash, report;
    int hash_id;
    string diff_file1, diff_file2, output_file;

    check_arguments(argc, argv, file, diff, help, hash, report, hash_id, diff_file1, diff_file2, output_file);

    if (help)
    {
        cout << "Usage: ./a.out [-f <output_filename>] [-d <file1> <file2>] [-S <hash_id>] [-r]" << endl;
        cout << "-f for output file" << endl;
        cout << "-d for diff chekcing between two files" << endl;
        cout << "-S for Hashi id-> 0 : for SDBM, 1 : for Custom Hash 1, 2 : for Custom Hash 2" << endl;
        cout << "-r for report generation" << endl;
        return 0;
    }

    if (hash && (hash_id < 0 || hash_id > 2))
    {
        cout << "Invalid hash function. Using default SDBM (0)." << endl;
        hash_id = 0;
    }

    streambuf *terminal = cout.rdbuf();
    ofstream fout;
    if (file)
    {
        fout.open(output_file);
        if (!fout)
        {
            cerr << "Error: cannot open output file " << output_file << endl;
            return 1;
        }
        cout.rdbuf(fout.rdbuf());
    }

    ifstream inputfile("sample_input.txt");
    if (!inputfile)
    {
        cerr << "Error: cannot open sample_input.txt" << endl;
        return 1;
    }

    string line;
    getline(inputfile, line);
    int bucket_size = stoi(line);

    if (report)
    {
        for (int i = 0; i < 3; i++)
        {
            ifstream inputfile("sample_input.txt");
            if (!inputfile)
            {
                cerr << "Error: cannot open sample_input.txt" << endl;
                return 1;
            }

            string line;
            getline(inputfile, line);
            int bucket_size = stoi(line);

            symbol_table symbol_table(bucket_size, i);
            test(symbol_table, inputfile, terminal, diff, line, report);
        }
        cout.rdbuf(terminal);
        cout << "Report generated in report.txt" << endl;
        return 0;
    }
    else
    {
        symbol_table symbol_table(bucket_size, hash_id);
        test(symbol_table, inputfile, terminal, diff, line, report);
    }
    if (diff)
    {
        string command = "diff " + diff_file1 + " " + diff_file2 + " > diff_report.txt";
        int ret = system(command.c_str());
        cout.rdbuf(terminal);
        cout << "Diff written to diff_report.txt" << endl;
    }
    return 0;
}
