#ifndef TIMER_H
#define TIMER_H

#include <QTimer>
#include <QString>

namespace Ui
{
    class Timer : public QTimer
    {
        Q_OBJECT
        int time;
    public:
        Timer(QObject *parent, int time);
        void update();
        void setTime(const int time);
        const QString getTime();
    signals:
        void timeUp();
    };
}

#endif // TIMER_H
