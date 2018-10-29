#include "verticalmove.h"
#include "color.h"
#include "piece.h"
#include "game.h"
#include "pawn.h"
#include "queen.h"
#include "illegalmoveexception.h"
#include <math.h>

namespace Model
{
    VerticalMove::VerticalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare)
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

    void VerticalMove::doMove()/* throws IllegalMoveException */
    {
        if (capturedPiece != NULL)
            game->removePieceFromList(capturedPiece);
        game->setPieceOnChessboard(endSquare, piece);
        game->setPieceOnChessboard(startSquare, NULL);
        piece->setCurrentSquare(endSquare);
        piece->setWasMoved(true);
        game->changeTurn();
        if (game->isKingInCheck() == true) {
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

    bool VerticalMove::isForwardMove()
    {
        return startSquare.getRank() - endSquare.getRank() > 0 && piece->getColor() == WHITE
                || startSquare.getRank() - endSquare.getRank() < 0 && piece->getColor() == BLACK;
    }

    bool VerticalMove::isOneStepMove()
    {
        return abs(startSquare.getRank() - endSquare.getRank()) == 1;
    }

    bool VerticalMove::isTwoStepsMove()
    {
        return abs(endSquare.getRank() - startSquare.getRank()) == 2;
    }

    bool VerticalMove::isPathClear()
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
        else {
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
        //Logger.print(this, "The path is clear...");*/
        return true;
    }

    Square VerticalMove::nextSquare(const Square &currentSquare)
    {
        Square nextSquare;
        nextSquare.setFile(currentSquare.getFile());
        if (endSquare.getRank() < startSquare.getRank())
            nextSquare.setRank(currentSquare.getRank() + 1);
        else
            nextSquare.setRank(currentSquare.getRank() - 1);
        return nextSquare;
    }

    void VerticalMove::undoMove()
    {
        game->setPieceOnChessboard(startSquare, piece);
        game->setPieceOnChessboard(endSquare, capturedPiece);
        if (capturedPiece != NULL)
            game->addPieceToList(capturedPiece);
        if (pawnPromotedTo != NULL) {
            game->removePieceFromList(pawnPromotedTo);
            game->addPieceToList(piece);
        }
        piece->setCurrentSquare(startSquare);
        piece->setWasMoved(wasMoved);
        game->changeTurn();
        //Logger.print(this, "The move has been undone.");
    }
}
