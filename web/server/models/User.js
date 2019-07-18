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

const userSchema = new Schema({

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
    },
    tag: {
        type: String,
    },
    isAdmin: {
        type: Boolean,
        default: false,
    },
    active: {
        type: Boolean,
        default: true,
    },
    displayName: String,
    vehicle: [vehicleSchema],
    partner: [{ type: Schema.ObjectId, ref: 'Partner' }]
});

class UserClass {
    // User's public fields
    static publicFields() {
        return ['name', 'mobile', 'tag', 'email'];
    }
    static async list() {
        const populateMUserVehicle = [{ path: "vehicle.manufacturer" }, { path: "vehicle.model" }];
        const users = await this.find({"active": true})
            .populate(populateMUserVehicle)
            .sort({ createdAt: -1 });
        return { users };
    }
    static async add({ name, mobile, pin, email, tag, partner, vehicle }) {
        if (mobile) {
            const user = await this.findOne({ mobile });
            if (user) return user;
            /* let partner = await Partner.findById(partner_id);
            console.log(partner); */
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
    static async update(user, req) {
        const updUser = await User.findByIdAndUpdate(user._id, { $set: req }, { new: true });
        console.log(updUser);
        return updUser;
    }
    static async delete(id) {
        const delUser = await User.findByIdAndDelete(id);
        console.log(delUser);
        return delUser;
    }
    static async addUserVehicle(user, vehicle) {
        user.vehicle.push(vehicle);
        const addUserVehicle = await user.save();
        console.log(addUserVehicle);
        return addUserVehicle;
    }
    static async updateUserVehicle(user, vehicle) {
        console.log("user is ", user._id);
        console.log("vehicle is ", vehicle._id);
        const updUser = await this.findOneAndUpdate({"_id": user._id,"vehicle._id": vehicle._id}, {"$set": {"vehicle.$": vehicle}}, {new: true});
        console.log("The updated userVehicle is ",updUser);
        return updUser;
    }
    static async deleteUserVehicle(user, {vehicle_id}) {
        console.log("user is ", user);
        console.log("vehicle is ", vehicle_id);
        const inactivateUserVeh = await this.findOneAndUpdate({"_id": user._id,"vehicle._id": vehicle_id}, {"$set": {"vehicle.$.active": false}}, {new: true});
        console.log("The updated userVehicle is ",inactivateUserVeh);
        return inactivateUserVeh;
    }
    static async listUserVehicles(user) {
        const populateMUserVehicle = [{ path: "vehicle.manufacturer" }, { path: "vehicle.model" }];
        const users = await this.findById(user._id)
            .populate(populateMUserVehicle)
            .sort({ createdAt: -1 });
        const userVehicles = users.vehicle;
        const userActiveVehicles = userVehicles.filter(obj => {
            return obj.active === true
        })
        return { userActiveVehicles };
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
userSchema.loadClass(UserClass);
const User = mongoose.model('User', userSchema);
module.exports = User;

