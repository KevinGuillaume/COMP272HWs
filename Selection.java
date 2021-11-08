
import java.util.*;
public class Selection <E extends Comparable<E>> {
int k;
ArrayList<E> input ; // this holds the values of type E from which your code will find kth largest.
// constructors
public  Selection(int kVal,ArrayList<E> inputs){
    k = kVal;
    input = inputs;
}


//quick sort algorithm 
public void quickSort(ArrayList<E> arr, int start, int end) 
    {
        //check for empty or null array
        if (arr == null || arr.size() == 0){
            return;
        }
         
        if (start >= end){
            return;
        }

        int middle = start + (end - start) / 2;
        E pivot = arr.get(middle);
 
        int i = start, j = end;
        while (i <= j) 
        {
            while (arr.get(i).compareTo(pivot) == -1) 
            {
                i++;
            }
            while (arr.get(j).compareTo(pivot) == 1) 
            {
                j--;
            }
            
            if (i <= j) 
            {
                //swap here
                E temporary = arr.get(i);
                arr.set(i,arr.get(j));
                arr.set(j,temporary);
                i++;
                j--;
            }
        }
        if (start < j){
            quickSort(arr,start, j);
        }
        if (end > i){
            quickSort(arr,i, end);
        }
    }


    // algorithm 1 methods -- implement 1B
 public E algo1(){    
    ArrayList<E> temp = new ArrayList<E>();
    for(int i =0; i < k; i++){
        temp.add(input.get(i));
    }
    //sort the subset and now 0 index is smallest since it is asending
    quickSort(temp,0,temp.size()-1);
    
    for(int j = k; j < input.size();j++){
        if(input.get(j).compareTo(temp.get(0)) == 1){
            //Add , then remove, then reorder to corret spot
            temp.remove(0);
            temp.add(input.get(j));
            quickSort(temp,0,temp.size()-1);
        
        }
    }
    
    E kValue = temp.get(0);
    
    return kValue;
 } 
  
  // algorithm 2 methods -- 6A -- change the algorithm to do kth largest not kth smallest that is
  //described here
  public E algo2(){
      
    Queue<E> q = new PriorityQueue<E>(Collections.reverseOrder());
    for(int i =0; i < input.size();i++){
        q.add(input.get(i));
    }
    E kValue = null;
    int count = k;
    while(count > 0){
        kValue= q.poll();
        count--;
    }
    
    return kValue;
    }

    // algorithm 3 methods – 6B
  public E algo3(){
      
    Queue<E> S = new PriorityQueue<E>();
    for(int i =0; i < k; i++){
        S.add(input.get(i));
    }
    //Sk is going to be at the beginning of the list or top of queue
    
    for(int j=k;j<input.size();j++){
        if(input.get(j).compareTo(S.peek()) == 1){
            S.poll();
            S.add(input.get(j));
        }
    }
    
    return S.peek();
  }
  
    public static void main(String[] args){
      ArrayList<Integer> tester = new ArrayList<Integer>();
      for(int i =0;i<1000000;i++){
          int val = (int)(1 + Math.random()*1000000);
          tester.add(val);
         
      }
      
      
      
      Selection test = new Selection(100000, tester);
      
      
      long one = System.currentTimeMillis();
      System.out.println("Kth from 1: " + test.algo1());
      System.out.println("Time Taken for 1: " + (System.currentTimeMillis()-one));
      System.out.println();
      
      long two = System.currentTimeMillis();
      System.out.println("Kth from 2: " + test.algo2());
      System.out.println("Time Taken for 2: " + (System.currentTimeMillis()-two));
      System.out.println();
      
      long three = System.currentTimeMillis();
      System.out.println("Kth from 3: " + test.algo3()); 
      System.out.println("Time Taken for 3: " + (System.currentTimeMillis()-three));
      System.out.println();
  }
}
