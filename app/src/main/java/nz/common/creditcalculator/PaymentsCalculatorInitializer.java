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
        for (int i = months; i > 0; i--)
        {
            repository.AddItem(new PaymentsCalculator(i, sum, percents));
        }
    }
}
