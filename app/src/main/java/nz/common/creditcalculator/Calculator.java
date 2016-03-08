package nz.common.creditcalculator;

/**
 * Created by Nick on 27.07.2015.
 */
public class Calculator {
    int months = 0;
    int monthsInYear = 12;
    double annuitetCoefficient = 0.0;
    double monthlyPay = 0.0;
    double totalPay = 0.0;
    double overpay = 0.0;
    double overpayPercentage = 0.0;
    double recentYearDifference = 0.0;
    double creditAmount = 0.0;
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

    public void recalculate(double monthlyPercent, double creditAmount){
        double power = Math.pow((1 + monthlyPercent), this.months);
        if(monthlyPercent != 0) {
            this.annuitetCoefficient = monthlyPercent * power / (power - 1);
        } else {
            this.annuitetCoefficient = 1.0 / months;
        }
        this.creditAmount = creditAmount;
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
            recentPeriod.recalculate(monthlyPercent, creditAmount);
            recentYearDifference = overpayPercentage - recentPeriod.overpayPercentage;
        }
        else
        {
            recentYearDifference = 0;
        }
    }
}
