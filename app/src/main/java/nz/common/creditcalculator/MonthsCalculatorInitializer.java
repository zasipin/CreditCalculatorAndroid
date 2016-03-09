package nz.common.creditcalculator;

/**
 * Created by ZasypinNV on 09.03.2016.
 */
public class MonthsCalculatorInitializer {
    private CalculatorsRepository repository;
    private int monthsInYear = 12;
    private int years = 0;

    public MonthsCalculatorInitializer(CalculatorsRepository repo, int years)
    {
        repository = repo;
        this.years = years;
    }

    public void Initialize()
    {
        repository.AddItem(new Calculator(monthsInYear , true));
        for (int i = 2; i <= years; i++)
        {
            repository.AddItem(new Calculator(i * monthsInYear ));
        }
    }
}
