package nz.common.creditcalculator;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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

        String success = "Data getted: months - " + intentData;
        Toast toast ;
        if (intentData != 0)
        {
            toast = Toast.makeText(getContext(), success, Toast.LENGTH_SHORT);
        }
        else {
            toast = Toast.makeText(getContext(), "No correct data", Toast.LENGTH_SHORT);
        }
        toast.show();

        return view;
    }
}
