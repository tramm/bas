/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;
const Partner = require("./Partner");

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
    default: true
  },
  partner:[{type: Schema.ObjectId, ref: "Partner", required: true}]
});

class ServiceCenterClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    var populateSerCenterQuery = [{path:'partner'}];
    const SerCenter = await this.find({})
      .populate(populateSerCenterQuery)
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"SerCenter":SerCenter };
  }
  static async add({ name, primary_contact, secondary_contact, partner_id, active }) {
    console.log("the partner is "+partner_id);
    const partner = await Partner.findById(partner_id);
    console.log(partner);
    const serCenter =  this.create({
      name,
      primary_contact,
      secondary_contact,
      partner,
      active
    });
    console.log("created sercenter partner "+serCenter.partner);
    return serCenter;
  }

  static async update(id, req) {
    const updServiceCenter = await this.findByIdAndUpdate(id, {$set: req});
    console.log(updServiceCenter);
    return updServiceCenter;
  }

  static async delete(id) {
    const delServiceCenter = await this.findByIdAndRemove(id);
    console.log(delServiceCenter);
    return delServiceCenter;
  }

}
mongoSchema.loadClass(ServiceCenterClass);

const ServiceCenter = mongoose.model('ServiceCenter', mongoSchema);

module.exports = ServiceCenter;

