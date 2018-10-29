#ifndef SQUARE_H
#define SQUARE_H

#include <string>

namespace Model
{
    class Square
    {
        int x, y;
    public:
        Square();

        Square(const int x, const int y)
        {
            this->x = x;
            this->y = y;
        }

        bool operator==(Square const &square) const
        {
            return x == square.x && y == square.y;
        }

        bool operator!=(Square const &square) const
        {
            return x != square.x || y != square.y;
        }

        const int getFile() const
        {
            return x;
        }

        const int getRank() const
        {
            return y;
        }

        void setFile(const int file)
        {
            this->x = file;
        }

        void setRank(const int rank)
        {
            this->y = rank;
        }

        std::string toString()
        {
            return "["/* + x + ", " + y + "]"*/;
        }
    };
}

#endif // SQUARE_H
