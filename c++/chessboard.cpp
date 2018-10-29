#include "chessboard.h"
#include <QGraphicsItem>
#include <iostream>
#include <QGraphicsSceneMouseEvent>
#include <QGraphicsView>
#include "illegalmoveexception.h"
#include "gameoverexception.h"

namespace Ui
{
    Chessboard::Chessboard()
    {
        QBrush whiteBrush(QColor(255, 206, 158));
        QBrush blackBrush(QColor(209, 139, 71));
        QBrush noBrush(Qt::NoBrush);
        QPen noPen(Qt::NoPen);
        QPen blackPen(Qt::black);
        QFont borderFont("Times", 30);

        QString borderLetter[] = {"a", "b", "c", "d", "e", "f", "g", "h"};
        QString borderNumber[] = {"1", "2", "3", "4", "5", "6", "7", "8"};

        squareItemSize = 200;
        borderSize = 40;

        int x = 0;
        int y = 0;
        int width = borderSize;
        int height = borderSize;

        this->addRect(x, y, width, height, blackPen, noBrush);
        for(int i=0; i<8; ++i)
        {
            x += width;
            width = squareItemSize;
            QGraphicsRectItem *borderRect = this->addRect(0, 0, width, height, blackPen, noBrush);
            borderRect->setPos(x, y);
            QGraphicsTextItem *borderText = this->addText(borderLetter[i], borderFont);
            borderText->setParentItem(borderRect);
            borderText->setPos((borderRect->boundingRect().width()-borderText->boundingRect().width())/2,
                               (borderRect->boundingRect().height()-borderText->boundingRect().height())/2);
        }
        x += width;
        width = borderSize;
        this->addRect(x, y, width, height, blackPen, noBrush);

        for(int i=0; i<8; ++i)
        {
            x = 0;
            y += height;
            height = squareItemSize;
            QGraphicsRectItem *borderRect = this->addRect(0, 0, width, height, blackPen, noBrush);
            borderRect->setPos(x, y);
            QGraphicsTextItem *borderText = this->addText(borderNumber[7-i], borderFont);
            borderText->setParentItem(borderRect);
            borderText->setPos((borderRect->boundingRect().width()-borderText->boundingRect().width())/2,
                               (borderRect->boundingRect().height()-borderText->boundingRect().height())/2);
            for(int j=0; j<8; ++j)
            {
                x += width;
                width = squareItemSize;
                if((i + j) % 2 == 0)
                    squareItem[j][i] = this->addRect(0, 0, width, height, noPen, whiteBrush);
                else
                    squareItem[j][i] = this->addRect(0, 0, width, height, noPen, blackBrush);
                squareItem[j][i]->setPos(x, y);
            }
            x += width;
            width = borderSize;
            borderRect = this->addRect(0, 0, width, height, blackPen, noBrush);
            borderRect->setPos(x, y);
            borderText = this->addText(borderNumber[7-i], borderFont);
            borderText->setParentItem(borderRect);
            borderText->setPos((borderRect->boundingRect().width()-borderText->boundingRect().width())/2,
                               (borderRect->boundingRect().height()-borderText->boundingRect().height())/2);
        }

        x = 0;
        y += height;
        height = borderSize;
        this->addRect(x, y, width, height, blackPen, noBrush);
        for(int i=0; i<8; ++i)
        {
            x += width;
            width = squareItemSize;
            QGraphicsRectItem *borderRect = this->addRect(0, 0, width, height, blackPen, noBrush);
            borderRect->setPos(x, y);
            QGraphicsTextItem *borderText = this->addText(borderLetter[i], borderFont);
            borderText->setParentItem(borderRect);
            borderText->setPos((borderRect->boundingRect().width()-borderText->boundingRect().width())/2,
                               (borderRect->boundingRect().height()-borderText->boundingRect().height())/2);
        }
        x += width;
        width = borderSize;
        this->addRect(x, y, width, height, blackPen, noBrush);

        piecePixmap[0] = new QPixmap("://res/200px-Chess_klt45.svg.png");
        piecePixmap[1] = new QPixmap("://res/200px-Chess_qlt45.svg.png");
        piecePixmap[2] = new QPixmap("://res/200px-Chess_rlt45.svg.png");
        piecePixmap[3] = new QPixmap("://res/200px-Chess_blt45.svg.png");
        piecePixmap[4] = new QPixmap("://res/200px-Chess_nlt45.svg.png");
        piecePixmap[5] = new QPixmap("://res/200px-Chess_plt45.svg.png");
        piecePixmap[6] = new QPixmap("://res/200px-Chess_kdt45.svg.png");
        piecePixmap[7] = new QPixmap("://res/200px-Chess_qdt45.svg.png");
        piecePixmap[8] = new QPixmap("://res/200px-Chess_rdt45.svg.png");
        piecePixmap[9] = new QPixmap("://res/200px-Chess_bdt45.svg.png");
        piecePixmap[10] = new QPixmap("://res/200px-Chess_ndt45.svg.png");
        piecePixmap[11] = new QPixmap("://res/200px-Chess_pdt45.svg.png");

        /*for(int i=0; i<8; ++i)
        {
            for(int j=0; j<8; ++j)
            {
                if(chessboard[i][j] != NULL)
                {
                    int pieceId = chessboard[i][j]->getClassId()-1;
                    QGraphicsPixmapItem *pieceItem = this->addPixmap(*piecePixmap[pieceId]);
                    pieceItem->setPos(squareItem[i][j]->pos());
                    pieceItem->setFlag(QGraphicsItem::ItemIsMovable);
                }
            }
        }*/

        movingItem = NULL;
    }

