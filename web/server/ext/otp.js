const Speakeasy = require("speakeasy");
const Sms = require('./sms');
class Otp {

    static async generate_secret() {
        secret = Speakeasy.generateSecret({ length: 6 });
        return secret.base32;
    };
    static async generate_otp() {
        console.log(secret);
        //secret_val = await Speakeasy.generateSecret({ length: 6 }); 
        return {
            "token": Speakeasy.totp({
                secret: secret,
                encoding: "base32"
            }),
            "remaining": (30 - Math.floor((new Date()).getTime() / 1000.0 % 30))
        };
    };
    static async validate({ token, secret }) {
        return Speakeasy.totp.verify({
            secret: secret,
            encoding: "base32",
            token: token,
            window: 0
        });
    };
};
module.exports = Otp;
