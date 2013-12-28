#!/bin/bash

mode=$1 # easy/ medium/ hard
row=0
col=0
mines=0

if [ "$mode" == "easy" ]
then
	row=8
	col=8
	mines=10
elif [ "$mode" == "medium" ]
then
	row=16
	col=16
	mines=40
elif [ "$mode" == "hard" ]
then
	row=30
	col=16
	mines=99
else
	echo 'Masukkan mode [easy/medium/hard]!'
	exit
fi

N=`cat seed.txt | head -n 1`
accu=0

`mkfifo interaction`
for (( i=2; i<=$N+1; i++))
do
	seed=`cat seed.txt | head -n $i | tail -n 1`
	
	terbesar=0
	for (( j = 0; j < 5; j++))
	do
		`java CommandSimulator $row $col $mines $seed < interaction | java WrappedAgent > interaction`
		temp=`cat .tempo | cut -d " " -f 2`
		
		terbesar=`awk -v a=$terbesar -v b=$temp 'BEGIN { print ((a > b) ? a : b) }'`
	done
	
	echo "testing $i = $terbesar"
	accu=`awk -v a=$accu -v b=$terbesar 'BEGIN { print (a + b) }'`
done

total=`awk -v a=$accu -v b=$N 'BEGIN { print (a / b) }'`

echo "Difficulty Mode : $mode"
echo "Test Performed  : $N time(s)"
echo "Average Score   : $total"

`rm -f interaction`