    void Chessboard::update()
    {
        foreach(QGraphicsItem *item, this->items())
        {
            if(item->flags().testFlag(QGraphicsItem::ItemIsMovable))
            {
                this->removeItem(item);
            }
        }

        for(int i=0; i<8; ++i)
        {
            for(int j=0; j<8; ++j)
            {
                if(chessboard[i][j] != NULL)
                {
                    int pieceId = chessboard[i][j]->getClassId()-1;
                    QGraphicsPixmapItem *pieceItem = this->addPixmap(*piecePixmap[pieceId]);
                    pieceItem->setPos(squareItem[i][j]->pos());
                    pieceItem->setFlag(QGraphicsItem::ItemIsMovable);
                    pieceItemMap[chessboard[i][j]->getObjectId()] = pieceItem;
                }

            }
        }
    }

    void Chessboard::mousePressEvent(QGraphicsSceneMouseEvent *event)
    {
        QGraphicsItem *item = this->itemAt(event->buttonDownScenePos(Qt::LeftButton), QTransform());
        if(item)
        {
            if(item->flags().testFlag(QGraphicsItem::ItemIsMovable))
            {
                //std::cout << "Mouse Pressed on Movable Item" << std::endl;
                movingItem = item;
            }
        }
    }

    void Chessboard::mouseMoveEvent(QGraphicsSceneMouseEvent *event)
    {
        if(movingItem)
        {
            movingItem->setPos(event->scenePos() - movingItem->boundingRect().center());
        }
        //std::cout << "Item Mouse Moved" << std::endl;
    }

    void Chessboard::mouseReleaseEvent(QGraphicsSceneMouseEvent *event)
    {
        if(movingItem)
        {
            //std::cout << this->items(event->buttonDownScenePos(Qt::LeftButton)).size() << std::endl;
            QGraphicsItem *startSquareItem = NULL;
            QGraphicsItem *endSquareItem = NULL;
            foreach(QGraphicsItem *item, this->items(event->buttonDownScenePos(Qt::LeftButton)))
            {
                if(!item->flags().testFlag(QGraphicsItem::ItemIsMovable))
                {
                    startSquareItem = item;
                    break;
                }
            }
            foreach(QGraphicsItem *item, this->items(event->scenePos()))
            {
                if(!item->flags().testFlag(QGraphicsItem::ItemIsMovable))
                {
                    endSquareItem = item;
                    break;
                }
            }
            int startX = (startSquareItem->pos().x()-borderSize)/squareItemSize;
            int startY = (startSquareItem->pos().y()-borderSize)/squareItemSize;
            int endX = (endSquareItem->pos().x()-borderSize)/squareItemSize;
            int endY = (endSquareItem->pos().y()-borderSize)/squareItemSize;

            try
            {
                this->doMove(startX, startY, endX, endY);
                emit turnChanged();
            }

            catch(GameOverException &e)
            {
                switch(e.getWinner())
                {
                case WHITE:
                    std::cout << "Game over - white win." << std::endl;
                    break;
                case BLACK:
                    std::cout << "Game over - black win." << std::endl;
                    break;
                case NONE:
                    std::cout << "Game over - tie." << std::endl;
                    break;
                }
                emit gameOver();
            }
            catch(IllegalMoveException &e)
            {
                std::cout << "Illegal move" << std::endl;
            }

            this->update();
            movingItem = NULL;
            std::cout << "Start: [" << startX << ", " << startY << "]" << std::endl;
            std::cout << "End: [" << endX << ", " << endY << "]" << std::endl << std::endl;
        }
    }
}
