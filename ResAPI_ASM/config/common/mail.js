var nodemailer = require("nodemailer");

const transporter = nodemailer.createTransport({
    service: "gmail",
    auth: {
        email: "namden9399@gmail.com",
        password: "scsa mila bsaq veck"
    },
});

module.exports = transporter;
