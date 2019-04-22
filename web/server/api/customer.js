const express = require('express');
const _ = require('lodash');

const MUser = require('../models/MUser');
const Offer = require('../models/Offer');
const Service = require('../models/Service');
const logger = require('../logs');
const router = express.Router();
const passport = require ('passport');

router.use((req, res, next) => {
  console.log("customer api authenication ");
  passport.authenticate('jwt', { session: false }, (err, user, info) => {
    if (err) {
      console.error(err);
      res.status(4011).send("Unauthorized Access");
    }
    if (info !== undefined) {
      res.status(403).send(info.message);
    } else {
      res.status(200).send("You have succcesfully Authenticated");
    }
  })(req, res, next);
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

router.get('/services', async (req, res) => {
    try {
      const services = await Service.list();
      res.json(services);
    } catch (err) {
      res.json({ error: err.message || err.toString() });
    }
  });


module.exports = router;
