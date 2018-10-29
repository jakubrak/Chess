#ifndef MOVE_H
#define MOVE_H

#include "square.h"

namespace Model
{
    class Piece;
    class Game;

    class Move
    {
    protected:
        Game *game;
        Piece *piece;
        Piece *capturedPiece;
        Square startSquare;
        Square endSquare;
        bool wasMoved;
    public:
        Move();

        /**
         * Does a chess move.
         * @throws IllegalMoveException
         */
        virtual void doMove()=0;

        /**
         * @return piece which is captured
         */
        Piece *getCapturedPiece();

        /**
         * @return square to which piece is being moved
         */
        Square getEndSquare();

        /**
         * @return piece which is being moved
         */
        Piece *getPiece();

        /**
         * @return square from which piece is being moved
         */
        Square getStartSquare();

        /**
         * Undoes a chess move.
         */
        virtual void undoMove()=0;

        /**
         * @return true if piece which is being moved was moved any time before, otherwise false
         */
        bool getWasMoved();

        /**
         * @return true if path which piece is being moved along with is clear, otherwise false
         */
        virtual bool isPathClear()=0;
    };
}

#endif // MOVE_H
