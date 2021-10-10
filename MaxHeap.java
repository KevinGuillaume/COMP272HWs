
import java.util.*;
public class MaxHeap<E extends Comparable<E>> extends ArrayList<E>   {
    // construct an empty Heap using ArrayList
    // with root at index 0.
    // don't use binary tree for implementing the heap.
    
    ArrayList<E> heap;
    
    public MaxHeap(){
       heap = new ArrayList<>(1);
       
    }
    // returns max value
    public E findMax() {
        return heap.get(0);
    }
    
    // adds a new value to the heap at the end of the Heap and 
    // adjusts values up to the root to ensure Max heap property is satisfied.
    // parent of node at i is given by the formula (i-1)/2
    // throw appropriate exception
    public void addHeap(E val) {
         if(heap.size()==0){
                heap.add(val);
                return;
         }
         //add to end
         heap.add(val);
            
            
         addHeapHelper(heap.size(),heap.size()-1);
            
        
    }
    
    public void addHeapHelper(int size, int curr){
            int parentIndex = (curr-1)/2;
            //if previous node exists
            if(heap.get(parentIndex) != null){
            
                //now check if added val is greater than previous; If it is then it means swap
                if(heap.get(curr).compareTo(heap.get(parentIndex)) > 0){
                    E val = heap.get(parentIndex);
                    E val2 = heap.get(curr);
                    heap.set(curr,val);
                    heap.set(parentIndex,val2);
                
                    addHeapHelper(size,parentIndex);
                }
            }
    
    }
    
    //returns the max value at the root of the heap by swapping the last value 
    // and percolating the value down from the root to preserve max heap property
    // children of node at i are given by the formula 2i+1,2i+2, to not exceed the
    // bounds of the Heap index, namely, 0 ... size()-1.
    // throw appropriate exception
    public E removeHeap() {
        if(heap.size() > 0){
        E lastVal = heap.get(heap.size()-1);
        E head = heap.get(0);
        heap.set(0,lastVal);
        
        //gets rid of the end but the if case makes sure it doesn't remove if it's the last one
        heap.remove(heap.size()-1);
        removeHeapHelper(heap.size(),0);
        
        
        return head;
        
       
        }
        else{
            System.out.println("Heap is empty");
            return null;
        }
    }
    public void removeHeapHelper(int size, int pos){
        //index of largest value
        int biggestVal = pos;
        //children index of value
        int left = (2*pos) + 1;
        int right = (2*pos) + 2;
        
        
        if(right < size && heap.get(right).compareTo(heap.get(biggestVal)) > 0){
            biggestVal = right;
        }
        
        if(left < size && heap.get(left).compareTo(heap.get(biggestVal)) > 0){
            biggestVal = left;
        }
        
        //check if it follows heap rule with biggestVal index at beginning
        if( biggestVal != pos){
            E temp = heap.get(pos);
            heap.set(pos,heap.get(biggestVal));
            heap.set(biggestVal,temp);
            
            removeHeapHelper(size,biggestVal);
        
        
        }
    
    }
    
    // takes a list of items E and builds the heap and then prints 
    // decreasing values of E with calls to removeHeap().  
    public void heapSort(List<E> list){
        MaxHeap<E> res = new MaxHeap<E>();
        Iterator<E> itr = list.iterator();
        while(itr.hasNext()){
            res.addHeap(itr.next());
        
        }
        while(res.heap.size() >0){
            System.out.print(res.removeHeap() + " " );
            
        }
        
        System.out.println();
    }
    
    // merges the other maxheap with this maxheap to produce a new maxHeap.  
    public void heapMerge(MaxHeap<E> other){
        ArrayList<E> combined = new ArrayList<E>();
        //Fill combined array
        for(E val: heap){
            combined.add(val);
        }
        for(E val2: other.heap){
            combined.add(val2);
        }
        //new heap to return using same logic as buildheap
        MaxHeap<E> res = new MaxHeap<>();
        Iterator<E> itr = combined.iterator();
        while(itr.hasNext()){
            res.addHeap(itr.next());
            
        }

    }
    
    //takes a list of items E and builds the heap by calls to addHeap(..)
    public void buildHeap(List<E> list) {
        Iterator<E> itr = list.iterator();
        while(itr.hasNext()){
            addHeap(itr.next());
        
        }
    }
    
}
