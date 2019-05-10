const publicApi = require('./public');
const userApi = require('./user');
const serviceApi = require('./service.js');
const offerApi = require('./offer');
const vehicleApi = require('./vehicle');


function api(server) {
  server.use('/api/v1/public', publicApi);
  server.use('/api/v1/user', userApi);
  server.use('/api/v1/admin', adminApi);
  server.use('/api/v1/service', serviceApi);
  server.use('/api/v1/offer', offerApi);
  server.use('/api/v1/vehicle', vehicleApi);
}

module.exports = api;
