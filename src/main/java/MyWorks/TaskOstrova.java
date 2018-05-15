package MyWorks;

public class TaskOstrova {
    public static void main(String[] args) {
        int count = 0;
        int n = 10;
        int[][] ostrov = new int[n][n];
        for (int i = 1; i < n -1; i++){
            for (int j = 1; j < n - 1; j++){
            ostrov[i][j] = random();
                  System.out.print(ostrov[i][j] + "\t");
            }
           System.out.println();
        }
       for (int i = 1; i < n - 1; i++) {
            for (int j = 1; j < n - 1; j++) {
                if (ostrov [i][j] == 1){
                    count++;
                utopit(ostrov, i, j);
                }
            }
        }
            System.out.println("Тут " + count + " островов");
    }

    public static int random (){
        return (Math.random() < 0.5) ? 1 : 0;
    }
    public static void utopit(int [][] ostrov, int i, int j){
            if (ostrov [i][j] == 1) {
                ostrov[i][j] = 0;
                    utopit(ostrov, i + 1, j);
                    utopit(ostrov, i, j + 1);
                    utopit(ostrov, i - 1, j);
                    utopit(ostrov, i, j - 1);
            }
    }
}
