//{ Driver Code Starts
import java.io.*;
import java.util.*;

class Sorting {
    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int t = Integer.parseInt(br.readLine());
        for (int g = 0; g < t; g++) {
            String[] str = (br.readLine()).trim().split(" ");
            int arr[] = new int[str.length];
            for (int i = 0; i < str.length; i++) arr[i] = Integer.parseInt(str[i]);
            System.out.println(new Solution().inversionCount(arr));
            System.out.println("~");
        }
    }
}
// } Driver Code Ends


// User function Template for Java

class Solution {
    // Function to count inversions in the array.
    static int inversionCount(int arr[]) {
        return mergeSort(arr,0,arr.length-1);
     }
    static int mergeSort(int arr[],int left,int right) {
        // Your Code Here
        int count=0;
        if(left<right){
            int mid=left+(right-left)/2;
            count+=mergeSort(arr,left,mid);
            count+=mergeSort(arr,mid+1,right);
            count+=merge(arr,left,mid,right);
        }
        return count;
    }
    static int merge(int arr[],int left,int mid, int right){
        int leftArr[] = new int [mid-left+1];
        int rightArr[] = new int [right-mid];
        
        for(int i=0;i<leftArr.length;i++){
            leftArr[i]=arr[left+i];
        }
        for(int j=0;j<rightArr.length;j++){
            rightArr[j]=arr[mid+1+j];
        }
        int i=0,j=0,k=left,count=0;
        
        while(i<leftArr.length && j<rightArr.length){
            if(leftArr[i]<=rightArr[j]){
                arr[k++]=leftArr[i++];
            }
            else{
                arr[k++]= rightArr[j++];
                count+=(leftArr.length -i);
            }
            
        }
        while(i<leftArr.length){
            arr[k++]=leftArr[i++];
        }
        while(j<rightArr.length){
            arr[k++]=rightArr[j++];
        }
        return count;
    }
}
