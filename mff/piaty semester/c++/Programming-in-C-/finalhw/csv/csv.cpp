#include <iostream>
#include <vector>
#include <string>
#include <unordered_map>
#include <fstream>
#include <sstream>
#include <algorithm>


using Table = std::vector<std::vector<std::string>>;

class Reader {
public:
    Table read_csv(const std::string& file_name) {
        Table table;
        std::ifstream file(file_name);
        if (!file.is_open()) {
            throw std::runtime_error("Could not open file: " + file_name);
        }

        std::string line;
        std::vector<std::string> header;

        // Read the header
        if (std::getline(file, line)) {
            header = split_line(line);
            table.push_back(header);
        }
        else {
            throw std::runtime_error("Empty file or missing header.");
        }


        // Reading the rest of the file
        while (std::getline(file, line)) {
            if (line.empty()) {
                break;  // Stop reading if an empty line is encountered
            }

            std::vector<std::string> row = split_line(line);

            // Validate that the number of columns matches the header
            if (row.size() != header.size()) {
                throw std::runtime_error("Invalid row: Column count does not match header.");
            }

            table.push_back(row);  // Add the data row to the table
        }

        file.close();
        return table;
    }

private:
    // Splitting the line by "," 
    std::vector<std::string> split_line(const std::string& line) {
        std::vector<std::string> result;
        std::stringstream ss(line);
        std::string item;

        while (std::getline(ss, item, ',')) {
            result.push_back(item);
        }
        return result;
    }
};

class Helpers {
public:
    // Helper function to print rows and headers
    void print_table(const Table& table, const std::vector<size_t>& column_indices, const std::vector<size_t>& row_indices = {}) {
        // Print the header
        for (size_t i = 0; i < column_indices.size(); ++i) {
            std::cout << table[0][column_indices[i]];
            if (i != column_indices.size() - 1) std::cout << ",";
        }
        std::cout << "\n";

        // Print data rows (filtered if row_indices is provided)
        const auto& rows = row_indices.empty()
            ? std::vector<size_t>{1, table.size()} // All rows
        : row_indices;

        for (size_t i = 1; i < table.size(); ++i) { // Skip header
            for (size_t j = 0; j < column_indices.size(); ++j) {
                std::cout << table[i][column_indices[j]];
                if (j != column_indices.size() - 1) std::cout << ",";
            }
            std::cout << "\n";
        }
    }

    std::vector<std::string> split_columns(const std::string& columns) {
        std::vector<std::string> result;
        std::stringstream ss(columns);
        std::string column;

        while (std::getline(ss, column, ',')) {
            result.push_back(column);
        }
        return result;
    }

};

class Queries {
private:
    std::unordered_map<std::string, Table> database;
    Helpers helpers;
public:
    Queries() {
        std::cout << "Queries instance created.\n";
    }
    void execute_bulk_insert(const std::string& table_name, const std::string& file_name);
    void execute_drop_table(const std::string& table_name);
    void execute_select(const std::vector<std::string>& columns, const std::string& name);
    void execute_where(const std::vector<std::string>& columns, const std::string& filter_column, const std::string& value, const std::string& table_name);
    void execute_order_by(const std::string& table_name, const std::string& order_column);
};

void Queries::execute_bulk_insert(const std::string& table_name, const std::string& file_name) {
    Reader reader;

    std::ifstream file(file_name);
    if (!file.is_open()) {
        std::cout << "Query execution failed.\n";
        return;
    }

    Table table = reader.read_csv(file_name);

    if (table.empty()) {
        std::cout << "Query execution failed.\n";
        return;
    }
    // debug 
    std::cout << "Loaded table \"" << table_name << "\":\n";
    for (const auto& row : table) {
        for (const auto& cell : row) {
            std::cout << cell << " ";
        }
        std::cout << "\n";
    }

    database[table_name] = table;

    // Debug
    std::cout << "Database now contains:\n";
    for (const auto& entry : database) {
        std::cout << "- Table: " << entry.first << " with " << entry.second.size() << " rows\n";
    }
}

void Queries::execute_drop_table(const std::string& table_name)
{
    if (database.find(table_name) == database.end()) {
        std::cout << "Query execution failed.\n";
        return;
    }

    database.erase(table_name);
}

void Queries::execute_select(const std::vector<std::string>& columns, const std::string& name) {

    std::cout << "Executing SELECT on table: \"" << name << "\"\n";

    // Debug: Print database contents
    std::cout << "Database contents:\n";
    for (const auto& entry : database) {
        std::cout << "- Table: \"" << entry.first << "\" with " << entry.second.size() - 1 << " rows\n";
    }

    if (database.find(name) == database.end()) {
        std::cout << "Query execution failed select first.\n";
        return;
    }

    std::cout << "Executing SELECT on table: " << name << "\n";

    const Table& table = database[name];
    const std::vector<std::string>& header = table[0];


    // Debug: Print header and validate columns
    std::cout << "Table header contains: ";
    for (const auto& col : header) {
        std::cout << col << " ";
    }
    std::cout << "\n";

    // Find column indices
    std::vector<size_t> column_indices;
    for (const auto& col : columns) {
        auto it = std::find(header.begin(), header.end(), col);
        if (it != header.end()) {
            column_indices.push_back(std::distance(header.begin(), it));
        }
        else {
            std::cout << "Query execution failed.\n";
            return;
        }
    }
    helpers.print_table(table, column_indices);
};



