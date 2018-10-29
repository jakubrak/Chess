#include "move.h"

namespace Model
{
    Move::Move()
    {
    }

    Piece *Move::getCapturedPiece()
    {
        return capturedPiece;
    }

    Square Move::getEndSquare()
    {
        return endSquare;
    }

    Piece *Move::getPiece()
    {
        return piece;
    }

    Square Move::getStartSquare()
    {
        return startSquare;
    }

    bool Move::getWasMoved()
    {
        return wasMoved;
    }
}

