#-------------------------------------------------
#
# Project created by QtCreator 2013-12-27T09:09:06
#
#-------------------------------------------------

QT       += core gui

greaterThan(QT_MAJOR_VERSION, 4): QT += widgets

TARGET = Chess
TEMPLATE = app


SOURCES += main.cpp\
        mainwindow.cpp \
    piece.cpp \
    move.cpp \
    square.cpp \
    verticalmove.cpp \
    horizontalmove.cpp \
    diagonalmove.cpp \
    knightsmove.cpp \
    bishop.cpp \
    king.cpp \
    knight.cpp \
    pawn.cpp \
    queen.cpp \
    rook.cpp \
    game.cpp \
    chessboard.cpp \
    illegalmoveexception.cpp \
    castlingexception.cpp \
    captureexception.cpp \
    gameoverexception.cpp \
    timer.cpp

HEADERS  += mainwindow.h \
    piece.h \
    move.h \
    square.h \
    verticalmove.h \
    horizontalmove.h \
    diagonalmove.h \
    knightsmove.h \
    bishop.h \
    king.h \
    knight.h \
    pawn.h \
    queen.h \
    rook.h \
    game.h \
    chessboard.h \
    illegalmoveexception.h \
    castlingexception.h \
    captureexception.h \
    gameoverexception.h \
    color.h \
    timer.h

FORMS    += mainwindow.ui

RESOURCES += \
    resources.qrc
