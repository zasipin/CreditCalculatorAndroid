package nz.common.creditcalculator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;

/**
 * Created by Nick on 19.08.2015.
 */
public class CalculatorListAdapter extends ArrayAdapter<Calculator> {
    int resource;
    int monthsInYear = 12;

    public CalculatorListAdapter(Context _context, int _resource, Calculator[] _objects)
    {
        super(_context, _resource, _objects);
        resource = _resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Calculator item  = getItem(position);
        if (convertView == null) {
            //LayoutInflater li = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item, parent, false);
        }
        TextView month = (TextView)convertView.findViewById(R.id.textMonth);
        TextView payMonth = (TextView)convertView.findViewById(R.id.textPayMonth);
        TextView payTotal = (TextView)convertView.findViewById(R.id.textPayTotal);

        TextView payOverpay = (TextView)convertView.findViewById(R.id.textOverpay);
        TextView payOverpayPercent = (TextView)convertView.findViewById(R.id.textOverpayPercent);
        TextView payDeltaOverpay = (TextView)convertView.findViewById(R.id.textDeltaOverpay);

        month.setText(Integer.toString(item.months) + "(" + Integer.toString(item.months / monthsInYear) + ")");
        payMonth.setText(new DecimalFormat("#").format(item.monthlyPay));
        payTotal.setText(new DecimalFormat("#").format(item.totalPay));
        payOverpay.setText(new DecimalFormat("#").format(item.overpay));
        payOverpayPercent.setText(new DecimalFormat("0.00").format(item.overpayPercentage));
        payDeltaOverpay.setText(new DecimalFormat("0.00").format(item.recentYearDifference));

        //return super.getView(position, convertView, parent);
        return convertView;
    }
}
