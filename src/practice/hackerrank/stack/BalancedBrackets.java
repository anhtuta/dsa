package practice.hackerrank.stack;

/**
 * Problem: https://www.hackerrank.com/challenges/balanced-brackets/problem
 * (Medium, but in my opinion, this one is easy!!!)
 * Bài toán check chuỗi dấu ngoặc có hợp lệ hay ko: cho 1 chuỗi chỉ gồm các ký tự dấu ngoặc như sau:
 * ( ) { } [ và ]. Check xem chuỗi đó có hợp lệ hay ko
 * 
 * There are three types of matched pairs of brackets: [], {}, and ().
 * 
 * A matching pair of brackets is not balanced if the set of brackets it encloses are not matched.
 * For example, {[(])} is not balanced because the contents in between { and } are not balanced. The
 * pair of square brackets encloses a single, unbalanced opening bracket, (, and the pair of
 * parentheses encloses a single, unbalanced closing square bracket, ].
 * 
 * By this logic, we say a sequence of brackets is balanced if the following conditions are met:
 * - It contains no unmatched brackets.
 * - The subset of brackets enclosed within the confines of a matched pair of brackets is also a
 * matched pair of brackets.
 * 
 * Cách làm: dùng stack để check, quá easy!
 * 
 * @author tatu
 *
 */
public class BalancedBrackets {

    public static String isBalanced(String s) {
        char[] stack = new char[1000];
        int top = -1;   // top of stack
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case '(':
                case '[':
                case '{':
                    stack[++top] = s.charAt(i);
                    break;
                case ')':
                    if (top < 0 || stack[top] != '(')
                        return "NO";
                    else
                        top--;
                    break;
                case ']':
                    if (top < 0 || stack[top] != '[')
                        return "NO";
                    else
                        top--;
                    break;
                case '}':
                    if (top < 0 || stack[top] != '{')
                        return "NO";
                    else
                        top--;
                    break;

                default:
                    break;
            }
        }

        return top == -1 ? "YES" : "NO";
    }

    public static void main(String[] args) {
        String s1 = "{{[[(())]]}}";
        System.out.println(isBalanced(s1));

        String s2 = "{[(])}";
        System.out.println(isBalanced(s2));
    }
}
