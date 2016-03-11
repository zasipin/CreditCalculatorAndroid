package nz.common.creditcalculator;

/**
 * Created by ZasypinNV on 09.03.2016.
 */
public class PaymentsCalculatorInitializer {
    private CalculatorsRepository repository;
    private int monthsInYear = 12;
    private int months = 0;
    private double sum = 0;
    private double percents = 0;

    public PaymentsCalculatorInitializer(CalculatorsRepository repo, int months, double sum, double percents)
    {
        repository = repo;
        this.months = months;
        this.sum = sum;
        this.percents = percents;
    }

    public void Initialize()
    {
        double paymentToCredit = 0.0;
        double creditSum = sum;
        PaymentsCalculator paymentCalculator;

        for (int i = months; i > 0; i--)
        {
            creditSum = creditSum - paymentToCredit;
            paymentCalculator = new PaymentsCalculator(i, creditSum, percents);
            repository.AddItem(paymentCalculator);
            paymentToCredit = paymentCalculator.paymentToCredit;
        }
    }
}
