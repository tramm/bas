/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

/* const ServiceCategory = require('./ServiceCategory');
 */const MUser = require('./MUser');
const Offer = require('./Offer');
const Vehicle = require('./Vehicle');


const mongoSchema = new Schema({
  booking_contact_id: {
    type: String,
    required: true,
  },
  status: {
    type: Boolean,
  },
     offer:[{type: Schema.ObjectId, ref: 'Offer', required: true}],
     muser:[{type: Schema.ObjectId, ref: 'MUser', required: true}],
/*   category:[{ type: Schema.ObjectId, ref: 'ServiceCategory',required: true }],
 */  vehicle:[{type: Schema.ObjectId, ref: 'Vehicle', require: true}]
});

class BookingClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const bookings = await this.find({})
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"bookings":bookings };
  }
  static async add({offers_id, muser_id, booking_contact_id, status, vehicle_id}) {
    console.log("the offer is "+offers_id);
    console.log("the user is "+muser_id);
    /* console.log("the category is "+category_id); */
    console.log("the vehicle is "+vehicle_id);
    const vehicle =  await Vehicle.findById(vehicle_id);
    const muser = await MUser.findById(muser_id);
    const offer = await Offer.findById(offers_id);
    /* const category = await ServiceCategory.findById(category_id); */
    console.log(vehicle);
    console.log(muser);
    console.log(offer);
    /* console.log(category); */
    let book =  this.create({
      booking_date : new Date(),
      service_date : new Date(),
      vehicle,
      muser,
      offer,
      /* category, */
      booking_contact_id,
      status
    });
    console.log(book);
    return book;
  }
}
mongoSchema.loadClass(BookingClass);

const Booking = mongoose.model('Booking', mongoSchema);

module.exports = Booking;

