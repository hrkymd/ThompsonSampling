package Sampling;

import java.util.Random;

/**
 * Created by Yamada on 2016/12/20.
 */
public class TThompsonSampling {

    private Random fRandom;
    private int fNoOfArms;
    private TArm[] fArms;

    public TThompsonSampling(int noOfArms, Random random){
        fNoOfArms = noOfArms;
        fRandom = random;
        fArms = new TArm[noOfArms];
        for(int i = 0; i < noOfArms; i++){
            fArms[i] = new TArm();
        }
    }

    /**
     * サンプルする
     * @param mu
     * @param sigma
     * @return
     */
    private double sample(double mu, double sigma){
        return fRandom.nextGaussian() * sigma + mu;
    }

    /**
     * 腕の選択
     * @return
     */
    public int selectArm(){
        assert fArms != null: "Arms are null.";

        double maxSampleValue = Double.NEGATIVE_INFINITY;

        int selectedIndex = -1;
        //正規分布からサンプルして最大のものを選択する
        for(int i = 0; i < fNoOfArms; i++){
            double sampleValue = sample(fArms[i].getMu(), 1.0 / ( fArms[i].getSelectedCount() + 1) );
            if(maxSampleValue < sampleValue){
                selectedIndex = i;
                maxSampleValue = sampleValue;
            }
        }

        assert selectedIndex >= 0 : "SelectedIndex is not valid.";
        return selectedIndex;
    }

    /**
     * 平均ベクトルの更新
     */
    public void upDateMu(int selectedIndex, double reward){
        fArms[selectedIndex].setMu( (fArms[selectedIndex].getMu() * (fArms[selectedIndex].getSelectedCount() + 1) + reward) / (fArms[selectedIndex].getSelectedCount() + 2) );
    }

    /**
     * カウントを1増やす
     */
    public void incrementCount(int selectedIndex){
        fArms[selectedIndex].setSelectedCount(fArms[selectedIndex].getSelectedCount() + 1);
    }

    public TArm[] getArms() {
        return fArms;
    }

    /**
     * 腕クラス
     */
    public class TArm {
        private double fMu;
        private int fSelectedCount;

        public TArm(){
            fMu = 0.0;
            fSelectedCount = 0;
        }


        public double getMu() {
            return fMu;
        }

        public void setMu(double fMu) {
            this.fMu = fMu;
        }

        public int getSelectedCount() {
            return fSelectedCount;
        }

        public void setSelectedCount(int fSelectedCount) {
            this.fSelectedCount = fSelectedCount;
        }
    }
}
