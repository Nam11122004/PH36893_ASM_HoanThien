package com.example.asm_ph36893.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_ph36893.CartMannager;
import com.example.asm_ph36893.Fayvorite;
import com.example.asm_ph36893.MainActivity;
import com.example.asm_ph36893.R;
import com.example.asm_ph36893.handle.Item_SanPham_Handle;
import com.example.asm_ph36893.love;
import com.example.asm_ph36893.model.SanPham;
import com.example.asm_ph36893.services.HttpRequest;

import java.util.ArrayList;

public class SanPham_Adapter extends RecyclerView.Adapter<SanPham_Adapter.ViewHolder> {
    private ArrayList<SanPham> list;
    private Context context;

    private Item_SanPham_Handle handle;

    private HttpRequest httpRequest;


    public SanPham_Adapter(MainActivity mainActivity,ArrayList<SanPham> list, Context context) {
        this.list = list;
        this.context = context;
        this.handle = handle;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        httpRequest = new HttpRequest();
        SanPham sanPham = list.get(position);
        Glide.with(context)
                .load(sanPham.getHinhanh())
                .into(holder.imgSp);
        holder.txtTenSp.setText(sanPham.getTensp());
        holder.txtMoTa.setText(sanPham.getMota());
        holder.txtGia.setText(sanPham.getGia());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SanPham product1 = list.get(holder.getAdapterPosition());
                Intent intent = new Intent(holder.itemView.getContext(), Fayvorite.class);
                intent.putExtra("productId", product1.getTensp());
                intent.putExtra("nameProduct", product1.getMota());
                intent.putExtra("priceProduct", product1.getGia());
                intent.putExtra("descriptionProduct", product1.getHinhanh());

                holder.itemView.getContext().startActivity(intent);
            }
        });
        holder.imgAddLove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Lấy vị trí của mục được click
                int clickedPosition = holder.getAdapterPosition();
                if (clickedPosition != RecyclerView.NO_POSITION) {
                    SanPham sanPham = list.get(clickedPosition);
                    // Tạo Intent để chuyển dữ liệu sang activity love
                    Intent intent = new Intent(context, love.class);
                    // Truyền dữ liệu bằng Intent (ví dụ: id sản phẩm)
                    intent.putExtra("productId", sanPham.getId());
                    // Khởi chạy activity love
                    context.startActivity(intent);
                }
            }
        });
        holder.imgSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, Fayvorite.class);
                context.startActivity(intent);
            }
        });
        holder.btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Lấy vị trí của mục trong danh sách hiển thị (nếu bạn sử dụng RecyclerView)
                int position = holder.getAdapterPosition();

                // Kiểm tra xem vị trí có hợp lệ không
                if (position != RecyclerView.NO_POSITION) {
                    // Lấy Fruit từ danh sách hoặc mảng Fruit tương ứng
                    SanPham sanPham = list.get(position); // Giả sử fruits là danh sách các mặt hàng

                    // Thêm Fruit vào giỏ hàng bằng cách gọi phương thức addToCart của CartManager
                    CartMannager.getInstance().addToCart(sanPham);

                    // Thông báo cho người dùng rằng sản phẩm đã được thêm vào giỏ hàng
                    Toast.makeText(context, "Đã thêm sản phẩm vào giỏ hàng", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSp, txtMoTa, txtGia;
        ImageView imgSp, imgAddLove;
        Button btnAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSp = itemView.findViewById(R.id.txtTenSp);
            txtMoTa = itemView.findViewById(R.id.txtMota);
            txtGia = itemView.findViewById(R.id.txtGia);
            btnAdd = itemView.findViewById(R.id.btnAddCart);
            imgSp = itemView.findViewById(R.id.imgSp);
            imgAddLove = itemView.findViewById(R.id.imgAddLove);
        }
    }

}
