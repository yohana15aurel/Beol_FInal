package android.example.mocar;

import android.renderscript.Sampler;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestApi {


    @Multipart
    @POST("/dev/mit/1317012/mst_upload_pict.php")
    Call<ResponseBody> postItemImage(@Part("isImageEmpty") RequestBody isImageEmpty, @Part("userToken") RequestBody userToken, @Part MultipartBody.Part image);

    @Multipart
    @POST("/dev/mit/1317012/mst_upload_pictKonsul.php")
    Call<ResponseBody> postItemImageKonsul(@Part("isImageEmpty") RequestBody isImageEmpty, @Part("userToken") RequestBody userToken, @Part MultipartBody.Part image);
}
