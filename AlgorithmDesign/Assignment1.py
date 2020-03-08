
# (left, right, y)
s={(0,1,0),(5,6,0),(0,3,1),(3,6,2),(3,6,3),(0,3,4)}
def solve(saucers):
    cannons = [ ]

    def compareRight(saucer):
        return saucer[1]

    saucers = sorted(saucers, key = compareRight) #O(nlogn)

    x = saucers[0][1]
    cannons.append(x)
    index = 0

    while index in range(len(saucers)): #O(n)
        saucer = saucers[index]
        # destroy saucer if x is between the left and right
        # if a saucer was destroyed, then see if the next saucer is also destroyed
        # if no (next) saucer is destroyed, its time to place the next cannon
        if not(saucer[0] <= x and x <= saucer[1]):
            x = saucer[1]
            cannons.append(x) # append to list is O(1)
        index = index + 1
    return cannons

print(solve(s))