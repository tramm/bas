package com.bookservice.event;

import com.bookservice.data.model.user.User;

public class EditUserEvent extends BaseEvent {

    public EditUserEvent(User user) {
        super(user);
    }

    @Override
    public User getCastedObject() {
        return (User) getObject();
    }

}