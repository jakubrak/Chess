#include "pawn.h"
#include "game.h"

namespace Model
{
    Pawn::Pawn(Game *game, const Color color, const Square &currentSquare)
    {
        classId = color == WHITE ? 6 : 12;
        this->currentSquare = currentSquare;
        this->game = game;
        this->color = color;
        wasMoved = false;
    }

    bool Pawn::isMoveValid(DiagonalMove *diagonalMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = diagonalMove->isPathClear();
        bool isOneStepMove = diagonalMove->isOneStepMove();
        bool isForwardMove = diagonalMove->isForwardMove();
        bool isCapture = diagonalMove->getCapturedPiece() != NULL;

        if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && isCapture)
            return true;
        if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && diagonalMove->checkEnPassant())
            return true;
        return false;
    }

    bool Pawn::isMoveValid(VerticalMove *verticalMove)
    {
        bool isRightTurn = game->getWhoseTurn() == color;
        bool isPathClear = verticalMove->isPathClear();
        bool isOneStepMove = verticalMove->isOneStepMove();
        bool isTwoStepsMove = verticalMove->isTwoStepsMove();
        bool isForwardMove = verticalMove->isForwardMove();
        bool isCapture = verticalMove->getCapturedPiece() != NULL;

        if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && !isCapture)
            return true;
        if (isRightTurn && isPathClear && isTwoStepsMove && !wasMoved && isForwardMove && !isCapture)
            return true;
        return false;
    }

    void Pawn::getAccessibleSquares(std::list<Square> &accessibleSquares)
    {
        if (color == WHITE) {
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 1 >= 0
                    && currentSquare.getRank() + 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() + 1));
            if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 1 >= 0
                    && currentSquare.getRank() + 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() + 1));
            if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 1 >= 0
                    && currentSquare.getRank() + 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() + 1));
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 2 >= 0
                    && currentSquare.getRank() + 2 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() + 2));
        }
        else {
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 1 >= 0
                    && currentSquare.getRank() - 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() - 1));
            if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 1 >= 0
                    && currentSquare.getRank() - 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 1, currentSquare.getRank() - 1));
            if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 1 >= 0
                    && currentSquare.getRank() - 1 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() - 1, currentSquare.getRank() - 1));
            if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 2 >= 0
                    && currentSquare.getRank() - 2 <= 7)
                accessibleSquares.push_back(Square(currentSquare.getFile() + 0, currentSquare.getRank() - 2));
        }
    }

    std::string Pawn::toString() const
    {
        if(color == WHITE)
            return "WPawn";
        else
            return "BPawn";
    }
}
