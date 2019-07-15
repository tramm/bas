const express = require('express');
const _ = require('lodash');

const Booking = require('../models/Booking');
const ServiceCenter = require('../models/ServiceCenter');
const Leads = require('../models/Leads');
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


router.get('/bookings', async (req, res) => {
    try {
        const bookings = await Booking.list();
        res.json(bookings);
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

router.post('/bookings/add', async (req, res) => {
    try {
        console.log("The user is ",req.user);
        const bookings = await Booking.add(req.user, req.body);
        res.json({ message: "Successfully Added" });
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/bookings/update', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const bookings = await Booking.update(myParams[i].id, req.body);
        }
        res.json("Updated successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/bookings/delete', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
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

router.post('/leads/update', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const leads = await Leads.update(myParams[i].id, req.body);
        }
        res.json("Updated successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/leads/delete', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const leads = await Leads.delete(myParams[i].id);
        }
        res.json("Deleted successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});


router.get('/serCenters', async (req, res) => {
    try {
        const serCenters = await ServiceCenter.list();
        res.json(serCenters);
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() })
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

router.post('/serCenters/update', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const serCenters = await ServiceCenter.update(myParams[i].id, req.body);
        }
        res.json("Updated successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/serCenters/delete', async (req, res) => {
    try {
        const myParams = req.body;
        for (var i = 0; i < myParams.length; i++) {
            const serCenters = await ServiceCenter.delete(myParams[i].id);
        }
        res.json("Deleted successfully");
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});



module.exports = router;

