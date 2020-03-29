#Problem Statement: given a (random) timeline of investment fluctuations, find the best window of investment
#NOTE: This is the Maximum-Sum Contiguous Subarray (MCS) problem
#find indices i,j where 0<=i<=j<n that maximizes an array "days" of gain/loss values for the day
import numpy as np
import datetime
import csv


def preProcess(days):
    pre = list()
    def eval(x,y): #O(1)
        return pre[x]- pre[y+1] #O(1)
    #init pre
    for i in range(len(days)+1): #O(n) * O(n)
        sum = 0
        j = i
        for j in range(len(days)): #O(n)
            sum += days[j]
        pre.append(sum)
    answer = (-1,-1)
    for i in range(len(days)): #O(n^2)
        j = i
        for j in range(len(days)): #O(n)
            if eval(i,j) > eval(answer[0], answer[1]): #O(4)
                answer = (i,j)
    return answer


def naive(days):
    def eval(x,y):
        #sum of values from x to y in days, otherwise days[x] + days[x+1] + ... + days[y]
        sum = 0
        index = x
        while(index <= y): #O(n)
            sum += days[index]
            index+=1
        return sum
    answer = (-1,-1)
    for i in range(len(days)): #O(n) * O(n) * O(2n) = O(n^3)
        j = i
        for j in range(len(days)): #O(n) * O(2n)
            if eval(i,j) > eval(answer[0], answer[1]): #O(2n)
                answer = (i,j)
    return answer

def divideAndConquer(days):
    #T(n) = 2(n/2) + O(n) = O(nlogn)
    #recurse over left --> Find MCS of left
    #recurse over right --> Find MCS of right
    #merge --> Find MCS between left and right

    left = days[:len(days)//2]
    right = days[len(days)//2:]

    leftMCS, leftAnswer = divideAndConquer(left)
    rightMCS, rightAnswer = divideAndConquer(right)

def divideAndConquer(days):
    #T(n) = 2(n/2) + O(n) = O(nlogn)
    #recurse over left --> Find MCS of left
    #recurse over right --> Find MCS of right
    #merge --> Find MCS between left and right
    
    #base case n=1
    if len(days) == 1:
        if(days[0] > 0):
            return days[0], (0,0)
        else:
            return 0, (-1,-1)

    #divide list in half x2
    left = days[:len(days)//2]
    right = days[len(days)//2:]

    #recurse each half
    leftMCS, leftAnswer = divideAndConquer(left)
    rightMCS, rightAnswer = divideAndConquer(right)

    #find middle to left so that middle is always in the list
    mcs_left = 0
    i = len(days)//2
    total = 0
    middleLeftAnswer = (i,i)

    while i >= 0:
        total += days[i]
        if(total > mcs_left):
            mcs_left = total
            middleLeftAnswer = (i,len(days)//2)
        i-=1

    #find middle to right
    mcs_right = 0
    total=0
    i=len(days)//2
    middleRightAnswer = (i,i)

    while i < len(days):
        total += days[i]
        if(total > mcs_right):
            mcs_right = total
            middleRightAnswer = (len(days)//2,i)
        i+=1
    middleMCS = mcs_left + mcs_right
    
    answer = (middleLeftAnswer[0], middleRightAnswer[1])

    best = max(leftMCS,rightMCS,middleMCS)
    if best == leftMCS:
        answer = leftAnswer
    if best == rightMCS:
        answer = (len(days)//2+rightAnswer[0], len(days)//2+rightAnswer[1])

    return best, answer

def dynamic(days):
    #subproblems: OPT(i) = value of optimal solution ending at i
    #which is A[0] + A[1] + ... + A[i]

    OPT = [0]*len(days)
    answers = [(-1,-1)]*len(days)
    if(days[0] < 0):
        OPT[0] = 0
        answers[0] = (-1,-1)
    else:
        OPT[0] = days[0]
        answers[0] = (0,0)
    
    i=1
    for i in range(len(days)): #O(n)
        if OPT[i-1] + days[i] > 0: #accessing OPT[i-1] is O(1)
            OPT[i] = OPT[i-1] + days[i] #setting OPT[i] is O(1)
            if(answers[i-1][0] == -1):
                answers[i] = (i, i)
            else:
                answers[i] = (answers[i-1][0], i)
        else:
            OPT[0] = 0
            answers[0] = (-1,-1)
    bestIndex = -1
    best = -1
    for j in range(len(OPT)): #O(n) to find best OPT
        if best < OPT[j]:
            bestIndex = j
            best = OPT[j]
    
    return answers[bestIndex]

print("Start timing")
with open('timer2.csv', 'w', newline='') as file:
    writer = csv.writer(file)
    # print("i\tD&C\tDynamic")
    writer.writerow(["i", "Naive", "Preprocess", "D&C", "Dynamic"])
    i=0
    while (i < 1000000):
        i+=1000
        randomDays = np.random.randint(-10,10,i)

        # startTime3 = datetime.datetime.now()
        # naive(randomDays)
        # endTime3 = datetime.datetime.now()
        # val3 = endTime3 - startTime3

        # startTime4 = datetime.datetime.now()
        # preProcess(randomDays)
        # endTime4 = datetime.datetime.now()
        # val4 = endTime4 - startTime4

        startTime = datetime.datetime.now()
        divideAndConquer(randomDays)
        endTime = datetime.datetime.now()
        val = endTime - startTime

        startTime2 = datetime.datetime.now()
        dynamic(randomDays)
        endTime2 = datetime.datetime.now()
        val2 = endTime2 - startTime2

        #    print(str(i) + "\t" + str(val.microseconds) + "\t" + str(val2.microseconds))
        writer.writerow([i, 0, 0, val.microseconds, val2.microseconds])
print("Done timing")
    
    

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
