public class BinarySearchTree<E extends Comparable<E>> extends BinaryTree<E> {
    
    BinaryTree<E> tree;
    
    
     public BinarySearchTree() {
        tree = new BinaryTree<>();
    }
    public BinarySearchTree(E val) {
        tree = new BinaryTree(val);
        
    }
    
// returns true if BST has val else false.
    public boolean contains (E val) {
        return checkIf(tree.root, val);
        
    }
    
    public boolean checkIf(Node<E> n, E val){
      int compVal = val.compareTo(n.getInfo());
      
      if(compVal == 0) return true;
      
      //Check if there is a left node since val is less than the root t
      if(compVal < 0 && n.getLeft() !=null){
        return checkIf(n.getLeft(), val); 
      }
      //Check if there is a right node is val is great than the root
      if(compVal > 0 && n.getRight() !=null)
        return checkIf(n.getRight(), val);
    
      return false;
    
    
    }
// inserts val at the right place satisfying search tree properties, should handle if the tree is empty
// if value is already there then don’t insert it again
    public void insert(E val) {
        //if its empty
        if(tree.size == 0){
            tree.root = new Node(val);
            tree.size++;
        }
        else{
            Node<E> current = tree.root;
            //don't have this yet since starting at root
            Node<E> parent =null;
            
            //while a node exists...
            //this loop is going to take us to the necessary location to insert after
            while(current !=null){
                parent = current;
                
                
                if(val.compareTo(current.getInfo()) < 0){
                    current = current.getLeft();
                }
                else if(val.compareTo(current.getInfo()) == 0){ //if value is present
                    return;
                }
                else{
                    current = current.getRight();
                }
            
            }
            
            //now to insert left or right
            if(val.compareTo(parent.getInfo()) < 0)
                tree.addLeft(parent,val);
            
            if(val.compareTo(parent.getInfo()) > 0)
                tree.addRight(parent,val);
            
        }
        
    }
         

// returns the minimum value stored in the tree
    public E findMin() {
        Node<E> current = tree.root;
        //while there is a left node to check
        while (current.getLeft() != null){
        if(current.getLeft() != null){
           current = current.getLeft(); 
           System.out.println(current.getInfo());
        }
        }
        
        return current.getInfo();
    }   

// returns the maximum value stored in the tree
    public E findMax() {
        Node<E> current = tree.root;        
        while (current.getRight() != null){
        if(current.getRight() != null)
           current = current.getRight(); 
        }
        
        return current.getInfo();
    }


 
    public static void main(String[] args) {
        BinarySearchTree<Integer> bt= new BinarySearchTree<>();
        bt.insert(5);
        bt.insert(10);
        bt.insert(3);
        bt.insert(20);
        bt.insert(8);
        bt.insert(4);
    }
    
             
}