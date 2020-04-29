package DataStructures.Trees;

/**
 * This entire class is used to build a Binary Tree data structure.
 * There is the Node Class and the Tree Class, both explained below.
 */


import org.junit.Test;

import java.util.Random;

/**
 * A binary tree is a data structure in which an element
 * has two successors(children). The left child is usually
 * smaller than the parent, and the right child is usually
 * bigger.
 *
 * @author Unknown
 *
 */
public class BinaryTree {

    /**
     * This class implements the nodes that will go on the Binary Tree.
     * They consist of the data in them, the node to the left, the node
     * to the right, and the parent from which they came from.
     *
     * @author Unknown
     *
     */
    class Node {
        /** Data for the node */
        public int data;
        /** The Node to the left of this one */
        public Node left;
        /** The Node to the right of this one */
        public Node right;
        /** The parent of this node */
        public Node parent;

        /**
         * Constructor of Node
         *
         * @param value Value to put in the node
         */
        public Node(int value) {
            data = value;
            left = null;
            right = null;
            parent = null;
        }
    }


    /** The root of the Binary Tree */
    private Node root;

    /**
     * Constructor
     */
    public BinaryTree() {
        root = null;
    }

    /**
     * Method to find a Node with a certain value
     * 查找某个结点
     * @param key Value being looked for
     * @return The node if it finds it, otherwise returns the parent
     */
    public Node find(int key) {
        Node current = root; //从根节点开始查找
        while (current != null) {
            if (key < current.data) { //如果要查找的值小于当前结点
                if (current.left == null) //如果当前结点的左孩子为null,表示要查找的结点不存在，返回当前结点（要查找的结点的父结点），
                    return current;    //The key isn't exist, returns the parent
                current = current.left; //当前结点的左结点不为null,将当前结点的左结点复制给当前结点，继续循环
            } else if (key > current.data) { //如果要查找的值大于当前结点
                if (current.right == null) //如果当前结点的右孩子为null,表示要查找的结点不存在，返回当前结点（要查找的结点的父结点），
                    return current;
                current = current.right; //当前结点的左结点不为null,将当前结点的右结点赋值给当前结点，继续循环
            } else {    // If you find the value return it
                return current; //如果当前结点的值等于要查找的值，返回当前结点
            }
        }
        return null;
    }

    /**
     * Inserts certain value into the Binary Tree
     *
     * @param value Value to be inserted
     */
    public void put(int value) { //插入一个结点到二叉树
        Node newNode = new Node(value); //用当前值构建一个结点
        if (root == null)
            root = newNode;//如果根节点为null,表示第一次插入，将改结点赋值给根结点
        else {
            //This will return the soon to be parent of the value you're inserting
            Node parent = find(value); //查找要插入的结点，如果要插入的结点不存在，则返回父结点的位置

            //This if/else assigns the new node to be either the left or right child of the parent
            if (value < parent.data) {//如果当前结点的值小于parent结点的值,
                parent.left = newNode; //将其赋值给parent的左结点
                parent.left.parent = parent; //设置插入子节点的父结点为parent
                return;
            } else {//如果要插入的结点的值大于（或等于）parent的值
                parent.right = newNode; //将其赋值给parent的右结点
                parent.right.parent = parent; //设置插入子节点的父结点为parent
                return;
            }
        }
    }

