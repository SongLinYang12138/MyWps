package com.example.ysl.mywps.net;

        import com.example.ysl.mywps.bean.PostQueryInfo;

        import okhttp3.MultipartBody;
        import okhttp3.ResponseBody;
        import retrofit2.Call;
        import retrofit2.http.GET;
        import retrofit2.http.Multipart;
        import retrofit2.http.POST;
        import retrofit2.http.Part;
        import retrofit2.http.Query;
        import retrofit2.http.Url;

/**
 * Created by ysl on 2018/1/17.
 */

public interface NetApi {
    @POST("query")
    Call<String> login(@Query("username") String username, @Query("password") String password, @Query("regid") String regId);

    @POST("query")
    Call<String> doucmentList(@Query("token") String token, @Query("page") String page, @Query("pagelimit") String pagemilit);

    /**
     * 提交审核
     * */
    @Multipart
    @POST("query")
    Call<String> commitAudit(@Query("doc_id") String doc_id, @Query("to_uid") String to_uid, @Query("token") String token,@Part MultipartBody.Part file);

    @GET
    Call<ResponseBody> downloadWps(@Url String url);

    @POST("query")
    Call<String> contact(@Query("token") String token);

    /**
     * 文档返回拟稿人
     * */
    @Multipart
    @POST("query")
    Call<String> uploadWps(@Query("doc_id") String docId, @Query("proce_id") String proceId, @Query("token") String token, @Query("opinion") String opinion, @Part MultipartBody.Part file);

    /**
     *提交文件领导签署
     */
    @Multipart
    @POST("query")
    Call<String> commitSign(@Query("doc_id") String docId,@Query("proce_id") String proceId,@Query("token") String token,@Query("opinion") String opinion,@Part MultipartBody.Part file);

    /***
     *签署完成返回公文给拟稿人
     */
    @Multipart
    @POST("query")
    Call<String> signedCommit(@Query("proce_id") String proceId,@Query("doc_id") String docId,@Query("opinion") String opinion,@Query("is_signed") String signed,@Query("token") String token,@Part MultipartBody.Part file);

}
