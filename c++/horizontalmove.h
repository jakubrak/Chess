#ifndef HORIZONTALMOVE_H
#define HORIZONTALMOVE_H

#include "move.h"

namespace Model
{
    class HorizontalMove : public Move
    {
        HorizontalMove *castling;
    public:
        HorizontalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare);

        /**
         * Checks if if it might be castling move.
         * @return true if it might be castling move, otherwise false
         */
        bool checkCastling();

        void doMove();

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

#endif // HORIZONTALMOVE_H
