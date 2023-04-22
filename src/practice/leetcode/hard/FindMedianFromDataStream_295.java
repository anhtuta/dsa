package practice.leetcode.hard;

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
 * Ref: https://emre.me/coding-patterns/two-heaps/
 */
public class FindMedianFromDataStream_295 {
    MinHeapInt minHeap;
    MaxHeapInt maxHeap;

    public FindMedianFromDataStream_295() {
        minHeap = new MinHeapInt(50_001);
        maxHeap = new MaxHeapInt(50_001);
    }

    public void addNum(int num) {
        if (maxHeap.size == 0 || maxHeap.peak() >= num) {
            maxHeap.add(num);
        } else {
            minHeap.add(num);
        }

        if (maxHeap.size > minHeap.size + 1) {
            minHeap.add(maxHeap.pop());
        } else if (maxHeap.size < minHeap.size) {
            maxHeap.add(minHeap.pop());
        }

        maxHeap.print();
        minHeap.print();
        System.out.println();
    }

    public double findMedian() {
        if (maxHeap.size == minHeap.size) {
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

    static class MinHeapInt {
        private int[] heap;
        private int size;

        public MinHeapInt(int maxCapacity) {
            heap = new int[maxCapacity];
            size = 0;
        }

        public void add(int data) {
            if (size == heap.length) {
                System.out.println("Heap is full!");
                return;
            }

            heap[size++] = data;
            buildHeap();
        }

        public int pop() {
            int res = heap[0];
            swap(0, size - 1);
            size--;
            heapify(0);
            return res;
        }

        public int peak() {
            return heap[0];
        }

        public int getSize() {
            return size;
        }

        public void print() {
            if (size <= 0)
                return;
            System.out.print("MinHeapInt [");
            for (int i = 0; i < size - 1; i++) {
                System.out.print(heap[i] + " ");
            }
            System.out.println(heap[size - 1] + "]");
        }

        private void buildHeap() {
            // Bởi vì các node từ n/2 -> n-1 đều là lá nên chỉ cần vun đống cho các node trước đó (ko phải lá)
            for (int i = size / 2 - 1; i >= 0; i--) {
                heapify(i);
            }
        }

        private void heapify(int i) {
            if (i < 0)
                return;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int smallest = i;

            if (left < size && heap[left] < heap[i]) {
                smallest = left;
            }
            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
            }

            // Nếu node i hiện tại < 1 trong 2 node con, thì ta hoán vị i với node con đó rồi lại tiếp tục vun
            // đống cho thằng con đó, tới khi nào toàn bộ node ko vi phạm tính chất đống nữa thì thôi
            if (i != smallest) {
                swap(i, smallest);
                heapify(smallest);
            }
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

    static class MaxHeapInt {
        private int[] heap;
        private int size;

        public MaxHeapInt(int maxCapacity) {
            heap = new int[maxCapacity];
            size = 0;
        }

        public void add(int data) {
            if (size == heap.length) {
                System.out.println("Heap is full!");
                return;
            }

            heap[size++] = data;
            buildHeap();
        }

        public int pop() {
            int res = heap[0];
            swap(0, size - 1);
            size--;
            heapify(0);
            return res;
        }

        public int peak() {
            return heap[0];
        }

        public int getSize() {
            return size;
        }

        public void print() {
            if (size <= 0)
                return;
            System.out.print("MaxHeapInt [");
            for (int i = 0; i < size - 1; i++) {
                System.out.print(heap[i] + " ");
            }
            System.out.println(heap[size - 1] + "]");
        }

        private void buildHeap() {
            // Bởi vì các node từ n/2 -> n-1 đều là lá nên chỉ cần vun đống cho các node trước đó (ko phải lá)
            for (int i = size / 2 - 1; i >= 0; i--) {
                heapify(i);
            }
        }

        private void heapify(int i) {
            if (i < 0)
                return;
            int left = 2 * i + 1;
            int right = 2 * i + 2;
            int largest = i;

            if (left < size && heap[left] > heap[i]) {
                largest = left;
            }
            if (right < size && heap[right] > heap[largest]) {
                largest = right;
            }

            // Nếu node i hiện tại < 1 trong 2 node con, thì ta hoán vị i với node con đó rồi lại tiếp tục vun
            // đống cho thằng con đó, tới khi nào toàn bộ node ko vi phạm tính chất đống nữa thì thôi
            if (i != largest) {
                swap(i, largest);
                heapify(largest);
            }
        }

        private void swap(int i, int j) {
            int temp = heap[i];
            heap[i] = heap[j];
            heap[j] = temp;
        }
    }

}
