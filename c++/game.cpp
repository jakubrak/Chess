#include "game.h"
#include "bishop.h"
#include "king.h"
#include "knight.h"
#include "pawn.h"
#include "queen.h"
#include "rook.h"
#include "color.h"

#include "move.h"
#include "illegalmoveexception.h"
#include "gameoverexception.h"

#include <math.h>
#include <iostream>
#include <iterator>

namespace Model
{
    Game::Game()
    {
        for(int i=0; i<8; ++i)
            for(int j=0; j<8; ++j)
                chessboard[i][j] = NULL;
        whiteKing = NULL;
        blackKing = NULL;
    }

    void Game::cleanUp()
    {
        while(!stackOfMoves.empty())
        {
            Move *move = stackOfMoves.top();
            stackOfMoves.pop();
            delete move;
        }

        listOfBlackPieces.clear();
        listOfBlackPieces.clear();

        for(int i=0; i<8; ++i)
        {
            for(int j=0; j<8; ++j)
            {
                if(chessboard[i][j] != NULL)
                {
                    delete chessboard[i][j];
                    chessboard[i][j] = NULL;
                }
            }
        }

        whiteKing = NULL;
        blackKing = NULL;
    }

    void Game::createNewGame()
    {
        cleanUp();

        whoseTurn = WHITE;

        /*Initial positions of pieces on chessboard[8][8]:
         * 		0		1		2		3		4		5		6		7
         * 0 WRook	WKnight	WBishop	WKing	WQueen	WBishop	WKnight	WRook
         * 1 WPawn	WPawn	WPawn	WPawn	WPawn	WPawn	WPawn	WPawn
         * 2 NULL	NULL	NULL	NULL	NULL	NULL	NULL	NULL
         * 3 NULL	NULL	NULL	NULL	NULL	NULL	NULL	NULL
         * 4 NULL	NULL	NULL	NULL	NULL	NULL	NULL	NULL
         * 5 NULL	NULL	NULL	NULL	NULL	NULL	NULL	NULL
         * 6 BPawn	BPawn	BPawn	BPawn	BPawn	BPawn	BPawn	BPawn
         */

        chessboard[0][7] = new Rook(this, WHITE, Square(0, 7));
        chessboard[1][7] = new Knight(this, WHITE, Square(1, 7));
        chessboard[2][7] = new Bishop(this, WHITE, Square(2, 7));
        chessboard[3][7] = new King(this, WHITE, Square(3, 7));
        chessboard[4][7] = new Queen(this, WHITE, Square(4, 7));
        chessboard[5][7] = new Bishop(this, WHITE, Square(5, 7));
        chessboard[6][7] = new Knight(this, WHITE, Square(6, 7));
        chessboard[7][7] = new Rook(this, WHITE, Square(7, 7));

        listOfWhitePieces.push_back(chessboard[4][7]);
        listOfWhitePieces.push_back(chessboard[0][7]);
        listOfWhitePieces.push_back(chessboard[7][7]);
        listOfWhitePieces.push_back(chessboard[2][7]);
        listOfWhitePieces.push_back(chessboard[5][7]);
        listOfWhitePieces.push_back(chessboard[1][7]);
        listOfWhitePieces.push_back(chessboard[6][7]);
        listOfWhitePieces.push_back(chessboard[3][7]);

        chessboard[0][0] = new Rook(this, BLACK, Square(0, 0));
        chessboard[1][0] = new Knight(this, BLACK, Square(1, 0));
        chessboard[2][0] = new Bishop(this, BLACK, Square(2, 0));
        chessboard[3][0] = new King(this, BLACK, Square(3, 0));
        chessboard[4][0] = new Queen(this, BLACK, Square(4, 0));
        chessboard[5][0] = new Bishop(this, BLACK, Square(5, 0));
        chessboard[6][0] = new Knight(this, BLACK, Square(6, 0));
        chessboard[7][0] = new Rook(this, BLACK, Square(7, 0));

        listOfBlackPieces.push_back(chessboard[4][0]);
        listOfBlackPieces.push_back(chessboard[0][0]);
        listOfBlackPieces.push_back(chessboard[7][0]);
        listOfBlackPieces.push_back(chessboard[2][0]);
        listOfBlackPieces.push_back(chessboard[5][0]);
        listOfBlackPieces.push_back(chessboard[1][0]);
        listOfBlackPieces.push_back(chessboard[6][0]);
        listOfBlackPieces.push_back(chessboard[3][0]);

        for (int i = 0; i < 8; ++i)
        {
            chessboard[i][6] = new Pawn(this, WHITE, Square(i, 6));
            chessboard[i][1] = new Pawn(this, BLACK, Square(i, 1));
            listOfWhitePieces.push_back(chessboard[i][6]);
            listOfBlackPieces.push_back(chessboard[i][1]);
        }

        whiteKing = chessboard[3][7];
        blackKing = chessboard[3][0];
    }

