const mongoose = require('mongoose');
const Scheme = mongoose.Schema;

const Accounts = new Scheme({
    email: {type: String,unique: true, maxLength: 255},
    password: {type: String, maxLength: 255},
},{
    timestamps: true
})
module.exports = mongoose.model('account',Accounts)