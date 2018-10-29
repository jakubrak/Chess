#include "rook.h"
#include "game.h"

namespace  Model
{
    Rook::Rook(Game *game, const Color color, const Square &currentSquare)
    {
        classId = color == WHITE ? 3 : 9;
        this->currentSquare = currentSquare;
        this->game = game;
        this->color = color;
        wasMoved = false;
    }

    bool Rook::isMoveValid(HorizontalMove *horizontalMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = horizontalMove->isPathClear();
        if (isRightTurn && isPathClear)
            return true;
        return false;
    }

    bool Rook::isMoveValid(VerticalMove *verticalMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = verticalMove->isPathClear();
        if (isRightTurn && isPathClear)
            return true;
        return false;
    }

    void Rook::getAccessibleSquares(std::list<Square> &accessibleSquares)
    {
        for (int i=1; i<=7; ++i)
        {
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + i >= 0
                    && currentSquare.getRank() + i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() + i));
            if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() + 0 >= 0
                    && currentSquare.getRank() + 0 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + i, currentSquare.getRank() + 0));
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - i >= 0
                    && currentSquare.getRank() - i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() - i));
            if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() + 0 >= 0
                    && currentSquare.getRank() + 0 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() - i, currentSquare.getRank() + 0));
        }
    }

    std::string Rook::toString() const
    {
        if(color == WHITE)
            return "WRook";
        else
            return "BRook";
    }
}
