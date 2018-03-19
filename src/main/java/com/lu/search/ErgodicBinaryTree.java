package com.lu.search;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 遍历二叉树
 */
public class ErgodicBinaryTree {
    // 将遍历结果存入List中
    private static List<Node> list = new ArrayList<>();

    /**
     * 树
     */
    private static class Tree {
        private Node root;
        public Tree() {
            init();
        }

        private void init() {
            // 从叶节点到根
            Node D = new Node("D", null, null);
            Node E = new Node("E", null, null);
            Node B = new Node("B", D, E);
            Node F = new Node("F", null, null);
            Node C = new Node("C", F, null);
            Node A = new Node("A", B, C);
            root = A;
        }
    }

    /**
     * 节点类
     */
    private static class Node {
        private String data;
        private Node lChild; // 指向左子节点的指针
        private Node rChild; // 指向右子节点的指针

        public Node(String data, Node lChild, Node rChild) {
            this.data = data;
            this.lChild = lChild;
            this.rChild = rChild;
        }
    }

    /**
     * 前序遍历 （根-左-右）
     * @param node 当前节点
     */
    private static void preOrder(Node node) {
        list.add(node);// 将根节点存入list
        if (node.lChild != null) {
            preOrder(node.lChild);
        }
        if (node.rChild != null) {
            preOrder(node.rChild);
        }
    }

    /**
     * 中序遍历 （左-根-右）
     * @param node 节点
     */
    private static void inOrder(Node node) {
        if (node.lChild != null) {
            inOrder(node.lChild);
        }
        list.add(node);
        if (node.rChild != null) {
            inOrder(node.rChild);
        }
    }

    /**
     * 后序遍历 （左-右-根）
     * @param node
     */
    private static void postOrder(Node node) {
        if (node.lChild != null) {
            postOrder(node.lChild);
        }
        if (node.rChild != null) {
            postOrder(node.rChild);
        }
        list.add(node);
    }

    public static void main(String[] args) {
        Tree tree = new Tree();
//        preOrder(tree.root);
//        inOrder(tree.root);
        postOrder(tree.root);
        for (Node node : list) {
            System.out.println(node.data);
        }
    }
}
