#ifndef PIECE_H
#define PIECE_H

#include <list>
#include <ostream>

#include "color.h"
#include "square.h"

#include "verticalmove.h"
#include "horizontalmove.h"
#include "diagonalmove.h"
#include "knightsmove.h"

namespace Model
{
    class VerticalMove;
    class HorizontalMove;
    class DiagonalMove;
    class KnightsMove;
    class Game;

    class Piece
    {
        static int id;
        int objectId;
    protected:
        int classId;
        Game *game;
        Color color;
        bool wasMoved;
        Square currentSquare;
    public:
        Piece();
        /**
         * Checks if the move is valid for the piece.
         * @param move
         * @return true if move is valid, otherwise false
         */
        virtual bool isMoveValid(DiagonalMove *diagonalMove);
        virtual bool isMoveValid(HorizontalMove *horizontalMove);
        virtual bool isMoveValid(VerticalMove *verticalMove);
        virtual bool isMoveValid(KnightsMove *knightsMove);

        /**
         * Gets all squares which piece can access from current square (without checking if move is valid).
         * @return list of squares
         */
        virtual void getAccessibleSquares(std::list<Square> &accessibleSquares)=0;

        /**
         * Gets color of piece.
         * @return color of piece
         */
        const Color getColor();

        /**
         * Gets square on which piece currently stands.
         * @return current square
         */
        const Square getCurrentSquare();

        /**
         * Gets piece identification number.
         * @return piece id
         */
        const int getClassId();

        /**
         * Sets square on which piece currently is.
         * @param square - square on which piece is set
         */
        void setCurrentSquare(const Square &square);

        /**
         * Remembers if piece was moved or not.
         * @param wasMoved - true if piece was moved, otherwise false
         */
        void setWasMoved(const bool wasMoved);

        /**
         * Checks if piece was moved any time before.
         * @return true if piece was moved, otherwise false
         */
        const bool getWasMoved();


        friend std::ostream &operator <<(std::ostream &out, const Piece &piece)
        {
            out << piece.toString();
            return out;
        }

        virtual std::string toString() const =0;

        int getObjectId() const;
    };
}

#endif // PIECE_H
