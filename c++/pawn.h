#ifndef PAWN_H
#define PAWN_H

#include <list>

#include "piece.h"

namespace Model
{
    class VerticalMove;
    class DiagonalMove;
    class Square;

    class Pawn : public Piece
    {
    public:
        Pawn(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(VerticalMove *verticalMove);
        bool isMoveValid(DiagonalMove *diagonalMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // PAWN_H