    /**
     * Deletes a given value from the Binary Tree
     *
     * @param value Value to be deleted
     * @return If the value was deleted
     */
    public boolean remove(int value) {
        //temp is the node to be deleted
        Node temp = find(value);//找到存储该值的结点或者其父结点

        //If the value doesn't exist
        if (temp.data != value) //表示未找到存储该值得结点
            return false;

        //No children
        if (temp.right == null && temp.left == null) {//如果该结点没有左右结点
            if (temp == root) //如果存储该值的是根结点，将根节点设置为null
                root = null;

                //This if/else assigns the new node to be either the left or right child of the parent
            else if (temp.parent.data < temp.data) //判断要删除的结点是左节点还是右节点
                temp.parent.right = null; //由于要删除的结点没有子节点，所以可以直接将其的父结点的右节点置为null,
            else
                temp.parent.left = null; //由于要删除的结点没有子节点，所以可以直接将其的父结点的左节点置为null,
            return true;
        }

        //Two children
        else if (temp.left != null && temp.right != null) {
            Node successor = findSuccessor(temp); //找到该结点的下一个结点，按照值来排序，

            //The left tree of temp is made the left tree of the successor
            successor.left = temp.left; //将要删除的结点的左结点赋值给其后继结点的左结点
            successor.left.parent = successor; //将删除的结点的左结点的父结点设置为要删除结点的后继结点

            //If the successor has a right child, the child's grandparent is it's new parent
            if(successor.parent!=temp){ //如果后继结点的父结点不为要删除的结点
                if(successor.right!=null){ //如果后继结点的右节点不为null
                    successor.right.parent = successor.parent; //把后继结点的右节点的父结点设置为后继结点的父结点
                    successor.parent.left = successor.right; //将后继结点的父结点的左结点设置为后继结点的右节点
                    successor.right = temp.right; //将后继结点的右结点设置为要删除结点的右结点
                    successor.right.parent = successor; //将要删除结点的右结点的父结点设置为后继结点
                }else{ //后继结点没有右结点
                    successor.parent.left=null; //将后继结点的父结点的左结点设置为null
                    successor.right=temp.right; //将要删除结点的右节点设置为后继结点的右节点
                    successor.right.parent=successor; //把要删除结点的右节点的父结点设置为后继结点
                }
            }

            if (temp == root) { //如果要删除的是根节点，将后继结点设置为根节点
                successor.parent = null;
                root = successor;
                return true;
            }

            //If you're not deleting the root
            else { //如果要删除的不是根节点，把后继结点的父结点设置为要删除结点的父结点
                successor.parent = temp.parent;

                //This if/else assigns the new node to be either the left or right child of the parent
                if (temp.parent.data < temp.data) //判断要删除结点是左结点还是右结点
                    temp.parent.right = successor; //如果是右结点，则把要删除结点的父结点的右节点设置为后继结点
                else
                    temp.parent.left = successor;  //如果是左结点，则把要删除结点的父结点的左节点设置为后继结点
                return true;
            }
        }
        //One child
        else { //要删除的结点只有一个子结点
            //If it has a right child
            if (temp.right != null) { //要删除的结点只有一个子结点，且子节点为右结点
                if (temp == root) { //如果要删除的是根结点
                    root = temp.right; //则吧根结点的右结点设置为根结点
                    return true;
                }

                temp.right.parent = temp.parent; //把要删除结点的父结点赋值给要删除结点的右节点的父结点，相当于把自己移除。

                //Assigns temp to left or right child
                if (temp.data < temp.parent.data) //判断删除的结点是左结点还是右结点
                    temp.parent.left = temp.right; //如果是左结点，则把要删除的右结点赋值给其父结点的左结点
                else
                    temp.parent.right = temp.right; //如果是右结点，则把要删除的右节点赋值给其父结点的右节点
                return true;
            }
            //If it has a left child
            else { //要删除的结点只有一个子结点，且子节点为左结点
                if (temp == root) { //如果要删除的是根结点
                    root = temp.left; //则吧根结点的左结点设置为根结点
                    return true;
                }

                temp.left.parent = temp.parent;  //把要删除结点的父结点赋值给要删除结点的左节点的父结点，相当于把自己移除。

                //Assigns temp to left or right side
                if (temp.data < temp.parent.data) //判断删除的结点是左结点还是右结点
                    temp.parent.left = temp.left; //如果是左结点，则把要删除的左结点赋值给其父结点的左结点
                else
                    temp.parent.right = temp.left;  //如果是右结点，则把要删除的左节点赋值给其父结点的右节点
                return true;
            }
        }
    }

    /**
     * This method finds the Successor to the Node given.
     * Move right once and go left down the tree as far as you can
     *
     * @param n Node that you want to find the Successor of
     * @return The Successor of the node
     */
    public Node findSuccessor(Node n) { //找到该结点的后继结点，按照值排序
        if (n.right == null) //如果当前的右结点为null，则返回当前结点
            return n;
        Node current = n.right;  //将当前结点的右结点赋值给curren
        Node parent = n.right;  //将当前结点的右结点同时赋值给parent
        while (current != null) { //如果左结点为null，则表示已经找到了后继结点，后继结点为parent
            parent = current;
            current = current.left; //将左结点赋值给current
        }
        return parent; //返回后继结点
    }

    /**
     * Returns the root of the Binary Tree
     *
     * @return the root of the Binary Tree
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Prints leftChild - root - rightChild
     *
     * @param localRoot The local root of the binary tree
     */
    public void inOrder(Node localRoot) {
        if (localRoot != null) {
            inOrder(localRoot.left); //左子树
            System.out.print(localRoot.data + " ");
            inOrder(localRoot.right); //右子树
        }
    }

    /**
     * Prints root - leftChild - rightChild
     *
     * @param localRoot The local root of the binary tree
     */
    public void preOrder(Node localRoot) {
        if (localRoot != null) {
            System.out.print(localRoot.data + " ");
            preOrder(localRoot.left);
            preOrder(localRoot.right);
        }
    }

    /**
     * Prints rightChild - leftChild - root
     *
     * @param localRoot The local root of the binary tree
     */
    public void postOrder(Node localRoot) {
        if (localRoot != null) {
            postOrder(localRoot.left);
            postOrder(localRoot.right);
            System.out.print(localRoot.data + " ");
        }
    }

    @Test
    public void test(){
        BinaryTree binaryTree = new BinaryTree();
        Random random = new Random();

        for (int i = 0; i < 100000; i++) {
            int i1 = random.nextInt(50000);
            System.out.print(i1 + " ");
            binaryTree.put(i1);
        }
        System.out.println();

//      binaryTree.preOrder(binaryTree.getRoot());

        System.out.println();
        binaryTree.inOrder(binaryTree.getRoot());

        System.out.println();
//        binaryTree.postOrder(binaryTree.getRoot());
    }

    @Test
    public void testDelete(){
        BinaryTree binaryTree = new BinaryTree();
        binaryTree.put(15);
        binaryTree.put(10);
        binaryTree.put(8);
        binaryTree.put(6);
        binaryTree.put(12);
        binaryTree.put(11);
        binaryTree.put(13);
        binaryTree.put(17);

        binaryTree.inOrder(binaryTree.getRoot());

        binaryTree.remove(10);
        System.out.println();

        binaryTree.inOrder(binaryTree.getRoot());


    }

}
