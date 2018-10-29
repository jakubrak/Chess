#ifndef ILLEGALMOVEEXCEPTION_H
#define ILLEGALMOVEEXCEPTION_H

#include <exception>

class IllegalMoveException : public std::exception
{
public:
    IllegalMoveException();
};

#endif // ILLEGALMOVEEXCEPTION_H
