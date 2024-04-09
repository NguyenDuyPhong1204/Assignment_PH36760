var express = require('express');
var router = express.Router();
const User = require('../models/users');
const Carts = require('../models/carts');
const Favorites = require('../models/favorites');
const Bills = require('../models/bills');
const BillDetails = require('../models/billdetails');
const Products = require('../models/products');
const Categorys = require('../models/category');
const Upload = require('../config/common/upload');
const JWT = require('jsonwebtoken');
const SECRETKEY = "NDP";

// thêm tất cả các thứ

//1. thêm user
router.post('/add-user', async function (req, res) {
    try {
        const data = req.body;
        const newUser = new Users({
            emailUser: data.emailUser,
            passwordUser: data.passwordUser,
            fullname: data.fullname,
            imageUser: data.imageUser,
            addressUser: data.addressUser
        });

        const result = await newUser.save();//thêm 
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }

    } catch (error) {
        console.log(error);
    }
});

//2.Thêm loại sản phẩm
router.post('/add-category', async function (req, res) {
    try {
        const data = req.body;
        const newCategory = new Categorys({
            title: data.title,
            imageCategory: data.imageCategory
        })

        const result = await newCategory.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

//3. Thêm sản phẩm
router.post('/add-product', async function (req, res) {
    try {
        const data = req.body;
        const newProduct = new Products({
            productName: data.productName,
            productImage: data.productImage,
            productPrice: data.productPrice,
            description: data.description,
            quantity: data.quantity,
            categoryID: data.categoryID,
            isFavorite: data.isFavorite,
            isCart: data.isCart
        })

        const result = await newProduct.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

//4. Thêm vào yêu thích
router.post('/add-favorite', async function (req, res) {
    try {
        const data = req.body;
        const newFavorite = new Favorites({
            userId: data.userId,
            productId: data.productId
        })

        const result = await newFavorite.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

router.post('/add-cart', async function (req, res) {
    try {
        const data = req.body;
        const newCart = new Carts({
            userId: data.userId,
            productId: data.productId,
            productName: data.productName,
            productImage: data.productImage,
            quantity: data.quantity,
            productPrice: data.productPrice,


        })

        const result = await newCart.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

//6. Thêm hoá đơn
router.post('/add-bill', async function (req, res) {
    try {
        const data = req.body;
        const newBill = new Bills({
            userId: data.userId,
            total: data.total,
            address: data.address
        })

        const result = await newBill.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

//7. thêm hoá đơn chi tiết
router.post('/add-bill-detail', async function (req, res) {
    try {
        const data = req.body;
        const newBillDetails = new BillDetails({
            billId: data.billId,
            productId: data.productId,
            quantity: data.quantity
        })

        const result = newBillDetails.save();
        if (result) {
            res.json({
                "status": 200,
                "message": "Thêm thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Thêm mới thất bại",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
    }
});

//update tất cả các thứ

//up date thông tin user
router.put('update-user/:id', async function (req, res) {
    try {
        const { id } = req.params;
        const data = req.body;
        const updateUser = await Users.findById(id);

        let result = null;
        if (updateUser) {
            updateUser.fullname = data.fullname ?? updateUser.fullname,
                updateUser.imageUser = data.imageUser ?? updateUser.imageUser,
                updateUser.addressUser = data.addressUser ?? updateUser.addressUser
            result = await updateUser.save();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Cập nhật thành công",
                    "data": result
                })
            } else {
                res.json({
                    "status": 400,
                    "message": "Cập nhật thất bại",
                    "data": []
                })
            }
        }
    } catch (error) {
        console.log(error);
    }
});

//xoá tất cả mọi thứ

//1. Xoá khỏi giỏ hàng
router.delete('/delete-cart-by-id/:id', async function (req, res) {
    try {
        const { id } = req.params;
        const result = await Carts.findByIdAndDelete(id);//tìm và thực hiện xoá
        if (result) {
            res.json({
                "status": 200,
                "message": "Xóa thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Xóa thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//2. xoá khỏi yêu thích
router.delete('/delete-favorite-by-id/:id', async function (req, res) {
    try {
        const { id } = req.params;
        const result = await Favorites.findByIdAndDelete(id);//tìm và thực hiện xoá
        if (result) {
            res.json({
                "status": 200,
                "message": "Xóa thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Xóa thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//update các thứ
//1. update favorite
router.put("/update-product/:id", async function (req, res) {
    try {
        const { id } = req.body;
        const data = req.body;
        const updateProduct = await Products.findById(id);
        let result = null;
        if (updateProduct) {
            updateProduct.isFavorite = data.isFavorite ?? updateProduct.isFavorite

            result = await updateProduct.save();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Cập nhật thành công",
                    "data": result
                })
            } else {
                res.json({
                    "status": 400,
                    "message": "Cập nhật thất bại",
                    "data": []
                })
            }
        }
    } catch (error) {
        console.log(error);
    }
});

//category
router.put("/update-category/:id", async function (req, res) {
    try {
        const { id } = req.params;
        const data = req.body;
        const updateCategory = await Categorys.findById(id);
        let result = null;
        if (updateCategory) {
            updateCategory.imageCategory = data.imageCategory ?? updateCategory.imageCategory
            result = await updateCategory.save();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Cập nhật thành công",
                    "data": result
                })
            } else {
                res.json({
                    "status": 400,
                    "message": "Cập nhật thất bại",
                    "data": []
                })
            }
        }
    } catch (error) {
        console.log(error);
    }
})


//get list tất cả các thứ

//1. get list Category
router.get('/get-list-category', async function (req, res) {
    try {
        const result = await Categorys.find().sort({ createdAt: - 1 });
        if (result) {
            res.json({
                "status": 200,
                "message": "Lấy thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lấy thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//2.get list product
router.get('/get-list-product/:categoryID', async function (req, res) {
    try {
        const { categoryID } = req.params;
        const result = await Products.find({ categoryID: categoryID }).sort({ createdAt: -1 });
        if (result) {
            res.json({
                "status": 200,
                "message": "Lấy dữ liệu thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lấy dữ liệu thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//3. get list cart
router.get('/get-list-cart/:userId', async function (req, res) {
    try {
        const { userId } = req.params;
        const result = await Carts.find({ userId: userId }).sort({ createdAt: - 1 });
        if (result) {
            res.json({
                "status": 200,
                "message": "Lấy dữ liệu thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lấy dữ liệu thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});


//4.get list favorite
router.get('/get-list-favorite/:userId', async function (req, res) {
    try {
        const { userId } = req.params;
        const result = await Favorites.find({ userId: userId }).sort({ createdAt: - 1 });
        if (result) {
            res.json({
                "status": 200,
                "message": "Lấy dữ liệu thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lấy dữ liệu thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//getuser
router.get("/get-user-by-id/:id", async function (req, res) {
    const { id } = req.params;
    const result = await User.findById(id);
    if (result) {
        res.json({
            "status": 200,
            "message": "Lấy dữ liệu thành công",
            "data": result
        })
    } else {
        res.json({
            "status": 400,
            "message": "Lấy dữ liệu thất bại",
            "data": []
        })
    }
})


//tìm kiếm

//1. tìm kiếm product
router.get('/search-product', async function (req, res) {
    try {
        const key = req.query.key;
        const data = await Products.find({
            productName: { "$regex": key, "$options": "i" }
        }).sort({ createdAt: - 1 });
        if (data) {
            res.json({
                status: 200,
                messenger: "Tìm thành công",
                data: data,
            });
        } else {
            res.json({
                status: 400,
                messenger: "tìm thất bại",
                data: [],
            });
        }
    } catch (error) {
        console.log(error);
    }
});


//login, register

//1. login
router.post('/login', async (req, res) => {
    try {
        const { username, password } = req.body;
        const user = await User.findOne({ username, password });
        if (user) {
            //Token người dùng sẽ sử dụng gửi lên trên header mỗi lần muốn gọi api
            const token = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1h' });
            //khi token hết hạn, người dùng sẽ call 1 api khác để lấy token mới
            //Lúc này người dùng sẽ truyền refreshToken lên để nhận về 1 cặp token, refershToken mới
            //Nếu cả 2 token đều hết hạn người dùng sẽ phải thoát app và đăng nhập lại
            const refreshToken = JWT.sign({ id: user._id }, SECRETKEY, { expiresIn: '1d' });
            //expiresIn thời gian token
            res.json({
                "status": 200,
                "message": "Đăng nhận thành công",
                "data": user,
                "token": token,
                "refreshToken": refreshToken
            })
        } else {
            res.json({
                "status": 400,
                "message": "Đăng nhận thất bại",
                "data": {}
            })
        }
    } catch (error) {
        console.log(error);
    }
});

//đăng kí
const Transporter = require('../config/common/mail');
router.post("/register-send-email", Upload.none(), async (req, res) => {
    try {
        const data = req.body;
        const newUser = User({
            emailUser: data.emailUser,
            username: data.username,
            password: data.password,
            fullname: data.fullname,
            imageUser: data.imageUser,
            addressUser: data.addressUser,
            phoneNumber: data.phoneNumber
        })

        const result = await newUser.save();
        if (result) {
            const mailOptions = {
                from: 'nguyenduyphong12122004@gmail.com',//mail gửi đi
                to: result.email,//email nhận
                subject: "Đăng kí thành công",
                text: "Cảm ơn bạn đã đăng kí"//nội dung email
            };
            await Transporter.sendMail(mailOptions);
            res.json({
                "status": 200,
                "message": "Đăng kí thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Đăng kí thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log(error);
    }
})

//lấy thông tin product theo productId
router.get("/get-product-by-id/:productId", async (req, res) => {
    const { productId } = req.params;
    const result = await Products.findById(productId);
    if (result) {
        res.json({
            "status": 200,
            "message": "Lấy dữ liệu thành công",
            "data": result
        })
    } else {
        res.json({
            "status": 400,
            "message": "Lấy dữ liệu thất bại",
            "data": []
        })
    }
});

//update user
router.put("/update-user/:id", Upload.single("imageUser"), async function (req, res) {
    try {
        const { id } = req.params;
        const { file } = req;
        const data = req.body;
        const updateUser = await User.findById(id);
        let result = null;
        if (updateUser) {
            updateUser.imageUser = data.imageUser ?? `${req.protocol}://${req.get('host')}/uploads/${file.filename}`
            updateUser.fullname = data.fullname ?? updateUser.fullname,
                updateUser.addressUser = data.addressUser ?? updateUser.addressUser
            updateUser.phoneNumber = data.phoneNumber ?? updateUser.phoneNumber
            result = await updateUser.save();
            if (result) {
                res.json({
                    "status": 200,
                    "message": "Cập nhật thành công",
                    "data": result
                })
            } else {
                res.json({
                    "status": 400,
                    "message": "Cập nhật thất bại",
                    "data": []
                })
            }
        }
    } catch (error) {
        console.log(error);
    }
})

router.delete('/delete-cart-by-user/:id', async (req, res) => {
    try {
        const { id_user } = req.params;
        const result = await Carts.deleteMany({ id_user: id_user });
        if (result) {
            res.json({
                "status": 200,
                "message": "Xóa giỏ hàng thành công",
                "data": result
            });
        } else {
            res.json({
                "status": 400,
                "message": "Không thể xóa giỏ hàng",
                "data": []
            });
        }
    } catch (error) {
        console.log(error);
        res.json({
            "status": 500,
            "message": "Lỗi server",
            "data": []
        });
    }
});


router.get("/get-list-bill-by-id/:userId", async function (req, res) {
    try {
        const { userId } = req.params;
        const result = await Bills.find({ userId: userId }).sort({ createdAt: - 1 });
        if (result) {
            res.json({
                "status": 200,
                "message": "Lấy dữ liệu thành công",
                "data": result
            })
        } else {
            res.json({
                "status": 400,
                "message": "Lấy dữ liệu thất bại",
                "data": []
            })
        }
    } catch (error) {
        console.log("Lỗi lấy bill" +error);
    }
})

router.get("get-image-by-userId/:userId", async function(req,res){
    const {userId} = req.params;
    const result = await User.findById(userId);
    if(result){
        res.json({
            "status": 200,
            "message": "Lấy dữ liệu thành công",
            "data": result
        })
    }else{
        res.json({
            "status": 400,
            "message": "Lấy dữ liệu thất bại",
            "data": []
        })
    }
})
module.exports = router;