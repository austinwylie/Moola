package com.hci.moola;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.hci.moola.model.ColorPicker;
import com.hci.moola.model.DataCollector;
import com.hci.moola.model.Iou;
import com.hci.moola.model.PostOffice;
import com.hci.moola.model.Transaction;

import java.util.ArrayList;
import java.util.Calendar;

public class IouListActivity extends Activity implements IouListFragment.IouListFragmentCallback {
    private ArrayList<Iou> mIous;

    private static final int REQUEST_ADD_TRANSACTION = 1;
    private static final int REQUEST_EDIT_TRANSACTION = 2;
    private static final String TAG_IOU_LIST = "IouListFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iou_list);
        if (savedInstanceState == null) {
            mIous = new ArrayList<Iou>();
            ColorPicker cp = ColorPicker.getInstance();
            Iou a = new Iou(new Transaction("Ben", true, 10, "lunch", Calendar.getInstance()), cp.next());
            a.addTransaction(new Transaction("Ben", true, 15, "lunch2", Calendar.getInstance()));
            mIous.add(a);
            mIous.add(new Iou(new Transaction("Braden", false, 30, "hi", Calendar.getInstance()), cp.next()));
            mIous.add(new Iou(new Transaction("Lana", true, 15.60, "idk", Calendar.getInstance()), cp.next()));

            IouListFragment f = IouListFragment.newInstance(mIous);
            getFragmentManager().beginTransaction().add(R.id.container, f, TAG_IOU_LIST).commit();

            DataCollector.getInstance().attachTouchCounterToActivity(this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_iou_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(this, EditIouActivity.class);
            startActivityForResult(intent, REQUEST_ADD_TRANSACTION);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch(requestCode) {
            case REQUEST_ADD_TRANSACTION: {
                Transaction createdTransaction = (Transaction) PostOffice.getMessage(this.getClass());
                IouListFragment f = (IouListFragment) getFragmentManager().findFragmentByTag(TAG_IOU_LIST);
                f.addTransaction(createdTransaction);
                break;
            }
            case REQUEST_EDIT_TRANSACTION: {
                Transaction editedTransaction = (Transaction) PostOffice.getMessage(this.getClass());
                IouListFragment f = (IouListFragment) getFragmentManager().findFragmentByTag(TAG_IOU_LIST);
                f.updateTransaction(editedTransaction);
                break;
            }
        }
    }

    @Override
    public void onTransactionClicked(Iou iou, Transaction txn) {
        PostOffice.putMessage(EditIouActivity.class, txn);
        Intent intent = new Intent(this, EditIouActivity.class);
        startActivityForResult(intent, REQUEST_EDIT_TRANSACTION);
    }
}
