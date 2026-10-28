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
          String[] t = { traversal1, traversal2, traversal3 };
          String[] names = { "Inorder", "Preorder", "Postorder" };

          // try every way of picking which string is inorder (i), preorder (p), postorder (q)
          for (int i = 0; i < 3; i++) {
               for (int p = 0; p < 3; p++) {
                    int q = 3 - i - p;
                    if (p == i || q < 0 || q == i || q == p) {
                         continue;
                    }

                    // build the BST by inserting the preorder values in order
                    Node root = null;
                    for (String s : t[p].split("-")) {
                         root = insert(root, Integer.parseInt(s));
                    }

                    // the tree's traversals must match the given strings exactly
                    if (inorder(root).equals(t[i]) && preorder(root).equals(t[p])
                              && postorder(root).equals(t[q])) {
                         String[] roles = new String[3];
                         roles[i] = names[0];
                         roles[p] = names[1];
                         roles[q] = names[2];
                         return "Traversal 1 - " + roles[0] + ", Traversal 2 - " + roles[1]
                                   + ", Traversal 3 - " + roles[2];
                    }
               }
          }
          return "Invalid traversals";
     }

     static class Node {
          int val;
          Node left, right;

          Node(int val) {
               this.val = val;
          }
     }

     static Node insert(Node root, int v) {
          if (root == null) {
               return new Node(v);
          }
          if (v < root.val) {
               root.left = insert(root.left, v);
          } else if (v > root.val) {
               root.right = insert(root.right, v);
          }
          return root;
     }

     // each traversal returns the nodes joined by "-", e.g. "1-2-3"
     static String inorder(Node n) {
          if (n == null) {
               return "";
          }
          return join(inorder(n.left), "" + n.val, inorder(n.right));
     }

     static String preorder(Node n) {
          if (n == null) {
               return "";
          }
          return join("" + n.val, preorder(n.left), preorder(n.right));
     }

     static String postorder(Node n) {
          if (n == null) {
               return "";
          }
          return join(postorder(n.left), postorder(n.right), "" + n.val);
     }

     static String join(String a, String b, String c) {
          String result = "";
          for (String s : new String[] { a, b, c }) {
               if (!s.isEmpty()) {
                    result += result.isEmpty() ? s : "-" + s;
               }
          }
          return result;
     }
}
