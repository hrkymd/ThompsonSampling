package Main;

import Sampling.TThompsonSampling;
import java.util.Random;

/**
 * Created by Yamada on 2016/12/20.
 */
public class Main {

    //腕の数
    private static final int N = 5;

    //最大時刻
    private static final int MAX = 1000;

    public static void main(String[] args) {

        Random random = new Random();
        int[] count = new int[N];
        TThompsonSampling thompsonSampling = new TThompsonSampling(N, random);

        for(int loop = 0; loop < MAX; loop++){
            int selectedIndex = thompsonSampling.selectArm();
            count[selectedIndex]++;

            //
            double reward = getReward(selectedIndex, random);

            //方策の更新
            thompsonSampling.upDateMu(selectedIndex, reward);
            thompsonSampling.incrementCount(selectedIndex);

        }//end for(loop)

        System.out.println("final state");
        for(int i = 0; i < N; i++){
            System.out.println("Policy" + i
                    + " : mu = " + thompsonSampling.getArms()[i].getMu()
                    + ", sigma = " + 1.0 / (thompsonSampling.getArms()[i].getSelectedCount() + 1.0)
                    + ", count = " + thompsonSampling.getArms()[i].getSelectedCount());
        }
    }

    private static double getReward(int policyIndex, Random random){

        //各腕に対する実際の平均報酬と分散
        double[] mu = {0.7, 0.3, 0.5, 0.8, 0.9};
        double[] sigma = {0.3, 0.2, 0.2, 0.3, 0.1};

        assert N == mu.length && N == sigma.length : "Arms are not sufficient.";

        return random.nextGaussian() * sigma[policyIndex] + mu[policyIndex];

    }
}
