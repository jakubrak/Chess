#ifndef MAINWINDOW_H
#define MAINWINDOW_H

#include <QMainWindow>
#include <QResizeEvent>
#include "timer.h"

namespace Ui
{
    class MainWindow;
    class Chessboard;
}

class MainWindow : public QMainWindow
{
    Q_OBJECT

public:
    explicit MainWindow(QWidget *parent = 0);
    void resizeEvent(QResizeEvent *event);
    ~MainWindow();

private slots:
    void on_newGameButton_clicked();
    void on_startButton_clicked();
    void on_undoMoveButton_clicked();
    void on_giveUpButton_clicked();
    void blackTimerUpdate();
    void whiteTimerUpdate();
    void blackTimeUp();
    void whiteTimeUp();
    void timeChanged();
    void turnChanged();
    void gameOver();

private:
    Ui::Chessboard *chessboard;
    Ui::MainWindow *ui;
    Ui::Timer *whiteTimer;
    Ui::Timer *blackTimer;
};

#endif // MAINWINDOW_H
