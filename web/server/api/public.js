const express = require('express');
const _ = require('lodash');
const logger = require('../logs');
const router = express.Router();


router.use((req, res, next) => {
  next();
});

module.exports = router;
