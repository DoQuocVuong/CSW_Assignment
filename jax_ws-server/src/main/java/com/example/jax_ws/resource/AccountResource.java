package com.example.jax_ws.resource;

import com.example.jax_ws.model.AccountModel;

import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;

public class AccountResource {
    private AccountModel accountModel;

    public AccountResource(){
        this.accountModel = new AccountModel();
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findByid(@HeaderParam("Authorization") int userId){
        try {
            return Response.status(Response.Status.OK).entity(AccountModel.findById(userId)).build();
        } catch (SQLException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
