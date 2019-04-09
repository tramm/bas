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

class OfferClass {
    static async list({offset = 0, limit = 10} = {}){
    const offers = await this.find({})
        .sort({ createdAt: -1 })
        .skip(offset)
        .limit(limit);
    return { offers };    
    }
    static async add({ name, price}) {
        return this.create({
          name,
          price,
          createdAt: new Date()
        });
      }
}
mongoSchema.loadClass(OfferClass);

const Offer = mongoose.model('Offer', mongoSchema);

module.exports = Offer;

