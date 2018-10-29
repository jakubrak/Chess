#ifndef DIAGONALMOVE_H
#define DIAGONALMOVE_H

#include "move.h"

namespace Model
{
    class DiagonalMove : public Move
    {
        Piece *pawnPromotedTo;
    public:
        DiagonalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare);

        /**
         * Checks if it might be en passant move.
         * @return true if it might be en passant move, otherwise false
         */
        bool checkEnPassant();

        void doMove();

        /**
         * Checks if it is a forward move.
         * @return true if it is a forward move, otherwise false
         */
        bool isForwardMove();

        /**
         * Checks if it is one step move.
         * @return true if it is one step move, otherwise false
         */
        bool isOneStepMove();

        bool isPathClear();

        /**
         * Gets next square on move's path (direction from {@link #endSquare} to {@link #startSquare})
         * @param currentSquare - current square
         * @return next square
         */
        Square nextSquare(const Square &currentSquare);

        void undoMove();
    };
}

#endif // DIAGONALMOVE_H
