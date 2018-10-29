#ifndef BISHOP_H
#define BISHOP_H

#include <list>

#include "piece.h"

namespace Model
{
    class Square;
    class Bishop : public Piece
    {
    public:
        Bishop(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(DiagonalMove *diagonalMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // BISHOP_H
