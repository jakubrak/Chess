#include "knight.h"
#include "game.h"

namespace Model
{
    Knight::Knight(Game *game, const Color color, const Square &currentSquare)
    {
        classId = color == WHITE ? 5 : 11;
        this->currentSquare = currentSquare;
        this->game = game;
        this->color = color;
        wasMoved = false;
    }

    bool Knight::isMoveValid(KnightsMove *knightsMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = knightsMove->isPathClear();
        if (isRightTurn && isPathClear)
            return true;
        return false;
    }

    void Knight::getAccessibleSquares(std::list<Square> &accessibleSquares)
    {
        if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 2 >= 0
                && currentSquare.getRank() + 2 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() + 2));
        if (currentSquare.getFile() + 2 >= 0 && currentSquare.getFile() + 2 <= 7 && currentSquare.getRank() + 1 >= 0
                && currentSquare.getRank() + 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 2, currentSquare.getRank() + 1));
        if (currentSquare.getFile() + 2 >= 0 && currentSquare.getFile() + 2 <= 7 && currentSquare.getRank() - 1 >= 0
                && currentSquare.getRank() - 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 2, currentSquare.getRank() - 1));
        if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 2 >= 0
                && currentSquare.getRank() - 2 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() - 2));
        if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 2 >= 0
                && currentSquare.getRank() - 2 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() - 2));
        if (currentSquare.getFile() - 2 >= 0 && currentSquare.getFile() - 2 <= 7 && currentSquare.getRank() - 1 >= 0
                && currentSquare.getRank() - 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 2, currentSquare.getRank() - 1));
        if (currentSquare.getFile() - 2 >= 0 && currentSquare.getFile() - 2 <= 7 && currentSquare.getRank() + 1 >= 0
                && currentSquare.getRank() + 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 2, currentSquare.getRank() + 1));
        if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 2 >= 0
                && currentSquare.getRank() + 2 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() + 2));
    }

    std::string Knight::toString() const
    {
        if(color == WHITE)
            return "WKnight";
        else
            return "BKnight";
    }
}

