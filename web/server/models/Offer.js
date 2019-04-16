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
  }
});

class OfferClass {
  static async list({ offset = 0, limit = 10 } = {}) {
    const offers = await this.find({})
      .sort({ createdAt: -1 })
      .skip(offset)
      .limit(limit);
    return { "url":STATIC_HOST,"offers":offers };
  }
  static async add({ name, description, url, price  }) {
    return this.create({
      name,
      description,
      url,
      price,
      createdAt: new Date()
    });
  }
}
mongoSchema.loadClass(OfferClass);

const Offer = mongoose.model('Offer', mongoSchema);

module.exports = Offer;

