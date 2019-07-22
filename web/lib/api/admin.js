import sendRequest from './sendRequest';

const BASE_PATH = '/api/v1/public';

export const getPartners = () =>
  sendRequest(`${BASE_PATH}/partners`, {
    method: 'GET',
  });

export const getTodayBookings = () =>
  sendRequest(`${BASE_PATH}/bookingsToday`, {
    method: 'GET',
  });

export const getLoginCreds = (userData) =>
  sendRequest(`/auth`, {
    method: 'POST',
    body: JSON.stringify(userData)
  });