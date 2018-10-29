#ifndef ROOK_H
#define ROOK_H

#include <list>

#include "piece.h"

namespace Model
{
    class VerticalMove;
    class HorizontalMove;
    class Square;

    class Rook : public Piece
    {
    public:
        Rook(Game *game, const Color color, const Square &currentSquare);
        bool isMoveValid(VerticalMove *verticalMove);
        bool isMoveValid(HorizontalMove *horizontalMove);
        void getAccessibleSquares(std::list<Square> &accessibleSquares);
        std::string toString() const;
    };
}

#endif // ROOK_H
