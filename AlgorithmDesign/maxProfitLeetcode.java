public class maxProfitLeetcode {

    public static void main(String[] args) {
        int[] prices = {3,2,1,2,3,5};
        int profit = 0;
        for(int i=1;i<prices.length;i++) {
            if(prices[i-1] < prices[i]) {
                profit += prices[i] - prices[i-1];
            }
        }
            System.out.println(profit);
        }
}
        
        