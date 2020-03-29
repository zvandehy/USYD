# Start with the code for counting inversions

invertedList = [8,7,3,4,6,1,2,5]
expected = 20

def sort_and_count(rectangles):
    
    if len(rectangles) == 1:
        return rectangles, 0
    else:
        #divide rectangles into two lists
        left = rectangles[:len(rectangles)//2]
        right = rectangles[len(rectangles)//2:]

        #recursively conquer
        left, left_inversions = sort_and_count(left)
        right, right_inversions = sort_and_count(right)

        #temp array to store merged/sorted list
        temp = []
        inversions = 0
        #indices for tracking/merging each list
        left_i = 0
        right_i = 0

        #combine
        while left_i < len(left) and right_i < len(right):
            #if value in left array is smaller then add it to temp
            if left[left_i] <= right[right_i]:
                temp.append(left[left_i])
                left_i+=1
            #if value in right array is smaller then add it to temp AND increment obstructions
            else:
                temp.append(right[right_i])
                right_i+=1
                #num of obstructions is equal to the number of remaining elements in left
                inversions += len(left)-left_i
            
        #once one list has been exhausted, add the remaining elements of the other list
        temp+=left[left_i:]
        temp+=right[right_i:]

        #obstructions equals num in left, num in right, and num in merge (already added)
        return temp, (inversions + left_inversions + right_inversions)

print("Expected: " + str(expected))
print("Solution: " + str(sort_and_count(invertedList)[1]))
print("Sorted  : " + str(sort_and_count(invertedList)[0]))