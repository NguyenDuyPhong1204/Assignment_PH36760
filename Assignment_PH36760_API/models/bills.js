const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Bills = new Schema({
    userId: {type: Schema.Types.ObjectId, ref: "users"},
    total: {type: Number},
    address: {type: String}
},{timestamps: true})

module.exports = mongoose.model('bills', Bills);