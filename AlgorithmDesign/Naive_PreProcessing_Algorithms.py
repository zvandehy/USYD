#Problem Statement: given a (random) timeline of investment fluctuations, find the best window of investment
#find indices i,j where 0<=i<=j<n that maximizes an array "days" of gain/loss values for the day
import numpy as np
import datetime

def preProcess(days):
    pre = list()
    def eval(x,y):
        return pre[x]- pre[y+1]
    #init pre
    for i in range(len(days)+1):
        sum = 0
        j = i
        for j in range(len(days)):
            sum += days[j]
        pre.append(sum)
    answer = (0,0)
    for i in range(len(days)):
        j = i
        for j in range(len(days)):
            if eval(i,j) > eval(answer[0], answer[1]):
                answer = (i,j)
    return answer


def naive(days):
    def eval(x,y):
        #sum of values of x to y in days
        sum = 0
        index = x
        while(index <= y):
            sum += days[index]
            index+=1
        return sum
    answer = (0,0)
    for i in range(len(days)):
        j = i
        for j in range(len(days)):
            if eval(i,j) > eval(answer[0], answer[1]):
                answer = (i,j)
    return answer

print("i\tNaive\tPreProcess")
i=0
while (i < 1000000):
   i+=500
   randomDays = np.random.randint(-10,10,i)

   startTime = datetime.datetime.now()
   #naive(randomDays)
   endTime = datetime.datetime.now()
   val = endTime - startTime

   startTime2 = datetime.datetime.now()
   preProcess(randomDays)
   endTime2 = datetime.datetime.now()
   val2 = endTime2 - startTime2

   print(str(i) + "\t" + str(val.seconds) + "\t" + str(val2.seconds))
#i       Naive   PreProcess
#500     11      0
#1000    45      0
#1500    156     1
#2000    451     2
#2500    672     4
#3000    1143    6
#3500    3487    8
#4000    0       11
#4500    0       14
#5000    0       17
#5500    0       22
#6000    0       27
#6500    0       29
#7000    0       34
#7500    0       39
#8000    0       43
#8500    0       50
#9000    0       57
#9500    0       61
#10000   0       71
#10500   0       79
#11000   0       86
#11500   0       97
#12000   0       105
#12500   0       112
#13000   0       124
#13500   0       134
#14000   0       140
#14500   0       154
#15000   0       165
#15500   0       172
#16000   0       187
#16500   0       200
#17000   0       208
#17500   0       224
#18000   0       237
#18500   0       245
#19000   0       269
#19500   0       274
#20000   0       274
