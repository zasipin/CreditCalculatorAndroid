package nz.common.creditcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentScheduleActivityFragment extends Fragment {

    private int defaultInt = 0;
    private double defaultDouble = 0;
    private double sum;
    private double percents;
    private int months;

    CalculatorsRepository<PaymentsCalculator> paymentsCalculator;

    public PaymentScheduleActivityFragment() {
        paymentsCalculator = new CalculatorsRepository<>(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_schedule, container, false);

        getDataFromIntent();

        configureTexts(view);

        List<PaymentsCalculator> paymentsList;

        initializePaymentsCalculator();
        paymentsList = paymentsCalculator.GetCalcuatorsList();

        PaymentsCalculatorArrayAdapter adapter = new PaymentsCalculatorArrayAdapter(getContext(),
                                                                                    R.layout.payment_item,
                                                                                    paymentsList.toArray(new PaymentsCalculator[0]),
                                                                                    months,
                                                                                    paymentsCalculator);
                                                                                    //CreateFakePayments().toArray(new PaymentsCalculator[0]));
        ListView lv = (ListView)view.findViewById(R.id.lv_payments_schedule);
        lv.setAdapter(adapter);

        return view;
    }

    private void initializePaymentsCalculator()
    {
        PaymentsCalculatorInitializer init = new PaymentsCalculatorInitializer(paymentsCalculator, months, sum, percents);
        init.Initialize();
    }

    private List<PaymentsCalculator> CreateFakePayments()
    {
        PaymentsCalculator[] items = {
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 140000, 22),
                new PaymentsCalculator(60, 170000, 22),
                new PaymentsCalculator(60, 1500, 22),
                new PaymentsCalculator(60, 1500, 22),
                new PaymentsCalculator(60, 1500, 22),
                new PaymentsCalculator(60, 1500, 22),
                new PaymentsCalculator(60, 1500, 22),
                new PaymentsCalculator(60, 100, 22),
                new PaymentsCalculator(60, 100, 22),
                new PaymentsCalculator(60, 100, 22),
                new PaymentsCalculator(60, 100, 22),
                new PaymentsCalculator(60, 100, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(36, 150000, 22)

        };

        List<PaymentsCalculator> all = new ArrayList<PaymentsCalculator>(Arrays.asList(items));

        return all;
    }

    private void configureTexts(View view)
    {
        TextView tv_sum = (TextView)view.findViewById(R.id.tv_payments_sum);
        tv_sum.setText(new DecimalFormat("#").format(sum));

        TextView tv_percents = (TextView)view.findViewById(R.id.tv_payments_percents);
        tv_percents.setText(new DecimalFormat("0.00").format(percents));

        TextView tv_total_months = (TextView)view.findViewById(R.id.tv_payments_total_months);
        tv_total_months.setText(new DecimalFormat("#").format(months));
    }

    private void getDataFromIntent()
    {
        Intent intent = getActivity().getIntent();
        sum = intent.getDoubleExtra(IntentExtras.SUM, defaultDouble);
        percents = intent.getDoubleExtra(IntentExtras.PERCENTS, defaultDouble);
        months = intent.getIntExtra(IntentExtras.MONTHS, defaultInt);
    }
}
