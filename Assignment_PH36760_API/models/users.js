const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Users = new Schema({
    emailUser: { type: String, unique: true },
    username: { type: String, unique: true },
    password: { type: String },
    fullname: { type: String },
    imageUser: { type: String },
    addressUser: { type: String },
    phoneNumber: {type : String}
}, {
    timestamps: true
});

module.exports = mongoose.model('users', Users);