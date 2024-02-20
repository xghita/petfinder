package com.ghitai.petfinder.data.network


import com.ghitai.petfinder.data.network.model.AccessTokenResponse
import com.ghitai.petfinder.data.network.model.PetDetailResponse
import com.ghitai.petfinder.data.network.model.PetListResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface PetApi {

    @FormUrlEncoded
    @POST("oauth2/token")
    fun getAccessToken(
        @Field("grant_type") grantType: String,
        @Field("client_id") clientId: String,
        @Field("client_secret") clientSecret: String
    ): Single<AccessTokenResponse>

    @GET("animals")
    fun getPetList(): Single<PetListResponse>

    @GET("animals/{id}")
    fun getPetDetail(@Path("id") pet_id: Int): Single<PetDetailResponse>
}