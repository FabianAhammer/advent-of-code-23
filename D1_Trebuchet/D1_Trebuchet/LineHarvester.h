#pragma once
#include <string>

class LineHarvester
{
public:
    static int parseLineToInts(std::string inputLine);
    static int advancedParseLineToInts(std::string inputLine);

private:
    static int tryParseLines(std::string);
    static bool matchLines(std::string str, std::string search);
};
