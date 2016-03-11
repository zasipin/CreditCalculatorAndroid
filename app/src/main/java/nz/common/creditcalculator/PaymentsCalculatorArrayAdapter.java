package nz.common.creditcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

/**
 * Created by Николай on 08.03.2016.
 */
public class PaymentsCalculatorArrayAdapter extends ArrayAdapter<PaymentsCalculator> {
    int resource;
    int totalMonths;
    CalculatorsRepository calculatorsRepository;

    public PaymentsCalculatorArrayAdapter(Context context, int resource, PaymentsCalculator[] objects) {
        this(context, resource, objects, 0, null);
    }

    public PaymentsCalculatorArrayAdapter(Context context, int resource, PaymentsCalculator[] objects, int months, CalculatorsRepository calculatorsRepository) {
        super(context, resource, objects);
        this.resource = resource;
        this.totalMonths = months;
        this.calculatorsRepository = calculatorsRepository;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        PaymentsCalculator item = getItem(position);
        if (convertView == null) {
            //LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.payment_item, null);
        }
        configureTexts(convertView, item);
        configureEditEvent(convertView, item, position);

        return convertView;
    }

    private void configureTexts(View view, PaymentsCalculator item) {
        TextView month = (TextView) view.findViewById(R.id.tv_PIMonth);
        month.setText(Integer.toString(totalMonths - item.calculator.months + 1));

        TextView payment = (TextView) view.findViewById(R.id.tv_PIPayment);
        payment.setText(new DecimalFormat("#").format(item.calculator.monthlyPay));

        TextView paymentToCredit = (TextView) view.findViewById(R.id.tv_PIForCredit);
        paymentToCredit.setText(new DecimalFormat("#").format(item.paymentToCredit));

        TextView paymentToPercents = (TextView) view.findViewById(R.id.tv_PIPaymentToPercents);
        paymentToPercents.setText(new DecimalFormat("#").format(item.paymentToPercents));

        TextView leftToPay = (TextView) view.findViewById(R.id.tv_PILeftToPay);
        leftToPay.setText(new DecimalFormat("#").format(item.getLeftToPay()));
    }

    private void configureEditEvent(View view, final PaymentsCalculator item, final int position)
    {
        final PaymentsCalculatorArrayAdapter self = this;
        EditText extraPayment = (EditText) view.findViewById(R.id.et_PIExtraPayment);

        extraPayment.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    //final int position = v.getId();
//                    final EditText s = (EditText) v;
//                    int currentSum = 0;
//                    try {
//                        currentSum = Integer.parseInt(s.getText().toString());
//                        if (currentSum > 0) {
//                            calculatorsRepository.recalculate(item.creditAmount, item.yearPercents, currentSum, item.getMonths());
//                        self.notifyDataSetChanged();
//                        }
//                    } catch (Exception ex) {
//
//                    }
                }
            }
        });

//        extraPayment.addTextChangedListener(new TextWatcher() {
//
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                int currentSum = 0;
//                try {
//                    currentSum = Integer.parseInt(s.toString());
//                    if (currentSum > 0) {
////                        Toast toast = Toast.makeText(getContext(), "called " + item.leftToPay, Toast.LENGTH_SHORT);
////                        toast.show();
//                        calculatorsRepository.recalculate(item.creditAmount, item.yearPercents, currentSum, item.getMonths());
////                        self.notifyDataSetChanged();
//                    }
//                } catch (Exception ex) {
//
//                }
//            }
//        });
    }

    private void showToast()
    {
        Toast.makeText(getContext(), "called ", Toast.LENGTH_SHORT).show();
    }
}
