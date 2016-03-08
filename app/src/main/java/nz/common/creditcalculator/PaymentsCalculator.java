package nz.common.creditcalculator;

/**
 * Created by Николай on 08.03.2016.
 */
public class PaymentsCalculator {
    private int monthsInYear = 12;
    private int toPercents = 100;

    Calculator calculator;
    double paymentToCredit = 0;
    double paymentToPercents = 0;
    double leftToPay = 0;
    double extraPayment = 0;

    public PaymentsCalculator(int months, double sum, double percents)
    {
        this(months, sum, percents, 0);
    }

    public PaymentsCalculator(int months, double sum, double percents, double extraPayment)
    {
        calculator = new Calculator(months);
        this.extraPayment = extraPayment;
        this.recalculate(sum, percents, extraPayment);
    }

    public void recalculate(double sum, double percents, double extraPayment){
        calculator.recalculate(percents / monthsInYear / toPercents, sum);

        this.extraPayment = extraPayment;
        this.paymentToPercents = percents * sum / monthsInYear / toPercents;
        this.paymentToCredit = calculator.monthlyPay - this.paymentToPercents;
        this.leftToPay = sum - this.paymentToCredit - this.extraPayment;
    }
}
