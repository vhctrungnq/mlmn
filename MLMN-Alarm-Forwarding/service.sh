#! /bin/bash
WORK_DIR=/u02/server/core/alarm
CLASSPATH=AlarmForwarding.jar:lib/bonecp-0.7.1.RELEASE.jar:lib/ojdbc6.jar:lib/commons-configuration-1.9.jar:lib/commons-lang-2.6.jar:lib/commons-logging-1.1.2.jar:lib/guava-14.0.1.jar:lib/log4j-1.2.17.jar:lib/netty-3.6.5.Final.jar:lib/slf4j-api-1.7.5.jar:lib/slf4j-log4j12-1.7.5.jar:$CLASSPATH
APP_PATH=vn.com.vhc.manager.AlarmForwardingServer
pid_file=service.pid
#JAVA=/usr/jdk/instances/jdk1.5.0/bin/java
JAVA=java

RUNNING=0
if [ -f $pid_file ]; then
	pid=`cat $pid_file`
	if [ "x$pid" != "x" ] && kill -0 $pid 2>/dev/null; then
		RUNNING=1
	fi
fi

start()

{
	cd $WORK_DIR
	if [ $RUNNING -eq 1 ]; then
		echo "Service already started (pid $pid)"
	else
		$JAVA -cp $CLASSPATH $APP_PATH &
		echo $! > $pid_file
		echo "Service started (pid $pid)"
	fi
}

stop()
{
	if [ $RUNNING -eq 1 ]; then
		kill -9 $pid
		echo "Service stopped"
	else
		echo "Service not running"
	fi
}

restart()
{
	stop
	start
}


case "$1" in

	'start')
		start
		;;

	'stop')
		stop
		;;

	'restart')
		restart
		;;

	*)
		echo "Usage: $0 {  start | stop | restart  }"
		exit 1
		;;
esac

exit 0
