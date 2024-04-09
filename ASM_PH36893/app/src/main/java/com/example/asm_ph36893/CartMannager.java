package com.example.asm_ph36893;

import com.example.asm_ph36893.model.CartRequest;
import com.example.asm_ph36893.model.SanPham;

import java.util.ArrayList;

public class CartMannager {
    private static CartMannager instance;
    private ArrayList<CartRequest> cartItems;

    // Sửa lại constructor để làm nó là một constructor thực sự
    private CartMannager() {
        cartItems = new ArrayList<>();
    }

    public static CartMannager getInstance() {
        if (instance == null) {
            instance = new CartMannager();
        }
        return instance;
    }

    public ArrayList<CartRequest> getCartItems() {
        return cartItems;
    }

    public void addToCart(SanPham sanPham) {
        // Đảm bảo rằng cartItems không null trước khi thêm vào giỏ hàng
        if (cartItems == null) {
            cartItems = new ArrayList<>();
        }

        // Kiểm tra xem sản phẩm đã tồn tại trong giỏ hàng chưa
        boolean found = false;
        for (CartRequest item : cartItems) {
            if (item.getCartid().equals(sanPham.getId())) {
                // Nếu sản phẩm đã tồn tại, tăng số lượng
                item.increaseQuantity();
                found = true;
                break;
            }
        }
        // Nếu sản phẩm chưa tồn tại trong giỏ hàng, thêm mới vào giỏ hàng với số lượng là 1
        if (!found) {
            cartItems.add(new CartRequest(sanPham.getId(), sanPham.getTensp(), sanPham.getMota(), sanPham.getGia(), sanPham.getHinhanh(), "1"));
        }
    }

    public void removeFromCart(CartRequest cartItem) {
        // Tìm và xóa sản phẩm khỏi giỏ hàng
        for (int i = 0; i < cartItems.size(); i++) {
            CartRequest item = cartItems.get(i);
            if (item.getCartid().equals(cartItem.getCartid())) {
                // Nếu số lượng của mục trong giỏ hàng là 1, xóa mục khỏi giỏ hàng
                if (item.getQuantity().equals("1")) {
                    cartItems.remove(i);
                } else {
                    // Ngược lại, giảm số lượng sản phẩm đi 1
                    item.decreaseQuantity();
                }
                break;
            }
        }
    }
}

