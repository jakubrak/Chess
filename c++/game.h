#ifndef GAME_H
#define GAME_H

#include <list>
#include <stack>

#include "move.h"
#include "piece.h"

namespace Model
{
    class Game
    {
    protected:
        Piece *chessboard[8][8];
        std::stack<Move*> stackOfMoves;
        std::list<Piece*> listOfWhitePieces;
        std::list<Piece*> listOfBlackPieces;
        Color whoseTurn;
        Piece *whiteKing;
        Piece *blackKing;
    public:
        Game();
        /**
         * Does and remembers a chess move.
         * @param startSquare - square from which a piece is being moved
         * @param endSquare - square to which a piece is being moved
         * @throws IllegalMoveException
         */
        void doMove(int startX, int startY, int endX, int endY);

        /**
         * Gets player's whose turn it is color of pieces.
         * @return color of pieces
         */
        const Color getWhoseTurn();

        /**
         * Checks if there is and who is the winner.
         * @return the winner of the game
         */
        const Color getWinner();

        /**
         * Undoes a chess move.
         */
        void undoMove();

        /**
         * Checks if there is any move possible.
         * @return true if there is any move possible, otherwise false
         */
        bool isAnyMovePossible();

        /**
         * Creates a new game.
         */
        void createNewGame();

        /**
         * Adds piece to list of player's pieces.
         * @param piece - piece which is being added
         */
        void addPieceToList(Piece *piece);

        /**
         * Changes turn.
         */
        void changeTurn();

        /**
         * Generates a chess move.
         * @param startSquare - square from which a piece is being moved
         * @param endSquare - square to which a piece is being moved
         * @return {@link Move}
         * @throws IllegalMoveException
         */
        Move *generateMove(const Square &startSquare, const Square &endSquare);

        void tryMove(const Square &startSquare, const Square &endSquare);

        /**
         * Gets last done move.
         * @return move from top of {@link #stackOfMoves}
         */
        Move *getLastMove();

        /**
         * Gets piece if there is anyone on chessboard.
         * @param square - square on where sought piece is
         * @return piece which is on given square
         */
        Piece *getPiece(const Square &square);

        /**
         * Checks if king is in check.
         * @return true if king is in check, otherwise false
         */
        bool isKingInCheck();

        /**
         * Checks if square is free.
         * @param square - square which is checked
         * @return true if square isn't occupied by any piece, otherwise false
         */
        bool isSquareFree(const Square &square);

//    //    /**
//    //     * Prints chessboard.
//    //     */
//    //    void printChessboard() {
//    //        for (int j = 0; j < 8; ++j) {
//    //            for (int i = 0; i < 8; ++i)
//    //                System.out.print(chessboard[i][j] + "\t");
//    //            System.out.println();
//    //        }
//    //        System.out.println();
//    //    }

//    //    /**
//    //     * Prints lists of pieces owned by each of players.
//    //     */
//    //    void printlistsOfPieces() {
//    //        System.out.println("List of white pieces:");
//    //        for (final Piece piece : listOfWhitePieces)
//    //            System.out.println(piece);
//    //        System.out.println("List of black pieces:");
//    //        for (final Piece piece : listOfBlackPieces)
//    //            System.out.println(piece);
//    //    }

        /**
         * Removes given piece from list of pieces.
         * @param piece - piece to remove
         */
        void removePieceFromList(Piece *piece);

        /**
         * Set piece on square of chessboard.
         * @param square - square on which piece is set
         * @param piece - piece which is set
         */
        void setPieceOnChessboard(const Square &square, Piece *piece);

        void checkState();

        void cleanUp();
    };
}

#endif // GAME_H
