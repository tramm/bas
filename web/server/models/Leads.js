/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;
const Booking = require('./Booking');

const mongoSchema = new Schema({
 created_date: {
    type: Date,
    required: true,
 },
 booking: [{type:Schema.ObjectId, ref: 'Booking', required: true}] 
});

class LeadsClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const leads = await this.find({})
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"leads":leads };
  }
  static async add({ booking_id }) {
    console.log("The booking is "+booking_id);
    const booking = await Booking.findById(booking_id);
    const lead =  this.create({
       created_date: new Date(),
       booking
    });
    return lead;
  }
}
mongoSchema.loadClass(LeadsClass);

const Leads = mongoose.model('Leads', mongoSchema);

module.exports = Leads;

