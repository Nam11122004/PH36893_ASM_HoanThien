const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Carts = new Scheme({
    accountId: {
        type: mongoose.Schema.Types.ObjectId,
        ref: 'Account',
        required: true
    },
    items: [{
        productId: {
            type: mongoose.Schema.Types.ObjectId,
            ref: 'SanPham',
            required: true
        },
        quantity: {
            type: Number,
            default: 1
        }
    }],
    createdAt: {
        type: Date,
        default: Date.now
    }
},{
    timestamps: true
})
module.exports = mongoose.model('cart',Carts)
