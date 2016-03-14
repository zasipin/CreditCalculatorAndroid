package nz.common.creditcalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.text.DecimalFormat;
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
    List<PaymentsCalculator> paymentsList;

    CalculatorsRepository<PaymentsCalculator> paymentsCalculator;
    PaymentsCalculatorArrayAdapter mAdapter;

    public PaymentScheduleActivityFragment() {
        paymentsCalculator = new CalculatorsRepository<>(0);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(months == 0) {
            getDataFromIntent();
            initializePaymentsCalculator();
            if (paymentsList == null) {
                paymentsList = paymentsCalculator.GetCalcuatorsList();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_payment_schedule, container, false);

        configureTexts(view);

        mAdapter = new PaymentsCalculatorArrayAdapter(getContext(),
                                                      R.layout.payment_item_table,
                                                      paymentsList.toArray(new PaymentsCalculator[0]),
                                                      months,
                                                      paymentsCalculator);

        ListView lv = (ListView)view.findViewById(R.id.lv_payments_schedule);
//        View listHeader = inflater.inflate(R.layout.payment_header, null);
//        lv.addHeaderView(listHeader);
        lv.setAdapter(mAdapter);
        setListenerForListView(lv);

        return view;
    }

    private void initializePaymentsCalculator()
    {
        PaymentsCalculatorInitializer init = new PaymentsCalculatorInitializer(paymentsCalculator, months, sum, percents);
        init.Initialize();
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

    private void setListenerForListView(ListView lv)
    {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final int localI = i;
                final EditText editText = new EditText(getContext());

                PaymentsCalculator calc = mAdapter.getItem(i);
                editText.setText(Integer.toString((int)calc.extraPayment));
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);

                final DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        PaymentsCalculator calc = mAdapter.getItem(localI);
                        paymentsCalculator.recalculate(calc.creditAmount,
                                calc.yearPercents,
                                Double.parseDouble(editText.getText().toString())
                                , calc.getMonths());
                        mAdapter.notifyDataSetChanged();
                    }
                };

                DialogsBuilder.buildAlertTextDialog(getContext(),
                                                    getString(R.string.dialog_extraPayment_title),
                                                    getString(R.string.dialog_extraPayment_message),
                                                    okListener,
                                                    editText);
            }
        });
    }
}