    void Game::doMove(int startX, int startY, int endX, int endY)
    {
        Move *move = generateMove(Square(startX, startY), Square(endX, endY));
        move->doMove();
        stackOfMoves.push(move);
        checkState();
    }

    void Game::undoMove()
    {
        if (!stackOfMoves.empty())
        {
            Move *move = stackOfMoves.top();
            stackOfMoves.pop();
            move->undoMove();
            delete move;
        }
    }

    Move *Game::generateMove(const Square &startSquare, const Square &endSquare)
    {
        if (startSquare.getFile() < 0 ||
            startSquare.getFile() > 7 ||
            startSquare.getRank() < 0 ||
            startSquare.getRank() > 7 ||
            endSquare.getFile() < 0 ||
            endSquare.getFile() > 7 ||
            endSquare.getRank() < 0 ||
            endSquare.getRank() > 7)
            throw IllegalMoveException();

        if (isSquareFree(startSquare))
            throw IllegalMoveException();

        if (startSquare.getFile() == endSquare.getFile() && startSquare.getRank() != endSquare.getRank())
        {
            std::cout << "vertical move" << std::endl;
            Piece * piece = chessboard[startSquare.getFile()][startSquare.getRank()];
            VerticalMove *verticalMove = new VerticalMove(this, piece, startSquare, endSquare);
            if(piece->isMoveValid(verticalMove))
                return verticalMove;
            delete verticalMove;
            throw IllegalMoveException();
        }

        if (startSquare.getRank() == endSquare.getRank() && startSquare.getFile() != endSquare.getFile())
        {
            Piece * piece = chessboard[startSquare.getFile()][startSquare.getRank()];
            HorizontalMove *horizontalMove = new HorizontalMove(this, piece, startSquare, endSquare);
            if(piece->isMoveValid(horizontalMove))
                return horizontalMove;
            delete horizontalMove;
            throw IllegalMoveException();
        }

        if (abs(startSquare.getFile() - endSquare.getFile()) == abs(startSquare.getRank() - endSquare.getRank()))
        {
            Piece * piece = chessboard[startSquare.getFile()][startSquare.getRank()];
            DiagonalMove *diagonalMove = new DiagonalMove(this, piece, startSquare, endSquare);
            if (piece->isMoveValid(diagonalMove))
                return diagonalMove;
            delete diagonalMove;
            throw IllegalMoveException();
        }

        if (abs(startSquare.getFile() - endSquare.getFile()) == 2
                && abs(startSquare.getRank() - endSquare.getRank()) == 1
                || abs(startSquare.getFile() - endSquare.getFile()) == 1
                && abs(startSquare.getRank() - endSquare.getRank()) == 2)
        {
            Piece *piece = chessboard[startSquare.getFile()][startSquare.getRank()];
            KnightsMove *knightsMove = new KnightsMove(this, piece, startSquare, endSquare);
            if (piece->isMoveValid(knightsMove))
                return knightsMove;
            throw IllegalMoveException();
        }
        throw IllegalMoveException();
    }

    bool Game::isSquareFree(const Square &square)
    {
        if (chessboard[square.getFile()][square.getRank()] == NULL)
            return true;
        else
            return false;
    }

    const Color Game::getWhoseTurn()
    {
        return whoseTurn;
    }

