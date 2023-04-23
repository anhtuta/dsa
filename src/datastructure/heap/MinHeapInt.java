package datastructure.heap;

/**
 * Clone từ class MinHeap. Nếu sửa gì thì phải sửa cả 2
 * Có thể xem thêm comment bên class MinHeap để hiểu rõ hơn
 */
public class MinHeapInt {
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
        heapifyUp(size - 1);
    }

    public int remove() {
        int res = heap[0];
        swap(0, size - 1);
        size--;
        heapifyDown(0);
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

    private int parentIndex(int pos) {
        if (pos <= 0)
            return -1;
        return (pos - 1) / 2;
    }

    private int parent(int pos) {
        if (pos <= 0)
            throw new ArrayIndexOutOfBoundsException("Cannot get parent of root");
        return heap[(pos - 1) / 2];
    }

    private void heapifyDown(int i) {
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
            heapifyDown(smallest);
        }
    }

    private void heapifyUp(int i) {
        int curr = heap[i];
        // i == 0 là root rồi, break
        while (i > 0 && curr < parent(i)) {
            heap[i] = parent(i);
            i = parentIndex(i);
        }
        heap[i] = curr;
    }

    private void swap(int i, int j) {
        int temp = heap[i];
        heap[i] = heap[j];
        heap[j] = temp;
    }

    public static void main(String[] args) {
        MinHeapInt heap = new MinHeapInt(10_000);
        heap.add(5);
        heap.add(9);
        heap.add(11);
        heap.add(14);
        heap.add(18);
        heap.add(19);
        heap.add(21);
        heap.add(22);
        heap.add(17);
        heap.add(27);
        heap.print();

        heap.add(2);
        heap.print();

        heap.remove();
        heap.print();
        heap.remove();
        heap.print();
    }
}
