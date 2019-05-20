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
  user: {
    type: Schema.ObjectId, 
    ref: 'User'
  }
});

class BookingClass {
  static async list({vehicleId},{ offset = 0, limit = 10 } = {}) {
    var populateBookingQuery = [{path:'user', populate:[{path: 'vehicle.model'}, {path: 'vehicle.manufacturer'}]}, {path:'offer'}, {path:'partner'}];
    const bookings = await this.find({"active": true})
      .populate(populateBookingQuery)
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
      const vehicle = bookings[0].user.vehicle;
      const bookedVehicle = vehicle.filter(obj =>{
        return obj._id == vehicleId
      })
      console.log("The Booked vehicle is ",bookedVehicle);
      bookings[0].user.vehicle = [];
      bookings[0].user.vehicle.push(bookedVehicle[0]);
      console.log("The booking list is ",bookings);
    return { "url":STATIC_HOST,"bookings":bookings };
  }
  static async add(loginUser, {dateOfService, offers_id, partner_id, vehicle_id}) {
    console.log("the offer is "+offers_id);
    console.log("the partner is "+partner_id);
    console.log("the user id is "+loginUser._id);
    console.log("the vehicle id is "+vehicle_id);
    const user =  await User.findOne({"_id": loginUser._id,"vehicle._id": vehicle_id});
   // const vehicle = user.vehicle.find(veh => veh._id == vehicle_id);
   // console.log("the vehicle is "+vehicle);
    const partner = await Partner.findById(partner_id);
    const offer = await Offer.findById(offers_id);
    console.log(partner);
    console.log(offer);
    console.log(user);
    let book =  this.create({
      dateOfService,
      user,
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

