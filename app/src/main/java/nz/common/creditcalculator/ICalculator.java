package nz.common.creditcalculator;

/**
 * Created by ZasypinNV on 09.03.2016.
 */
public interface ICalculator {
    void recalculate(double percents, double sum, double extraPayment);
    void recalculate(double percents, double sum);
    double getLeftToPay();
    int getMonths();
}
