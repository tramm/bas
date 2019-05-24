/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

/* const ServiceCategory = require('./ServiceCategory');*/
const Partner = require('./Partner');
const Offer = require('./Offer');
//const Vehicle = require('./Vehicle');
const User = require('./User');

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

const mongoSchema = new Schema({
  dateOfService: {
    type: String,
    required: true,
  },
  active: {
    type: Boolean,
    default: true,
  }, 
  offer: {
    type: Schema.ObjectId, 
    ref: 'Offer'
  },
  partner: {
    type: Schema.ObjectId, 
    ref: 'Partner'
  },
  vehicle: [vehicleSchema]
});

class BookingClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    var populateBookingQuery = [{path:'vehicle.model'},{path:'vehicle.manufacturer'}, {path:'offer'}, {path:'muser'}];
    const bookings = await this.find({"active": true})
      .populate(populateBookingQuery)
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"bookings":bookings };
  }
  static async add(loginUser, {dateOfService, offers_id, partner_id, vehicle_id}) {
    console.log("the offer is "+offers_id);
    console.log("the partner is "+partner_id);
    console.log("the user id is "+loginUser._id);
    console.log("the vehicle id is "+vehicle_id);
    const user =  await User.findById(loginUser._id);
    const vehicle = user.vehicle.find(veh => veh._id == vehicle_id);
    const partner = await Partner.findById(partner_id);
    const offer = await Offer.findById(offers_id);
    console.log(partner);
    console.log(offer);
    let book =  this.create({
      dateOfService,
      vehicle,
      partner,
      offer,
    });
    console.log(book);
    return book;
  }

  static async update(id, req) {
    const updBooking = await this.findByIdAndUpdate(id, {$set: req});
    console.log(updBooking);
    return updBooking;
  }

  static async delete(id) {
    const delBooking = await this.findOneAndUpdate({"_id": id}, {"$set": {"active": false}}, {new: true});
    //const delBooking = await this.findByIdAndRemove(id);
    console.log(delBooking);
    return delBooking;
  }

}
mongoSchema.loadClass(BookingClass);

const Booking = mongoose.model('Booking', mongoSchema);

module.exports = Booking;

