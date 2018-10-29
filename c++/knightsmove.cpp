#include "knightsmove.h"
#include "piece.h"
#include "game.h"
#include "illegalmoveexception.h"

namespace Model
{
    KnightsMove::KnightsMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare)
    {
        this->game = game;
        this->piece = piece;
        //capturedPiece = game->getPiece(endSquare);
        this->startSquare = startSquare;
        this->endSquare = endSquare;
        capturedPiece = NULL;
        wasMoved = piece->getWasMoved();
    }

    void KnightsMove::doMove() /*throws IllegalMoveException */
    {
        if (capturedPiece != NULL)
            game->removePieceFromList(capturedPiece);
        game->setPieceOnChessboard(endSquare, piece);
        game->setPieceOnChessboard(startSquare, NULL);
        piece->setCurrentSquare(endSquare);
        piece->setWasMoved(true);
        game->changeTurn();
        if (game->isKingInCheck())
        {
            undoMove();
            throw IllegalMoveException();
        }
        //Logger.print(this, "The move has been done.");
    }

    bool KnightsMove::isPathClear()
    {
        Square square = endSquare;
        if (game->isSquareFree(square))
            return true;
        if (piece->getColor() != game->getPiece(square)->getColor())
        {
            capturedPiece = game->getPiece(square);
            return true;
        }
        //Logger.print(this, "Path isn't clear.");*/
        return false;
    }

    void KnightsMove::undoMove()
    {
        game->setPieceOnChessboard(startSquare, piece);
        game->setPieceOnChessboard(endSquare, capturedPiece);
        if (capturedPiece != NULL)
            game->addPieceToList(capturedPiece);
        piece->setCurrentSquare(startSquare);
        piece->setWasMoved(wasMoved);
        game->changeTurn();
        //Logger.print(this, "The move has been undone.");
    }
}
