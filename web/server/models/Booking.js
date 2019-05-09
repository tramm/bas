/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

/* const ServiceCategory = require('./ServiceCategory');*/
const Partner = require('./Partner');
const Offer = require('./Offer');
const Vehicle = require('./Vehicle');


const mongoSchema = new Schema({
  dateOfService: {
    type: String,
    required: true,
  },
  offer: {
    type: Schema.ObjectId, 
    ref: 'Offer'
  },
  partner: {
    type: Schema.ObjectId, 
    ref: 'Partner'
  },
  vehicle: {
    type: Schema.ObjectId, 
    ref: 'Vehicle'
  }
});

class BookingClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    var populateBookingQuery = [{path:'vehicle'}, {path:'offer'}, {path:'partner'}];
    const bookings = await this.find({})
      .populate(populateBookingQuery)
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"bookings":bookings };
  }
  static async add({dateOfService, offers_id, partner_id, vehicle_id}) {
    console.log("the offer is "+offers_id);
    console.log("the user is "+partner_id);
    /* console.log("the category is "+category_id); */
    console.log("the vehicle is "+vehicle_id);
    const vehicle =  await Vehicle.findById(vehicle_id);
    const partner = await Partner.findById(partner_id);
    const offer = await Offer.findById(offers_id);
    /* const category = await ServiceCategory.findById(category_id); */
    console.log(vehicle);
    console.log(partner);
    console.log(offer);
    /* console.log(category); */
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
    const delBooking = await this.findByIdAndRemove(id);
    console.log(delBooking);
    return delBooking;
  }

}
mongoSchema.loadClass(BookingClass);

const Booking = mongoose.model('Booking', mongoSchema);

module.exports = Booking;

