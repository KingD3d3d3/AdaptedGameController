#! /bin/sh
# /etc/init.d/transmission

### BEGIN INIT INFO
# Provides: transmission.sh
# Required-Start: $remote_fs $syslog
# Required-Stop: $remote_fs $syslog
# Default-Start: 2 3 4 5
# Default-Stop: 0 1 6
# Short-Description: Start daemon at boot time
# Description: Enable service provided by daemon.
### END INIT INFO


case "$1" in
  start)
    echo "Starting /home/pi/transmission"
    /home/pi/transmission &
    ;;
  stop)
    echo "Stopping /home/pi/transmission"
    killall transmission
    ;;
  *)
    echo "Usage: /etc/init.d/transmission {start|stop}"
    exit 1
    ;;
esac

exit 0
