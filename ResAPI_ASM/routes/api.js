var express = require('express');
var router = express.Router();

const Account = require('../models/accounts')
const SanPham = require("../models/sanpham");
const GioHang = require('../models/carts'); 


const transporter= require('../config/common/mail');
const JWT = require('jsonwebtoken');
const SECRETKEY = "FPTPOLYTECHNIC"
const nodemailer = require('nodemailer');

router.post('/register-send-email', async (req, res) => {
    try {
        const data = req.body;
        
        // Kiểm tra xem địa chỉ email đã tồn tại trong cơ sở dữ liệu chưa
        const existingAccount = await Account.findOne({ email: data.email });
        if (existingAccount) {
            return res.status(400).json({
                status: 400,
                message: 'Địa chỉ email đã tồn tại trong cơ sở dữ liệu',
                data: []
            });
        }
        
        // Tạo tài khoản mới
        const newAccount = new Account({
            email: data.email,
            password: data.password, // Lưu ý: cần mã hóa mật khẩu trước khi lưu vào cơ sở dữ liệu
        });
        const result = await newAccount.save();

        const mailOptions = {
            from: 'namden9399@gmail.com',
            to: result.email,
            subject: 'Đăng Ký Thành Công',
            text: 'Cảm ơn bạn đã đăng ký dịch vụ lừa đảo của chúng tôi'
        };
        transporter.sendMail(mailOptions, (error, info) => {
            if (error) {
                console.log(error);
            } else {
                console.log('Email sent: ' + info.response);
            }
        });

        // Trả về kết quả
        res.json({
            status: 200,
            message: 'Thêm người dùng thành công',
            data: result
        });
    } catch (error) {
        console.log(error);
        res.status(500).json({
            status: 500,
            message: 'Lỗi server',
            data: []
        });
    }
});

// Route để xử lý yêu cầu đăng nhập
router.post('/login', async (req, res) => {
    try {
        const { email, password } = req.body;
        const account = await Account.findOne({ email, password });
        if (account) {
            const token = JWT.sign({ id: account._id }, SECRETKEY, { expiresIn: '1h' });
            const refreshToken = JWT.sign({ id: account._id }, SECRETKEY, { expiresIn: '1h' });

            res.json({
                status: 200,
                messenger: "Đăng nhập thành công",
                data: account,
                "token": token,
                "refreshToken": refreshToken
            });
        } else {
            res.json({
                status: 400,
                messenger: "Lỗi, đăng nhập không thành công",
                data: [],
            });
        }
    } catch (error) {
        console.log(error);
        res.status(500).json({
            status: 500,
            message: 'Lỗi server',
            data: []
        });
    }
});


router.get("/get-list-sanpham", async (req, res) => {
    try {
        const data = await SanPham.find();
        if (data) {
            res.json({
                status: 200,
                messenger: "Lay thanh cong",
                data: data,
            });
        } else {
            res.json({
                status: 400,
                messenger: "Loi ko lay thanh cong",
                data: [],
            });
        }
    } catch (error) {
        console.log(error);
    }
});

router.post("/add-sanpham", async (req, res) => {
    try {
        const data = req.body;
        const newSanPham = new SanPham({
            tensp: data.tensp,
            mota: data.mota,
            gia: data.gia,
            hinhanh: data.hinhanh,
        });
        const result = await newSanPham.save();
        if (result) {
            res.json({
                status: 200,
                messenger: "Them thanh cong",
                data: result,
            });
        } else {
            res.json({
                status: 400,
                messenger: "Loi. Them khong thanh cong",
                data: [],
            });
        }
    } catch (error) {
        console.log(error);
    }
});

router.get('/search-sanpham', async (req, res) => {
    try {
        const key = req.query.key

        const data = await SanPham.find({ tensp: { "$regex": key, "$options": "i" } })
            .sort({ createdAt: -1 });

        if (data) {
            res.json({
                "status": 200,
                "messenger": "Thành công",
                "data": data
            })
        } else {
            res.json({
                "status": 400,
                "messenger": "Lỗi, không thành công",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})



module.exports = router;

