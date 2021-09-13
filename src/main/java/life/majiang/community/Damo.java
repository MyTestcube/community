package life.majiang.community;

import java.util.Scanner;

public class Damo {
    public static void main(String[] args) {
        String s1, s2;
        Scanner scanner = new Scanner(System.in);
        System.out.println("输入");
        s1 = scanner.nextLine();
        s1.replace(" ","");
        char[] num;
        num = s1.toCharArray();

        int[] a = new int[num.length];
        for (int i = 0; i < num.length; i++) {
            a[i] = Integer.parseInt(num[i] + "");
        }
        
    }
}
