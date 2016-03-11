package nz.common.creditcalculator;

/**
 * Created by Николай on 08.03.2016.
 */
public class PaymentsCalculator implements ICalculator {
    private int monthsInYear = 12;
    private int toPercents = 100;

    Calculator calculator;
    double paymentToCredit = 0;
    double paymentToPercents = 0;
    double leftToPay = 0;
    double extraPayment = 0;
    double yearPercents = 0;
    double creditAmount = 0;

    public PaymentsCalculator(int months, double sum, double percents)
    {
        this(months, sum, percents, 0);
    }

    public PaymentsCalculator(int months, double sum, double percents, double extraPayment)
    {
        calculator = new Calculator(months);
        this.extraPayment = extraPayment;
        this.recalculate(percents, sum, extraPayment);
    }

    @Override
    public int getMonths() {
        return this.calculator.getMonths();
    }

    @Override
    public double getLeftToPay() {
        return this.leftToPay;
    }

    @Override
    public void recalculate(double percents, double sum) {
        this.recalculate(percents, sum, this.extraPayment);
    }

    @Override
    public void recalculate(double percents, double sum, double extraPayment){
        calculator.recalculate(percents, sum);

        this.creditAmount = sum;
        this.yearPercents = percents;
        this.extraPayment = extraPayment;
        this.paymentToPercents = percents * sum / monthsInYear / toPercents;
        this.paymentToCredit = calculator.monthlyPay - this.paymentToPercents;
        this.leftToPay = sum - this.paymentToCredit - this.extraPayment;
    }
}
