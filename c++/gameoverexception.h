#ifndef GAMEOVEREXCEPTION_H
#define GAMEOVEREXCEPTION_H

#include <exception>
#include "color.h"

class GameOverException : public std::exception
{
    Color winner;
public:
    GameOverException(const Color winner);
    Color getWinner() const;
};

#endif // GAMEOVEREXCEPTION_H
