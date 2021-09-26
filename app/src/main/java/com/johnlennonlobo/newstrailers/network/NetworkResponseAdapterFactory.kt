package com.johnlennonlobo.newstrailers.network

import com.johnlennonlobo.newstrailers.network.model.dto.MovieResponseDTO
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class NetworkResponseAdapterFactory : CallAdapter.Factory(){

    override fun get(
        returnType: Type,
        annotations: Array<out Annotation>,
        retrofit: Retrofit
    ): CallAdapter<*, *>? {

        //verifica se os parametros correspondem
        if(Call::class.java != getRawType(returnType)){
            return null
        }

        //checka se tem parametros
        check(returnType is ParameterizedType){
            "return type must be parameterized as Call<NetworkResponse<something>>"
        }

        // entra dentro do parametro do returnType
        val responseType = getParameterUpperBound(0, returnType)

        if(NetworkResponse::class.java !=  getRawType(responseType)){
            return null
        }

        check(responseType is ParameterizedType){
            "return type must be parameterized as NetworkResponse<something>"
        }

        val successBodyType = getParameterUpperBound(0,responseType)
        val errBodyType = getParameterUpperBound(1,responseType)

        val errorBodyConverter = retrofit.nextResponseBodyConverter<Any>(null,errBodyType, annotations)

        return NetworkResponseAdapter<Any, Any>(successBodyType,errorBodyConverter)
    }

}