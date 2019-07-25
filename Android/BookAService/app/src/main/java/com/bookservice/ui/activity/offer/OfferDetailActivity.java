package com.bookservice.ui.activity.offer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.adapter.OfferDetailAdapter;
import com.bookservice.data.model.user.User;
import com.bookservice.data.model.user.UserReponse;
import com.bookservice.data.model.vehicle.UserVehicle;
import com.bookservice.data.model.vehicle.VehicleResponse;
import com.bookservice.ui.activity.home.HomeActivity;
import com.bookservice.ui.activity.user.UserDetailActivity;
import com.bookservice.ui.activity.vehicle.VehicleDetailActivity;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.Utils;
import com.bookservice.utils.view.BsToast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.bookservice.constants.Constants.EXTRA_DESCRIPTION;
import static com.bookservice.constants.Constants.EXTRA_NAME;
import static com.bookservice.constants.Constants.EXTRA_OFFER_ID;
import static com.bookservice.constants.Constants.EXTRA_SERVICE_CENTER_ID;
import static com.bookservice.constants.Constants.EXTRA_SERVICE_CENTER_NAME;

public class OfferDetailActivity extends BsBaseActivity implements IOffersDetailView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.select_date)
    TextView selectDate;
    @BindView(R.id.name)
    TextView name;
    @BindView(R.id.email)
    TextView email;
    @BindView(R.id.mobile)
    TextView mobile;
    @BindView(R.id.customer_type)
    TextView customerType;
    @BindView(R.id.vehicle_brand)
    TextView txtbrand;
    @BindView(R.id.vehicle_model)
    TextView txtModel;
    @BindView(R.id.vehicle_detail)
    TextView txtVehicleDetail;
    @BindView(R.id.vehicle_type)
    TextView vehicleType;

    private Calendar myCalendar;
    private GridLayoutManager gridLayoutManager;
    private OfferDetailAdapter offerAdapter;
    Activity activity;

    OfferDetailPresenter offerDetailPresenter;
    ArrayList<HashMap<String, String>> arrayList;

    String strVehicleId;
    String strUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        activity = OfferDetailActivity.this;

        offerDetailPresenter = new OfferDetailPresenter(this);

        arrayList = new ArrayList<>();
        final Intent intent = getIntent();
        if (intent != null) {
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put(EXTRA_NAME, intent.getStringExtra(EXTRA_NAME));
            hashMap.put(EXTRA_DESCRIPTION, intent.getStringExtra(EXTRA_DESCRIPTION));
            hashMap.put(EXTRA_SERVICE_CENTER_ID, intent.getStringExtra(EXTRA_SERVICE_CENTER_ID));
            hashMap.put(EXTRA_SERVICE_CENTER_NAME, intent.getStringExtra(EXTRA_SERVICE_CENTER_NAME));
            hashMap.put(EXTRA_OFFER_ID, intent.getStringExtra(EXTRA_OFFER_ID));
            arrayList.add(hashMap);

            showTile(intent.getStringExtra(EXTRA_SERVICE_CENTER_NAME));
        }

        showWhiteStatusBar(Color.WHITE);
        showBackButton();
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        offerAdapter = new OfferDetailAdapter(arrayList);
        recyclerView.setAdapter(offerAdapter);

        myCalendar = Calendar.getInstance();
        offerDetailPresenter.getVehiclesList(activity);
        offerDetailPresenter.getUsersList(activity);
    }

    @OnClick(R.id.select_date) //ButterKnife uses.
    public void selectBookingDate() {

        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                String myFormat = "dd/MM/yy"; //In which you need put here
                SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                selectDate.setText(sdf.format(myCalendar.getTime()));
            }

        };

        final DatePickerDialog datePickerDialog = new DatePickerDialog(OfferDetailActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.getDatePicker().setMinDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    @OnClick(R.id.book_offer) //ButterKnife uses.
    public void bookOffer() {
        offerDetailPresenter.bookOffer(activity, arrayList.get(0).get(EXTRA_OFFER_ID),
                strUserId, strVehicleId, selectDate.getText().toString());
    }

    @OnClick(R.id.share) //ButterKnife uses.
    public void openShare() {
        final HashMap<String, String> hashMap = arrayList.get(0);
        final String name = hashMap.get(EXTRA_NAME);
        final String description = hashMap.get(EXTRA_DESCRIPTION);
        final String serviceCenter = hashMap.get(EXTRA_SERVICE_CENTER_NAME);

        Utils.share(activity, name, description, serviceCenter);
    }

    @OnClick(R.id.edit_customer) //ButterKnife uses.
    public void openEditCustomer() {
        Intent intent = new Intent(OfferDetailActivity.this, UserDetailActivity.class);
        intent.putExtra("from_offer_detail", true);
        startActivityForResult(intent, 1);
    }

    @OnClick(R.id.edit_vehicle) //ButterKnife uses.
    public void openEditVehicle() {
        Intent intent = new Intent(OfferDetailActivity.this, VehicleDetailActivity.class);
        intent.putExtra("from_offer_detail", true);
        startActivityForResult(intent, 2);
    }

    public void bookingConfirmedDialog() {
        final View alertContentView = getLayoutInflater().inflate(R.layout.dialog_confirmed, null);
        final Dialog dialog = new Dialog(OfferDetailActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(alertContentView);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(false);
        final LinearLayout continueHomePage = (LinearLayout) alertContentView.findViewById(R.id.continue_home_page);

       /* final KonfettiView konfettiView = findViewById(R.id.viewKonfetti);

        konfettiView.build()
                .addColors(Color.YELLOW, Color.GREEN, Color.MAGENTA)
                .setDirection(0.0, 359.0)
                .setSpeed(1f, 5f)
                .setFadeOutEnabled(true)
                .setTimeToLive(2000L)
                .addShapes(Shape.RECT, Shape.CIRCLE)
                .addSizes(new Size(12, 5))
                .setPosition(-50f, konfettiView.getWidth() + 50f, -50f, -50f)
                .streamFor(300, 5000L);
*/
        continueHomePage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OfferDetailActivity.this, HomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
            }
        });

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

    @Override
    public void onSuccessBooking() {
        bookingConfirmedDialog();
    }

    @Override
    public void onSuccessVehicleList(VehicleResponse vehicleResponse) {
        final List<UserVehicle> userVehicles = vehicleResponse.getUserVehicles();
        if (userVehicles.isEmpty() || userVehicles == null) {
            strVehicleId = "";
            txtbrand.setText("");
            txtModel.setText("");
            txtVehicleDetail.setText("");
            vehicleType.setText("");
            vehicleType.setVisibility(View.GONE);
        } else {
            strVehicleId = userVehicles.get(0).getId();
            txtbrand.setText(userVehicles.get(0).getManufacturer().getName());
            txtModel.setText(userVehicles.get(0).getModel().getName());
            txtVehicleDetail.setText(userVehicles.get(0).getRegistrationNumber() + ", " + userVehicles.get(0).getYear());
            vehicleType.setVisibility(View.VISIBLE);
            vehicleType.setText(userVehicles.get(0).getTag());

        }
    }

    @Override
    public void onSuccessUserList(UserReponse userReponse) {
        final List<User> users = userReponse.getUsers();
        if (users.isEmpty() || users == null) {
            strUserId = "";
            name.setText("");
            email.setText("");
            mobile.setText("");
            customerType.setText("");
            customerType.setVisibility(View.GONE);
        } else {
            strUserId = users.get(0).getId();
            name.setText(users.get(0).getName());
            email.setText(users.get(0).getEmail());
            mobile.setText(users.get(0).getMobile());
            customerType.setVisibility(View.VISIBLE);
            customerType.setText(users.get(0).getTag());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                User user = (User) data.getSerializableExtra("user");
                strUserId = user.getId();
                name.setText(user.getName());
                email.setText(user.getEmail());
                mobile.setText(user.getMobile());
                customerType.setText(user.getTag());

            }
        } else if (requestCode == 2) {
            if (resultCode == RESULT_OK) {
                UserVehicle vehicle = (UserVehicle) data.getSerializableExtra("vehicle");
                strVehicleId = vehicle.getId();
                txtbrand.setText(vehicle.getManufacturer().getName());
                txtModel.setText(vehicle.getModel().getName());
                txtVehicleDetail.setText(vehicle.getRegistrationNumber() + ", " + vehicle.getYear());
                vehicleType.setText(vehicle.getTag());
            }
        }
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }
}
