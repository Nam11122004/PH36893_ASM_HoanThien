const mongoose = require("mongoose");
const Schema = mongoose.Schema;

const SanPhams = new Schema({
    tensp: { type: String},
    mota: { type: String},
    gia: { type: Number, default: 0 },
    hinhanh: { type: String },
}, {
    timestamps: true,
});
module.exports = mongoose.model("sanpham", SanPhams);