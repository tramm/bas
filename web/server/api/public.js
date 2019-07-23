const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const Partner = require('../models/Partner');
const Booking = require('../models/Booking');
const categories = require('../models/ServiceCategory');
 

router.use((req, res, next) => {
  next();
});
router.get('/partners', async (req, res) => {
  try {
    console.log("Being caled from web fron end");
    const partners = await Partner.list();
    res.json(partners);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/bookingsToday', async (req, res) => {
    try {
        const bookings = await Booking.todayBookingslist();
        res.json(bookings);
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

module.exports = router;
