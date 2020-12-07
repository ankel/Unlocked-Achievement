import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;

public class LeetTree {
    public static void main(String[] args) {
        TreeNode tree = deserialize("3,9,20,null,null,15,7,null,null,null,null");
        System.out.println(serialize(tree));
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }
    }

    private static final String NULL = "null";
    private static final String SPLITER = ",";

    public static String serialize(TreeNode root) {
        if (root == null) return null;

        LinkedList<String> fields = new LinkedList<>();
        fields.add(String.valueOf(root.val));
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty()) {
            TreeNode next = q.poll();
            addField(fields, q, next.left);
            addField(fields, q, next.right);
        }

        while(NULL.equals(fields.peekLast())) fields.pollLast();

        return String.join(SPLITER, fields);
    }

    private static void addField(final List<String> fields, final Queue<TreeNode> q, final TreeNode left) {
        if (left == null) {
            fields.add(NULL);
        } else {
            fields.add(String.valueOf(left.val));
            q.add(left);
        }
    }

    public static TreeNode deserialize(String data) {
        if (data == null || data.isEmpty() || NULL.equals(data)) {
            return null;
        }
        Queue<String> fields = new LinkedList<>();
        for (String s : data.split(SPLITER)) {
            fields.offer(s.trim());
        }

        TreeNode root = new TreeNode(Integer.parseInt(Objects.requireNonNull(fields.poll())));

        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);

        while (!q.isEmpty() && !fields.isEmpty()) {
            TreeNode next = q.poll();
            String left = fields.poll();
            if (left != null && !NULL.equals(left)) {
                next.left = new TreeNode(Integer.parseInt(left));
                q.offer(next.left);
            }
            String right = fields.poll();
            if (right != null && !NULL.equals(right)) {
                next.right = new TreeNode(Integer.parseInt(right));
                q.offer(next.right);
            }
        }
        return root;
    }
}