void Queries::execute_where(const std::vector<std::string>& columns, const std::string& filter_column, const std::string& value, const std::string& table_name) {
    // Validate that the specified table exists
    if (database.find(table_name) == database.end()) {
        std::cout << "Query execution failed.\n";
        return;
    }

    const Table& table = database[table_name];
    const std::vector<std::string>& header = table[0]; // First row is the header

    // Find column indices
    std::vector<size_t> column_indices;
    for (const auto& col : columns) {
        auto it = std::find(header.begin(), header.end(), col);
        if (it != header.end()) {
            column_indices.push_back(std::distance(header.begin(), it));
        }
        else {
            std::cout << "Query execution failed.\n";
            return;
        }
    }

    // Find the filter column index
    auto filter_it = std::find(header.begin(), header.end(), filter_column);
    if (filter_it == header.end()) {
        std::cout << "Query execution failed.\n";
        return;
    }
    size_t filter_index = std::distance(header.begin(), filter_it);

    // Filter rows
    std::vector<size_t> row_indices;
    for (size_t i = 1; i < table.size(); ++i) {
        if (table[i][filter_index] == value) {
            row_indices.push_back(i);
        }
    }
    // Print the filtered rows
    helpers.print_table(table, column_indices, row_indices);
};


void Queries::execute_order_by(const std::string& table_name, const std::string& order_column) {
    if (database.find(table_name) == database.end()) {
        std::cout << "Query execution failed.\n";
        return;
    }

    Table& table = database[table_name];
    const std::vector<std::string>& header = table[0];

    // Find the index of the order column
    auto it = std::find(header.begin(), header.end(), order_column);
    if (it == header.end()) {
        std::cout << "Query execution failed.\n";
        return;
    }
    size_t order_index = std::distance(header.begin(), it);

    // Sort the rows based on the order column (excluding the header)
    std::sort(table.begin() + 1, table.end(), [order_index](const std::vector<std::string>& a, const std::vector<std::string>& b) {
        return a[order_index] < b[order_index]; // Ascending order
        });

    // Print the sorted table
    helpers.print_table(table, std::vector<size_t>(header.size()));
}




class Parser {
private:
    Queries queries;
    Helpers helpers;
public:
    void Parse(int argc, char* argv[]) {
        if (argc < 3) {
            std::cout << "Invalid query.\n" << std::endl;
            return;
        }

        std::string command = argv[0];
        std::cout << command << std::endl;

        if (command == "SELECT") {
            std::vector<std::string> columns = helpers.split_columns(argv[2]);
            std::string FROM = argv[2];
            std::string name = argv[3];

            if (argc > 6 && std::string(argv[4]) == "ORDER") {
                std::string BY = argv[5];
                std::string order_column = argv[7];
                queries.execute_order_by(name, order_column);
            }
            else if (argc > 8 && std::string(argv[4]) == "WHERE") {
                std::string column = argv[5];
                std::string value = argv[7];
                queries.execute_where(columns, column, value, name);

                if (argc > 9 && std::string(argv[8]) == "ORDER") {
                    std::string order_column = argv[10];
                    queries.execute_order_by(name, order_column);
                }
            }
            else {
                queries.execute_select(columns, name);
            }
        }
        else if (command == "BULK") {
            if (argc == 5 && std::string(argv[1]) == "INSERT" && std::string(argv[3]) == "FROM") {
                std::string table_name = argv[2];
                std::string file_name = argv[4];
                queries.execute_bulk_insert(table_name, file_name);
            }
            else {
                std::cout << "Invalid query.\n";
            }
        }
        else if (command == "DROP") {
            if (argc == 3 && std::string(argv[1]) == "TABLE") {
                std::string table_name = argv[2];
                queries.execute_drop_table(table_name);
            }
            else {
                std::cout << "Invalid query.\n";
            }
        }
        else {
            std::cout << "Invalid query.\n";
        }
    }
};

//
//int main(int argc, char* argv[])
//{
//    static Parser parser;
//    parser.Parse(argc, argv);
//}

int main() {
    Parser parser;  // Single persistent Parser instance

    // Simulate interactive command inputs for testing
    std::string command;
    while (true) {
        std::cout << "> ";
        std::getline(std::cin, command);

        if (command == "EXIT") {  // Exit condition
            break;
        }

        // Convert command to argc/argv format
        std::istringstream iss(command);
        std::vector<std::string> args{std::istream_iterator<std::string>{iss}, std::istream_iterator<std::string>{}};
        std::vector<char*> argv;
        for (auto& arg : args) {
            argv.push_back(const_cast<char*>(arg.data()));

        }

        parser.Parse(argv.size(), argv.data());
    }
}

