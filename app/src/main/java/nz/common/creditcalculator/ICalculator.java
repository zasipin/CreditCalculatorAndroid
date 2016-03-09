package nz.common.creditcalculator;

/**
 * Created by ZasypinNV on 09.03.2016.
 */
public interface ICalculator {
    void recalculate(double sum, double percents, double extraPayment);
    void recalculate(double sum, double percents);
    double getLeftToPay();
}
