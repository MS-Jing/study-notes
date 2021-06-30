#!/bin/bash

echo "=====正在查找当前目录下的jar文件...====="
target=`ls *.jar |awk '{printf $0}'`
echo "=====目标文件为：$target====="
PID=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
if [[ $PID == "" ]]
then
	echo "=====服务【$target】未启动====="
	exit
fi
echo -e "是否要停止服务【$target】 pid:【$PID】 ?(y/n):\c"

read shut

if [[ $shut == "y" ]]
then
	echo "=====正在停止====="
	pid=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
	kill -15 $pid
	int = 1
	while(( $int<10 ))
	do
		pid0=`ps -aux|grep $target|grep -v grep |awk '{printf $2}'`
		if [[ $pid0 == "" ]]
		then
			echo "服务【$target】已停止"
			exit
		fi
		sleep 3s
		let "int++"
	done
	echo "服务【$target】停止超时 请收到停止或重启"
else
	echo "=====正在退出====="
fi
