#include "LineHarvester.h"


int LineHarvester::parseLineToInts(std::string inputLine)
{
    int firstNumber = -1;
    int secondNumber = -1;
    for (const char value : inputLine)
    {
        if (std::isdigit(value))
        {
            if (firstNumber == -1)
            {
                firstNumber = value - '0';
                secondNumber = firstNumber;
            }
            else
            {
                secondNumber = value - '0';
            }
        }
    }
    if (firstNumber == -1)
    {
        return 0;
    }
    return firstNumber * 10 + secondNumber;
}


int LineHarvester::advancedParseLineToInts(std::string inputLine)
{
    int firstNumber = -1;
    int secondNumber = -1;
    std::string currentRead;
    for (const char value : inputLine)
    {
        currentRead += value;
        if (std::isdigit(value))
        {
            if (firstNumber == -1)
            {
                firstNumber = value - '0';
                secondNumber = firstNumber;
            }
            else
            {
                secondNumber = value - '0';
            }
        }
        else
        {
            const int num = tryParseLines(currentRead);
            if (num != -1)
            {
                if (firstNumber == -1)
                {
                    firstNumber = num;
                    secondNumber = firstNumber;
                }
                else
                {
                    secondNumber = num;
                }
            }
        }
    }
    if (firstNumber == -1)
    {
        return 0;
    }
    return firstNumber * 10 + secondNumber;
}

int LineHarvester::tryParseLines(std::string str)
{
    if (matchLines(str, "one"))
    {
        return 1;
    }
    if (matchLines(str, "two"))
    {
        return 2;
    }
    if (matchLines(str, "three"))
    {
        return 3;
    }
    if (matchLines(str, "four"))
    {
        return 4;
    }
    if (matchLines(str, "five"))
    {
        return 5;
    }
    if (matchLines(str, "six"))
    {
        return 6;
    }
    if (matchLines(str, "seven"))
    {
        return 7;
    }
    if (matchLines(str, "eight"))
    {
        return 8;
    }
    if (matchLines(str, "nine"))
    {
        return 9;
    }

    return -1;
}

bool LineHarvester::matchLines(std::string str, std::string search)
{
    if (str.length() < search.length())
    {
        return false;
    }
    const int start = str.length() - 1;
    const int stay_above = str.length() - search.length();
    for (int i = start; i >= stay_above; --i)
    {
        char characterOfSearchString = search[i - (str.length() - search.length())];
        if (str[i] != characterOfSearchString)
        {
            return false;
        }
    }
    return true;
}
