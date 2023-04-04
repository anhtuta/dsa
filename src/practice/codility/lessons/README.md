# Codility lesson

Ref: https://app.codility.com/programmers/lessons/

Note: các bài nếu ko ghi level thì default là Easy

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

## 2. 1 số test case đặc biệt

- Mảng gồm toàn các số giống nhau
- Mảng gồm toàn số 0
- Mảng gồm toàn số Integer.MAX_VALUE
- Mảng rỗng
- Mảng gồm toàn số âm
- Input = max giới hạn cho trong đề bài
