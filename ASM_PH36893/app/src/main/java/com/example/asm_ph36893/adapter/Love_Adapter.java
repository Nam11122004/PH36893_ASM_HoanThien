package com.example.asm_ph36893.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.asm_ph36893.R;
import com.example.asm_ph36893.handle.Item_SanPham_Handle;
import com.example.asm_ph36893.love;
import com.example.asm_ph36893.model.CartRequest;
import com.example.asm_ph36893.services.HttpRequest;

import java.util.ArrayList;

public class Love_Adapter extends RecyclerView.Adapter<Love_Adapter.ViewHolder>{
    private ArrayList<CartRequest> list;
    private Context context;

    private Item_SanPham_Handle handle;

    private HttpRequest httpRequest;


    public Love_Adapter(love mainActivity, ArrayList<CartRequest> list, Context context) {
        this.list = list;
        this.context = context;
        this.handle = handle;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recylove, parent, false);
        return new Love_Adapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        httpRequest = new HttpRequest();
        CartRequest cartRequest = list.get(position);
        Glide.with(context)
                .load(cartRequest.getHinhanh())
                .into(holder.imgSp);
        holder.txtTenSp.setText(cartRequest.getTensp());
        holder.txtMoTa.setText(cartRequest.getMota());
        holder.txtGia.setText(cartRequest.getGia());

    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenSp, txtMoTa, txtGia, txtSoLuong;
        ImageView imgSp;
        Button btnAdd, btnThanhToan;
        ImageView imgGiam,imgTang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenSp = itemView.findViewById(R.id.txtTenSp);
            txtMoTa = itemView.findViewById(R.id.txtMota);
            txtGia = itemView.findViewById(R.id.txtGia);
            txtSoLuong = itemView.findViewById(R.id.txtSoLuong);
            btnAdd = itemView.findViewById(R.id.btnAddCart);
            btnThanhToan = itemView.findViewById(R.id.btnThanhToan);
            imgGiam = itemView.findViewById(R.id.imggiam);
            imgTang = itemView.findViewById(R.id.imgtang);
            imgSp = itemView.findViewById(R.id.imgSp);
        }
    }
    public void removeItem(int position) {
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }
}