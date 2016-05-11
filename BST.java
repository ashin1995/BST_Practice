/*

  SOME PRACTICE WITH BINARY SEARCH TREES
  	Andrew Shin
*/


import java.util.*;

public class BST
{
    // component node class
    private static class Node
    {
        int key;
        Node left;
        Node right;
        
        Node() { super(); }
        
        Node(int key) { super(); this.key = key; }
    }
    
    // keeps track of root and size
    private Node root;
    private int size;
    
    // default constructor    
    public BST() { super(); }
    
    
    // method to access number of keys in the tree
    public int size()
    {
        return size;
    }
    
    public boolean contains (int key)
    {
        Node n = root;
        while (n != null) {
            if (n.key == key) return true;
            if (key < n.key)
                n = n.left;
            else
                n = n.right;
        }
        
        return false;
    }
    
    // prints a preorder traversal, with X for null pointers
    public void preorder()
    {
        preorder(root);
        System.out.println();
    }
    
    // recursive helper for preorder traversal
    private void preorder(Node root)
    {
        if (root == null) {
            System.out.print("X ");
            return;
        }
            
        System.out.print(root.key);
        System.out.print(' ');
        
        preorder(root.left);
        preorder(root.right);
    }
    
    


    /************
    
    for testing
    
    ************/
    
    public static void main (String[] args)
    {   
    }
  
    
    public int LCA (int x, int y)
    {
        if (!contains(x) || !contains(y)) 
            throw new NoSuchElementException();
        return LCA(root, x, y).key;
    }
    
    
    public ArrayList<Integer> findBetween (int x, int y)
    {
        if (!contains(x) || !contains(y)) 
            throw new NoSuchElementException();
        ArrayList<Integer> keys = new ArrayList<Integer>();
        findBetween(this.root, keys, x, y);
        return keys;
    } 
    
    
    public BST (int[] arr)
    {
        if(!isValidArr(arr))
            throw new IllegalArgumentException();
        root = BSThelper(arr, 0, arr.length - 1);
    }
    
    
    public static BST merge (BST t1, BST t2)
    {
        int[] tree1 = new int[t1.size()]; // initialized array for t1
        int[] tree2 = new int[t2.size()]; // initialized array for t2
        int[] counter = {0};
        t1.convertBST(tree1, t1.root, counter);
        counter[0] = 0;
        t2.convertBST(tree2, t2.root, counter); // load respective arrays with tree values
        int[] arr = mergeArr(tree1, tree2); // merges array witout duplicates
        return new BST(arr);
    }

  
    public void merge(BST other) 
    {
    	// merges two BST's together 
        BST t = merge(this, other);
        this.root = t.root;
        this.size = t.size;
        t = null;
    }

    /********
    /
    /   HELPER FUNCTIONS
    /
    ********/

    private static boolean isValidArr(int[] arr){ // checks to see if arr is sorted in ascending order & contains no duplicates
        HashSet<Integer> dupes = new HashSet<Integer>();
        for (int i = 0; i < arr.length - 1; i++){
            if (arr[i] >= arr[i + 1])
                return false;
            if (dupes.contains(arr[i]))
                return false;
            dupes.add(arr[i]);
        }
        return true;
    }

    private Node BSThelper(int[] arr, int start, int end){
        if (start > end)
            return null; 
        int mid = (start + end)/2;
        Node node = new Node(arr[mid]);
        size++;
        node.left = BSThelper(arr, start, mid - 1);
        node.right = BSThelper(arr, mid + 1, end);
        return node;
    }

    private Node LCA(Node node, int x, int y){
        if (node == null)
            return null;
        if (x < node.key && y < node.key){
            return LCA(node.left, x, y);
        }
        if (x > node.key && y  > node.key){
            return LCA(node.right, x, y);
        }
        return node;
    }

    private void findBetween(Node node, ArrayList<Integer> keys, int x, int y){
        if (node == null)
            return;
        if (node.key < Math.min(x, y))
            findBetween(node.right, keys, x, y);
        else if (Math.min(x, y) <= node.key && Math.max(x, y) >= node.key){
            findBetween(node.left, keys, x, y);
            keys.add(node.key);
            findBetween(node.right, keys, x, y);
        }
        else
            findBetween(node.left, keys, x, y);
    }

    private void convertBST(int[] arr, Node node, int[] counter){
        if (node.left != null)
            convertBST(arr, node.left, counter);
        arr[counter[0]] = node.key;
        counter[0] += 1;
        if (node.right != null) 
            convertBST(arr, node.right, counter);
    }

    private static int[] mergeArr(int[] a, int[] b){ // merges arrays
        int[] arr = new int[a.length + b.length];
        int i = 0; int j = 0; int idx = 0;
        while (i < a.length && j < b.length) {
            if (a[i] < b[j]) {
                arr[idx] = a[i];
                i++;
            }
            else if (b[j] < a[i]) {
                arr[idx] = b[j];
                j++;
            }
            else {
                arr[idx] = a[i];
                i++; j++;
            }
            idx++;
        }
        while (i < a.length) { // adds leftovers
            arr[idx] = a[i];
            i++;
            idx++;
        }

        while (j < b.length) { // adds leftovers 
            arr[idx] = b[j];
            j++;
            idx++;
        }
        if (!isValidArr(arr)) // if duplicates exist, array wont be in order
            return resizeArr(arr);
        return arr;
    }

    private static int[] resizeArr(int[] arr){ // returns array with end cut off
        int shave = 0;
        int[] newArr;
        for (int i = 0; i < arr.length - 1; i++){
            if (arr[i] >= arr[i + 1])
                shave++;
        }
        newArr = new int[arr.length - shave];
        for (int i = 0; i < newArr.length; i++){
            newArr[i] = arr[i];
        }
        return newArr;
    }
}