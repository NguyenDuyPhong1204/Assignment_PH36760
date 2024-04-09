var nodemailer = require("nodemailer");
const transporter = nodemailer.createTransport({
    service:'gmail',
    auth: {
        user: "nguyenduyphong12122004@gmail.com",
        pass: "Phong1204"
    }
})

module.exports = transporter;