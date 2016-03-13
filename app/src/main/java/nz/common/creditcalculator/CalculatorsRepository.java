package nz.common.creditcalculator;

import java.util.ArrayList;

/**
 * Created by Nick on 29.07.2015.
 */
public class CalculatorsRepository<T extends ICalculator> {
    int years;
    int monthsInYear = 12;
    int toPercents = 100;
    ArrayList<T> calculatorsArr = new ArrayList<T>();

    public CalculatorsRepository(int _years)
    {
        years = _years;
    }

    public void recalculate(double sum, double percents)
    {
        for(int i = 0; i < calculatorsArr.size(); i++)
        {
            calculatorsArr.get(i).recalculate(percents, sum);
        }
    }

//    public void recalculate(double sum, double percents, double extraPayment) {
//        recalculate(sum, percents);
//    }

    public void recalculate(double sum, double percents, double extraPayment, int months) {
        if (months > 0 && months <= calculatorsArr.size())
        {
            for (T item : calculatorsArr){
                if (item.getMonths() == months)
                {
                    item.recalculate(percents, sum, extraPayment);
                    break;
                }
            }

//            calculatorsArr.get(calculatorsArr.indexOf(item)).recalculate(percents, sum, extraPayment);
        }
        for(int i = 1; i < calculatorsArr.size(); i++)
        {
            T prev = calculatorsArr.get(i - 1);
            calculatorsArr.get(i).recalculate(percents, prev.getLeftToPay());
        }
    }


    public ArrayList<T> GetCalcuatorsList()
    {
        return calculatorsArr;
    }

    public void SetShiftNumberOfYears(int _years)
    {
        years = _years;
        ShiftCalculatorsArray(_years);
    }

    // отдельно сделать extend
    private void ShiftCalculatorsArray(int numberOfItems)
    {
        int numOfCalculators = calculatorsArr.size();
        if(numOfCalculators > numberOfItems)
        {
            for(int i = numOfCalculators - 1; i >= years; i--)
                calculatorsArr.remove(i);
        }
//        if(numOfCalculators < years)
//        {
//            for(int i = numOfCalculators; i <= years; i++)
//                calculatorsArr.add(new Calculator(i * monthsInYear));
//        }
    }

    public void AddItem(T val)
    {
        //years++;
        calculatorsArr.add(val);
    }

    public void RemoveItem()
    {
        //years--;
        calculatorsArr.remove(years);
    }

    public int getNumberOfItems()
    {
        return calculatorsArr.size();
    }

}