    void Game::addPieceToList(Piece *piece)
    {
        if (piece != NULL)
            if (piece->getColor() == WHITE)
                listOfWhitePieces.push_back(piece);
            else
                listOfBlackPieces.push_back(piece);
    }

    void Game::removePieceFromList(Piece *piece)
    {
        if (piece != NULL)
            if (piece->getColor() == WHITE)
                listOfWhitePieces.remove(piece);
            else
                listOfBlackPieces.remove(piece);
    }

    void Game::changeTurn()
    {
        if (whoseTurn == WHITE)
            whoseTurn = BLACK;
        else
            whoseTurn = WHITE;
    }

    Move *Game::getLastMove()
    {
        if (stackOfMoves.empty())
            return NULL;
        else
            return stackOfMoves.top();
    }

    Piece *Game::getPiece(const Square &square)
    {
        return chessboard[square.getFile()][square.getRank()];
    }

    void Game::setPieceOnChessboard(const Square &square, Piece *piece)
    {
        chessboard[square.getFile()][square.getRank()] = piece;
    }

    bool Game::isAnyMovePossible()
    {
        if (whoseTurn == WHITE)
        {
            for(std::list<Piece *>::iterator i=listOfWhitePieces.begin(); i!=listOfWhitePieces.end(); ++i)
            {
                std::list<Square> accessibleSquares;
                (*i)->getAccessibleSquares(accessibleSquares);
                for(std::list<Square>::iterator j=accessibleSquares.begin(); j!=accessibleSquares.end(); ++j)
                {
                    try
                    {
                        tryMove((*i)->getCurrentSquare(), *j);
                        return true;
                    }
                    catch (IllegalMoveException &e)
                    {
                        continue;
                    }
                }
            }
            return false;
        }
        else
        {
            for(std::list<Piece *>::iterator i=listOfBlackPieces.begin(); i!=listOfBlackPieces.end(); ++i)
            {
                std::list<Square> accessibleSquares;
                (*i)->getAccessibleSquares(accessibleSquares);
                for(std::list<Square>::iterator j=accessibleSquares.begin(); j!=accessibleSquares.end(); ++j)
                {
                    try
                    {
                        tryMove((*i)->getCurrentSquare(), *j);
                        return true;
                    }
                    catch (IllegalMoveException &e)
                    {
                        continue;
                    }
                }
            }
            return false;
        }
    }

    bool Game::isKingInCheck()
    {
        if (whoseTurn == BLACK)
        {
            //Logger.print(this, "Checking if " + whiteKing + " is in check...");
            for(std::list<Piece *>::iterator i = listOfBlackPieces.begin(); i!=listOfBlackPieces.end(); ++i)
            {
                try
                {
                    delete generateMove((*i)->getCurrentSquare(), whiteKing->getCurrentSquare());
                    //Logger.print(this, "King is in check.");
                    return true;
                }
                catch (IllegalMoveException &e)
                {
                    continue;
                }
            }
            //Logger.print(this, "King isn't in check.");
            return false;
        }
        else
        {
            //Logger.print(this, "Checking if " + blackKing + " is in check...");
            for(std::list<Piece *>::iterator i = listOfWhitePieces.begin(); i!=listOfWhitePieces.end(); ++i)
            {
                try
                {
                    delete generateMove((*i)->getCurrentSquare(), blackKing->getCurrentSquare());
                    return true;
                }
                catch (IllegalMoveException &e)
                {
                    continue;
                }
            }
            //Logger.print(this, "King isn't in check.");
            return false;
        }
    }

    void Game::checkState()
    {
        if (isAnyMovePossible())
            return;
        //Game is over
        changeTurn();
        if (isKingInCheck())
        {
            if (whoseTurn == WHITE)
                throw GameOverException(BLACK);
            else
                throw GameOverException(WHITE);
        }
        throw GameOverException(NONE);
    }

    void Game::tryMove(const Square &startSquare, const Square &endSquare)
    {
        Move *move = generateMove(startSquare, endSquare);
        move->doMove();
        move->undoMove();
        delete move;
    }
}
