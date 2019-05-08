const mongoose = require("mongoose");
const logger = require('../logs');
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;
const { Schema } = mongoose;

const mongoSchema = new Schema({
    manufacturer: {
        type: String,
        required: true,
    },
    model: {
        type: String,
        required: true,
    }
});

class Vehicle_BrandsClass {
    static async list({ offset = 0, limit = 10 } = {}) {
      const brands = await this.find({})
        .sort({ active: -1 })
        .skip(offset)
        .limit(limit);
      return { "url":STATIC_HOST,"brands":brands };
    }
    static async add({ manufacturer, model}) {
      return this.create({
        manufacturer,
        model
      });
    }
  
  }
  mongoSchema.loadClass(Vehicle_BrandsClass);
  
  const Vehicle_Brands = mongoose.model('Vehicle_Brands', mongoSchema);
  
  module.exports = Vehicle_Brands;