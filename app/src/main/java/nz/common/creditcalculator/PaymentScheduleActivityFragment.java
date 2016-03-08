package nz.common.creditcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class PaymentScheduleActivityFragment extends Fragment {

    public PaymentScheduleActivityFragment() {
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

        Intent intent = getActivity().getIntent();
        int intentData = intent.getIntExtra(Intent.EXTRA_TEXT, 0);

        PaymentsCalculatorArrayAdapter adapter = new PaymentsCalculatorArrayAdapter(getContext(),
                                                                                    R.layout.payment_item,
                                                                                    CreateFakePayments().toArray(new PaymentsCalculator[0]));
        ListView lv = (ListView)view.findViewById(R.id.lv_payments_schedule);
        lv.setAdapter(adapter);

        return view;
    }

    private List<PaymentsCalculator> CreateFakePayments()
    {
        PaymentsCalculator[] items = {
                new PaymentsCalculator(60, 150000, 22),
                new PaymentsCalculator(36, 150000, 22)

        };

        List<PaymentsCalculator> all = new ArrayList<PaymentsCalculator>(Arrays.asList(items));

        return all;
    }
}
