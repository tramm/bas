package com.bookservice.ui.activity.vehicle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.adapter.AutoCompleteViewAdapter;
import com.bookservice.adapter.VehicleDetailAdapter;
import com.bookservice.data.model.brands.Brand;
import com.bookservice.data.model.brands.BrandsResponse;
import com.bookservice.data.model.brands.ModelResponse;
import com.bookservice.data.model.vehicle.UserVehicle;
import com.bookservice.data.model.vehicle.VehicleResponse;
import com.bookservice.event.EditVehicleEvent;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VehicleDetailActivity extends BsBaseActivity implements IVehicleView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fabAddVehicle;
    @BindView(R.id.ll_no_list)
    LinearLayout llNoList;

    private GridLayoutManager gridLayoutManager;
    private VehicleDetailAdapter offerAdapter;
    Activity activity;
    VehiclePresenter vehiclePresenter;
    AutoCompleteTextView etBrand;
    AutoCompleteTextView etModel;
    String strSelectedBrandId;
    String strSelectedModelId;
    Dialog dialog;
    boolean isFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_detail);
        ButterKnife.bind(this);
        activity = VehicleDetailActivity.this;

        final Intent intent = getIntent();
        if (intent != null) {
            isFrom = intent.getBooleanExtra("from_offer_detail", false);
        }

        showTile("Vehicle Detail");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();

        vehiclePresenter = new VehiclePresenter(this);
        vehiclePresenter.getVehiclesList(activity);
    }

    @OnClick(R.id.fab) //ButterKnife uses.
    public void onCreateVehicle() {
        vehicleDetailsDialog(null, true);
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onEditVehicle(EditVehicleEvent event) {
        final UserVehicle vehicle = event.getCastedObject();
        vehicleDetailsDialog(vehicle, false);
    }

    public void vehicleDetailsDialog(final UserVehicle vehicle, final boolean isAdd) {

        strSelectedBrandId = "";
        strSelectedModelId = "";
        final View view = getLayoutInflater().inflate(R.layout.vehicle_bottom_sheet_dialog, null);

        dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        etBrand = view.findViewById(R.id.brand);
        etModel = view.findViewById(R.id.model);
        final Button ok = view.findViewById(R.id.ok);
        final Button close = view.findViewById(R.id.close);
        final EditText etRegNo = view.findViewById(R.id.regNo);
        final EditText etYear = view.findViewById(R.id.year);
        final TextView title = view.findViewById(R.id.tv_title);
        final RadioButton rbSelf = view.findViewById(R.id.rb_self);
        final RadioButton rbOthers = view.findViewById(R.id.rb_other);
        final RadioGroup radioGroup = view.findViewById(R.id.rg_group);
        final ImageView delete = view.findViewById(R.id.delete);

        vehiclePresenter.getBrands(activity);

        if (isAdd) {
            title.setText("Add Vehicle");
            delete.setVisibility(View.GONE);
        } else {
            title.setText("Edit Vehicle");
            etBrand.setText(vehicle.getManufacturer().getName());
            etModel.setText(vehicle.getModel().getName());
            etRegNo.setText(vehicle.getRegistrationNumber());
            etYear.setText(String.valueOf(vehicle.getYear()));

            if (vehicle.getTag().equalsIgnoreCase("Self")) {
                rbSelf.setChecked(true);
                rbOthers.setChecked(false);
            } else {
                rbSelf.setChecked(false);
                rbOthers.setChecked(true);
            }

            strSelectedBrandId = vehicle.getManufacturer().getId();
            strSelectedModelId = vehicle.getModel().getId();
            vehiclePresenter.getModels(activity, strSelectedBrandId);
        }


        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strRegNo = etRegNo.getText().toString();
                final String strYear = etYear.getText().toString();
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rbButton = (RadioButton) view.findViewById(selectedId);
                String strType = rbButton.getText().toString();

                if (isAdd) {
                    vehiclePresenter.addVehicle(activity, strSelectedBrandId,
                            strSelectedModelId, strRegNo, strYear, strType);
                } else {
                    vehiclePresenter.updateVehicle(activity, strSelectedBrandId,
                            strSelectedModelId, strRegNo, strYear, vehicle.getId(), strType);
                }
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vehiclePresenter.deleteVehicle(activity, vehicle.getId());
                dialog.dismiss();
            }
        });

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Rect displayRectangle = new Rect();
        Window window = activity.getWindow();
        window.getDecorView().getWindowVisibleDisplayFrame(displayRectangle);
        view.setMinimumWidth((int) (displayRectangle.width() * 0.9f));
        dialog.setContentView(view);

        dialog.show();
    }

    @Override
    public void onSuccessBrandsList(BrandsResponse brandsResponse) {

        final List<Brand> brandsRecords = brandsResponse.getBrands();

        AutoCompleteViewAdapter adapter = new AutoCompleteViewAdapter(this, R.layout.item_autocompleteview, (ArrayList<Brand>) brandsRecords);

        etBrand.setThreshold(1);
        etBrand.setAdapter(adapter);

        etBrand.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = (Brand) adapterView.getItemAtPosition(i);
                strSelectedBrandId = brand.getId();
                vehiclePresenter.getModels(activity, brand.getId());
            }
        });
        etBrand.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                strSelectedModelId = "";
                etModel.setText("");
                etModel.setAdapter(null);
            }
        });
    }

    @Override
    public void onSuccessModelList(ModelResponse list) {
        final List<Brand> modelRecords = list.getBrands();
        AutoCompleteViewAdapter adapter = new AutoCompleteViewAdapter(this, R.layout.item_autocompleteview, (ArrayList<Brand>) modelRecords);

        etModel.setThreshold(1);
        etModel.setAdapter(adapter);

        etModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Brand brand = (Brand) adapterView.getItemAtPosition(i);
                strSelectedModelId = brand.getId();
            }
        });

    }

    @Override
    public void onSuccessVehicleList(VehicleResponse vehicleResponse) {

        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        final List<UserVehicle> userVehicles = vehicleResponse.getUserVehicles();
        if (userVehicles.isEmpty() || userVehicles == null) {
            llNoList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            llNoList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        offerAdapter = new VehicleDetailAdapter(userVehicles, this, isFrom);
        recyclerView.setAdapter(offerAdapter);
    }

    @Override
    public void onSuccessCrudVehicle(String message) {
        BsToast.showLong(activity, message);
        dialog.dismiss();
        vehiclePresenter.getVehiclesList(activity);
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }
}