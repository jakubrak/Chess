#ifndef QUEEN_H
#define QUEEN_H

#include <list>

#include "piece.h"

namespace Model
{
    class VerticalMove;
    class HorizontalMove;
    class DiagonalMove;
    class Square;

    class Queen : public Piece
    {
    public:
        Queen(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(VerticalMove *verticalMove);
        bool isMoveValid(HorizontalMove *horizontalMove);
        bool isMoveValid(DiagonalMove *diagonalMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // QUEEN_H
