# Codility lesson

Ref: https://app.codility.com/programmers/lessons/

Note: các bài nếu ko ghi level thì default là Easy

Cách solution trong file pdf có vẻ như được viết = python

Sau khi học xong codility, hãy chuyển qua [leetcode](../../leetcode/README.md), bởi vì chủ yếu luyện tập trên đó

## 1 số mẹo:

- Time complexity: dựa vào số lượng input n có thể dùng thuật toán hợp lý mà ko cần tìm thuật toán nhanh hơn cho mệt:
  - n <= 1 000 000, the expected time complexity is O(n) or O(n log n)
  - n <= 10 000, the expected time complexity is O(n^2)
  - n <= 500, the expected time complexity is O(n^3)
- Mấy cái bài mà có dãy input to đùng, xong rồi yêu cầu:

  - Tìm số lượng số trong dãy đó chia hết cho k,
  - Tìm số lượng số trong dãy đó mà dạng nhị phân có k số 0
  - Tìm số lượng số trong dãy đó mà ...

  Thì đừng có duyệt từng số xong chia thử hay convert từng số sang dạng nhị phân, vì chắc chắn sẽ
  bị timeout, cần tìm công thức để tính toán đơn giản hơn

- Mấy bài toán đếm, mà nói nếu như số lượng đếm được vượt quá 10 triệu phần tử thì return -1: thì chắc chắn phải DUYỆT từng phần tử để đếm, chứ KHÔNG có công thức toán học nào như case trên, bởi vì đếm từng thằng mới biết khi nào đếm tới 10 kq thì mới return được -1 chứ

## 1 số test case đặc biệt

- Mảng gồm toàn các số giống nhau
- Mảng gồm toàn các số khác nhau
- Mảng gồm toàn số 0
- Mảng gồm toàn số Integer.MAX_VALUE
- Mảng gồm toàn số âm
- Mảng rỗng
- Mảng gồm duy nhất 1 phần tử
- Mảng gồm duy nhất 1 phần tử = 0
- Input = max giới hạn cho trong đề bài

Nếu input là mảng, khi duyệt vòng for cần chú ý các giá trị đầu, cuối dãy (a[0], a[n-1]). Cẩn thận lỗi `ArrayIndexOutOfBoundsException`

# Coding patterns

Quy ước, ký hiệu:

- a[]: mảng a,
- slice: dãy con của mảng a, là 1 tập gồm các phần tử LIÊN TIẾP
- a[x...y]: slice của mảng a[] từ index x->y, tức là gồm các phần tử từ a[x] -> a[y] (với 0 <= x <= y < a.length)

## Lesson 4: Counting elements

Giống như thuật toán counting sort, ta sẽ dùng thêm mảng `cntArr` để đếm số lượng phần tử xuất hiện trong mảng. Nếu như ta biết các phần tử của mảng trong khoảng {0, 1, ... , m}, thì ta có thể tạo mảng `cntArr[m+1]` để đếm

```java
public int solution(int[] a) {
    int len = a.length;
    int[] cntArr = new int[len + 1];
    for (int i = 0; i < len; i++) {
        cntArr[a[i]]++;
    }
    // do something to solve the problem
}
```

Giới hạn:

- Chỉ áp dụng được với mảng các số nguyên dương
- Bộ nhớ: usually, we are not able to create arrays of 10^9 integers, because this would require more than one gigabyte of available memory. Do đó nó thường áp dụng với mảng mà có range <= 10^6 thôi!

## Lesson 5: Prefix sums

Một kỹ thuật khá đơn giản để tính tổng slice của 1 mảng, đó là dùng prefix sums.

Giả sử mảng input là a[], ký hiệu mảng p[] là mảng prefix sums của a[]. Prefix sum `p[i]` là tổng i+1 phần tử đầu tiên của a[] (quy ước khác file pdf 1 chút: trong file họ dùng p[i] = tổng của i phần tử đầu tiên, tức là = tổng p[0] -> p[i-1]):

```
p[i] = a[0] + a[1] + ... + a[i], i = [0...n-1] (n = a.length)
```

Thế thì, việc tính tổng của slice bất kì a[x...y] của mảng a[] sẽ chỉ tốn O(1):

```
sum(a[x...y]) = p[y] - p[x-1]
```

Sample code:

```java
private int[] getPrefixSums(int[] a) {
    int[] p = new int[a.length];
    p[0] = a[0];
    for (int i = 1; i < a.length; i++) {
        p[i] = p[i - 1] + a[i];
    }
    return p;
}
private int getSliceSum(int[] p, int left, int right) {
    if (left == 0)
        return p[right];
    return p[right] - p[left - 1];
}
// ex:
// a[] = [2, 3, 7, 5, 1, 3, 9]
// p[] = [2, 5, 12, 17, 18, 21, 30]
// a[2...4] = a[2] + a[3] + a[4] = 7 + 5 + 1 = 13
// a[2...4] = p[4] - p[1] = 18 - 5 = 13
```

Tương tự với sum, ta cũng có prefixProduct, là tích của các phần tử đầu tiên, xem ví dụ tại [leetcode 238](../../leetcode/medium/prefixsum/ProductOfArrayExceptSelf_238.java)

