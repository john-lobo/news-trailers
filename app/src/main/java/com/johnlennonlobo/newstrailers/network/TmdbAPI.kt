package com.johnlennonlobo.newstrailers.network

import com.johnlennonlobo.newstrailers.network.model.dto.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {

    @GET("trending/{media_type}/{time_window}")
//  @GET("trending/movie/day")
   suspend  fun getTrending(

        // path posso colocar direto no @GET da url o valor que eu quero -> //@GET("/trending/movie/day/"),
        // mas posso mandar por parametro tbm como esta sendo usado no momento..
        // usado em filtros

        @Path("media_type")
        media_type :String,

        @Path("time_window")
        time_window :String,

        // query acrescenta na url sozinha...
        @Query("language")
        language:String,

        @Query("page")
        page:Int,
    ) :NetworkResponse<MovieResponseDTO,ErrorResponse>

}