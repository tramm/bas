/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;
const Patner = require("./Patner");

const mongoSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  primary_contact: {
    type: String,
    required: true,
  },
  secondary_contact: {
    type: String,
    required: true,
  },
  active: {
    type: Boolean,
    default: false
  },
  patner:[{type: Schema.ObjectId, ref: "Patner", required: true}]
});

class ServiceCenterClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const SerCenter = await this.find({})
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"SerCenter":SerCenter };
  }
  static async add({ name, primary_contact, secondary_contact, patner_id, active }) {
    console.log("the patner is "+patner_id);
    const patner = await Patner.findById(patner_id);
    console.log(patner);
    const serCenter =  this.create({
      name,
      primary_contact,
      secondary_contact,
      patner,
      active
    });
    console.log("created sercenter patner "+serCenter.patner);
    return serCenter;
  }
}
mongoSchema.loadClass(ServiceCenterClass);

const ServiceCenter = mongoose.model('ServiceCenter', mongoSchema);

module.exports = ServiceCenter;

