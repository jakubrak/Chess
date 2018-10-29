#ifndef KNIGHT_H
#define KNIGHT_H

#include <list>

#include "piece.h"

namespace Model
{
    class KnightsMove;
    class Square;

    class Knight : public Piece
    {
    public:
        Knight(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(KnightsMove *knightsMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // KNIGHT_H
