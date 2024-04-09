const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const BillDetails = new Schema({
    billId : {type: String},
    productId: {type: String},
    quantity: {type: Number},
},{
    timestamps: true
});

module.exports = mongoose.model('billdetails', BillDetails);