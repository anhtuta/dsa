package datastructure.heap;

import java.util.ArrayList;
import java.util.List;

/**
 * Là cây nhị phân gần hoàn chỉnh mà node cha luôn <= node con (cây nhị phân hoàn chỉnh là cây mà
 * tất cả các mức đều là đầy, ngoại trừ mức cuối cùng, mức cuối được điền từ trái sang phải)
 * 
 * Ta sẽ dùng mảng để lưu trữ data cho heap (xem thêm hình).
 * i = 0: root của heap
 * Parent của node i là (i - 1)/2
 * Node trái của i là 2*i + 1
 * Node phải của i là 2*i + 2
 * Các node từ n/2 -> n-1 đều là lá
 * 
 * Ref:
 * - https://emre.me/data-structures/heaps/
 * - CTDL&GT, Nguyễn Đức Nghĩa
 */
public class MinHeap<T extends Comparable<T>> {
    List<T> heap;

    /**
     * @param initCapacity là capacity ban đầu chứ ko phải max capacity, khi heap full mà vẫn add thêm
     *        phần tử, thì heap sẽ resize
     */
    public MinHeap(int initCapacity) {
        heap = new ArrayList<>(initCapacity); // Phải dùng array nhé, chứ ko dùng LinkedList
    }

    public MinHeap(List<T> initData) {
        heap = new ArrayList<>(initData);
        buildHeap();
    }

    public void add(T data) {
        heap.add(data);
        // Phải build lại heap, nhưng việc build này khá ko tối ưu, cần tìm các chỉ vun đống lại những node
        // liên quan
        buildHeap();
    }

    /**
     * Lấy phần tử nhỏ nhất của heap (root của heap):
     * - Trả về root
     * - Hoán vị root với phần tử cuối cùng
     * - Vun lại đống tại root
     */
    public T pop() {
        T res = heap.get(0);
        swap(0, heap.size() - 1);
        heap.remove(heap.size() - 1);
        heapify(0);
        return res;
    }

    /**
     * @return root of the heap
     */
    public T peak() {
        return heap.get(0);
    }

    public void print() {
        System.out.print("MinHeap [");
        for (int i = 0; i < heap.size() - 1; i++) {
            System.out.print(heap.get(i) + " ");
        }
        System.out.println(heap.get(heap.size() - 1) + "]");
    }

    private void buildHeap() {
        int n = heap.size();

        // Bởi vì các node từ n/2 -> n-1 đều là lá nên chỉ cần vun đống cho các node trước đó (ko phải lá)
        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(i);
        }
    }

    /**
     * Vun đống tại node i. Hiện tại node i ko thoả mãn tính chất đống (node i > node con), ta cần tìm
     * con bé nhất trong 2 con trái phải, rồi đổi chỗ i với thằng con đó. Sau đó lại tiếp tục vun đống
     * cho thằng con đó, tới khi nào toàn bộ node ko vi phạm tính chất đống nữa thì thôi
     */
    private void heapify(int i) {
        if (i < 0)
            return;
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int n = heap.size();
        int smallest = i;

        if (left < n && heap.get(left).compareTo(heap.get(i)) < 0) {
            smallest = left;
        }
        if (right < n && heap.get(right).compareTo(heap.get(smallest)) < 0) {
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
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        MinHeap<Integer> heap = new MinHeap<>(10);
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

        heap.pop();
        heap.print();
        heap.pop();
        heap.print();
    }
}

