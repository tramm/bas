const mongoose = require('mongoose');
const _ = require('lodash');
const Partner = require('./Partner');
const VehicleBrand = require('./VehicleBrand');
const VehicleModel = require('./VehicleModel');


const { Schema } = mongoose;
const vehicleSchema = new Schema({
    color: {
        type: String,
    },
    variant: {
        type: String,
    },
    registration_Number: {
        type: String,
    },
    tag: {
        type: String,
    },
    year: {
        type: Number,
    },
    active: {
        type: Boolean,
        default: true,
    },
    manufacturer: {
        type: Schema.ObjectId,
        ref: 'VehicleBrand'
    },
    model: {
        type: Schema.ObjectId,
        ref: 'VehicleModel'
    }
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
        unique: true,
    },
    createdAt: {
        type: Date,
    },
    email: {
        type: String,
        unique: true,
    },
    tag: {
        type: String,
    },
    isAdmin: {
        type: Boolean,
        default: false,
    },
    displayName: String,
    vehicle: [vehicleSchema],
    partner: [{ type: Schema.ObjectId, ref: 'Partner' }]
});

class MUserClass {
    // User's public fields
    static publicFields() {
        return ['name', 'mobile', 'tag', 'email'];
    }
    static async list() {
        const populateMUserVehicle = [{ path: "vehicle.manufacturer" }, { path: "vehicle.model" }];
        const users = await this.find({})
            .populate(populateMUserVehicle)
            .sort({ createdAt: -1 });
        return { users };
    }
    static async add({ name, mobile, pin, email, tag, partner_id, vehicle }) {
        if (mobile) {
            const user = await this.findOne({ mobile });
            if (user) return user;
            let partner = await Partner.findById(partner_id);
            console.log(partner);
            const newUser = await this.create({
                createdAt: new Date(),
                name,
                mobile,
                pin,
                email,
                tag,
                partner,
                vehicle
            });
            console.log(newUser.vehicle);
            return newUser;
        } else {
            console.log("ERROR in request - no mobile");
            throw new Error('User cannot be created without mobile number');
        }
    };
    static async update(id, req) {
        const updUser = await MUser.findByIdAndUpdate(id, { $set: req }, { new: true });
        console.log(updUser);
        return updUser;
    }
    static async delete(id) {
        const delUser = await MUser.findByIdAndDelete(id);
        console.log(delUser);
        return delUser;
    }
    static async addUserVehicle(user, vehicle) {
        user.vehicle.push(vehicle);
        const addUserVehicle = await user.save();
        console.log(addUserVehicle);
        return addUserVehicle;
    }
    static async deleteUserVehicle(user, {vehicle_id}) {
        console.log("user is ", user);
        console.log("vehicle is ", vehicle_id);
        user.vehicle.pull(vehicle_id);
        const delUserVeh = await user.save();
        return delUserVeh;
    }
    static async listUserVehicles(user) {
        const populateMUserVehicle = [{ path: "vehicle.manufacturer" }, { path: "vehicle.model" }];
        const users = await this.findById(user._id)
            .populate(populateMUserVehicle)
            .sort({ createdAt: -1 });
        const userVehicles = users.vehicle;
        return { userVehicles };
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

