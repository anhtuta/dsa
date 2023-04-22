package datastructure.heap;

/**
 * Clone từ class MinHeapInt. Nếu sửa gì thì phải sửa cả 2.
 * 2 class này chỉ khác nhau hàm heapify, chỗ so sánh lớn hơn vs nhỏ hơn thôi, và tên biến smallest
 * vs largest nữa
 */
public class MaxHeapInt {
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

    public static void main(String[] args) {
        MaxHeapInt heap = new MaxHeapInt(10_000);
        heap.add(16);
        heap.add(14);
        heap.add(10);
        heap.add(8);
        heap.add(7);
        heap.add(9);
        heap.add(3);
        heap.add(2);
        heap.add(4);
        heap.add(1);
        heap.print();

        heap.add(15);
        heap.print();
    }
}
