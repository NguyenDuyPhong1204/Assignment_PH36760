const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const Favorites = new Schema({
    userId: {type: Schema.Types.ObjectId, ref: "users"},
    productId: {type: Schema.Types.ObjectId, ref: "products"},
},{
    timestamps: true
});

module.exports = mongoose.model('favorites', Favorites)