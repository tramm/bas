/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');

const { Schema } = mongoose;

const mongoSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
 
  createdAt: {
    type: Date,
    required: true,
  },
  // price in dollars
  price: {
    type: Number,
    required: true,
  }
});

class ServiceClass {
    static async list({offset = 0, limit = 10} = {}){
    const services = await this.find({})
        .sort({ createdAt: -1 })
        .skip(offset)
        .limit(limit);
    return { services };    
    }
}
mongoSchema.loadClass(ServiceClass);

const Service = mongoose.model('Service', mongoSchema);

module.exports = Service;

