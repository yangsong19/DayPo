package com.xinlab.blueapple.test;

/**
 * 二叉查找树
 * 
 * @author 马永华
 * 
 */
public class BSTree {

    // 定义二叉树的数据结构
    static class BinaryNode<T> {

        private T element;

        private BinaryNode<T> left;

        private BinaryNode<T> right;

        // 创建一个单节点
        public BinaryNode(T element) {
            this(element, null, null);
        }

        // 创建子节点
        public BinaryNode(T element, BinaryNode<T> left, BinaryNode<T> right) {
            this.element = element;
            this.left = left;
            this.right = right;
        }

    }

    // 插入
    public BinaryNode<Integer> insert(Integer i, BinaryNode<Integer> node) {

        if (node == null) {
            return new BinaryNode<Integer>(i);
        }

        // 如果i 小于当前节点的值，则查找器左子树
        if (i < node.element) {
            node.left = insert(i, node.left);
        }

        // 如果i 大于当前节点的值，则查找其右子树
        if (i > node.element) {
            node.right = insert(i, node.right);
        }

        return node;
    }

    // 查找
    public BinaryNode<Integer> find(int i, BinaryNode<Integer> node) {
        // 如果树为空，则直接返回null
        if (node == null)
            return null;

        if (node.element == i)
            return node;

        if (i < node.element)
            return find(i, node.left);

        if (i > node.element)
            return find(i, node.right);

        System.out.println("这里会执行吗");
        return null;
    }

    // 查询树的最大值
    public BinaryNode<Integer> findMax(BinaryNode<Integer> node) {
        // 如果树为空，则直接返回null
        if (node == null)
            return null;

        if (node.right == null)
            return node;
        else
            return findMax(node.right);
    }

    // 查询树的最小值
    public BinaryNode<Integer> findMin(BinaryNode<Integer> node) {
        // 如果树为空，则直接返回null
        if (node == null)
            return null;

        if (node.left == null)
            return node;
        else
            return findMin(node.left);
    }

    // 获得当前节点的父节点
    public BinaryNode<Integer> findParent(int i, BinaryNode<Integer> node) {

        if (node == null)
            return null;

        // 如果i的值小于
        if (i < node.element) {
            if (node.left != null && i == node.left.element)
                return node;
            else
                return findParent(i, node.left);
        }

        // 如果该值
        if (i > node.element) {
            if (node.right != null && i == node.right.element)
                return node;
            else
                return findParent(i, node.right);
        }

        return null;
    }

    // 删除节点
    public BinaryNode<Integer> delete(int i, BinaryNode<Integer> node) {

        if (node == null)
            return null;

        if (i < node.element) {
            // 如果要删除的节点小于当前节点
            node.left = delete(i, node.left);
        } else if (i > node.element) {
            // 如果要删除的节点大于当前节点
            node.right = delete(i, node.right);
        } else {
            // 要删除的节点等于当前节点
            // 如果该节点有两个子节点
            if (node.left != null && node.right != null) {
                // 找到该节点右子树中最小的节点，将它的值赋给当前节点
                node.element = findMin(node.right).element;
                // 然后递归去删除这个最小的节点
                node.right = delete(node.element, node.right);
            } else {
                // 如果该节点有一个子节点或者没有子节点
                return node = node.left != null ? node.left : node.right;
            }

        }
        return node;
    }

    // 对二叉查找树进行中序输出，输出的是一个从小到大的有序数列
    public void sort(BinaryNode<Integer> node) {

        // 先输出左子树
        if (node.left != null) {
            sort(node.left);
        }

        // 再输出中间
        System.out.print(node.element + "->");

        // 最后输出右子树
        if (node.right != null) {
            sort(node.right);
        }

    }

    public static void main(String args[]) {
        BSTree bst = new BSTree();

        // 插入 6 2 8 1 5 3 4
        BinaryNode<Integer> root = bst.insert(6, null);
        bst.insert(2, root);
        bst.insert(8, root);
        bst.insert(1, root);
        bst.insert(5, root);
        bst.insert(3, root);
        bst.insert(4, root);

        // 查询
//        BinaryNode<Integer> node2 = bst.find(2, root);
//        BinaryNode<Integer> node0 = bst.find(0, root);
//        BinaryNode<Integer> nodeMax = bst.findMax(root);
//        BinaryNode<Integer> nodeMin = bst.findMin(root);

        // 排序
//        bst.sort(root);

        // 删除
//         BinaryNode<Integer> delete4 = bst.delete(4,root);
        // BinaryNode<Integer> delete3 = bst.delete(3,root);
         BinaryNode<Integer> delete2 = bst.delete(2,root);
         bst.sort(root);
         StringBuffer buffer = new StringBuffer();
         buffer.append("a");
         StringBuilder builder = new StringBuilder();
         builder.append("b");
    }
}
