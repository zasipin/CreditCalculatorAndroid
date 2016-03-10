package nz.common.creditcalculator;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    int creditSum = 100000;
    float percentsSum = 10;

    int SUM_INIT = 100000;
    float PERCENTS_INIT = 10;

    SharedPreferences prefs;

    Button sumPlus;
    Button sumMinus;
    EditText sumEdit;
    Button percentsPlus;
    Button percentsMinus;
    EditText percentsEdit;

    int SUM_TO_ADD = 10000;
    float PERCENTS_TO_ADD = (float)1.0;

    String CREDIT_SUM = "sum";
    String PERCENTS = "percents";

    int yearsByDefault = 30;
    MonthsCalculator monthsCalculator;

    CalculatorListAdapter adapter;

    public MainActivityFragment() {
        monthsCalculator = new MonthsCalculator(yearsByDefault);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //savedInstanceState.getInt("creditSum", creditSum);
        sumPlus = (Button) getActivity().findViewById(R.id.btn_creditSumPlus);
        sumMinus = (Button) getActivity().findViewById(R.id.btn_creditSumMinus);
        sumEdit = (EditText) getActivity().findViewById(R.id.txt_creditSum);

        percentsPlus = (Button) getActivity().findViewById(R.id.btn_percentsPlus);
        percentsMinus = (Button) getActivity().findViewById(R.id.btn_percentsMinus);
        percentsEdit = (EditText) getActivity().findViewById(R.id.et_Percents);

        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getBaseContext());
        restorePreferences(prefs);
        this.restoreSavedInstance(savedInstanceState);
        /*
        if(savedInstanceState != null && savedInstanceState.containsKey("sum")) {
            creditSum = savedInstanceState.getInt("sum");
            percentsSum = savedInstanceState.getFloat("percents");
            monthsCalculator.Recalculate(creditSum, percentsSum);
        }
        */
        adapter = new CalculatorListAdapter(getActivity().getApplicationContext(),
                R.layout.item, monthsCalculator.GetCalcuatorsList().toArray(new Calculator[0]));

        ListView lv = (ListView)getActivity().findViewById(R.id.listViewItems);
        lv.setAdapter(adapter);

        // Populate list with our static array of titles.
        //setListAdapter(new CalculatorListAdapter(getActivity().getApplicationContext(),
        //        R.layout.item, (Calculator[]) monthsCalculator.GetCalcuatorsList().toArray()));

        configureSumButtons();
        configurePercentsButtons();
        configureEdits();
    }

    @Override
    public void onPause() {
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        savePreferences(prefs);
        super.onPause();
    }

    @Override
    public void onStop() {
        savePreferences(prefs);
        super.onStop();
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        this.restoreSavedInstance(savedInstanceState);
        /*
        if(savedInstanceState != null && savedInstanceState.containsKey("sum")) {
            creditSum = savedInstanceState.getInt("sum");
            percentsSum = savedInstanceState.getFloat("percents");
            monthsCalculator.Recalculate(creditSum, percentsSum);
        }
        */
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        saveInstanceState(outState);
        /*
        outState.putInt("sum", creditSum);
        outState.putFloat("percents", percentsSum);
        */
        super.onSaveInstanceState(outState);
    }


    private int addInt(String strVal, int sumToAdd)
    {
        return Integer.parseInt(strVal) + sumToAdd;
    }

    private float addFloat(String strVal, float sumToAdd)
    {
        return Float.parseFloat(strVal) + sumToAdd;
    }

    private void configureSumButtons()
    {
        sumEdit.setText(Integer.toString(creditSum));
        sumPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentSum = 0;
                try {
                    currentSum = addInt(sumEdit.getText().toString(), SUM_TO_ADD);
                }
                catch (Exception ex){
                    currentSum = 0;
                }
                sumEdit.setText(Integer.toString(currentSum));
                updateFromSum(currentSum);
            }
        });
        sumMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentSum = 0;
                try {
                    currentSum = addInt(sumEdit.getText().toString(), -SUM_TO_ADD);
                }
                catch (Exception ex)
                {
                    currentSum = 0;
                }
                if(currentSum >= 0)
                {
                    sumEdit.setText(Integer.toString(currentSum));
                    updateFromSum(currentSum);
                }
            }
        });
    }

    private void configurePercentsButtons()
    {
        percentsEdit.setText(Float.toString(percentsSum));
        percentsPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentSum = 0;
                try {
                    currentSum = addFloat(percentsEdit.getText().toString(), PERCENTS_TO_ADD);

                /*
                percentsSum = currentSum;
                percentsEdit.setText(Float.toString(currentSum));
                monthsCalculator.Recalculate(creditSum, percentsSum);
                adapter.notifyDataSetChanged();
                */
                } catch (Exception ex) {
                    currentSum = 0;
                }
                percentsEdit.setText(Float.toString(currentSum));
                updateFromPercents(currentSum);
            }
        });
        percentsMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float currentSum = 0;
                try {
                    currentSum = addFloat(percentsEdit.getText().toString(), -PERCENTS_TO_ADD);
                } catch (Exception ex) {
                    currentSum = 0;
                }
                if (currentSum >= 0.0) {
                    percentsEdit.setText(Float.toString(currentSum));
                    updateFromPercents(currentSum);
                    /*
                    percentsSum = currentSum;
                    percentsEdit.setText(Float.toString(currentSum));
                    monthsCalculator.Recalculate(creditSum, percentsSum);
                    adapter.notifyDataSetChanged();
                    */
                }
            }
        });
    }

    private void configureEdits()
    {
        sumEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int currentSum = 0;
                try {
                    currentSum = Integer.parseInt(s.toString());
                    if (currentSum > 0) {
                        updateFromSum(currentSum);
                    }
                } catch (Exception ex) {

                }
            }
        });

        percentsEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                float currentValue = 0;
                try {
                    currentValue = Float.parseFloat(s.toString());
                    if (currentValue > 0) {
                        updateFromPercents(currentValue);
                    }
                } catch (Exception ex) {

                }
            }
        });
    }

    private void updateFromSum(int currentSum)
    {
        creditSum = currentSum;
        monthsCalculator.Recalculate(creditSum, percentsSum);
        adapter.notifyDataSetChanged();
    }

    private void updateFromPercents(float currentPercents)
    {
        percentsSum = currentPercents;
        monthsCalculator.Recalculate(creditSum, percentsSum);
        adapter.notifyDataSetChanged();
    }

    private void restoreSavedInstance(Bundle savedInstanceState)
    {
        if(savedInstanceState != null && savedInstanceState.containsKey(CREDIT_SUM)) {
            creditSum = savedInstanceState.getInt(CREDIT_SUM);
            percentsSum = savedInstanceState.getFloat(PERCENTS);
            monthsCalculator.Recalculate(creditSum, percentsSum);
        }
    }

    private void saveInstanceState(Bundle outState)
    {
        outState.putInt(CREDIT_SUM, creditSum);
        outState.putFloat(PERCENTS, percentsSum);
    }

    private void savePreferences(SharedPreferences prefs)
    {
        if(prefs != null) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putFloat(PERCENTS, percentsSum);
            editor.putInt(CREDIT_SUM, creditSum);
            editor.commit();
        }
    }

    private void restorePreferences(SharedPreferences prefs)
    {
        if(prefs == null) {
            percentsSum = PERCENTS_INIT;
            creditSum = SUM_INIT;
        }

        percentsSum = prefs.getFloat(PERCENTS, PERCENTS_INIT);
        creditSum = prefs.getInt(CREDIT_SUM, SUM_INIT);

        monthsCalculator.Recalculate(creditSum, percentsSum);
    }
}
