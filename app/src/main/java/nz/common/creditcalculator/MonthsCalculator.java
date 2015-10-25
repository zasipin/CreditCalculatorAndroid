package nz.common.creditcalculator;

import java.util.ArrayList;

/**
 * Created by Nick on 29.07.2015.
 */
public class MonthsCalculator {
    int years;
    int monthsInYear = 12;
    int toPercents = 100;
    ArrayList<Calculator> calculatorsArr = new ArrayList<Calculator>();
    public MonthsCalculator(int _years)
    {
        years = _years;
        InitCalculators();
    }

    private void InitCalculators()
    {
        calculatorsArr.add(new Calculator(monthsInYear, true));
        for (int i = 2; i <= years; i++)
        {
            calculatorsArr.add(new Calculator(i * monthsInYear));
        }
    }

    public void Recalculate(double sum, double percents)
    {
        for(int i = 0; i < calculatorsArr.size(); i++)
        {
            calculatorsArr.get(i).recalculate(percents / monthsInYear / toPercents, sum);
        }
    }

    public ArrayList<Calculator> GetCalcuatorsList()
    {
        return calculatorsArr;
    }

    public void SetNumberOfYears(int _years)
    {
        years = _years;
        UpdateCalculatorsArray();
    }

    private void UpdateCalculatorsArray()
    {
        int numOfCalculators = calculatorsArr.size();
        if(numOfCalculators > years)
        {
            for(int i = numOfCalculators - 1; i >= years; i--)
                calculatorsArr.remove(i);
        }
        if(numOfCalculators < years)
        {
            for(int i = numOfCalculators; i <= years; i++)
                calculatorsArr.add(new Calculator(i * monthsInYear));
        }
    }

    public void AddOneYear()
    {
        years++;
        calculatorsArr.add(new Calculator(years * monthsInYear));
    }

    public void RemoveOneYear()
    {
        years--;
        calculatorsArr.remove(years);
    }
}
