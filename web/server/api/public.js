const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const Partner = require('../models/Partner');
const Booking = require('../models/Booking');
 

router.use((req, res, next) => {
  if (!req.user) {
    res.status(401).json({ error: 'Unauthorized' });
    return;
}
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
module.exports = router;
