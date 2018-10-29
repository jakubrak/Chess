#ifndef KNIGHTSMOVE_H
#define KNIGHTSMOVE_H

#include "move.h"

namespace Model
{
    class KnightsMove : public Move
    {
    public:
        KnightsMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare);

        void doMove();

        bool isPathClear();

        void undoMove();
    };
}

#endif // KNIGHTSMOVE_H
