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
