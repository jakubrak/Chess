#include "timer.h"

namespace Ui
{
    Timer::Timer(QObject *parent, int time) : QTimer(parent)
    {
        this->time = time;
    }

    void Timer::update()
    {
        time -= interval();
        if(time == 0)
            emit timeUp();
    }

    void Timer::setTime(const int time)
    {
        this->time = time;
    }

    const QString Timer::getTime()
    {
        int minutes = time/60000;
        int seconds = (time/1000 - minutes*60);
        return QString::number(minutes) + ":" + QString::number(seconds);
    }
}

