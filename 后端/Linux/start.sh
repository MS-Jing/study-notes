#!/bin/bash

echo "=====正在查找当前目录下的jar文件...====="
target=`ls *.jar |awk '{printf $0}'`
echo "=====目标文件为：$target====="

function restart(){
	echo "=====正在重启====="
	kill -15 $1
	while true
	do
		pid=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
		if [[ $pid == "" ]]
		then
			nohup java -Xms256m -Xmx1024m -jar $target > /dev/null 2>&1 &
			sleep 2s
			pid0=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
			if [[ $pid0 != "" ]]
			then
				echo "===== $target 服务以重启成功 pid:[ $pid0 ]====="
			else    
				echo "===== $target 服务重启失败 你可查看日志或者尝试重启====="
			fi
			break
		fi
		sleep 3s
	done
}


echo "=====查看当前目标是否在运行====="
PID=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
if [[ $PID != "" ]]
then
	echo "=====$target 以启动PID: $PID ====="
	echo -e "是否重启 $target ?(y/n):\c"
	read restart
	if [[ $restart == "y" ]]
	then
		restart $PID
	else
		echo "=====正在退出====="
		exit	
	fi
else
	echo "=====目标jar[ $target ]未启动,正在启动中...====="
	nohup java -Xms256m -Xmx1024m -jar $target > /dev/null 2>&1 &
	sleep 2s
	pid0=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
	if [[ $pid0 != "" ]]
	then
		echo "===== $target 服务以启动成功 pid:[ $pid0 ]====="
	else
		echo "===== $target 服务启动失败 你可查看日志或者尝试重启====="
	fi
fi

