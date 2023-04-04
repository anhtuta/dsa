# from sys import maxint

# Solution inside pdf file!
# Rõ ràng là SAI với dãy toàn số âm!


def golden_max_slice(A):
    max_ending = max_slice = A[0]
    for a in A:
        max_ending = max(a, max_ending + a)
        max_slice = max(max_slice, max_ending)
    return max_slice


print('max is: ', golden_max_slice([-2, 11, -4, 13, -5, 2]))
print('max is: ', golden_max_slice([-21, -3, -10, -5, -9]))

# Ref: https://www.geeksforgeeks.org/largest-sum-contiguous-subarray/


def maxSubArraySum(a, size):

    # max_so_far = -maxint - 1
    max_so_far = -99999999 - 1
    max_ending_here = 0

    for i in range(0, size):
        max_ending_here = max_ending_here + a[i]
        if (max_so_far < max_ending_here):
            max_so_far = max_ending_here

        if max_ending_here < 0:
            max_ending_here = 0
    return max_so_far


a = [-2, -3, 4, -1, -2, 1, 5, -3]
b = [-21, -3, -10, -5, -9]

print("Maximum contiguous sum is", maxSubArraySum(a, len(a)))
print("Maximum contiguous sum is", maxSubArraySum(b, len(b)))
