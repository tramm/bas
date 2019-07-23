const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const Partner = require('../models/Partner');
const Booking = require('../models/Booking');
const categories = require('../models/ServiceCategory');
 

router.use((req, res, next) => {
  console.log("service api authenication ");
  if (req.user) {
      next();
  } else {
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
