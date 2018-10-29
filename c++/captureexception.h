#ifndef CAPTUREEXCEPTION_H
#define CAPTUREEXCEPTION_H

#include <exception>

class CaptureException : public std::exception
{
public:
    CaptureException();
};

#endif // CAPTUREEXCEPTION_H
