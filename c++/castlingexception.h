#ifndef CASTLINGEXCEPTION_H
#define CASTLINGEXCEPTION_H

#include <exception>

class CastlingException : public std::exception
{
public:
    CastlingException();
};

#endif // CASTLINGEXCEPTION_H
