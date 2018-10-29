#include "diagonalmove.h"
#include "piece.h"
#include "game.h"
#include "pawn.h"
#include "queen.h"
#include "illegalmoveexception.h"
#include <math.h>

namespace Model
{
    DiagonalMove::DiagonalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare)
    {
        this->game = game;
        this->piece = piece;
        //capturedPiece = game->getPiece(endSquare);
        pawnPromotedTo = NULL;
        this->startSquare = startSquare;
        this->endSquare = endSquare;
        capturedPiece = NULL;
        wasMoved = piece->getWasMoved();
    }

    bool DiagonalMove::checkEnPassant()
    {
        Move *lastMove = game->getLastMove();
        if (lastMove != NULL && !lastMove->getWasMoved() && dynamic_cast<Pawn *>(lastMove->getPiece())
                && startSquare.getRank() == lastMove->getEndSquare().getRank()
                && endSquare.getFile() == lastMove->getEndSquare().getFile()
                && abs(startSquare.getFile() - lastMove->getEndSquare().getFile()) == 1)
        {
            capturedPiece = lastMove->getPiece();
            return true;
        }
        return false;
    }

    void DiagonalMove::doMove() /*throws IllegalMoveException*/
    {
        if (capturedPiece != NULL)
        {
            game->removePieceFromList(capturedPiece);
            game->setPieceOnChessboard(capturedPiece->getCurrentSquare(), NULL);
        }
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
        if (dynamic_cast<Pawn *>(piece))
            if (piece->getColor() == WHITE)
            {
                if (endSquare.getRank() == 7)
                {
                    game->removePieceFromList(piece);
                    pawnPromotedTo = new Queen(game, WHITE, endSquare);
                    game->addPieceToList(pawnPromotedTo);
                    game->setPieceOnChessboard(endSquare, pawnPromotedTo);
                }
            }
            else if (endSquare.getRank() == 0)
            {
                game->removePieceFromList(piece);
                pawnPromotedTo = new Queen(game, WHITE, endSquare);
                game->addPieceToList(pawnPromotedTo);
                game->setPieceOnChessboard(endSquare, pawnPromotedTo);
            }
        //Logger.print(this, "The move has been done.");
    }

    bool DiagonalMove::isForwardMove()
    {
        return startSquare.getRank() - endSquare.getRank() > 0 && piece->getColor() == WHITE
                || startSquare.getRank() - endSquare.getRank() < 0 && piece->getColor() == BLACK;
    }

    bool DiagonalMove::isOneStepMove()
    {
        return abs(startSquare.getRank() - endSquare.getRank()) == 1;
    }

    bool DiagonalMove::isPathClear()
    {
        Square square = endSquare;
        if (game->isSquareFree(square))
            square = nextSquare(square);
        else if (piece->getColor() != game->getPiece(square)->getColor())
        {
            capturedPiece = game->getPiece(square);
            square = nextSquare(square);
            //Logger.print(this, "Capture..");
        }
        else
        {
            //Logger.print(this, "Path isn't clear.");
            return false;
        }
        while (square != startSquare)
        {
            if (!game->isSquareFree(square))
            {
                //Logger.print(this, "Path isn't clear.");
                return false;
            }
            square = nextSquare(square);
        }
        //Logger.print(this, "The path is clear...");
        return true;
    }

    Square DiagonalMove::nextSquare(const Square &currentSquare)
    {
        Square nextSquare;
        if (endSquare.getFile() < startSquare.getFile())
            nextSquare.setFile(currentSquare.getFile() + 1);
        else
            nextSquare.setFile(currentSquare.getFile() - 1);
        if (endSquare.getRank() < startSquare.getRank())
            nextSquare.setRank(currentSquare.getRank() + 1);
        else
            nextSquare.setRank(currentSquare.getRank() - 1);
        return nextSquare;
    }

    void DiagonalMove::undoMove()
    {
        game->setPieceOnChessboard(startSquare, piece);
        if (capturedPiece != NULL && capturedPiece->getCurrentSquare() != endSquare)
        {
            game->setPieceOnChessboard(capturedPiece->getCurrentSquare(), capturedPiece);
            game->setPieceOnChessboard(endSquare, NULL);
            game->addPieceToList(capturedPiece);
        }
        else
        {
            game->setPieceOnChessboard(endSquare, capturedPiece);
            game->addPieceToList(capturedPiece);
        }
        if (pawnPromotedTo != NULL)
        {
            game->removePieceFromList(pawnPromotedTo);
            game->addPieceToList(piece);
        }
        piece->setCurrentSquare(startSquare);
        piece->setWasMoved(wasMoved);
        game->changeTurn();
        //Logger.print(this, "The move has been undone.");
    }
}
