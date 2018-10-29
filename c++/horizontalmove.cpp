#include "horizontalmove.h"
#include "piece.h"
#include "rook.h"
#include "game.h"
#include "illegalmoveexception.h"
#include <math.h>

namespace Model
{
    HorizontalMove::HorizontalMove(Game *game, Piece *piece, const Square &startSquare, const Square &endSquare)
    {
        this->game = game;
        this->piece = piece;
        //capturedPiece = game->getPiece(endSquare);
        this->startSquare = startSquare;
        this->endSquare = endSquare;
        capturedPiece = NULL;
        wasMoved = piece->getWasMoved();
        castling = NULL;
    }

    bool HorizontalMove::checkCastling()
    {
        if (startSquare.getFile() - endSquare.getFile() == -2 && piece->getColor() == BLACK)
        {
            Piece *rook = game->getPiece(Square(7, 0));
            HorizontalMove *horizontalMove = new HorizontalMove(game, rook, Square(7, 0), Square(4, 0));
            if (rook != NULL && !rook->getWasMoved() && rook->isMoveValid(horizontalMove))
            {
                castling = horizontalMove;
                return true;
            }
            return false;
        }
        else if (startSquare.getFile() - endSquare.getFile() == 2 && piece->getColor() == BLACK)
        {
            Piece *rook = game->getPiece(Square(0, 0));
            HorizontalMove *horizontalMove = new HorizontalMove(game, rook, Square(0, 0), Square(2, 0));
            if (rook != NULL && !rook->getWasMoved() && rook->isMoveValid(horizontalMove))
            {
                castling = horizontalMove;
                return true;
            }
            return false;
        }
        else if (startSquare.getFile() - endSquare.getFile() == -2 && piece->getColor() == WHITE)
        {
            Piece *rook = game->getPiece(Square(7, 7));
            HorizontalMove *horizontalMove = new HorizontalMove(game, rook, Square(7, 7), Square(4, 7));
            if (rook != NULL && !rook->getWasMoved() && rook->isMoveValid(horizontalMove))
            {
                castling = horizontalMove;
                return true;
            }
            return false;
        }
        else if (startSquare.getFile() - endSquare.getFile() == 2 && piece->getColor() == WHITE)
        {
            Piece *rook = game->getPiece(Square(0, 7));
            HorizontalMove *horizontalMove = new HorizontalMove(game, rook, Square(0, 7), Square(2, 7));
            if (rook != NULL && !rook->getWasMoved() && rook->isMoveValid(horizontalMove))
            {
                castling = horizontalMove;
                return true;
            }
            return false;
        }
        else
            return false;
    }

    void HorizontalMove::doMove() /*throws IllegalMoveException*/
    {
        if (castling == NULL)
        {
            if (capturedPiece != NULL)
                game->removePieceFromList(capturedPiece);
            game->setPieceOnChessboard(endSquare, piece);
            game->setPieceOnChessboard(startSquare, NULL);
            piece->setCurrentSquare(endSquare);
            piece->setWasMoved(true);
        }
        else
        {
            game->setPieceOnChessboard(castling->endSquare, piece);
            game->setPieceOnChessboard(startSquare, NULL);
            piece->setCurrentSquare(castling->endSquare);
            piece->setWasMoved(true);
            if (game->isKingInCheck())
            {
                castling = NULL;
                game->undoMove();
                throw IllegalMoveException();
            }
            game->setPieceOnChessboard(endSquare, piece);
            game->setPieceOnChessboard(startSquare, NULL);
            piece->setCurrentSquare(endSquare);
            game->setPieceOnChessboard(castling->endSquare, castling->piece);
            game->setPieceOnChessboard(castling->startSquare, NULL);
            castling->piece->setCurrentSquare(castling->endSquare);
            castling->piece->setWasMoved(true);
        }
        game->changeTurn();
        if (game->isKingInCheck())
        {
            undoMove();
            throw IllegalMoveException();
        }
        //Logger.print(this, "The move has been done.");
    }

    bool HorizontalMove::isOneStepMove()
    {
        return abs(startSquare.getFile() - endSquare.getFile()) == 1;
    }

    bool HorizontalMove::isPathClear()
    {
        Square square = endSquare;
        if (game->isSquareFree(square) == true)
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

    Square HorizontalMove::nextSquare(const Square &currentSquare)
    {
        Square nextSquare;
        nextSquare.setRank(currentSquare.getRank());
        if (endSquare.getFile() < startSquare.getFile())
            nextSquare.setFile(currentSquare.getFile() + 1);
        else
            nextSquare.setFile(currentSquare.getFile() - 1);
        return nextSquare;
    }

    void HorizontalMove::undoMove()
    {
        game->setPieceOnChessboard(startSquare, piece);
        game->setPieceOnChessboard(endSquare, capturedPiece);
        if (capturedPiece != NULL)
            game->addPieceToList(capturedPiece);
        piece->setCurrentSquare(startSquare);
        piece->setWasMoved(wasMoved);
        if (castling != NULL)
        {
            game->setPieceOnChessboard(castling->startSquare, castling->piece);
            game->setPieceOnChessboard(castling->endSquare, NULL);
            castling->piece->setCurrentSquare(castling->startSquare);
            castling->piece->setWasMoved(wasMoved);
        }
        game->changeTurn();
        //Logger.print(this, "The move has been undone.");
    }
}

