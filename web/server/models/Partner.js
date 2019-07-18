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
  mobile: {
    type: String,
    required: true,
  },
  email: {
    type: String,
  },
  tag: {
    type: String,
  },
  is_customer: {
    type: Boolean,
    default: true,
  },
  is_service_center: {
    type: Boolean,
    default: true,
  },
  is_admin: {
    type: Boolean,
    default: false,
  },
  active: {
    type: Boolean,
    default: true,
  }
});

class PartnerClass {

  // partner's public fields
  static publicFields() {
    return ['name', 'mobile', 'tag', 'email'];
  }
  static async list({ offset = 0, limit } = {}) {
    const partners = await this.find({ "active": true })
      .select(PartnerClass.publicFields().join(' '))
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url": STATIC_HOST, "partners": partners };
  }

  static async userPartners(loginUser, { offset = 0, limit } = {}) {
    const loginUserPartners = loginUser.partner;
    const partners = await this.find({ _id: { "$in": loginUserPartners }, "active": true })
      .select(PartnerClass.publicFields().join(' '))
      .sort({ active: -1 })
      .skip(offset)
      .limit(limit);
    return { "url": STATIC_HOST, "partners": partners };
  }

  static async add(loginUser, { name, mobile, email, tag, is_customer, is_service_center, is_admin }) {
    console.log("The login user for add partner ",loginUser);
    const  createdPart  = await this.create({
      name,
      mobile,
      email,
      tag,
      is_customer,
      is_service_center,
      is_admin
    });
    console.log("The created partner is ", createdPart );
    console.log("The created partner id is ",  typeof(createdPart._id) );
    loginUser.partner.push(createdPart._id);
    const addUserPartner = await loginUser.save();
        console.log(addUserPartner);
    return addUserPartner;
  }

  static async update(id, req) {
    const updPartner = await this.findByIdAndUpdate(id, { $set: req }, { new: true });
    console.log(updPartner);
    return updPartner;
  }

  static async delete(id) {
    const delPartner = await this.findOneAndUpdate({ "_id": id }, { "$set": { "active": false } }, { new: true });
    //const delPartner = await this.findByIdAndRemove(id);
    console.log(delPartner);
    return delPartner;
  }

}
mongoSchema.loadClass(PartnerClass);

const Partner = mongoose.model('Partner', mongoSchema);

module.exports = Partner;

