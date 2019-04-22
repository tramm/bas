const express = require('express');
const _ = require('lodash');

const MUser = require('../models/MUser');
const Offer = require('../models/Offer');
const Vehicle = require('../models/Vehicle');
const Booking = require('../models/Booking');
const Patner = require('../models/Patner');
const Leads = require('../models/Leads');

const categories = require('../models/ServiceCategory');
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

router.post('/user/add', async (req, res) => {
  try {
    const user = await MUser.add(req.body);
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

router.get('/patners', async (req, res) => {
  try {
    const patners = await Patner.list();
    res.json(patners);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/patners/add', async (req, res) => {
  try {
    const patners = await Patner.add(req.body);
    res.json(patners);
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



module.exports = router;
