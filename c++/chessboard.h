#ifndef CHESSBOARD_H
#define CHESSBOARD_H

#include <QGraphicsScene>
#include <map>
#include "game.h"

namespace Ui
{
    class Chessboard : public QGraphicsScene, public Model::Game
    {
        Q_OBJECT
        QGraphicsRectItem *squareItem[8][8];
        std::map<int, QGraphicsPixmapItem*> pieceItemMap;
        QPixmap *piecePixmap[12];
        QGraphicsItem *movingItem;
        int squareItemSize;
        int borderSize;
    public:
        Chessboard();
        void update();
        void mousePressEvent(QGraphicsSceneMouseEvent *event);
        void mouseMoveEvent(QGraphicsSceneMouseEvent *event);
        void mouseReleaseEvent(QGraphicsSceneMouseEvent *event);
    signals:
        void gameOver();
        void turnChanged();
    };
}

#endif // CHESSBOARD_H
