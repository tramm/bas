const mongoose = require('mongoose');
const logger = require('../logs');
require('dotenv').config();
const { Schema } = mongoose;

const vehicleBrandSchema = new Schema({
    name: {
      type: String,  
      unique: true
    },
    active: {
        type: Boolean,
        default: true
    }
  });

  class VehicleBrandClass{
    static async list() {
        const brands = await this.find({"active": true})
            .sort({ createdAt: -1 });
        return { brands };
    }
    static async add({name}){
        if(name){
            const brand = await this.findOne({ name });
            if(brand)return brand;
            const newBrand = await this.create({
                name
            });
            console.log(newBrand);
            return newBrand;
        }else{
            console.log("ERROR in request - no brand name");
            throw new Error('Brand cannot be created without name');
        }
    };
  }

  vehicleBrandSchema.loadClass(VehicleBrandClass);
  const VehicleBrand = mongoose.model('VehicleBrand',vehicleBrandSchema);
  module.exports = VehicleBrand;