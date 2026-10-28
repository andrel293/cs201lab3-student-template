import java.util.Arrays;

public class Q1Test {
     public static void main(String[] args) {

          String result, traversal1, traversal2, traversal3;

          traversal1 = "1-2";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "2-1-3";
          traversal3 = "1-3-2";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "1-2-3";
          traversal2 = "3-2-1";
          traversal3 = "2-3-1";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println("Expected Result : Invalid traversals");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "3-1-2-5-4";
          traversal2 = "2-1-4-5-3";
          traversal3 = "1-2-3-4-5";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Preorder, Traversal 2 - Postorder, Traversal 3 - Inorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();

          traversal1 = "10-20-30-40-50";
          traversal2 = "20-10-30-40-50";
          traversal3 = "10-50-40-30-20";
          result = verify(traversal1, traversal2, traversal3);
          System.out.println("Traversal 1 : " + traversal1);
          System.out.println("Traversal 2 : " + traversal2);
          System.out.println("Traversal 3 : " + traversal3);
          System.out.println(
                    "Expected Result : Traversal 1 - Inorder, Traversal 2 - Preorder, Traversal 3 - Postorder");
          System.out.println("  Actual Result : " + result);
          System.out.println();
     } 

      // write your codes here
     public static String verify(String traversal1, String traversal2, String traversal3){
          String[] input = {traversal1, traversal2, traversal3};
          int[][] a = new int[3][];
          for (int i = 0; i < 3; i++) {
               String[] parts = input[i].split("-");
               a[i] = new int[parts.length];
               for (int j = 0; j < parts.length; j++) a[i][j] = Integer.parseInt(parts[j].trim());
          }
          int n = a[0].length;
          if (a[1].length != n || a[2].length != n) return "Invalid traversals";

          String[] names = {"Inorder", "Preorder", "Postorder"};
          int[][] perms = {{0,1,2}, {0,2,1}, {1,0,2}, {1,2,0}, {2,0,1}, {2,1,0}};

          for (int[] p : perms) {
               int[] in = a[p[0]], pre = a[p[1]], post = a[p[2]];

               // inorder must be the sorted version of the same values
               int[] sorted = pre.clone();
               Arrays.sort(sorted);
               if (!Arrays.equals(sorted, in)) continue;

               // build BST by inserting preorder values; node i = pre[i]
               int[] left = new int[n], right = new int[n];
               Arrays.fill(left, -1);
               Arrays.fill(right, -1);
               for (int i = 1; i < n; i++) {
                    int cur = 0;
                    while (true) {
                         if (pre[i] < pre[cur]) {
                              if (left[cur] == -1) { left[cur] = i; break; }
                              cur = left[cur];
                         } else {
                              if (right[cur] == -1) { right[cur] = i; break; }
                              cur = right[cur];
                         }
                    }
               }

               // preorder check: visiting the tree root-left-right must give nodes 0,1,2,...
               int[] stack = new int[n];
               int top = 0, count = 0;
               boolean ok = true;
               stack[top++] = 0;
               while (top > 0) {
                    int c = stack[--top];
                    if (c != count++) { ok = false; break; }
                    if (right[c] != -1) stack[top++] = right[c];
                    if (left[c] != -1) stack[top++] = left[c];
               }
               if (!ok) continue;

               // postorder check: visit root-right-left, which is postorder reversed
               int k = 0;
               stack[top++] = 0;
               while (top > 0) {
                    int c = stack[--top];
                    if (pre[c] != post[n - 1 - k]) { ok = false; break; }
                    k++;
                    if (left[c] != -1) stack[top++] = left[c];
                    if (right[c] != -1) stack[top++] = right[c];
               }
               if (!ok) continue;

               String[] label = new String[3];
               label[p[0]] = names[0];
               label[p[1]] = names[1];
               label[p[2]] = names[2];
               return "Traversal 1 - " + label[0] + ", Traversal 2 - " + label[1] + ", Traversal 3 - " + label[2];
          }
          return "Invalid traversals";
     }
}