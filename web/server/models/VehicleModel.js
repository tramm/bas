const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const { Schema } = mongoose;

const VehicleBrand = require('./VehicleBrand'); 

const vehicleModelSchema = new Schema({
    name: {
      type: String,  
    },
    brand: {
        type: Schema.ObjectId, 
        ref: 'VehicleBrand'
    }
  });

  class VehicleModelClass{
    static async list() {
        const models = await this.find({})
            .sort({ createdAt: -1 });
        return { models };
    }
    static async add({name, brand_id}){
            console.log(brand_id);
            const brand = await VehicleBrand.findById(brand_id); 
            const newModel = await this.create({
                name,
                brand
            });
            console.log(newModel.brand);
            return newModel;
  };
  static async listByBrand({ brand_id }) {
    console.log(brand_id);
    const modelList = await this.find({ brand: brand_id })
    return  modelList;
  }
};

  vehicleModelSchema.loadClass(VehicleModelClass);
  const VehicleModel = mongoose.model('VehicleModel',vehicleModelSchema);
  module.exports = VehicleModel;