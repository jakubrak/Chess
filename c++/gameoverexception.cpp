#include "gameoverexception.h"


Color GameOverException::getWinner() const
{
    return winner;
}

GameOverException::GameOverException(const Color winner)
{
    this->winner = winner;
}
