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
import android.widget.Toast;

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
    PaymentsCalculatorArrayAdapter mAdapter;

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

        mAdapter = new PaymentsCalculatorArrayAdapter(getContext(),
                                                                                    R.layout.payment_item,
                                                                                    paymentsList.toArray(new PaymentsCalculator[0]),
                                                                                    months,
                                                                                    paymentsCalculator);
                                                                                    //CreateFakePayments().toArray(new PaymentsCalculator[0]));
        ListView lv = (ListView)view.findViewById(R.id.lv_payments_schedule);
        lv.setAdapter(mAdapter);
        setListenerForListView(lv);

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

    private void setListenerForListView(ListView lv)
    {
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final int localI = i;
                final EditText editText = new EditText(getContext());

                PaymentsCalculator calc = mAdapter.getItem(i);
                editText.setText(Double.toString(calc.extraPayment));
                editText.setInputType(InputType.TYPE_CLASS_NUMBER);
//                editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//                    @Override
//                    public void onFocusChange(View view, boolean b) {
//                        if (b) {
//                            if (editText.requestFocus()) {
//                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
//                            }
//                        }
//                    }
//                });


                final DialogInterface.OnClickListener okListener = new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // get user input and set it to result
                        // edit text
                        PaymentsCalculator calc = mAdapter.getItem(localI);
                        paymentsCalculator.recalculate(calc.creditAmount,
                                calc.yearPercents,
                                Double.parseDouble(editText.getText().toString())
                                , calc.getMonths());
//                        Toast.makeText(getContext(), "done " + Integer.toString(localI), Toast.LENGTH_SHORT);
                    }
                };


//                Toast.makeText(getContext(), "clicked", Toast.LENGTH_SHORT);
                DialogsBuilder.buildAlertTextDialog(getContext(), "Title", "set some value", okListener, editText);
            }
        });
    }

    class EditDialogListener implements DialogInterface.OnClickListener
    {
        EditText mEdit;
        int i;

        public EditDialogListener(int i)
        {
            this.i = i;
        }

        public void setEdit(EditText edit)
        {
            mEdit = edit;
        }

        @Override
        public void onClick(DialogInterface dialogInterface, int id) {
            if(mEdit != null) {
                PaymentsCalculator calc = mAdapter.getItem(i);
                paymentsCalculator.recalculate(calc.creditAmount,
                        calc.yearPercents,
                        Double.parseDouble(mEdit.getText().toString())
                        , calc.getMonths());
                Toast.makeText(getContext(), "done " + Integer.toString(i), Toast.LENGTH_SHORT);
            }
        }
    }
}
