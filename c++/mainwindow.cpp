#include "mainwindow.h"
#include "ui_mainwindow.h"
#include "chessboard.h"
#include <iostream>
#include <QDir>
#include <QGraphicsPixmapItem>
#include <QDebug>
#include <QMessageBox>

MainWindow::MainWindow(QWidget *parent) :
    QMainWindow(parent),
    ui(new Ui::MainWindow)
{
    ui->setupUi(this);
    chessboard = new Ui::Chessboard();
    ui->graphicsView->setScene(chessboard);
    this->setWindowTitle("Chess");
    this->showMaximized();
    blackTimer = new Ui::Timer(this, ui->setTimeSpinBox->value()*60000);
    whiteTimer = new Ui::Timer(this, ui->setTimeSpinBox->value()*60000);
    ui->whiteTimeLabel->setText(QString::number(ui->setTimeSpinBox->value()) + ":0");
    ui->blackTimeLabel->setText(QString::number(ui->setTimeSpinBox->value()) + ":0");
    connect(blackTimer, SIGNAL(timeout()), this, SLOT(blackTimerUpdate()));
    connect(blackTimer, SIGNAL(timeUp()), this, SLOT(blackTimeUp()));
    connect(whiteTimer, SIGNAL(timeout()), this, SLOT(whiteTimerUpdate()));
    connect(whiteTimer, SIGNAL(timeUp()), this, SLOT(whiteTimeUp()));
    connect(ui->setTimeSpinBox, SIGNAL(valueChanged(int)), this, SLOT(timeChanged()));
    connect(chessboard, SIGNAL(turnChanged()), this, SLOT(turnChanged()));
    connect(chessboard, SIGNAL(gameOver()), this, SLOT(gameOver()));
    ui->graphicsView->setEnabled(false);
}

MainWindow::~MainWindow()
{
    delete ui;
}

void MainWindow::resizeEvent(QResizeEvent *event)
{
    QMainWindow::resizeEvent(event);
    /*int h = e->size().height(),
        w = e->size().width(),
        s;
    if(w > h) s = h;
    else s = w;
    ui->graphicsView->setGeometry(0, (h-s)/2, s, s);*/
    chessboard->setSceneRect(chessboard->sceneRect());
    ui->graphicsView->fitInView(chessboard->sceneRect(), Qt::KeepAspectRatio);

}

void MainWindow::on_newGameButton_clicked()
{
    chessboard->createNewGame();
    chessboard->update();
    blackTimer->stop();
    whiteTimer->stop();
}

void MainWindow::on_startButton_clicked()
{
    whiteTimer->setTime(ui->setTimeSpinBox->value()*60000);
    blackTimer->setTime(ui->setTimeSpinBox->value()*60000);
    ui->graphicsView->setEnabled(true);
    whiteTimer->start(1000);
}
void MainWindow::on_undoMoveButton_clicked()
{
    chessboard->undoMove();
    chessboard->update();
    turnChanged();
}

void MainWindow::on_giveUpButton_clicked()
{

}

void MainWindow::blackTimerUpdate()
{
    ui->blackTimeLabel->setText(blackTimer->getTime());
    blackTimer->update();
}

void MainWindow::whiteTimerUpdate()
{
    ui->whiteTimeLabel->setText(whiteTimer->getTime());
    whiteTimer->update();
}

void MainWindow::blackTimeUp()
{
    QMessageBox msgBox;
    msgBox.setText("White won");
    msgBox.exec();
    blackTimer->stop();
    whiteTimer->stop();
    ui->graphicsView->setEnabled(false);
}

void MainWindow::whiteTimeUp()
{
    QMessageBox msgBox;
    msgBox.setText("Black won");
    msgBox.exec();
    blackTimer->stop();
    whiteTimer->stop();
    ui->graphicsView->setEnabled(false);
}

void MainWindow::timeChanged()
{
    ui->whiteTimeLabel->setText(QString::number(ui->setTimeSpinBox->value()) + ":0");
    ui->blackTimeLabel->setText(QString::number(ui->setTimeSpinBox->value()) + ":0");
}

void MainWindow::turnChanged()
{
    if(blackTimer->isActive())
    {
        blackTimer->stop();
        whiteTimer->start(1000);
    }
    else
    {
        blackTimer->start(1000);
        whiteTimer->stop();
    }
}

void MainWindow::gameOver()
{
    QMessageBox msgBox;
    msgBox.setText("Game over");
    msgBox.exec();
    blackTimer->stop();
    whiteTimer->stop();
    ui->graphicsView->setEnabled(false);
}
