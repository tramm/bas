package com.bookservice.ui.activity.user;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bookservice.R;
import com.bookservice.adapter.CustomerDetailAdapter;
import com.bookservice.data.model.user.User;
import com.bookservice.data.model.user.UserReponse;
import com.bookservice.event.EditUserEvent;
import com.bookservice.ui.base.BsBaseActivity;
import com.bookservice.utils.view.BsToast;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserDetailActivity extends BsBaseActivity implements IUserView {

    @BindView(R.id.recycler_View)
    RecyclerView recyclerView;
    @BindView(R.id.fab)
    FloatingActionButton fabAddUser;
    @BindView(R.id.ll_no_list)
    LinearLayout llNoList;
    private GridLayoutManager gridLayoutManager;
    private CustomerDetailAdapter offerAdapter;
    UserPresenter userPresenter;
    Activity activity;
    Dialog dialog;
    boolean isFrom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_detail);
        ButterKnife.bind(this);
        activity = UserDetailActivity.this;

        final Intent intent = getIntent();
        if (intent != null) {
            isFrom =  intent.getBooleanExtra("from_offer_detail", false);
        }

        userPresenter = new UserPresenter(this);
        showTile("Customer Detail");
        showWhiteStatusBar(Color.WHITE);
        showBackButton();
        userPresenter.getUsersList(activity);

    }

    @OnClick(R.id.fab) //ButterKnife uses.
    public void onCreateUser() {
        customerDetailsDialog(null, true);
    }


    @Override
    public void onSuccessUserList(UserReponse userReponse) {
        gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);

        final List<User> users = userReponse.getUsers();
        if (users.isEmpty() || users == null) {
            llNoList.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            llNoList.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }

        offerAdapter = new CustomerDetailAdapter(users, this, isFrom);
        recyclerView.setAdapter(offerAdapter);

    }

    @Override
    public void onSuccessCrudUser(String message) {
        BsToast.showLong(activity, message);
        dialog.dismiss();
        userPresenter.getUsersList(activity);
    }

    @Override
    public void onError(String message) {
        BsToast.showLong(activity, message);
    }

    @SuppressWarnings("unused") //Otto uses.
    @Subscribe
    public void onEditUser(EditUserEvent event) {
        User user = event.getCastedObject();
        customerDetailsDialog(user, false);
    }

    public void customerDetailsDialog(final User user, final boolean isAdd) {
        final View view = getLayoutInflater().inflate(R.layout.customer_bottom_sheet_dialog, null);

        dialog = new Dialog(this);
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);

        final Button ok = view.findViewById(R.id.ok);
        final Button close = view.findViewById(R.id.close);
        final TextView name = view.findViewById(R.id.name);
        final TextView email = view.findViewById(R.id.email);
        final TextView mobile = view.findViewById(R.id.mobile);
        final TextView title = view.findViewById(R.id.tv_title);
        final RadioButton rbSelf = view.findViewById(R.id.rb_self);
        final RadioButton rbOthers = view.findViewById(R.id.rb_other);
        final RadioGroup radioGroup = view.findViewById(R.id.radioGroup);
        final ImageView delete = view.findViewById(R.id.delete);

        if (isAdd) {
            title.setText("Add Customer");
            delete.setVisibility(View.GONE);
        } else {
            name.setText(user.getName());
            email.setText(user.getEmail());
            mobile.setText(user.getMobile());
            title.setText("Edit Customer");
            if (user.getTag().equalsIgnoreCase("Self")) {
                rbSelf.setChecked(true);
                rbOthers.setChecked(false);
            } else {
                rbSelf.setChecked(false);
                rbOthers.setChecked(true);
            }
        }

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                RadioButton rbButton = (RadioButton) view.findViewById(selectedId);
                if (isAdd) {
                    userPresenter.addUser(activity, name.getText().toString(), mobile.getText().toString(),
                            email.getText().toString(), rbButton.getText().toString());
                } else {
                    userPresenter.updateUser(activity, name.getText().toString(), mobile.getText().toString(),
                            email.getText().toString(), rbButton.getText().toString(), user.getId());
                }
                // dialog.dismiss();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userPresenter.deleteUser(activity, user.getId());
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

}
