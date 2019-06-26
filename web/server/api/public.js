const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();
const Partner = require('../models/Partner');
 

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
module.exports = router;
