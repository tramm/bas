/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

const mongoSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  mobile: {
    type: String,
    required: true,
    unique: true,
  },
  pin: {
    type: Number,
    required: true,
  },
  email: {
    type: String,
    required: true,
  },
  street1: {
    type: String,
    required: true,
  },
  street2: {
    type: String,
    required: true,
  },
  zip: {
    type: String,
    required: true,
  },
  is_customer: {
    type: Boolean,
    default: false,
  },
  is_service_center: {
    type: Boolean,
    default: false,
  },
  is_admin: {
    type: Boolean,
    default: false,
  }
});

class PatnerClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const patners = await this.find({})
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"patners":patners };
  }
  static async add({ name, mobile, pin, email, street1, street2, city, zip, is_customer, is_service_center, is_admin}) {
    if (mobile){
        const user = await this.findOne({ mobile });
        if(user)return user;
        const patner =  this.create({
            name,
            mobile,
            pin,
            email,
            street1,
            street2,
            city,
            zip,
            is_customer,
            is_service_center,
            is_admin
        });
     return patner;
    }else {
        console.log("ERROR in request - no mobile");
        throw new Error('User cannot be created without mobile number');
    }
  }
}
mongoSchema.loadClass(PatnerClass);

const Patner = mongoose.model('Patner', mongoSchema);

module.exports = Patner;

