mine = [[1, 0, 0, 0, 3],
[0, 1, 0, 1, 0],
[0, 0, 5, 0, 0],
[0, 1, 0, 10, 0],
[1, 0, 0, 0, 2]]

m=len(mine[0])
n=len(mine)

#initialize OPT [i,j] = [None * n][None * m]
OPT = [None] * n
for i in range(n):
    OPT[i] = [None] * m


#OPT at level 0
OPT[0][0] = (mine[0][0], float("-inf"))
OPT[0][m-1] = (float("-inf"), mine[0][m-1])
for j in range(m):
    OPT[0][j] = (float("-inf"), mine[0][j], float("-inf"))

# #OPT at level 1
# OPT[1][0] = (float("-inf"), mine[1][0] + max(OPT[0][0]), mine[1][0] + max(OPT[0][1]))
# OPT[1][m-1] = (mine[1][m-1] + max(OPT[0][m-2]), mine[1][m-1] + max(OPT[0][m-1]), float("-inf"))
# for j in range(1,m-1):
#     OPT[1][j] = (mine[1][j] + max(OPT[0][j-1]), mine[1][j] + max(OPT[0][j]), mine[1][j] + max(OPT[0][j+1]))

def getOPT(OPT,i,j):
    
    #OPT[i][j] = (left, middle, right)
    #left = max of valid values from OPT[i-1][j-1] + mine[i][j]
    #middle = max of valid values from OPT[i-1][j] + mine[i][j]
    #right = max of valid values from OPT[i-1][j+1] + mine[i][j]

    #if left then 2 is not valid
    if(j==0):
        left = float("-inf")
    else:
        left = max(OPT[i-1][j-1][0], OPT[i-1][j-1][1]) + mine[i][j]
    #if middle then all are valid
    middle = max(OPT[i-1][j][0], OPT[i-1][j][1], OPT[i-1][j][2]) + mine[i][j]
    #if right then 0 is invalid
    if (j==m-1):
        right = float("-inf")
    else: 
        right = max(OPT[i-1][j+1][1], OPT[i-1][j+1][2]) + mine[i][j]
    
    return (left, middle, right)

#recurrences
for i in range(1,n):
    for j in range(m):
        OPT[i][j] = getOPT(OPT, i, j)

#print the optimal values
# for i in range(n):
#     print(str(OPT[i]) + "\t" + str(mine[i]))

# #max mine shaft
profit = 0
for j in range(m):
    if(max(OPT[n-1][j]) > profit):
        profit = max(OPT[n-1][j])

print(profit)
