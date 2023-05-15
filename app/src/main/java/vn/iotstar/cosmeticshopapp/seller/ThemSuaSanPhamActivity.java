package vn.iotstar.cosmeticshopapp.seller;

import static vn.iotstar.cosmeticshopapp.ChiTietSanPhamActivity.REQUEST_CODE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import vn.iotstar.cosmeticshopapp.DanhGiaDonHangActivity;
import vn.iotstar.cosmeticshopapp.R;
import vn.iotstar.cosmeticshopapp.SettingActivity;
import vn.iotstar.cosmeticshopapp.adapter.SliderProductImageAdapter;
import vn.iotstar.cosmeticshopapp.api.APIService;
import vn.iotstar.cosmeticshopapp.model.Category;
import vn.iotstar.cosmeticshopapp.model.CategoryAndStyleResponse;
import vn.iotstar.cosmeticshopapp.model.Product;
import vn.iotstar.cosmeticshopapp.model.ProductDetailResponse;
import vn.iotstar.cosmeticshopapp.model.ProductResponse;
import vn.iotstar.cosmeticshopapp.model.Store;
import vn.iotstar.cosmeticshopapp.model.Style;
import vn.iotstar.cosmeticshopapp.retrofit.RetrofitCosmeticShop;
import vn.iotstar.cosmeticshopapp.sharedPreferentManager.SharedPrefManager;

