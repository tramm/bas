const mongoose = require('mongoose');
const _ = require('lodash');
const Vehicle = require('./Vehicle');

const { Schema } = mongoose;
const addressSchema = new Schema({
    street: {
        type: String,
    },
});
const muserSchema = new Schema({

    name: {
        type: String,
    },
    pin: {
        type: String,
    },
    mobile: {
        type: String,
        required: true,
        unique: true,
    },
    createdAt: {
        type: Date,
        required: true,
    },
    email: {
        type: String,
        required: true,
        unique: true,
    },
    tag: {
        type: String,
        required: true,
    },
    isAdmin: {
        type: Boolean,
        default: false,
    },
    displayName: String,
    address:[addressSchema],
    vehicle:[{type: Schema.ObjectId, ref: 'Vehicle'}]

});

class MUserClass {
    // User's public fields
    static publicFields() {
        return ['name', 'mobile', 'tag', 'email'];
    }
    static async list() {
        const users = await this.find({})
            .select(MUserClass.publicFields().join(' '))
            .sort({ createdAt: -1 });
        return { users };
    }
    static async add({name,mobile,pin,email,tag,address,vehicle_id}){
        console.log("the vehicle is "+vehicle_id);
        if (mobile){
            const user = await this.findOne({ mobile });
            if(user)return user;
            let vehicle = await Vehicle.findById(vehicle_id);
            console.log(vehicle);
            const newUser = await this.create({
                createdAt: new Date(),
                name,
                mobile,
                pin,
                email,
                tag,
                address,
                vehicle
            });
            console.log(newUser.vehicle);
            return newUser;
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };
    static async update(id , req){
        const updUser = await MUser.findByIdAndUpdate(id, {$set:req},{new: true});
        console.log(updUser);
        return updUser;
    }
    static async delete(id){
        const delUser = await MUser.findByIdAndDelete(id);
        console.log(delUser);
        return delUser;
    }
    static async signInOrSignUp({
        googleId, email, googleToken, displayName, avatarUrl,
    }) {
        const user = await this.findOne({ googleId }).select(UserClass.publicFields().join(' '));

        if (user) {
            const modifier = {};

            if (googleToken.accessToken) {
                modifier.access_token = googleToken.accessToken;
            }

            if (googleToken.refreshToken) {
                modifier.refresh_token = googleToken.refreshToken;
            }

            if (_.isEmpty(modifier)) {
                return user;
            }

            await this.updateOne({ googleId }, { $set: modifier });

            return user;
        }

        const newUser = await this.create({
            createdAt: new Date(),
            name,
            mobile,
            pin,
            displayName
        });

        return _.pick(newUser, UserClass.publicFields());
    }
}
muserSchema.loadClass(MUserClass);
const MUser = mongoose.model('MUser', muserSchema);
module.exports = MUser;

