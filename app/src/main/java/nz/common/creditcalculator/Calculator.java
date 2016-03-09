package nz.common.creditcalculator;

/**
 * Created by Nick on 27.07.2015.
 */
public class Calculator implements ICalculator {
    private int monthsInYear = 12;
    private int toPercents = 100;

    int months = 0;
    double annuitetCoefficient = 0.0;
    double monthlyPay = 0.0;
    double totalPay = 0.0;
    double overpay = 0.0;
    double overpayPercentage = 0.0;
    double recentYearDifference = 0.0;
    double creditAmount = 0.0;
    double yearPercents = 0.0;
    Calculator recentPeriod;

    public Calculator(int _months)
    {
        this(_months, false);
    }

    public Calculator(int _months, boolean recent)
    {
        if(_months < 0)
            _months = 0;
        months = _months;
        if(!recent){
            recentPeriod = new Calculator(months - monthsInYear, true);
        }
    }

    @Override
    public double getLeftToPay() {
        return 0;
    }

    @Override
    public void recalculate(double sum, double percents, double extraPayment) {
        this.recalculate(sum, percents, 0);
    }

    @Override
    public void recalculate(double yearPercent, double creditAmount){
        double monthlyPercent = yearPercent / monthsInYear / toPercents;
        double power = Math.pow((1 + monthlyPercent), this.months);
        if(monthlyPercent != 0) {
            this.annuitetCoefficient = monthlyPercent * power / (power - 1);
        } else {
            this.annuitetCoefficient = 1.0 / months;
        }
        this.creditAmount = creditAmount;
        this.yearPercents = yearPercent;
        this.monthlyPay = creditAmount * this.annuitetCoefficient;
        this.totalPay = this.monthlyPay * this.months;
        this.overpay = this.totalPay - creditAmount;
        if(creditAmount > 0) {
            this.overpayPercentage = (this.overpay / creditAmount) * 100;
        }
        else {
            this.overpayPercentage = 0;
        }

        if(recentPeriod != null)
        {
            recentPeriod.recalculate(yearPercent, creditAmount);
            recentYearDifference = overpayPercentage - recentPeriod.overpayPercentage;
        }
        else
        {
            recentYearDifference = 0;
        }
    }
}