public class ThemSuaSanPhamActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_IMAGE_PICKER = 100;
    EditText NameProduct_edt, Price_edt,promoprice_edt,Description_edt,Makeof_edt,Color_edt,Makein_edt;
    EditText SoLuong_edt, KichThuoc_edt;
    SliderView sliderProduct;
    FrameLayout flthemHinhAnh;
    TextView tv_soluonghinhanh,Category_textview, Style_textview, tvSubmit, tvTrangThai;
    Spinner Category_spinner,Stype_spinner;
    Switch switchTrangThai;
    Boolean trangThai = false;
    SliderProductImageAdapter sliderProductImageAdapter;
    Boolean isUpdate = false;
    APIService apiService;
    SharedPrefManager sharedPrefManager;
    ProgressDialog progressDialog;
    AlertDialog alertDialog;
    AlertDialog.Builder builder;
    List<Category> categoryList;
    List<Style> styleList;
    Integer categoryId;
    Integer styleId;
    List<File> fileList = new ArrayList<>();
    String productName, productDescription, madeOf, madeIn, color;
    Integer productPrice, productPromoPrice, productId;
    List<String> sizeList = new ArrayList<>();
    List<Integer> quantityList = new ArrayList<>();
    Integer storeId;
    Product product;
    Category currentCategory;
    Style currentStyle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sua_san_pham);
        anhXa();
        //lấy dữ liệu:
        //nếu có dữ liệu thì là sửa, không có dữ liệu thì là thêm
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        selectImage();
        if(bundle != null){
            product = (Product) bundle.getSerializable("product");
            apiService.getCategoryAndStyleOfProduct(product.getId()).enqueue(new Callback<CategoryAndStyleResponse>() {
                @Override
                public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                    if (response.isSuccessful()){
                        currentCategory = response.body().getBody().get(0);
                        currentStyle = response.body().getBody().get(0).getStyles().get(0);
                        setSwitch();
                        setSpinner();
                    }
                }

                @Override
                public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {

                }
            });
            suaSanPham(product);
        }else{
            setSwitch();
            setSpinner();
            themSanPham();
        }
    }

    private void setSwitch() {
        if (product != null){
            if (product.getIsSelling()){
                switchTrangThai.setChecked(true);
                switchTrangThai.setBackgroundResource(R.drawable.background_boder);
                switchTrangThai.setTrackDrawable(getResources().getDrawable(R.drawable.background_boder));
                trangThai = true;
                tvTrangThai.setText("Đang bán");
            }else {
                switchTrangThai.setChecked(false);
                switchTrangThai.setBackgroundResource(R.drawable.switch_background);
                switchTrangThai.setTrackDrawable(getResources().getDrawable(R.drawable.switch_background));
                trangThai = false;
                tvTrangThai.setText("Ngừng bán");
            }
        } else {
            switchTrangThai.setChecked(true);
            switchTrangThai.setBackgroundResource(R.drawable.background_boder);
            switchTrangThai.setTrackDrawable(getResources().getDrawable(R.drawable.background_boder));
            trangThai = true;
            tvTrangThai.setText("Đang bán");
        }
        switchTrangThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(switchTrangThai.isChecked()){
                    switchTrangThai.setBackgroundResource(R.drawable.background_boder);
                    switchTrangThai.setTrackDrawable(getResources().getDrawable(R.drawable.background_boder));
                    trangThai = true;
                    tvTrangThai.setText("Đang bán");
                }else{
                    switchTrangThai.setBackgroundResource(R.drawable.switch_background);
                    switchTrangThai.setTrackDrawable(getResources().getDrawable(R.drawable.switch_background));
                    trangThai = false;
                    tvTrangThai.setText("Ngừng bán");
                }
            }
        });
    }

    private void selectImage() {
        flthemHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    ImagePicker.create(ThemSuaSanPhamActivity.this)
                            .limit(5) // Giới hạn số lượng ảnh có thể chọn (ở đây là 5 ảnh)
                            .folderMode(true)
                            .start(REQUEST_CODE_IMAGE_PICKER);
                } else {
                    ActivityCompat.requestPermissions(ThemSuaSanPhamActivity.this,
                            new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_IMAGE_PICKER);
                }
            }
        });
    }

    private void setSpinner() {
        apiService.getCategory().enqueue(new Callback<CategoryAndStyleResponse>() {
            @Override
            public void onResponse(Call<CategoryAndStyleResponse> call, Response<CategoryAndStyleResponse> response) {
                categoryList = response.body().getBody();
                List<String> categoryNameList = new ArrayList<>();
                for (Category category : categoryList){
                    categoryNameList.add(category.getName());
                }
                ArrayAdapter<String> categoryNameAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_custom_spinner_dropdown_item, categoryNameList);
                Category_spinner.setAdapter(categoryNameAdapter);
                //
                if (currentCategory == null){
                    Category_textview.setText(categoryNameList.get(0));
                    categoryId = categoryList.get(0).getId();
                } else {
                    Category_textview.setText(currentCategory.getName());
                    categoryId = currentCategory.getId();
                }
                //
                Category_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String selectedCategory = adapterView.getItemAtPosition(i).toString();
                        Category_textview.setText(selectedCategory);
                        styleList = categoryList.get(categoryNameList.indexOf(selectedCategory)).getStyles();
                        List<String> styleNameList = new ArrayList<>();
                        for (Style style : styleList){
                            styleNameList.add(style.getName());
                        }
                        ArrayAdapter<String> styleNameAdapter = new ArrayAdapter<>(getApplicationContext(), R.layout.my_custom_spinner_dropdown_item, styleNameList);
                        Stype_spinner.setAdapter(styleNameAdapter);
                        //
                        if (currentStyle == null){
                            Style_textview.setText(styleNameList.get(0));
                            styleId = styleList.get(0).getId();
                        } else {
                            Style_textview.setText(currentStyle.getName());
                            styleId = currentStyle.getId();
                        }
                        //
                        Stype_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                String selectedStyle = adapterView.getItemAtPosition(i).toString();
                                styleId = styleList.get(styleNameList.indexOf(selectedStyle)).getId();
                                Style_textview.setText(selectedStyle);
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onFailure(Call<CategoryAndStyleResponse> call, Throwable t) {

            }
        });
    }
    private void getAndCheckData(){
        productName = NameProduct_edt.getText().toString().trim();
        productDescription = Description_edt.getText().toString().trim();
        madeOf = Makeof_edt.getText().toString().trim();
        madeIn = Makein_edt.getText().toString().trim();
        color = Color_edt.getText().toString().trim();
        productPrice = Integer.parseInt(Price_edt.getText().toString().trim());
        productPromoPrice = Integer.parseInt(promoprice_edt.getText().toString().trim());
        List<String> sizeNameList = Arrays.asList(KichThuoc_edt.getText().toString().split(","));
        for (String sizeName : sizeNameList){
            sizeList.add(sizeName);
        }
        List<String> quantityStringList = Arrays.asList(SoLuong_edt.getText().toString().split(","));
        for (String quantityString : quantityStringList){
            quantityList.add(Integer.parseInt(quantityString));
        }
        //storeId
        //styleId
        //categoryId
        //trangThai
        //file
        if (productName.isEmpty()){
            Toast.makeText(this, "Tên sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productDescription.isEmpty()){
            Toast.makeText(this, "Mô tả sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (madeOf.isEmpty()){
            Toast.makeText(this, "Chất liệu sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (madeIn.isEmpty()){
            Toast.makeText(this, "Xuất xứ sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (color.isEmpty()){
            Toast.makeText(this, "Màu sắc sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productPrice == 0){
            Toast.makeText(this, "Giá sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productPromoPrice == 0){
            Toast.makeText(this, "Giá khuyến mãi sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (productPromoPrice > productPrice){
            Toast.makeText(this, "Giá khuyến mãi sản phẩm không được lớn hơn giá sản phẩm", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sizeList.size() == 0){
            Toast.makeText(this, "Kích thước sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantityList.size() == 0){
            Toast.makeText(this, "Số lượng sản phẩm không được để trống", Toast.LENGTH_SHORT).show();
            return;
        }
        if (sizeList.size() != quantityList.size()){
            Toast.makeText(this, "Số lượng kích thước và số lượng sản phẩm không khớp nhau", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    private void themSanPham() {
        tvSubmit.setText("Thêm sản phẩm");
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getAndCheckData();
                progressDialog.show();
                //xử lý thêm sản phẩm ở đây
                List<MultipartBody.Part> imagesParts = new ArrayList<>();
                for (File file: fileList){
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("productImages", file.getName(), requestBody);
                    imagesParts.add(part);
                }
                RequestBody nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), productName);
                RequestBody descriptionBody = RequestBody.create(MediaType.parse("multipart/form-data"), productDescription);
                RequestBody madeOfBody = RequestBody.create(MediaType.parse("multipart/form-data"), madeOf);
                RequestBody madeInBody = RequestBody.create(MediaType.parse("multipart/form-data"), madeIn);
                RequestBody colorBody = RequestBody.create(MediaType.parse("multipart/form-data"), color);
                RequestBody priceBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(productPrice));
                RequestBody promoPriceBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(productPromoPrice));
                RequestBody categoryBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(categoryId));
                RequestBody styleBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(styleId));
                RequestBody storeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(storeId));
                RequestBody statusBody;
                if (trangThai == true){
                    statusBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
                }else {
                    statusBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(0));
                }
                List<RequestBody> sizeBodyList = new ArrayList<>();
                for (String size : sizeList){
                    RequestBody sizeBody = RequestBody.create(MediaType.parse("multipart/form-data"), size);
                    sizeBodyList.add(sizeBody);
                }
                List<RequestBody> quantityBodyList = new ArrayList<>();
                for (Integer quantity : quantityList){
                    RequestBody quantityBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(quantity));
                    quantityBodyList.add(quantityBody);
                }
                //in ra logd các thông tin để kiểm tra
                Log.d("TAG", "onClick: " + nameBody);
                Log.d("TAG", "onClick: " + descriptionBody);
                Log.d("TAG", "onClick: " + madeOfBody);
                Log.d("TAG", "onClick: " + madeInBody);
                Log.d("TAG", "onClick: " + colorBody);
                Log.d("TAG", "onClick: " + priceBody);
                Log.d("TAG", "onClick: " + promoPriceBody);
                Log.d("TAG", "onClick: " + categoryBody);
                Log.d("TAG", "onClick: " + styleBody);
                Log.d("TAG", "onClick: " + storeBody);
                Log.d("TAG", "onClick: " + statusBody);
                Log.d("TAG", "onClick: " + sizeBodyList);
                Log.d("TAG", "onClick: " + quantityBodyList);


                apiService.addProduct(nameBody, imagesParts, priceBody,
                        promoPriceBody, descriptionBody,
                        madeOfBody, colorBody,
                        madeInBody, styleBody,
                        categoryBody, storeBody,
                        sizeBodyList, quantityBodyList,
                        statusBody).enqueue(new Callback<ProductDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                        Log.d("TAG", "onResponse: " + response.code());
                        if (response.isSuccessful()){
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            Log.d("TAG", "onResponse: " + response.body().getBody().getId());
                            Intent intent = new Intent(ThemSuaSanPhamActivity.this, QuanLySanPhamActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "Thêm sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });

            }
        });

    }

    private void suaSanPham(Product p) {

        tvSubmit.setText("Cập nhật sản phẩm");

        //xử lý sửa sản phẩm ở đây
        NameProduct_edt.setText(p.getName());
        Price_edt.setText(String.valueOf(p.getPrice()));
        promoprice_edt.setText(String.valueOf(p.getPromotionalPrice()));
        Description_edt.setText(p.getDescription());
        Makeof_edt.setText(p.getMadeOf());
        Color_edt.setText(p.getColor());
        Makein_edt.setText(p.getMadeIn());
        //set Kich thuoc edt theo product quantity có dạng: S,M,L,XL
        String size = "";
        for (int i = 0; i < p.getProductQuantities().size(); i++){
            if (i == p.getProductQuantities().size() - 1){
                size += p.getProductQuantities().get(i).getSize();
            } else {
                size += p.getProductQuantities().get(i).getSize() + ",";
            }
        }
        KichThuoc_edt.setText(size);
        //set so luong
        String quantity = "";
        for (int i = 0; i < p.getProductQuantities().size(); i++){
            if (i == p.getProductQuantities().size() - 1 ){
                quantity += p.getProductQuantities().get(i).getQuantity();
            } else {
                quantity += p.getProductQuantities().get(i).getQuantity() + ",";
            }
        }
        SoLuong_edt.setText(quantity);

        //set Spiner


        //nếu có hình ảnh thì set hình ảnh
        sliderProduct.setVisibility(View.VISIBLE);
        tv_soluonghinhanh.setText(String.valueOf(p.getProductImages().size()));//set số lượng ảnh

        sliderProductImageAdapter = new SliderProductImageAdapter(getApplicationContext(), p.getProductImages(), REQUEST_CODE);
        sliderProduct.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        sliderProduct.setSliderAdapter(sliderProductImageAdapter);

        //tạo list ảnh mới để chọn, nếu có ảnh mới thì thay thế nó
        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAndCheckData();
                progressDialog.show();
                //xử lý thêm sản phẩm ở đây
                List<MultipartBody.Part> imagesParts = new ArrayList<>();
                for (File file: fileList){
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("productImages", file.getName(), requestBody);
                    imagesParts.add(part);
                }
                RequestBody productIdBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(p.getId()));
                RequestBody nameBody = RequestBody.create(MediaType.parse("multipart/form-data"), productName);
                RequestBody descriptionBody = RequestBody.create(MediaType.parse("multipart/form-data"), productDescription);
                RequestBody madeOfBody = RequestBody.create(MediaType.parse("multipart/form-data"), madeOf);
                RequestBody madeInBody = RequestBody.create(MediaType.parse("multipart/form-data"), madeIn);
                RequestBody colorBody = RequestBody.create(MediaType.parse("multipart/form-data"), color);
                RequestBody priceBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(productPrice));
                RequestBody promoPriceBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(productPromoPrice));
                RequestBody categoryBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(categoryId));
                RequestBody styleBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(styleId));
                RequestBody storeBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(storeId));
                RequestBody statusBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(1));
                List<RequestBody> sizeBodyList = new ArrayList<>();
                for (String s : sizeList){
                    RequestBody sizeBody = RequestBody.create(MediaType.parse("multipart/form-data"), s);
                    sizeBodyList.add(sizeBody);
                }
                List<RequestBody> quantityBodyList = new ArrayList<>();
                for (Integer i : quantityList){
                    RequestBody quantityBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(i));
                    quantityBodyList.add(quantityBody);
                }
                apiService.updateProduct(productIdBody, nameBody, imagesParts, priceBody,
                        promoPriceBody, descriptionBody,
                        madeOfBody, colorBody,
                        madeInBody, styleBody,
                        categoryBody, storeBody,
                        sizeBodyList, quantityBodyList,
                        statusBody).enqueue(new Callback<ProductDetailResponse>() {
                    @Override
                    public void onResponse(Call<ProductDetailResponse> call, Response<ProductDetailResponse> response) {
                        progressDialog.dismiss();
                        if (response.isSuccessful()){
                            Toast.makeText(getApplicationContext(), "Cập nhật sản phẩm thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), QuanLySanPhamActivity.class);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(getApplicationContext(), "Cập nhật sản phẩm thất bại", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ProductDetailResponse> call, Throwable t) {
                        Log.e("TAG", "onFailure: " + t.getMessage() );
                    }
                });
            }
        });

    }


    private void anhXa() {
        NameProduct_edt = findViewById(R.id.NameProduct_edt);
        Price_edt = findViewById(R.id.Price_edt);
        promoprice_edt = findViewById(R.id.promoprice_edt);
        Description_edt = findViewById(R.id.Description_edt);
        Makeof_edt = findViewById(R.id.Makeof_edt);
        Color_edt = findViewById(R.id.Color_edt);
        Makein_edt = findViewById(R.id.Makein_edt);
        sliderProduct = findViewById(R.id.sliderProduct);
        flthemHinhAnh = findViewById(R.id.flthemHinhAnh);
        tv_soluonghinhanh = findViewById(R.id.tv_soluonghinhanh);
        Category_textview = findViewById(R.id.Category_textview);
        Style_textview = findViewById(R.id.Style_textview);
        tvSubmit = findViewById(R.id.tvSubmit);
        switchTrangThai = findViewById(R.id.switchTrangThai);
        Category_spinner = findViewById(R.id.Category_spinner);
        Stype_spinner = findViewById(R.id.Stype_spinner);
        KichThuoc_edt = findViewById(R.id.KichThuoc_edt);
        SoLuong_edt = findViewById(R.id.SoLuong_edt);
        categoryList = new ArrayList<>();
        styleList = new ArrayList<>();
        tvTrangThai = findViewById(R.id.tvTrangThai);
        apiService = RetrofitCosmeticShop.getRetrofit().create(APIService.class);
        sharedPrefManager = new SharedPrefManager(getApplicationContext());
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Đang xử lý...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có muốn thực hiện hành động này?");
        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        storeId = sharedPrefManager.getStoreId();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_IMAGE_PICKER && resultCode == RESULT_OK && data != null) {
            List<Image> images = ImagePicker.getImages(data);
            for (Image image : images) {
                Log.d("TAG", "onActivityResult: " + image.getId() + " " + image.getPath());
                File file = new File(image.getPath());
                fileList.add(file);
            }
            tv_soluonghinhanh.setText(String.valueOf(images.size()));

            // Your code for handling the selected images
        }
    }
}