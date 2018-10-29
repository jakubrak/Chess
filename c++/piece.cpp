#include "piece.h"

namespace Model
{
    int Piece::id;
    int Piece::getObjectId() const
    {
        return objectId;
    }

    Piece::Piece()
    {
        objectId = id++;
    }

    bool Piece::isMoveValid(DiagonalMove *diagonalMove)
    {
    return false;
    }

    bool Piece::isMoveValid(HorizontalMove *horizontalMove)
    {
        return false;
    }

    bool Piece::isMoveValid(VerticalMove *verticalMove)
    {
        return false;
    }

    bool Piece::isMoveValid(KnightsMove *knightsMove)
    {
        return false;
    }

    const Color Piece::getColor()
    {
        return color;
    }

    const Square Piece::getCurrentSquare()
    {
        return currentSquare;
    }

    const int Piece::getClassId()
    {
        return classId;
    }

    void Piece::setCurrentSquare(const Square &currentSquare)
    {
        this->currentSquare = currentSquare;
    }

    void Piece::setWasMoved(const bool wasMoved)
    {
        this->wasMoved = wasMoved;
    }

    const bool Piece::getWasMoved()
    {
        return wasMoved;
    }
}
