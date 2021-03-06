package com.ahf.antwerphasfallen.Helpers;

import com.ahf.antwerphasfallen.Model.Anagrams;
import com.ahf.antwerphasfallen.Model.DAD;
import com.ahf.antwerphasfallen.Model.FinishedGame;
import com.ahf.antwerphasfallen.Model.Game;
import com.ahf.antwerphasfallen.Model.Inventory;
import com.ahf.antwerphasfallen.Model.Item;
import com.ahf.antwerphasfallen.Model.Location;
import com.ahf.antwerphasfallen.Model.Player;
import com.ahf.antwerphasfallen.Model.QuizPuzzles;
import com.ahf.antwerphasfallen.Model.ShopItem;
import com.ahf.antwerphasfallen.Model.SubstitutionPuzzles;
import com.ahf.antwerphasfallen.Model.Team;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by Jorren on 21/10/2018.
 */

public interface GameDataService {

    @GET("games/{id}")
    Call<Game> getGame(@Path("id") int id);

    @GET("games/history/{id}")
    Call<FinishedGame> getFinishedGame(@Path("id") int gameId);

    @GET("games/")
    Call<List<Game>> getGames();

    @GET("players/{id}")
    Call<Player> getPlayer(@Path("id") int id);

    @GET("teams/{id}")
    Call<Team> getTeam(@Path("id") int id);

    @PUT("teams/{id}/blackout")
    Call<Team> stopBlackout(@Path("id") int teamId);

    @PUT("teams/{id}/timer")
    Call<Team> resetTimer(@Path("id") int teamId);

    @POST("teams/{id}/use/{ItemId}")
    Call<Inventory> useShopItem(@Path("id") int teamId, @Path("ItemId") int InventoryItemId, @Body int targetTeamId);

    @GET("shop")
    Call<ArrayList<ShopItem>> getShopItems();

    @PUT("shop/buy/{shopItemId}")
    Call<Inventory> buyShopItem(@Path("shopItemId") int shopItemId, @Body int teamId);

    @GET("inventory/{id}")
    Call<Inventory> getInventory(@Path("id") int id);

    @GET("locations/anagram/{location}")
    Call<List<Anagrams>> getAnagramByName(@Path("location") String location);

    @GET("locations/subs/{location}")
    Call<List<SubstitutionPuzzles>> getSubsByName(@Path("location") String location);

    @GET("locations/dad/{location}")
    Call<List<DAD>> getDadByName(@Path("location") String location);

    @GET("locations/quiz/{location}")
    Call<List<QuizPuzzles>> getQuizByName(@Path("location") String location);

    @GET("teams/randomlocation/{id}")
    Call<Location> getRandomLocation(@Path("id") int id);

    @GET("shop/ingredients")
    Call<ArrayList<Item>> getIngredients();

    @POST("games/newgame/{teams}")
    Call<Game> newGame(@Path("teams") int teams,@Body String[] teamNames);

    @POST("games/join/{gameId}")
    Call<Player> joinGame(@Path("gameId") int gameId, @Body int teamId);

    @DELETE("games/{gameId}")
    Call<Boolean> endGame(@Path("gameId") int gameId);

    @PUT("teams/{id}/{money}")
    Call<Team> updateMoney(@Path("id") int id, @Path("money") int money);

    @PUT("rewards/{teamId}")
    Call<Team> reward(@Path("teamId") int teamId, @Header("difficulty") String difficulty, @Header("answer") String answer, @Header("gotIngredient") String gotIngredient, @Body ArrayList<String> missingIngredients);
}
