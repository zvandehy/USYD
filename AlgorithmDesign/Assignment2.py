# Start with the code for counting inversions
#NOTE: This is not what I submitted because it does not work. My submitted solution uses p values that track
#how much of the left side of a rectangle has been obstructed. It isn't O(nlogn) though :(


import random

rectangles = [(0,1,1), (0,2,5), (2,4,2), (1,2,4), (3,4,3)]
expected = 3

Ry1 = sorted(rectangles, key =lambda rectangle: (rectangle[2], rectangle[0]) ) #O(nlogn)
Rx1 = sorted(rectangles, key =lambda rectangle: (rectangle[0], rectangle[2]) ) #O(nlogn)

def sort_and_count(Ry, Rx):
    #base case where size is 1
    if len(Ry) == 1:
        return Ry, 0
    #conquer
    else:
        #divide rectangles into 4 listls
        Ry_bottom = Ry[:len(Ry)//2]
        Ry_top = Ry[len(Ry)//2:]
        midpoint = Ry[len(Ry)//2]
        Rx_bottom = list()
        Rx_top = list()
        for rectangle in Rx:
            if rectangle[2] <= midpoint[2]:
                Rx_bottom.append(rectangle)
            else:
                Rx_top.append(rectangle)
        
        #recursively conquer by counting obstructions in each half
        combined_bottom, bottom_obstructions = sort_and_count(Ry_bottom, Rx_bottom)
        combined_top, top_obstructions = sort_and_count(Ry_top, Rx_top)
        
        #obstructions of bottom and top, still need obstructions between them
        obstructions = bottom_obstructions + top_obstructions

        #merge and count on combined_bottom and combined_top
        #combined_bottom [(rectangle, num of rectangles)]
        # [((0,1,1), 1)]
        print("-----START MERGE-----")
        print("combined bottom : " + str(combined_bottom))
        print("combined top: " + str(combined_top))
        print("---------------")
        merged = list()
        i=0
        j=0
        while i < len(combined_bottom) and j < len(combined_top):
            bot_rect = combined_bottom[i]
            top_rect = combined_top[j]
            #full obstruction
            if bot_rect[0] <= top_rect[0] and bot_rect[1] >= top_rect[1]:
                print("-----FULL-----")
                print("bot : " + str(bot_rect))
                print("top: " + str(top_rect))
                print("merged: " + str(merged))
                print("---------------")
                obstructions += 1
                j += 1
            #partial obstruction
            #TODO: Combine if adjacent but not overlapping
            elif max(bot_rect[0], top_rect[0]) <= min(bot_rect[1], top_rect[1]):
                #TODO: Problem when bottom is > 1 level below top, need to keep bottom
                merged.append((min(bot_rect[0], top_rect[0]),max(bot_rect[1], top_rect[1]), top_rect[2]))
                print("-----PARTIAL-----")
                print("bot   : " + str(bot_rect))
                print("top   : " + str(top_rect))
                print("merged: " + str(merged))
                print("---------------")
                j += 1
            #not adjacent
            else:
                #If we step through top, then if another bottom obstructs this top we will miss it
                #if we step through bottom, if this bottom abstructs another top we will miss it
                #but we can't do both unless it is n^2...
                merged.append(top_rect)
                print("-----MISS-----")
                print("bot : " + str(bot_rect))
                print("top: " + str(top_rect))
                print("merged: " + str(merged))
                print("---------------")
                j += 1
        #add remaining
        merged += (combined_bottom[i:])
        merged += (combined_top[j:])

        print("-----FINISH MERGE-----")
        print("bot : " + str(bot_rect))
        print("top: " + str(top_rect))
        print("merged: " + str(merged))
        print("---------------")

        #obstructions equals num in left, num in right, and num in merge (already added)
        return merged, obstructions


random.shuffle(rectangles)
combined, obstructed = sort_and_count(Ry1,Rx1)
if(obstructed == expected):
    print("Success: " + str(rectangles))
else:
    print("FAIL: " + str(rectangles) + "    " + str(obstructed))
    

print("Expected: " + str(expected))
print("Solution: " + str(obstructed))
print("Unobstructed Rectangles (combined)  : " + str(combined))



# print("-----Debug-----")
# print("left : " + str(left))
# print("right: " + str(right))
# print("after: " + str(temp))
# print("obstr: " + str(obstructions))
# print("---------------")