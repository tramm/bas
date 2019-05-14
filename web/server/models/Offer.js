/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const ServiceCategory = require('./ServiceCategory');

const { Schema } = mongoose;
const mongoSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  url: {
    type: String,
    required: true,
  },
  description: {
    type: String,
    required: true,
  },
  createdAt: {
    type: Date,
    required: true,
  },
  price: {
    type: Number,
  },
  serviceCenter_Id: {
    type: String,
  },
  serviceCenter_Name: {
    type: String,
  },
  category: [{ type: Schema.ObjectId, ref: 'ServiceCategory', required: true }]
});

class OfferClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const offers = await this.find({})
      .sort({ createdAt: -1 })
      .skip(offset)
      .limit(limit);
    return { "url": STATIC_HOST, "offers": offers };
  }
  static async listByCategory({ category_id }) {
    console.log(category_id);
    const offerList = await this.find({ category: category_id })
    return { "url": STATIC_HOST, "offerList": offerList };
  }
  static async add({ name, description, url, price,serviceCenter_Id, serviceCenter_Name, category_id }) {
    console.log("the category is " + category_id);
    let category = await ServiceCategory.findById(category_id);
    console.log(category);
    let ret = this.create({
      name,
      description,
      url,
      price,
      createdAt: new Date(),
      serviceCenter_Id,
      serviceCenter_Name,
      category
    });
    console.log(ret.category);
    return ret;
  }

  static async update(id, req) {
    const updOffer = await this.findByIdAndUpdate(id, {$set: req});
    console.log(updOffer);
    return updOffer;
  }

  static async delete(id) {
    const delOffer = await this.findByIdAndRemove(id);
    console.log(delOffer);
    return delOffer;
  }

}
mongoSchema.loadClass(OfferClass);
const Offer = mongoose.model('Offer', mongoSchema);
module.exports = Offer;

