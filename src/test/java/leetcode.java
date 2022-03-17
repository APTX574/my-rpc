import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class leetcode {


    class Solution {
        public int movingCount(int m, int n, int k) {
            int[] max =new int[1];
            Set<Integer> set=new HashSet();
            for(int i=0;i<m;i++){
                for(int j=0;j<n;j++){
                    if(set.contains(i*1000+j)){
                        continue;
                    }
                    process(m,n,k,i,j,max,set);
                }
            }

            return max[0];
        }
        public void process(int m, int n, int k, int i, int j, int[] max, Set set){
            if(i>=m||j>=n||set.contains(i*1000+j)){
                return;
            }
            set.add(i*1000+j);
            if(isok(i,j,k)){
                max[0]++;
                if(!set.contains((i+1)*1000+j)){
                    process(m,n,k,i+1,j,max,set);
                }
                if(!set.contains((j+1)+i*1000)){
                    process(m,n,k,i,j+1,max,set);
                }

            }
        }
        public boolean isok(int i,int j,int k){
            int n=0;
            while(i>0){
                n+=i%10;
                i/=10;
            }
            while(j>0){
                n+=j%10;
                j/=10;
            }
            return k>=n;

        }

    }
}