Chú ý: ví dụ trên phải dùng kiểu tính toán như trong slide, tức là prefixProduct[i] là tích của i phần tử đầu tiên, chứ ko phải là tích của a[0] -> a[i]. Thế nên mới thấy, slide chuẩn hơn, hay giờ cứ đổi theo cách tính giống như trong slide nhờ!!!

## Lesson 8: Leader (dominator)

Cho dãy a[] độ dài n, leader của dãy là phần tử xuất hiện NHIỀU HƠN n/2 lần. Dễ thấy mỗi dãy chỉ có tối đa 1 leader, vì nếu có 2, thì tổng số lượng phần tử sẽ nhiều hơn `2*n/2 = n`, mâu thuẫn với điều kiện ban đầu

Cách tìm leader:

- Solution `O(n*log(n))`: sort dãy a[], nếu dãy có leader thì lúc này nó sẽ là phần tử a[n/2]. Đặt `candidate = a[n/2]` Chỉ cần đếm nếu dãy có hơn n/2 phần tử candidate thì nó chính là leader
- Solution `O(n)`: nếu ta lần lượt remove 2 phần tử khác nhau bất kì của a[], thì phần tử cuối cùng sẽ là candidate:
  - Dùng 1 stack, duyệt dãy a[] và push từng phần tử vào
  - Nếu phần tử định push vào == peak của stack: push bình thường
  - Nếu phần tử định push vào != peak của stack: remove cả 2
  - Phần tử cuối cùng trong stack là candidate
  - Duyệt lại dãy a[], đếm số phần tử = candidate. Nếu count > n/2 thì candidate đó là leader. Bằng ko thì dãy ko có leader
  - Xem chi tiết code [tại đây](./l8/Dominator.java)

## Lesson 9: Max slice

Đây chính là bài tìm dãy con lớn nhất, bài đầu tiên ngày xưa học môn CTDL&GT. Có thể dùng chia để trị hoặc quy hoạch động (DP) để giải. Xem code [tại đây](./l9/MaxSliceSum.java)

Ý tưởng của DP áp dụng cho bài này chính là: tìm dãy con lớn nhất kết thúc tại a[i], với i = [0...n-1]. Duyệt tới khi i = n-1 là thu được kq bài toán

Có 1 bài toán khá thú vị và nâng cao hơn, cũng liên quan đến max slice, nhưng ta phải dùng DP cho cả 2 chiều:

- Tìm dãy con lớn nhất KÉT THÚC tại a[i-1]
- Tìm dãy con lớn nhất BẮT ĐẦU tại a[i+1]
- Trong đó: i = [0...n-1]

Cụ thể xem thêm [tại đây](./l9/MaxDoubleSliceSum.java)

## Lesson 10: Prime numbers

Bài toán đếm số các thừa số (factor) của một số, factor còn gọi là ước số. Việc đếm factor khá đơn giản, nếu số X = A \* B thì cả A và B đều là thừa số, do đó chỉ cần tìm số lượng factor từ 0 -> sqrt(X), rồi nhân 2 là được. Nhưng nhớ check nếu X là số chính phương thì kq cuối cùng phải trừ đi 1

Cụ thể xem thêm [tại đây](./l10/CountFactors.java)

Tương tự, việc check 1 số X có là số nguyên tố hay ko, chỉ cần check từ 0 -> sqrt(X). Nếu trong khoảng đó X có factor > 1 thì KHÔNG là số nguyên tố

## Lesson 14: Binary search

Tìm kiếm nhị phân khá đơn giản và quen thuộc rồi. Nhưng trong phần này có 1 bài dùng BS ko phải để search key, mà là đếm số lượng thoả mãn điều kiện bài toán

Cụ thể xem thêm [tại đây](./l14/MinMaxDivision.java)

## Lesson 15: Caterpillar method

Pattern này khá giống với Two Pointers, Sliding Window bên [leetcode](../../leetcode/README.md)

Idea: check elements in an array giống với việc con sâu bướm di chuyển. Con sâu bướm chui qua array, tại mỗi vị trí, nó có thể di chuyển về phía trước từ phía đầu hoặc đuôi (giống như sling window: nó có thể mở rộng về phía trước hoặc co dãn từ phía sau). Ex:

```java
public int solution(int[] a) {
    int back = 0;
    int front = a.length - 1;
    while (back <= front) {
        // chỗ này tuỳ vào đề bài để tính toán, có thể s = sum(a[back] -> a[front])...
        int s = a[back] + a[front];
        if (isValid(s))
            back++; // sâu bướm (window) di chuyển (mở rộng) về phía trước
        else
            front--; // sâu bướm (window) co lại từ phía sau
    }
}
```

## Lesson 17: Dynamic programming

Vui lòng xem bên [leetcode](../../leetcode/README.md#10-dynamic-programming)

# Kết luận: hãy học chủ yếu từ leetcode

Tóm lại bên codility này ko có nhiều pattern mới so với leetcode, chỉ có prefix sum thôi, do bên leetcode pattern này yêu cầu account premium mới xem được
