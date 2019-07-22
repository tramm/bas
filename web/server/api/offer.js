const express = require('express');
const _ = require('lodash');
const Offer = require('../models/Offer');
const categories = require('../models/ServiceCategory');
const logger = require('../logs');
const router = express.Router();
const passport = require('passport');

router.use((req, res, next) => {
    console.log("customer api authenication ",req.user);
    if (!req.user) {
        res.status(401).json({ error: 'Unauthorized' });
        return;
    }
    next();
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
        res.json({ message: "Successfully Added" });
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});
router.post('/offers/list', async (req, res) => {
    try {
        const offerList = await Offer.listByCategory(req.body);
        console.log("In public API");
        console.log(offerList);
        res.json(offerList);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/offers/update', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const offer = await Offer.update(myParams[i].id, req.body);
        }
        res.json("Updated successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/offers/delete', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
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

router.post('/services/update', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const serviceCat = await categories.update(myParams[i].id, req.body);
        }
        res.json("Updated successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/services/delete', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const serviceCat = await categories.delete(myParams[i].id);
        }
        res.json("Deleted successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});


module.exports = router;

