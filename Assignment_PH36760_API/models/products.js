const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Products = new Schema({
    productName: {type: String},
    productImage: {type: String},
    productPrice: {type: Number},
    description: {type: String},
    quantity: {type: Number},
    categoryID: {type: String},
    isFavorite:{type: Boolean, default: false},
    isCart: {type: Boolean, default: false}
},{
    timestamps: true
});

module.exports = mongoose.model('products', Products)