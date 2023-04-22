package practice.leetcode.hard;

import datastructure.heap.MaxHeapInt;
import datastructure.heap.MinHeapInt;

/**
 * https://leetcode.com/problems/find-median-from-data-stream/
 * 
 * Cách làm: dùng 2 heap (1 min heap, 1 max heap). Hàm addNum sẽ add phần tử vào 1 trong 2 heap, sao
 * cho 2 heap luôn cân bằng, hoặc max heap > min heap 1 phần tử. (If the count of elements is odd,
 * let’s decide to have more elements in max_heap than the min_heap)
 * 
 * Max heap sẽ là nơi lưu 1 nửa phần tử nhỏ hơn, min heap lưu 1 nửa lớn hơn
 * 
 * Solution này có kết quả chạy: 20 / 21 testcases passed. Case cuối bị timeout, chứng tỏ solution
 * đúng rồi. Bây giờ chỉ cần tối ưu thêm đoạn vun đống lúc add mới phần tử cho heap là được
 * 
 * Update: sau khi tối ưu lại heap thì solution này đã được accepted! Note: nếu submit trên leetcode
 * thì phải copy 2 class MinHeapInt và MaxHeapInt vào bên trong class MedianFinder (nên dùng inner
 * static class)
 * 
 * Ref: https://emre.me/coding-patterns/two-heaps/
 */
public class FindMedianFromDataStream_295 {
    private MinHeapInt minHeap;
    private MaxHeapInt maxHeap;

    public FindMedianFromDataStream_295() {
        minHeap = new MinHeapInt(50_001);
        maxHeap = new MaxHeapInt(50_001);
    }

    public void addNum(int num) {
        if (maxHeap.getSize() == 0 || maxHeap.peak() >= num) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }

        if (maxHeap.getSize() > minHeap.getSize() + 1) {
            minHeap.add(maxHeap.remove());
        } else if (maxHeap.getSize() < minHeap.getSize()) {
            maxHeap.add(minHeap.remove());
        }
    }

    public double findMedian() {
        if (maxHeap.getSize() == minHeap.getSize()) {
            return (double) (maxHeap.peak() + minHeap.peak()) / 2;
        } else {
            return maxHeap.peak();
        }
    }

    public static void main(String[] args) {
        FindMedianFromDataStream_295 app = new FindMedianFromDataStream_295();
        app.addNum(3);
        app.addNum(1);
        app.addNum(5);
        System.out.println("Median = " + app.findMedian());
        app.addNum(4);
        System.out.println("Median = " + app.findMedian());
    }

}
