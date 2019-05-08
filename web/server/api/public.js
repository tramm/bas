const express = require('express');
const _ = require('lodash');

const MUser = require('../models/MUser');
const Offer = require('../models/Offer');
const Vehicle = require('../models/Vehicle');
const Booking = require('../models/Booking');
const Partner = require('../models/Partner');
const Leads = require('../models/Leads');

const categories = require('../models/ServiceCategory');
const ServiceCenter = require('../models/ServiceCenter');
const VehicleBrands = require('../models/Vehicle_Brands');

const logger = require('../logs');
const router = express.Router();


router.use((req, res, next) => {
  next();
});

router.get('/users', async (req, res) => {
  try {
    const muser = await MUser.list();
    res.json(muser);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/add', async (req, res) => {
  try {
    const user = await MUser.add(req.body);
    res.json(user);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/update', async (req, res) => {
  try {
       const myParams = req.body;
       const user = await MUser.update(myParams.id, req.body);
       res.json(user);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/delete', async (req, res) => {
  try {
    const myParams = req.body;
    const user = await MUser.delete(myParams.id);
    res.json(user);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


router.get('/offers', async (req, res) => {
  try {
    console.log(Offer);
    const books = await Offer.list();
    res.json(books);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/offers/add', async (req, res) => {
  try {
    let val = Object.assign({ userId: 1239 }, req.body);
    console.log(val);
    console.log(req);
    const offer = await Offer.add(req.body);
    res.json(offer);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});
router.get('/offers/list', async (req, res) => {
  try {
    const offerList = await Offer.listByCategory(req.body);
    console.log("In public API");
    console.log(offerList);
    res.json(offerList);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/offers/update', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const offer = await Offer.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/offers/delete', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const offer = await Offer.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/services', async (req, res) => {
  try {
    const services = await categories.list();
    res.json(services);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/services/add', async (req, res) => {
  try {
    let val = Object.assign({ userId: 1239 }, req.body);
    console.log(val);
    console.log(req);
    const offer = await categories.add(req.body);
    res.json(offer);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/services/update', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const serviceCat = await categories.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/services/delete', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const serviceCat = await categories.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


router.get('/vehicles', async (req, res) => {
  try {
    const vehicles = await Vehicle.list();
    res.json(vehicles);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/vehicles/add', async (req, res) => {
  try {
    const vehicles = await Vehicle.add(req.body);
    res.json(vehicles);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/vehicles/update', async (req, res) => {
  try {
    const myParams = req.body;
    console.log("The body values are "+myParams[0].model);
    console.log("The body values are "+myParams.length);
    for(var i = 0; i<myParams.length; i++){
        const vehicles = await Vehicle.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/vehicles/delete', async (req, res) => {
  try {
    const myParams = req.body;
    console.log("The body values are "+myParams[0].model);
    console.log("The body values are "+myParams.length);
    for(var i = 0; i<myParams.length; i++){
        const vehicles = await Vehicle.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/partners', async (req, res) => {
  try {
    const partners = await Partner.list();
    res.json(partners);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/partners/add', async (req, res) => {
  try {
    const partners = await Partner.add(req.body);
    res.json({message: "Successfully Added"});
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/partners/update', async (req, res) => {
  try {
    const myParams = req.body;
    const partner = await Partner.update(myParams.id, req.body);
    res.json({message: "Successfully Updated"});
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/partners/delete', async (req, res) => {
  try {
    const myParams = req.body;
    const partner = await Partner.delete(myParams.id);
    res.json({message: "Successfully Deleted"});
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


router.get('/bookings', async (req, res) => {
  try {
    const bookings = await Booking.list();
    res.json(bookings);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/bookings/add', async (req, res) => {
  try {
    const bookings = await Booking.add(req.body);
    res.json(bookings);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/bookings/update', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const bookings = await Booking.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/bookings/delete', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const bookings = await Booking.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


router.get('/leads', async (req, res) => {
  try {
    const leads = await Leads.list();
    res.json(leads);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/leads/add', async (req, res) => {
  try {
    const leads = await Leads.add(req.body);
    res.json(leads);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/leads/update', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const leads = await Leads.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/leads/delete', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const leads = await Leads.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


router.get('/serCenters', async (req,res) => {
  try{
    const serCenters = await ServiceCenter.list();
    res.json(serCenters);
  } catch (err) {
    logger.error(err);
    res.json({error: err.message || err.toString() })
  }
})

router.post('/serCenters/add', async (req, res) => {
  try {
    const serCenters = await ServiceCenter.add(req.body);
    res.json(serCenters);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.put('/serCenters/update', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const serCenters = await ServiceCenter.update(myParams[i].id, req.body);
    }
    res.json("Updated successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.delete('/serCenters/delete', async (req, res) => {
  try {
    const myParams = req.body;
    for(var i = 0; i<myParams.length; i++){
        const serCenters = await ServiceCenter.delete(myParams[i].id);
    }
    res.json("Deleted successfully");
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/vehicleBrands', async (req,res) => {
  try{
    const vehicleBrands = await VehicleBrands.list();
    res.json(vehicleBrands);
  } catch (err) {
    logger.error(err);
    res.json({error: err.message || err.toString() })
  }
})

router.post('/vehicleBrands/add', async (req, res) => {
  try {
    const vehicleBrands = await VehicleBrands.add(req.body);
    res.json(vehicleBrands);
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});


module.exports = router;
