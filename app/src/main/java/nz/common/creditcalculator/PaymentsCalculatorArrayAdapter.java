package nz.common.creditcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * Created by Николай on 08.03.2016.
 */
public class PaymentsCalculatorArrayAdapter extends ArrayAdapter<PaymentsCalculator>
{
    int resource;
    public PaymentsCalculatorArrayAdapter(Context context, int resource, PaymentsCalculator[] objects)
    {
        super(context, resource, objects);
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        return super.getView(position, convertView, parent);
        PaymentsCalculator item  = getItem(position);
        if (convertView == null) {
            //LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.payment_item, null);
        }
        configureTexts(convertView, item);

        return convertView;
    }

    private void configureTexts(View view, PaymentsCalculator item)
    {
        TextView sum = (TextView)view.findViewById(R.id.tv_PISum);
        sum.setText(Double.toString(item.creditAmount));
//
//        TextView percents = (TextView)view.findViewById(R.id.tv_payments_percents);
//        sum.setText(Double.toString(item.yearPercents));

    }
}
