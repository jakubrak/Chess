#ifndef KING_H
#define KING_H

#include <list>

#include "piece.h"

namespace Model
{
    class VerticalMove;
    class HorizontalMove;
    class DiagonalMove;
    class Square;

    class King : public Piece
    {
    public:
        King(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(VerticalMove *verticalMove);
        bool isMoveValid(HorizontalMove *horizontalMove);
        bool isMoveValid(DiagonalMove *diagonalMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // KING_H
