package com.hci.moola;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.hci.moola.model.DataCollector;
import com.hci.moola.model.PostOffice;
import com.hci.moola.model.Transaction;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Calendar;


public class EditIouActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_iou);
        getActionBar().setTitle("Add IOU");

        if (savedInstanceState == null) {
            Transaction model = (Transaction) PostOffice.getMessage(this.getClass());
            EditIouFragment f = EditIouFragment.newInstance(model); // can be null
            getFragmentManager().beginTransaction()
                    .add(R.id.container, f, f.getClass().getName())
                    .commit();
        }

        DataCollector.getInstance().attachTouchCounterToActivity(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_edit_iou, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_save) {
            EditIouFragment f = (EditIouFragment) getFragmentManager().findFragmentByTag(EditIouFragment.class.getName());
            Transaction t = f.getModel();
            int result = t.isValid();
            switch (result) {
                case 0:
                    PostOffice.putMessage(IouListActivity.class, t);
                    setResult(Activity.RESULT_OK);
                    finish();
                    break;
                case Transaction.ERROR_NO_PERSON:
                    Toast.makeText(this, "You must enter a name.", Toast.LENGTH_LONG).show();
                    break;
                case Transaction.ERROR_NO_DESCRIPTION:
                    Toast.makeText(this, "You must enter an amount or description.", Toast.LENGTH_LONG).show();
                    break;
            }
            return true;
        } else if (id == android.R.id.home) {
            setResult(Activity.RESULT_CANCELED);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class EditIouFragment extends Fragment {
        private Transaction mModel;
        private EditText mPersonEditText;
        private Switch mOwesMeSwitch;
        private EditText mAmountEditText;
        private EditText mDescriptionEditText;

        private static final String KEY_ARG = "Transaction";

        public static EditIouFragment newInstance(Transaction model) {
            EditIouFragment f = new EditIouFragment();
            Bundle args = new Bundle();
            args.putParcelable(KEY_ARG, model);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (savedInstanceState == null) {
                mModel = getArguments().getParcelable(KEY_ARG);
            }
        }

        public Transaction getModel() {
            String person = mPersonEditText.getText().toString();
            boolean owesMe = mOwesMeSwitch.isChecked();
            double amount = parseAmountText(mAmountEditText.getText().toString());
            String description = mDescriptionEditText.getText().toString();
            if (mModel == null)
                return new Transaction(person, owesMe, amount, description, Calendar.getInstance());
            else
                return new Transaction(person, owesMe, amount, description, Calendar.getInstance(), mModel.getId());
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_edit_iou, container, false);

            mPersonEditText = (EditText) rootView.findViewById(R.id.edit_title_edittext);
            if (mModel != null && mModel.getPerson() != null && !mModel.getPerson().isEmpty()) {
                mPersonEditText.setText(mModel.getPerson());
                mPersonEditText.setEnabled(false);
            }

            mOwesMeSwitch = (Switch) rootView.findViewById(R.id.edit_owe_switch);
            if (mModel != null)
                mOwesMeSwitch.setChecked(mModel.isOwesMe());

            mAmountEditText = (EditText) rootView.findViewById(R.id.edit_amount_edittext);
            if (mModel != null)
                mAmountEditText.setText(formatAmountText(mModel.getAmount()));
            else
                mAmountEditText.setText("$0.00");
            mAmountEditText.setSelectAllOnFocus(true);
            mAmountEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (!hasFocus) {
                        String s = mAmountEditText.getText().toString();
                        double amountVal = parseAmountText(s.toString());
                        String formatted = formatAmountText(amountVal);
                        mAmountEditText.setText(formatted);
                        mAmountEditText.setSelection(formatted.length());
                    }
                }
            });

            mDescriptionEditText = (EditText) rootView.findViewById(R.id.edit_description_edittext);
            if (mModel != null && mModel.getDescription() != null && !mModel.getDescription().isEmpty())
                mDescriptionEditText.setText(mModel.getDescription());
            return rootView;
        }

        private String formatAmountText(double amount) {
            return NumberFormat.getCurrencyInstance().format(amount);
        }

        private double parseAmountText(String s) {
            if (s.contains(".")) {
                s = s.replaceAll("[$,]", "");
                String[] parts = s.split("[.]");
                String dollars = parts[0];
                String cents = parts[1];
                s = "$" + dollars + '.' + cents;
            } else {
                s = "$" + s;
            }

            try {
                Number n = NumberFormat.getCurrencyInstance().parse(s);
                return n.doubleValue();
            } catch (ParseException e) {
                return 0;
            }
        }
    }
}
