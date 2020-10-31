/**
 * @author lihai
 * @date 2020/9/19-21:37
 */
public class Main {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        for (int i = 0; i <5 ; i++) {
            stack.push(i);
        }

        while(!stack.isEmpty()){
            System.out.println(stack.pop());
        }

    }
}
