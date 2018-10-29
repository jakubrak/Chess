#ifndef VERTICALMOVE_H
#define VERTICALMOVE_H

#include "move.h"

namespace Model
{
    class VerticalMove : public Move
    {
        Piece *pawnPromotedTo;
    public:
        VerticalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare);

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
         * Checks if it is two steps move.
         * @return true if it is two steps move, otherwise false
         */
        bool isTwoStepsMove();

        /**
         * Gets next square on move's path (direction from {@link #endSquare} to {@link #startSquare})
         * @param currentSquare - current square
         * @return next square
         */
        Square nextSquare(const Square &currentSquare);

        void undoMove();
    };
}

#endif // VERTICALMOVE_H
