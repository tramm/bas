import 'isomorphic-unfetch';
import getRootUrl from './getRootUrl';

export default async function sendRequest(path, opts = {}) {
  const headers = Object.assign({}, opts.headers || {}, {
    'Content-type': 'application/json; charset=UTF-8',
  });

  console.log("The headers are", opts);
  const response = await fetch(
    `${getRootUrl()}${path}`,
    Object.assign({ method: 'POST', credentials: 'same-origin' }, opts, { headers }),
  );
  console.log("The response object", response);
  if (response.status && response.status !='200') {
    throw new Error("Unable to fetch Data");
  }
  const data = await response.json();
  console.log("The data returned", data);
  if (data.error) {
    return data;
    // throw new Error(data.error);
  }

  return data;
}