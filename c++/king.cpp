#include "king.h"
#include "game.h"

namespace Model
{
    King::King(Game *game, const Color color, const Square &currentSquare)
    {
        classId = color == WHITE ? 1 : 7;
        this->currentSquare = currentSquare;
        this->game = game;
        this->color = color;
        wasMoved = false;
    }

    bool King::isMoveValid(DiagonalMove *diagonalMove)
    {
        bool isOneStepMove = diagonalMove->isOneStepMove();
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = diagonalMove->isPathClear();
        if (isOneStepMove && isRightTurn && isPathClear)
            return true;
        return false;
    }

    bool King::isMoveValid(HorizontalMove *horizontalMove)
    {
        bool isOneStepMove = horizontalMove->isOneStepMove();
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = horizontalMove->isPathClear();
        if (isRightTurn && isPathClear)
        {
            if(isOneStepMove)
                return true;
            bool isCastling = horizontalMove->checkCastling();
            if(!wasMoved && isCastling)
                return true;
        }
        return false;
    }

    bool King::isMoveValid(VerticalMove *verticalMove)
    {
        bool isOneStepMove = verticalMove->isOneStepMove();
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = verticalMove->isPathClear();
        if (isOneStepMove && isRightTurn && isPathClear)
            return true;
        return false;
    }

    void King::getAccessibleSquares(std::list<Square> &accessibleSquares)
    {
        if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 1 >= 0
                && currentSquare.getRank() + 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() + 1));
        if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 1 >= 0
                && currentSquare.getRank() + 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() + 1));
        if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 0 >= 0
                && currentSquare.getRank() + 0 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() + 0));
        if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 1 >= 0
                && currentSquare.getRank() - 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() - 1));
        if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 1 >= 0
                && currentSquare.getRank() - 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() - 1));
        if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 1 >= 0
                && currentSquare.getRank() - 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() - 1));
        if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 0 >= 0
                && currentSquare.getRank() + 0 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() + 0));
        if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 1 >= 0
                && currentSquare.getRank() + 1 <= 7)
            accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() + 1));
    }

    std::string King::toString() const
    {
        if(color == WHITE)
            return "WKing";
        else
            return "BKing";
    }
}
