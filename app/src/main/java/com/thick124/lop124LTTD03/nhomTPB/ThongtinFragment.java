package com.thick124.lop124LTTD03.nhomTPB;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.thick124.lop124LTTD03.nhomTPB.Models.User;
import com.thick124.lop124LTTD03.nhomTPB.Service.APIservice;
import com.thick124.lop124LTTD03.nhomTPB.Service.Dataservice;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ThongtinFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ThongtinFragment extends Fragment implements Thongtin_adapter.OnPlaylistDeletedListener {
    View view;
    RecyclerView recyclerView;
    Thongtin_adapter thongtinAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ThongtinFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ThongtinFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ThongtinFragment newInstance(String param1, String param2) {
        ThongtinFragment fragment = new ThongtinFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_thongtin, container, false);

        recyclerView = view.findViewById(R.id.recycleview);
        EditText etHoTen = view.findViewById(R.id.etName);
        EditText etAvatar = view.findViewById(R.id.etAvatar);
        EditText etGioiThieu = view.findViewById(R.id.etIntroduction);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        // Gọi dữ liệu ban đầu
        GetDataUser();

        // Xử lý khi nhấn nút "Thêm"
        btnAdd.setOnClickListener(v -> {
            String hoTen = etHoTen.getText().toString().trim();
            String avatar = etAvatar.getText().toString().trim();
            String gioiThieu = etGioiThieu.getText().toString().trim();

            if (!hoTen.isEmpty() && !avatar.isEmpty() && !gioiThieu.isEmpty()) {
                AddDataUser(hoTen, avatar, gioiThieu);
                etHoTen.setText("");
                etAvatar.setText("");
                etGioiThieu.setText("");
            } else {
                Log.d("hihi", "Vui lòng nhập đầy đủ thông tin.");
            }
        });

        return view;
    }
    private void GetDataUser() {
        Dataservice dataservice = APIservice.getService();
        Call<List<User>> callback = dataservice.getThongtin();
        callback.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                ArrayList<User> UserArraylist = (ArrayList<User>) response.body();
                thongtinAdapter= new Thongtin_adapter(getActivity(),UserArraylist,ThongtinFragment.this);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setAdapter(thongtinAdapter);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Log.d("hihi", "Lỗi khi tải dữ liệu User: " + t.getMessage());
            }
        });
    }
    private void AddDataUser(String hoTen, String avatar, String gioiThieu) {
        Dataservice dataservice = APIservice.getService();
        Call<ResponseBody> callback = dataservice.Addthongtin(hoTen, avatar, gioiThieu);
        callback.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d("hihi", "Thêm thành công!");
                    // Cập nhật lại danh sách sau khi thêm
                    GetDataUser();
                } else {
                    Log.d("hihi", "Lỗi khi thêm thông tin: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d("hihi", "Lỗi khi gọi API thêm thông tin: " + t.getMessage());
            }
        });
    }

    @Override
    public void onPlaylistDeleted() {
        GetDataUser();
    }
}