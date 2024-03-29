/* eslint-disable no-use-before-define */

const mongoose = require('mongoose');
const logger = require('../logs');
const { Schema } = mongoose;
require('dotenv').config();
const STATIC_HOST = process.env.STATIC_WEB_HOST;

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
    active: {
        type: Boolean,
        default: true,
    }
});

class ServiceCategoryClass {
    static async list({ offset = 0, limit = 10 } = {}) {
        const categories = await this.find({"active": true})
            .sort({ createdAt: -1 })
            .skip(offset)
            .limit(limit).lean();
        return { "url": STATIC_HOST, "categories": categories };
    }
    static async add({ name, description, url }) {
        return this.create({
            name,
            description,
            url,
            createdAt: new Date()
        });
    }

    static async update(id, req) {
        const updCategories = await this.findByIdAndUpdate(id, {$set: req});
        console.log(updCategories);
        return updCategories;
      }
    
    static async delete(id) {
        const delCategories = await this.findOneAndUpdate({"_id": id}, {"$set": {"active": false}}, {new: true});
        //const delCategories = await this.findByIdAndRemove(id);
        console.log(delCategories);
        return delCategories;
      }
    
}
mongoSchema.loadClass(ServiceCategoryClass);

const ServiceCategory = mongoose.model('ServiceCategory', mongoSchema);

module.exports = ServiceCategory;

