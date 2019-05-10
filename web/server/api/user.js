const express = require('express');
const _ = require('lodash');

const MUser = require('../models/User');
const Partner = require('../models/Partner');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');

router.use((req, res, next) => {
  console.log("customer api authenication ");
  passport.authenticate('jwt', { session: false }, (err, user, info) => {
    if (err) {
      console.error(err);
      res.status(401).send("Unauthorized Access");
      return;
    }
    if (info !== undefined) {
      console.log(req);
      console.log(info.message);
      res.status(403).send({ "error": info.message });
      return;
    }
    console.log(user);
    req.user = user;
    next();
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

router.post('/users/add', async (req, res) => {
  try {
    const user = await MUser.add(req.body);
    res.json({ message: "Successfully Added" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/update', async (req, res) => {
  try {
    const myParams = req.body;
    const user = await MUser.update(myParams.id, req.body);
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/users/delete', async (req, res) => {
  try {
    const myParams = req.body;
    const user = await MUser.delete(myParams.id);
    res.json({ message: "Successfully Deleted" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.get('/listUserVehicles', async (req, res) => {
  try {
    const vehicles = await MUser.listUserVehicles(req.user);
    res.json(vehicles);
  } catch (err) {
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/addUserVehicle', async (req, res) => {
  try {
    const vehicles = await MUser.addUserVehicle(req.user, req.body);
    res.json({ message: "Successfully Added" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/updateUserVehicle', async (req, res) => {
  try {
    const myParams = req.body;
    console.log("The body values are " + myParams[0].model);
    console.log("The body values are " + myParams.length);
    for (var i = 0; i < myParams.length; i++) {
      const vehicles = await Vehicle.update(myParams[i].id, req.body);
    }
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/deleteUserVehicle', async (req, res) => {
  try {
    const myParams = req.body;
    for (var i = 0; i < myParams.length; i++) {
      const vehicles = await MUser.deleteUserVehicle(req.user, myParams[i]);
    }
    res.json({ message: "Successfully Deleted" });
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
    res.json({ message: "Successfully Added" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/partners/update', async (req, res) => {
  try {
    const myParams = req.body;
    const partner = await Partner.update(myParams.id, req.body);
    res.json({ message: "Successfully Updated" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

router.post('/partners/delete', async (req, res) => {
  try {
    const myParams = req.body;
    const partner = await Partner.delete(myParams.id);
    res.json({ message: "Successfully Deleted" });
  } catch (err) {
    logger.error(err);
    res.json({ error: err.message || err.toString() });
  }
});

module.exports = router;

