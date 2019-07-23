import sendRequest from './sendRequest';

const BASE_PATH = '/api/v1';

export const getPartners = () =>
  sendRequest(`${BASE_PATH}/partners`, {
    method: 'GET',
  });

export const getTodayBookings = () =>
  sendRequest(`${BASE_PATH}/service/bookings`, {
    method: 'GET',
  });

export const getLoginCreds = (userData) =>
  sendRequest(`/login`, {
    method: 'POST',
    body: JSON.stringify(userData)
  });