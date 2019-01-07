package com.ahf.antwerphasfallen;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jorren on 21/10/2018.
 */

public interface GameDataService {

    @GET("games")
    Call<GameList> getGames();

    @GET("quiz")
    Call<QuizPuzzles> GetQuestions();

    @GET("puzzles/substitution/{id}")
    Call<SubstitutionPuzzles> getQuestionbyId(@Path("id") int id);

    @GET("games/{id}")
    Call<Game> getGame(@Path("id") int id);

    @GET("players/{id}")
    Call<Player> getPlayer(@Path("id") int id);

    @GET("teams/{id}")
    Call<Team> getTeam(@Path("id") int id);

    @GET("shop")
    Call<ArrayList<ShopItem>> getShopItems();

    @PUT("shop/buy/{shopItemId}")
    Call<Inventory> buyShopItem(@Path("shopItemId") int shopItemId, @Body int teamId);

    @GET("inventory/{id}")
    Call<Inventory> getInventory(@Path("id") int id);

    @GET("locations")
    Call<LocationList> getLocations();



    @GET("locations/{id}")
    Call<Location> getLocation(@Path("id") int id);

    @GET("teams/randomlocation/{id}")
    Call<Location> getRandomLocation(@Path("id") int id);

    @POST("games/newgame/{teams}")
    Call<Game> newGame(@Path("teams") int teams,@Body String[] teamNames);

    @POST("games/join/{gameId}")
    Call<Player> joinGame(@Path("gameId") int gameId, @Body int teamId);

    @DELETE("games/{gameId}")
    Call<Boolean> endGame(@Path("gameId") int gameId);

}
