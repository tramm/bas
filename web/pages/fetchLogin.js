import React from 'react';

const loginPage = (props) => {
    return(
        <div>
        <input type = "text" placeholder="Email"  onChange = {props.emailChanged} value = {props.email}/>
        <input type = "password" placeholder="Password"  onChange={props.passChanged} value = {props.password}/>
        </div>
    )
};

export default loginPage;