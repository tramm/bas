
const http  = require('http');

const key = "270723AeyRd1KseIak5ca4968a"
const options = {
  "method": "GET",
  "hostname": "api.textlocal.in",
  "port": null,
  "path": "/send/?numbers=919840021822&apikey=le9v1YyRX88-kdWfT9F8eS9p3A4m77ljf1IVpodXVi&message=Hello!%20This%20is%20a%20test%20message",
  "headers": {}
};

const req = http.request(options, function (res) {
  var chunks = [];

  res.on("data", function (chunk) {
    chunks.push(chunk);
  });

  res.on("end", function () {
    var body = Buffer.concat(chunks);
    console.log(body.toString());
  });
});

req.end();