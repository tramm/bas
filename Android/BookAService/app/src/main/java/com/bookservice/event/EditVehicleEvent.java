package com.bookservice.event;

import com.bookservice.data.model.vehicle.UserVehicle;

public class EditVehicleEvent extends BaseEvent {

    public EditVehicleEvent(UserVehicle user) {
        super(user);
    }

    @Override
    public UserVehicle getCastedObject() {
        return (UserVehicle) getObject();
    }

}