#include "bishop.h"
#include "diagonalmove.h"
#include "game.h"

namespace Model
{
    Bishop::Bishop(Game *game, const Color color, const Square &currentSquare)
    {
        classId = color == WHITE ? 4 : 10;
        this->currentSquare = currentSquare;
        this->game = game;
        this->color = color;
        wasMoved = false;
    }

    bool Bishop::isMoveValid(DiagonalMove *diagonalMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = diagonalMove->isPathClear();

        if (isRightTurn && isPathClear)
            return true;
        return false;
    }

    void Bishop::getAccessibleSquares(std::list<Square> &accessibleSquares)
    {
        for (int i = 1; i <= 7; ++i)
        {
            if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() + i >= 0
                    && currentSquare.getRank() + i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + i, currentSquare.getRank() + i));
            if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() - i >= 0
                    && currentSquare.getRank() - i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + i, currentSquare.getRank() - i));
            if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() + i >= 0
                    && currentSquare.getRank() + i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() - i, currentSquare.getRank() + i));
            if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() - i >= 0
                    && currentSquare.getRank() - i <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() - i, currentSquare.getRank() - i));
        }
    }

    std::string Bishop::toString() const
    {
        if(color == WHITE)
            return "WBishop";
        else
            return "BBishop";
    }
}
