import sendRequest from './sendRequest';

const BASE_PATH = '/api/v1/public';
/* const userData = {	"mobile":"9739792292",
                    "pin":"1234"
                  }; */

export const getPartners = () =>
  sendRequest(`${BASE_PATH}/partners`, {
    method: 'GET',
  });

export const getLoginCreds = (userData) => 
  sendRequest(`/login`, { 
    method: 'POST',
    body: JSON.stringify(userData)
  });