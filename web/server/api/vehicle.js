const express = require('express');
const _ = require('lodash');

const VehicleBrand = require('../models/VehicleBrand');
const VehicleModel = require('../models/VehicleModel');

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

router.get('/vehicleBrands', async (req, res) => {
    try {
        const vehicleBrand = await VehicleBrand.list();
        res.json(vehicleBrand);
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() })
    }
})

router.post('/vehicleBrands/add', async (req, res) => {
    try {
        const vehicleBrand = await VehicleBrand.add(req.body);
        res.json({ message: "Successfully Added" });
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.get('/vehicleModels', async (req, res) => {
    try {
        const vehicleModel = await VehicleModel.list();
        res.json(vehicleModel);
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() })
    }
})

router.post('/vehicleModels/add', async (req, res) => {
    try {
        const vehicleModel = await VehicleModel.add(req.body);
        res.json({ message: "Successfully Added" });
    } catch (err) {
        logger.error(err);
        res.json({ error: err.message || err.toString() });
    }
});

router.post('/vehicleModels/listByBrand', async (req, res) => {
    try {
        const modelList = await VehicleModel.listByBrand(req.body);
        console.log(modelList);
        res.json(modelList);
    } catch (err) {
        res.json({ error: err.message || err.toString() });
    }
});


module.exports = router;

