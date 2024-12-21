package com.thick124.lop124LTTD03.nhomTPB;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thick124.lop124LTTD03.nhomTPB.Models.User;
import com.thick124.lop124LTTD03.nhomTPB.Service.APIservice;
import com.thick124.lop124LTTD03.nhomTPB.Service.Dataservice;

import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Thongtin_adapter extends RecyclerView.Adapter<Thongtin_adapter.ViewHolder> {
    Context context;
    ArrayList<User> mangUser;
    private OnPlaylistDeletedListener onPlaylistDeletedListener;

    public interface OnPlaylistDeletedListener {
        void onPlaylistDeleted();  // Phương thức callback
    }
    public Thongtin_adapter(Context context, ArrayList<User> mangUser ,OnPlaylistDeletedListener listener) {
        this.context = context;
        this.mangUser = mangUser;
        this.onPlaylistDeletedListener = listener;
    }

    @NonNull
    @Override
    public Thongtin_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dong_thongtin, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Thongtin_adapter.ViewHolder holder, int position) {
        User user = mangUser.get(position);
        holder.txt_hoten.setText(user.getHoTen());
        holder.txt_gioithieu.setText(user.getGioiThieu());

        if (user.getAvatar() != null && !user.getAvatar().isEmpty()) {
            Glide.with(context)
                    .load(user.getAvatar())
                    .placeholder(R.drawable.image2)
                    .error(R.drawable.image2)
                    .into(holder.imageAvartar);
        } else {
            holder.imageAvartar.setImageResource(R.drawable.image2);
        }
        holder.itemView.setOnClickListener(v -> {
            // Hiển thị dialog hoặc màn hình sửa thông tin
            showEditDialog(user);
        });
        // Xử lý sự kiện nhấn giữ (long click)
        holder.itemView.setOnLongClickListener(v -> {
            // Hiển thị dialog xác nhận xóa
            new AlertDialog.Builder(v.getContext())
                    .setTitle("Xóa Playlist")
                    .setMessage("Bạn có chắc chắn muốn xóa người dùng này không?")
                    .setPositiveButton("Xóa", (dialog, which) -> {
                        // Thực hiện hành động xóa playlist
                        deleteUser(user);
                    })
                    .setNegativeButton("Hủy", (dialog, which) -> {
                        // Đóng dialog mà không làm gì
                        dialog.dismiss();
                    })
                    .show();
            return true; // Trả về true để sự kiện nhấn giữ được xử lý
        });

    }

    @Override
    public int getItemCount() {
        return mangUser.size();
    }

    private void showEditDialog(User user) {
        // Tạo một dialog để chỉnh sửa thông tin
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Sửa thông tin");

        // Tạo một layout với EditText cho các thông tin cần sửa
        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_edit_user, null);
        EditText edtHoTen = dialogView.findViewById(R.id.edt_hoTen);
        EditText edtGioiThieu = dialogView.findViewById(R.id.edt_gioiThieu);
        EditText edtAvatar = dialogView.findViewById(R.id.edt_avatar);

        // Điền thông tin hiện tại vào các EditText
        edtHoTen.setText(user.getHoTen());
        edtGioiThieu.setText(user.getGioiThieu());
        edtAvatar.setText(user.getAvatar());

        builder.setView(dialogView);

        builder.setPositiveButton("Lưu", (dialog, which) -> {
            // Lấy dữ liệu đã sửa và gọi API để sửa
            String hoTen = edtHoTen.getText().toString();
            String gioiThieu = edtGioiThieu.getText().toString();
            String avatar = edtAvatar.getText().toString();

            // Gọi API để sửa thông tin người dùng
            Dataservice dataservice = APIservice.getService();
            Call<ResponseBody> callback = dataservice.Suathongtin(user.getId(),hoTen, avatar, gioiThieu);
            callback.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(context, "Cập nhật thông tin thành công", Toast.LENGTH_SHORT).show();
                        // Cập nhật lại danh sách người dùng nếu cần
                        if (onPlaylistDeletedListener != null) {
                            onPlaylistDeletedListener.onPlaylistDeleted();
                        }
                    } else {
                        Toast.makeText(context, "Cập nhật thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

        builder.setNegativeButton("Hủy", (dialog, which) -> {
            // Đóng dialog
            dialog.dismiss();
        });

        builder.show();
    }
    private void deleteUser(User user) {
        Toast.makeText(context, "Đang xóa User: " + user.getHoTen(), Toast.LENGTH_SHORT).show();

        Dataservice dataservice = APIservice.getService();
        Call<ResponseBody> callback = dataservice.Deletethongtin(user.getId());
        callback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(context, "Xóa User thành công", Toast.LENGTH_SHORT).show();
                    if (onPlaylistDeletedListener != null) {
                        onPlaylistDeletedListener.onPlaylistDeleted();  // Gọi callback
                    }

                } else {
                    Toast.makeText(context, "Xóa User thất bại: " + response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(context, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageAvartar;
        TextView txt_hoten, txt_gioithieu;

        public ViewHolder(View itemview) {
            super(itemview);
            imageAvartar = itemview.findViewById(R.id.img_avartar);
            txt_hoten = itemview.findViewById(R.id.tv_tensv);
            txt_gioithieu = itemview.findViewById(R.id.tv_gioithieu);
        }
    }
}

