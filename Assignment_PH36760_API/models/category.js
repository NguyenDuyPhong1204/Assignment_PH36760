const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Categorys = new Schema({
    title: {type: String},
    imageCategory: {type: String}
},{
    timestamps: true
});

module.exports = mongoose.model('categorys', Categorys);